import axios from "axios"
import useSWR from "swr"

// Custom Hooks
import useStorage from "../../hook/useStorage"

import BACKENDROUTES from "../../BackendRoutes"

// Models
import StatusDto from "../../model/status/dto/StatusDto";
import Status from "../../model/status/model/Status";

/**
 * Fetch all statuses
 * @param fetch if true, fetch data from backend
 * @returns {statuses: Status[], isLoading: boolean, isError: boolean}
 */
export function useFetchAllStatus(fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<StatusDto[]>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.STATUS}/all` : null, fetcher)

    return {
        statuses: data ? data.map(Status.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }
}