// react
import { useState } from "react";

// icons
import { FiFileText, FiList, FiMinus } from "react-icons/fi";

// services

// models
import Phase from "../../../lib/model/phase/model/Phase";

// components
import { toast } from "react-toastify";
import { generateInstance } from "../../../lib/service/instance/InstanceResource";
import useStorage from "../../../lib/hook/useStorage";
import ANNOTATIONTYPES from "../../../lib/AnnotationTypes";

const GenerateInstancesForPhaseModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, phase: Phase, mutateCallback: Function, additional: boolean, additionalFileName: string }> = ({ isOpen, closeModalCallback, phase, mutateCallback, additional, additionalFileName }) => {

    // hooks
    const storage = useStorage();

    // data and state
    const [modalState, setModalState] = useState({
        labels: "0,1,2,3,4,5",
        nonLabel: "-",

        selectedFile: null as unknown as File,
    })


    const onConfirm = () => {
        if (!phase) {
            toast.error("This should not happen. Please contact the developers!");
            return;
        }


        // @ts-ignore
        generateInstance(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), modalState.labels, modalState.nonLabel, modalState.selectedFile, storage.get)
            .catch((err) => {
                toast.error("Failed to generate instances");
                cleanUp();
            })
            .finally(() => {
                toast.success("The instances are being generated. This may take a while, depending on the size of the dataset.");
                // close modal
                cleanUp();
                // trigger mutate
                mutateCallback();
            });
    }

    const cleanUp = () => {
        setModalState({
            labels: "0,1,2,3,4,5",
            nonLabel: "-",

            selectedFile: null as unknown as File,
        });
        closeModalCallback();
    }

    const onCancel = () => {
        toast.info("Canceled adding instances to phase");
        // close modal
        cleanUp();
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
                                    You are about to generate new instances for the phase.
                                </div>


                                <div className="flex flex-col items-left mb-6">
                                    <div className="font-bold text-lg">
                                        Labels
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiList className='basic-svg' />
                                        <input
                                            id="labels"
                                            name="labels"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            type="text"
                                            value={modalState.labels}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                labels: e.target.value
                                            })} />
                                    </div>
                                </div>


                                <div className="flex flex-col items-left mb-6">
                                    <div className="font-bold text-lg">
                                        Non-Label
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiMinus className='basic-svg' />
                                        <input
                                            id="nonlabel"
                                            name="nonlabel"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            type="text"
                                            value={modalState.nonLabel}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                nonLabel: e.target.value
                                            })} />
                                    </div>
                                </div> 
                                {/* only, if additional set */}
                                {additional && (
                                    <div className="flex flex-col items-left my-6">
                                        <div className="font-bold text-lg">
                                            File: {additionalFileName}
                                        </div>
                                        <div className="font-dm-mono-regular my-2">
                                            Some additional information is needed to generate the instances.
                                            For this, you need to upload a file.
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
                                )}

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

export default GenerateInstancesForPhaseModal;
