// React
import { useState, useEffect, useCallback } from "react";

// toast
import { toast } from "react-toastify";

// icons
import { FiEye, FiFeather, FiFileText, FiFolder, FiGlobe, FiUser } from "react-icons/fi";

// services
import useStorage from "../../../lib/hook/useStorage";
import { addGuideline } from "../../../lib/service/guideline/GuidelineResource";
import Router, { useRouter } from "next/router";




// Services
import useAuthenticated from "../../../lib/hook/useAuthenticated";

import { useFetchPersonalData, updateUser as updateUserApi } from "../../../lib/service/user/UserResource";
import { useFetchProject, UpdateProject as updateProjectApi } from "../../../lib/service/project/ProjectResource";
import { useFetchAllLanguages } from "../../../lib/service/language/LanguageResource";
import { useFetchAllVisibility } from "../../../lib/service/visibility/VisibilityResource";



// Models
import UserData from "../../../lib/model/user/model/UserData";
import Language from "../../../lib/model/language/model/Language";
import Visibility from "../../../lib/model/visibility/model/Visibility";
import UpdateUserCommand from "../../../lib/model/user/command/UpdateUserCommand";


// models
import Project from "../../../lib/model/project/model/Project";

// components
import DropdownSelect from "../../generic/dropdown/dropdownselect";
import UpdateProjectCommand from "../../../lib/model/project/command/UpdateProjectCommand";
import ProjectId from "../../../lib/model/project/model/ProjectId";
import SingleContentLayout from "../../generic/layout/singlecontentlayout";
import HelpButton from "../../generic/button/helpbutton";
import { language } from "gray-matter";
import Togglebox from "../../generic/checkbox/togglebox";
import UsageModal from "../card/usagecard";

const EditProjectModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, mutateCallback: Function }> = ({ isOpen, closeModalCallback, mutateCallback }) => {

    const { get } = useStorage();
    const authenticated = useAuthenticated();

    const router = useRouter();

    const storage = useStorage()

    const languages = useFetchAllLanguages();

    const visibilities = useFetchAllVisibility();

    languages.languages.sort((a, b) => a.getName().localeCompare(b.getName()));
    visibilities.visibilities.sort((a, b) => a.getName().localeCompare(b.getName()));

    const { user: username, project: projectname } = router.query as { user: string, project: string };
    const personalProjectData = useFetchProject(username, projectname, router.isReady);



    // update project state
    const [updateProject, setUpdateProject] = useState({
        newname: "",
        newdescription: "",
        language: null as unknown as Language,
        visibility: null as unknown as Visibility,
        active: true
    });



    // callback function to set the state of the update project language and visibility
    const setCorrectState = useCallback(() => {
        if (!personalProjectData.isLoading && !personalProjectData.isError && personalProjectData.project != null) {
            if (updateProject.language === null) {
                setUpdateProject({
                    ...updateProject,
                    language: personalProjectData.project.getLanguage() || [],
                })
            }

            if (updateProject.visibility == null) {
                setUpdateProject({
                    ...updateProject,
                    visibility: personalProjectData.project.getVisibility() || null,
                })
            }


        }


    }, [personalProjectData, updateProject]);

    useEffect(() => {
        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }

        if (personalProjectData.isError) {
            toast.error("Error while fetching project data!");
            Router.push("/dashboard");
        }

        setCorrectState();

    }, [authenticated, personalProjectData.isError, setCorrectState]);


    const onConfirm = () => {
        if (personalProjectData === null) {
            toast.warning("This should not happen. Please contact the developer");
            return;
        }


        const updateCommand = new UpdateProjectCommand(
            updateProject.newname,
            updateProject.newdescription,
            updateProject.visibility.getName(),
            updateProject.language.getName(),
            updateProject.active
        );





        if (UpdateProjectCommand == null) {
            toast.error("Please enter details");
            return;
        }

        updateProjectApi(username, projectname, updateCommand, storage.get).then(() => {
            toast.success("Project updated sucesfully.");
            mutateCallback();
            closeModalCallback();

        }).catch(error => {
            if (error?.response?.status === 500) {
                toast.error("Error while updating data: " + error.response.data.message + "!");
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        })


        clear();

    }

    const onCancel = () => {
        clear();
        toast.info("Canceled updating projet.");
    }

    const clear = () => {
        closeModalCallback();

    }


    if (!isOpen) {
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



                                <div className="flex flex-row justify-between items-center">
                                    <h1 className="font-dm-mono-medium font-black text-3xl">
                                        Edit Project
                                    </h1>

                                    <HelpButton
                                        title="Help: Edit Project"
                                        tooltip="Help: Edit Project"
                                        text="Here you can edit your project details. You can change your project visible name, description, language and visibility. 
                                        Additionally, you can also active and deactive your project.    
                                            After saving your change, you can see the changes in your project profile."
                                        reference=""
                                        linkage={false}
                                    />
                                </div>


                                <div className="font-dm-mono-medium mt-6">


                                    <div className="flex flex-col xl:flex-row justify-between my-6 space-y-4 xl:space-y-0 xl:space-x-8">

                                        <div className="xl:w-1/2 flex flex-col grow items-left xl:mr-4">
                                            <div className="font-bold text-lg">
                                                Project name
                                            </div>
                                            <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                                <FiFolder className="basic-svg" />
                                                <input
                                                    id="newname"
                                                    name="newname"
                                                    className="pl-3 flex flex-auto outline-none border-none placeholder:text-base16-gray-900"
                                                    type={"text"}
                                                    placeholder={personalProjectData.project.getDisplayname()}
                                                    value={updateProject.newname || personalProjectData.project.getDisplayname()}
                                                    onChange={(e: any) => setUpdateProject({
                                                        ...updateProject,
                                                        newname: e.target.value,
                                                    })}
                                                />
                                            </div>
                                        </div>

                                    </div>


                                </div>
                                <div className="flex flex-col xl:flex-row justify-between my-6 space-y-4 xl:space-y-0 xl:space-x-8">

                                    <div className="xl:w-1/2 flex flex-col grow items-left xl:mr-4">
                                        <div className="font-bold text-lg">
                                            Visibility
                                        </div>
                                        <div className="flex flex-auto items-center border-b-2 py-2 px-3">
                                            <DropdownSelect
                                                icon={<FiEye className="basic-svg" />}
                                                items={visibilities.visibilities}
                                                selected={updateProject.visibility ? [updateProject.visibility] : []} // Convert to an array

                                                onSelectFunction={(item: Visibility) => setUpdateProject({
                                                    ...updateProject,
                                                    visibility: item,
                                                })}
                                                message={updateProject.visibility ? updateProject.visibility.getVisiblename() : ""}
                                            />

                                        </div>
                                    </div>
                                </div>
                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Languages
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <DropdownSelect
                                            icon={<FiGlobe className="basic-svg" />}
                                            items={languages.languages}
                                            selected={updateProject.language ? [updateProject.language] : []}

                                            onSelectFunction={(language: Language) => setUpdateProject({
                                                ...updateProject,
                                                language: language
                                            })}

                                            message={updateProject.language ? updateProject.language.getVisiblename() : "None selected yet"}
                                        />
                                    </div>
                                </div>
                                <div className="flex flex-col items-left my-6r font-bold text-lg">
                                    <Togglebox
                                        selected={updateProject.active}
                                        left="Archived"
                                        right="Active"
                                        onClick={() => setUpdateProject({
                                            ...updateProject,
                                            active: !updateProject.active
                                        })} />

                                </div>


                                <div className="flex flex-col items-left my-4">
                                    <div className="font-bold text-lg">
                                        Description
                                    </div>
                                    <div className="h-32 flex items-start border-l-2 py-2 px-3 my-6">
                                        <FiFeather className='basic-svg' />
                                        <textarea
                                            className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                                            name="context"
                                            placeholder={personalProjectData.project.getDescription()}
                                            value={updateProject.newdescription || personalProjectData.project.getDescription()}
                                            onChange={(e: any) => setUpdateProject({
                                                ...updateProject,
                                                newdescription: e.target.value,
                                            })} />

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
            </div>
        </div>

    )
}

export default EditProjectModal;
/* function verifyUpdate(project: Project, updateProject: {
    newname: string,
    newdescription: string,
    language: Language,
    visibility: Visibility,
    active: boolean
}): UpdateProjectCommand | null {


    return new UpdateProjectCommand(
        updateProject.newname,
        updateProject.newdescription,
        updateProject.visibility.getName(),
        updateProject.language.getName(),
        updateProject.active
    );
}
 */


