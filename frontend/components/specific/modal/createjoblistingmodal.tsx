// React
import { useState } from "react";

// Next

// Icons
import { FiClipboard, FiEdit3, FiFeather, FiFolder, FiLayers, FiUnderline } from "react-icons/fi";

// Toast

// Services
import { useFetchPersonalProject } from "../../../lib/service/project/ProjectResource";

// Models
import useStorage from "../../../lib/hook/useStorage";
import Project from "../../../lib/model/project/model/Project";
import CreateJoblistingCommand from "../../../lib/model/joblisting/command/CreateJoblistingCommand";

// Components
import DropdownSelect from "../../generic/dropdown/dropdownselect";
import Checkbox from "../../generic/checkbox/checkbox";
import { toast } from "react-toastify";
import { postNewJoblisting } from "../../../lib/service/joblisting/JoblistingResource";
import { useFetchPhases } from "../../../lib/service/phase/PhaseResource";
import Phase from "../../../lib/model/phase/model/Phase";


const CreateJoblistingModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, mutateCallback: Function }> = ({ isOpen, closeModalCallback, mutateCallback }) => {

    // storage hook
    const storage = useStorage();
    const projects = useFetchPersonalProject("", isOpen);


    // Data and State
    const [modalState, setModalState] = useState({
        name: "",
        project: null as unknown as Project,
        open: false,
        phase:null as unknown as Phase,
        description: "",
    })
   
    const phases = useFetchPhases(
        modalState.project && modalState.project.getId() ? modalState.project.getId().getOwner() : "",
        modalState.project && modalState.project.getId() ? modalState.project.getId().getName() : ""
      );


    // handlers

    const onConfirm = () => {
        const command: CreateJoblistingCommand | null = verifyAndCreateCommand(modalState);
        if (command) {
            postNewJoblisting(command, storage.get)
                .then((res) => {
                    toast.success("You have successfully created a new joblisting!");

                    // close modal
                    setModalState({
                        name: "",
                        project: null as unknown as Project,
                        open: false,
                        phase:null as unknown as Phase,
                        description: "",
                    });
                    mutateCallback();
                    closeModalCallback();
                }).catch((error) => {
                    if (error?.response?.status === 500) {
                        toast.error("Error while create joblisting: " + error.response.data.message + "!");
                    } else {
                        toast.warning("The system is currently not available, please try again later!");
                    }
                });
        }
    }
  
      

  
    const onCancel = () => {
        toast.info("Canceled creating a new joblisting.");
        setModalState({
            name: "",
            project: null as unknown as Project,
            open: false,
            phase:null as unknown as Phase,
            description: "",
        });
        closeModalCallback();
    }


    if (!isOpen || projects.isLoading) {
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
                                    New Joblisting
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You are about to create a new Joblisting.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Joblisting
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiClipboard className='basic-svg' />
                                        <input
                                            id="jobname"
                                            name="Jobname"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder="Name"
                                            type={"text"}
                                            value={modalState.name}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                name: e.target.value
                                            })} />
                                    </div>
                                </div>
                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Project
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <DropdownSelect
                                            icon={<FiFolder className="basic-svg" />}
                                            items={projects.projects}
                                            selected={modalState.project ? [modalState.project] : []}
                                            onSelectFunction={(project: Project) => setModalState({
                                                ...modalState,
                                                project: project
                                            })}
                                            message={modalState.project ? modalState.project.getDisplayname() : "Select Project"}
                                        />
                                         {modalState.project && (
                                        <DropdownSelect
                                            icon={<FiLayers className="basic-svg" />}
                                            items={phases.phases}
                                            selected={modalState.phase ? [modalState.phase] : []}
                                            onSelectFunction={(phase: Phase) => setModalState({
                                                ...modalState,
                                                phase: phase
                                            })}
                                            message={modalState.phase ? modalState.phase.getDisplayname() : "Select Phase"}
                                        />)}
                                    </div>
                                </div>



                                <div className="flex w-full items-center py-2 px-3">
                                    <Checkbox selected={modalState.open} description={"Join Project without Waitinglist"} onClick={() => setModalState({
                                        ...modalState,
                                        open: !modalState.open
                                    })} />
                                </div>
                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Description
                                    </div>
                                    <div className="h-32 flex items-start border-l-2 py-2 px-3 my-6">
                                        <FiFeather className='basic-svg' />
                                        <textarea
                                            className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                                            name="description"
                                            placeholder="Description"
                                            value={modalState.description}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                description: e.target.value
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

export default CreateJoblistingModal;


function verifyAndCreateCommand(command: {
    name: string,
    project: Project,
    phase: Phase,
    open: boolean,
    description: string
}): CreateJoblistingCommand | null {
    if (command.name === '') {
        toast.warning("The name for the joblisting can not be empty.");
        return null;
    }
    if (!command.project) {
        toast.warning("You have to select a project.");
        return null;
    }
    return new CreateJoblistingCommand(
        command.name,
        command.project.getId().getOwner(),
        command.project.getId().getName(),
        command.phase.getId().getPhase(),
        command.open,
        command.description
    );
}

