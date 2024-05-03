package de.garrafao.phitag.infrastructure.persistence.jpa.instance.sentimentandchoiceannotation;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstance;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentInstanceAndChoiceRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.instance.sentimentandchoiceannotation.query.SentimentAndChoiceAnnotationInstanceQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SentimentAndChoiceInstanceRepositoryBridge implements SentimentInstanceAndChoiceRepository {

    private final SentimentAndChoiceAnnotationInstanceRepositoryJpa sentimentAndChoiceAnnotationInstanceRepositoryJpa;

    @Autowired
    public SentimentAndChoiceInstanceRepositoryBridge(SentimentAndChoiceAnnotationInstanceRepositoryJpa sentimentAndChoiceAnnotationInstanceRepositoryJpa) {
        this.sentimentAndChoiceAnnotationInstanceRepositoryJpa = sentimentAndChoiceAnnotationInstanceRepositoryJpa;
    }

    @Override
    public List<SentimentAndChoiceInstance> findByQuery(Query query) {
        return this.sentimentAndChoiceAnnotationInstanceRepositoryJpa.findAll(new SentimentAndChoiceAnnotationInstanceQueryJpa(query));
    }

    @Override
    public Page<SentimentAndChoiceInstance> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.sentimentAndChoiceAnnotationInstanceRepositoryJpa.findAll(new SentimentAndChoiceAnnotationInstanceQueryJpa(query), page.getPageRequest());
    }

    @Override
    public Optional<SentimentAndChoiceInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName) {
        return this.sentimentAndChoiceAnnotationInstanceRepositoryJpa
                .findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(instanceId,
                        phaseName, projectName, ownerName);
    }

    @Override
    public SentimentAndChoiceInstance save(SentimentAndChoiceInstance instanceData) {
        return this.sentimentAndChoiceAnnotationInstanceRepositoryJpa.save(instanceData);
    }

    @Override
    public void delete(Iterable<SentimentAndChoiceInstance> instanceData) {
        this.sentimentAndChoiceAnnotationInstanceRepositoryJpa.deleteInBatch(instanceData);
    }

}
