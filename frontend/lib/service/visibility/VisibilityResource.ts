import axios from "axios"
import useSWR from "swr"

// Custom Hooks
import useStorage from "../../hook/useStorage"

import BACKENDROUTES from "../../BackendRoutes"

// Models
import VisibilityDto from "../../model/visibility/dto/VisibilityDto";
import Visibility from "../../model/visibility/model/Visibility";

/**
 * Fetches all visibilitoes
 * 
 * @param fetch if data should be fetched
 * @returns list of all visibilities
 */
export function useFetchAllVisibility(fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<VisibilityDto[]>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.VISIBILITY}/all` : null, fetcher)

    return {
        visibilities: data ? data.map(Visibility.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }

}