import axios from "axios";
import useSWR from "swr";
import fileDownload from "js-file-download";

// Custom Hooks
import useStorage from "../../hook/useStorage";

// Routes
import BACKENDROUTES from "../../BackendRoutes";

// Interfaces
import IJudgementDto from "../../model/judgement/dto/IJudgementDto";
import IAddJudgementCommand from "../../model/judgement/command/IAddJudgementCommand";
import { IJudgement, IJudgementConstructor } from "../../model/judgement/model/IJudgement";
import IEditJudgementCommand from "../../model/judgement/command/IEditJudgementCommand";
import IDeleteJudgementCommand from "../../model/judgement/command/IDeleteJudgementCommand";
import PagedGenericDto from "../../model/interfaces/PagedGenericDto";
import UsePairJudgement from "../../model/judgement/usepairjudgement/model/UsePairJudgement";
import PagedUsePairJudgement from "../../model/judgement/usepairjudgement/model/PagedUsePairJudgement";
import WSSIMJudgementDto from "../../model/judgement/wssimjudgement/dto/WSSIMJudgementDto";
import PagedWSSIMJudgement from "../../model/judgement/wssimjudgement/model/PagedWSSIMJudgement";
import UsePairJudgementDto from "../../model/judgement/usepairjudgement/dto/UsePairJudgementDto";
import LexSubJudgementDto from "../../model/judgement/lexsubjudgement/dto/LexSubJudgementDto";
import PagedLexSubJudgement from "../../model/judgement/lexsubjudgement/model/PagedLexSubJudgement";
import UseRankJudgementDto from "../../model/judgement/userankjudgement/dto/UseRankJudgementDto";
import PagedUseRankJudgement from "../../model/judgement/userankjudgement/model/PagedUseRankJudgement";
import UseRankRelativeJudgementDto from "../../model/judgement/userankrelativejudgement/dto/UseRankRelativeJudgementDto";
import PagedUseRankRelativeJudgement from "../../model/judgement/userankrelativejudgement/model/PagedUseRankRelativeJudgement";
import UseRankPairJudgementDto from "../../model/judgement/userankpairjudgement/dto/UseRankPairJudgementDto";
import PagedUseRankPairJudgement from "../../model/judgement/userankpairjudgement/model/PagedUseRankPairJudgement";
import SentimentJudgementDto from "../../model/judgement/sentiment/dto/SentimentJudgementDto";
import PagedSentimentJudgement from "../../model/judgement/sentiment/model/PagedSentimentJudgement";

/** 
 * Fetches all judgements of a phase
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param constructor data class to be converted to (implements fromDto, i.e. the convertion function)
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchJudgements<G extends IJudgement, T extends IJudgementConstructor>(owner: string, project: string, phase: string, constructor: T, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<IJudgementDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}?owner=${owner}&project=${project}&phase=${phase}` : null, queryPhaseDataFetcher)

    return {
        data: data ? data.map(constructor.fromDto) : [] as G[],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/** 
 * Fetches all use pair judgements of a phase paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedUsePairJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UsePairJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUsePairJudgement.fromDto(data) : PagedUsePairJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}
/** 
 * Fetches all use pair judgements of a phase paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedUsePairJudgementsByAnnotator(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UsePairJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/count/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUsePairJudgement.fromDto(data) : PagedUsePairJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/** 
 * Fetches all use rank judgements of a phase paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedUseRankJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {

    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UseRankJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUseRankJudgement.fromDto(data) : PagedUseRankJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}
/** 
 * Fetches all use rank relative judgements of a phase paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedUseRankRelativeJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {

    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UseRankRelativeJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUseRankRelativeJudgement.fromDto(data) : PagedUseRankRelativeJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/** 
 * Fetches all use rank relative judgements of a phase paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedUseRankPairJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {

    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UseRankPairJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUseRankPairJudgement.fromDto(data) : PagedUseRankPairJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/** 
 * Fetches all wssim judgements of a phase paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedWSSIMJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<WSSIMJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedWSSIMJudgement.fromDto(data) : PagedWSSIMJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetch all lexsub judgements of a phase paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedLexSubJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<LexSubJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedLexSubJudgement.fromDto(data) : PagedLexSubJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetch all sentiment judgements of a phase paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedSentimentJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<SentimentJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedSentimentJudgement.fromDto(data) : PagedSentimentJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches the judgement history of the user of a phase
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param constructor data class to be converted to (implements fromDto, i.e. the convertion function)
 * @param fetch if data should be fetched
 * @returns 
 */
