package de.garrafao.phitag.application.judgement.sentimentandchoice;

import de.garrafao.phitag.application.common.CommonMathService;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.AddSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.DeleteSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.EditSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.statistics.annotatostatistic.AnnotatorStatisticApplicationService;
import de.garrafao.phitag.application.statistics.phasestatistic.PhaseStatisticApplicationService;
import de.garrafao.phitag.application.statistics.userstatistic.UserStatisticApplicationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.authentication.error.AccessDenidedException;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.error.CsvParseException;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstance;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentInstanceAndChoiceRepository;
import de.garrafao.phitag.domain.instance.sentimentandchoice.query.SentimentAndChoiceInstanceQueryBuilder;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgement;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgementRepository;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.page.SentimentAndChoiceJudgementPageBuilder;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.query.SentimentAndChoiceJudgementQueryBuilder;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.error.TutorialException;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasureEnum;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistory;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistoryRepository;
import de.garrafao.phitag.domain.tutorial.sentimentandchoice.SentimentAndChoiceTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.sentimentandchoice.SentimentAndChoiceTutorialJudgementRepository;
import de.garrafao.phitag.domain.tutorial.sentimentandchoice.query.SentimentAndChoiceTutorialJudgementQueryBuilder;
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
public class SentimentAndChoiceJudgementApplicationService {

    private final SentimentAndChoiceJudgementRepository sentimentAndChoiceJudgementRepository;
    private final SentimentAndChoiceTutorialJudgementRepository sentimentAndChoiceTutorialJudgementRepository;
    private final SentimentInstanceAndChoiceRepository sentimentInstanceAndChoiceRepository;
    private final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository;

    // Statistics

    private final UserStatisticApplicationService userStatisticApplicationService;

    private final AnnotatorStatisticApplicationService annotatorStatisticApplicationService;

    private final PhaseStatisticApplicationService phaseStatisticApplicationService;

    // Math

    private final CommonMathService commonMathService;

    @Autowired
    public SentimentAndChoiceJudgementApplicationService(
            final SentimentAndChoiceJudgementRepository sentimentAndChoiceJudgementRepository,
            final SentimentAndChoiceTutorialJudgementRepository sentimentAndChoiceTutorialJudgementRepository,
            final SentimentInstanceAndChoiceRepository sentimentInstanceAndChoiceRepository,
            final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository,

            final UserStatisticApplicationService userStatisticApplicationService,
            final AnnotatorStatisticApplicationService annotatorStatisticApplicationService,
            final PhaseStatisticApplicationService phaseStatisticApplicationService,

            final CommonMathService commonMathService) {
        this.sentimentAndChoiceJudgementRepository = sentimentAndChoiceJudgementRepository;
        this.sentimentAndChoiceTutorialJudgementRepository = sentimentAndChoiceTutorialJudgementRepository;
        this.sentimentInstanceAndChoiceRepository = sentimentInstanceAndChoiceRepository;
        this.tutorialAnnotationMeasureHistoryRepository = tutorialAnnotationMeasureHistoryRepository;

        this.userStatisticApplicationService = userStatisticApplicationService;
        this.annotatorStatisticApplicationService = annotatorStatisticApplicationService;
        this.phaseStatisticApplicationService = phaseStatisticApplicationService;

        this.commonMathService = commonMathService;
    }

    // Getter

    /**
     * Get all judgements for a specific phase
     * 
     * @param phase
     * @return List of judgements
     */
    public List<SentimentAndChoiceJudgement> findByPhase(final Phase phase) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();

