package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.lexsub;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.tutorial.lexsub.LexSubTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.lexsub.LexSubTutorialJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.lexsub.query.LexSubTutorialJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LexSubTutorialJudgementRepositoryBridge implements LexSubTutorialJudgementRepository {

    private final LexSubTutorialJudgementRepositoryJpa lexSubJudgementRepositoryJpa;

    @Autowired
    public LexSubTutorialJudgementRepositoryBridge(LexSubTutorialJudgementRepositoryJpa lexSubJudgementRepositoryJpa) {
        this.lexSubJudgementRepositoryJpa = lexSubJudgementRepositoryJpa;
    }

    @Override
    public List<LexSubTutorialJudgement> findByQuery(Query query) {
        return this.lexSubJudgementRepositoryJpa.findAll(new LexSubTutorialJudgementQueryJpa(query));
    }

    @Override
    public Page<LexSubTutorialJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.lexSubJudgementRepositoryJpa.findAll(new LexSubTutorialJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public LexSubTutorialJudgement save(LexSubTutorialJudgement judgement) {
        return this.lexSubJudgementRepositoryJpa.save(judgement);
    }

    @Override
    public void delete(LexSubTutorialJudgement judgement) {
        this.lexSubJudgementRepositoryJpa.delete(judgement);
    }

}
