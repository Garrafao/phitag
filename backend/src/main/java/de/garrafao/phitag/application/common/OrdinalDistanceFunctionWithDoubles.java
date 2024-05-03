package de.garrafao.phitag.application.common;

import org.dkpro.statistics.agreement.IAnnotationStudy;
import org.dkpro.statistics.agreement.coding.CodingAnnotationStudy;
import org.dkpro.statistics.agreement.coding.ICodingAnnotationStudy;
import org.dkpro.statistics.agreement.distance.IDistanceFunction;

import java.util.Map;

public class OrdinalDistanceFunctionWithDoubles implements IDistanceFunction {
    public OrdinalDistanceFunctionWithDoubles() {
    }

    public double measureDistance(IAnnotationStudy study, Object category1, Object category2) {
        if (category1 instanceof Double && category2 instanceof Double) {
            if (category1.equals(category2)) {
                return 0.0;
            } else {
                Map<Object, Integer> nk = CodingAnnotationStudy.countTotalAnnotationsPerCategory((ICodingAnnotationStudy) study);
                double result = 0.0;
                Integer v = nk.get(category1);
                if (v != null) {
                    result += v / 2.0;
                }

                v = nk.get(category2);
                if (v != null) {
                    result += v / 2.0;
                }

                double minCat = Math.min((Double) category1, (Double) category2);
                double maxCat = Math.max((Double) category1, (Double) category2);

                for (Map.Entry<Object, Integer> entry : nk.entrySet()) {
                    if (minCat < (Double) entry.getKey() && (Double) entry.getKey() < maxCat) {
                        result += entry.getValue();
                    }
                }

                return result * result;
            }
        } else {
            return category1.equals(category2) ? 0.0 : 1.0;

        }
    }
}
