package de.garrafao.phitag.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import de.garrafao.phitag.application.common.CommonMathService;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasureEnum;

@SpringBootTest
@ActiveProfiles("test")
public class CommonMathServiceTest {

    @Autowired
    private CommonMathService commonMathService;

    @Test
    public void test_calculateFleissKappa_perfect() {
        List<String> annotationAnnotator1 = Arrays.asList("1", "1", "1", "1", "1");
        List<String> annotationAnnotator2 = Arrays.asList("1", "1", "1", "1", "1");

        // Data 
        List<List<String>> annotations = Arrays.asList(annotationAnnotator1, annotationAnnotator2);
        List<String> categories = Arrays.asList("0", "1");
        
        // Calculate different measures
        assertEquals(1.0, commonMathService.calculateAnnotatorAgreement(categories, StatisticAnnotationMeasureEnum.PERCENTAGE_AGREEMENT, annotations), 0.001);
        assertEquals(1.0, commonMathService.calculateAnnotatorAgreement(categories, StatisticAnnotationMeasureEnum.SCOTT_PI, annotations), 0.001);
        assertEquals(1.0, commonMathService.calculateAnnotatorAgreement(categories, StatisticAnnotationMeasureEnum.COHEN_KAPPA, annotations), 0.001);
        assertEquals(1.0, commonMathService.calculateAnnotatorAgreement(categories, StatisticAnnotationMeasureEnum.RANDOLPH_KAPPA, annotations), 0.001);
        assertEquals(1.0, commonMathService.calculateAnnotatorAgreement(categories, StatisticAnnotationMeasureEnum.FLEISS_KAPPA, annotations), 0.001);
        // assertEquals(1.0, commonMathService.calculateAnnotatorAgreement(categories, StatisticMeasureEnum.HUBERT_KAPPA, annotations), 0.001);
        assertEquals(0.0, commonMathService.calculateAnnotatorAgreement(categories, StatisticAnnotationMeasureEnum.KRIPPENDORFF_ALPHA_NOMINAL, annotations), 0.001);
        
    }

    @Test
    public void test_calculateFleissKappa_slight() {
        List<String> annotationAnnotator1 = Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        List<String> annotationAnnotator2 = Arrays.asList("1", "1", "1", "1", "0", "1", "1", "1", "1", "1");

        // Calculate Fleiss' Kappa
        List<List<String>> annotations = Arrays.asList(annotationAnnotator1, annotationAnnotator2);
        List<String> categories = Arrays.asList("0", "1");
        StatisticAnnotationMeasureEnum statisticMeasure = StatisticAnnotationMeasureEnum.FLEISS_KAPPA;

        double kappa = commonMathService.calculateAnnotatorAgreement(categories, statisticMeasure, annotations);

        assertEquals(-0.052, kappa, 0.001);
    }

    @Test
    public void test_calculateFleissKappa_moderate() {
        List<String> annotationAnnotator1 = Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        List<String> annotationAnnotator2 = Arrays.asList("1", "1", "1", "0", "0", "1", "1", "1", "1", "1");

        // Calculate Fleiss' Kappa
        List<List<String>> annotations = Arrays.asList(annotationAnnotator1, annotationAnnotator2);
        List<String> categories = Arrays.asList("0", "1");
        StatisticAnnotationMeasureEnum statisticMeasure = StatisticAnnotationMeasureEnum.FLEISS_KAPPA;

        double kappa = commonMathService.calculateAnnotatorAgreement(categories, statisticMeasure, annotations);

        assertEquals(-0.111, kappa, 0.001);
    }

    @Test
    public void test_calculateFleissKappa_fair() {
        List<String> annotationAnnotator1 = Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        List<String> annotationAnnotator2 = Arrays.asList("1", "1", "0", "0", "0", "1", "1", "1", "1", "1");

        // Calculate Fleiss' Kappa
        List<List<String>> annotations = Arrays.asList(annotationAnnotator1, annotationAnnotator2);
        List<String> categories = Arrays.asList("0", "1");
        StatisticAnnotationMeasureEnum statisticMeasure = StatisticAnnotationMeasureEnum.FLEISS_KAPPA;

        double kappa = commonMathService.calculateAnnotatorAgreement(categories, statisticMeasure, annotations);

        assertEquals(-0.176, kappa, 0.001);
    }

    @Test
    public void test_calculateFleissKappa_poor() {
        List<String> annotationAnnotator1 = Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        List<String> annotationAnnotator2 = Arrays.asList("1", "0", "0", "0", "0", "1", "1", "1", "1", "1");

        // Calculate Fleiss' Kappa
        List<List<String>> annotations = Arrays.asList(annotationAnnotator1, annotationAnnotator2);
        List<String> categories = Arrays.asList("0", "1");
        StatisticAnnotationMeasureEnum statisticMeasure = StatisticAnnotationMeasureEnum.FLEISS_KAPPA;

        double kappa = commonMathService.calculateAnnotatorAgreement(categories, statisticMeasure, annotations);

        assertEquals(-0.25, kappa, 0.01);
    }

}
