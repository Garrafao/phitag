// Next
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React
import { useEffect, useState } from "react";

// Toast
import { toast } from "react-toastify";

// React Icons
import { FiDownload, FiFilePlus, FiLayers } from "react-icons/fi";

// Services
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";
import useStorage from "../../../../../lib/hook/useStorage";
import { useFetchPhase } from "../../../../../lib/service/phase/PhaseResource";
import { exportJudgement } from "../../../../../lib/service/judgement/JudgementResource";
import { useFetchSelfEntitlement } from "../../../../../lib/service/annotator/AnnotatorResource";

// models
import ANNOTATIONTYPES from "../../../../../lib/AnnotationTypes";
import ENTITLEMENTS from "../../../../../lib/model/entitlement/Entitlements";

// components 
import Layout from "../../../../../components/generic/layout/layout";
import PhaseTabBar from "../../../../../components/specific/tab/phasedatatab";
import FullLoadingPage from "../../../../../components/pages/fullloadingpage";
import SingleContentLayout from "../../../../../components/generic/layout/singlecontentlayout";
import IconButtonOnClick from "../../../../../components/generic/button/iconbuttononclick";
import UseRankJudgementTable from "../../../../../components/specific/table/userank/userankjudgementtable";
import UsePairJudgementTable from "../../../../../components/specific/table/usepair/usepairjudgementtable";
import WSSIMJudgementTable from "../../../../../components/specific/table/wssim/wssimjudgementtable";
import LinkHead from "../../../../../components/generic/linker/linkhead";
import LexSubJudgementTable from "../../../../../components/specific/table/lexsub/lexsubjudgementtable";
import UseRankRelativeJudgementTable from "../../../../../components/specific/table/userankrelative/userankrelativejudgementtable";
import UseRankPairJudgementTable from "../../../../../components/specific/table/userankpair/userankpairjudgementtable";
import SentimentJudgementTable from "../../../../../components/specific/table/sentiment/sentimentjudgementtable";
import ChoiceJudgementTable from "../../../../../components/specific/table/choice/choicejudgementtable";
import SpanJudgementTable from "../../../../../components/specific/table/span/spanjudgementtable";

const JudgementPage: NextPage = () => {

    // data
    const storage = useStorage();
    const authenticated = useAuthenticated();

    const router = useRouter();
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };

    // TODO: Find a better way to do this, generally
    const phase = useFetchPhase(username, projectname, phasename, router.isReady);
    const entitlement = useFetchSelfEntitlement(username, projectname, router.isReady);

    // modal
    const [modalState, setModalState] = useState({
        isOpenAddDataModal: false,
    });


    // handlers

    const handleExport = () => {
        exportJudgement(username, projectname, phasename, storage.get)
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
        if (phase.isError || entitlement.isError) {
            toast.error("Phase not found");
            Router.push(`/phi/${username}/${projectname}`);
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, phase.isError, entitlement.isError, username, projectname]);


    if (phase.isLoading || phase.phase === null || entitlement.isLoading || entitlement.entitlement === null || !router.isReady) {
        return <FullLoadingPage headtitle="Instance Data" />
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USEPAIR) {

        return (
            <Layout>

                <Head>

                    <title>PhiTag : {phase.phase.getName()} : Judgements </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/judgement`,
                                name: "Judgements",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiFilePlus className="basic-svg" />}
                                tooltip="Add Data"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Judgement Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <UsePairJudgementTable phase={phase.phase} modalState={
                            {
                                open: modalState.isOpenAddDataModal,
                                callback: () => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: false,
                                })
                            }
                        } />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK) {

        return (
            <Layout>

                <Head>

                    <title>PhiTag : {phase.phase.getName()} : Judgements </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/judgement`,
                                name: "Judgements",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiFilePlus className="basic-svg" />}
                                tooltip="Add Data"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Judgement Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>

                                      <div className="m-8">
                        {/* @ts-ignore */}
                        <UseRankJudgementTable phase={phase.phase} modalState={
                            {
                                open: modalState.isOpenAddDataModal,
                                callback: () => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: false,
                                })
                            }
                        } />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_RELATIVE) {

        return (
            <Layout>

                <Head>

                    <title>PhiTag : {phase.phase.getName()} : Judgements </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/judgement`,
                                name: "Judgements",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiFilePlus className="basic-svg" />}
                                tooltip="Add Data"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Judgement Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <UseRankRelativeJudgementTable phase={phase.phase} modalState={
                            {
                                open: modalState.isOpenAddDataModal,
                                callback: () => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: false,
                                })
                            }
                        } />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_PAIR) {

        return (
            <Layout>

                <Head>

                    <title>PhiTag : {phase.phase.getName()} : Judgements </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/judgement`,
                                name: "Judgements",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiFilePlus className="basic-svg" />}
                                tooltip="Add Data"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Judgement Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <UseRankPairJudgementTable phase={phase.phase} modalState={
                            {
                                open: modalState.isOpenAddDataModal,
                                callback: () => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: false,
                                })
                            }
                        } />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }



    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_WSSIM) {
        return (
            <Layout>

                <Head>

                    <title>PhiTag : {phase.phase.getName()} : Judgements </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/judgement`,
                                name: "Judgements",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiFilePlus className="basic-svg" />}
                                tooltip="Add Data"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Judgement Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <WSSIMJudgementTable phase={phase.phase} modalState={
                            {
                                open: modalState.isOpenAddDataModal,
                                callback: () => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: false,
                                })
                            }
                        } />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_LEXSUB) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Judgements </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/judgement`,
                                name: "Judgements",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiFilePlus className="basic-svg" />}
                                tooltip="Add Data"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Judgement Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <LexSubJudgementTable phase={phase.phase} modalState={
                            {
                                open: modalState.isOpenAddDataModal,
                                callback: () => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: false,
                                })
                            }
                        } />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_SENTIMENT) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Judgements </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/judgement`,
                                name: "Judgements",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiFilePlus className="basic-svg" />}
                                tooltip="Add Data"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Judgement Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <SentimentJudgementTable phase={phase.phase} modalState={
                            {
                                open: modalState.isOpenAddDataModal,
                                callback: () => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: false,
                                })
                            }
                        } />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_CHOICE) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Judgements </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/judgement`,
                                name: "Judgements",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiFilePlus className="basic-svg" />}
                                tooltip="Add Data"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Judgement Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <ChoiceJudgementTable phase={phase.phase} modalState={
                            {
                                open: modalState.isOpenAddDataModal,
                                callback: () => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: false,
                                })
                            }
                        } />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_SPAN) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Judgements </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/judgement`,
                                name: "Judgements",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiFilePlus className="basic-svg" />}
                                tooltip="Add Data"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Judgement Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <SpanJudgementTable phase={phase.phase} modalState={
                            {
                                open: modalState.isOpenAddDataModal,
                                callback: () => setModalState({
                                    ...modalState,
                                    isOpenAddDataModal: false,
                                })
                            }
                        } />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }


    return (
        <FullLoadingPage headtitle="Annotate" />
    );
}

export default JudgementPage;