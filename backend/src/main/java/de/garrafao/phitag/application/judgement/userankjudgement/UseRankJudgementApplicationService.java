package de.garrafao.phitag.application.judgement.userankjudgement;

import de.garrafao.phitag.application.common.CommonMathService;
import de.garrafao.phitag.application.judgement.userankjudgement.data.AddUseRankJudgementCommand;
import de.garrafao.phitag.application.judgement.userankjudgement.data.DeleteUseRankJudgementCommand;
import de.garrafao.phitag.application.judgement.userankjudgement.data.EditUseRankJudgementCommand;
import de.garrafao.phitag.application.statistics.annotatostatistic.AnnotatorStatisticApplicationService;
import de.garrafao.phitag.application.statistics.phasestatistic.PhaseStatisticApplicationService;
import de.garrafao.phitag.application.statistics.userstatistic.UserStatisticApplicationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.authentication.error.AccessDenidedException;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.error.CsvParseException;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankInstance;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankRepository;
import de.garrafao.phitag.domain.instance.userankinstance.query.UseRankInstanceQueryBuilder;
import de.garrafao.phitag.domain.judgement.userankjudgement.UseRankJudgement;
import de.garrafao.phitag.domain.judgement.userankjudgement.UseRankJudgementRepository;
import de.garrafao.phitag.domain.judgement.userankjudgement.error.UseRankJudgementNotFoundException;
import de.garrafao.phitag.domain.judgement.userankjudgement.page.UseRankJudgementPageBuilder;
import de.garrafao.phitag.domain.judgement.userankjudgement.query.UseRankJudgementQueryBuilder;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.error.TutorialException;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasureEnum;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistory;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistoryRepository;
import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgementRepository;
import de.garrafao.phitag.domain.tutorial.userank.page.UseRankTutorialJudgementPageBuilder;
import de.garrafao.phitag.domain.tutorial.userank.query.UseRankTutorialJudgementQueryBuilder;
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
public class UseRankJudgementApplicationService {

    private final UseRankJudgementRepository useRankJudgementRepository;

    private final UseRankTutorialJudgementRepository useRankTutorialJudgementRepository;

    private final UseRankRepository useRankRepository;

    private final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository;

    // Statistics

    private final UserStatisticApplicationService userStatisticApplicationService;

    private final AnnotatorStatisticApplicationService annotatorStatisticApplicationService;

    private final PhaseStatisticApplicationService phaseStatisticApplicationService;

    private final CommonMathService commonMathService;

    @Autowired
    public UseRankJudgementApplicationService(
            final UseRankJudgementRepository useRankJudgementRepository,
            final UseRankTutorialJudgementRepository useRankTutorialJudgementRepository,
            final UseRankRepository useRankRepository,
            final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository,

            final UserStatisticApplicationService userStatisticApplicationService,
            final AnnotatorStatisticApplicationService annotatorStatisticApplicationService,
            final PhaseStatisticApplicationService phaseStatisticApplicationService,
            final CommonMathService commonMathService) {
        this.useRankJudgementRepository = useRankJudgementRepository;
        this.useRankTutorialJudgementRepository = useRankTutorialJudgementRepository;
        this.useRankRepository = useRankRepository;

        this.tutorialAnnotationMeasureHistoryRepository = tutorialAnnotationMeasureHistoryRepository;

        this.userStatisticApplicationService = userStatisticApplicationService;
        this.annotatorStatisticApplicationService = annotatorStatisticApplicationService;
        this.phaseStatisticApplicationService = phaseStatisticApplicationService;
        this.commonMathService = commonMathService;
    }

    // Getter

    /**
     * Get all use rank tutorial judgements for a given phase.
     * 
     * @param phase the phase
     * @return a {@link UserRankTutorialJudgement} list
     */
    public List<UseRankTutorialJudgement> findTutorialByPhase(final Phase phase) {
        final Query query = new UseRankJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.useRankTutorialJudgementRepository.findByQuery(query);
    }

    /**
     * Get all use rank judgements for a given phase.
     *
     * @param phase the phase
     * @return a {@link UserRankJudgement} list
     */
    public List<UseRankJudgement> findByPhase(final Phase phase) {
        final Query query = new UseRankJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.useRankJudgementRepository.findByQuery(query);
    }

