package de.garrafao.phitag.application.judgement.usepairjudgement;

import de.garrafao.phitag.application.common.CommonMathService;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.AddUsePairJudgementCommand;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.DeleteUsePairJudgementCommand;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.EditUsePairJudgementCommand;
import de.garrafao.phitag.application.statistics.annotatostatistic.AnnotatorStatisticApplicationService;
import de.garrafao.phitag.application.statistics.phasestatistic.PhaseStatisticApplicationService;
import de.garrafao.phitag.application.statistics.userstatistic.UserStatisticApplicationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.authentication.error.AccessDenidedException;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.error.CsvParseException;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstance;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstanceRepository;
import de.garrafao.phitag.domain.instance.usepairinstance.query.UsePairInstanceQueryBuilder;
import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgement;
import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgementRepository;
import de.garrafao.phitag.domain.judgement.usepairjudgement.error.UsePairJudgementException;
import de.garrafao.phitag.domain.judgement.usepairjudgement.error.UsePairJudgementNotFoundException;
import de.garrafao.phitag.domain.judgement.usepairjudgement.page.UsePairJudgementPageBuilder;
import de.garrafao.phitag.domain.judgement.usepairjudgement.query.UsePairJudgementQueryBuilder;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.error.TutorialException;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasureEnum;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistory;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistoryRepository;
import de.garrafao.phitag.domain.tutorial.usepair.UsePairTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.usepair.UsePairTutorialJudgementRepository;
import de.garrafao.phitag.domain.tutorial.usepair.page.UsePairTutorialJudgementPageBuilder;
import de.garrafao.phitag.domain.tutorial.usepair.query.UsePairTutorialJudgementQueryBuilder;
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
import java.util.Arrays;
import java.util.List;

@Service
public class UsePairJudgementApplicationService {

    private final UsePairJudgementRepository usePairJudgementRepository;

    private final UsePairTutorialJudgementRepository usePairTutorialJudgementRepository;

    private final UsePairInstanceRepository usePairInstanceRepository;

    private final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository;

    // Statistics

    private final UserStatisticApplicationService userStatisticApplicationService;

    private final AnnotatorStatisticApplicationService annotatorStatisticApplicationService;

    private final PhaseStatisticApplicationService phaseStatisticApplicationService;

    private final CommonMathService commonMathService;





    @Autowired
    public UsePairJudgementApplicationService(
            final UsePairJudgementRepository usePairJudgementRepository,
            final UsePairTutorialJudgementRepository usePairTutorialJudgementRepository,
            final UsePairInstanceRepository usePairInstanceRepository,
            final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository,

            final UserStatisticApplicationService userStatisticApplicationService,
            final AnnotatorStatisticApplicationService annotatorStatisticApplicationService,
            final PhaseStatisticApplicationService phaseStatisticApplicationService,
            final CommonMathService commonMathService) {
        this.usePairJudgementRepository = usePairJudgementRepository;
        this.usePairTutorialJudgementRepository = usePairTutorialJudgementRepository;
        this.usePairInstanceRepository = usePairInstanceRepository;
        this.tutorialAnnotationMeasureHistoryRepository = tutorialAnnotationMeasureHistoryRepository;

        this.userStatisticApplicationService = userStatisticApplicationService;
        this.annotatorStatisticApplicationService = annotatorStatisticApplicationService;
        this.phaseStatisticApplicationService = phaseStatisticApplicationService;
        this.commonMathService = commonMathService;
    }

    // Getter

    /**
     * Get all use pair judgements for a given phase.
     * 
     * @param phase the phase
     * @return a {@link UsePairJudgement} list
     */
    public List<UsePairJudgement> findByPhase(final Phase phase) {
        final Query query = new UsePairJudgementQueryBuilder()
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.usePairJudgementRepository.findByQuery(query);
    }