export function useFetchHistory<G extends IJudgement, T extends IJudgementConstructor>(owner: string, project: string, phase: string, constructor: T, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<IJudgementDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/history/personal?owner=${owner}&project=${project}&phase=${phase}` : null, queryPhaseDataFetcher)

    return {
        data: data ? data.map(constructor.fromDto) : [] as G[],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }

}


/** 
 * Fetches all use pair judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryUsePairJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UsePairJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUsePairJudgement.fromDto(data) : PagedUsePairJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}


/** 
 * Fetches all use rank judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryUseRankJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UseRankJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUseRankJudgement.fromDto(data) : PagedUseRankJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}
/** 
 * Fetches all use rank relative judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryUseRankRelativeJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UseRankRelativeJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUseRankRelativeJudgement.fromDto(data) : PagedUseRankRelativeJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/** 
 * Fetches all use rank pair judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryUseRankPairJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UseRankPairJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUseRankPairJudgement.fromDto(data) : PagedUseRankPairJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}


/** 
 * Fetches all wssim judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryWSSIMJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<WSSIMJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedWSSIMJudgement.fromDto(data) : PagedWSSIMJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches all lexsub judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * 
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryLexSubJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<LexSubJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedLexSubJudgement.fromDto(data) : PagedLexSubJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/**
 * Fetches all sentiment judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * 
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistorySentimentJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<SentimentJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedSentimentJudgement.fromDto(data) : PagedSentimentJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}


/**
 * Fetches all judgements of a phase as a csv file
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param get storage hook
 * @returns a csv file of the judgements of the phase
 */
export function exportJudgement(owner: string, project: string, phase: string, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.get(`${BACKENDROUTES.JUDGEMENT}/export?owner=${owner}&project=${project}&phase=${phase}`, {
        responseType: 'blob',
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => {
        fileDownload(res.data, 'results.csv');
    });
}

/**
 * Adds judgements to the phase as a csv file 
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param file file to be uploaded
 * @param get storage hook
 * @returns Promise
 */
export function addJudgement(owner: string, project: string, phase: string, file: File, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('owner', owner);
    formData.append('project', project);
    formData.append('phase', phase);
    formData.append('file', file);

    return axios.post(`${BACKENDROUTES.JUDGEMENT}`,
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
 * Edit use pair judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 */
export function editUsepair(command: IEditJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/edit/usepair`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}


/**
 * Edit use rank judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 */
export function editUserank(command: IEditJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/edit/userank`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Edit use rank relative judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 */
export function editUserankrelative(command: IEditJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/edit/userankrelative`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Edit use rank pair judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 */
export function editUserankpair(command: IEditJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/edit/userankpair`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}




/**
 * Edit WSSIM judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 * 
 * @returns Promise
 */
export function editWssim(command: IEditJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/edit/wssim`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Edit LexSub judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 * 
 * @returns Promise
 */
export function editLexSub(command: IEditJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/edit/lexsub`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Edit Sentiment judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 * 
 * @returns Promise
 */
export function editSentiment(command: IEditJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/edit/sentiment`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}


/**
 * Delete Use Pair judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 */
export function deleteUsepair(command: IDeleteJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/delete/usepair`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Delete Use Rank judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 */
export function deleteUserank(command: IDeleteJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/delete/userank`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Delete Use Rank Realtive judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 */
export function deleteUserankrealtive(command: IDeleteJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/delete/userankrelative`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}


/**
 * Delete Use Rank Pair judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 */
export function deleteUserankpair(command: IDeleteJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/delete/userankpair`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}




/**
 * Delete WSSIM judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 */
export function deleteWssim(command: IDeleteJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/delete/wssim`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Delete LexSub judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 * 
 * @returns Promise
 */
export function deleteLexSub(command: IDeleteJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/delete/lexsub`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Delete LexSub judgement
 * 
 * @param command command containing the judgement
 * @param get storage hook
 * 
 * @returns Promise
 */
export function deleteSentiment(command: IDeleteJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/delete/sentiment`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}



/** 
 * Add a judgement to the phase (i.e. annotate an instance of a phase)
 * 
 * @param command command containing the judgement
 * @returns Promise
 */
export function annotateUsepair(command: IAddJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/usepair`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);

}

/**
 * Add a bulk of judgements to the phase (i.e. annotate instances of a phase)
 */
export function bulkAnnotateUsepair(commands: IAddJudgementCommand[], get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/usepair/bulk`, commands,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/** 
 * Add a judgement to the phase (i.e. annotate an instance of a phase)
 * 
 * @param command command containing the judgement
 * @returns Promise
 */
export function annotateUserank(command: IAddJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/userank`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);

}

/**
 * Add a bulk of judgements to the phase (i.e. annotate instances of a phase)
 */
export function bulkAnnotateUserank(commands: IAddJudgementCommand[], get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/userank/bulk`, commands,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/** 
 * Add a judgement to the phase (i.e. annotate an instance of a phase)
 * 
 * @param command command containing the judgement
 * @returns Promise
 */
export function annotateUserankrelative(command: IAddJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/userankrelative`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);

}

/**
 * Add a bulk of judgements to the phase (i.e. annotate instances of a phase)
 */
export function bulkAnnotateUserankrelative(commands: IAddJudgementCommand[], get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/userankrelative/bulk`, commands,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/** 
 * Add a judgement to the phase (i.e. annotate an instance of a phase)
 * 
 * @param command command containing the judgement
 * @returns Promise
 */
export function annotateUserankpair(command: IAddJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/userankpair`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);

}

