import axios from "axios"
import useSWR from "swr"

// Custom Hooks
import useStorage from "../../hook/useStorage"

// Routes
import BACKENDROUTES from "../../BackendRoutes"

// Models
import SamplingDto from "../../model/sampling/dto/SamplingDto";
import Sampling from "../../model/sampling/model/Sampling";

/**
 * Returns all sampling methods available
 * @param fetch if data should be fetched
 * @returns list of all sampling methods
 */
export function useFetchAllSamplingMethods(fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<SamplingDto[]>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.SAMPLING}/all` : null, fetcher)

    return {
        sampling: data ? data.map(Sampling.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }
    
}