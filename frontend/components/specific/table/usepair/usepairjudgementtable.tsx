// next
import Link from "next/link";

// services
import { deleteUsepair, useFetchJudgements, useFetchPagedUsePairJudgements } from "../../../../lib/service/judgement/JudgementResource";

// models
import UsePairJudgement, { UsePairJudgementConstructor } from "../../../../lib/model/judgement/usepairjudgement/model/UsePairJudgement";
import Phase from "../../../../lib/model/phase/model/Phase";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";

// components
import LoadingComponent from "../../../generic/loadingcomponent";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { useRouter } from "next/router";
import AddJudgementToPhaseModal from "../../modal/addjudgementtophasemodal";
import IconButtonOnClick from "../../../generic/button/iconbuttononclick";
import { FiEdit, FiTool, FiTrash } from "react-icons/fi";
import EditUsePairJudgementModal from "../../modal/editusepairjudgementmodal";
import useStorage from "../../../../lib/hook/useStorage";
import DeleteUsePairJudgementCommand from "../../../../lib/model/judgement/usepairjudgement/command/DeleteUsePairJudgementCommand";
import PageChange from "../../../generic/table/pagination";

const UsePairJudgementTable: React.FC<{ phase: Phase, modalState: { open: boolean, callback: Function } }> = ({ phase, modalState }) => {

    const storage = useStorage();
    const router = useRouter();
    const [page, setPage] = useState(0);
    const usepairjudgements = useFetchPagedUsePairJudgements(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

    const username = useStorage().get("USER");

    const [editModal, setEditModal] = useState({
        open: false,
        judgement: null as unknown as UsePairJudgement,
    });



    const deleteCallback = (usepairjudgement: UsePairJudgement) => {

        deleteUsepair(new DeleteUsePairJudgementCommand(
            usepairjudgement.getId().getOwner(),
            usepairjudgement.getId().getProject(),
            usepairjudgement.getId().getPhase(),
            usepairjudgement.getId().getInstanceId(),
            usepairjudgement.getId().getAnnotator(),
            usepairjudgement.getId().getId(),
        ), storage.get).then((res) => {
            toast.success("Judgement deleted");
            usepairjudgements.mutate();
        }).catch((err) => {
            if (err?.response?.status === 500) {
                toast.error("Error deleting judgement: " + err.response.data.message);
            } else {
                toast.error("Error deleting judgement!");
            }
        });
    }

    useEffect(() => {
        if (usepairjudgements.isError && router.isReady) {
            toast.error("Error while fetching judgements");
        }

        // reload the data on reload
        usepairjudgements.mutate();

    }, [usepairjudgements.isError, router.isReady]);

    if (!phase || usepairjudgements.isLoading || usepairjudgements.isError) {
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
                                    First Usage
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Second Usage
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Label Set
                                    <br />
                                    and Non Label
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Label
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
                            {usepairjudgements.data.getContent().map((judgement, i) => {
                                //@ts-ignore, TODO: fix this
                                let usepairjudgement: UsePairJudgement = judgement;
                                return (
                                    <tr key={usepairjudgement.getId().getId()}>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {usepairjudgement.getId().getInstanceId()}
                                        </td>

                                        <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                            <span key={i} className="tooltip group w-fit">
                                                {getFormatedShortUsage(usepairjudgement.getInstance().getFirstusage())}
                                                <div className="tooltip-container group-hover:scale-100">
                                                    <div className="whitespace-nowrap mx-4 my-2">
                                                        Data ID: {usepairjudgement.getInstance().getFirstusage().getId().getDataid()}
                                                    </div>
                                                </div>
                                            </span>
                                        </td>

                                        <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                            <span key={i} className="tooltip group w-fit">
                                                {getFormatedShortUsage(usepairjudgement.getInstance().getSecondusage())}
                                                <div className="tooltip-container group-hover:scale-100">
                                                    <div className="whitespace-nowrap mx-4 my-2">
                                                        Data ID: {usepairjudgement.getInstance().getSecondusage().getId().getDataid()}
                                                    </div>
                                                </div>
                                            </span>
                                        </td>


                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {usepairjudgement.getInstance().getLabelSet().join(", ")}, {usepairjudgement.getInstance().getNonLabel()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {usepairjudgement.getLabel()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {usepairjudgement.getComment()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <Link href={`/phi/${usepairjudgement.getId().getAnnotator()}`}>
                                                <a className="underline">
                                                    {usepairjudgement.getId().getAnnotator()}
                                                </a>
                                            </Link>
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <div className="flex flex-row space-x-4">
                                                <IconButtonOnClick onClick={() => {
                                                    setEditModal({
                                                        open: true,
                                                        judgement: usepairjudgement,
                                                    });
                                                }}
                                                    icon={<FiTool className="basic-svg" />}
                                                    tooltip="Edit Judgement"
                                                    hide={username !== usepairjudgement.getId().getAnnotator()}
                                                />
                                                <IconButtonOnClick onClick={() => {
                                                    deleteCallback(usepairjudgement);
                                                }}
                                                    icon={<FiTrash className="basic-svg" />}
                                                    tooltip="Delete Judgement"
                                                    hide={username !== usepairjudgement.getId().getAnnotator()}
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
            <PageChange page={usepairjudgements.data.getPage()} maxPage={usepairjudgements.data.getTotalPages()} pageChangeCallback={(p: number) => {setPage(p)}} />

            <AddJudgementToPhaseModal isOpen={modalState.open} closeModalCallback={modalState.callback} phase={phase} mutateCallback={usepairjudgements.mutate} />
            <EditUsePairJudgementModal isOpen={editModal.open} closeModalCallback={() => setEditModal({ open: false, judgement: null as unknown as UsePairJudgement })} judgement={editModal.judgement} mutateCallback={usepairjudgements.mutate} />
        </div>
    );

}

export default UsePairJudgementTable;

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