/**
 * Add a bulk of judgements to the phase (i.e. annotate instances of a phase)
 */
export function bulkAnnotateUserankpair(commands: IAddJudgementCommand[], get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/userankpair/bulk`, commands,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}




/**
 * Add a judgement to the phase where Task is WSSIM
 * 
 * @param command command containing the judgement
 * @returns Promise
 */
export function annotateWSSIM(command: IAddJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/wssim`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Add a bulk of judgements to phase of task type WSSIM
 */
export function bulkAnnotateWSSIM(commands: IAddJudgementCommand[], get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/wssim/bulk`, commands,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Add a judgement to the phase where Task is LexSub
 * 
 * @param command command containing the judgement
 * @returns Promise
 * 
 * @returns Promise
 */
export function annotateLexSub(command: IAddJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/lexsub`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Add a judgement to the phase where Task is Sentiment
 * 
 * @param command command containing the judgement
 * @returns Promise
 * 
 * @returns Promise
 */
export function annotateSentiment(command: IAddJudgementCommand, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/sentiment`, command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Add a bulk of judgements to phase of task type LexSub
 * 
 * @param commands commands containing the judgements
 * @param get storage hook
 * 
 * @returns Promise
 */
export function bulkAnnotateLexSub(commands: IAddJudgementCommand[], get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/lexsub/bulk`, commands,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/**
 * Add a bulk of judgements to phase of task type Sentiment
 * 
 * @param commands commands containing the judgements
 * @param get storage hook
 * 
 * @returns Promise
 */
export function bulkAnnotateSentiment(commands: IAddJudgementCommand[], get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.post(`${BACKENDROUTES.JUDGEMENT}/annotate/sentiment/bulk`, commands,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    ).then(res => res.data);
}

/** 
 * Count all attempted judgements of a phase
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns l
 */
export function useFetchAttemptedJudgement(owner: string, project: string, phase: string, fetch: boolean = true) {
    const queryPhaseDataFetcher = async (url: string) => {
        try {
            const token = localStorage.getItem('JWT') ?? '';
            const response = await axios.get(url, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error("Error fetching data:", error);
            throw error;
        }
    };

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/count?owner=${owner}&project=${project}&phase=${phase}` : null, queryPhaseDataFetcher);

    return {
        data: data,
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    };
}

//Tutorials 

/** 
 * Fetches all judgements of a phase
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param constructor data class to be converted to (implements fromDto, i.e. the convertion function)
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchTutorialJudgements<G extends IJudgement, T extends IJudgementConstructor>(owner: string, project: string, phase: string, constructor: T, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<IJudgementDto[]>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data)

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}?owner=${owner}&project=${project}&phase=${phase}` : null, queryPhaseDataFetcher)

    return {
        data: data ? data.map(constructor.fromDto) : [] as G[],
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/** 
 * Fetches all use pair tutorial judgements of a phase paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedUsePairTutorialJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UsePairJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/tutorialpaged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUsePairJudgement.fromDto(data) : PagedUsePairJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}



