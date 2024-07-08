// Next
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React
import { useEffect, useState } from "react";

// Toast
import { toast } from "react-toastify";

// React Icons
import { FiCpu, FiDownload, FiFilePlus, FiLayers, FiToggleLeft, FiToggleRight } from "react-icons/fi";

import ANNOTATIONTYPES from "../../../../../lib/AnnotationTypes";

// Services
import { exportInstance } from "../../../../../lib/service/instance/InstanceResource";
import useStorage from "../../../../../lib/hook/useStorage";
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";
import { useFetchPhase } from "../../../../../lib/service/phase/PhaseResource";
import { useFetchSelfEntitlement } from "../../../../../lib/service/annotator/AnnotatorResource";

// Models
import ENTITLEMENTS from "../../../../../lib/model/entitlement/Entitlements";

// Components
import FullLoadingPage from "../../../../../components/pages/fullloadingpage";
import Layout from "../../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../../components/generic/layout/singlecontentlayout";
import PhaseTabBar from "../../../../../components/specific/tab/phasedatatab";
import IconButtonOnClick from "../../../../../components/generic/button/iconbuttononclick";
import UsePairInstanceTable from "../../../../../components/specific/table/usepair/usepairinstancetable";
import WSSIMInstanceTable from "../../../../../components/specific/table/wssim/wssiminstancetable";
import WSSIMTagTable from "../../../../../components/specific/table/wssim/wssimtagtable";
import LinkHead from "../../../../../components/generic/linker/linkhead";
import LexSubInstanceTable from "../../../../../components/specific/table/lexsub/lexsubinstancetable";
import UseRankInstanceTable from "../../../../../components/specific/table/userank/userankinstancetable";
import UseRankRelativeInstanceTable from "../../../../../components/specific/table/userankrelative/userankrelativeinstancetable";
import UseRankPairInstanceTable from "../../../../../components/specific/table/userankpair/userankpairinstancetable";
import SentimentInstanceTable from "../../../../../components/specific/table/sentiment/sentimentinstancetable";
import ChoiceInstanceTable from "../../../../../components/specific/table/choice/choiceinstancetable";
import SpanInstanceTable from "../../../../../components/specific/table/span/spaninstancetable";

