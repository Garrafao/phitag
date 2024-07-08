package de.garrafao.phitag.domain.instance.sentimentandchoice;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SentimentInstanceAndChoiceRepository {

    List<SentimentAndChoiceInstance> findByQuery(final Query query);
    
    Page<SentimentAndChoiceInstance> findByQueryPaged(final Query query, final PageRequestWraper page);

    Optional<SentimentAndChoiceInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(final String instanceId, final String phaseName, final String projectName, final String ownerName);

    SentimentAndChoiceInstance save(SentimentAndChoiceInstance instanceData);

    void delete(Iterable<SentimentAndChoiceInstance> instanceData);

    void delete(SentimentAndChoiceInstance instanceData);
}
