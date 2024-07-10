package de.garrafao.phitag.computationalannotator.common.function;

import de.garrafao.phitag.domain.error.CsvParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExportCompuationalAnnotatorParameter {

    public InputStreamResource exportParmeter(final String openAIKey,
                                               final String model,
                                               final String temperature,
                                               final String topP,
                                               final String systemMessage,
                                               final  String prompt,
                                               final String finalMessage) {

        String[] csvHeader = {
                "key", "model-name", "temperature", "topP", "system prompt", "main prompt", "final prompt"
        };
        List<List<String>> csvData = parseParameterToCsvBody(openAIKey, model,
                temperature, topP, systemMessage, prompt, finalMessage);

        ByteArrayInputStream inputStream;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = createCsvPrinter(csvHeader, out);

        for (List<String> row : csvData) {
            try {
                csvPrinter.printRecord(row);
            } catch (Exception e) {
                throw new CsvParseException("Error writing CSV record");
            }
        }
        try {
            csvPrinter.flush();
            inputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new CsvParseException("Error flushing CSV printer");
        }

        return new InputStreamResource(inputStream);
    }

    private List<List<String>> parseParameterToCsvBody(final String openAIKey,
                                                                 final String model,
                                                                 final String temperature,
                                                                 final String topP,
                                                                 final String systemMessage,
                                                                 final String prompt,
                                                                 final String finalMessage) {
        List<List<String>> csvBody = new ArrayList<>();
        List<String> csvRow = new ArrayList<>();

        csvRow.add(openAIKey);
        csvRow.add(model);
        csvRow.add(String.valueOf(temperature));
        csvRow.add(String.valueOf(topP));
        csvRow.add(systemMessage);
        csvRow.add(prompt);
        csvRow.add(finalMessage);

        csvBody.add(csvRow);
        return csvBody;
    }
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



}
