import axios from "axios"
import useSWR from "swr"

// Custom Hooks
import useStorage from "../../hook/useStorage"

// Routes
import BACKENDROUTES from "../../BackendRoutes"

// Models
import Entitlement from "../../model/entitlement/model/Entitlement";
import EntitlementDto from "../../model/entitlement/dto/EntitlementDto";

// Custom hooks for fetching entitlements

/**
 * Returns the entitlements 
 * 
 * @param fetch if data should be fetched
 * @returns list of all entitlements
 */
export function useFetchAllEntitlements(fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<EntitlementDto[]>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.ENTITLEMENT}` : null, fetcher)

    return {
        entitlements: data ? data.map(Entitlement.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }

}