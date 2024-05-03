import axios from "axios";
import useSWR from "swr";

// Custom Hooks
import useStorage from "../../hook/useStorage";

// Routes
import BACKENDROUTES from "../../BackendRoutes";

// Models
import JoblistingDto from "../../model/joblisting/dto/JoblistingDto";
import PersonalJoblistingDto from "../../model/joblisting/dto/PersonalJoblistingDto";
import Joblisting from "../../model/joblisting/model/Joblisting";
import PersonalJoblisting from "../../model/joblisting/model/PersonalJoblisting";

// Commands
import CreateJoblistingCommand from "../../model/joblisting/command/CreateJoblistingCommand";
import JoinJoblistingCommand from "../../model/joblisting/command/JoinJoblistingCommand";
import AddUsersFromWaitinglistCommand from "../../model/joblisting/command/AddUsersFromWaitinglistCommand";

// custom hooks for fetching joblistings

/**
 * Returns the joblistings for a given query
 * 
 * @param query the query to search for
 * @param open  if open or closed joblistings should be returned
 * @param fetch  if data should be fetched
 * @returns  list of joblistings
 */
export function useQueryJoblisting(query: string, open: boolean = true, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryFetcher = (url: string) => axios.get<JoblistingDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)
    
    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JOBLISTING}/query?query=${query}&open=${open}` : null, queryFetcher)

    return {
        data: data ? data.map(Joblisting.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches all personal joblistings of a user
 * 
 * @param query the query to search for
 * @param open if open or closed joblistings should be returned
 * @param active if active or inactive joblistings should be returned
 * @param fetch if data should be fetched
 * @returns list of joblistings
 */
export function useFetchPersonalJobListing(query: string, open: boolean = true, active: boolean = true, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryFetcher = (url: string) => axios.get<PersonalJoblistingDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JOBLISTING}/personal?query=${query}&open=${open}&active=${active}` : null, queryFetcher)

    return {
        data: data ? data.map(PersonalJoblisting.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

// Custom functions for posting to joblisting

/**
 * Posts a new joblisting
 * 
 * @param joblisting the joblisting to post
 * @param get the get function from useStorage
 * @returns Promise
 */
export async function postNewJoblisting(joblisting: CreateJoblistingCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JOBLISTING}/create`, joblisting, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);
}

/**
 * Join a joblisting
 * 
 * @param command the join command
 * @param get the get function from useStorage
 * @returns Promise
 */
export function joinJoblisting(command: JoinJoblistingCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JOBLISTING}/join`, command, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);
}

/**
 * Add users from waitinglist to joblisting
 * 
 * @param command the add command
 * @param get the get function from useStorage
 * @returns Promise
 */
export function addUsersFromWaitinglist(command: AddUsersFromWaitinglistCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JOBLISTING}/add`, command, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);
}