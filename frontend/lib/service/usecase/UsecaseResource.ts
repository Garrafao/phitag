import axios from "axios";
import useStorage from "../../hook/useStorage";
import UsecaseDto from "../../model/usecase/dto/UsecaseDto";
import useSWR from "swr";
import BACKENDROUTES from "../../BackendRoutes";
import Usecase from "../../model/usecase/model/Usecase";

export function useFetchAllUsecase(fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const fetcher = (url: string) => axios.get<UsecaseDto[]>(url, {
        headers: { "Authorization": `Bearer ${token}` }
    }).then(res => res.data)

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.USECASE}/all` : null, fetcher)

    return {
        usecases: data ? data.map(Usecase.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }
}