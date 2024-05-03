import axios from "axios";
import useSWR from "swr";
import fileDownload from "js-file-download";

// Custom Hooks
import useStorage from "../../hook/useStorage";

// Routes
import BACKENDROUTES from "../../BackendRoutes";

// Models
import UsageDto from "../../model/phitagdata/usage/dto/UsageDto";
import Usage from "../../model/phitagdata/usage/model/Usage";
import EditUsageCommand from "../../model/phitagdata/usage/command/EditUsageCommand";
import PagedUsageDto from "../../model/phitagdata/usage/dto/PagedUsageDto";
import PagedUsage from "../../model/phitagdata/usage/model/PagedUsage";


/**
 * Fetches all usages for a project
 * 
 * @param owner owner of the project
 * @param project project name
 * @param fetch if data should be fetched
 * @returns Usages associated with the project
 */
export function useFetchUsages(owner: string, project: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<UsageDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PHITAGDATA}/usage?owner=${owner}&project=${project}` : null, queryProjectFetcher)

    return {
        usages: data ? data.map(Usage.fromDto) : [],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches all usages for a project with pagination
 * 
 * @param owner owner of the project
 * @param project project name
 * @param page page number
 * @param fetch if data should be fetched
 * @returns Usages associated with the project
 */
export function useFetchUsagesWithPagination(owner: string, project: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryProjectFetcher = (url: string) => axios.get<PagedUsageDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.PHITAGDATA}/usage/paged?owner=${owner}&project=${project}&page=${page}` : null, queryProjectFetcher,
        {
            revalidateIfStale: false,
            revalidateOnFocus: false,
            revalidateOnReconnect: false
        });

    return {
        data: data ? PagedUsage.fromDto(data) : null as unknown as PagedUsage,
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}



/**
 * Exports all usages for a project as a csv file
 * 
 * @param owner owner of the project
 * @param project project name
 * @param get the get function from useStorage
 * @returns Usages associated with the project as a csv file
 */
export function exportUsage(owner: string, project: string, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.get(`${BACKENDROUTES.PHITAGDATA}/export?owner=${owner}&project=${project}`, {
        responseType: 'blob',
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => {
        // TODO: Dont rely on this extension, but for now ok
        fileDownload(res.data, 'usages.csv');
    });
}


/**
 * Adds new usages to a project
 * 
 * @param owner owner of the project
 * @param project project name
 * @param file file to upload containing usages as csv
 * @param get the get function from useStorage
 * @returns 
 */
export function addUsages(owner: string, project: string, file: File, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('owner', owner);
    formData.append('project', project);
    formData.append('file', file);

    return axios.post(`${BACKENDROUTES.PHITAGDATA}/usage`,
        formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(res => res.data);
}

/**
 * Edits an existing usage
 * 
 * @param command command containing the edit
 * @param get the get function from useStorage
 * @returns
 * 
 */
export function editUsage(command: EditUsageCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.PHITAGDATA}/usage/edit`,
        command,
        {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    ).then(res => res.data);
}