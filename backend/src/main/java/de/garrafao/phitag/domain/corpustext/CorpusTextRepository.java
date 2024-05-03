package de.garrafao.phitag.domain.corpustext;

import java.util.List;
import java.util.Optional;

public interface CorpusTextRepository {

    Optional<CorpusText> findById(String id);

    List<CorpusText> findByIdIn(List<String> ids);

}
