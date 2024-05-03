package de.garrafao.phitag.application.instance.wssimtag;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.error.CsvParseException;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTagRepository;
import de.garrafao.phitag.domain.instance.wssimtag.error.WSSIMTagAlreadyExistsException;
import de.garrafao.phitag.domain.instance.wssimtag.error.WSSIMTagNotFoundException;
import de.garrafao.phitag.domain.instance.wssimtag.page.WSSIMTagPageBuilder;
import de.garrafao.phitag.domain.instance.wssimtag.query.WSSIMTagQueryBuilder;
import de.garrafao.phitag.domain.phase.Phase;
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
import java.util.List;

@Service
public class WSSIMTagApplicationService {

    private final WSSIMTagRepository wssimTagRepository;

    @Autowired
    public WSSIMTagApplicationService(final WSSIMTagRepository wssimTagRepository) {
        this.wssimTagRepository = wssimTagRepository;
    }

    // Getter

    /**
     * Get all WSSIMTags for a given phase.
     * 
     * @param phase The phase.
     * @return {@link WSSIMTag} list
     */
    public List<WSSIMTag> findByPhase(final Phase phase) {
        final Query query = new WSSIMTagQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.wssimTagRepository.findByQuery(query);
    }

    /**
     * Get all WSSIM tags for a given phase paged.
     * 
     * 
     * @param phase      the phase
     * @param pagesize   the size of the page
     * @param pagenumber the number of the page
     * @param orderBy    the field to order by
     * @return a {@link WSSIMTag} page
     */
    public Page<WSSIMTag> findByPhasePaged(final Phase phase, final int pagesize, final int pagenumber,
            final String orderBy) {
        final Query query = new WSSIMTagQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .build();
        return this.wssimTagRepository.findByQueryPaged(query,
                new WSSIMTagPageBuilder()
                        .withPageSize(pagesize)
                        .withPageNumber(pagenumber)
                        .withOrderBy(orderBy)
                        .build());
    }

    /**
     * Get the WSSIM Tags for a specific lemma
     * 
     * @param phase The name of the phase
     * @param lemma The lemma in question
     * @return WSSIM Tags with same lemma for this phase
     */
    public List<WSSIMTag> findByPhaseAndLemma(final Phase phase, final String lemma) {
        final Query query = new WSSIMTagQueryBuilder()
                .withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName())
                .withPhase(phase.getId().getName())
                .withLemma(lemma)
                .build();

        return this.wssimTagRepository.findByQuery(query);
    }

    /**
     * Get specific WSSIMTag
     * 
     * @param phase The phase.
     * @param tagid The tagid.
     * @return {@link WSSIMTag}
     */
    public WSSIMTag getWSSIMTag(final Phase phase, final String tag) {
        final Query query = new WSSIMTagQueryBuilder().withOwner(phase.getId().getProjectid().getOwnername())
                .withProject(phase.getId().getProjectid().getName()).withPhase(phase.getId().getName()).withTagid(tag)
                .build();
        List<WSSIMTag> wssimTags = this.wssimTagRepository.findByQuery(query);

        if (wssimTags.size() != 1) {
            throw new WSSIMTagNotFoundException();
        }

        return wssimTags.get(0);
    }

    /**
     * Export all WSSIMTags for a given phase.
     * 
     * @param phase the phase
     * @return a {@link InputStreamResource} with the CSV data
     */
    public InputStreamResource exportWSSIMTag(final Phase phase) {
        List<WSSIMTag> instanceData = this.findByPhase(phase);
        String[] csvHeader = {
                "senseID", "definition", "lemma"
        };
        List<List<String>> csvData = parseWSSIMTagsToCsvBody(instanceData);

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
     * Import WSIMTags for a given phase from a CSV file.
     * 
     * @param phase the phase
     * @param file  the CSV file
     */
    @Transactional
    public void save(final Phase phase, final MultipartFile file) {
        validateCsvFile(file);

        parseCsvFile(file).forEach(csvrecord -> {
            final WSSIMTag data = parseRecordToWSSIMTag(phase, csvrecord);
            validateUniqueInstance(data);
            this.wssimTagRepository.save(data);
        });

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

    private List<List<String>> parseWSSIMTagsToCsvBody(List<WSSIMTag> wssimTags) {
        List<List<String>> csvBody = new ArrayList<>();

        for (WSSIMTag data : wssimTags) {
            List<String> csvRow = new ArrayList<>();

            csvRow.add(data.getId().getTagid());
            csvRow.add(data.getDefinition());
            csvRow.add(data.getLemma());

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

    private WSSIMTag parseRecordToWSSIMTag(final Phase phase, final CSVRecord csvrecord) {
        final String tagid;
        final String definition;
        final String lemma;

        try {

            tagid = csvrecord.get("senseID");
            definition = csvrecord.get("definition");
            lemma = csvrecord.get("lemma");

        } catch (Exception e) {
            throw new CsvParseException("CSV record is not valid, please check the format");
        }
        return new WSSIMTag(tagid, phase, definition, lemma);
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

    private void validateUniqueInstance(final WSSIMTag instanceData) {
        if (this.wssimTagRepository
                .findByIdTagidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
                        instanceData.getId().getTagid(), instanceData.getId().getPhaseid().getName(),
                        instanceData.getId().getPhaseid().getProjectid().getName(),
                        instanceData.getId().getPhaseid().getProjectid().getOwnername())
                .isPresent()) {
            throw new WSSIMTagAlreadyExistsException();
        }
    }



}
