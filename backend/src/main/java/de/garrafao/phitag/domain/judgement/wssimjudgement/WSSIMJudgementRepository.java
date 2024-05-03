package de.garrafao.phitag.domain.judgement.wssimjudgement;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface WSSIMJudgementRepository {

    List<WSSIMJudgement> findByQuery(final Query query);

    Page<WSSIMJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);
    
    WSSIMJudgement save(WSSIMJudgement judgement);

    void delete(WSSIMJudgement judgement);
}
