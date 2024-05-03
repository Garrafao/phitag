import { useRouter } from "next/router";
import useStorage from "../../../../lib/hook/useStorage";
import Phase from "../../../../lib/model/phase/model/Phase";
import { useEffect, useState } from "react";
import { deleteLexSub, useFetchPagedHistoryLexSubJudgements, useFetchPagedLexSubJudgements } from "../../../../lib/service/judgement/JudgementResource";
import LexSubJudgement from "../../../../lib/model/judgement/lexsubjudgement/model/LexSubJudgement";
import DeleteLexSubJudgementCommand from "../../../../lib/model/judgement/lexsubjudgement/command/DeleteLexSubJudgementCommand";
import { toast } from "react-toastify";
import LoadingComponent from "../../../generic/loadingcomponent";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";
import Link from "next/link";
import IconButtonOnClick from "../../../generic/button/iconbuttononclick";
import PageChange from "../../../generic/table/pagination";
import { FiTool, FiTrash } from "react-icons/fi";
import AddJudgementToPhaseModal from "../../modal/addjudgementtophasemodal";
import EditLexSubJudgementCommand from "../../../../lib/model/judgement/lexsubjudgement/command/EditLexSubJudgementCommand";
import EditLexSubJudgementModal from "../../modal/editlexsubjudgementmodal";
import { useFetchPagedHistoryLexSubTutorialJudgements } from "../../../../lib/service/tutorial/tutorialresources";

const LexSubTutorialJudgementHistoryTable: React.FC<{ phase: Phase }> = ({ phase}) => {


    const storage = useStorage();
    const router = useRouter();
    const [page, setPage] = useState(0);

    const lexsubjudgements = useFetchPagedHistoryLexSubTutorialJudgements(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

    console.log(lexsubjudgements, "lexsub")
  /*   const [editModal, setEditModal] = useState({
        open: false,useFetchPagedHistoryLexSubTutorialJudgements
        judgement: null as unknown as LexSubJudgement,
    }); */

   /*  const deleteCallback = (judgement: LexSubJudgement) => {
        deleteLexSub(
            new DeleteLexSubJudgementCommand(
                judgement.getId().getOwner(),
                judgement.getId().getProject(),
                judgement.getId().getPhase(),
                judgement.getId().getInstanceId(),
                judgement.getId().getAnnotator(),
                judgement.getId().getId()
            ), storage.get).then(() => {
                toast.success("Judgement deleted");
                lexsubjudgements.mutate();
            }).catch((err) => {
                if (err?.response?.status === 500) {
                    toast.error("Error deleting judgement: " + err.response.data.message);
                } else {
                    toast.error("Error deleting judgement!");
                }
            });
    } */

    useEffect(() => {
        if (lexsubjudgements.isError && router.isReady) {
            toast.error("Error while fetching judgements");
        }
    
        // Reload the data on reload
        lexsubjudgements.mutate();
        
    }, [lexsubjudgements.isError, router.isReady]);

    if (!phase || lexsubjudgements.isLoading || lexsubjudgements.isError) {
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
                               {/*  <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider">
                                    Edit
                                </th> */}

                            </tr>
                        </thead>
                        <tbody className=" text-base16-gray-700">
                            {lexsubjudgements.data.getContent().map((judgement, i) => {

                                return (
                                    <tr key={judgement.getId().getId()}>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {judgement.getId().getInstanceId()}
                                        </td>

                                        <td className="px-6 py-4 overflow-auto font-dm-mono-light">
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

                                   {/*      <td className="px-6 py-4 whitespace-nowrap">
                                            <div className="flex flex-row space-x-4">
                                                <IconButtonOnClick onClick={() => {
                                                    setEditModal({
                                                        open: true,
                                                        judgement: judgement,
                                                    });
                                                }}
                                                    icon={<FiTool className="basic-svg" />}
                                                    tooltip="Edit Judgement"
                                                />
                                                <IconButtonOnClick onClick={() => {
                                                    deleteCallback(judgement);
                                                }}
                                                    icon={<FiTrash className="basic-svg" />}
                                                    tooltip="Delete Judgement"
                                                />
                                            </div>
                                        </td> */}
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </div>
            </div>
            <PageChange page={lexsubjudgements.data.getPage()} maxPage={lexsubjudgements.data.getTotalPages()} pageChangeCallback={(p: number) => { setPage(p) }} />

          {/*   <EditLexSubJudgementModal isOpen={editModal.open} closeModalCallback={() => setEditModal({ open: false, judgement: null as unknown as LexSubJudgement })} judgement={editModal.judgement} mutateCallback={lexsubjudgements.mutate} /> */}
        </div>
    );
}

export default LexSubTutorialJudgementHistoryTable;


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