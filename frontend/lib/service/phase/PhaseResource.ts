import axios from "axios";
import useSWR from "swr";

// Custom Hooks
import useStorage from "../../hook/useStorage";

// Routes
import BACKENDROUTES from "../../BackendRoutes";

// Models
import PhaseDto from "../../model/phase/dto/PhaseDto";
import Phase from "../../model/phase/model/Phase";

// Commands
import CreatePhaseCommand from "../../model/phase/command/CreatePhaseCommand";
import AddRequirementsCommand from "../../model/phase/command/AddRequirementsCommand";
import StartComputationalAnnotationCommand from "../../model/phase/command/StartComputationalAnnotationCommand";
import { toast } from "react-toastify";
import TutorialHistoryDto from "../../model/tutorialhistory/dto/TutorialHistoryDto";
import TutorialHistory from "../../model/tutorialhistory/model/TutorialHistory";
import { error } from "console";

// Custom hooks for fetching phases

/**
 * Returns all phases based on the given query belonging to the given project
 * 
 * @param owner username of the owner of the project
 * @param project project name
 * @param annotationType annotation type of the phases
 * @param tutorial if tutorial phases should be fetched
 * @param fetch if data should be fetched
 * @returns list of all phases
 */
export function useFetchPhases(owner: string, project: string, annotationType: string = '', tutorial: boolean = false, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseFetcher = (url: string) => axios.get<PhaseDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PHASE}/by-project?owner=${owner}&project=${project}&annotation-type=${annotationType}&tutorial=${tutorial}` : null, queryPhaseFetcher)

    return {
        phases: data ? data.map(Phase.fromDto) : [] as Phase[],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }

}

/**
 * Fetches the phase with the given id
 * 
 * @param owner username of the owner of the project
 * @param project project name
 * @param phase phase name
 * @param fetch if data should be fetched
 * @returns phase
 */
export function useFetchPhase(owner: string, project: string, phase: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const phaseFetcher = (url: string) => axios.get<PhaseDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PHASE}/phase?owner=${owner}&project=${project}&phase=${phase}` : null, phaseFetcher)

    return {
        phase: data ? Phase.fromDto(data) : null,
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }

}

/** 
 * Checks if requesting user has access to annotate phase.
 * 
 * @param owner username of the owner of the project
 * @param project project name
 * @param phase phase name
 * @param fetch if data should be fetched
 * @returns true if user has access to annotate phase  
 */
export function useFetchAnnotationAccess(owner: string, project: string, phase: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const accessFetcher = (url: string) => axios.get<boolean>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data).catch(error => {
        toast.info("The phase is not ready for annotation yet. " + error.response.data.message);
        throw new Error(error);
    })

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PHASE}/has-access?owner=${owner}&project=${project}&phase=${phase}` : null, accessFetcher)

    return {
        hasAccess: data ? data : undefined,
        isLoading: !error,
        isError: !!error,
        mutate: mutate
    }
}

/**
 * Get tutorial measurement history.
 * 
 * @param owner username of the owner of the project
 * @param project project name
 * @param phase phase name
 * 
 * @returns list of tutorial history 
 */
export function useFetchTutorialMeasurementHistory(owner: string, project: string, phase: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const tutorialHistoryFetcher = (url: string) => axios.get<TutorialHistoryDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data).catch(error => {
        toast.info("There seems to be an error with the tutorial history. " + error.response.data.message);
    })

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PHASE}/tutorial/measurement-history?owner=${owner}&project=${project}&phase=${phase}` : null, tutorialHistoryFetcher)

    return {
        tutorialHistory: data ? data.map(TutorialHistory.fromDto) : [] as TutorialHistory[],
        isLoading: !error && !data,
        isError: !!error,
    }
}

// Custom functions for posting data to the backend

/**
 * Creates a new phase
 * 
 * @param command command to create a phase
 * @param get function to get data from local storage
 * @returns Promise
 */
export function createPhase(command: CreatePhaseCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.PHASE}/create`,
        command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Close a phase
 * 
 * @param owner username of the owner of the project
 * @param project project name
 * @param phase phase name
 * @param get function to get data from local storage
 * @returns Promise
 */
export function closePhase(owner: string, project: string, phase: string, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.PHASE}/close?owner=${owner}&project=${project}&phase=${phase}`,
        {},
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Add requirements to a phase
 * 
 * @param command command to add requirements to a phase
 * @param get function to get data from local storage
 * @returns Promise
 */
export function addRequirementsToPhase(command: AddRequirementsCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.PHASE}/add-requirements`,
        command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * delete requirements from a phase
 * 
 * @param owner owner of the project
 * @param project name of the project
 * @param phase name of the project
 * @param requirements name of the project
 * @param get function to get data from local storage
 * @returns Promise
 */
export function deleteRequirementsFromPhase(owner: string, project: string, phase: string, requirements: string, get: Function = () => { }) {
    const token = get('JWT') || '';
    return axios.post(
        `${BACKENDROUTES.PHASE}/delete-requirements?owner=${owner}&project=${project}&phase=${phase}&requirements=${requirements}`,
        {}, 
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
} 

export function startComputationalAnnotation(command: StartComputationalAnnotationCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.PHASE}/start-computational-annotation`,
        command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}




/**
 * Set code to phase
 * 
 * @param command command to set a phase
 * @param get function to get data from local storage
 * @returns Promise
 */
export function setCode(owner: string, project: string, phase: string, code: string, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(
        `${BACKENDROUTES.PHASE}/create/code?owner=${owner}&project=${project}&phase=${phase}`,
        code,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                // Set Content-Type to text/plain or remove this header
                "Content-Type": "text/plain",
            },
        }
    ).then(res => res.data)
    .catch((error) => {
        if (error.response) {
            toast.error('Error uploading code', error.response.status);
            toast.error('Response data:', error.response.data);
        } else if (error.request) {
            toast.error('No response received from the server');
        } else {
            toast.error('Error setting up the request:', error.message);
        }
        toast.error('Error config:', error.config);
    });
}
/**
 * delete  phase
 * 
 * @param owner owner of the project
 * @param project name of the project
 * @param phase name of the project
 * @param get function to get data from local storage
 * @returns Promise
 */
export function deletePhase(owner: string, project: string, phase: string, get: Function = () => { }) {

    const token = get('JWT') || '';
    return axios.post(
        `${BACKENDROUTES.PHASE}/delete?owner=${owner}&project=${project}&phase=${phase}`,
        {}, 
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
} 

