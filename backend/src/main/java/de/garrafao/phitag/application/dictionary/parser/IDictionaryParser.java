package de.garrafao.phitag.application.dictionary.parser;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import de.garrafao.phitag.application.dictionary.dictionary.data.DictionaryFileType;
import de.garrafao.phitag.domain.dictionary.dictionary.Dictionary;

/**
 * Interface for dictionary parsers.
 */
public interface IDictionaryParser {
   
    /**
     * Parses the given file and adds the entries to the dictionary.
     * 
     * @param file The file to parse
     */
    public void parse(final Dictionary dictionary, final MultipartFile file);
    
    /**
     * Returns the file type of the parser.
     * 
     * @return The file type
     */
    public DictionaryFileType getFileType();

    /**
     * Export the given dictionary to a file.
     */
    public InputStreamResource export(final Dictionary dictionary);
}
