import { useState } from "react";

// toast
import { toast } from "react-toastify";

// icons
import { FiEdit3, FiFeather } from "react-icons/fi";

// service
import useStorage from "../../../lib/hook/useStorage";
import { editWssim } from "../../../lib/service/judgement/JudgementResource";

// model
import WSSIMJudgement from "../../../lib/model/judgement/wssimjudgement/model/WSSIMJudgement";
import DummySelectable from "../../../lib/model/dummy/DummySelectable";
import EditWSSIMJudgementCommand from "../../../lib/model/judgement/wssimjudgement/command/EditWSSIMJudgementCommand";

// components
import DropdownSelect from "../../generic/dropdown/dropdownselect";

interface EditWSSIMJudgementModalProps {
    judgement: WSSIMJudgement;

    isOpen: boolean;
    closeModalCallback: Function;
    mutateCallback: Function;
}

const EditWSSIMJudgementModal: React.FC<EditWSSIMJudgementModalProps> = ({ judgement, isOpen, closeModalCallback, mutateCallback }) => {

    // data and state
    const storage = useStorage();

    const [modalState, setModalState] = useState({
        label: new DummySelectable(""),
        commentChange: false,
        comment: "",
    });

    const onConfirm = () => {
        if (judgement == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }

        const command: EditWSSIMJudgementCommand = new EditWSSIMJudgementCommand(
            judgement.getId().getOwner(),
            judgement.getId().getProject(),
            judgement.getId().getPhase(),

            judgement.getId().getInstanceId(),
            judgement.getId().getAnnotator(),

            judgement.getId().getId(),

            modalState.label.getName(),
            modalState.commentChange ? modalState.comment : judgement.getComment()
        );
        editWssim(command, storage.get).then(() => {
            toast.success("Judgement updated");
            // close modal
            setModalState({
                ...modalState,
                label: new DummySelectable(""),
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
            label: new DummySelectable(""),
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
                                    Edit WSSIM Judgement
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    Editing judgement.
                                </div>

                                <div className="flex flex-col items-left mt-6">
                                    <div className="font-bold text-lg">
                                        Label
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <DropdownSelect
                                            icon={<FiEdit3 className='basic-svg' />}
                                            items={DummySelectable.fromArray(judgement.getInstance().getLabelAndNonLabel())}
                                            selected={[modalState.label.getName() === "" ? new DummySelectable(judgement.getLabel()) : modalState.label]}
                                            onSelectFunction={(selected: DummySelectable) => setModalState({
                                                ...modalState,
                                                label: selected
                                            })}
                                            message={modalState.label.getName() || judgement.getLabel()}
                                        />
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

export default EditWSSIMJudgementModal;
