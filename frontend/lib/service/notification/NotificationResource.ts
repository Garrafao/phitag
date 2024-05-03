import axios from "axios";
import useSWR from "swr";
import BACKENDROUTES from "../../BackendRoutes";
import useStorage from "../../hook/useStorage";
import PagedNotificationDto from "../../model/notfication/dto/PagedNotificationDto";
import PagedNotification from "../../model/notfication/model/PagedNotification";

export function useFetchNotifications(
    page: number,
    onlyunread: boolean = false,
    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryNotification = (url: string) => axios.get<PagedNotificationDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ?
        `${BACKENDROUTES.NOTIFICATION}?page=${page}&onlyunread=${onlyunread}`
        : null, queryNotification, {
        revalidateIfStale: false,
        revalidateOnFocus: false,
        revalidateOnReconnect: false
    });

    return {
        data: data ? PagedNotification.fromDto(data) : null as unknown as PagedNotification,
        isLoading: !error && !data,
        isError: error,
        mutate: mutate,
    };

}

export function useFetchUnreadNotifications(
    page: number,
    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryNotification = (url: string) => axios.get<PagedNotificationDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ?
        `${BACKENDROUTES.NOTIFICATION}/unread?page=${page}`
        : null, queryNotification, {
        revalidateIfStale: false,
        revalidateOnFocus: false,
        revalidateOnReconnect: false
    });

    return {
        data: data ? PagedNotification.fromDto(data) : null as unknown as PagedNotification,
        isLoading: !error && !data,
        isError: error,
        mutate: mutate,
    };
    
}

export function markAsRead(
    notificationIds: number[],
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.NOTIFICATION}/read`, 
    {
        notificationIds: notificationIds
    }, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);
}