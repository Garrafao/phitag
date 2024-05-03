package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.usepair;

import de.garrafao.phitag.domain.tutorial.usepair.UsePairTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.usepair.UsePairTutorialJudgementId;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsePairTutorialJudgementRepositoryJpa
        extends JpaRepository<UsePairTutorialJudgement, UsePairTutorialJudgementId>, JpaSpecificationExecutor<UsePairTutorialJudgement> {


    long count(Specification<UsePairTutorialJudgement> usePairJudgement);
}
