package de.garrafao.phitag.domain.phitagdata.usage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface UsageRepository {

    List<Usage> findByQuery(Query query); 

    Page<Usage> findByQueryPaged(Query query, PageRequestWraper page);

    Optional<Usage> findByIdDataidAndIdProjectidNameAndIdProjectidOwnername(String dataid, String projectname, String ownername);

    Usage save(Usage phaseData);

    List<String> findAllDataIdsByProjectnameAndOwnername(String projectname, String ownername);

    // For statistics

    Integer countDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(final String projectname, final String ownername);

    Integer countByIdProjectidNameAndIdProjectidOwnername(final String projectname, final String ownername);

    List<String> findDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(final String projectname, final String ownername);

    Integer countDistinctLemmaByLemmaAndIdProjectidNameAndIdProjectidOwnername(final String lemma, final String projectname, final String ownername);

}
