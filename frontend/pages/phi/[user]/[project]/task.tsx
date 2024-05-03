// Next
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React
import { useEffect } from "react";

// Toast
import { toast } from "react-toastify";

// services
import useAuthenticated from "../../../../lib/hook/useAuthenticated";
import { useFetchTasksOfProject } from "../../../../lib/service/tasks/TaskResource";

// Layout
import Layout from "../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../components/generic/layout/singlecontentlayout";
import ProjectTabBar from "../../../../components/specific/tab/projecttabbar";
import TaskTable from "../../../../components/generic/table/tasktable";
import FullLoadingPage from "../../../../components/pages/fullloadingpage";
import { FiFolder } from "react-icons/fi";
import Link from "next/link";
import LinkHead from "../../../../components/generic/linker/linkhead";

const TaskPage: NextPage = () => {

    const authenticated = useAuthenticated();

    const router = useRouter();
    const { user: username, project: projectname } = router.query as { user: string, project: string };

    const tasks = useFetchTasksOfProject(username, projectname, router.isReady);

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

                <LinkHead icon={<FiFolder className="stroke-2" />}
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
                            href: `/phi/${username}/${projectname}/task`,
                            name: "Tasks",
                        },
                    ]}
                />

                <div className="w-full flex flex-col 2xl:flex-row justify-between">
                    <ProjectTabBar />
                    <div className="flex mt-8 xl:mt-0 mx-4 space-x-4 justify-end">
                    </div>
                </div>

                <div className="m-8">
                    {tasks.isLoading ? <FullLoadingPage headtitle="Tasks" /> : <TaskTable tasks={tasks.tasks} showPhase={true} />}
                </div>


            </SingleContentLayout>
        </Layout>

    );
}

export default TaskPage;