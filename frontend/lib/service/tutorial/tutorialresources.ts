
//Tutorials 

import axios from "axios";
import useStorage from "../../hook/useStorage";
import { IJudgement, IJudgementConstructor } from "../../model/judgement/model/IJudgement";
import IJudgementDto from "../../model/judgement/dto/IJudgementDto";
import useSWR from "swr";
import BACKENDROUTES from "../../BackendRoutes";
import PagedGenericDto from "../../model/interfaces/PagedGenericDto";
import UsePairJudgementDto from "../../model/judgement/usepairjudgement/dto/UsePairJudgementDto";
import PagedUsePairJudgement from "../../model/judgement/usepairjudgement/model/PagedUsePairJudgement";
import LexSubJudgementDto from "../../model/judgement/lexsubjudgement/dto/LexSubJudgementDto";
import PagedLexSubJudgement from "../../model/judgement/lexsubjudgement/model/PagedLexSubJudgement";
import UseRankJudgementDto from "../../model/judgement/userankjudgement/dto/UseRankJudgementDto";
import PagedUseRankJudgement from "../../model/judgement/userankjudgement/model/PagedUseRankJudgement";
import UseRankRelativeJudgementDto from "../../model/judgement/userankrelativejudgement/dto/UseRankRelativeJudgementDto";
import PagedUseRankRelativeJudgement from "../../model/judgement/userankrelativejudgement/model/PagedUseRankRelativeJudgement";
import UseRankPairJudgementDto from "../../model/judgement/userankpairjudgement/dto/UseRankPairJudgementDto";
import PagedUseRankPairJudgement from "../../model/judgement/userankpairjudgement/model/PagedUseRankPairJudgement";
import fileDownload from "js-file-download";
import WSSIMJudgementDto from "../../model/judgement/wssimjudgement/dto/WSSIMJudgementDto";
import PagedWSSIMJudgement from "../../model/judgement/wssimjudgement/model/PagedWSSIMJudgement";
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

/** 
 * Fetches all use pair tutorial judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryUsePairTutorialJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UsePairJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/tutorial/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUsePairJudgement.fromDto(data) : PagedUsePairJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/** 
 * Fetches all use pair tutorial judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryUseRankTutorialJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UseRankJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/tutorial/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUseRankJudgement.fromDto(data) : PagedUseRankJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/** 
 * Fetches all use pair tutorial judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryUseRankPairTutorialJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UseRankPairJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/tutorial/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUseRankPairJudgement.fromDto(data) : PagedUseRankPairJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}

/** 
 * Fetches all use pair tutorial judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryUseRankRelativeTutorialJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<UseRankRelativeJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/tutorial/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedUseRankRelativeJudgement.fromDto(data) : PagedUseRankRelativeJudgement.empty(),
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
export function useFetchPagedHistoryLexSubTutorialJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<LexSubJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/tutorial/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedLexSubJudgement.fromDto(data) : PagedLexSubJudgement.empty(),
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
export function useFetchPagedHistorySentimentTutorialJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<SentimentJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/tutorial/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedSentimentJudgement.fromDto(data) : PagedSentimentJudgement.empty(),
        isLoading: !error && !data,
        isError: error,
        mutate: mutate
    }
}
/**
 * Fetches all wssim of tutorial judgements of a user paged
 * 
 * @param owner owner of the project
 * @param project project name
 * @param phase phase name in the project
 * 
 * @param page page number
 * @param fetch if data should be fetched
 * @returns list of all judgements
 */
export function useFetchPagedHistoryWSSIMTutorialJudgements(owner: string, project: string, phase: string, page: number, fetch: boolean = true) {
    const { get } = useStorage();
    const token = get('JWT') ?? '';

    const queryPhaseDataFetcher = (url: string) => axios.get<PagedGenericDto<WSSIMJudgementDto>>(url, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => res.data);

    const { data, error, mutate } = useSWR(fetch ? `${BACKENDROUTES.JUDGEMENT}/tutorial/history/personal/paged?owner=${owner}&project=${project}&phase=${phase}&page=${page}` : null, queryPhaseDataFetcher)

    return {
        data: data ? PagedWSSIMJudgement.fromDto(data) : PagedWSSIMJudgement.empty(),
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
export function exportTutorialJudgement(owner: string, project: string, phase: string, get: Function = () => { }) {
    const token = get('JWT') ?? '';

    return axios.get(`${BACKENDROUTES.JUDGEMENT}/export/tutorial/?owner=${owner}&project=${project}&phase=${phase}`, {
        responseType: 'blob',
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(res => {
        fileDownload(res.data, 'results.csv');
    });
}





