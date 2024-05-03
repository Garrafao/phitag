import axios from "axios";
import useSWR from "swr";

// Custom Hooks
import useStorage from "../../hook/useStorage";

// Routes
import BACKENDROUTES from "../../BackendRoutes";

// Models
import AnnotatorDto from "../../model/annotator/dto/AnnotatorDto";
import Annotator from "../../model/annotator/model/Annotator";

// Commands
import CreateAnnotatorCommand from "../../model/annotator/command/CreateAnnotatorCommand";
import RemoveAnnotatorCommand from "../../model/annotator/command/RemoveAnnotatorCommand";
import EditAnnotatorCommand from "../../model/annotator/command/EditAnnotatorCommand";

// Custom hooks for fetching annotators and annotator related data

/**
 * Fetches all annotators of a project
 * 
 * @param owner username of the owner of the project
 * @param project project name
 * @param fetch if data should be fetched
 * @returns a list of annotators for the given project
 */
export function useFetchAnnotatorsByProject(owner: string, project: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<Array<AnnotatorDto>>(url,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.ANNOTATOR}/by-project?owner=${owner}&project=${project}` : null, fetcher);

    return {
        annotators: data ? data.map(Annotator.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}


/**
 * Returns the Entitlement of the requesting user for the given project.
 * 
 * @param authenticationToken the authentication token of the requesting user
 * @param owner               the owner of the project
 * @param project             the name of the project
 * @return a list of all {@AnnotatorDto}
 */
export function useFetchComputationAnnotatorsOfPhase(owner: string, project: string, phase: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<Array<AnnotatorDto>>(url,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.ANNOTATOR}/computational/by-phase?owner=${owner}&project=${project}&phase=${phase}` : null, fetcher);

    return {
        annotators: data ? data.map(Annotator.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}


/**
 * Fetch the annotators entitlement for a project
 * 
 * @param owner username of the owner of the project
 * @param project project name
 * @param fetch if data should be fetched
 * @returns the annotators entitlement for the given project as string
 */
export function useFetchSelfEntitlement(owner: string, project: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<string>(url,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.ANNOTATOR}/entitlement?owner=${owner}&project=${project}` : null, fetcher);

    return {
        entitlement: data ?? '',
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

// Custom functions for posting, editing and deleting annotators

/**
 * Creates a new annotator for a project
 * 
 * @param annotator annotator to be created
 * @param get callback function to get the auth token
 * @returns Promise  
 */
export function addAnnotator(annotator: CreateAnnotatorCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.ANNOTATOR}/add`,
        annotator,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Removes an annotator from a project
 * NOTE: This is currently not supported by the backend
 * 
 * @param annotator annotator to be removed
 * @param get callback function to get the auth token 
 * @returns Promise
 */
export function removeAnnotator(annotator: RemoveAnnotatorCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.ANNOTATOR}/delete`,
        annotator,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

export function updateAnnotator(annotator: EditAnnotatorCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.ANNOTATOR}/update`,
        annotator,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}
