import axios from "axios";
import useSWR from "swr";

// Custom Hooks
import useStorage from "../../hook/useStorage";

// Routes
import BACKENDROUTES from "../../BackendRoutes";

// Models
import AnnotatorStatistic from "../../model/statistic/model/AnnotatorStatistic";
import AnnotatorStatisticDto from "../../model/statistic/dto/AnnotatorStatisticDto";

// Custom hooks for fetching annotator statistics and annotator statistic related data

/**
 * Returns the project statistics for a specific project
 * 
 * @param owner the owner of the project
 * @param project the project name
 * @param phase the phase of the project
 * @returns the project statistics for a specific project
 */
export function useFetchProjectStatistic(owner: string, project: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<AnnotatorStatisticDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    // MAybe not the best way to do this, as this will trigger new requests -> more calculation time on the server
    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.STATISTIC}/project?owner=${owner}&project=${project}` : null, queryProjectFetcher)

    return {
        data: data ? data.map(AnnotatorStatistic.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }

}

/**
 * Returns the phase statistics for a specific project
 * 
 * @param owner the owner of the project
 * @param project the project name
 * @param phase the phase of the project
 * @param fetch if data should be fetched
 * @returns the phase statistics for a specific project
 */
export function useFetchPhaseStatistic(owner: string, project: string, phase: string,  fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<AnnotatorStatisticDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    // MAybe not the best way to do this, as this will trigger new requests -> more calculation time on the server
    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.STATISTIC}/phase?owner=${owner}&project=${project}&phase=${phase}`: null, queryProjectFetcher)

    return {
        data: data ? data.map(AnnotatorStatistic.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }
}

/**
 * Returns the annotator statistics for a specific project
 * 
 * @param owner the owner of the project
 * @param project the project name
 * @param fetch if data should be fetched
 * @returns the annotator statistics for a specific project
 */
export function useFetchAnnotatorStatistic(owner: string, project: string, annotator: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<AnnotatorStatisticDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    // MAybe not the best way to do this, as this will trigger new requests -> more calculation time on the server
    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.STATISTIC}/annotator?owner=${owner}&project=${project}&annotator=${annotator}` : null, queryProjectFetcher)

    return {
        data: data ? AnnotatorStatistic.fromDto(data) : null,
        isLoading: !error && !data,
        isError: error
    }

}