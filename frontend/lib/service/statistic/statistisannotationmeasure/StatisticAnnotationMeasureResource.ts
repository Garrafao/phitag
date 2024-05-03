import axios from "axios";
import useStorage from "../../../hook/useStorage";
import StatisticAnnotationMeasureDto from "../../../model/statistic/statisticannotationmeasure/dto/StatisticAnnotationMeasureDto";
import BACKENDROUTES from "../../../BackendRoutes";
import useSWR from "swr";
import StatisticAnnotationMeasure from "../../../model/statistic/statisticannotationmeasure/model/StatisticAnnotationMeasure";


export function useFetchAllStatisticAnnotationMeasureResource(fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<StatisticAnnotationMeasureDto[]>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.STATISTIC}/annotationmeasure` : null, fetcher)

    return {
        statisticAnnotationMeasures: data ? data.map(StatisticAnnotationMeasure.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }
}