import axios from "axios";
import useSWR from "swr";

import BACKENDROUTES from "../../BackendRoutes";
import useStorage from "../../hook/useStorage";

// Models
import Project from "../../model/project/model/Project";
import ProjectDto from "../../model/project/dto/ProjectDto";

// Commands
import CreateProjectCommand from "../../model/project/command/CreateProjectCommand";
import UpdateProjectCommand from "../../model/project/command/UpdateProjectCommand";

// Custom hooks for fetching projects and project related data

/**
 * Fetches all projects matching the given search query
 * 
 * @param query query to search for
 * @param fetch if data should be fetched
 * @returns list of projects matching the query
 */
export function useQueryProjects(query: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<Array<ProjectDto>>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PROJECT}/query?query=${query}` : null, queryProjectFetcher)

    return {
        projects: data ? data.map(Project.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches all projects of a user
 * 
 * @param owner username of the owner of the projects
 * @param fetch if data should be fetched
 * @returns list of projects of the given user
 */
export function useFetchProjectsOfUser(owner: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<Array<ProjectDto>>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PROJECT}/user?owner=${owner}` : null, queryProjectFetcher)

    return {
        projects: data ? data.map(Project.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches a projects matching its id
 * 
 * @param owner username of the owner of the project
 * @param project project name
 * @param fetch if data should be fetched
 * @returns the project matching the given id
 */
export function useFetchProject(owner: string, project: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<ProjectDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PROJECT}/find?owner=${owner}&project=${project}` : null, queryProjectFetcher)

    return {
        project: data ? Project.fromDto(data) : null as unknown as Project,
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches all projects of the current user where the user is owner
 * 
 * @param query query to search for
 * @param fetch if data should be fetched
 * @returns list of projects matching the query of the current user
 */
export function useFetchPersonalProject(query: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<ProjectDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PROJECT}/personal?query=${query}` : null, queryProjectFetcher)
    return {
        projects: data ? data.map(Project.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}


/**
 * Fetches all projects of the current user where the user is a annotator
 * 
 * @param query query to search for
 * @param fetch if data should be fetched
 * @returns list of projects matching the query of the current user
 */
export function useFetchMemberProject(query: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<ProjectDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PROJECT}/annotator?query=${query}` : null, queryProjectFetcher)
    
    return {
        projects: data ? data.map(Project.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

// Custom functions for creating, editing and removing projects

/**
 * Creates a new project
 * 
 * @param project project to create
 * @param get function to get data from local storage
 * @returns 
 */
export function createProject(project: CreateProjectCommand, get: Function = () => {}) {
    const token = get('JWT') ?? '';
    
    return axios.post(`${BACKENDROUTES.PROJECT}/create`,
        project,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Deletes a project
 * 
 * @param project project to delete
 * @param get function to get data from local storage
 * @returns 
 */
export function deleteProject(project: string, get: Function = () => {}) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.PROJECT}/delete?project=${project}`, {},
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}


/**
 * Update project
 * 
 * @param project project to create
 * @param get function to get data from local storage
 * @returns 
 */
export async function UpdateProject(owner: string, project: string,  editProject:UpdateProjectCommand, get: Function = () => {}) {
    const token = get('JWT') ?? '';
    
    const res = await axios.post(`${BACKENDROUTES.PROJECT}/update?owner=${owner}&project=${project}`,
        editProject,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    );
    return res.data;
}
