package de.garrafao.phitag.application.instance.sentimentandchoice;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.instance.data.DeleteInstanceCommand;
import de.garrafao.phitag.application.sampling.data.SamplingEnum;
import de.garrafao.phitag.domain.annotationprocessinformation.AnnotationProcessInformation;
import de.garrafao.phitag.domain.annotationprocessinformation.error.AnnotationProcessInformationException;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.error.CsvParseException;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstance;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstanceFactory;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentInstanceAndChoiceRepository;
import de.garrafao.phitag.domain.instance.sentimentandchoice.error.SentimentAndChoiceInstanceException;
import de.garrafao.phitag.domain.instance.sentimentandchoice.page.SentimentAndChoicePageBuilder;
import de.garrafao.phitag.domain.instance.sentimentandchoice.query.SentimentAndChoiceInstanceQueryBuilder;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.query.SentimentAndChoiceJudgementQueryBuilder;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import de.garrafao.phitag.domain.phitagdata.usage.UsageRepository;
import de.garrafao.phitag.domain.phitagdata.usage.error.UsageNotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SentimentAndChoiceInstanceApplicationService {

    private final UsageRepository usageRepository;

    private final SentimentInstanceAndChoiceRepository sentimentInstanceAndChoiceRepository;

    private final CommonService commonService;

    @Autowired
    public SentimentAndChoiceInstanceApplicationService(
            final UsageRepository usageRepository,
            final SentimentInstanceAndChoiceRepository sentimentInstanceAndChoiceRepository,

            final CommonService commonService) {
        this.usageRepository = usageRepository;

        this.sentimentInstanceAndChoiceRepository = sentimentInstanceAndChoiceRepository;
        this.commonService = commonService;
    }

    // Getter

    /**
     * Get all Sentiment instances for a given phase
     * 
     * @return List of {@link SentimentAndChoiceInstance}
     */
    public List<SentimentAndChoiceInstance> findByPhase(final Phase phase) {
        final Query query = new SentimentAndChoiceInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.sentimentInstanceAndChoiceRepository.findByQuery(query);
    }

    /**
     * Gett all Sentiment instances for a given phase
     * 
     * @param phase      the phase
     * @param pagesize   the size of the page
     * @param pagenumber the number of the page
     * @param orderBy    the field to order by
     * @return a {@link Page} of {@link SentimentAndChoiceInstance}
     */
    public Page<SentimentAndChoiceInstance> findByPhasePaged(
            final Phase phase,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new SentimentAndChoiceInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();

        return this.sentimentInstanceAndChoiceRepository.findByQueryPaged(query,
                new SentimentAndChoicePageBuilder()
                        .withPageSize(pagesize)
                        .withPageNumber(pagenumber)
                        .withOrderBy(orderBy)
                        .build());
    }



    /**
     * Sample a instance for a given phase and corresponding annotator
     *
     * @param phase     the phase
     * @param annotator the annotator
     * @return a {@link SentimentAndChoiceInstance}
     */
    @Transactional
    public SentimentAndChoiceInstance getAnnotationInstance(final Phase phase, final Annotator annotator) {
        return this.sample(phase, annotator);
    }

    /**
     * Export all instances for a given phase
     * 
     * @param phase the phase
     * @return a {@link InputStreamResource} containing the instances
     */
    public InputStreamResource exportInstance(final Phase phase) {
        List<SentimentAndChoiceInstance> instances = this.findByPhase(phase);
        String[] csvHeader = {
                "instanceID", "dataIDs", "label_set", "non_label"
        };
        List<List<String>> csvData = parseInstancesToCsvBody(instances);

        ByteArrayInputStream outputStream;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = createCsvPrinter(csvHeader, out);
        csvData.forEach(row -> {
            try {
                csvPrinter.printRecord(row);
            } catch (Exception e) {
                throw new CsvParseException();
            }
        });
        try {
            csvPrinter.flush();
            outputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new CsvParseException();
        }

        return new InputStreamResource(outputStream);

    }

    /**
     * Import use pairs for a given phase from a CSV file.
     * @param phase the phase
     * @param file  the CSV file
     */
    @Transactional
    public void save(final Phase phase, final MultipartFile file) {
        validateCsvFile(file);
        parseCsvFile(file).forEach(csvrecord -> {
            final SentimentAndChoiceInstance instanceData = parseRecordToInstance(phase, csvrecord);
            validateUniqueInstance(instanceData);
            this.sentimentInstanceAndChoiceRepository.save(instanceData);
        });

        this.generateSamplingTasks(phase);
    }

    /**
     * Generate instances for a given phase
     * 
     * @param phase the phase
     */
    @Transactional
    public void generateInstances(Phase phase) {
        // fetch all data ids for the project
        List<String> dataIds = this.usageRepository.findAllDataIdsByProjectnameAndOwnername(
                phase.getId().getProjectid().getName(),
                phase.getId().getProjectid().getOwnername());

        // create instances for each data id
        SentimentAndChoiceInstanceFactory factory = new SentimentAndChoiceInstanceFactory();
        factory.withPhase(phase).withLabelSet("").withNonLabel("-");

        for (String dataId : dataIds) {
            factory.withInstanceId(UUID.randomUUID().toString())
                    .withUsage(this.usageRepository
                            .findByIdDataidAndIdProjectidNameAndIdProjectidOwnername(dataId,
                                    phase.getId().getProjectid().getName(), phase.getId().getProjectid().getOwnername())
                            .orElseThrow(UsageNotFoundException::new));
            this.sentimentInstanceAndChoiceRepository.save(factory.build());
        }

    }

    // Parser

    private CSVPrinter createCsvPrinter(String[] csvHeader, ByteArrayOutputStream outputStream) {
        CSVPrinter printer = null;

        try {
            PrintWriter writer = new PrintWriter(outputStream);
            CSVFormat format = CSVFormat.Builder.create().setHeader(csvHeader).setDelimiter("\t").build();
            printer = new CSVPrinter(writer, format);
        } catch (Exception e) {
            throw new CsvParseException();
        }

        return printer;
    }

    private List<List<String>> parseInstancesToCsvBody(List<SentimentAndChoiceInstance> instances) {
        List<List<String>> csvBody = new ArrayList<>();

        for (SentimentAndChoiceInstance instance : instances) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(instance.getId().getInstanceid());
            csvRow.add(String.format("%s", instance.getUsage().getId().getDataid()));
            csvRow.add(String.join(",", instance.getLabelSet()));
            csvRow.add(instance.getNonLabel());
            csvBody.add(csvRow);
        }

        return csvBody;
    }

    private Iterable<CSVRecord> parseCsvFile(final MultipartFile file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVFormat format = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).setDelimiter("\t")
                    .build();
            CSVParser parser = new CSVParser(reader, format);
            Iterable<CSVRecord> records = parser.getRecords();
            parser.close();

            return records;
        } catch (Exception e) {
            throw new CsvParseException();
        }
    }

    private SentimentAndChoiceInstance parseRecordToInstance(final Phase phase, final CSVRecord csvrecord) {
        String instanceId;
        String dataId;
        String labelSet;
        String nonLabel;

        try {
            instanceId = csvrecord.get("instanceID");
            dataId = csvrecord.get("dataIDs");
            labelSet = csvrecord.get("label_set");
            nonLabel = csvrecord.get("non_label");
        } catch (IllegalArgumentException e) {
            throw new CsvParseException("CSV record is not valid, please check the format");
        }

        Usage usage = this.usageRepository
                .findByIdDataidAndIdProjectidNameAndIdProjectidOwnername(dataId,
                        phase.getId().getProjectid().getName(), phase.getId().getProjectid().getOwnername())
                .orElseThrow(() -> new UsageNotFoundException("Usage not found for data id " + dataId));

        return new SentimentAndChoiceInstance(instanceId, phase, usage, labelSet, nonLabel);
    }

    // Validation

    private void validateCsvFile(final MultipartFile file) {
        if (file == null) {
            throw new IllegalArgumentException("file is null");
        }
        if (file.isEmpty()) {
            throw new IllegalArgumentException("file is empty");
        }
        if (file.getContentType() == null) {
            throw new IllegalArgumentException("file is not a csv");
        }
    }

    private void validateUniqueInstance(final SentimentAndChoiceInstance instanceData) {
        if (this.sentimentInstanceAndChoiceRepository
                .findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
                        instanceData.getId().getInstanceid(), instanceData.getId().getPhaseid().getName(),
                        instanceData.getId().getPhaseid().getProjectid().getName(),
                        instanceData.getId().getPhaseid().getProjectid().getOwnername())
                .isPresent()) {
            throw new SentimentAndChoiceInstanceException("Instance already exists");
        }
    }

    // Sampling methods

    /**
     * Generate sampling tasks for a given phase and all annotators
     * 
     * @param phase the phase
     */
    @Transactional
    public void generateSamplingTasks(final Phase phase) {
        this.commonService.getAnnotatorsOfProject(phase.getId().getProjectid().getOwnername(),
                phase.getId().getProjectid().getName()).forEach(annotator -> {
                    this.generateSamplingTask(phase, annotator);
                });
    }

    /**
     * Generate n  sampling order for random sampling without replacement.
     *
     * @param phase
     */
    private List<String> generateNSamplingOrderWithoutReplacement(final Phase phase) {

        final  int instancesPerSample = phase.getInstancePerSample();

        List<String> samplingOrder = new ArrayList<>();

        List<SentimentAndChoiceInstance> instances = new ArrayList<>(this.commonService.findSentimentInstanceByPhase(phase));
        Collections.shuffle(instances);
        int totalInstances = instances.size();
        int index = 0;
        while (samplingOrder.size() < instancesPerSample && index < totalInstances) {
            SentimentAndChoiceInstance instance = instances.get(index++);
            String instanceId = instance.getId().getInstanceid();
            if (!samplingOrder.contains(instanceId)) {
                samplingOrder.add(instanceId);
            }
        }

        // If not enough unique instances are available, reset the sampling order
        if (samplingOrder.size() < instancesPerSample) {
            // Reset the sampling order
            samplingOrder.clear();
            // Restart the selection process
            index = 0;
            while (samplingOrder.size() < instancesPerSample && index < totalInstances) {
               SentimentAndChoiceInstance instance = instances.get(index++);
                String instanceId = instance.getId().getInstanceid();
                if (!samplingOrder.contains(instanceId)) {
                    samplingOrder.add(instanceId);
                }
            }
        }

        return samplingOrder;
    }

    /**
     * Generate n  sampling order for random sampling with replacement.
     *
     * @param phase
     */
    private List<String> generateNSamplingOrderWithReplacement(final Phase phase) {
        final int instancesPerSample = phase.getInstancePerSample();
        List<String> samplingOrder = new ArrayList<>();
        List<SentimentAndChoiceInstance> instances = new ArrayList<>(this.commonService.findSentimentInstanceByPhase(phase));
        int totalInstances = instances.size();
        for (int i = 0; i < instancesPerSample; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(totalInstances);
            SentimentAndChoiceInstance instance = instances.get(randomIndex);
            String instanceId = instance.getId().getInstanceid();
            samplingOrder.add(instanceId);
        }
        return samplingOrder;
    }



    /**
     * Generate sampling tasks for a given phase and annotator
     * 
     * @param phase the phase
     */
    @Transactional
    public void generateSamplingTask(final Phase phase, final Annotator annotator) {
        List<String> samplingOrder = new ArrayList<>();

        // IF this gets out of hand, switch or some pattern
        if (phase.getSampling().getName().equals(SamplingEnum.SAMPLING_RANDOM_WITH_REPLACEMENT.name())
                || phase.getSampling().getName().equals(SamplingEnum.SAMPLING_RANDOM_WITHOUT_REPLACEMENT.name())) {
            samplingOrder = this.generateSamplingOrderWithoutReplacement(phase);
        } else if (phase.getSampling().getName().equals(SamplingEnum.SAMPLING_ID_ORDER.name())) {
            samplingOrder = this.generateSamplingIDOrder(phase);
        }
        else if (phase.getSampling().getName().equals(SamplingEnum.N_SAMPLING_RANDOM_WITHOUT_REPLACEMENT.name())) {
            samplingOrder = this.generateNSamplingOrderWithoutReplacement(phase);
        }
        else if (phase.getSampling().getName().equals(SamplingEnum.N_SAMPLING_RANDOM_WITH_REPLACEMENT.name())) {
            samplingOrder = this.generateNSamplingOrderWithReplacement(phase);
        }

        if (samplingOrder.isEmpty()) {
            throw new AnnotationProcessInformationException("Sampling order is empty");
        }

        String flattenedSamplingOrder = String.join(",", samplingOrder);

        final AnnotationProcessInformation annotationProcessInformation = new AnnotationProcessInformation(annotator,
                phase);
        annotationProcessInformation.setOrder(flattenedSamplingOrder);
        annotationProcessInformation.setIndex(0);

        this.commonService.saveAnnotationProcessInformation(annotationProcessInformation);
    }

    /**
     * Generate sampling order for random sampling without replacement.
     * 
     * @param phase
     * @return
     */
    private List<String> generateSamplingOrderWithoutReplacement(final Phase phase) {
        List<String> samplingOrder = new ArrayList<>();

        this.commonService.findSentimentInstanceByPhase(phase).forEach(instance -> {
            samplingOrder.add(instance.getId().getInstanceid());
        });

        Collections.shuffle(samplingOrder);
        return samplingOrder;
    }

    /**
     * Generate sampling order for ID-based sampling.
     * 
     * @param phase
     * @return
     */
    private List<String> generateSamplingIDOrder(final Phase phase) {
        List<String> samplingOrder = new ArrayList<>();

        this.commonService.findSentimentInstanceByPhase(phase).forEach(instance -> {
            samplingOrder.add(instance.getId().getInstanceid());
        });

        Collections.sort(samplingOrder, (o1, o2) -> {
            try {
                int i1 = Integer.parseInt(o1);
                int i2 = Integer.parseInt(o2);
                return i1 - i2;
            } catch (Exception e) {
                return o1.compareTo(o2);
            }
        });

        return samplingOrder;
    }

    /**
     * Sample with replacement.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * 
     * @return the a use pair instance
     */
    @Transactional
    public SentimentAndChoiceInstance sample(final Phase phase, final Annotator annotator) {
        AnnotationProcessInformation annotationProcessInformation;

        try {
            annotationProcessInformation = this.commonService.getAnnotationProcessInformation(annotator, phase);
        } catch (AnnotationProcessInformationException e) {
            this.generateSamplingTask(phase, annotator);
            annotationProcessInformation = this.commonService.getAnnotationProcessInformation(annotator, phase);
        }

        String queryId = annotationProcessInformation.next();

        if (phase.getSampling().getName().equals(SamplingEnum.SAMPLING_RANDOM_WITH_REPLACEMENT.name())) {
            queryId = annotationProcessInformation.getOrder()
                    .get((int) (Math.random() * annotationProcessInformation.getOrder().size()));
        }

        // If sampling index is null, return null
        if (queryId == null) {
            return null;
        }

        final Query query = new SentimentAndChoiceInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withInstanceid(queryId)
                .build();

        final List<SentimentAndChoiceInstance> instances = this.sentimentInstanceAndChoiceRepository.findByQuery(query);
        if (instances.isEmpty()) {
            throw new UsageNotFoundException();
        }

        return instances.get(0);
    }
    /**
     * Delete a sentiment and choice instance.
     *
     * @param phase     the phase
     * @param annotator the annotator
     * @param command   the command
     */
    @Transactional
    public void delete(final Phase phase, final Annotator annotator, final DeleteInstanceCommand command) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner(command.getOwner())
                .withProject(command.getProject())
                .withPhase(command.getPhase())
                .withInstanceid(command.getInstanceID())
                .build();
        final List<SentimentAndChoiceInstance> instances = this.sentimentInstanceAndChoiceRepository.findByQuery(query);
        this.sentimentInstanceAndChoiceRepository.delete(instances.get(0));
    }



}
