package de.garrafao.phitag.application.statistics.userstatistic.data;

import java.util.Map;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.domain.statistic.userstatistic.UserStatistic;
import lombok.Getter;

@Getter
public class UserStatisticDto {

    private final String username;

    private final Integer projectcount;
    private final Map<String, Integer> languageCountMap;
    private final Map<String, Integer> annotationTypeCountMap;
    private final Map<String, Integer> pojectAnnotationCountMap;

    public UserStatisticDto(
            final String username,
            final Integer projectcount,
            final Map<String, Integer> languageCountMap,
            final Map<String, Integer> annotationTypeCountMap,
            final Map<String, Integer> pojectAnnotationCountMap) {
        this.username = username;
        this.projectcount = projectcount;
        this.languageCountMap = languageCountMap;
        this.annotationTypeCountMap = annotationTypeCountMap;
        this.pojectAnnotationCountMap = pojectAnnotationCountMap;
    }

    public void setAnnotationTypeCountMap(Map<String, Integer> annotationTypeCountMap) {
        this.annotationTypeCountMap.clear();
        this.annotationTypeCountMap.putAll(annotationTypeCountMap);
    }

    public static UserStatisticDto from(UserStatistic userStatistic) {
        return new UserStatisticDto(
                userStatistic.getUsername(),
                userStatistic.getProjectcount(),
                userStatistic.getLanguageCountMap(),
                userStatistic.getAnnotationTypeCountMap(),
                userStatistic.getPojectAnnotationCountMap());
    }

}
