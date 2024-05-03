import { useState } from "react";

// toast
import { toast } from "react-toastify";

// service
import { editUsage } from "../../../lib/service/phitagdata/PhitagDataResource";
import useStorage from "../../../lib/hook/useStorage";

// model
import Usage from "../../../lib/model/phitagdata/usage/model/Usage";
import EditUsageCommand from "../../../lib/model/phitagdata/usage/command/EditUsageCommand";
import { FiBold, FiFeather, FiHash, FiUnderline } from "react-icons/fi";

interface EditUsageModalProps {
    usage: Usage;

    isOpen: boolean;
    closeModalCallback: Function;
    mutateCallback: Function;
}

const EditUsageModal: React.FC<EditUsageModalProps> = ({ usage, isOpen, closeModalCallback, mutateCallback }) => {

    // data and state
    const storage = useStorage();

    const [modalState, setModalState] = useState({
        context: "",

        indexTargetToken: "",
        indexTargetSentence: "",

        lemma: "",
        group: "",
    });

    const onConfirm = () => {
        if (usage == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }

        const command: EditUsageCommand = new EditUsageCommand(usage.getId().getDataid(), usage.getId().getOwner(), usage.getId().getProject(), modalState.context, modalState.indexTargetToken, modalState.indexTargetSentence, modalState.lemma, modalState.group);
        editUsage(command, storage.get).then(() => {
            toast.success("Usage updated");
            // close modal
            setModalState({
                context: "",

                indexTargetToken: "",
                indexTargetSentence: "",

                lemma: "",
                group: "",
            });
            mutateCallback();
            closeModalCallback();

        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while editing usage: " + error.response.data.message + "!");
            } else {
                toast.error("Error while editing usage!");
            }
        });
    }

    const onCancel = () => {
        toast.info("Edit usage canceled");
        setModalState({
            ...modalState,
            context: "",

            indexTargetToken: "",
            indexTargetSentence: "",

            lemma: "",
            group: "",
        });
        closeModalCallback();
    }

    if (!isOpen || !usage) {
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
                                    Edit Usage
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    Editing usage. For indexes use the format &quot;start:end,...&quot;.
                                </div>

                                <div className="flex flex-col items-left my-4">
                                    <div className="font-bold text-lg">
                                        Context
                                    </div>
                                    <div className="h-32 flex items-start border-l-2 py-2 px-3 my-6">
                                        <FiFeather className='basic-svg' />
                                        <textarea
                                            className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                                            name="context"
                                            placeholder={usage.getContext()}
                                            value={modalState.context || usage.getContext()}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                context: e.target.value
                                            })} />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left mt-2 mb-4">
                                    <div className="font-bold text-lg">
                                        Index Target Token
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiUnderline className='basic-svg' />
                                        <input
                                            id="indexTargetToken"
                                            name="indexTargetToken"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder={usage.getIndexTargetToken().map((index) => `${index.left}:${index.right}`).join(",")}
                                            value={modalState.indexTargetToken || usage.getIndexTargetToken().map((index) => `${index.left}:${index.right}`).join(",")}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                indexTargetToken: e.target.value
                                            })} />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left mb-6">
                                    <div className="font-bold text-lg">
                                        Index Target Sentence
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiUnderline className='basic-svg' />
                                        <input
                                            id="indexTargetSentence"
                                            name="indexTargetSentence"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder={usage.getIndexTargetSentence().map((index) => `${index.left}:${index.right}`).join(",")}
                                            value={modalState.indexTargetSentence || usage.getIndexTargetSentence().map((index) => `${index.left}:${index.right}`).join(",")}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                indexTargetSentence: e.target.value
                                            })} />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left mb-6">
                                    <div className="font-bold text-lg">
                                        Lemma
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiBold className='basic-svg' />
                                        <input
                                            id="lemma"
                                            name="lemma"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder={usage.getLemma()}
                                            value={modalState.lemma || usage.getLemma()}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                lemma: e.target.value
                                            })} />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left mb-6">
                                    <div className="font-bold text-lg">
                                        Group
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiHash className='basic-svg' />
                                        <input
                                            id="group"
                                            name="group"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder={usage.getGroup()}
                                            value={modalState.group || usage.getGroup()}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                group: e.target.value
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

export default EditUsageModal;
