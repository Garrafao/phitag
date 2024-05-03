package de.garrafao.phitag.domain.dictionary.example;

import java.util.Optional;

public interface DictionaryEntrySenseExampleRepository {

    Optional<DictionaryEntrySenseExample> findById(final DictionaryEntrySenseExampleId id);

    DictionaryEntrySenseExample save(DictionaryEntrySenseExample sense);

    void delete(DictionaryEntrySenseExample sense);

    void saveAll(Iterable<DictionaryEntrySenseExample> dictionaryEntrySenseExamples);

}