const InstancePage: NextPage = () => {

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
        isOpenGenerateInstancesModal: false,
    });

    // other state information
    const [instanceState, setInstanceState] = useState({
        tagfile: false
    });

    // handlers

    const handleExport = () => {
        exportInstance(username, projectname, phasename, false, storage.get)
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

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }

        if (phase.isError || entitlement.isError) {
            toast.error("Phase not found");
            Router.push(`/phi/${username}/${projectname}`);
        }

    }, [authenticated, phase, entitlement, username, projectname]);


    if (phase.isLoading || phase.phase === null || entitlement.isLoading || entitlement.entitlement === null || !router.isReady) {
        return <FullLoadingPage headtitle="Instance Data" />
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USEPAIR) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Instances </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/instance`,
                                name: "Instances",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiCpu className="basic-svg" />}
                                tooltip="Generate Instances"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenGenerateInstancesModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
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
                                tooltip="Download Instance Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="m-8">
                        {/* @ts-ignore */}
                        <UsePairInstanceTable phase={phase.phase}
                            modalState={{
                                openData: modalState.isOpenAddDataModal,
                                openGenerate: modalState.isOpenGenerateInstancesModal,

                                callbackData: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenAddDataModal: false,
                                    })
                                },

                                callbackGenerate: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenGenerateInstancesModal: false,

                                    })
                                }
                            }}
                        />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Instances </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/instance`,
                                name: "Instances",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                           {/*  <IconButtonOnClick
                                icon={<FiCpu className="basic-svg" />}
                                tooltip="Generate Instances"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenGenerateInstancesModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} /> */}
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
                                tooltip="Download Instance Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="m-8">
                        {/* @ts-ignore */}
                        <UseRankInstanceTable phase={phase.phase}
                            modalState={{
                                openData: modalState.isOpenAddDataModal,
                                openGenerate: modalState.isOpenGenerateInstancesModal,

                                callbackData: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenAddDataModal: false,
                                    })
                                },

                                callbackGenerate: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenGenerateInstancesModal: false,

                                    })
                                }
                            }}
                        />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_RELATIVE) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Instances </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/instance`,
                                name: "Instances",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                           {/*  <IconButtonOnClick
                                icon={<FiCpu className="basic-svg" />}
                                tooltip="Generate Instances"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenGenerateInstancesModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} /> */}
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
                                tooltip="Download Instance Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="m-8">
                        {/* @ts-ignore */}
                        <UseRankRelativeInstanceTable phase={phase.phase}
                            modalState={{
                                openData: modalState.isOpenAddDataModal,
                                openGenerate: modalState.isOpenGenerateInstancesModal,

                                callbackData: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenAddDataModal: false,
                                    })
                                },

                                callbackGenerate: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenGenerateInstancesModal: false,

                                    })
                                }
                            }}
                        />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_PAIR) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Instances </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/instance`,
                                name: "Instances",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                           {/*  <IconButtonOnClick
                                icon={<FiCpu className="basic-svg" />}
                                tooltip="Generate Instances"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenGenerateInstancesModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} /> */}
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
                                tooltip="Download Instance Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="m-8">
                        {/* @ts-ignore */}
                        <UseRankPairInstanceTable phase={phase.phase}
                            modalState={{
                                openData: modalState.isOpenAddDataModal,
                                openGenerate: modalState.isOpenGenerateInstancesModal,

                                callbackData: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenAddDataModal: false,
                                    })
                                },

                                callbackGenerate: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenGenerateInstancesModal: false,

                                    })
                                }
                            }}
                        />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }





    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_WSSIM) {
        return (
            <Layout>
                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Instances </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/instance`,
                                name: instanceState.tagfile ? "Tags" : "Instances",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 items-center">
                                <div className="font-dm-mono-medium font-bold">
                                    Instaces
                                </div>
                                {!instanceState.tagfile ?
                                    <IconButtonOnClick
                                        icon={<FiToggleLeft className="basic-svg" />}
                                        tooltip={"Instances"}
                                        onClick={() => setInstanceState({
                                            ...instanceState,
                                            tagfile: true
                                        })} />
                                    :
                                    <IconButtonOnClick
                                        icon={<FiToggleRight className="basic-svg" />}
                                        tooltip={"Tags"}
                                        onClick={() => setInstanceState({
                                            ...instanceState,
                                            tagfile: false
                                        })} />
                                }
                                <div className="font-dm-mono-medium font-bold">
                                    Tags
                                </div>
                            </div>
                            <IconButtonOnClick
                                icon={<FiCpu className="basic-svg" />}
                                tooltip="Generate Instances"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenGenerateInstancesModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
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
                                tooltip="Download Instance Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="m-8">
                        {/* @ts-ignore */}
                        {
                            !instanceState.tagfile ?
                                <WSSIMInstanceTable phase={phase.phase} modalState={
                                    {
                                        openData: modalState.isOpenAddDataModal,
                                        openGenerate: modalState.isOpenGenerateInstancesModal,

                                        callbackData: () => {
                                            setModalState({
                                                ...modalState,
                                                isOpenAddDataModal: false,
                                            })
                                        },

                                        callbackGenerate: () => {
                                            setModalState({
                                                ...modalState,
                                                isOpenGenerateInstancesModal: false,

                                            })
                                        }
                                    }
                                } />
                                :
                                <WSSIMTagTable phase={phase.phase} modalState={
                                    {
                                        open: modalState.isOpenAddDataModal,
                                        callback: () => setModalState({
                                            ...modalState,
                                            isOpenAddDataModal: false,
                                        })
                                    }
                                } />
                        }
                    </div>
                </SingleContentLayout>
            </Layout>
        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_LEXSUB) {
        return (
            <Layout>
                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Instances </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/instance`,
                                name: instanceState.tagfile ? "Tags" : "Instances",
                            }
                        ]}
                    />

                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                            <IconButtonOnClick
                                icon={<FiCpu className="basic-svg" />}
                                tooltip="Generate Instances"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenGenerateInstancesModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
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
                                tooltip="Download Instance Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="m-8">
                        <LexSubInstanceTable phase={phase.phase}
                            modalState={{
                                openData: modalState.isOpenAddDataModal,
                                openGenerate: modalState.isOpenGenerateInstancesModal,

                                callbackData: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenAddDataModal: false,
                                    })
                                },

                                callbackGenerate: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenGenerateInstancesModal: false,

                                    })
                                }
                            }}
                        />
                    </div>


                </SingleContentLayout>
            </Layout>
        );
    }

    
    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_SENTIMENT) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Instances </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/instance`,
                                name: "Instances",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                           {/*  <IconButtonOnClick
                                icon={<FiCpu className="basic-svg" />}
                                tooltip="Generate Instances"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenGenerateInstancesModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} /> */}
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
                                tooltip="Download Instance Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="m-8">
                        {/* @ts-ignore */}
                        <SentimentInstanceTable phase={phase.phase}
                            modalState={{
                                openData: modalState.isOpenAddDataModal,
                                openGenerate: modalState.isOpenGenerateInstancesModal,

                                callbackData: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenAddDataModal: false,
                                    })
                                },

                                callbackGenerate: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenGenerateInstancesModal: false,

                                    })
                                }
                            }}
                        />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_CHOICE) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Instances </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/instance`,
                                name: "Instances",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                           {/*  <IconButtonOnClick
                                icon={<FiCpu className="basic-svg" />}
                                tooltip="Generate Instances"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenGenerateInstancesModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} /> */}
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
                                tooltip="Download Instance Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="m-8">
                        {/* @ts-ignore */}
                        <ChoiceInstanceTable phase={phase.phase}
                            modalState={{
                                openData: modalState.isOpenAddDataModal,
                                openGenerate: modalState.isOpenGenerateInstancesModal,

                                callbackData: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenAddDataModal: false,
                                    })
                                },

                                callbackGenerate: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenGenerateInstancesModal: false,

                                    })
                                }
                            }}
                        />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_SPAN) {

        return (
            <Layout>

                <Head>
                    <title>PhiTag : {phase.phase.getName()} : Instances </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/instance`,
                                name: "Instances",
                            }
                        ]}
                    />


                    <div className="w-full flex flex-col 2xl:flex-row justify-between">
                        <PhaseTabBar />

                        <div className="flex mt-8 2xl:mt-0 mx-4 space-x-4 justify-end">
                           {/*  <IconButtonOnClick
                                icon={<FiCpu className="basic-svg" />}
                                tooltip="Generate Instances"
                                onClick={() => setModalState({
                                    ...modalState,
                                    isOpenGenerateInstancesModal: true,
                                })}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} /> */}
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
                                tooltip="Download Instance Data"
                                onClick={() => handleExport()}
                                hide={entitlement.entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>


                    <div className="m-8">
                        {/* @ts-ignore */}
                        <SpanInstanceTable phase={phase.phase}
                            modalState={{
                                openData: modalState.isOpenAddDataModal,
                                openGenerate: modalState.isOpenGenerateInstancesModal,

                                callbackData: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenAddDataModal: false,
                                    })
                                },

                                callbackGenerate: () => {
                                    setModalState({
                                        ...modalState,
                                        isOpenGenerateInstancesModal: false,

                                    })
                                }
                            }}
                        />
                    </div>

                </SingleContentLayout>
            </Layout>

        );
    }





    return (
        <FullLoadingPage headtitle="Annotate" />
    );
}

export default InstancePage;