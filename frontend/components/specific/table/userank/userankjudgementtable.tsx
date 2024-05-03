// next
import Link from "next/link";

// services
import { deleteUserank, editUserank, useFetchPagedUseRankJudgements } from "../../../../lib/service/judgement/JudgementResource";

// models
import Phase from "../../../../lib/model/phase/model/Phase";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";

// components
import LoadingComponent from "../../../generic/loadingcomponent";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { Router, useRouter } from "next/router";
import AddJudgementToPhaseModal from "../../modal/addjudgementtophasemodal";
import IconButtonOnClick from "../../../generic/button/iconbuttononclick";
import { FiEdit, FiHelpCircle, FiInfo, FiTool, FiTrash } from "react-icons/fi";
import useStorage from "../../../../lib/hook/useStorage";
import PageChange from "../../../generic/table/pagination";
import UseRankJudgement from "../../../../lib/model/judgement/userankjudgement/model/UseRankJudgement";
import DeleteUseRankJudgementCommand from "../../../../lib/model/judgement/userankjudgement/command/DeleteUseRankJudgementCommand";
import EditUseRankJudgementCommand from "../../../../lib/model/judgement/userankjudgement/command/EditUseRankJudgementCommand";
import EditUseRankJudgementModal from "../../modal/edituserankjudgementmodal";
import IconButtonWithTooltip from "../../../generic/button/iconbuttonwithtooltip";
import DraggableJudgemets from "./dndjudgements/dragablejudgements";
import { IoIosArrowDown, IoIosArrowUp } from "react-icons/io";
import UsageCard from "../../card/usagecard";

