package de.garrafao.phitag.infrastructure.persistence.jpa.samplingorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.annotationprocessinformation.AnnotationProcessInformation;
import de.garrafao.phitag.domain.annotationprocessinformation.AnnotationProcessInformationRepository;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.infrastructure.persistence.jpa.samplingorder.query.AnnotationProcessInformationQueryJpa;

@Repository
public class AnnotationProcessInformationBridge implements AnnotationProcessInformationRepository {

    private final AnnotationProcessInformationJpa annotationProcessInformationJpa;

    @Autowired
    public AnnotationProcessInformationBridge(AnnotationProcessInformationJpa annotationProcessInformationJpa) {
        this.annotationProcessInformationJpa = annotationProcessInformationJpa;
    }

    @Override
    public List<AnnotationProcessInformation> findByQuery(Query query) {
        final AnnotationProcessInformationQueryJpa specification = new AnnotationProcessInformationQueryJpa(query);

        return annotationProcessInformationJpa.findAll(specification);
    }

    @Override
    public Page<AnnotationProcessInformation> findByQueryPaged(Query query, PageRequestWraper page) {
        final AnnotationProcessInformationQueryJpa specification = new AnnotationProcessInformationQueryJpa(query);

        return annotationProcessInformationJpa.findAll(specification, page.getPageRequest());
    }

    @Override
    public AnnotationProcessInformation save(AnnotationProcessInformation annotationProcessInformation) {
        return annotationProcessInformationJpa.save(annotationProcessInformation);
    }

}
