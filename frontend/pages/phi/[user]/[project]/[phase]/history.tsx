// Next
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React
import { useEffect } from "react";

// Toast
import { toast } from "react-toastify";

// React Icons

// Services
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";
import { useFetchPhase } from "../../../../../lib/service/phase/PhaseResource";

// models
import ANNOTATIONTYPES from "../../../../../lib/AnnotationTypes";

// components 
import FullLoadingPage from "../../../../../components/pages/fullloadingpage";
import PhaseTabBar from "../../../../../components/specific/tab/phasedatatab";

// layout
import Layout from "../../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../../components/generic/layout/singlecontentlayout";
import { useFetchSelfEntitlement } from "../../../../../lib/service/annotator/AnnotatorResource";
import UsePairJudgementHistoryTable from "../../../../../components/specific/table/usepair/usepairjudgementhistorytable";
import UseRankJudgementHistoryTable from "../../../../../components/specific/table/userank/userankjudgementhistorytable";

import WSSIMJudgementHistoryTable from "../../../../../components/specific/table/wssim/wssimjudgementhistorytable";
import LinkHead from "../../../../../components/generic/linker/linkhead";
import { FiDownload, FiLayers } from "react-icons/fi";
import LexSubJudgementHistoryTable from "../../../../../components/specific/table/lexsub/lexsubjudgementhistorytable";
import UseRankRelativeJudgementHistoryTable from "../../../../../components/specific/table/userankrelative/userankrelativejudgementhistorytable";
import UseRankPairJudgementHistoryTable from "../../../../../components/specific/table/userankpair/userankpairjudgementhistorytable";
import UsePairTutorialJudgementHistoryTable from "../../../../../components/specific/tutorial/usepair/usepairtutorialjudgementhistorytable";
import IconButtonOnClick from "../../../../../components/generic/button/iconbuttononclick";
import ENTITLEMENTS from "../../../../../lib/model/entitlement/Entitlements";
import useStorage from "../../../../../lib/hook/useStorage";
import { exportJudgement } from "../../../../../lib/service/judgement/JudgementResource";
import LexSubTutorialJudgementHistoryTable from "../../../../../components/specific/tutorial/lexsub/lexsubtutorialjudgementhistorytable";
import UseRankTutorialJudgementHistoryTable from "../../../../../components/specific/tutorial/userank/useranktutorialjudgementhistorytable";
import UseRankRelativeTutorialJudgementHistoryTable from "../../../../../components/specific/tutorial/userankrelative/userankrelativetutorialjudgementhistorytable";
import UseRankPairTutorialJudgementHistoryTable from "../../../../../components/specific/tutorial/userankpair/userankpairtutorialjudgementhistorytable";
import { exportTutorialJudgement } from "../../../../../lib/service/tutorial/tutorialresources";
import WSSIMTutorialJudgementHistoryTable from "../../../../../components/specific/tutorial/wssim/wssimjudgementhistorytable";
import SentimentJudgementHistoryTable from "../../../../../components/specific/table/sentiment/sentimentjudgementhistorytable";


