package de.garrafao.phitag.application.judgement.spanjudgement;

import de.garrafao.phitag.application.common.CommonMathService;
import de.garrafao.phitag.application.judgement.spanjudgement.data.AddSpanJudgementCommand;
import de.garrafao.phitag.application.judgement.spanjudgement.data.DeleteSpanJudgementCommand;
import de.garrafao.phitag.application.judgement.spanjudgement.data.EditSpanJudgementCommand;
import de.garrafao.phitag.application.statistics.annotatostatistic.AnnotatorStatisticApplicationService;
import de.garrafao.phitag.application.statistics.phasestatistic.PhaseStatisticApplicationService;
import de.garrafao.phitag.application.statistics.userstatistic.UserStatisticApplicationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.authentication.error.AccessDenidedException;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.error.CsvParseException;
import de.garrafao.phitag.domain.instance.spaninstance.SpanInstance;
import de.garrafao.phitag.domain.instance.spaninstance.SpanInstanceRepository;
import de.garrafao.phitag.domain.instance.spaninstance.query.SpanInstanceQueryBuilder;
import de.garrafao.phitag.domain.judgement.lexsubjudgement.page.LexSubJudgementPageBuilder;
import de.garrafao.phitag.domain.judgement.lexsubjudgement.query.LexSubJudgementQueryBuilder;
import de.garrafao.phitag.domain.judgement.spanjudgement.SpanJudgement;
import de.garrafao.phitag.domain.judgement.spanjudgement.SpanJudgementRepository;
import de.garrafao.phitag.domain.judgement.spanjudgement.error.SpanJudgementException;
import de.garrafao.phitag.domain.judgement.spanjudgement.query.SpanJudgementQueryBuilder;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.error.TutorialException;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasureEnum;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistory;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistoryRepository;
import de.garrafao.phitag.domain.tutorial.lexsub.LexSubTutorialJudgementRepository;
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
public class SpanJudgementApplicationService {

    private final SpanJudgementRepository spanJudgementRepository;


    private final SpanInstanceRepository spanInstanceRepository;

    private final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository;

    // Statistics

    private final UserStatisticApplicationService userStatisticApplicationService;

    private final AnnotatorStatisticApplicationService annotatorStatisticApplicationService;

    private final PhaseStatisticApplicationService phaseStatisticApplicationService;

    // Math

    private final CommonMathService commonMathService;

