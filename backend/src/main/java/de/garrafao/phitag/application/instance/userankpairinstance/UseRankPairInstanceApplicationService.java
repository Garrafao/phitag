package de.garrafao.phitag.application.instance.userankpairinstance;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.sampling.data.SamplingEnum;
import de.garrafao.phitag.domain.annotationprocessinformation.AnnotationProcessInformation;
import de.garrafao.phitag.domain.annotationprocessinformation.error.AnnotationProcessInformationException;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.error.CsvParseException;
import de.garrafao.phitag.domain.error.InvalidLabelException;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstance;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstanceFactory;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairRepository;
import de.garrafao.phitag.domain.instance.userankpairinstances.error.UseRankPairInstanceAlreadyExistsException;
import de.garrafao.phitag.domain.instance.userankpairinstances.query.UseRankPairInstanceQueryBuilder;
import de.garrafao.phitag.domain.instance.userankrelative.page.UseRankRelativeInstancePageBuilder;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import de.garrafao.phitag.domain.phitagdata.usage.UsageRepository;
import de.garrafao.phitag.domain.phitagdata.usage.error.UsageNotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UseRankPairInstanceApplicationService {

    private final UsageRepository usageRepository;
    private final UseRankPairRepository useRankPairInstanceRepository;

    private final CommonService commonService;


    public UseRankPairInstanceApplicationService(UsageRepository usageRepository, UseRankPairRepository useRankPairInstanceRepository, CommonService commonService) {
        this.usageRepository = usageRepository;
        this.useRankPairInstanceRepository = useRankPairInstanceRepository;
        this.commonService = commonService;
    }

    // Getter

    /**
     * Get all use ranks pair for a given phase.
     *
     * @param phase the phase
     * @return a {@link UseRankPairInstance} list
     */
    public List<UseRankPairInstance> findByPhase(final Phase phase) {
        final Query query = new UseRankPairInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.useRankPairInstanceRepository.findByQuery(query);
    }
    /**
     * Get all Use Rank Pair Instances for a given phase paged.
     *
     * @param phase      the phase
     * @param pagesize   the size of the page
     * @param pagenumber the number of the page
     * @param orderBy    the field to order by
     * @return a {@link UseRankPairInstance} page
     */
    public Page<UseRankPairInstance> findByPhasePaged(
            final Phase phase,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new UseRankPairInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.useRankPairInstanceRepository.findByQueryPaged(query,
                new UseRankRelativeInstancePageBuilder()
                        .withPageSize(pagesize)
                        .withPageNumber(pagenumber)
                        .withOrderBy(orderBy)
                        .build());

    }


    /**
     * Get random use rank pair for a given phase.
     *
     * @param phase the phase
     * @return a {@link UseRankPairInstance}
     */
    @Transactional
    public UseRankPairInstance getAnnotationInstance(final Phase phase, final Annotator annotator) {
        return this.sample(phase, annotator);
    }

    /**
     * Export all use rank  pair for a given phase.
     *
     * @param phase the phase
     * @return a {@link InputStreamResource} with the CSV data
     */
    public InputStreamResource exportUseRankPairInstance(final Phase phase) {
        List<UseRankPairInstance> instanceData = this.findByPhase(phase);
        String[] csvHeader = {
                "instanceID", "dataIDs", "label_set", "non_label"
        };
        List<List<String>> csvData = parseUseRankInstancesToCsvBody(instanceData);

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
     * Import use rank instance for a given phase from a CSV file.
     *
     * @param phase the phase
     * @param file  the CSV file
     */
    @Transactional
    public void save(final Phase phase, final MultipartFile file) {
        validateCsvFile(file);
        parseCsvFile(file).forEach(csvrecord -> {
            final UseRankPairInstance instanceData = parseRecordToUseRankPairInstance(phase, csvrecord);
            validateUniqueInstance(instanceData);
            this.useRankPairInstanceRepository.save(instanceData);
        });
        this.generateSamplingTasks(phase);
    }

    /**
     * Generate instances from all usages for a phase.
     *
     * @param phase    the phase
     * @param nonLabel
     * @param labels
     */
    @Transactional
    public void generateInstances(Phase phase, List<String> labels, String nonLabel) {
        // fetch all data ids for the project
        List<String> dataIds = this.usageRepository.findAllDataIdsByProjectnameAndOwnername(
                phase.getId().getProjectid().getName(),
                phase.getId().getProjectid().getOwnername());

        UseRankPairInstanceFactory factory = new UseRankPairInstanceFactory();
        factory.withPhase(phase).withLabelSet(String.join(",", labels)).withNonLabel(nonLabel);

        for (int i = 0; i < dataIds.size(); i++) {
            for (int j = i + 1; j < dataIds.size(); j++) {
                for (int k = j + 1; k < dataIds.size(); k++) {
                    for (int l = k + 1; l < dataIds.size(); l++) {

                        Usage usage1 = fetchUsage(dataIds.get(i), phase);
                        Usage usage2 = fetchUsage(dataIds.get(j), phase);
                        Usage usage3 = fetchUsage(dataIds.get(k), phase);
                        Usage usage4 = fetchUsage(dataIds.get(l), phase);


                        UseRankPairInstance instance = factory.withInstanceId(UUID.randomUUID().toString())
                                .withFirstUsage(usage1)
                                .withSecondUsage(usage2)
                                .withThirdUsage(usage3)
                                .withFourthUsage(usage4)
                                .build();
                        this.useRankPairInstanceRepository.save(instance);
                    }
                }
            }
        }
    }
    private Usage fetchUsage(String dataId, Phase phase) {
        return this.usageRepository.findByIdDataidAndIdProjectidNameAndIdProjectidOwnername(
                dataId, phase.getId().getProjectid().getName(),
                phase.getId().getProjectid().getOwnername()).orElseThrow(UsageNotFoundException::new);
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
    private List<List<String>> parseUseRankInstancesToCsvBody(List<UseRankPairInstance> useRankPairInstances) {
        List<List<String>> csvBody = new ArrayList<>();

        for (UseRankPairInstance data : useRankPairInstances) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(data.getId().getInstanceid());
            csvRow.add(String.format("%s,%s,%s,%s",
                    data.getFirstusage().getId().getDataid(),
                    data.getSecondusage().getId().getDataid(),
                    data.getThirdusage().getId().getDataid(),
                    data.getFourthusage().getId().getDataid()
                    )
            );
            csvRow.add(String.join(",", data.getLabelSet()));
            csvRow.add(data.getNonLabel());
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

    private UseRankPairInstance parseRecordToUseRankPairInstance(final Phase phase, final CSVRecord csvrecord) {
        String instanceId;
        List<String> dataIds;
        String labelSet;
        String nonLabel;
        try {
            instanceId = csvrecord.get("instanceID");
            dataIds = Arrays.stream(csvrecord.get("dataIDs").split(",")).collect(Collectors.toList());
            labelSet = csvrecord.get("label_set");
            nonLabel = csvrecord.get("non_label");
        } catch (IllegalArgumentException e) {
            throw new CsvParseException("CSV record is not valid, please check the format");
        }
        if(dataIds.size()<2){
            throw new CsvParseException("dataIDs must be 2 or more than 2" + dataIds.size());
        }



        List<String> countLabel = Arrays.stream(csvrecord.get("label_set").split(",")).collect(Collectors.toList());

        if (countLabel.size() != dataIds.size() / 2) {
            throw new InvalidLabelException();
        }



        Usage firstUsage, secondUsage, thirdUsage, fourthUsage, fifthusage, sixthusage, seventhusage,
        eightusage,ninthusage, tenthusage;
        switch (dataIds.size()) {
            case 2:
                firstUsage = getUsage(dataIds.get(0), phase);
                secondUsage = getUsage(dataIds.get(1), phase);

                return new UseRankPairInstance(instanceId,phase,firstUsage,secondUsage,labelSet,nonLabel);
            case 3:
                firstUsage = getUsage(dataIds.get(0), phase);
                secondUsage = getUsage(dataIds.get(1), phase);
                thirdUsage = getUsage(dataIds.get(2), phase);

                return new UseRankPairInstance(instanceId,phase,firstUsage,secondUsage,thirdUsage, labelSet,nonLabel);

            case 4:
                firstUsage = getUsage(dataIds.get(0), phase);
                secondUsage = getUsage(dataIds.get(1), phase);
                thirdUsage = getUsage(dataIds.get(2), phase);
                fourthUsage = getUsage(dataIds.get(3), phase);

                return new UseRankPairInstance(instanceId, phase, firstUsage, secondUsage, thirdUsage, fourthUsage, labelSet, nonLabel);
            case 5:
                firstUsage = getUsage(dataIds.get(0), phase);
                secondUsage = getUsage(dataIds.get(1), phase);
                thirdUsage = getUsage(dataIds.get(2), phase);
                fourthUsage = getUsage(dataIds.get(3), phase);
                fifthusage = getUsage(dataIds.get(4), phase);

                return new UseRankPairInstance(instanceId, phase, firstUsage, secondUsage, thirdUsage, fourthUsage,
                        fifthusage, labelSet, nonLabel);
            case 6:
                firstUsage = getUsage(dataIds.get(0), phase);
                secondUsage = getUsage(dataIds.get(1), phase);
                thirdUsage = getUsage(dataIds.get(2), phase);
                fourthUsage = getUsage(dataIds.get(3), phase);
                fifthusage = getUsage(dataIds.get(4), phase);
                sixthusage = getUsage(dataIds.get(5), phase);

                return new UseRankPairInstance(instanceId, phase, firstUsage, secondUsage,
                        thirdUsage, fourthUsage, fifthusage,
                        sixthusage, labelSet, nonLabel);
            case 7:
                firstUsage = getUsage(dataIds.get(0), phase);
                secondUsage = getUsage(dataIds.get(1), phase);
                thirdUsage = getUsage(dataIds.get(2), phase);
                fourthUsage = getUsage(dataIds.get(3), phase);
                fifthusage = getUsage(dataIds.get(4), phase);
                sixthusage = getUsage(dataIds.get(5), phase);
                seventhusage= getUsage(dataIds.get(6), phase);


                return new UseRankPairInstance(instanceId, phase, firstUsage, secondUsage,
                        thirdUsage, fourthUsage, fifthusage,
                        sixthusage, seventhusage, labelSet, nonLabel);
            case 8:
                firstUsage = getUsage(dataIds.get(0), phase);
                secondUsage = getUsage(dataIds.get(1), phase);
                thirdUsage = getUsage(dataIds.get(2), phase);
                fourthUsage = getUsage(dataIds.get(3), phase);
                fifthusage = getUsage(dataIds.get(4), phase);
                sixthusage = getUsage(dataIds.get(5), phase);
                seventhusage= getUsage(dataIds.get(6), phase);
                eightusage= getUsage(dataIds.get(7), phase);

                return new UseRankPairInstance(instanceId, phase, firstUsage, secondUsage,
                        thirdUsage, fourthUsage, fifthusage,
                        sixthusage, seventhusage,
                        eightusage, labelSet, nonLabel);
            case 9:
                firstUsage = getUsage(dataIds.get(0), phase);
                secondUsage = getUsage(dataIds.get(1), phase);
                thirdUsage = getUsage(dataIds.get(2), phase);
                fourthUsage = getUsage(dataIds.get(3), phase);
                fifthusage = getUsage(dataIds.get(4), phase);
                sixthusage = getUsage(dataIds.get(5), phase);
                seventhusage= getUsage(dataIds.get(6), phase);
                eightusage= getUsage(dataIds.get(7), phase);
                ninthusage= getUsage(dataIds.get(8), phase);

                return new UseRankPairInstance(instanceId, phase, firstUsage, secondUsage,
                        thirdUsage, fourthUsage, fifthusage,
                        sixthusage, seventhusage,
                        eightusage, ninthusage,
                        labelSet, nonLabel);
            case 10:
                firstUsage = getUsage(dataIds.get(0), phase);
                secondUsage = getUsage(dataIds.get(1), phase);
                thirdUsage = getUsage(dataIds.get(2), phase);
                fourthUsage = getUsage(dataIds.get(3), phase);
                fifthusage = getUsage(dataIds.get(4), phase);
                sixthusage = getUsage(dataIds.get(5), phase);
                seventhusage= getUsage(dataIds.get(6), phase);
                eightusage= getUsage(dataIds.get(7), phase);
                ninthusage= getUsage(dataIds.get(8), phase);
                tenthusage= getUsage(dataIds.get(9), phase);

                return new UseRankPairInstance(instanceId, phase, firstUsage, secondUsage,
                        thirdUsage, fourthUsage, fifthusage,
                        sixthusage, seventhusage,
                        eightusage, ninthusage, tenthusage,
                        labelSet, nonLabel);

            default:
                throw new CsvParseException("Unexpected number of dataIDs: " + dataIds.size());
        }
    }

    private Usage getUsage(String dataId, Phase phase) {
        return this.usageRepository
                .findByIdDataidAndIdProjectidNameAndIdProjectidOwnername(dataId,
                        phase.getId().getProjectid().getName(), phase.getId().getProjectid().getOwnername())
                .orElseThrow(() -> new UsageNotFoundException("Usage not found for data id " + dataId));
    }

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


    private void validateUniqueInstance(final UseRankPairInstance instanceData) {
        if (this.useRankPairInstanceRepository
                .findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
                        instanceData.getId().getInstanceid(), instanceData.getId().getPhaseid().getName(),
                        instanceData.getId().getPhaseid().getProjectid().getName(),
                        instanceData.getId().getPhaseid().getProjectid().getOwnername())
                .isPresent()) {
            throw new UseRankPairInstanceAlreadyExistsException();
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
     *
     */
    private List<String> generateSamplingOrderWithoutReplacement(final Phase phase) {
        List<String> samplingOrder = new ArrayList<>();

        this.commonService.findUseRankPairInstanceByPhase(phase).forEach(useRankPairInstance -> {
            samplingOrder.add(useRankPairInstance.getId().getInstanceid());
        });

        Collections.shuffle(samplingOrder);
        return samplingOrder;
    }

    /**
     * Generate n  sampling order for random sampling without replacement.
     *
     * @param phase
     */
    private List<String> generateNSamplingOrderWithoutReplacement(final Phase phase) {

        final  int instancesPerSample = phase.getInstancePerSample();

        List<String> samplingOrder = new ArrayList<>();

        List<UseRankPairInstance> instances = new ArrayList<>(this.commonService.findUseRankPairInstanceByPhase(phase));
        Collections.shuffle(instances);
        int totalInstances = instances.size();
        int index = 0;
        while (samplingOrder.size() < instancesPerSample && index < totalInstances) {
            UseRankPairInstance instance = instances.get(index++);
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
                UseRankPairInstance instance = instances.get(index++);
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
        List<UseRankPairInstance> instances = new ArrayList<>(this.commonService.findUseRankPairInstanceByPhase(phase));
        int totalInstances = instances.size();
        for (int i = 0; i < instancesPerSample; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(totalInstances);
            UseRankPairInstance instance = instances.get(randomIndex);
            String instanceId = instance.getId().getInstanceid();
            samplingOrder.add(instanceId);
        }
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

        this.commonService.findUseRankPairInstanceByPhase(phase).forEach(useRankPairInstance -> {
            samplingOrder.add(useRankPairInstance.getId().getInstanceid());
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



    @Transactional
    public UseRankPairInstance sample(final Phase phase, final Annotator annotator) {
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

        final Query query = new UseRankPairInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withInstanceid(queryId)
                .build();

        final List<UseRankPairInstance> instances = this.useRankPairInstanceRepository.findByQuery(query);
        if (instances.isEmpty()) {
            throw new UsageNotFoundException();
        }

        return instances.get(0);
    }

    public int countAllocatedInstanceToAnnotator(Phase phase, Annotator annotator){
        final  AnnotationProcessInformation annotationProcessInformation = this.commonService.getAnnotationProcessInformation(annotator, phase);
        return  annotationProcessInformation.getOrder().size();

    }


}
