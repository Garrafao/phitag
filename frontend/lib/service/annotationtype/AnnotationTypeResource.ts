import axios from "axios"
import useSWR from "swr"

// Custom Hooks
import useStorage from "../../hook/useStorage"

// Routes
import BACKENDROUTES from "../../BackendRoutes"

// Models
import AnnotationTypeDto from "../../model/annotationtype/dto/AnnotationTypeDto";
import AnnotationType from "../../model/annotationtype/model/AnnotationType";


// Custom hooks for fetching annotation types

/**
 * Returns all annotation types 
 * @param fetch if data should be fetched
 * @returns list of all annotation types
 */
export function useFetchAllAnnotationTypes(fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<AnnotationTypeDto[]>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)
    
    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.ANNOTATIONTYPE}` : null, fetcher)

    return {
        annotationTypes: data ? data.map(AnnotationType.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }
    
}