        return this.sentimentAndChoiceJudgementRepository.findByQuery(query);
    }

    /**
     * Get all judgements for a specific phase and annotator
     *
     * @param phase
     * @return List of judgements
     */
    public List<SentimentAndChoiceJudgement> findByPhaseAndAnnotator(final Phase phase, final Annotator annotator) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withAnnotator(annotator.getId().getUsername())
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();

        return this.sentimentAndChoiceJudgementRepository.findByQuery(query);
    }


    /**
     * Get all judgements for a specific phase
     *
     * @param phase
     * @return List of judgements
     */
    public List<SentimentAndChoiceTutorialJudgement> findTutorialByPhase(final Phase phase) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();

        return this.sentimentAndChoiceTutorialJudgementRepository.findByQuery(query);
    }

    /**
     * Get all judgements for a specific phase as paged list
     * 
     * @param phase
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<SentimentAndChoiceJudgement> findByPhase(
            final Phase phase,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.sentimentAndChoiceJudgementRepository.findByQueryPaged(query, new SentimentAndChoiceJudgementPageBuilder()
                .withPageSize(pagesize)
                .withPageNumber(pagenumber)
                .withOrderBy(orderBy)
                .build());
    }

    /**
     * Get all judgements for a specific phase and annotator
     * 
     * @param phase
     * @param annotator
     * @return List of judgements
     */
    public List<SentimentAndChoiceJudgement> getHistory(final Phase phase, final Annotator annotator) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();

        return this.sentimentAndChoiceJudgementRepository.findByQuery(query);
    }

    /**
     * Get all judgements for a specific phase and annotator as paged list
     * 
     * @param phase
     * @param annotator
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<SentimentAndChoiceJudgement> getHistory(
            final Phase phase,
            final Annotator annotator,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();

        return this.sentimentAndChoiceJudgementRepository.findByQueryPaged(query,
                new PageRequestWraper(pagesize, pagenumber, orderBy));
    }

    /**
     * Get all tutorial judgements for a specific phase and annotator as paged list
     *
     * @param phase
     * @param annotator
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<SentimentAndChoiceTutorialJudgement> getTutorialHistory(
            final Phase phase,
            final Annotator annotator,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new SentimentAndChoiceTutorialJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();

        return this.sentimentAndChoiceTutorialJudgementRepository.findByQueryPaged(query,
                new PageRequestWraper(pagesize, pagenumber, orderBy));
    }

    /**
     * Export all use pair judgements for a given phase.
     * 
     * @param phase the phase
     * @return a CSV file as {@link InputStreamResource}
     */
    public InputStreamResource exportJudgement(final Phase phase) {
        List<SentimentAndChoiceJudgement> resultData = this.findByPhase(phase);
        String[] csvHeader = {
                "instanceID", "label", "comment", "annotator"
        };
        List<List<String>> csvData = parseJudgementsToCsvBody(resultData);

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
     * Export all use pair judgements for a given phase.
     *
     * @param phase the phase
     * @return a CSV file as {@link InputStreamResource}
     */
    public InputStreamResource exportTutorialJudgement(final Phase phase) {
        List<SentimentAndChoiceTutorialJudgement> resultData = this.findTutorialByPhase(phase);
        String[] csvHeader = {
                "instanceID", "label", "comment", "annotator"
        };
        List<List<String>> csvData = parseTutorialJudgementsToCsvBody(resultData);

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
            final SentimentAndChoiceJudgement resultData = parseRecordToJudgement(phase, annotator, csvrecord);
            this.sentimentAndChoiceJudgementRepository.save(resultData);
        });

    }

    /**
     * Edit  judgement.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * @param command   the command
     */
    @Transactional
    public void edit(final Phase phase, final Annotator annotator, final EditSentimentAndChoiceJudgementCommand command) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner(command.getOwner())
                .withProject(command.getProject())
                .withPhase(command.getPhase())
                .withAnnotator(command.getAnnotator())
                .withInstanceid(command.getInstance())
                .withUUID(command.getUUID())
                .build();
        final List<SentimentAndChoiceJudgement> sentimentAndChoiceJudgements = this.sentimentAndChoiceJudgementRepository.findByQuery(query);


        //  if (sentimentAndChoiceJudgements.size() != 1) {
        //    throw new SentimentAndJudgementException("Judgement not found");
        //}

        final SentimentAndChoiceJudgement judgement = sentimentAndChoiceJudgements.get(0);

        if (!annotator.equals(judgement.getAnnotator())) {
            throw new AccessDenidedException();
        }

        if (!command.getLabel().isBlank()) {
            judgement.setLabel(command.getLabel());
        }

        judgement.setComment(command.getComment());
        this.sentimentAndChoiceJudgementRepository.save(judgement);
    }

    /**
     * Delete  judgement.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * @param command   the command
     */
    @Transactional
    public void delete(final Phase phase, final Annotator annotator, final DeleteSentimentAndChoiceJudgementCommand command) {
        final Query query = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner(command.getOwner())
                .withProject(command.getProject())
                .withPhase(command.getPhase())
                .withAnnotator(command.getAnnotator())
                .withInstanceid(command.getInstance())
                .withUUID(command.getUUID())
                .build();
        final List<SentimentAndChoiceJudgement> sentimentAndChoiceJudgements = this.sentimentAndChoiceJudgementRepository.findByQuery(query);
      //  if (sentimentAndChoiceJudgements.size() != 1) {
       //     throw new SentimentAndJudgementException("Judgement not found");

       // }
        final SentimentAndChoiceJudgement judgement = sentimentAndChoiceJudgements.get(0);

        if (!annotator.equals(judgement.getAnnotator())) {
            throw new AccessDenidedException();
        }

        this.sentimentAndChoiceJudgementRepository.delete(judgement);
    }

    // Setter Command

    /**
     * Add a sentiment judgement.
     * 
     * @param phase     the phase to which the use pair judgement belongs
     * @param annotator the annotator who created the use pair judgement
     * @param command   the command
     */
    @Transactional
    public void annotate(final Phase phase, final Annotator annotator, final AddSentimentAndChoiceJudgementCommand command) {
        String instanceId = command.getInstance();

        final SentimentAndChoiceInstance instance = this.findCorrespondingInstanceData(phase, instanceId);
        //validateAddSentimentAndChoiceJudgementCommand(instance, command);

        final SentimentAndChoiceJudgement resultData = new SentimentAndChoiceJudgement(instance, annotator, command.getLabel(),
                command.getComment());
        this.sentimentAndChoiceJudgementRepository.save(resultData);

        // Update annotation count for the project
        this.userStatisticApplicationService.incrementAnnotationCountProject(phase.getProject());
        this.annotatorStatisticApplicationService.updateAnnotationStatistic(annotator, phase);
        this.phaseStatisticApplicationService.updatePhaseStatisticForAnnotation(annotator, phase);
    }


    /**
     * Add a sentiment tutorial  judgement.
     *
     * @param phase     the phase to which the use pair judgement belongs
     * @param annotator the annotator who created the use pair judgement
     * @param command   the command
     */
    @Transactional
    public void annotateTutorial(final Phase phase, final Annotator annotator, final AddSentimentAndChoiceJudgementCommand command) {
        String instanceId = command.getInstance();

        final SentimentAndChoiceInstance instance = this.findCorrespondingInstanceData(phase, instanceId);

        final SentimentAndChoiceTutorialJudgement resultData = new SentimentAndChoiceTutorialJudgement(instance, annotator, command.getLabel(),
                command.getComment());
        this.sentimentAndChoiceTutorialJudgementRepository.save(resultData);

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
            final List<AddSentimentAndChoiceJudgementCommand> commands) {
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

    private SentimentAndChoiceInstance findCorrespondingInstanceData(final Phase phase, final String instanceid) {
        final Query query = new SentimentAndChoiceInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withInstanceid(instanceid).build();
        List<SentimentAndChoiceInstance> resultData = this.sentimentInstanceAndChoiceRepository.findByQuery(query);
        if (resultData.size() == 1) {
            return resultData.get(0);
        } else {
            throw new CsvParseException(
                    "Instance ID " + instanceid + " not found or ambiguous. Please check your CSV file");
        }
    }

    /**
     * Count all lexsub attempted judgements for a given annotator.
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
        return (int) this.sentimentAndChoiceJudgementRepository.findByQueryPaged(
                new SentimentAndChoiceJudgementQueryBuilder()
                        .withProject(projectName)
                        .withPhase(phaseName)
                        .withOwner(ownerName)
                        .withAnnotator(annotatorName)
                        .build(),
                new PageRequestWraper(1, 0)).getTotalElements();
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
            List<AddSentimentAndChoiceJudgementCommand> commands) {
        final Query judgementQuery = new SentimentAndChoiceJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName()).build();

        final List<SentimentAndChoiceJudgement> golds = this.sentimentAndChoiceJudgementRepository.findByQuery(judgementQuery);

        if (commands.size() != golds.size()) {
            throw new TutorialException(
                    "Tutorial not completed. Please check your judgements. Not all instances were judged");
        }

        // Create two lists of the labels of the gold and the annotator
        List<String> goldLabels = golds.stream().map(SentimentAndChoiceJudgement::getLabel).toList();
        List<String> annotatorLabels = commands.stream().map(AddSentimentAndChoiceJudgementCommand::getLabel).toList();

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

    private List<List<String>> parseJudgementsToCsvBody(List<SentimentAndChoiceJudgement> judgements) {
        List<List<String>> csvBody = new ArrayList<>();

        for (SentimentAndChoiceJudgement judgement : judgements) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(judgement.getInstance().getId().getInstanceid());
            csvRow.add(judgement.getLabel());
            csvRow.add(judgement.getComment());
            csvRow.add(judgement.getAnnotator().getId().getUsername());

            csvBody.add(csvRow);
        }

        return csvBody;
    }

    private List<List<String>> parseTutorialJudgementsToCsvBody(List<SentimentAndChoiceTutorialJudgement> judgements) {
        List<List<String>> csvBody = new ArrayList<>();

        for (SentimentAndChoiceTutorialJudgement judgement : judgements) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(judgement.getInstance().getId().getInstanceid());
            csvRow.add(judgement.getLabel());
            csvRow.add(judgement.getComment());
            csvRow.add(judgement.getAnnotator().getId().getUsername());

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

    private SentimentAndChoiceJudgement parseRecordToJudgement(final Phase phase, final Annotator annotator,
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

        SentimentAndChoiceInstance instanceData = findCorrespondingInstanceData(phase, instanceId);
        return new SentimentAndChoiceJudgement(instanceData, annotator, label, comment);
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

    private void validateAddSentimentAndChoiceJudgementCommand(final SentimentAndChoiceInstance instance,
                                                    final AddSentimentAndChoiceJudgementCommand command) {
        if (!(instance.getLabelSet().contains(command.getLabel())
                || instance.getNonLabel().equals(command.getLabel()))) {
            throw new IllegalArgumentException("Not in label set, therefore not valid judgement");
        }
    }

}
