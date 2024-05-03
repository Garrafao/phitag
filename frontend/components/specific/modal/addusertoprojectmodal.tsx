// react
import { useEffect, useState } from "react";

// toast
import { toast } from "react-toastify";

// icons 
import { FiFolder, FiUserCheck } from "react-icons/fi";

// services
import { useFetchAllEntitlements } from "../../../lib/service/entitlement/EntitlementResource";
import { useFetchPersonalProject } from "../../../lib/service/project/ProjectResource";

// components
import DropdownSelect from "../../generic/dropdown/dropdownselect";

// models
import User from "../../../lib/model/user/model/User";
import Entitlement from "../../../lib/model/entitlement/model/Entitlement";
import Project from "../../../lib/model/project/model/Project";
import CreateAnnotatorCommand from "../../../lib/model/annotator/command/CreateAnnotatorCommand";
import { addAnnotator } from "../../../lib/service/annotator/AnnotatorResource";
import useStorage from "../../../lib/hook/useStorage";
import Router from "next/router";


const AddUserToProjectModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, user: User, mutateCallback: Function }> = ({ isOpen, closeModalCallback, user, mutateCallback }) => {

    // storage hook
    const storage = useStorage();

    // Data and State
    const [modalState, setModalState] = useState({
        entitlement: Array<Entitlement>(),
        project: Array<Project>(),
    })

    const entitlements = useFetchAllEntitlements(isOpen);
    entitlements.entitlements.sort((a, b) => a.getName().localeCompare(b.getName()));

    const projects = useFetchPersonalProject("", isOpen);
    projects.projects.sort((a, b) => a.getName().localeCompare(b.getName()));

    // handlers
    const onConfirm = () => {
        const command = verifyCreateAnnotatorCommand(user.getUsername(), user.isBot(), modalState);
        if (command != null) {
            addAnnotator(command, storage.get).then((res) => {
                toast.success("Annotator added to the project successfully");

                // close modal
                setModalState({
                    entitlement: Array<Entitlement>(),
                    project: Array<Project>(),
                });
                mutateCallback();
                closeModalCallback();

            }).catch((error) => {
                if (error?.response?.status === 500) {
                    toast.error("Error while adding user: " + error.response.data.message + "!");
                } else {
                    toast.warning("The system is currently not available, please try again later!");
                }
            });
        }
    }

    const onCancel = () => {
        toast.info("Canceled adding user to the project.");
        setModalState({
            entitlement: Array<Entitlement>(),
            project: Array<Project>(),
        });
        closeModalCallback();
    }

    // effects
    useEffect(() => {
        if (entitlements.isError || projects.isError) {
            toast.error("An error occured while fetching entitlements or projects");
            Router.push("/dashboard");
        }
    }, [entitlements.isError, projects.isError]);

    if (!isOpen || !user) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium overflow-auto" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                    User: {user ? user.getDisplayname() : "None"}
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You are about to add the user to one of your projects.
                                </div>


                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Project
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <DropdownSelect
                                            icon={<FiFolder className="basic-svg" />}
                                            items={projects.projects}
                                            selected={modalState.project}
                                            onSelectFunction={(project: Project) => setModalState({ ...modalState, project: [project] })}
                                            message={modalState.project.length == 0 ? "Select a Project" : modalState.project[0].getDisplayname()} />
                                    </div>
                                </div>

                                {user.isBot() ? <div /> :
                                    <div className="flex flex-col items-left my-6">
                                        <div className="font-bold text-lg">
                                            Entitlement
                                        </div>
                                        <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                            <DropdownSelect
                                                icon={<FiUserCheck className="basic-svg" />}
                                                items={entitlements.entitlements}
                                                selected={modalState.entitlement}
                                                onSelectFunction={(entitlement: Entitlement) => setModalState({ ...modalState, entitlement: [entitlement] })}
                                                message={modalState.entitlement.length == 0 ? "Select an Entitlement" : modalState.entitlement[0].getVisiblename()} />
                                        </div>
                                    </div>
                                }
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

export default AddUserToProjectModal;


function verifyCreateAnnotatorCommand(username: string, isBot: boolean, modalState: {
    project: Project[];
    entitlement: Entitlement[];
}): CreateAnnotatorCommand | null {
    if (username == null) {
        toast.warning("This should not appear. Please contact the administrator");
        return null;
    }

    if (modalState.project.length != 1) {
        toast.warning("No project selected");
        return null;
    }

    if (!isBot && modalState.entitlement.length != 1) {
        toast.warning("No entitlement selected");
        return null;
    }

    return new CreateAnnotatorCommand(
        modalState.project[0].getId().getOwner(),
        modalState.project[0].getId().getName(),
        username,
        modalState.entitlement[0]?.getName() ?? ""
    );
}