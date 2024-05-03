// react
import { useState } from "react";

// icons
import { FiFileText } from "react-icons/fi";

// services

// models
import Phase from "../../../lib/model/phase/model/Phase";

// components
import { toast } from "react-toastify";
import { addInstance } from "../../../lib/service/instance/InstanceResource";
import useStorage from "../../../lib/hook/useStorage";
import Checkbox from "../../generic/checkbox/checkbox";

const AddWSSIMInstanceToPhaseModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, phase: Phase, mutateCallback: Function }> = ({ isOpen, closeModalCallback, phase, mutateCallback }) => {

    // hooks
    const storage = useStorage();

    // data and state
    const [modalState, setModalState] = useState({
        selectedFile: null as unknown as File,
        tagfile: false,
    })


    const onConfirm = () => {
        if (!phase) {
            toast.error("This should not happen. Please contact the developers!");
            return;
        }

        if (!modalState.selectedFile) {
            toast.error("Please select a file");
            return;
        }

        // @ts-ignore
        addInstance(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), modalState.selectedFile, modalState.tagfile, storage.get)
            .then((phase) => {
                toast.success("Successfully added instances");

                // close modal
                setModalState({
                    selectedFile: null as unknown as File,
                    tagfile: false,
                })
                mutateCallback();
                closeModalCallback();
            }).catch((error) => {
                if (error?.response?.status === 500) {
                    toast.error("Error while adding data: " + error.response.data.message + "!");
                } else {
                    toast.warning("The system is currently not available, please try again later!");
                }
            });
    }

    const onCancel = () => {
        toast.info("Canceled adding instances to phase");
        // close modal
        setModalState({
            selectedFile: null as unknown as File,
            tagfile: false,
        })
        closeModalCallback();
    }

    if (!isOpen || !phase) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={onCancel}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                    Phase: {phase ? phase.getDisplayname() : "None"}
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You are about to add new instances data to the phase.
                                </div>
                                

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        File
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiFileText className='basic-svg' />
                                        <input
                                            type="file"
                                            className="hide-upload-button pl-3 flex flex-auto outline-none border-none text-sm"
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                selectedFile: e.target.files[0]
                                            })} />
                                    </div>
                                </div>

                                <div className="flex w-full items-center py-2 px-3">
                                    <Checkbox
                                        selected={modalState.tagfile}
                                        description={"Is a Tagfile"}
                                        onClick={() => setModalState({
                                            ...modalState,
                                            tagfile: !modalState.tagfile
                                        })} />
                                </div>
                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8">
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onCancel}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onConfirm}>Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )
}

export default AddWSSIMInstanceToPhaseModal;