const AnnotationHistory: NextPage = () => {

    // data
    const authenticated = useAuthenticated();

    const storage = useStorage();

    const router = useRouter();
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };

    // TODO: Find a better way to do this, generally
    const phase = useFetchPhase(username, projectname, phasename, router.isReady);
    const entitlement = useFetchSelfEntitlement(username, projectname, router.isReady);

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

        const handleExportTutorial = () => {
            exportTutorialJudgement(username, projectname, phasename, storage.get)
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
        return <FullLoadingPage headtitle="History Data" />
    }

    if (phase.phase.isTutorial()) {
        /* usepair history table */
        if (phase.phase.isTutorial() &&  phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USEPAIR) {

            return (
                <Layout>
    
                    <Head>
                        <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                    href: `/phi/${username}/${projectname}/${phasename}/history`,
                                    name: "History",
                                }
                            ]}
                        />
    
                       
                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Tutorial Annotated Data"
                                onClick={() => handleExportTutorial()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>
    
                        <div className="m-8">
                         
                       <UsePairTutorialJudgementHistoryTable phase={phase.phase}/> 
                        </div>
    
                    </SingleContentLayout>
                </Layout>
    
            );
        }
        
        if (phase.phase.isTutorial() &&  phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_LEXSUB) {

            return (
                <Layout>
    
                    <Head>
                        <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                    href: `/phi/${username}/${projectname}/${phasename}/history`,
                                    name: "History",
                                }
                            ]}
                        />
    
                       
                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Tutorial Annotated Data"
                                onClick={() => handleExportTutorial()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>
    
                        <div className="m-8">
                         
                       <LexSubTutorialJudgementHistoryTable phase={phase.phase}/> 
                        </div>
    
                    </SingleContentLayout>
                </Layout>
    
            );
        }

        if (phase.phase.isTutorial() &&  phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_LEXSUB) {

            return (
                <Layout>
    
                    <Head>
                        <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                    href: `/phi/${username}/${projectname}/${phasename}/history`,
                                    name: "History",
                                }
                            ]}
                        />
    
                       
                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Tutorial Annotated Data"
                                onClick={() => handleExportTutorial()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>
    
                        <div className="m-8">
                         
                       <LexSubTutorialJudgementHistoryTable phase={phase.phase}/> 
                        </div>
    
                    </SingleContentLayout>
                </Layout>
    
            );
        }
        if (phase.phase.isTutorial() &&  phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK) {

            return (
                <Layout>
    
                    <Head>
                        <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                    href: `/phi/${username}/${projectname}/${phasename}/history`,
                                    name: "History",
                                }
                            ]}
                        />
    
                       
                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Tutorial Annotated Data"
                                onClick={() => handleExportTutorial()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>
    
                        <div className="m-8">
                         
                       <UseRankTutorialJudgementHistoryTable phase={phase.phase}/> 
                        </div>
    
                    </SingleContentLayout>
                </Layout>
    
            );
        }
        if (phase.phase.isTutorial() &&  phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_RELATIVE) {

            return (
                <Layout>
    
                    <Head>
                        <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                    href: `/phi/${username}/${projectname}/${phasename}/history`,
                                    name: "History",
                                }
                            ]}
                        />
    
                       
                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Tutorial Annotated Data"
                                onClick={() => handleExportTutorial()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>
    
                        <div className="m-8">
                         
                       <UseRankRelativeTutorialJudgementHistoryTable phase={phase.phase}/> 
                        </div>
    
                    </SingleContentLayout>
                </Layout>
    
            );
        }
        if (phase.phase.isTutorial() &&  phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_PAIR) {

            return (
                <Layout>
    
                    <Head>
                        <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                    href: `/phi/${username}/${projectname}/${phasename}/history`,
                                    name: "History",
                                }
                            ]}
                        />
    
                       
                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Tutorial Annotated Data"
                                onClick={() => handleExportTutorial()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>
    
                        <div className="m-8">
                         
                       <UseRankPairTutorialJudgementHistoryTable phase={phase.phase}/> 
                        </div>
    
                    </SingleContentLayout>
                </Layout>
    
            );
        }
        if (phase.phase.isTutorial() &&  phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_WSSIM) {

            return (
                <Layout>
    
                    <Head>
                        <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                    href: `/phi/${username}/${projectname}/${phasename}/history`,
                                    name: "History",
                                }
                            ]}
                        />
    
                       
                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download Tutorial Annotated Data"
                                onClick={() => handleExportTutorial()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>
    
                        <div className="m-8">
                         
                       <WSSIMTutorialJudgementHistoryTable phase={phase.phase}/> 
                        </div>
    
                    </SingleContentLayout>
                </Layout>
    
            );
        }
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USEPAIR) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/history`,
                                name: "History",
                            }
                        ]}
                    />

                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />
                        <div />
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <UsePairJudgementHistoryTable phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/history`,
                                name: "History",
                            }
                        ]}
                    />

                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />
                        <div />
                    </div>

                    <div className="m-8 overflow-auto">
                        {/* @ts-ignore */}
                        <UseRankJudgementHistoryTable phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_RELATIVE) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/history`,
                                name: "History",
                            }
                        ]}
                    />

                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />
                        <div />
                    </div>

                    <div className="m-8 overflow-auto">
                        {/* @ts-ignore */}
                        <UseRankRelativeJudgementHistoryTable phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_PAIR) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/history`,
                                name: "History",
                            }
                        ]}
                    />

                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />
                        <div />
                    </div>

                    <div className="m-8 overflow-auto">
                        {/* @ts-ignore */}
                        <UseRankPairJudgementHistoryTable phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }


    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_WSSIM) {

        return (
            <Layout>

                <Head>

                    <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/history`,
                                name: "History",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />
                        <div />
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <WSSIMJudgementHistoryTable phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    } 

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_LEXSUB) {
        return (
            <Layout>

                <Head>

                    <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/history`,
                                name: "History",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />
                        <div />
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <LexSubJudgementHistoryTable phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_SENTIMENT) {
        return (
            <Layout>

                <Head>

                    <title>PhiTag : {phase.phase.getName()} : History </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/history`,
                                name: "History",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />
                        <div />
                    </div>

                    <div className="m-8">
                        {/* @ts-ignore */}
                        <SentimentJudgementHistoryTable phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    return (
        <FullLoadingPage headtitle="Annotate" />
    );
}

export default AnnotationHistory;