    @Autowired
    public SpanJudgementApplicationService(
            final LexSubTutorialJudgementRepository lexSubTutorialJudgementRepository,
            final SpanJudgementRepository spanJudgementRepository,
            final SpanInstanceRepository spanInstanceRepository,
            final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository,

            final UserStatisticApplicationService userStatisticApplicationService,
            final AnnotatorStatisticApplicationService annotatorStatisticApplicationService,
            final PhaseStatisticApplicationService phaseStatisticApplicationService,

            final CommonMathService commonMathService) {
        this.spanJudgementRepository = spanJudgementRepository;
        this.spanInstanceRepository = spanInstanceRepository;
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
    public List<SpanJudgement> findByPhase(final Phase phase) {
        final Query query = new SpanJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();

        return this.spanJudgementRepository.findByQuery(query);
    }

    /**
     * Get all judgements for a specific phase and annotator
     *
     * @param phase
     * @return List of judgements
     */
    public List<SpanJudgement> findByPhase(final Phase phase, final Annotator annotator) {
        final Query query = new SpanJudgementQueryBuilder()
                .withAnnotator(annotator.getId().getUsername())
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();

        return this.spanJudgementRepository.findByQuery(query);
    }

    /**
     * Get all judgements for a specific phase
     *
     * @param phase
     * @return List of judgements

    public List<LexSubTutorialJudgement> findTutorialByPhase(final Phase phase) {
        final Query query = new LexSubJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();

        return this.lexSubTutorialJudgementRepository.findByQuery(query);
    }
     */
    /**
     * Get all judgements for a specific phase as paged list
     * 
     * @param phase
     * @param pagesize
     * @param pagenumber
     * @param orderBy
     * @return
     */

    public Page<SpanJudgement> findByPhase(
            final Phase phase,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new SpanJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.spanJudgementRepository.findByQueryPaged(query, new LexSubJudgementPageBuilder()
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
    public List<SpanJudgement> getHistory(final Phase phase, final Annotator annotator) {
        final Query query = new SpanJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();

        return this.spanJudgementRepository.findByQuery(query);
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
    public Page<SpanJudgement> getHistory(
            final Phase phase,
            final Annotator annotator,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new SpanJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();

        return this.spanJudgementRepository.findByQueryPaged(query,
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

    public Page<LexSubTutorialJudgement> getTutorialHistory(
            final Phase phase,
            final Annotator annotator,
            final int pagesize,
            final int pagenumber,
            final String orderBy) {
        final Query query = new SpanJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withAnnotator(annotator.getId().getUsername())
                .build();

        return this.lexSubTutorialJudgementRepository.findByQueryPaged(query,
                new PageRequestWraper(pagesize, pagenumber, orderBy));
    }
     */
    /**
     * Export all use pair judgements for a given phase.
     * 
     * @param phase the phase
     * @return a CSV file as {@link InputStreamResource}
     */
    public InputStreamResource exportJudgement(final Phase phase) {
        List<SpanJudgement> resultData = this.findByPhase(phase);
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

    public InputStreamResource exportTutorialJudgement(final Phase phase) {
        List<LexSubTutorialJudgement> resultData = this.findTutorialByPhase(phase);
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
    }   */

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
            final SpanJudgement resultData = parseRecordToJudgement(phase, annotator, csvrecord);
            this.spanJudgementRepository.save(resultData);
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
    public void edit(final Phase phase, final Annotator annotator, final EditSpanJudgementCommand command) {
        final Query query = new SpanJudgementQueryBuilder()
                .withOwner(command.getOwner())
                .withProject(command.getProject())
                .withPhase(command.getPhase())
                .withAnnotator(command.getAnnotator())
                .withInstanceid(command.getInstance())
                .withUUID(command.getUUID())
                .build();
        final List<SpanJudgement> spanJudgements = this.spanJudgementRepository.findByQuery(query);
        if (spanJudgements.size() != 1) {
            throw new SpanJudgementException("Judgement not found");

        }
        final SpanJudgement judgement = spanJudgements.get(0);

        if (!annotator.equals(judgement.getAnnotator())) {
            throw new AccessDenidedException();
        }

        if (!command.getLabel().isBlank()) {
            judgement.setLabel(command.getLabel());
        }

        judgement.setComment(command.getComment());
        this.spanJudgementRepository.save(judgement);
    }

    /**
     * Delete a use pair judgement.
     * 
     * @param phase     the phase
     * @param annotator the annotator
     * @param command   the command
     */
    @Transactional
    public void delete(final Phase phase, final Annotator annotator, final DeleteSpanJudgementCommand command) {
        final Query query = new SpanJudgementQueryBuilder()
                .withOwner(command.getOwner())
                .withProject(command.getProject())
                .withPhase(command.getPhase())
                .withAnnotator(command.getAnnotator())
                .withInstanceid(command.getInstance())
                .withUUID(command.getUUID())
                .build();
        final List<SpanJudgement> spanJudgements = this.spanJudgementRepository.findByQuery(query);
        if (spanJudgements.size() != 1) {
            throw new SpanJudgementException("Judgement not found");

        }
        final SpanJudgement judgement = spanJudgements.get(0);

        if (!annotator.equals(judgement.getAnnotator())) {
            throw new AccessDenidedException();
        }

        this.spanJudgementRepository.delete(judgement);
    }

    // Setter Command

    /**
     * Add a lexsub judgement.
     * 
     * @param phase     the phase to which the use pair judgement belongs
     * @param annotator the annotator who created the use pair judgement
     * @param command   the command
     */
    @Transactional
    public void annotate(final Phase phase, final Annotator annotator, final AddSpanJudgementCommand command) {
        String instanceId = command.getInstance();

        final SpanInstance instance = this.findCorrespondingInstanceData(phase, instanceId);

        final SpanJudgement resultData = new SpanJudgement(instance, annotator, command.getLabel(),
                command.getComment());
        this.spanJudgementRepository.save(resultData);

        // Update annotation count for the project
        this.userStatisticApplicationService.incrementAnnotationCountProject(phase.getProject());
        this.annotatorStatisticApplicationService.updateAnnotationStatistic(annotator, phase);
        this.phaseStatisticApplicationService.updatePhaseStatisticForAnnotation(annotator, phase);
    }


    /**
     * Add a span tutorial  judgement.
     *
     * @param phase     the phase to which the use pair judgement belongs
     * @param annotator the annotator who created the use pair judgement
     * @param command   the command

    @Transactional
    public void annotateTutorial(final Phase phase, final Annotator annotator, final AddSpanJudgementCommand command) {
        String instanceId = command.getInstance();

        final SpanInstance instance = this.findCorrespondingInstanceData(phase, instanceId);

        final LexSubTutorialJudgement resultData = new LexSubTutorialJudgement(instance, annotator, command.getLabel(),
                command.getComment());
        this.lexSubTutorialJudgementRepository.save(resultData);

    }*/


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
            final List<AddSpanJudgementCommand> commands) {
       /** if (phase.isTutorial()) {
            tutorialAnnotationCorrectness(phase, annotator, commands);
            commands.forEach(command -> {
                this.annotateTutorial(phase, annotator, command);
            });
            return;
        }*/

        commands.forEach(command -> {
            this.annotate(phase, annotator, command);
        });
    }

    // Helper

    private SpanInstance findCorrespondingInstanceData(final Phase phase, final String instanceid) {
        final Query query = new SpanInstanceQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withInstanceid(instanceid).build();
        List<SpanInstance> resultData = this.spanInstanceRepository.findByQuery(query);
        if (resultData.size() == 1) {
            return resultData.get(0);
        } else {
            throw new CsvParseException(
                    "Instance ID " + instanceid + " not found or ambiguous. Please check your CSV file");
        }
    }

    /**
     * Count all span attempted judgements for a given annotator.
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
        return (int) this.spanJudgementRepository.findByQueryPaged(
                new SpanJudgementQueryBuilder()
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
            List<AddSpanJudgementCommand> commands) {
        final Query judgementQuery = new LexSubJudgementQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName()).build();

        final List<SpanJudgement> golds = this.spanJudgementRepository.findByQuery(judgementQuery);

        if (commands.size() != golds.size()) {
            throw new TutorialException(
                    "Tutorial not completed. Please check your judgements. Not all instances were judged");
        }

        // Create two lists of the labels of the gold and the annotator
        List<String> goldLabels = golds.stream().map(SpanJudgement::getLabel).toList();
        List<String> annotatorLabels = commands.stream().map(AddSpanJudgementCommand::getLabel).toList();

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

    private List<List<String>> parseJudgementsToCsvBody(List<SpanJudgement> judgements) {
        List<List<String>> csvBody = new ArrayList<>();

        for (SpanJudgement judgement : judgements) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(judgement.getInstance().getId().getInstanceid());
            csvRow.add(judgement.getLabel());
            csvRow.add(judgement.getComment());
            csvRow.add(judgement.getAnnotator().getId().getUsername());

            csvBody.add(csvRow);
        }

        return csvBody;
    }

    /**
    private List<List<String>> parseTutorialJudgementsToCsvBody(List<LexSubTutorialJudgement> judgements) {
        List<List<String>> csvBody = new ArrayList<>();

        for (LexSubTutorialJudgement judgement : judgements) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(judgement.getInstance().getId().getInstanceid());
            csvRow.add(judgement.getLabel());
            csvRow.add(judgement.getComment());
            csvRow.add(judgement.getAnnotator().getId().getUsername());

            csvBody.add(csvRow);
        }

        return csvBody;
    }
*/
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

    private SpanJudgement parseRecordToJudgement(final Phase phase, final Annotator annotator,
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

        SpanInstance instanceData = findCorrespondingInstanceData(phase, instanceId);
        return new SpanJudgement(instanceData, annotator, label, comment);
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

    public int countJudgements(Annotator annotator, Phase phase) {
        return (int) this.spanJudgementRepository.findByQueryPaged(
                new LexSubJudgementQueryBuilder()
                        .withAnnotator(annotator.getId().getUsername())
                        .withPhase(phase.getId().getName()).build(),
                new PageRequestWraper(1, 0)).getTotalElements();
    }


}
