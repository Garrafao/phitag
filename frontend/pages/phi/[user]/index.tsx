// React Modules
import { useEffect, useState } from "react";
// Next Modules
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// Toast
import { toast } from "react-toastify";

// Icons
import { FiChevronDown, FiChevronUp, FiCpu, FiUser, FiUserPlus } from "react-icons/fi";

// Services
import useStorage from "../../../lib/hook/useStorage";
import useAuthenticated from "../../../lib/hook/useAuthenticated";
import { useFetchUser } from "../../../lib/service/user/UserResource";

// Models
import User from "../../../lib/model/user/model/User";

// Custom Components
import AddUserToProjectModal from "../../../components/specific/modal/addusertoprojectmodal";
import FullLoadingPage from "../../../components/pages/fullloadingpage";

// Custom Layouts
import Layout from "../../../components/generic/layout/layout";
import SingleContentLayout from "../../../components/generic/layout/singlecontentlayout";
import { useFetchProjectsOfUser } from "../../../lib/service/project/ProjectResource";
import ProjectCard from "../../../components/generic/card/projectcard";
import LoadingComponent from "../../../components/generic/loadingcomponent";

import { Doughnut } from "react-chartjs-2";
import useFetchUserStatistic from "../../../lib/service/statistic/userstatistic/UserStatisticResource";
import { translateMapToChartJSData } from "../../../lib/service/statistic/StatisticChartJSTranslator";
import StatisticComponent from "../../../components/generic/other/StatisticComponent";

