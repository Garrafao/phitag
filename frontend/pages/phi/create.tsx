//Next Modules
import { NextPage } from "next";
import Head from "next/head";
import Router from "next/router";

// React
import { useEffect, useState } from "react";

// Toast
import { toast } from 'react-toastify';

// Icons
import { FiEye, FiFeather, FiFolder, FiGlobe } from "react-icons/fi";

// Services
import useStorage from "../../lib/hook/useStorage";
import useAuthenticated from "../../lib/hook/useAuthenticated";
import { useFetchAllLanguages } from "../../lib/service/language/LanguageResource";
import { useFetchAllVisibility } from "../../lib/service/visibility/VisibilityResource";
import { createProject } from "../../lib/service/project/ProjectResource";

// Models
import Language from "../../lib/model/language/model/Language";
import Visibility from "../../lib/model/visibility/model/Visibility";
import CreateProjectCommand from "../../lib/model/project/command/CreateProjectCommand";

// Layout
import Layout from "../../components/generic/layout/layout";
import SingleContentLayout from "../../components/generic/layout/singlecontentlayout";

// Components
import DropdownSelect from "../../components/generic/dropdown/dropdownselect";
import HelpButton from "../../components/generic/button/helpbutton";

const Create: NextPage = () => {

    // Data & Hooks
    const authenticated = useAuthenticated();
    const storage = useStorage();

    // Create Project state
    const [createProjectState, setCreateProjectState] = useState({
        projectName: "",
        language: null as unknown as Language,
        visibility: null as unknown as Visibility,
        description: "",
    });

    const languages = useFetchAllLanguages();
    languages.languages.sort((a, b) => a.getName().localeCompare(b.getName()));

    const visibilities = useFetchAllVisibility();
    visibilities.visibilities.sort((a, b) => a.getName().localeCompare(b.getName()));

    // Hanlers

    const handleCreate = () => {
        const command = verifyCreationAndBuild(createProjectState);
        if (command == null) {
            return;
        }

        const username = storage.get('USER');

        if (!username) {
            toast.warning("This should not happen. Please contact the administrator.");
            return;
        }

        createProject(command, storage.get)
            .then(() => Router.push(`/phi/${username}/${createProjectState.projectName}`))
            .catch((error) => {
                if (error?.response?.status === 500) {
                    toast.error("Error while creating the project: " + error.response.data.message + "!");
                    setCreateProjectState({
                        ...createProjectState,
                        projectName: "",
                        language: null as unknown as Language,
                        visibility: null as unknown as Visibility,
                        description: "",
                    });
                } else {
                    toast.warning("The system is currently not available, please try again later!");
                }
            });
    }


    // Other Hooks

    useEffect(() => {
        if (languages.isError || visibilities.isError) {
            toast.error("An error occurred while fetching data.");
            Router.push("/dashboard");
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, languages.isError, visibilities.isError]);

    return (
        <Layout>

            <Head>
                <title>PhiTag: Create Project</title>
            </Head>

            <SingleContentLayout>

                <form className="font-dm-mono-medium">

                    <div className="flex flex-row justify-between">

                        <h1 className="font-bold text-2xl">
                            Create a new Project!
                        </h1>

                        <HelpButton
                            title="Help: Create Project"
                            tooltip="Help: Create Project"
                            text="
                                Here you can create a new project. 
                                You can choose a name, a language and a visibility. 
                                The name must be unique not used for a different project created by you. 
                                The visibility determines who can see your project. 
                                If you choose private, only you can see it. 
                                If you choose public, everyone can see it.
                                "
                            reference="none"
                            linkage={false}
                        />
                    </div>

                    <div className="flex flex-col items-left my-6">
                        <div className="font-bold text-lg">
                            Project Name
                        </div>
                        <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                            <FiFolder className='basic-svg' />
                            <input
                                id="projectname"
                                name="projectname"
                                className="pl-3 flex flex-auto outline-none border-none"
                                placeholder="Project name"
                                type={"text"}
                                value={createProjectState.projectName}
                                onChange={(e: any) => setCreateProjectState({
                                    ...createProjectState,
                                    projectName: e.target.value
                                })} />
                        </div>
                    </div>

                    <div className="flex flex-col xl:flex-row justify-between my-6 space-y-4 xl:space-y-0 xl:space-x-8">

                        <div className="xl:w-1/2 flex flex-col grow items-left xl:mr-4">
                            <div className="font-bold text-lg">
                                Language
                            </div>
                            <div className="py-2 px-3 border-b-2 mt-2">
                                <DropdownSelect
                                    icon={<FiGlobe className="basic-svg" />}
                                    items={languages.languages}
                                    selected={createProjectState.language ? [createProjectState.language] : []}
                                    onSelectFunction={(language: Language) => setCreateProjectState({
                                        ...createProjectState,
                                        language: language
                                    })}
                                    message={createProjectState.language ? createProjectState.language.getVisiblename() : "None selected yet"} />
                            </div>
                        </div>

                        <div className="xl:w-1/2 flex flex-col grow items-left xl:ml-4">
                            <div className="font-bold text-lg">
                                Visibility
                            </div>
                            <div className="py-2 px-3 border-b-2 mt-2">
                                <DropdownSelect
                                    icon={<FiEye className="basic-svg" />}
                                    items={visibilities.visibilities}
                                    selected={createProjectState.visibility ? [createProjectState.visibility] : []}
                                    onSelectFunction={(visibility: Visibility) => setCreateProjectState({
                                        ...createProjectState,
                                        visibility: visibility
                                    })}
                                    message={createProjectState.visibility ? createProjectState.visibility.getVisiblename() : "None selected yet"} />
                            </div>
                        </div>
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
                                value={createProjectState.description}
                                onChange={(e: any) => setCreateProjectState({
                                    ...createProjectState,
                                    description: e.target.value
                                })} />
                        </div>
                    </div>


                    <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100" onClick={() => handleCreate()}>
                        Create
                    </button>
                </form>

            </SingleContentLayout>
        </Layout >
    )
}

export default Create;

function verifyCreationAndBuild(createProjectState: {
    projectName: string;
    language: Language;
    visibility: Visibility;
    description: string;
}): CreateProjectCommand | null {
    if (!createProjectState.projectName) {
        toast.error("Enter a project name.");
        return null;
    }
    if (!createProjectState.language || createProjectState.language == null || createProjectState.language == undefined) {
        toast.error("Select a language.");
        return null;
    }

    if (!createProjectState.visibility || createProjectState.visibility == null || createProjectState.visibility == undefined) {
        toast.error("Select a visibility.");
        return null;
    }

    return new CreateProjectCommand(
        createProjectState.projectName,
        createProjectState.visibility.getName(),
        createProjectState.language.getName(),
        createProjectState.description
    );
}