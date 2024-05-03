package de.garrafao.phitag.infrastructure.persistence.jpa.phitagdata.usage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import de.garrafao.phitag.domain.phitagdata.usage.UsageId;

public interface UsageRepositoryJpa extends JpaRepository<Usage, UsageId>, JpaSpecificationExecutor<Usage> {

    Optional<Usage> findByIdDataidAndIdProjectidNameAndIdProjectidOwnername(String dataid, String projectname,
            String ownername);

    @Query("SELECT DISTINCT u.id.dataid FROM Usage u WHERE u.id.projectid.name = ?1 AND u.id.projectid.ownername = ?2")
    List<String> findAllDataIdsByProjectnameAndOwnername(String projectname, String ownername);

    // For statistics
    // TODO: This is a hacky way to get the statistics. It should by using springs derived query methods.

    @Query("SELECT COUNT(DISTINCT u.lemma) FROM Usage u WHERE u.id.projectid.name = ?1 AND u.id.projectid.ownername = ?2")
    Integer countDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(final String projectname, final String ownername);

    @Query("SELECT COUNT(u) FROM Usage u WHERE u.id.projectid.name = ?1 AND u.id.projectid.ownername = ?2")
    Integer countByIdProjectidNameAndIdProjectidOwnername(final String projectname, final String ownername);

    @Query("SELECT DISTINCT u.lemma FROM Usage u WHERE u.id.projectid.name = ?1 AND u.id.projectid.ownername = ?2")
    List<String> findDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(final String projectname, final String ownername);

    @Query("SELECT COUNT(1) FROM Usage u WHERE u.lemma = ?1 AND u.id.projectid.name = ?2 AND u.id.projectid.ownername = ?3")
    Integer countDistinctLemmaByLemmaAndIdProjectidNameAndIdProjectidOwnername(final String lemma, final String projectname, final String ownername);


}