    /**
     * Get all use rank judgements for a given phase as a paged list.
     * 
     * @param phase
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<UseRankJudgement> findByPhase(
            final Phase phase,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new UseRankJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.useRankJudgementRepository.findByQueryPaged(query, new UseRankJudgementPageBuilder()
                .withPageSize(pagesize)
                .withPageNumber(pagenumber)
                .withOrderBy(orderBy)
                .build());
    }

    /**
     * Get all use rank judgements for a given annotator.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * @return a {@link UseRankJudgement} list
     */
    public List<UseRankJudgement> getHistory(final Phase phase, final Annotator annotator) {
        final Query query = new UseRankJudgementQueryBuilder().withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName()).withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername()).build();
        return this.useRankJudgementRepository.findByQuery(query);
    }

    /**
     * Get all use rank judgements for a given annotator as a paged list.
     * 
     * @param phase
     * @param annotator
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<UseRankJudgement> getHistory(
            final Phase phase,
            final Annotator annotator,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new UseRankJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();
        return this.useRankJudgementRepository.findByQueryPaged(query, new UseRankJudgementPageBuilder()
                .withPageSize(pagesize)
                .withPageNumber(pagenumber)
                .withOrderBy(orderBy)
                .build());
    }


    /**
     * Get all use rank  tutorial judgements for a given annotator as a paged list.
     *
     * @param phase
     * @param annotator
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */
    public Page<UseRankTutorialJudgement> getTutorialHistory(
            final Phase phase,
            final Annotator annotator,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new UseRankTutorialJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();
        return this.useRankTutorialJudgementRepository.findByQueryPaged(query, new UseRankTutorialJudgementPageBuilder()
                .withPageSize(pagesize)
                .withPageNumber(pagenumber)
                .withOrderBy(orderBy)
                .build());
    }


    /**
     * Export all use rank judgements for a given phase.
     * 
     * @param phase the phase
     * @return a CSV file as {@link InputStreamResource}
     */
    public InputStreamResource exportUseRankJudgement(final Phase phase) {
        List<UseRankJudgement> resultData = this.findByPhase(phase);
        String[] csvHeader = {
                "instanceID", "rank", "comment", "annotator"
        };
        List<List<String>> csvData = parseUseRankJudgementsToCsvBody(resultData);

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
     * Export all use rank judgements for a given phase.
     *
     * @param phase the phase
     * @return a CSV file as {@link InputStreamResource}
     */
    public InputStreamResource exportUseRankTutorialJudgement(final Phase phase) {
        List<UseRankTutorialJudgement> resultData = this.findTutorialByPhase(phase);
        String[] csvHeader = {
                "instanceID", "rank", "comment", "annotator"
        };
        List<List<String>> csvData = parseUseRankTutorialJudgementsToCsvBody(resultData);

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
     * Count all use rank judgements for a given annotator.
     * 
     * @param annotator the annotator
     * @return the number of use rank judgements
     */
    public int countAttemptedJudgements(final Phase phase,  Annotator annotator) {
        String phaseName = phase.getId().getName();
        String ownerName = phase.getProject().getOwner().getDisplayname();
        String projectName = phase.getProject().getId().getName();
        String annotatorName = annotator.getId().getUsername();
        return (int) this.useRankJudgementRepository.findByQueryPaged(
                new UseRankJudgementQueryBuilder()
                        .withProject(projectName)
                        .withPhase(phaseName)
                        .withOwner(ownerName)
                        .withAnnotator(annotatorName)
                        .build(),
                new PageRequestWraper(1, 0)).getTotalElements();
    }








    /**
     * Import use rank judgements from a CSV file.
     * 
     * @param file  the CSV file
     * @param phase the phase
     */
    @Transactional
    public void save(final Phase phase, final Annotator annotator, final MultipartFile file) {
        validateCsvFile(file);

        parseCsvFile(file).forEach(csvrecord -> {
            final UseRankJudgement resultData = parseRecordToUseRankJudgement(phase, annotator, csvrecord);
            this.useRankJudgementRepository.save(resultData);
        });

    }

    /**
     * Edit a use rank judgement.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * @param command   the command
     */
    @Transactional
    public void edit(final Phase phase, final Annotator annotator, final EditUseRankJudgementCommand command) {
        final Query query = new UseRankJudgementQueryBuilder()
                .withOwner(command.getOwner())
                .withProject(command.getProject())
                .withPhase(command.getPhase())
                .withAnnotator(command.getAnnotator())
                .withInstanceid(command.getInstance())
                .withUUID(command.getUUID())
                .build();
        final List<UseRankJudgement> useRankJudgements = this.useRankJudgementRepository.findByQuery(query);
        if (useRankJudgements.size() != 1) {
            throw new UseRankJudgementNotFoundException();

        }
        final UseRankJudgement judgement = useRankJudgements.get(0);

        if (!annotator.equals(judgement.getAnnotator())) {
            throw new AccessDenidedException();
        }

        if (!command.getLabel().isBlank()) {

            judgement.setLabel(command.getLabel());
        }

        judgement.setComment(command.getComment());
        this.useRankJudgementRepository.save(judgement);
    }

    /**
     * Delete a use rank judgement.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * @param command   the command
     */
    @Transactional
    public void delete(final Phase phase, final Annotator annotator, final DeleteUseRankJudgementCommand command) {
        final Query query = new UseRankJudgementQueryBuilder()
                .withOwner(command.getOwner())
                .withProject(command.getProject())
                .withPhase(command.getPhase())
                .withAnnotator(command.getAnnotator())
                .withInstanceid(command.getInstance())
                .withUUID(command.getUUID())
                .build();
        final List<UseRankJudgement> useRankJudgements = this.useRankJudgementRepository.findByQuery(query);
        if (useRankJudgements.size() != 1) {
            throw new UseRankJudgementNotFoundException();

        }
        final UseRankJudgement judgement = useRankJudgements.get(0);

        if (!annotator.equals(judgement.getAnnotator())) {
            throw new AccessDenidedException();
        }

        this.useRankJudgementRepository.delete(judgement);
    }

    // Setter Command

    /**
     * Add a use rank judgement.
     * 
     * @param phase     the phase to which the use rank judgement belongs
     * @param annotator the annotator who created the use rank judgement
     * @param command   the command
     */
    @Transactional
    public void annotate(final Phase phase, final Annotator annotator, final AddUseRankJudgementCommand command) {
        String instanceId = command.getInstance();

        final UseRankInstance instance = this.findCorrespondingInstanceData(phase, instanceId);
       // validateAddUseRankJudgementCommand(instance, command);

        final UseRankJudgement resultData = new UseRankJudgement(instance, annotator, command.getLabel(),
                command.getComment());
        this.useRankJudgementRepository.save(resultData);

        // Update annotation count for the project
        this.userStatisticApplicationService.incrementAnnotationCountProject(phase.getProject());
        this.annotatorStatisticApplicationService.updateAnnotationStatistic(annotator, phase);
        this.phaseStatisticApplicationService.updatePhaseStatisticForAnnotation(annotator, phase);
    }

    /**
     * Add a use rank tutorial judgement.
     *
     * @param phase     the phase to which the use pair judgement belongs
     * @param annotator the annotator who created the use pair judgement
     * @param command   the command
     */
    @Transactional
    public void annotateTutorial(final Phase phase, final Annotator annotator, final AddUseRankJudgementCommand command) {
        String instanceId = command.getInstance();

        final UseRankInstance instance = this.findCorrespondingInstanceData(phase, instanceId);
      //  validateAddUseRankJudgementCommand(instance, command);

        final UseRankTutorialJudgement resultData = new UseRankTutorialJudgement(instance, annotator, command.getLabel(),
                command.getComment());
        this.useRankTutorialJudgementRepository.save(resultData);
    }

    /**
     * Add bulk use rank judgement for a given phase.
     * If the phase is a tutorial phase, the judgements are checked against the
     * tutorial judgements and if they are correct, the tutorial phase is marked as
     * completed.
     * 
     * @param phase     the phase to which the use rank judgement belongs
     * @param annotator the annotator who created the use rank judgement
     * @param commands  the commands
     */
    @Transactional
    public void annotateBulk(final Phase phase, final Annotator annotator,
            final List<AddUseRankJudgementCommand> commands) {
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

    private UseRankInstance findCorrespondingInstanceData(final Phase phase, final String instanceid) {
        final Query query = new UseRankInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withInstanceid(instanceid).build();
        List<UseRankInstance> resultData = this.useRankRepository.findByQuery(query);
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
     * @param phase     the phase to which the use rank judgement belongs
     * @param annotator the annotator who created the use rank judgement
     * @param commands  the commands containing the judgements
     */
    @Transactional
    private void tutorialAnnotationCorrectness(final Phase phase, final Annotator annotator,
            List<AddUseRankJudgementCommand> commands) {
        final Query judgementQuery = new UseRankJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName()).build();

        final List<UseRankJudgement> golds = this.useRankJudgementRepository.findByQuery(judgementQuery);

        if (commands.size() != golds.size()) {
            throw new TutorialException(
                    "Tutorial not completed. Please check your judgements. Not all instances were judged");
        }

        // Create two lists of the labels of the gold and the annotator
        List<String> goldLabels = golds.stream().map(UseRankJudgement::getLabel).toList();
        List<String> annotatorLabels = commands.stream().map(AddUseRankJudgementCommand::getLabel).toList();

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

    private List<List<String>> parseUseRankJudgementsToCsvBody(List<UseRankJudgement> useRankJudgements) {
        List<List<String>> csvBody = new ArrayList<>();

        for (UseRankJudgement data : useRankJudgements) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(data.getInstance().getId().getInstanceid());
            csvRow.add(data.getLabel());
            csvRow.add(data.getComment());
            csvRow.add(data.getAnnotator().getId().getUsername());

            csvBody.add(csvRow);
        }

        return csvBody;
    }
    private List<List<String>> parseUseRankTutorialJudgementsToCsvBody(List<UseRankTutorialJudgement> judgements) {
        List<List<String>> csvBody = new ArrayList<>();

        for (UseRankTutorialJudgement data : judgements) {
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

    private UseRankJudgement parseRecordToUseRankJudgement(final Phase phase, final Annotator annotator,
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

        UseRankInstance instanceData = findCorrespondingInstanceData(phase, instanceId);
        return new UseRankJudgement(instanceData, annotator, label, comment);
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

    private void validateAddUseRankJudgementCommand(final UseRankInstance instance,
            final AddUseRankJudgementCommand command) {

        if (!(instance.getLabelSet().contains(command.getLabel())
                || instance.getNonLabel().equals(command.getLabel()))) {
            throw new IllegalArgumentException("Not in label set, therefore not valid judgement");
        }
    }

}
