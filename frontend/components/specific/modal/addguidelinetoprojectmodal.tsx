// React
import { useState } from "react";

// toast
import { toast } from "react-toastify";

// icons
import { FiFileText } from "react-icons/fi";

// services
import useStorage from "../../../lib/hook/useStorage";
import { addGuideline } from "../../../lib/service/guideline/GuidelineResource";

// models
import Project from "../../../lib/model/project/model/Project";

// components

const AddGuidelineToProjectModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, project: Project, mutateCallback: Function }> = ({ isOpen, closeModalCallback, project, mutateCallback }) => {

    const { get } = useStorage();
    const [selectedFile, setSelectedFile] = useState(null as File | null);

    const onConfirm = () => {
        if (!selectedFile) {
            toast.error("Please select a file");
            return;
        }

        if (!project) {
            toast.info("There seems to be some problem with the project. Please try again later.");
            return;
        }

        addGuideline(project.getId().getOwner(), project.getId().getName(), selectedFile, get).then((res) => {
            toast.success("Successfully added guideline");
            mutateCallback();
            closeModalCallback();
        }).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while adding guideline: " + error.response.data.message + "!");
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        });

        clear();


    }

    const onCancel = () => {
        clear();
        toast.info("Canceled adding guideline to project.");
    }

    const clear = () => {
        closeModalCallback();
        setSelectedFile(null);
    }


    if (!isOpen || !project) {
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
                                    Project: {project ? project.getName() : "None"}
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You are about to add a Guideline to the project.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Mardown-File
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiFileText className='basic-svg' />
                                        <input type="file" className="hide-upload-button pl-3 flex flex-auto outline-none border-none text-sm" onChange={(e: any) => setSelectedFile(e.target.files[0])} />
                                    </div>
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

export default AddGuidelineToProjectModal;
