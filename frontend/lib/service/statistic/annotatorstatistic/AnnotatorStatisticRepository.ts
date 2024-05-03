import axios from "axios";
import useSWR from "swr";
import BACKENDROUTES from "../../../BackendRoutes";
import useStorage from "../../../hook/useStorage";
import AnnotatorStatisticDto from "../../../model/statistic/annotatorstatistic/dto/AnnotatorStatisticDto";
import AnnotatorStatistic from "../../../model/statistic/annotatorstatistic/model/AnnotatorStatistic";

export default function useFetchAnnotatorStatistic(owner: string, project: string, annotator: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const userStatisticFetcher = (url: string) => axios.get<AnnotatorStatisticDto>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.ANNOTATORSTATISTIC}?annotator=${annotator}&owner=${owner}&project=${project}` : null, userStatisticFetcher)

    return {
        statistic: data ? AnnotatorStatistic.fromDto(data) : null as unknown as AnnotatorStatistic,
        isLoading: !error && !data,
        isError: error,
        mutate
    }
}