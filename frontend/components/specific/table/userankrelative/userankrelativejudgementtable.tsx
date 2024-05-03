// next
import Link from "next/link";

// services
import { deleteUserank, deleteUserankrealtive, editUserank, editUserankrelative, useFetchPagedUseRankJudgements, useFetchPagedUseRankRelativeJudgements } from "../../../../lib/service/judgement/JudgementResource";

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
import { IoIosArrowDown, IoIosArrowUp } from "react-icons/io";
import UsageCard from "../../card/usagecard";
import UseRankRelativeJudgement from "../../../../lib/model/judgement/userankrelativejudgement/model/UseRankRelativeJudgement";
import EditUseRankRelativeJudgementCommand from "../../../../lib/model/judgement/userankrelativejudgement/command/EditUseRankRelativeJudgementCommand";
import DeleteUseRankRelativeJudgementCommand from "../../../../lib/model/judgement/userankrelativejudgement/command/DeleteUseRankRelativeJudgementCommand";
import DraggableRelativeJudgemets from "./dndjudgements/dragablerelativejudgements";
import UsageCardRelative from "../../card/usagecardrelative";
import EditUseRankRelativeJudgementModal from "../../modal/edituserankrelativejudgementmodal";

const UseRankRelativeJudgementTable: React.FC<{ phase: Phase, modalState: { open: boolean, callback: Function } }> = ({ phase, modalState }) => {

    const storage = useStorage();
    const router = useRouter();
    const [page, setPage] = useState(0);
    const userankrelativejudgements = useFetchPagedUseRankRelativeJudgements(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

    const username = useStorage().get("USER");

    const [editModal, setEditModal] = useState({
        open: false,
        judgement: null as unknown as UseRankRelativeJudgement,
    });



    const [editCommand, setEditCommand] = useState({
        label: "",
        comment: ""
    });

    const [expandedInstances, setExpandedInstances] = useState<boolean[]>(
        new Array(userankrelativejudgements.data.getContent().length).fill(false)
    );

    const toggleExpansion = (index: number) => {
        setExpandedInstances((prevExpanded) => {
            const newExpanded = [...prevExpanded];
            newExpanded[index] = !newExpanded[index];
            return newExpanded;
        });
    };


    const deleteCallback = (userankrelativejudgement: UseRankRelativeJudgement) => {
        deleteUserankrealtive(new DeleteUseRankRelativeJudgementCommand(
            userankrelativejudgement.getId().getOwner(),
            userankrelativejudgement.getId().getProject(),
            userankrelativejudgement.getId().getPhase(),
            userankrelativejudgement.getId().getInstanceId(),
            userankrelativejudgement.getId().getAnnotator(),
            userankrelativejudgement.getId().getId(),
        ), storage.get).then((res) => {
            toast.success("Judgement deleted");
            userankrelativejudgements.mutate();
        }).catch((err) => {
            if (err?.response?.status === 500) {
                toast.error("Error deleting judgement: " + err.response.data.message);
            } else {
                toast.error("Error deleting judgement!");
            }
        });
    }



    const editJudgementCommentFunction = (userankrelativejudgement: UseRankRelativeJudgement, comment: string) => {
        if (userankrelativejudgement == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }


        const mutateCallback = () => {

            userankrelativejudgements.mutate();

        }
        const command: EditUseRankRelativeJudgementCommand = new EditUseRankRelativeJudgementCommand(
            userankrelativejudgement.getId().getOwner(),
            userankrelativejudgement.getId().getProject(),
            userankrelativejudgement.getId().getPhase(),
            userankrelativejudgement.getId().getInstanceId(),
            userankrelativejudgement.getId().getAnnotator(),
            userankrelativejudgement.getId().getId(),
            userankrelativejudgement.getLabel(),
            userankrelativejudgement.getComment()

        );
        editUserankrelative(command, storage.get).then(() => {
            toast.success("Judgement updated");
            mutateCallback();

        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while editing judgement: " + error.response.data.message + "!");
            }
        });
    }


    const editJudgementLabelFunction = (userankrelativejudgement: UseRankRelativeJudgement, label: string) => {
        if (userankrelativejudgement == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }
        const mutateCallback = () => {

            userankrelativejudgements.mutate();

        }
        const command: EditUseRankRelativeJudgementCommand = new EditUseRankRelativeJudgementCommand(
            userankrelativejudgement.getId().getOwner(),
            userankrelativejudgement.getId().getProject(),
            userankrelativejudgement.getId().getPhase(),
            userankrelativejudgement.getId().getInstanceId(),
            userankrelativejudgement.getId().getAnnotator(),
            userankrelativejudgement.getId().getId(),
            label,
            userankrelativejudgement.getComment()

        );
        editUserankrelative(command, storage.get).then(() => {
            toast.success("Judgement updated");
            mutateCallback();

        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while editing judgement: " + error.response.data.message + "!");
            }
        });
    }



    if (!phase || userankrelativejudgements.isLoading || userankrelativejudgements.isError) {
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
                            {userankrelativejudgements.data.getContent().map((judgement, i) => {
                                if (!judgement) {
                                    return null;
                                }
                                let userankrelativejudgement: UseRankRelativeJudgement = judgement;
                                return (
                                    <>  <tr key={userankrelativejudgement.getId().getId()}>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {userankrelativejudgement.getId().getInstanceId()}
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
                                                <DraggableRelativeJudgemets
                                                judgementData={[
                                                    {
                                                        id: i,
                                                        judgement: userankrelativejudgement,
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
                                            {userankrelativejudgement.getComment()}

                                        </td>


                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <Link href={`/phi/${userankrelativejudgement.getId().getAnnotator()}`}>
                                                <a className="underline">
                                                    {userankrelativejudgement.getId().getAnnotator()}
                                                </a>
                                            </Link>
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <div className="flex flex-row space-x-4">
                                                <IconButtonOnClick onClick={() => {
                                                    setEditModal({
                                                        open: true,
                                                        judgement: userankrelativejudgement,
                                                    });
                                                }}
                                                    icon={<FiTool className="basic-svg" />}
                                                    tooltip="Edit Judgement"
                                                    hide={username !== userankrelativejudgement.getId().getAnnotator()}
                                                />

                                                <IconButtonOnClick onClick={() => {
                                                    deleteCallback(userankrelativejudgement);
                                                }}
                                                    icon={<FiTrash className="basic-svg" />}
                                                    tooltip="Delete Judgement"
                                                    hide={username !== userankrelativejudgement.getId().getAnnotator()}
                                                />
                                            </div>
                                        </td>
                                        
                                    </tr>
                                    {expandedInstances[i] && (
                                        <tr key={`${i}-expanded`}
                                            className="transition-max-h transition-property: transform duration-0  max-h-0 ">
                                            <td colSpan={6}>
                                                <UsageCardRelative isOpen={true} userankrelativeinstance ={userankrelativejudgement.getInstance()} />
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
            <PageChange page={userankrelativejudgements.data.getPage()} maxPage={userankrelativejudgements.data.getTotalPages()} pageChangeCallback={(p: number) => { setPage(p) }} />
            <EditUseRankRelativeJudgementModal isOpen={editModal.open} closeModalCallback={() => setEditModal({ open: false, judgement: null as unknown as UseRankRelativeJudgement })} judgement={editModal.judgement} mutateCallback={userankrelativejudgements.mutate} />
            <AddJudgementToPhaseModal isOpen={modalState.open} closeModalCallback={modalState.callback} phase={phase} mutateCallback={userankrelativejudgements.mutate} />
        </div>
    );

}

export default UseRankRelativeJudgementTable;

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

