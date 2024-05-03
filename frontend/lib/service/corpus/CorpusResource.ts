import axios from "axios";
import useStorage from "../../hook/useStorage";

import BACKENDROUTES from "../../BackendRoutes";
import useSWR from "swr";
import PagedCorpusTextDto from "../../model/corpus/dto/PagedCorpusTextDto";
import PagedCorpusText from "../../model/corpus/model/PagedCorpusText";
import AddUsagesFromCorpusCommand from "../../model/corpus/command/AddUsagesFromCorpusCommand";

export function useFetchByQuery(
    lemma: string,
    pos: string,
    corpus: string,
    context: boolean,
    from: number,
    to: number,
    page: number,
    size: number,

    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryCorpus = (url: string) => axios.get<PagedCorpusTextDto>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error } = useSWR(fetch ?
        `${BACKENDROUTES.CORPUS}?lemma=${lemma}&pos=${pos}&corpus=${corpus.toLocaleLowerCase()}&context=${context}&from=${from}&to=${to}&page=${page}&size=${size}`
        : null, queryCorpus, {
        revalidateIfStale: false,
        revalidateOnFocus: false,
        revalidateOnReconnect: false
    });

    return {
        data: data ? PagedCorpusText.fromDto(data) : null as unknown as PagedCorpusText,
        isLoading: !error && !data,
        isError: error
    };


}

export function useFetchPossibleLemma(
    lemma: string,
    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryLemma = (url: string) => axios.get<string[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.CORPUS}/lemma?lemma=${lemma}` : null, queryLemma);

    return {
        data: data ? data : [] as unknown as string[],
        error
    };
}

export function useFetchPoSOfLemma(
    lemma: string,
    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryLemma = (url: string) => axios.get<string[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.CORPUS}/lemma/pos?lemma=${lemma}` : null, queryLemma);

    return {
        data: data ? data : [] as unknown as string[],
        error
    };
}

export function useFetchPossibleToken(
    lemma: string,
    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryLemma = (url: string) => axios.get<string[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.CORPUS}/lemma/token?lemma=${lemma}` : null, queryLemma);

    return {
        data: data ? data : [] as unknown as string[],
        error
    };
}

export function useFetchCorpusnamesShort(
    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryCorpusnames = (url: string) => axios.get<string[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.CORPUS}/corpus/names/short` : null, queryCorpusnames);

    return {
        data: data ? data : [] as unknown as string[],
        error
    };
}

export function addUsagesFromCorpusToProject(command: AddUsagesFromCorpusCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.CORPUS}/usage`, command, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);
}