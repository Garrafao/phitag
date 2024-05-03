package de.garrafao.phitag.application.common;

import de.garrafao.phitag.application.common.error.AgreementStatisticException;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasureEnum;
import org.dkpro.statistics.agreement.IAgreementMeasure;
import org.dkpro.statistics.agreement.InsufficientDataException;
import org.dkpro.statistics.agreement.coding.*;
import org.dkpro.statistics.agreement.distance.IntervalDistanceFunction;
import org.dkpro.statistics.agreement.distance.NominalDistanceFunction;
import org.dkpro.statistics.agreement.distance.RatioDistanceFunction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for common math operations for e.g. annotations
 */
@Service
public class CommonMathService {

    public CommonMathService() {

    }

    /**
     * Calculate the annotator agreement between multiple annotators for a given set of categories and a given statistic measure
     * Note, that this function assumes that the annotations are in the same order for each annotator and that if an annotation is
     * missing, it is represented by the null-type
     * 
     * Due to the underlying library, if only one category is used by the annotators, the wrapper decides which statistic measure is 1.0 
     *  
     * @param categories          the categories
     * @param statisticMeasure    the statistic measure
     * @param annotationsPerAnnotator the annotations per annotator
     * 
     * @return the annotator agreement
     */
    public double calculateAnnotatorAgreement(
            final List<String> categories,
            final StatisticAnnotationMeasureEnum statisticMeasure,
            final List<List<String>> annotationsPerAnnotator) {

        validateLength(annotationsPerAnnotator);

        final CodingAnnotationStudy study = new CodingAnnotationStudy(annotationsPerAnnotator.size());

        categories.forEach(study::addCategory);

        for (int i = 0; i < annotationsPerAnnotator.get(0).size(); i++) {
            // build the annotation array for the current annotation
            final String[] annotations = new String[annotationsPerAnnotator.size()];
            for (int j = 0; j < annotationsPerAnnotator.size(); j++) {
                annotations[j] = annotationsPerAnnotator.get(j).get(i);
            }

            study.addItemAsArray(annotations);
        }

        try {
            return getAgreementMeasure(study, statisticMeasure).calculateAgreement();
        } catch (final InsufficientDataException e) {
            return 1.0;
        } catch (final Exception e) {
            throw new AgreementStatisticException("Could not calculate agreement. This might be due to chosen statistic measure, categories or annotations");
        }
    }

    private IAgreementMeasure getAgreementMeasure(final CodingAnnotationStudy study,
            final StatisticAnnotationMeasureEnum statisticMeasure) {
        IAgreementMeasure agreementMeasure;

        switch (statisticMeasure) {
            case PERCENTAGE_AGREEMENT:
                agreementMeasure = new PercentageAgreement(study);
                break;
            case SCOTT_PI:
                agreementMeasure = new ScottPiAgreement(study);
                break;
            case COHEN_KAPPA:
                agreementMeasure = new CohenKappaAgreement(study);
                break;
            case RANDOLPH_KAPPA:
                agreementMeasure = new RandolphKappaAgreement(study);
                break;
            case FLEISS_KAPPA:
                agreementMeasure = new FleissKappaAgreement(study);
                break;
            case HUBERT_KAPPA:
                agreementMeasure = new HubertKappaAgreement(study);
                break;
            case KRIPPENDORFF_ALPHA_INTERVAL:
                agreementMeasure = new KrippendorffAlphaAgreement(study, new IntervalDistanceFunction());
                break;
            case KRIPPENDORFF_ALPHA_NOMINAL:
                agreementMeasure = new KrippendorffAlphaAgreement(study, new NominalDistanceFunction());
                break;
            case KRIPPENDORFF_ALPHA_ORDINAL:
                //agreementMeasure = new KrippendorffAlphaAgreement(study, new OrdinalDistanceFunction());
                agreementMeasure = new KrippendorffAlphaAgreement(study, new OrdinalDistanceFunctionWithDoubles());

                break;
            case KRIPPENDORFF_ALPHA_RATIO:
                agreementMeasure = new KrippendorffAlphaAgreement(study, new RatioDistanceFunction());
                break;
            default:
                throw new IllegalArgumentException("Statistic measure not supported");
        }

        return agreementMeasure;
    }

    private void validateLength(final List<List<String>> annotationsPerAnnotator) {
        final int length = annotationsPerAnnotator.get(0).size();
        for (final List<String> annotations : annotationsPerAnnotator) {
            if (annotations.size() != length) {
                throw new IllegalArgumentException("Annotations must have the same length");
            }
        }
    }
    
}
