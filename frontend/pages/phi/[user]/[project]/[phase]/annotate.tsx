// Next
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React Modules
import { useEffect, useState } from "react";
import { FiEdit3 } from "react-icons/fi";

// Toast
import { toast } from "react-toastify";
import Layout from "../../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../../components/generic/layout/singlecontentlayout";
import LinkHead from "../../../../../components/generic/linker/linkhead";
import FullLoadingPage from "../../../../../components/pages/fullloadingpage";
import UsePairAnnotation from "../../../../../components/specific/annotation/usepair/usepairannotation";
import UseRankAnnotation from "../../../../../components/specific/annotation/userank/usernakannotation";
import WSSIMAnnotation from "../../../../../components/specific/annotation/wssim/wssimannotation";
import ANNOTATIONTYPES from "../../../../../lib/AnnotationTypes";

// services
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";
import { useFetchPhase } from "../../../../../lib/service/phase/PhaseResource";
import LexSubAnnotation from "../../../../../components/specific/annotation/lexsub/lexsubannotation";
import UseRankRelativeAnnotation from "../../../../../components/specific/annotation/userankrelative/usernakrelativeannotation";
import UseRankPairAnnotation from "../../../../../components/specific/annotation/userankpair/usernakpairannotation";
import SentimentAnnotation from "../../../../../components/specific/annotation/sentiment/sentimentannotation";
import ChoiceAnnotation from "../../../../../components/specific/annotation/choice/choiceannotation";
import SpanAnnotation from "../../../../../components/specific/annotation/span/spanannotation";


const AnnotatePage: NextPage = () => {

    const authenticated = useAuthenticated();

    const router = useRouter();
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };

    const phase = useFetchPhase(username, projectname, phasename, router.isReady);

    useEffect(() => {
        if (phase.isError) {
            toast.error("Phase not found");
            Router.push(`/phi/${username}/${projectname}`);
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, phase.isError, username, projectname]);


    // Render
    if (phase.isLoading || phase.phase === null || !router.isReady) {
        return (
            <FullLoadingPage headtitle="Annotating Tutorial" />
        );
    }

    if (phase.phase.isTutorial()) {
        toast.error("Phase is a tutorial");
        Router.push(`/phi/${username}/${projectname}`);
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USEPAIR) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Annotate </title>
                </Head>

                <SingleContentLayout>

                    <LinkHead icon={<FiEdit3 className="stroke-2" />}
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
                                href: `/phi/${username}/${projectname}/${phasename}/annotate`,
                                name: "Annotate",
                            }
                        ]}
                    />


                    <div className="mt-2 xl:mt-10">
                        <UsePairAnnotation phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Annotate </title>
                </Head>

                <SingleContentLayout>

                    <LinkHead icon={<FiEdit3 className="stroke-2" />}
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
                                href: `/phi/${username}/${projectname}/${phasename}/annotate`,
                                name: "Annotate",
                            }
                        ]}
                    />


                    <div className="mt-2 xl:mt-10">
                        <UseRankAnnotation phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_RELATIVE) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Annotate </title>
                </Head>

                <SingleContentLayout>

                    <LinkHead icon={<FiEdit3 className="stroke-2" />}
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
                                href: `/phi/${username}/${projectname}/${phasename}/annotate`,
                                name: "Annotate",
                            }
                        ]}
                    />


                    <div className="mt-2 xl:mt-10">
                        <UseRankRelativeAnnotation phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_PAIR) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Annotate </title>
                </Head>

                <SingleContentLayout>

                    <LinkHead icon={<FiEdit3 className="stroke-2" />}
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
                                href: `/phi/${username}/${projectname}/${phasename}/annotate`,
                                name: "Annotate",
                            }
                        ]}
                    />


                    <div className="mt-2 xl:mt-10">
                        <UseRankPairAnnotation phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_WSSIM) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Annotate </title>
                </Head>

                <SingleContentLayout>
                    <LinkHead icon={<FiEdit3 className="stroke-2" />}
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
                                href: `/phi/${username}/${projectname}/${phasename}/annotate`,
                                name: "Annotate",
                            }
                        ]}
                    />

                    <div className="mt-2 xl:mt-10">
                        <WSSIMAnnotation phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_LEXSUB) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Annotate </title>
                </Head>

                <SingleContentLayout>
                    <LinkHead icon={<FiEdit3 className="stroke-2" />}
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
                                href: `/phi/${username}/${projectname}/${phasename}/annotate`,
                                name: "Annotate",
                            }
                        ]}
                    />

                    <div className="mt-2 xl:mt-10">
                        <LexSubAnnotation phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_SENTIMENT) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Annotate </title>
                </Head>

                <SingleContentLayout>
                    <LinkHead icon={<FiEdit3 className="stroke-2" />}
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
                                href: `/phi/${username}/${projectname}/${phasename}/annotate`,
                                name: "Annotate",
                            }
                        ]}
                    />

                    <div className="mt-2 xl:mt-10">
                        <SentimentAnnotation phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }
    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_CHOICE) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Annotate </title>
                </Head>

                <SingleContentLayout>
                    <LinkHead icon={<FiEdit3 className="stroke-2" />}
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
                                href: `/phi/${username}/${projectname}/${phasename}/annotate`,
                                name: "Annotate",
                            }
                        ]}
                    />

                    <div className="mt-2 xl:mt-10">
                        <ChoiceAnnotation phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_SPAN) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Annotate </title>
                </Head>

                <SingleContentLayout>
                    <LinkHead icon={<FiEdit3 className="stroke-2" />}
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
                                href: `/phi/${username}/${projectname}/${phasename}/annotate`,
                                name: "Annotate",
                            }
                        ]}
                    />

                    <div className="mt-2 xl:mt-10">
                        <SpanAnnotation phase={phase.phase} />
                    </div>

                </SingleContentLayout>
            </Layout>
        );
    }


    return (
        <FullLoadingPage headtitle="Annotate" />
    );

}

export default AnnotatePage;
