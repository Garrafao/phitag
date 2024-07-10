import axios from "axios";
import AddStaticHistoryCommand from "../../model/stataticsistory/command/AddStaticHistoryCommand";
import BACKENDROUTES from "../../BackendRoutes";
import useStorage from "../../hook/useStorage";
import AnnotationStaticHistoryDto from "../../model/stataticsistory/dto/AnnotatorStaticHistoryDto";
import useSWR from "swr";
import AnnotationHistoryTable from "../../model/stataticsistory/model/AnnotatorHistoryTable";

export function saveAnnotation(command: AddStaticHistoryCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.STATHISTORY}/save`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);

}


export function useFetchStatHistory(annotatorname: string, ownername: string, projectname: string, phasename: string ,fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseFetcher = (url: string) => axios.get<AnnotationStaticHistoryDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.STATHISTORY}/getAll?annotatorname=${annotatorname}&ownername=${ownername}&projectname=${projectname}&phasename=${phasename}` : null, queryPhaseFetcher)

    return {
        res: data ? data.map(AnnotationHistoryTable.fromDto) : [] as AnnotationHistoryTable[],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }

}