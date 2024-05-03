import axios from "axios";
import useSWR from "swr";
import BACKENDROUTES from "../../BackendRoutes";
import useStorage from "../../hook/useStorage";
import PagedReportDto from "../../model/report/dto/PagedReportDto";
import PagedReport from "../../model/report/model/PagedReport";

export function useFetchReport(
    user: string,
    status: string,
    page: number,

    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryReport = (url: string) => axios.get<PagedReportDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ?
        `${BACKENDROUTES.REPORT}/find?user=${user}&status=${status}&page=${page}`
        : null, queryReport, {
        revalidateIfStale: false,
        revalidateOnFocus: false,
        revalidateOnReconnect: false
    });

    return {
        data: data ? PagedReport.fromDto(data) : null as unknown as PagedReport,
        isLoading: !error && !data,
        isError: error,
        mutate: mutate,
    };
}

export function useFetchReportOfSelf(
    status: string,
    page: number,

    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryReportUser = (url: string) => axios.get<PagedReportDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error } = useSWR(fetch ?
        `${BACKENDROUTES.REPORT}/find/user?&status=${status}&page=${page}`
        : null, queryReportUser, {
        revalidateIfStale: false,
        revalidateOnFocus: false,
        revalidateOnReconnect: false
    });

    return {
        data: data ? PagedReport.fromDto(data) : null as unknown as PagedReport,
        isLoading: !error && !data,
        isError: error
    };
}

export function addReport(
    description: string,

    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.REPORT}/add?description=${description}`, {}, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);
}

export function updateReport(
    id: string,
    description: string,
    status: string,

    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.REPORT}/update`, 
    {
        id: id, 
        description: description,
        status: status

    }, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);
}
