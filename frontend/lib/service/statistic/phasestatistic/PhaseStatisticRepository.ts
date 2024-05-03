import axios from "axios";
import useSWR from "swr";
import BACKENDROUTES from "../../../BackendRoutes";
import useStorage from "../../../hook/useStorage";
import PhaseStatisticDto from "../../../model/statistic/phasestatistic/dto/PhaseStatisticDto";
import PhaseStatistic from "../../../model/statistic/phasestatistic/model/PhaseStatistic";

export default function useFetchPhaseStatistic(owner: string, project: string, phase: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const phaseStatisticFetcher = (url: string) => axios.get<PhaseStatisticDto>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PHASESTATISTIC}?owner=${owner}&project=${project}&phase=${phase}` : null, phaseStatisticFetcher)

    return {
        statistic: data ? PhaseStatistic.fromDto(data) : null as unknown as PhaseStatistic,
        isLoading: !error && !data,
        isError: error,
        mutate
    }
}