    /**
     * Get all use pair judgements for a given phase as a paged list.
     * 
     * @param phase
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<UsePairJudgement> findByPhase(
            final Phase phase,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new UsePairJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.usePairJudgementRepository.findByQueryPaged(query, new UsePairJudgementPageBuilder()
                .withPageSize(pagesize)
                .withPageNumber(pagenumber)
                .withOrderBy(orderBy)
                .build());
    }

    //tutorials getter

    /**
     * Get all use pair tutorail judgements for a given phase.
     *
     * @param phase the phase
     * @return a {@link UsePairJudgement} list
     */
    public List<UsePairTutorialJudgement> findTutorialByPhase(final Phase phase) {
        final Query query = new UsePairTutorialJudgementQueryBuilder()
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.usePairTutorialJudgementRepository.findByQuery(query);
    }

    /**
     * Get all use pair tutorial judgements for a given phase as a paged list.
     *
     * @param phase
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<UsePairTutorialJudgement> findTutorialByPhase(
            final Phase phase,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new UsePairTutorialJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.usePairTutorialJudgementRepository.findByQueryPaged(query, new UsePairTutorialJudgementPageBuilder()
                .withPageSize(pagesize)
                .withPageNumber(pagenumber)
                .withOrderBy(orderBy)
                .build());
    }


    /**
     * Get all use pair judgements for a given annotator.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * @return a {@link UsePairJudgement} list
     */
    public List<UsePairJudgement> getHistory(final Phase phase, final Annotator annotator) {
        final Query query = new UsePairJudgementQueryBuilder().withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName()).withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername()).build();
        return this.usePairJudgementRepository.findByQuery(query);
    }

    /**
     * Get all use pair judgements for a given annotator.
     *
     * @param phase     the phase
     * @param annotator the annotator
     * @return a {@link UsePairJudgement} list
     */
    public List<UsePairTutorialJudgement> getTutorialHistory(final Phase phase, final Annotator annotator) {
        final Query query = new UsePairTutorialJudgementQueryBuilder().withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName()).withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername()).build();
        return this.usePairTutorialJudgementRepository.findByQuery(query);
    }

    /**
     * Get all use pair judgements for a given annotator as a paged list.
     * 
     * @param phase
     * @param annotator
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<UsePairJudgement> getHistory(
            final Phase phase,
            final Annotator annotator,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new UsePairJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();
        return this.usePairJudgementRepository.findByQueryPaged(query, new UsePairJudgementPageBuilder()
                .withPageSize(pagesize)
                .withPageNumber(pagenumber)
                .withOrderBy(orderBy)
                .build());
    }

    /**
     * Get all use pair judgements for a given annotator as a paged list.
     *
     * @param phase
     * @param annotator
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<UsePairTutorialJudgement> getTutorialHistory(
            final Phase phase,
            final Annotator annotator,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new UsePairTutorialJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();
        return this.usePairTutorialJudgementRepository.findByQueryPaged(query, new UsePairTutorialJudgementPageBuilder()
                .withPageSize(pagesize)
                .withPageNumber(pagenumber)
                .withOrderBy(orderBy)
                .build());
    }


    /**
     * Export all use pair judgements for a given phase.
     * 
     * @param phase the phase
     * @return a CSV file as {@link InputStreamResource}
     */
    public InputStreamResource exportUsePairJudgement(final Phase phase) {
        List<UsePairJudgement> resultData = this.findByPhase(phase);
        String[] csvHeader = {
                "instanceID", "label", "comment", "annotator"
        };
        List<List<String>> csvData = parseUsePairJudgementsToCsvBody(resultData);

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
     * Export all use pair tutorial judgements for a given phase.
     *
     * @param phase the phase
     * @return a CSV file as {@link InputStreamResource}
     */
    public InputStreamResource exportUsePairTutorialJudgement(final Phase phase) {
        List<UsePairTutorialJudgement> resultData = this.findTutorialByPhase(phase);
        String[] csvHeader = {
                "instanceID", "label", "comment", "annotator"
        };
        List<List<String>> csvData = parseUsePairTutorialJudgementsToCsvBody(resultData);

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
     * Count all use pair judgements for a given annotator.
     * 
     * @param annotator the annotator
     * @return the number of use pair judgements
     */
    public int countJudgements(Annotator annotator) {
        return (int) this.usePairJudgementRepository.findByQueryPaged(
                new UsePairJudgementQueryBuilder()
                        .withAnnotator(annotator.getId().getUsername())
                        .build(),
                new PageRequestWraper(1, 0)).getTotalElements();
    }



    /**
     * Count all use pair judgements for a given annotator and phase.
     * 
     * @param annotator the annotator
     * @param phase     the phase
     * @return the number of use pair judgements
     */
    public int countJudgements(Annotator annotator, Phase phase) {
        return (int) this.usePairJudgementRepository.findByQueryPaged(
                new UsePairJudgementQueryBuilder()
                        .withAnnotator(annotator.getId().getUsername())
                        .withAnnotatorProjectName(annotator.getProject().getId().getName())
                        .withPhase(phase.getId().getName()).build(),
                new PageRequestWraper(1, 0)).getTotalElements();
    }

    /**
     * Count all use pair attempted judgements for a given annotator.
     *
     * @param annotator the annotator
     * @param phase  Phase
     * @return the number of use rank judgements
     */
    public int countAttemptedJudgements(final Phase phase,  Annotator annotator) {
        String phaseName = phase.getId().getName();
        String ownerName = phase.getProject().getOwner().getDisplayname();
        String projectName = phase.getProject().getId().getName();
        String annotatorName = annotator.getId().getUsername();
        return (int) this.usePairJudgementRepository.findByQueryPaged(
                new UsePairJudgementQueryBuilder()
                        .withProject(projectName)
                        .withPhase(phaseName)
                        .withOwner(ownerName)
                        .withAnnotator(annotatorName)
                        .build(),
                new PageRequestWraper(1, 0)).getTotalElements();
    }



    // Setter Files

    /**
     * Import use pair judgements from a CSV file.
     * 
     * @param file  the CSV file
     * @param phase the phase
     */
    @Transactional
    public void save(final Phase phase, final Annotator annotator, final MultipartFile file) {

        validateCsvFile(file);

        parseCsvFile(file).forEach(csvrecord -> {
            final UsePairJudgement resultData = parseRecordToUsePairJudgement(phase, annotator, csvrecord);
            this.usePairJudgementRepository.save(resultData);
        });

    }

    /**
     * Edit a use pair judgement.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * @param command   the command
     */
    @Transactional
    public void edit(final Phase phase, final Annotator annotator, final EditUsePairJudgementCommand command) {
        final Query query = new UsePairJudgementQueryBuilder()
                .withOwner(command.getOwner())
                .withProject(command.getProject())
                .withPhase(command.getPhase())
                .withAnnotator(command.getAnnotator())
                .withInstanceid(command.getInstance())
                .withUUID(command.getUUID())
                .build();
        final List<UsePairJudgement> usePairJudgements = this.usePairJudgementRepository.findByQuery(query);
        if (usePairJudgements.size() != 1) {
            throw new UsePairJudgementNotFoundException();

        }
        final UsePairJudgement judgement = usePairJudgements.get(0);

        if (!annotator.equals(judgement.getAnnotator())) {
            throw new AccessDenidedException();
        }

        if (!command.getLabel().isBlank()) {
            if (!(judgement.getUsePairInstance().getLabelSet().contains(command.getLabel())
                    || judgement.getUsePairInstance().getNonLabel().equals(command.getLabel()))) {
                throw new UsePairJudgementException("Label not found");
            }
            judgement.setLabel(command.getLabel());
        }

        judgement.setComment(command.getComment());
        this.usePairJudgementRepository.save(judgement);
    }


    /**
     * Delete a use pair judgement.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * @param command   the command
     */
    @Transactional
    public void delete(final Phase phase, final Annotator annotator, final DeleteUsePairJudgementCommand command) {
        final Query query = new UsePairJudgementQueryBuilder()
                .withOwner(command.getOwner())
                .withProject(command.getProject())
                .withPhase(command.getPhase())
                .withAnnotator(command.getAnnotator())
                .withInstanceid(command.getInstance())
                .withUUID(command.getUUID())
                .build();
        final List<UsePairJudgement> usePairJudgements = this.usePairJudgementRepository.findByQuery(query);

        /**
        if (usePairJudgements.size() != 1) {
            throw new UsePairJudgementNotFoundException();

        }
        final UsePairJudgement judgement = usePairJudgements.get(0);
         */
        final UsePairJudgement judgement = usePairJudgements.get(0);
        if (!annotator.equals(judgement.getAnnotator())) {
            throw new AccessDenidedException();
        }


        this.usePairJudgementRepository.delete(judgement);
    }

    // Setter Command

    /**
     * Add a use pair judgement.
     * 
     * @param phase     the phase to which the use pair judgement belongs
     * @param annotator the annotator who created the use pair judgement
     * @param command   the command
     */
    @Transactional
    public void annotate(final Phase phase, final Annotator annotator, final AddUsePairJudgementCommand command) {
        String instanceId = command.getInstance();

        final UsePairInstance instance = this.findCorrespondingInstanceData(phase, instanceId);
        validateAddUsePairJudgementCommand(instance, command);

        final UsePairJudgement resultData = new UsePairJudgement(instance, annotator, command.getLabel(),
                command.getComment());
        this.usePairJudgementRepository.save(resultData);

        // Update annotation count for the project
        this.userStatisticApplicationService.incrementAnnotationCountProject(phase.getProject());
        this.annotatorStatisticApplicationService.updateAnnotationStatistic(annotator, phase);
        this.phaseStatisticApplicationService.updatePhaseStatisticForAnnotation(annotator, phase);
    }

    /**
     * Add a use pair tutorial judgement.
     *
     * @param phase     the phase to which the use pair judgement belongs
     * @param annotator the annotator who created the use pair judgement
     * @param command   the command
     */
    @Transactional
    public void annotateTutorial(final Phase phase, final Annotator annotator, final AddUsePairJudgementCommand command) {
        String instanceId = command.getInstance();

        final UsePairInstance instance = this.findCorrespondingInstanceData(phase, instanceId);
       // validateAddUsePairJudgementCommand(instance, command);

        final UsePairTutorialJudgement resultData = new UsePairTutorialJudgement(instance, annotator, command.getLabel(),
                command.getComment());
        this.usePairTutorialJudgementRepository.save(resultData);
    }


    /**
     * Add bulk use pair judgement for a given phase.
     * If the phase is a tutorial phase, the judgements are checked against the
     * tutorial judgements and if they are correct, the tutorial phase is marked as
     * completed.
     * 
     * @param phase     the phase to which the use pair judgement belongs
     * @param annotator the annotator who created the use pair judgement
     * @param commands  the commands
     */
    @Transactional
    public void annotateBulk(final Phase phase, final Annotator annotator,
            final List<AddUsePairJudgementCommand> commands) {
        if (phase.isTutorial()) {
            tutorialAnnotationCorrectness(phase, annotator, commands);
           commands.forEach(command -> {
                this.annotateTutorial(phase, annotator, command);
            });
            return;
        }

        commands.forEach(command -> {
            this.annotate(phase, annotator, command);
        });
    }

    // Helper

    private UsePairInstance findCorrespondingInstanceData(final Phase phase, final String instanceid) {
        final Query query = new UsePairInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withInstanceid(instanceid).build();
        List<UsePairInstance> resultData = this.usePairInstanceRepository.findByQuery(query);
        if (resultData.size() == 1) {
            return resultData.get(0);
        } else {
            throw new CsvParseException(
                    "Instance ID " + instanceid + " not found or ambiguous. Please check your CSV file");
        }
    }

    /**
     * Helper function validating the correctness of the tutorial judgements.
     * 
     * This validation is done by comparing the judgements in the tutorial phase
     * with the judgements of the annotator. If the judgements are correct, the
     * tutorial phase is marked as completed.
     * 
     * NOTE: This is a temporary solution, as there are more sophisticated ways to
     * validate if the tutorial was completed.
     * 
     * @param phase     the phase to which the use pair judgement belongs
     * @param annotator the annotator who created the use pair judgement
     * @param commands  the commands containing the judgements
     */
    @Transactional
    public void tutorialAnnotationCorrectness(final Phase phase, final Annotator annotator,
            List<AddUsePairJudgementCommand> commands) {
        final Query judgementQuery = new UsePairJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName()).build();

        final List<UsePairJudgement> golds = this.usePairJudgementRepository.findByQuery(judgementQuery);

        if (commands.size() != golds.size()) {
            throw new TutorialException(
                    "Tutorial not completed. Please check your judgements. Not all instances were judged");
        }

        // Create two lists of the labels of the gold and the annotator
        List<String> goldLabels = golds.stream().map(UsePairJudgement::getLabel).toList();
        List<String> annotatorLabels = commands.stream().map(AddUsePairJudgementCommand::getLabel).toList();
        List<List<String>> annotatorLabelList = Arrays.asList(goldLabels, annotatorLabels);
        List<String> categories = golds.get(0).getInstance().getLabelSet();

        // Check if the tutorial phase has a statistic annotation measure 
        if (phase.getStatisticAnnotationMeasure() == null || phase.getStatisticAnnotationMeasureThreshold() == null
                || StatisticAnnotationMeasureEnum.fromId(phase.getStatisticAnnotationMeasure().getId()) == null) {
            throw new TutorialException(
                    "This tutorial is not valid anymore. Please contact the project owner for a new tutorial.");
        }

        // Calculate the annotator agreement
        double agreement = this.commonMathService.calculateAnnotatorAgreement(categories,
                StatisticAnnotationMeasureEnum.fromId(phase.getStatisticAnnotationMeasure().getId()),
                annotatorLabelList);

        // Finally, mark the tutorial phase as completed for the annotator
        // As the object annotator is managed by the persistence context, we do not need
        // to
        // call the annotator repository and can simply update the object, HOPEFULLY?!?
        if (agreement >= phase.getStatisticAnnotationMeasureThreshold()) {
            annotator.addCompletedTutorial(phase);
        }

        tutorialAnnotationMeasureHistoryRepository.save(
                new TutorialAnnotationMeasureHistory(
                        phase,
                        annotator,
                        agreement,
                        agreement >= phase.getStatisticAnnotationMeasureThreshold()));
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

    private List<List<String>> parseUsePairJudgementsToCsvBody(List<UsePairJudgement> usePairJudgements) {
        List<List<String>> csvBody = new ArrayList<>();

        for (UsePairJudgement data : usePairJudgements) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(data.getInstance().getId().getInstanceid());
            csvRow.add(data.getLabel());
            csvRow.add(data.getComment());
            csvRow.add(data.getAnnotator().getId().getUsername());

            csvBody.add(csvRow);
        }

        return csvBody;
    }
    private List<List<String>> parseUsePairTutorialJudgementsToCsvBody(List<UsePairTutorialJudgement> usePairTutorialJudgements) {
        List<List<String>> csvBody = new ArrayList<>();

        for (UsePairTutorialJudgement data : usePairTutorialJudgements) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(data.getInstance().getId().getInstanceid());
            csvRow.add(data.getLabel());
            csvRow.add(data.getComment());
            csvRow.add(data.getAnnotator().getId().getUsername());

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

    private UsePairJudgement parseRecordToUsePairJudgement(final Phase phase, final Annotator annotator,
            final CSVRecord csvrecord) {
        final String instanceId;
        final String label;
        final String comment;

        try {

            instanceId = csvrecord.get("instanceID");

            label = csvrecord.get("label");
            comment = csvrecord.get("comment");

        } catch (Exception e) {
            throw new CsvParseException("CSV record is not valid, please check the format");
        }

        UsePairInstance instanceData = findCorrespondingInstanceData(phase, instanceId);
        return new UsePairJudgement(instanceData, annotator, label, comment);
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

    private void validateAddUsePairJudgementCommand(final UsePairInstance instance,
            final AddUsePairJudgementCommand command) {

        if (!(instance.getLabelSet().contains(command.getLabel())
                || instance.getNonLabel().equals(command.getLabel()))) {
            throw new IllegalArgumentException("Not in label set, therefore not valid judgement");
        }
    }

}