const UserPage: NextPage = () => {

    // Data Fetching
    const storage = useStorage();
    const authenticated = useAuthenticated();


    const router = useRouter();
    const { user: username } = router.query as { user: string };
    const userEntity = useFetchUser(username as string, router.isReady);

    // Modal/Create Annotator and add to project
    const [modalState, setModalState] = useState({
        isOpen: false,
    });


    // hooks
    useEffect(() => {
        if (userEntity.isError) {
            toast.error("An error occurred while fetching the user.");
            Router.push("/dashboard");
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, userEntity.isError]);

    // Pages
    if (!router.isReady || userEntity.isLoading || !userEntity.user) {
        return <FullLoadingPage headtitle="Project " />
    }

    return (
        <Layout>

            <StatisticComponent />

            <Head>
                <title>PhiTag: {userEntity.user?.getUsername()}</title>
            </Head>

            <SingleContentLayout>
                <div className="flex flex-col xl:flex-row xl:px-4 xl:space-x-8 space-y-8 xl:space-y-0">
                    <UserInformation user={userEntity.user} storage={storage}
                        callbackModal={
                            () => {
                                setModalState({
                                    isOpen: true,
                                });
                            }
                        } />

                    <TabPage />
                </div>

            </SingleContentLayout>

            <AddUserToProjectModal isOpen={modalState.isOpen} closeModalCallback={() => setModalState({
                isOpen: false,
            })} user={userEntity.user} mutateCallback={() => { }} />
        </Layout >
    );
};

export default UserPage;

const UserInformation = ({ user, storage, callbackModal }: { user: User, storage: any, callbackModal: Function }) => {

    const hidden = user.getUsername() == storage.get("USER") ? " hidden" : "";

    return (

        <div className="flex flex-col self-center xl:self-auto items-center w-full sm:w-96 xl:mx-auto">
            <div className="w-2/3 flex self-center ">
                <FiUser className="w-full h-full aspect-square text-base16-gray-300" />
            </div>

            <div className="mt-6 font-dm-mono-medium text-xl">
                {user.getUsername()} {user.getUsername() == storage.get("USER") ? "(You)" : ""} {user.isBot() ? <FiCpu className="inline stroke-base16-gray-500" /> : ""}
            </div>

            <div className="w-1/2 mx-16 my-8 border-b-2" />

            <div className="flex flex-col w-full">
                <div className="font-dm-mono-medium text-xl self-center">
                    Language(s)
                </div>

                <div className="flex mt-2 font-dm-mono-light text-left self-center">
                    {user.getLanguages().length > 0 ?
                        user.getLanguages().map((language) => language.getName()).join(", ")
                        : user.isBot() ? "All suported Languages" : "No language specified"
                    }
                </div>
            </div>

            <div className="w-1/2 mx-16 my-8 border-b-2 " />

            <div className="flex flex-col w-full ">
                <div className="font-dm-mono-medium text-xl self-center">
                    Description
                </div>

                <div className="flex mt-2 font-dm-mono-light self-center">
                    {user.getDescription() ? user.getDescription() : "No description provided"}
                </div>
            </div>

            <div className={`flex mt-8 self-end xl:mt-auto ${hidden}`}>
                <button className="text-base16-gray-400  border-base16-gray-400 hover:border-base16-gray-900 hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                    onClick={() => callbackModal()}>
                    <div className="flex my-2 mx-4 font-dm-mono-medium font-bold items-center">
                        Add to Project
                        <FiUserPlus className="ml-2 basic-svg" />
                    </div>
                </button>
            </div>



        </div>

    );
}



const TabPage = () => {

    const [tab, setTab] = useState(0);

    return (
        <div className="w-full h-full flex flex-col justify-between">

            <div className="my-2 2xl:mx-4 flex flex-col 2xl:flex-row justify-start space-y-2 2xl:space-x-10 2xl:space-y-0">
                <Tab title="Overview" active={tab === 0} onClick={() => setTab(0)} />
                <Tab title="Projects" active={tab === 1} onClick={() => setTab(1)} />
                <Tab title="Statistic" active={tab === 2} onClick={() => setTab(2)} />
            </div>

            <div className="w-full flex my-2 2xl:mx-4">
                <div className="w-full">
                    {tab === 0 && <OverviewTab />}
                    {tab === 1 && <ProjectTab />}
                    {tab === 2 && <StatisticTab />}

                </div>
            </div>
        </div>
    );

}

const Tab = ({ title, active, onClick }: { title: string, active: boolean, onClick: Function }) => {

    return (

        <button className={active ? "project-tab-selected" : "project-tab-unselected"} onClick={() => onClick()}>
            <div className="my-2 mx-10">
                {title}
            </div>
        </button>

    );
}

const OverviewTab = () => {


    const router = useRouter();
    const { user: username } = router.query as { user: string };

    const projects = useFetchProjectsOfUser(username, router.isReady);

    return (
        <div className="flex flex-col 2xl:flex-row">

            {/* Project Overview 3 */}
            <div className="w-full 2xl:w-1/2 flex flex-col my-2">
                <div className="font-dm-mono-medium text-2xl">
                    Recent Projects:
                </div>

                {
                    projects.isLoading ? <LoadingComponent /> :
                        <div className="project-list">
                            {
                                projects.projects.length > 0 ?
                                    projects.projects.slice(0, 3).map((project) => (
                                        <ProjectCard key={project.getId().getOwner() + "-" + project.getId().getName()} project={project} />
                                    ))
                                    : <div className="text-center text-xl font-dm-mono-medium">No projects found</div>
                            }
                        </div>
                }
            </div>

            {/* User Statistics */}
            <div className="w-full 2xl:w-1/2 flex flex-col my-2 mx-4">
                <AnnotationsPerProject />
            </div>
        </div>
    );
}


const ProjectTab = () => {

    const router = useRouter();
    const { user: username } = router.query as { user: string };

    const projects = useFetchProjectsOfUser(username as string, router.isReady);
    const [shortview, setShortview] = useState(true);

    return (
        <div className="w-full flex flex-col">

            {
                projects.isLoading ? <LoadingComponent /> :
                    <div className="project-list">
                        {
                            projects.projects.length > 0 ?
                                projects.projects.slice(0, shortview ? 3 : undefined).map((project) => (
                                    <ProjectCard key={project.getId().getOwner() + "-" + project.getId().getName()} project={project} />
                                ))
                                : <div className="text-center text-xl font-dm-mono-medium">No projects found</div>
                        }
                    </div>
            }

            <div className="flex self-end">
                <button className="text-base16-gray-400  border-base16-gray-400 hover:border-base16-gray-900 hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                    onClick={() => setShortview(!shortview)}>
                    <div className="flex my-2 mx-4 font-dm-mono-medium font-bold items-center">
                        {shortview ? "Show more" : "Show less"}
                        {
                            shortview ?
                                <FiChevronDown className="ml-2" />
                                : <FiChevronUp className="ml-2" />
                        }
                    </div>
                </button>
            </div>
        </div>
    );

}


const AnnotationsPerProject = () => {

    const router = useRouter();
    const { user: username } = router.query as { user: string };

    const statistics = useFetchUserStatistic(username, router.isReady);

    if (statistics.isLoading || !router.isReady) {
        return <LoadingComponent />
    }

    if (statistics.statistic == null || statistics.statistic.getPojectAnnotationCountMap().size === 0) {
        return (
            <div className="flex flex-col">
                <div className="font-dm-mono-medium text-2xl mb-4">
                    Annotations per Project:
                </div>
                <div className="flex flex-col justify-center mx-auto max-w-lg">
                    <div className="text-center text-xl font-dm-mono-medium">No statistics found</div>
                </div>
            </div>
        );
    }

    const options = {
        plugins: {
            legend: {
                display: true,
                position: 'bottom' as const,
                labels: {
                    fontColor: '#151515',
                    fontSize: 16,
                    boxWidth: 16,
                    padding: 16,
                    usePointStyle: true,
                },
            },
        },
    };

    const config = {
        type: 'doughnut',
        data: translateMapToChartJSData("Annotations", statistics.statistic.getPojectAnnotationCountMap()),

    };

    return (
        <div className="flex flex-col">
            <div className="font-dm-mono-medium text-2xl mb-4">
                Annotations per Project:
            </div>

            <div className="flex flex-col justify-center mx-auto max-w-lg">
                <Doughnut {...config} options={options} />
            </div>
        </div>
    );
}

const ProjectLanguages = () => {

    const router = useRouter();
    const { user: username } = router.query as { user: string };

    const statistics = useFetchUserStatistic(username, router.isReady);

    if (statistics.isLoading || !router.isReady) {
        return <LoadingComponent />
    }

    if (statistics.statistic == null || statistics.statistic.getLanguageCountMap().size === 0) {
        return (
            <div className="flex flex-col">
                <div className="font-dm-mono-medium text-2xl mb-4">
                    Projects per Language:
                </div>
                <div className="flex flex-col justify-center mx-auto max-w-lg">
                    <div className="text-center text-xl font-dm-mono-medium">No statistics found</div>
                </div>
            </div>
        )
    }

    const options = {
        plugins: {
            legend: {
                display: true,
                position: 'bottom' as const,
                labels: {
                    fontColor: '#151515',
                    fontSize: 16,
                    boxWidth: 16,
                    padding: 16,
                    usePointStyle: true,
                },
            },
        },
    };

    const config = {
        type: 'doughnut',
        data: translateMapToChartJSData("Projects", statistics.statistic.getLanguageCountMap()),

    };

    return (
        <div className="flex flex-col">
            <div className="font-dm-mono-medium text-2xl mb-4">
                Projects per Language:
            </div>
            <div className="flex flex-col justify-center mx-auto max-w-lg">
                <Doughnut {...config} options={options} />
            </div>
        </div>
    );
}

const AnnotationTypes = () => {

    const router = useRouter();
    const { user: username } = router.query as { user: string };

    const statistics = useFetchUserStatistic(username, router.isReady);

    if (statistics.isLoading || !router.isReady) {
        return <LoadingComponent />
    }

    if (statistics.statistic == null || statistics.statistic.getAnnotationTypeCountMap().size === 0) {
        return (
            <div className="flex flex-col">
                <div className="font-dm-mono-medium text-2xl mb-4">
                    Annotation-Types used:
                </div>
                <div className="flex flex-col justify-center mx-auto max-w-lg">
                    <div className="text-center text-xl font-dm-mono-medium">No statistics found</div>
                </div>
            </div>
        );

    }

    const options = {
        plugins: {
            legend: {
                display: true,
                position: 'bottom' as const,
                labels: {
                    fontColor: '#151515',
                    fontSize: 16,
                    boxWidth: 16,
                    padding: 16,
                    usePointStyle: true,
                },
            },
        },
    };

    const config = {
        type: 'doughnut',
        data: translateMapToChartJSData("Annotation-Types", statistics.statistic.getAnnotationTypeCountMap()),

    };

    return (
        <div className="flex flex-col">
            <div className="font-dm-mono-medium text-2xl mb-4">
                Annotation-Types used:
            </div>
            <div className="flex flex-col justify-center mx-auto max-w-lg">
                <Doughnut {...config} options={options} />
            </div>
        </div>
    );
}

const StatisticTab = () => {

    return (
        <div className="flex flex-col 2xl:flex-row justify-between ">
            <AnnotationsPerProject />
            <ProjectLanguages />
            <AnnotationTypes />
        </div>
    );
}