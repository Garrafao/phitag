// next
import Link from "next/link";

import { useEffect, useState } from "react";

import { FiEdit, FiTrash } from "react-icons/fi";

// services
import { deleteWssim, useFetchJudgements, useFetchPagedWSSIMJudgements } from "../../../../lib/service/judgement/JudgementResource";
import useStorage from "../../../../lib/hook/useStorage";

// models
import WSSIMJudgement, { WSSIMJudgementConstructor } from "../../../../lib/model/judgement/wssimjudgement/model/WSSIMJudgement";
import Phase from "../../../../lib/model/phase/model/Phase";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";

// components
import LoadingComponent from "../../../generic/loadingcomponent";
import AddJudgementToPhaseModal from "../../modal/addjudgementtophasemodal";
import IconButtonOnClick from "../../../generic/button/iconbuttononclick";
import EditWSSIMJudgementModal from "../../modal/editwssimjudgementmodal";
import DeleteWSSIMJudgementCommand from "../../../../lib/model/judgement/wssimjudgement/command/DeleteWSSIMJudgementCommand";
import { toast } from "react-toastify";
import PageChange from "../../../generic/table/pagination";

const WSSIMJudgementTable: React.FC<{ phase: Phase, modalState: { open: boolean, callback: Function } }> = ({ phase, modalState }) => {

    const storage = useStorage();
    const [page, setPage] = useState(0);
    const wssimjudgements = useFetchPagedWSSIMJudgements(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

    const username = useStorage().get("USER");

    const [editModal, setEditModal] = useState({
        open: false,
        judgement: null as unknown as WSSIMJudgement,
    });

    const deleteCallback = (judgement: WSSIMJudgement) => {
        deleteWssim(new DeleteWSSIMJudgementCommand(
            judgement.getId().getOwner(),
            judgement.getId().getProject(),
            judgement.getId().getPhase(),
            judgement.getId().getInstanceId(),
            judgement.getId().getAnnotator(),
            judgement.getId().getId(),
        ), storage.get).then(() => {
            toast.success("Judgement deleted");
            wssimjudgements.mutate();
        }).catch((err) => {
            if (err?.response?.status === 500) {
                toast.error("Error deleting judgement: " + err.response.data.message);
            } else {
                toast.error("Error deleting judgement!");
            }
        });
    }

    // Reload the data on reload
    useEffect(() => {
        wssimjudgements.mutate();
    }, [phase]);

    if (!phase || wssimjudgements.isLoading || wssimjudgements.isError) {
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
                                    Usage
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Tag
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Label Set and Non Label
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
                            {wssimjudgements.data.getContent().map((judgement, i) => {
                                //@ts-ignore, TODO: fix this
                                let castedJudgement: WSSIMJudgement = judgement;
                                return (
                                    <tr key={castedJudgement.getId().getId()}>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {castedJudgement.getId().getInstanceId()}
                                        </td>


                                        <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                            <span className="tooltip group w-fit">
                                                {getFormatedShortUsage(castedJudgement.getInstance().getUsage())}
                                                <div className="tooltip-container group-hover:scale-100">
                                                    <div className="whitespace-nowrap mx-4 my-2">
                                                        Data ID: {castedJudgement.getInstance().getUsage().getId().getDataid()}
                                                    </div>
                                                </div>
                                            </span>
                                        </td>

                                        <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                            <span className="tooltip group w-fit">
                                                {castedJudgement.getInstance().getTag().getDefinition()}
                                                <div className="tooltip-container group-hover:scale-100">
                                                    <div className="whitespace-nowrap mx-4 my-2">
                                                        Data ID: {castedJudgement.getInstance().getTag().getId().getInstanceId()}
                                                    </div>
                                                </div>
                                            </span>
                                        </td>


                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {castedJudgement.getInstance().getLabelSet().join(", ")}, {castedJudgement.getInstance().getNonLabel()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {castedJudgement.getLabel()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {castedJudgement.getComment()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <Link href={`/phi/${castedJudgement.getId().getAnnotator()}`}>
                                                <a className="underline">
                                                    {castedJudgement.getId().getAnnotator()}
                                                </a>
                                            </Link>
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <div className="flex flex-row space-x-4">

                                                <IconButtonOnClick onClick={() => {
                                                    setEditModal({
                                                        open: true,
                                                        judgement: castedJudgement,
                                                    });
                                                }}
                                                    icon={<FiEdit className="basic-svg" />}
                                                    tooltip="Edit Judgement"
                                                    hide={username !== castedJudgement.getId().getAnnotator()}
                                                />
                                                <IconButtonOnClick onClick={() => {
                                                    deleteCallback(castedJudgement);
                                                }}
                                                    icon={<FiTrash className="basic-svg" />}
                                                    tooltip="Delete Judgement"
                                                    hide={username !== castedJudgement.getId().getAnnotator()}
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
            <PageChange page={wssimjudgements.data.getPage()} maxPage={wssimjudgements.data.getTotalPages()} pageChangeCallback={(p: number) => {setPage(p)}} />


            <AddJudgementToPhaseModal isOpen={modalState.open} closeModalCallback={modalState.callback} phase={phase} mutateCallback={wssimjudgements.mutate} />
            <EditWSSIMJudgementModal isOpen={editModal.open} closeModalCallback={() => setEditModal({ open: false, judgement: null as unknown as WSSIMJudgement })} judgement={editModal.judgement} mutateCallback={wssimjudgements.mutate} />

        </div>
    );

}

export default WSSIMJudgementTable;

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