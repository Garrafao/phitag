
//Next Modules
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React Module
import { useEffect, useState } from "react";

// React Icons

// Toast
import { toast } from "react-toastify";

// Services
import useAuthenticated from "../../../../lib/hook/useAuthenticated";
import { useFetchSelfEntitlement } from "../../../../lib/service/annotator/AnnotatorResource";
import { useFetchProject } from "../../../../lib/service/project/ProjectResource";

// Models
import ENTITLEMENTS from "../../../../lib/model/entitlement/Entitlements";

// Components
import FullLoadingPage from "../../../../components/pages/fullloadingpage";
import ProjectTabBar from "../../../../components/specific/tab/projecttabbar";
import PhaseCarousel from "../../../../components/specific/carousel/phasecarousel";
import ProjectGuidelinesCard from "../../../../components/specific/card/projectguidelinescard";

// Layouts
import Layout from "../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../components/generic/layout/singlecontentlayout";
import ProjectOverviewCard from "../../../../components/generic/card/projectoverviewcard";
import Link from "next/link";
import { FiFolder } from "react-icons/fi";
import LinkHead from "../../../../components/generic/linker/linkhead";


const ProjectPage: NextPage = () => {

    // hooks
    const authenticated = useAuthenticated();
    const router = useRouter();
    const { user: username, project: projectname } = router.query as { user: string, project: string };
    const project = useFetchProject(username, projectname, router.isReady);

    const entitlement = useFetchSelfEntitlement(username, projectname, !project.isLoading && !project.isError);
    


    useEffect(() => {
        if (project.isError) {
            toast.error("An error occurred while fetching the project.");
            Router.push("/dashboard");
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, project.isError]);

    if (project.isLoading || project.isError || project.project === null) {
        return <FullLoadingPage headtitle="Project " />
    }

    return (
        <Layout>

            <Head>
                <title>PhiTag : {project.project?.getId().getName()}</title>
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
                        }
                    ]}
                />
                <ProjectTabBar />

                <div className="mt-10 mx-auto max-w-7xl">

                    <div className="flex flex-col xl:flex-row justify-between xl:space-x-8">

                        <div className="flex flex-col xl:w-1/2">
                            <ProjectGuidelinesCard
                                project={project.project}
                                showAddGuideline={project.project?.isActive() && entitlement.entitlement === ENTITLEMENTS.ADMIN} />
                        </div>

                        {/* @ts-ignore */}
                        <div className="flex flex-col xl:w-1/2">
                            <ProjectOverviewCard project={project.project} />
                        </div>
                    </div>

                    <div className="my-8">
                        <PhaseCarousel project={project.project} />
                    </div>

                </div>

            </SingleContentLayout>


        </Layout>
    );
}

export default ProjectPage;

