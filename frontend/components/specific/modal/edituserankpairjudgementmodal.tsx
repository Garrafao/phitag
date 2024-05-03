import { useEffect, useState } from "react";

// toast
import { toast } from "react-toastify";

// icons
import { FiEdit3, FiFeather } from "react-icons/fi";

// service
import useStorage from "../../../lib/hook/useStorage";

// model
import { editUserank, editUserankpair, editUserankrelative } from "../../../lib/service/judgement/JudgementResource";
import UseRankRelativeJudgement from "../../../lib/model/judgement/userankrelativejudgement/model/UseRankRelativeJudgement";
import EditUseRankRelativeJudgementCommand from "../../../lib/model/judgement/userankrelativejudgement/command/EditUseRankRelativeJudgementCommand";
import EditUseRankPairJudgementCommand from "../../../lib/model/judgement/userankpairjudgement/command/EditUseRankPairJudgementCommand";
import UseRankPairJudgement from "../../../lib/model/judgement/userankpairjudgement/model/UseRankPairJudgement";

interface EditUseRankRealtiveJudgementModalProps {
    judgement: UseRankPairJudgement;
    isOpen: boolean;
    closeModalCallback: Function;
    mutateCallback: Function;
}

const EditUseRankPairJudgementModal: React.FC<EditUseRankRealtiveJudgementModalProps> = ({ judgement, isOpen, closeModalCallback, mutateCallback }) => {


    const [modalState, setModalState] = useState({
        commentChange: false,
        comment: "",
    });
    const [label, setLabel] = useState("");





    // data and state
    const storage = useStorage();

    const addLabel = () => {
        if (judgement == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }
        const command: EditUseRankRelativeJudgementCommand = new EditUseRankRelativeJudgementCommand(
            judgement.getId().getOwner(),
            judgement.getId().getProject(),
            judgement.getId().getPhase(),
            judgement.getId().getInstanceId(),
            judgement.getId().getAnnotator(),
            judgement.getId().getId(),
            label,
            judgement.getComment()

        );
        editUserankrelative(command, storage.get).then(() => {
            toast.success("Label Added");
            mutateCallback();
            closeModalCallback();


        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while editing judgement: " + error.response.data.message + "!");
            }
        });
    }

    useEffect(() => {

        if(judgement){
            const labelSet = judgement.getInstance().getLabelSet().toString();
            setLabel(labelSet);
        }

    }, [judgement])



    const onConfirm = () => {
        if (judgement == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }
        const command: EditUseRankPairJudgementCommand = new EditUseRankPairJudgementCommand(
            judgement.getId().getOwner(),
            judgement.getId().getProject(),
            judgement.getId().getPhase(),
            judgement.getId().getInstanceId(),
            judgement.getId().getAnnotator(),
            judgement.getId().getId(),
            judgement.getLabel(),
            modalState.commentChange ? modalState.comment : judgement.getComment()

        );
        editUserankpair(command, storage.get).then(() => {
            toast.success("Comment updated");
            mutateCallback();
            closeModalCallback();


        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while editing judgement: " + error.response.data.message + "!");
            }
        });
    }


    const onCancel = () => {
        toast.info("Edit judgement canceled");
        setModalState({
            ...modalState,
            commentChange: false,
            comment: "",
        });
        closeModalCallback();
    }

    if (!isOpen || !judgement) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                    Edit Use Rank Judgement Comment
                                </div>
                                {
                                    (judgement.getLabel() ==="-"  &&
                                    <div className="my-2 w-full">
                                        <div className="font-black text-l">
                                            If you want to add label, then click on Add Label.
                                        </div>
                                        <div>
                                            <button
                                                type="button"
                                                className="block w-40 mt-2 py-2 bg-base16-gray-900 text-base16-gray-100 active:transform active:scale-95"
                                                onClick={addLabel}
                                            >
                                                Add Label
                                            </button>
                                        </div>
                                    </div>

                                )}





                                <div className="flex flex-col items-left my-4">
                                    <div className="font-bold text-lg">
                                        Comment
                                    </div>
                                    <div className="h-32 flex items-start border-l-2 py-2 px-3 my-6">
                                        <FiFeather className='basic-svg' />
                                        <textarea
                                            className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                                            name="context"
                                            placeholder={judgement.getComment()}
                                            value={modalState.comment || judgement.getComment()}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                commentChange: true,
                                                comment: e.target.value
                                            })} />
                                    </div>
                                </div>

                            </div>

                        </div>
                        <div className="flex flex-row divide-x-8">
                            <button type="button" className="block  w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 active:transform active:scale-95 " onClick={() => onCancel()}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 active:transform active:scale-95 " onClick={onConfirm}>Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )

}

export default EditUseRankPairJudgementModal;
