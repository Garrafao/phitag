// react

// icons
import { FiFile, FiFileText } from "react-icons/fi";

// models
import DummySelectable from "../../../lib/model/dummy/DummySelectable";
import Project from "../../../lib/model/project/model/Project";
import DropdownSelect from "../../generic/dropdown/dropdownselect";

// services
import useStorage from "../../../lib/hook/useStorage";
import { useState } from "react";
import { toast } from "react-toastify";
import { addUsages } from "../../../lib/service/phitagdata/PhitagDataResource";

const AddDataToProjectModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, project: Project, mutateCallback: Function }> = ({ isOpen, closeModalCallback, project, mutateCallback }) => {
    
    // hooks
    const storage = useStorage();

    // data and state
    const [modalState, setModalState] = useState({
        selectedFiletype: [new DummySelectable("Usage")],
        selectedFile: null as unknown as File,
    })


    const onConfirm = () => {
        if (modalState.selectedFile === null) {
            toast.warning("Please select a file.");
            return;
        }

        if (project === undefined || project === null) {
            toast.warning("Project is undefined. This should not happen. Please try again later.");
            return;
        }

        addUsages(project.getId().getOwner(), project.getId().getName(), modalState.selectedFile, storage.get).then(() => {
            toast.success("Successfully added data to project.");

            // close modal
            setModalState({
                ...modalState,
                selectedFile: null as unknown as File,
            });
            mutateCallback();
            closeModalCallback();
            
        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while adding data: " + error.response.data.message);
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        });
    }

    const onCancel = () => {
        toast.info("Canceled adding data to project.");
        setModalState({
            ...modalState,
            selectedFile: null as unknown as File,
        });
        closeModalCallback();
    }

    if (!isOpen || !project) {
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
                                    Project: {project.getDisplayname()}
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You are about to add new data to the project.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Select File Type
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <DropdownSelect 
                                        icon={<FiFile className="basic-svg" />} 
                                        items={modalState.selectedFiletype} 
                                        selected={modalState.selectedFiletype} 
                                        onSelectFunction={(item) => {}} 
                                        message={modalState.selectedFiletype[0].getName()} />
                                    </div>
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
                                            selectedFile: e.target.files[0],
                                        })}/>
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

export default AddDataToProjectModal;
