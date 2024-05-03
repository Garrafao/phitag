import axios from "axios";
import useSWR from "swr";
import BACKENDROUTES from "../../../BackendRoutes";
import useStorage from "../../../hook/useStorage";
import UserStatisticDto from "../../../model/statistic/userstatistic/dto/UserStatisticDto";
import UserStatistic from "../../../model/statistic/userstatistic/model/UserStatistic";

export default function useFetchUserStatistic(user: string, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const userStatisticFetcher = (url: string) => axios.get<UserStatisticDto>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.USERSTATISTIC}?user=${user}` : null, userStatisticFetcher)

    return {
        statistic: data ? UserStatistic.fromDto(data) : null as unknown as UserStatistic,
        isLoading: !error && !data,
        isError: error,
        mutate
    }

}