import axios from "axios";
import useStorage from "../../hook/useStorage";
import BACKENDROUTES from "../../BackendRoutes";
import useSWR from "swr";
import OpenAIModelDto from "../../model/computationalannotator/openaimodel/dto/OpenAIModelDto";
import OpenAIModel from "../../model/computationalannotator/openaimodel/model/OpenAIModel";
import ComputationalAnnotatorCommand from "../../model/computationalannotator/ComputationalAnnotatorCommand";
import { data } from "autoprefixer";
import UsePairTutorialData from "../../model/computationalannotator/UsePairTutorailData";


export async function chatGptUsePairAnnotation(command: ComputationalAnnotatorCommand, get: Function = () => {}) {

    const token = get('JWT') ?? '';
    
    const res = await axios.post(`${BACKENDROUTES.COMPUTATIONALANNOTATOR}/use-pair-annotate`,
        command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    );
    return res.data;
}


export async function chatGptLexsubAnnotation(command: ComputationalAnnotatorCommand, get: Function = () => {}) {

    const token = get('JWT') ?? '';
    
    const res = await axios.post(`${BACKENDROUTES.COMPUTATIONALANNOTATOR}/lexsub-annotate`,
        command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    );
    return res.data;
}

export async function chatWSSIMAnnotation(command: ComputationalAnnotatorCommand, get: Function = () => {}) {

    const token = get('JWT') ?? '';
    
    const res = await axios.post(`${BACKENDROUTES.COMPUTATIONALANNOTATOR}/wssim-annotate`,
        command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    );
    return res.data;
}


export async function chatUsePairTutorialAnnotation(command: ComputationalAnnotatorCommand, get: Function = () => {}) {

    const token = get('JWT') ?? '';
    
    const res = await axios.post(`${BACKENDROUTES.COMPUTATIONALANNOTATOR}/use-pair-tutorial-annotation`,
        command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    );
    return res.data;
}
export async function chatWSSIMTutorialAnnotation(command: ComputationalAnnotatorCommand, get: Function = () => {}) {

    const token = get('JWT') ?? '';
    
    const res = await axios.post(`${BACKENDROUTES.COMPUTATIONALANNOTATOR}/wssim-tutorial-annotation`,
        command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    );
    return res.data;
}

export async function chatLexSubTutorialAnnotation(command: ComputationalAnnotatorCommand, get: Function = () => {}) {

    const token = get('JWT') ?? '';
    
    const res = await axios.post(`${BACKENDROUTES.COMPUTATIONALANNOTATOR}/lexsub-tutorial-annotation`,
        command,
        {
            headers: { "Authorization": `Bearer ${token}` },
        }
    );
    return res.data;
}



// Custom hooks for fetching openai model

/**
 * Returns all OpenAI Model
 * 
 * @param fetch if data should be fetched
 * @returns list of all openai model
 */
export function useFetchAllOpenAIModel(fetch: boolean = true) {

    const fetcher = (url: string) => axios.get<OpenAIModelDto[]>(url).then(res => res.data)

    const { data, error } = useSWR(fetch ? `${BACKENDROUTES.OPENAIMODEL}` : null, fetcher)

    return {
        openaimodels: data ? data.map(OpenAIModel.fromDto) : [],
        isLoading: !error && !data,
        isError: error
    }

}

export async function UseFetchPagedUsePairJudgementsTutorials(owner: string, project: string, phase: string, page: number, fetch: boolean = true): Promise<UsePairTutorialData[] | null> {
    if (!fetch) {
        return null;
    }

    const token = localStorage.getItem('JWT') ?? '';

    try {
        const response = await axios.get<UsePairTutorialData[]>(`${BACKENDROUTES.COMPUTATIONALANNOTATOR}/usepair-tutorial-data?owner=${owner}&project=${project}&phase=${phase}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });
        return response.data;
    } catch (error) {
        console.error("Error fetching data:", error);
        return null;
    }
}