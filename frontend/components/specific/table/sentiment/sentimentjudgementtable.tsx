import { useRouter } from "next/router";
import useStorage from "../../../../lib/hook/useStorage";
import Phase from "../../../../lib/model/phase/model/Phase";
import { useEffect, useState } from "react";
import {  deleteJudgement, useFetchPagedLexSubJudgements, useFetchPagedSentimentJudgements } from "../../../../lib/service/judgement/JudgementResource";
import { toast } from "react-toastify";
import LoadingComponent from "../../../generic/loadingcomponent";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";
import Link from "next/link";
import IconButtonOnClick from "../../../generic/button/iconbuttononclick";
import PageChange from "../../../generic/table/pagination";
import { FiTool, FiTrash } from "react-icons/fi";
import AddJudgementToPhaseModal from "../../modal/addjudgementtophasemodal";
import SentimentJudgement from "../../../../lib/model/judgement/sentiment/model/SentimentJudgement";
import EditSentimentJudgementModal from "../../modal/editsentimentjudgementmodal";
import DeleteSentimentAndChoiceJudgementCommand from "../../../../lib/model/judgement/sentiment/command/DeleteSentimentAndChoiceJudgementCommand";

const SentimentJudgementTable: React.FC<{ phase: Phase, modalState: { open: boolean, callback: Function } }> = ({ phase, modalState }) => {


    const storage = useStorage();
    const router = useRouter();
    const [page, setPage] = useState(0);
    const username = useStorage().get("USER");

    const sentimentjudgements = useFetchPagedSentimentJudgements(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

    const [editModal, setEditModal] = useState({
        open: false,
        judgement: null as unknown as SentimentJudgement,
    });

    const deleteCallback = (judgement: SentimentJudgement) => {
        deleteJudgement(
            new DeleteSentimentAndChoiceJudgementCommand(
                judgement.getId().getOwner(),
                judgement.getId().getProject(),
                judgement.getId().getPhase(),
                judgement.getId().getInstanceId(),
                judgement.getId().getAnnotator(),
                judgement.getId().getId()
            ), storage.get).then(() => {
                toast.success("Judgement deleted");
                sentimentjudgements.mutate();
            }).catch((err) => {
                if (err?.response?.status === 500) {
                    toast.error("Error deleting judgement: " + err.response.data.message);
                } else {
                    toast.error("Error deleting judgement!");
                }
            });
    }

    useEffect(() => {
        if (sentimentjudgements.isError && router.isReady) {
            toast.error("Error while fetching judgements");
        }

        // Reload the data on reload
        sentimentjudgements.mutate();

    }, [sentimentjudgements.isError, router.isReady]);

    if (!phase || sentimentjudgements.isLoading || sentimentjudgements.isError) {
        return <LoadingComponent />;
    }

    return (
        <div>
            <div className="flex flex-col font-dm-mono-medium">
                <div className="overflow-auto">
                    <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                        <thead className="font-bold text-lg">
                            <tr>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Instance ID
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Sentence
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Judgement
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Comment
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Annotator
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider">
                                    Edit
                                </th>

                            </tr>
                        </thead>
                        <tbody className=" text-base16-gray-700">
                            {sentimentjudgements.data.getContent().map((judgement, i) => {
                                //@ts-ignore, TODO: fix this
                                return (
                                    <tr key={judgement.getId().getId()}>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {judgement.getId().getInstanceId()}
                                        </td>

                                        <td className="px-6 py-4 overflow-auto font-dm-mono-bold">
                                            <span key={judgement.getId().getId()} className="tooltip group w-fit">
                                                {getFormatedShortUsage(judgement.getInstance().getUsage())}
                                                <div className="tooltip-container group-hover:scale-100">
                                                    <div className="whitespace-nowrap mx-4 my-2">
                                                        Data ID: {judgement.getInstance().getUsage().getId().getDataid()}
                                                    </div>
                                                </div>
                                            </span>
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {judgement.getLabel()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {judgement.getComment()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <Link href={`/phi/${judgement.getId().getAnnotator()}`}>
                                                <a className="underline">
                                                    {judgement.getId().getAnnotator()}
                                                </a>
                                            </Link>
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <div className="flex flex-row space-x-4">
                                                <IconButtonOnClick onClick={() => {
                                                    setEditModal({
                                                        open: true,
                                                        judgement: judgement,
                                                    });
                                                }}
                                                    icon={<FiTool className="basic-svg" />}
                                                    tooltip="Edit Judgement"
                                                    hide={username !== judgement.getId().getAnnotator()}
                                                />
                                                <IconButtonOnClick onClick={() => {
                                                    deleteCallback(judgement);
                                                }}
                                                    icon={<FiTrash className="basic-svg" />}
                                                    tooltip="Delete Judgement"
                                                    hide={username !== judgement.getId().getAnnotator()}
                                                />
                                            </div>
                                        </td>
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </div>
            </div>
            <PageChange page={sentimentjudgements.data.getPage()} maxPage={sentimentjudgements.data.getTotalPages()} pageChangeCallback={(p: number) => { setPage(p) }} />

            <AddJudgementToPhaseModal isOpen={modalState.open} closeModalCallback={modalState.callback} phase={phase} mutateCallback={sentimentjudgements.mutate} />
            <EditSentimentJudgementModal isOpen={editModal.open} closeModalCallback={() => setEditModal({ open: false, judgement: null as unknown as SentimentJudgement })} judgement={editModal.judgement} mutateCallback={sentimentjudgements.mutate} /> 
        </div>
    );
}

export default SentimentJudgementTable;

// TODO: move this to a separate file
function getFormatedShortUsage(usage: Usage) {
    return (
        <div className="">
            {usageContextBuilder(usage).map((sentence, index) => {
                return (
                    <span
                        key={index}
                        className={sentence.highlight === "bold" ? "font-dm-mono-medium" : sentence.highlight === "color" ? "inline font-dm-sans-bold text-lg text-base16-green" : ""}>
                        {sentence.sentence}
                    </span>
                );
            })}
        </div>
    )
}


/**
 * Constructs a context array with Pairs of the form {sentence: string, highlight: "none" | "bold" | "color"}
 * 
 * @param usage usage to construct the context from
 */
function usageContextBuilder(usage: Usage): { sentence: string, highlight: "none" | "bold" | "color" }[] {

    const context = usage.getContext();

    const contextArray: { sentence: string, highlight: "none" | "bold" | "color" }[] = [];

    // Add the first sentence
    contextArray.push({
        sentence: context.substring(0, usage.getIndexTargetSentenceStart()),
        highlight: "none"
    });

    let lastTargetTokenEnd = 0;

    usage.getIndexTargetSentence().forEach((sentence, index) => {
        lastTargetTokenEnd = sentence.left;
        usage.getIndexTargetToken().forEach((token, index) => {
            if (token.left >= sentence.left && token.right <= sentence.right) {
                // Add the sentence till the target token
                contextArray.push({
                    sentence: context.substring(lastTargetTokenEnd, token.left),
                    highlight: "bold"
                });
                // Add the target token
                contextArray.push({
                    sentence: context.substring(token.left, token.right),
                    highlight: "color"
                });
                lastTargetTokenEnd = token.right;
            }
        });
        // Add the sentence after the target token
        contextArray.push({
            sentence: context.substring(lastTargetTokenEnd, sentence.right),
            highlight: "bold"
        });
    });

    // Add the last sentence
    contextArray.push({
        sentence: context.substring(usage.getIndexTargetSentenceEnd()),
        highlight: "none"
    });

    return contextArray;

}