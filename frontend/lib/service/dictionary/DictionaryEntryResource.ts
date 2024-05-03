import axios from "axios";
import DictionaryEntryDto from "../../model/dictionary/entry/dto/DictionaryEntryDto";
import PagedGenericDto from "../../model/interfaces/PagedGenericDto";
import useStorage from "../../hook/useStorage";
import BACKENDROUTES from "../../BackendRoutes";
import useSWR from "swr";
import PagedDictionaryEntry from "../../model/dictionary/entry/model/PagedDictionaryEntry";

export function useFetchDictionaryEntries(
    dname: string,
    uname: string,
    headword: string,
    pos: string,
    page: number = 0,
    fetch: boolean = true
) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const dictionaryEntryDataFetcher = (url: string) => axios.get<PagedGenericDto<DictionaryEntryDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const fetchURL = `${BACKENDROUTES.DICTIONARY}/entry?dname=${dname}&uname=${uname}&headword=${headword}&pos=${pos}&page=${page}`;

    const { data, error, mutate } = useSWR(fetch ? fetchURL : null, dictionaryEntryDataFetcher);

    return {
        data: data ? PagedDictionaryEntry.fromDto(data) : PagedDictionaryEntry.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate
    }
}

export function createDictionaryEntry(
    dname: string,
    uname: string,
    headword: string,
    pos: string,
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('dname', dname);
    formData.append('uname', uname);
    formData.append('headword', headword);
    formData.append('partofspeech', pos);

    return axios.post(`${BACKENDROUTES.DICTIONARY}/entry/create`, formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(res => res.data);

}

export function updateDictionaryEntry(
    id: string,
    dname: string,
    uname: string,
    headword: string,
    pos: string,
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('id', id);
    formData.append('dname', dname);
    formData.append('uname', uname);
    formData.append('headword', headword);
    formData.append('partofspeech', pos);

    return axios.post(`${BACKENDROUTES.DICTIONARY}/entry/update`, formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(res => res.data);

}

export function deleteDictionaryEntry(
    id: string,
    dname: string,
    uname: string,
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('id', id);
    formData.append('dname', dname);
    formData.append('uname', uname);

    return axios.post(`${BACKENDROUTES.DICTIONARY}/entry/delete`, formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(res => res.data);

}