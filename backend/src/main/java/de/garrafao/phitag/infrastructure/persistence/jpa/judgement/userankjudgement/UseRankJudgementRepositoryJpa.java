package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankjudgement;

import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgement;
import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgementId;
import de.garrafao.phitag.domain.judgement.userankjudgement.UseRankJudgement;
import de.garrafao.phitag.domain.judgement.userankjudgement.UseRankJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UseRankJudgementRepositoryJpa
        extends JpaRepository<UseRankJudgement, UseRankJudgementId>, JpaSpecificationExecutor<UseRankJudgement> {

}
