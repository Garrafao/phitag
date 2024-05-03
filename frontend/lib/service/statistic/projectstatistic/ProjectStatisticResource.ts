import axios from "axios";
import useSWR from "swr";
import BACKENDROUTES from "../../../BackendRoutes";
import useStorage from "../../../hook/useStorage";
import ProjectStatisticDto from "../../../model/statistic/projectstatistic/dto/ProjectStatisticDto";
import ProjectStatistic from "../../../model/statistic/projectstatistic/model/ProjectStatistic";

export default function useFetchProjectStatistic(user: string, project: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const userStatisticFetcher = (url: string) => axios.get<ProjectStatisticDto>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PROJECTSTATISTIC}?user=${user}&project=${project}` : null, userStatisticFetcher)

    return {
        statistic: data ? ProjectStatistic.fromDto(data) : null as unknown as ProjectStatistic,
        isLoading: !error && !data,
        isError: error,
        mutate
    }

}