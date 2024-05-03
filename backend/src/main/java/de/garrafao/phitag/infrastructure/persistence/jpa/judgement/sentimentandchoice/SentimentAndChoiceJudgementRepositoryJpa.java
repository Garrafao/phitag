package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.sentimentandchoice;

import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgement;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SentimentAndChoiceJudgementRepositoryJpa extends JpaRepository<SentimentAndChoiceJudgement, SentimentAndChoiceJudgementId>, JpaSpecificationExecutor<SentimentAndChoiceJudgement> {
    
}
