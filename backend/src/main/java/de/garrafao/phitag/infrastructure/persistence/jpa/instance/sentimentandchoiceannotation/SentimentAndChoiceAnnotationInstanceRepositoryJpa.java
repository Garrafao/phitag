package de.garrafao.phitag.infrastructure.persistence.jpa.instance.sentimentandchoiceannotation;

import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceAnnotationId;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SentimentAndChoiceAnnotationInstanceRepositoryJpa extends JpaRepository<SentimentAndChoiceInstance, SentimentAndChoiceAnnotationId>, JpaSpecificationExecutor<SentimentAndChoiceInstance> {

    Optional<SentimentAndChoiceInstance> findByIdInstanceidAndIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(
            String instanceId, String phaseName, String projectName, String ownerName);
    
}