const UseRankJudgementTable: React.FC<{ phase: Phase, modalState: { open: boolean, callback: Function } }> = ({ phase, modalState }) => {

    const storage = useStorage();
    const router = useRouter();
    const [page, setPage] = useState(0);
    const userankjudgements = useFetchPagedUseRankJudgements(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

    const username = useStorage().get("USER");

    const [editModal, setEditModal] = useState({
        open: false,
        judgement: null as unknown as UseRankJudgement,
    });



    const [editCommand, setEditCommand] = useState({
        label: "",
        comment: ""
    });

    const [expandedInstances, setExpandedInstances] = useState<boolean[]>(
        new Array(userankjudgements.data.getContent().length).fill(false)
    );

    const toggleExpansion = (index: number) => {
        setExpandedInstances((prevExpanded) => {
            const newExpanded = [...prevExpanded];
            newExpanded[index] = !newExpanded[index];
            return newExpanded;
        });
    };


    const deleteCallback = (userankjudgement: UseRankJudgement) => {
        deleteUserank(new DeleteUseRankJudgementCommand(
            userankjudgement.getId().getOwner(),
            userankjudgement.getId().getProject(),
            userankjudgement.getId().getPhase(),
            userankjudgement.getId().getInstanceId(),
            userankjudgement.getId().getAnnotator(),
            userankjudgement.getId().getId(),
        ), storage.get).then((res) => {
            toast.success("Judgement deleted");
            userankjudgements.mutate();
        }).catch((err) => {
            if (err?.response?.status === 500) {
                toast.error("Error deleting judgement: " + err.response.data.message);
            } else {
                toast.error("Error deleting judgement!");
            }
        });
    }



    const editJudgementCommentFunction = (userankjudgement: UseRankJudgement, comment: string) => {
        if (userankjudgement == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }


        const mutateCallback = () => {

            userankjudgements.mutate();

        }
        const command: EditUseRankJudgementCommand = new EditUseRankJudgementCommand(
            userankjudgement.getId().getOwner(),
            userankjudgement.getId().getProject(),
            userankjudgement.getId().getPhase(),
            userankjudgement.getId().getInstanceId(),
            userankjudgement.getId().getAnnotator(),
            userankjudgement.getId().getId(),
            userankjudgement.getLabel(),
            userankjudgement.getComment()

        );
        editUserank(command, storage.get).then(() => {
            toast.success("Judgement updated");
            mutateCallback();

        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while editing judgement: " + error.response.data.message + "!");
            }
        });
    }


    const editJudgementLabelFunction = (userankjudgement: UseRankJudgement, label: string) => {
        if (userankjudgement == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }
        const mutateCallback = () => {

            userankjudgements.mutate();

        }
        const command: EditUseRankJudgementCommand = new EditUseRankJudgementCommand(
            userankjudgement.getId().getOwner(),
            userankjudgement.getId().getProject(),
            userankjudgement.getId().getPhase(),
            userankjudgement.getId().getInstanceId(),
            userankjudgement.getId().getAnnotator(),
            userankjudgement.getId().getId(),
            label,
            userankjudgement.getComment()

        );
        editUserank(command, storage.get).then(() => {
            toast.success("Judgement updated");
            mutateCallback();

        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while editing judgement: " + error.response.data.message + "!");
            }
        });
    }



    if (!phase || userankjudgements.isLoading || userankjudgements.isError) {
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
                                    Rank
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
                            {userankjudgements.data.getContent().map((judgement, i) => {
                                if (!judgement) {
                                    return null;
                                }
                                let userankjudgement: UseRankJudgement = judgement;
                                return (
                                    <>  <tr key={userankjudgement.getId().getId()}>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {userankjudgement.getId().getInstanceId()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <div className="flex items-center">
                                                {expandedInstances[i] ? (
                                                    <div>
                                                        Hide
                                                        <IoIosArrowUp
                                                            className="ml-2 cursor-pointer"
                                                            onClick={() => toggleExpansion(i)}
                                                        />
                                                    </div>
                                                ) : (
                                                    <div>
                                                        Show
                                                        <IoIosArrowDown
                                                            className="ml-2 cursor-pointer"
                                                            onClick={() => toggleExpansion(i)}
                                                        />
                                                    </div>
                                                )}
                                            </div>
                                        </td>
                                        <td className="px-9 py-4 whitespace-nowrap" key={i}>
                                            {judgement.getLabel()!=="-"?
                                                <DraggableJudgemets
                                                judgementData={[
                                                    {
                                                        id: i,
                                                        judgement: userankjudgement,
                                                    },
                                                ]}
                                                editFunction={editJudgementLabelFunction}
                                            />
                                                :
                                                <div className="w-full flex  justify-center space-y-4 ">
                                                    -
                                                </div>
                                            }
                                           
                                        </td>

                                        <td className="px-2 py-2 whitespace-nowrap" >
                                            {userankjudgement.getComment()}

                                        </td>


                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <Link href={`/phi/${userankjudgement.getId().getAnnotator()}`}>
                                                <a className="underline">
                                                    {userankjudgement.getId().getAnnotator()}
                                                </a>
                                            </Link>
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <div className="flex flex-row space-x-4">
                                                <IconButtonOnClick onClick={() => {
                                                    setEditModal({
                                                        open: true,
                                                        judgement: userankjudgement,
                                                    });
                                                }}
                                                    icon={<FiTool className="basic-svg" />}
                                                    tooltip="Edit Judgement"
                                                    hide={username !== userankjudgement.getId().getAnnotator()}
                                                />

                                                <IconButtonOnClick onClick={() => {
                                                    deleteCallback(userankjudgement);
                                                }}
                                                    icon={<FiTrash className="basic-svg" />}
                                                    tooltip="Delete Judgement"
                                                    hide={username !== userankjudgement.getId().getAnnotator()}
                                                />
                                            </div>
                                        </td>
                                        
                                    </tr>
                                    {expandedInstances[i] && (
                                        <tr key={`${i}-expanded`}
                                            className="transition-max-h transition-property: transform duration-0  max-h-0 ">
                                            <td colSpan={6}>
                                                <UsageCard isOpen={true} userankinstance={userankjudgement.getInstance()} />
                                            </td>
                                        </tr>
                                    )}
                                </>
                                );
                            })}
                        </tbody>
                    </table>
                </div>
            </div>
            <PageChange page={userankjudgements.data.getPage()} maxPage={userankjudgements.data.getTotalPages()} pageChangeCallback={(p: number) => { setPage(p) }} />
            <EditUseRankJudgementModal isOpen={editModal.open} closeModalCallback={() => setEditModal({ open: false, judgement: null as unknown as UseRankJudgement })} judgement={editModal.judgement} mutateCallback={userankjudgements.mutate} />
            <AddJudgementToPhaseModal isOpen={modalState.open} closeModalCallback={modalState.callback} phase={phase} mutateCallback={userankjudgements.mutate} />
        </div>
    );

}

export default UseRankJudgementTable;

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

