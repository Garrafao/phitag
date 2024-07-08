import StatisticAnnotationMeasure from "../../statistic/statisticannotationmeasure/model/StatisticAnnotationMeasure";

export default class AddStaticHistoryCommand{

    private readonly annotatorname: string;
    private readonly goldannotator: string;
    private readonly ownername: string;
    private readonly projectname: string;
    private readonly phasename: string;
    private readonly  statisticAnnotationMeasure : StatisticAnnotationMeasure;  

    constructor(annotatorname: string, goldannotator: string, ownername: string, projectname: string, phasename: string,  statisticAnnotationMeasure : StatisticAnnotationMeasure) {
        this.annotatorname = annotatorname;
        this.goldannotator  = goldannotator;
        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;
        this.statisticAnnotationMeasure  = statisticAnnotationMeasure ;

    }

}
