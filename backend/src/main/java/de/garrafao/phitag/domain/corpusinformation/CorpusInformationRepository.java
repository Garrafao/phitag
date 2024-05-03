package de.garrafao.phitag.domain.corpusinformation;

import java.util.List;
import java.util.Optional;

public interface CorpusInformationRepository {
    
    Optional<CorpusInformation> findById(String id);

    List<String> findDistinctCorpusnamesShort();
}
