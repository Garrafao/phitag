package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.usepair;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.tutorial.usepair.UsePairTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.usepair.UsePairTutorialJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.usepair.query.UsePairTutorialJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsePairTutorialJudgementRepositoryBridge implements UsePairTutorialJudgementRepository {

    private final UsePairTutorialJudgementRepositoryJpa judgementRepository;

    @Autowired
    public UsePairTutorialJudgementRepositoryBridge(UsePairTutorialJudgementRepositoryJpa resultDataRepositoryJpa) {
        this.judgementRepository = resultDataRepositoryJpa;
    }

    @Override
    public List<UsePairTutorialJudgement> findByQuery(Query query) {
        return judgementRepository.findAll(new UsePairTutorialJudgementQueryJpa(query));
    }

    @Override
    public Page<UsePairTutorialJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return judgementRepository.findAll(new UsePairTutorialJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public UsePairTutorialJudgement save(UsePairTutorialJudgement judgement) {
        return judgementRepository.save(judgement);
    }

    @Override
    public void delete(UsePairTutorialJudgement judgement) {
        judgementRepository.delete(judgement);
    }



}
