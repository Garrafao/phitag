package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.sentimentandchoice;

import de.garrafao.phitag.domain.tutorial.sentimentandchoice.SentimentAndChoiceTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.sentimentandchoice.SentimentAndChoiceTutorialJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SentimentAndChoiceTutorialJudgementRepositoryJpa extends JpaRepository<SentimentAndChoiceTutorialJudgement, SentimentAndChoiceTutorialJudgementId>, JpaSpecificationExecutor<SentimentAndChoiceTutorialJudgement> {
    
}
