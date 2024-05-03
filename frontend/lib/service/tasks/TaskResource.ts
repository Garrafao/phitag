import axios from "axios";
import useSWR from "swr";

import BACKENDROUTES from "../../BackendRoutes";
import useStorage from "../../hook/useStorage";

// Models
import TaskDto from "../../model/tasks/dto/TaskDto";
import Task from "../../model/tasks/model/Task";

/**
 * Fetch all tasks of a project
 * @param owner owner of the project
 * @param project project name
 * @param fetch if true, fetch data from backend
 * @returns {tasks: Task[], isLoading: boolean, isError: boolean}
 */
export function useFetchTasksOfProject(owner: string, project: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<TaskDto[]>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.TASK}/by-project?owner=${owner}&project=${project}` : null, fetcher)

    return {
        tasks: data ? data.map(Task.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetch all tasks of a phase
 * @param owner owner of the project
 * @param project  project name
 * @param phase phase name of the project
 * @param fetch  if true, fetch data from backend
 * @returns {tasks: Task[], isLoading: boolean, isError: boolean}
 */
export function useFetchTasksOfPhase(owner: string, project: string, phase: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<TaskDto[]>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.TASK}/by-phase?owner=${owner}&project=${project}&phase=${phase}` : null, fetcher)

    return {
        tasks: data ? data.map(Task.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}