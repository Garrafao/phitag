import axios from "axios"
import useSWR from "swr"

// Routes
import BACKENDROUTES from "../../BackendRoutes"

// Models
import LanguageDto from "../../model/language/dto/LanguageDto"
import Language from "../../model/language/model/Language"

// Custom hooks for fetching languages

/**
 * Returns all languages
 * 
 * @param fetch if data should be fetched
 * @returns list of all languages
 */
export function useFetchAllLanguages(fetch: boolean = true) {

    const fetcher = (url: string) => axios.get<LanguageDto[]>(url).then(res => res.data)

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.LANGUAGE}` : null, fetcher)

    return {
        languages: data ? data.map(Language.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }

}