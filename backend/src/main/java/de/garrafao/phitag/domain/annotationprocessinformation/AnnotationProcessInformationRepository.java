package de.garrafao.phitag.domain.annotationprocessinformation;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface AnnotationProcessInformationRepository {

    List<AnnotationProcessInformation> findByQuery(final Query query);
    
    Page<AnnotationProcessInformation> findByQueryPaged(final Query query, final PageRequestWraper page);

    AnnotationProcessInformation save(AnnotationProcessInformation samplingOrder);

}
