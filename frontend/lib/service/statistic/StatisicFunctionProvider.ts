import AnnotatorStatistic from "../../model/statistic/model/AnnotatorStatistic";

export function getNumberOfAnnotationsPerAnnotator(statistics: AnnotatorStatistic[]) {
    const data: any = [];

    for (const statistic of statistics) {
        if (statistic.getNumberOfAnnotations() === 0) {
            continue;
        }
        data.push({
            key: statistic.getAnnotator().getUser().getUsername(),
            data: statistic.getNumberOfAnnotations()
        });
    }

    if (data.length === 0) {
        return [{ key: "None", data: 1 }];
    }

    return data;
}

export function getNumberOfAnnotationsPerAnnotatorPerPhase(statistics: AnnotatorStatistic[]) {
    const data: any = [];

    const phases: Set<string> = getAllPhases(statistics);
    phases.forEach(phase => {
        const phaseData = [];
        for (const statistic of statistics) {

            phaseData.push({
                key: statistic.getAnnotator().getUser().getUsername(),
                data: statistic.getNumberOfAnnotationsPerPhase().find(anno => anno.phase.getId().getPhase() === phase)?.numberOfAnnotations ?? 0
            });
        }
        data.push({
            key: phase,
            data: phaseData
        });
    });

    if (data.length === 0) {
        return {
            phases: [""],
            data: [{ key: "", data: [{ key: "", data: 0 }] }]
        };
    }

    return {
        phases: Array.from(phases),
        data: data,
    }
}

function getAllPhases(statistics: AnnotatorStatistic[]): Set<string> {
    const phases = new Set<string>();

    for (const statistic of statistics) {
        statistic.getNumberOfAnnotationsPerPhase().forEach(anno => {
            phases.add(anno.phase.getId().getPhase());
        })
    }

    return phases;
}

export function getPhaseAnnotationOfAnnotator(annotator: AnnotatorStatistic) {
    const data: any = [];

    annotator.getNumberOfAnnotationsPerPhase().forEach(anno => {
        data.push({
            key: anno.phase.getId().getPhase(),
            data: anno.numberOfAnnotations
        });
    });

    return data;
}