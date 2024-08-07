// react
import { useEffect, useState } from "react";
import Router, { useRouter } from "next/router";

// toast
import { toast } from "react-toastify";

// services
import useStorage from "../../../../lib/hook/useStorage";
import { bulkAnnotateUsepair } from "../../../../lib/service/judgement/JudgementResource";
import { useFetchInstances } from "../../../../lib/service/instance/InstanceResource";

// model
import AddUsePairJudgementCommand from "../../../../lib/model/judgement/usepairjudgement/command/AddUsePairJudgementCommand";
import UsePairInstance, { UsePairInstanceConstructor } from "../../../../lib/model/instance/usepairinstance/model/UsePairInstance";
import Phase from "../../../../lib/model/phase/model/Phase";
import FullLoadingPage from "../../../pages/fullloadingpage";
import UsePairTutorialTable from "./usepairtutorialtable";
import { FiArrowRight, FiEdit3 } from "react-icons/fi";
import Layout from "../../../generic/layout/layout";
import Head from "next/head";
import SingleContentLayout from "../../../generic/layout/singlecontentlayout";
import UsePairTutorialAnnotation from "./usepairtutorialannotation";
import LinkHead from "../../../generic/linker/linkhead";
import { useFetchAnnotationAccess } from "../../../../lib/service/phase/PhaseResource";

const UsePairTutorial: React.FC<{ phase: Phase }> = ({ phase }) => {

    // States
    const [tutorialAnnotation, setTutorialAnnotation] = useState({
        annotatedInstances: [] as unknown as AddUsePairJudgementCommand[],

        currentAnnotation: 0,
        isFetched: false,
    });


    // fetch data
    const router = useRouter();
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };

    const storage = useStorage();
    const annotationAccess = useFetchAnnotationAccess(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);
    const instances = useFetchInstances<UsePairInstance, UsePairInstanceConstructor>(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), new UsePairInstanceConstructor, false, !annotationAccess.isError && annotationAccess.hasAccess && !!phase);


    // Handlers
    const handleSubmitAnnotation = (judgement: string, comment: string) => {
        const resultCommand = verifyResultCommand(phase, instances.data[tutorialAnnotation.currentAnnotation].id.instanceId, judgement, comment);
        if (resultCommand !== null) {
            setTutorialAnnotation({
                ...tutorialAnnotation,
                annotatedInstances: [...tutorialAnnotation.annotatedInstances, resultCommand],
                currentAnnotation: tutorialAnnotation.currentAnnotation + 1,
            });
        }
    }

    const handleFinalizeAnnotation = () => {
        bulkAnnotateUsepair(tutorialAnnotation.annotatedInstances, storage.get).then(() => {
            toast.info("Tutorial finished. Results can be checked in the phase overview.");
        }).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while evaluating tutorial: " + error.response.data.message);
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        }).finally(() => {
            Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
        });
    }

    // effects
    useEffect(() => {
        if (annotationAccess.isError) {
            Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
        }

        if (!instances.isLoading && !instances.isError && !tutorialAnnotation.isFetched) {
            setTutorialAnnotation({
                ...tutorialAnnotation,
                isFetched: true,
            });
        }

    }, [instances.isLoading, instances.isError, tutorialAnnotation, annotationAccess.isError]);


    // Render

    if (instances.isLoading || instances.data === undefined || instances.data.length === 0) {
        return (
            <FullLoadingPage headtitle="Tutorial" />
        );
    }

    if (tutorialAnnotation.currentAnnotation === instances.data.length) {
        return (
            <Layout>

                <Head>
                    <title>PhiTag : Tutorial : {phase.getName()} </title>
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
                                href: `/phi/${username}/${projectname}/${phasename}/tutorial`,
                                name: "Annotation Overview",
                            }
                        ]}
                    />


                    <div className="m-8">
                        {/* @ts-ignore, fix later */}
                        <UsePairTutorialTable data={tutorialAnnotation.annotatedInstances} instances={instances.data} />
                    </div>


                    <div className="w-full flex justify-end pt-4">
                        <button className="flex flex-row items-center font-dm-mono-medium text-lg bg-base16-gray-900 text-base16-gray-100 py-2 px-10" onClick={() => handleFinalizeAnnotation()}>
                            Send {<FiArrowRight className="basic-svg ml-2" />}
                        </button>
                    </div>
                </SingleContentLayout>
            </Layout>
        );
    }

    return (

        <Layout>

            <Head>
                <title>PhiTag : Tutorial : {phase.getName()} </title>
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
                            href: `/phi/${username}/${projectname}/${phasename}/tutorial`,
                            name: "Tutorial",
                        }
                    ]}
                />


                <div className="mt-2 xl:mt-10">
                    {/* @ts-ignore, fix later */}
                    <UsePairTutorialAnnotation instance={instances.data[tutorialAnnotation.currentAnnotation]} handleSubmitAnnotation={handleSubmitAnnotation} phase={phase} />
                </div>

            </SingleContentLayout>
        </Layout>

    )

}

export default UsePairTutorial;

function verifyResultCommand(phase: Phase, instance: string, judgement: string, comment: string): AddUsePairJudgementCommand | null {

    if (phase === undefined || phase === null) {
        toast.warning("This should not happen. Please try again later.");
        return null;
    }

    if (instance === undefined || instance === null) {
        toast.warning("This should not happen. Please try again later.");
        return null;
    }

    if (judgement === "" || judgement === undefined || judgement === null) {
        toast.error("Please select a judgement.");
        return null;
    }

    return new AddUsePairJudgementCommand(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), instance, judgement, comment);
}