//Next Modules
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React
import { useEffect, useState } from "react";

// Toast
import { toast } from "react-toastify";

// Icons
import { FiFolder, FiMonitor, FiPlus, FiUserPlus } from "react-icons/fi";

// Custom Controllers
import useAuthenticated from "../../../../lib/hook/useAuthenticated";
import { useFetchAnnotatorsByProject, useFetchSelfEntitlement } from "../../../../lib/service/annotator/AnnotatorResource";

// Models
import Annotator from "../../../../lib/model/annotator/model/Annotator";
import ENTITLEMENTS from "../../../../lib/model/entitlement/Entitlements";

// Custom Components
import ProjectTabBar from "../../../../components/specific/tab/projecttabbar";
import IconButtonWithTooltip from "../../../../components/generic/button/iconbuttonwithtooltip";
import AnnotatorCard from "../../../../components/generic/card/annotatorcard";
import FullLoadingPage from "../../../../components/pages/fullloadingpage";
import EditAnnotatorModal from "../../../../components/specific/modal/editannotatormodal";

// Layout
import Layout from "../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../components/generic/layout/singlecontentlayout";
import Link from "next/link";
import LinkHead from "../../../../components/generic/linker/linkhead";

const AnnotatorPage: NextPage = () => {

    // Data & Hooks
    const authenticated = useAuthenticated();
    const router = useRouter();
    const { user: username, project: projectname } = router.query as { user: string, project: string };

    const entitlements = useFetchSelfEntitlement(username, projectname, router.isReady);
    const annotators = useFetchAnnotatorsByProject(username, projectname, router.isReady);

    // Modal State
    const [modalState, setModalState] = useState({
        editAnnotatorModal: false,
        selectedAnnotator: null as unknown as Annotator,
    });


    // Hook

    useEffect(() => {

        if (annotators.isError || entitlements.isError) {
            toast.error("Failed to load some data");
            Router.push("/dashboard");
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, annotators.isError, entitlements.isError]);

    // Pages

    if (!router.isReady || annotators.isLoading || entitlements.isLoading || annotators.annotators.length === 0) {
        return <FullLoadingPage headtitle="Project : Annotators" />
    }

    return (
        <Layout>

            <Head>
                <title>PhiTag : {projectname} : Annotators</title>
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
                            href: `/phi/${username}/${projectname}/annotator`,
                            name: "Annotators",
                        },
                    ]}
                />

                <div className="font-dm-mono-medium">

                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <ProjectTabBar />
                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonWithTooltip
                                icon={<FiUserPlus className="basic-svg" />}
                                reference={'/pool/annotator'}
                                tooltip="Add new Annotators"
                                hide={entitlements.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonWithTooltip
                                icon={<FiMonitor className="basic-svg" />}
                                reference={'/pool/annotator/computational'}
                                tooltip="Add Computational Annotators"
                                hide={entitlements.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="user-list">
                        {annotators.annotators.map((annotator, index) => {
                            return (
                                <AnnotatorCard
                                    key={annotator.getId().getUser()}
                                    annotator={annotator}
                                    entitlement={entitlements.entitlement}
                                    onClickEdit={() => setModalState({
                                        selectedAnnotator: annotator,
                                        editAnnotatorModal: true,
                                    })} />
                            )
                        })
                        }
                    </div>


                </div>

            </SingleContentLayout>

            <EditAnnotatorModal isOpen={modalState.editAnnotatorModal} closeModalCallback={
                () => {
                    setModalState({
                        ...modalState,
                        editAnnotatorModal: false,
                    });
                }
            } annotator={modalState.selectedAnnotator} mutateCallback={annotators.mutate} />

        </Layout>
    );
}

export default AnnotatorPage;
