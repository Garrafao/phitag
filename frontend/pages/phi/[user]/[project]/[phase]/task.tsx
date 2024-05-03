// Next
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React
import { useEffect } from "react";

// Toast
import { toast } from "react-toastify";

// services
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";
import { useFetchTasksOfPhase } from "../../../../../lib/service/tasks/TaskResource";

// Layout
import Layout from "../../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../../components/generic/layout/singlecontentlayout";
import TaskTable from "../../../../../components/generic/table/tasktable";
import FullLoadingPage from "../../../../../components/pages/fullloadingpage";
import PhaseTabBar from "../../../../../components/specific/tab/phasedatatab";
import LinkHead from "../../../../../components/generic/linker/linkhead";
import { FiLayers } from "react-icons/fi";

const TaskPage: NextPage = () => {

    const authenticated = useAuthenticated();

    const router = useRouter();
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };

    const tasks = useFetchTasksOfPhase(username, projectname, phasename, router.isReady);

    useEffect(() => {
        if (tasks.isError) {
            toast.error("An error occurred while fetching projects tasks");
            Router.push("/dashboard");
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, tasks.isError]);

    return (
        <Layout>

            <Head>
                <title>PhiTag : {projectname} : Tasks  </title>
            </Head>


            <SingleContentLayout>
                    <LinkHead icon={<FiLayers className="stroke-2" />}
                        links={[
                            {
                                href: `/phi/${username}`,
                                name: username,
                            },
                            {
                                href: `/phi/${username}/${projectname}`,
                                name: projectname,
                            },
                            {
                                href: `/phi/${username}/${projectname}/${phasename}`,
                                name: phasename,
                            },
                            {
                                href: `/phi/${username}/${projectname}/${phasename}/task`,
                                name: "Tasks",
                            }
                        ]}
                    />


                <div className="w-full flex flex-col 2xl:flex-row justify-between">
                    <PhaseTabBar />
                    <div className="flex mt-8 xl:mt-0 mx-4 space-x-4 justify-end">
                    </div>
                </div>

                <div className="m-8">
                    {tasks.isLoading ? <FullLoadingPage headtitle="Tasks" /> : <TaskTable tasks={tasks.tasks} showPhase={false} />}
                </div>


            </SingleContentLayout>
        </Layout>

    );
}

export default TaskPage;