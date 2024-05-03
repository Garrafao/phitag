// Next
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React
import { useEffect, useState } from "react";

// Toast
import { toast } from "react-toastify";

// React Icons
import { FiDownload, FiFilePlus, FiFolder, FiPlus } from "react-icons/fi";

// services
import useAuthenticated from "../../../../lib/hook/useAuthenticated";
import { exportUsage } from "../../../../lib/service/phitagdata/PhitagDataResource";
import { useFetchSelfEntitlement } from "../../../../lib/service/annotator/AnnotatorResource";


// Models
import ENTITLEMENTS from "../../../../lib/model/entitlement/Entitlements";

// Components

// Layouts

import SingleContentLayout from "../../../../components/generic/layout/singlecontentlayout";
import IconButtonOnClick from "../../../../components/generic/button/iconbuttononclick";
import ProjectTabBar from "../../../../components/specific/tab/projecttabbar";
import { useFetchProject } from "../../../../lib/service/project/ProjectResource";
import useStorage from "../../../../lib/hook/useStorage";
import FullLoadingPage from "../../../../components/pages/fullloadingpage";
import Layout from "../../../../components/generic/layout/layout";
import UsageTable from "../../../../components/specific/table/usagetable";
import Link from "next/link";
import LinkHead from "../../../../components/generic/linker/linkhead";


const PhaseDataPage: NextPage = () => {

    // data
    const authenticated = useAuthenticated();
    const storage = useStorage();

    const router = useRouter();
    const { user: username, project: projectname } = router.query as { user: string, project: string };

    const project = useFetchProject(username, projectname, router.isReady);
    const entitlements = useFetchSelfEntitlement(username, projectname, router.isReady);

    // handlers

    // Modal Data Project
    const [modalState, setModalState] = useState({
        addDataToProjectModal: false,
    });

    const handleExport = () => {
        exportUsage(username, projectname, storage.get)
            .then((response) => {
                toast.success("Exported data");
            })
            .catch((error) => {
                if (error?.response?.status === 500) {
                    toast.error("Error while exporting data: " + error.response.data.message + "!");
                } else {
                    toast.warning("The system is currently not available, please try again later!");
                }
            });
    }

    // hooks


    useEffect(() => {
        if (project.isError) {
            toast.error("An error occurred while fetching project");
            Router.push("/dashboard");
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, project.isError]);

    if (project.isLoading || !project.project) {
        return <FullLoadingPage headtitle="Usages" />
    }

    return (
        <Layout>

            <Head>
                <title>PhiTag : {project.project.getDisplayname()}  </title>
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
                            href: `/phi/${username}/${projectname}/data`,
                            name: "Data",
                        },
                    ]}
                />

                <div className="w-full flex flex-col 2xl:flex-row justify-between">
                    <ProjectTabBar />
                    <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                        <IconButtonOnClick
                            icon={<FiFilePlus className="basic-svg" />}
                            tooltip="Add Data"
                            onClick={() => setModalState({
                                ...modalState,
                                addDataToProjectModal: true,
                            })}
                            hide={entitlements.entitlement !== ENTITLEMENTS.ADMIN} />
                        <IconButtonOnClick
                            icon={<FiDownload className="basic-svg " />}
                            tooltip="Download Phase Data"
                            onClick={() => handleExport()}
                            hide={entitlements.entitlement !== ENTITLEMENTS.ADMIN} />
                    </div>
                </div>
                <div className="m-8">
                    <UsageTable project={project.project} hideEdit={entitlements.entitlement !== ENTITLEMENTS.ADMIN}
                    modalState={{
                        open: modalState.addDataToProjectModal,
                        callback: () => setModalState({
                            ...modalState,
                            addDataToProjectModal: false,
                        })
                    }} />
                </div>

            </SingleContentLayout>

        </Layout>

    );
}

export default PhaseDataPage;