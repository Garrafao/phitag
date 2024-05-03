import axios from "axios"
import useSWR from "swr"

// Custom Hooks
import useStorage from "../../hook/useStorage"

// Routes
import BACKENDROUTES from "../../BackendRoutes"

// Models
import UserDto from "../../model/user/dto/UserDto";
import UserDataDto from "../../model/user/dto/UserDataDto";
import User from "../../model/user/model/User";
import UserData from "../../model/user/model/UserData";
import CreateUserCommand from "../../model/user/command/CreateUserCommand";
import UpdateUserCommand from "../../model/user/command/UpdateUserCommand";
import CreateProlificUserCommand from "../../model/user/command/CreateProlificUserCommnad";

// Commands

// Custom hooks for fetching users and user related data

/**
 * Returns the users matching the given search term
 * 
 * @param query the query to search for
 * @param bot if computational bots should be searched for
 * @param fetch if data should be fetched
 * @returns list of users
 */
export function useQueryUsers(query: string, bot: boolean, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryUsersFetcher = (url: string) => axios.get<Array<UserDto>>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.USER}/query?query=${query}&bot=${bot}` : null, queryUsersFetcher)

    return {
        users: data ? data.map(User.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches the user with the given id
 * 
 * @param username username of the user to fetch
 * @param fetch if data should be fetched
 * @returns the user matching the given username
 */
export function useFetchUser(username: any, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const singleUserFetcher = (url: string) => axios.get<UserDto>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)


    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.USER}/find?username=${username}` : null, singleUserFetcher)
    return {
        user: data ? User.fromDto(data) : null,
        isLoading: username === undefined || !error && !data,
        isError: error && username !== undefined,
        mutate: mutate
    }
}

/**
 * Fetches the current users personal data
 * 
 * @param fetch if data should be fetched
 * @returns the current users personal data
 */ 
export function useFetchPersonalData(fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const personalDataFetcher = (url: string) => axios.get<UserDataDto>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.USER}/personal` : null, personalDataFetcher)

    return {
        user: data ? UserData.fromDto(data) : new UserData(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

// Custom functions for posting to user

/**
 * Creates a new user
 * 
 * @param user the user to create
 * @returns Promise 
 */
export async function createUser(user: CreateUserCommand) {
    return axios.post(`${BACKENDROUTES.USER}/create`, user).then(res => res.data);
}

/**
 * Creates a new user
 * 
 * @param user the user to create
 * @returns Promise 
 */
export async function createProlificUser(user: CreateProlificUserCommand) {
    return axios.post(`${BACKENDROUTES.USER}/create`, user).then(res => res.data);
}


/**
 * Updates the current users personal data
 *  
 * @param command the user to update
 * @param fetch get funxtion for storage
 * @returns Promise
 */
export async function updateUser(user: UpdateUserCommand, get: Function = () => {}) {
    const token = get('JWT') ?? '';
    
    return axios.post(`${BACKENDROUTES.USER}/update`, user, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data);
}