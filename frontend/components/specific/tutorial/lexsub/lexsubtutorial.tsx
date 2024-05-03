import { useEffect, useState } from "react";
import AddLexSubJudgementCommand from "../../../../lib/model/judgement/lexsubjudgement/command/AddLexSubJudgementCommand";
import Router, { useRouter } from "next/router";
import useStorage from "../../../../lib/hook/useStorage";
import { useFetchAnnotationAccess } from "../../../../lib/service/phase/PhaseResource";
import { useFetchInstances } from "../../../../lib/service/instance/InstanceResource";
import LexSubInstance, { LexSubInstanceConstructor } from "../../../../lib/model/instance/lexsubinstance/model/LexSubInstance";
import { bulkAnnotateLexSub } from "../../../../lib/service/judgement/JudgementResource";
import { toast } from "react-toastify";
import FullLoadingPage from "../../../pages/fullloadingpage";
import Layout from "../../../generic/layout/layout";
import Head from "next/head";
import SingleContentLayout from "../../../generic/layout/singlecontentlayout";
import LinkHead from "../../../generic/linker/linkhead";
import { FiArrowRight, FiEdit3 } from "react-icons/fi";
import LexSubTutorialTable from "./lexsubtutorialtable";
import LexSubTutorialAnnotation from "./lexsubtutorialannotation";
import Phase from "../../../../lib/model/phase/model/Phase";

const LexSubTutorial: React.FC<{ phase: Phase }> = ({ phase }) => {

    const [tutorialAnnotation, setTutorialAnnotation] = useState({
        annotatedInstances: [] as unknown as AddLexSubJudgementCommand[],
        currentAnnotation: 0,
        isFetched: false,
    });

    const router = useRouter();
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };

    const storage = useStorage();
    const annotationAccess = useFetchAnnotationAccess(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);
    const instances = useFetchInstances<LexSubInstance, LexSubInstanceConstructor>(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), new LexSubInstanceConstructor, false, !annotationAccess.isError && annotationAccess.hasAccess && !!phase);

    const handleSubmitAnnotation = (judgement: string, comment: string) => {
        const resultCommand = new AddLexSubJudgementCommand(
            phase?.getId().getOwner(),
            phase?.getId().getProject(),
            phase?.getId().getPhase(),
            instances.data[tutorialAnnotation.currentAnnotation].id.instanceId,
            judgement,
            comment
        );
        setTutorialAnnotation({
            ...tutorialAnnotation,
            annotatedInstances: [...tutorialAnnotation.annotatedInstances, resultCommand],
            currentAnnotation: tutorialAnnotation.currentAnnotation + 1,
        });
    }


    const handleFinalizeAnnotation = () => {
        bulkAnnotateLexSub(tutorialAnnotation.annotatedInstances, storage.get).then(() => {
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
                                name: "Tutorial",
                            }
                        ]}
                    />


                    <div className="m-8">
                        {/* @ts-ignore, fix later */}
                        <LexSubTutorialTable data={tutorialAnnotation.annotatedInstances} instances={instances.data} />
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
                    <LexSubTutorialAnnotation instance={instances.data[tutorialAnnotation.currentAnnotation]} handleSubmitAnnotation={handleSubmitAnnotation} />
                </div>

            </SingleContentLayout>
        </Layout>

    )

}

export default LexSubTutorial;