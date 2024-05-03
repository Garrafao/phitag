package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.usepairjudgement;

import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgement;
import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgementId;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsePairJudgementRepositoryJpa
        extends JpaRepository<UsePairJudgement, UsePairJudgementId>, JpaSpecificationExecutor<UsePairJudgement> {


    long count(Specification<UsePairJudgement> usePairJudgement);
}
