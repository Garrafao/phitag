import { useState } from "react";

// toast
import { toast } from "react-toastify";

// icons
import { FiEdit3, FiFeather } from "react-icons/fi";

// service
import useStorage from "../../../lib/hook/useStorage";

// model
import { editLexSub, editUsepair} from "../../../lib/service/judgement/JudgementResource";
import DropdownSelect from "../../generic/dropdown/dropdownselect";
import DummySelectable from "../../../lib/model/dummy/DummySelectable";
import LexSubJudgement from "../../../lib/model/judgement/lexsubjudgement/model/LexSubJudgement";
import EditLexSubJudgementCommand from "../../../lib/model/judgement/lexsubjudgement/command/EditLexSubJudgementCommand";

interface EditLexSubJudgementModalProps {
    judgement: LexSubJudgement;

    isOpen: boolean;
    closeModalCallback: Function;
    mutateCallback: Function;
}

const EditLexSubJudgementModal: React.FC<EditLexSubJudgementModalProps> = ({ judgement, isOpen, closeModalCallback, mutateCallback }) => {

    // data and state
    const storage = useStorage();

    const [modalState, setModalState] = useState({
        label: "",
        commentChange: false,
        comment: "",
    });

    const onConfirm = () => {
        if (judgement == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }

        const command: EditLexSubJudgementCommand = new EditLexSubJudgementCommand(
            judgement.getId().getOwner(),
            judgement.getId().getProject(),
            judgement.getId().getPhase(),

            judgement.getId().getInstanceId(),
            judgement.getId().getAnnotator(),

            judgement.getId().getId(),

            modalState.label,
            modalState.commentChange ? modalState.comment : judgement.getComment()
        );
        editLexSub(command, storage.get).then(() => {
            toast.success("Judgement updated");
            // close modal
            setModalState({
                ...modalState,
                label: "",
                commentChange: false,
                comment: "",
            });
            mutateCallback();
            closeModalCallback();

        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while editing judgement: " + error.response.data.message + "!");
            } else {
                toast.error("Error while editing judgement!");
            }
        });
    }

    const onCancel = () => {
        toast.info("Edit judgement canceled");
        setModalState({
            ...modalState,
            label: "",
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
                                    Edit Substitution Judgement
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    Editing substitution judgement.
                                </div>

                                <div className="flex flex-col items-left mt-6">
                                    <div className="font-bold text-lg">
                                        Label
                                    </div>                                    
                                    <div className="h-32 flex items-start border-l-2 py-2 px-3 my-6">
                                        <textarea
                                            className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                                            name="label"
                                            placeholder={judgement.getLabel()}
                                            value={modalState.label || judgement.getLabel()}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                label: e.target.value
                                            })} />
                                    </div>
                                </div>


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
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={() => onCancel()}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onConfirm}>Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )

}

export default EditLexSubJudgementModal;
