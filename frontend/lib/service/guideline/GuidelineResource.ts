import axios from "axios";
import useSWR from "swr";

// Custom Hooks
import useStorage from "../../hook/useStorage";

// Routes
import BACKENDROUTES from "../../BackendRoutes";

// Models
import GuidelineDto from "../../model/guideline/dto/GuidelineDto";
import Guideline from "../../model/guideline/model/Guideline";

/**
 * Fetches a specific guideline for a project
 * 
 * @param owner owner of the project
 * @param project project name
 * @param guideline guideline name in the project
 * @param fetch if data should be fetched
 * @returns Guideline object
 */
export function useFetchGuideline(owner: string, project: string, guideline: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseFetcher = (url: string) => axios.get<GuidelineDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.GUIDELINE}?owner=${owner}&project=${project}&guideline=${guideline}` : null, queryPhaseFetcher)

    return {
        guideline: data ? Guideline.fromDto(data) : null,
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches all guidelines for a project
 * 
 * @param owner owner of the project
 * @param project project name
 * @param fetch if data should be fetched
 * @returns list of all guidelines
 */
export function useFetchGuidelines(owner: string, project: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryGuidelineFetcher = (url: string) => axios.get<GuidelineDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.GUIDELINE}/by-project?owner=${owner}&project=${project}` : null, queryGuidelineFetcher)

    return {
        guidelines: data ? data.map(Guideline.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Adds a guideline to a project
 * 
 * @param owner owner of the project
 * @param project project name
 * @param guideline guideline file
 * @param get function to get the JWT token
 * @returns Promise
 */
export function addGuideline(owner: string, project: string, guideline: File, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('owner', owner);
    formData.append('project', project);
    formData.append('guideline', guideline);


    return axios.post(`${BACKENDROUTES.GUIDELINE}/create`, formData, {
        headers: {
            "Authorization": `Bearer ${token}`,
            'Content-Type': 'multipart/form-data'
        }
    }).then(res => res.data)
}