import { useEffect, useState } from "react";
import LexSubInstance, { LexSubInstanceConstructor } from "../../../../lib/model/instance/lexsubinstance/model/LexSubInstance";
import Phase from "../../../../lib/model/phase/model/Phase";
import useStorage from "../../../../lib/hook/useStorage";
import { useFetchAnnotationAccess } from "../../../../lib/service/phase/PhaseResource";
import { toast } from "react-toastify";
import AddLexSubJudgementCommand from "../../../../lib/model/judgement/lexsubjudgement/command/AddLexSubJudgementCommand";
import { annotateLexSub, annotateSentiment, useFetchAttemptedJudgement } from "../../../../lib/service/judgement/JudgementResource";
import Router, { useRouter } from "next/router";
import { fetchRandomInstance, useFetchAllocatedInstanceNumber } from "../../../../lib/service/instance/InstanceResource";
import LoadingComponent from "../../../generic/loadingcomponent";
import UsageField from "../usage/usagefield";
import { FiArrowRight, FiBookmark, FiChevronRight, FiEdit3, FiFeather } from "react-icons/fi";
import ProgressBar from "../progressbar/progressbar";
import AddSentimentJudgementCommand from "../../../../lib/model/judgement/sentiment/command/AddSentimentJudgementCommand";
import SentimentInstance, { SentimentInstanceConstructor } from "../../../../lib/model/instance/sentimentinstance/model/SentimentInstance";
import SubmitStudyComponent from "../../../generic/submitstudy";

const SentimentAnnotation: React.FC<{ phase: Phase }> = ({ phase }) => {

    const [annotation, setAnnotation] = useState({
        instance: null as unknown as SentimentInstance,
        comment: "",
        initialLoad: true

    });
    const page = 0;
    const storage = useStorage();


    const annotationAccess = useFetchAnnotationAccess(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);

    const { data: userAnnotationCount, mutate: mutateCountJudgements } = useFetchAttemptedJudgement(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);



    const { data: userAllocatedInstance, mutate: mutateInstanceCount } = useFetchAllocatedInstanceNumber(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);




    const handleSubmitAnnotation = (judgement: string) => {
        mutateCountJudgements();
        const resultCommand = verifyResultCommand(phase, judgement, annotation);
        setAnnotation({
            ...annotation,
            instance: null as unknown as SentimentInstance,
            comment: ""
        });
        if (resultCommand !== null) {
            annotateSentiment(resultCommand, storage.get)
                .then((result) => {
                    fetchNewAnnotation();
                }).catch((error) => {
                    if (error?.response?.status === 500) {
                        toast.error("Error while adding judgement: " + error.response.data.message + "!");
                    } else {
                        toast.warning("The system is currently not available, please try again later!");
                    }
                }
                );
        }

    }

    const fetchNewAnnotation = () => {
        mutateCountJudgements();
        fetchRandomInstance<SentimentInstance, SentimentInstanceConstructor>(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), (new SentimentInstanceConstructor()), storage.get)
            .then((instance) => {
                if (instance) {
                    setAnnotation({
                        ...annotation,

                        instance: instance,
                        comment: "",
                    });
                }

            }).catch((error) => {
                toast.error("Could not fetch new annotation. Check if instances are provided for annotation.");
                Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
            });


    }



    // Hook
    useEffect(() => {

        if (annotationAccess.isError) {
            Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
            return;
        }


        if (!annotationAccess.isError && annotationAccess.hasAccess && annotation.initialLoad) {
            fetchRandomInstance<SentimentInstance, SentimentInstanceConstructor>(
                phase.getId().getOwner(),
                phase.getId().getProject(),
                phase.getId().getPhase(),
                new SentimentInstanceConstructor(),
                storage.get
            )
                .then((instance) => {
                    if (instance) {
                        setAnnotation((prevAnnotation) => ({
                            ...prevAnnotation,
                            instance: instance,
                            comment: "",
                            initialLoad: false,
                        }));
                    }
                })
                .catch((error) => {
                    toast.error("Could not fetch new annotation. Check if instances are provided for annotation.");
                    Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
                });
        }
    }, [annotationAccess, annotation.initialLoad, storage, phase]);

    if ((!phase || !annotation.instance || annotation.initialLoad) && (userAllocatedInstance !== userAnnotationCount)) {

        return <LoadingComponent />;
    }
    
    if ((!annotation.instance && annotation.initialLoad && (userAllocatedInstance === userAnnotationCount))) {
        return <SubmitStudyComponent phase={phase} />;
    }


    return (
        <div className="w-full flex flex-col justify-between">
            {(true && annotation.instance !== null) ?
                <div className="w-full flex flex-col justify-center space-y-4">
                    <ProgressBar minValue={0} maxValue={userAllocatedInstance} currentValue={userAnnotationCount} />
                    {(phase.getTaskHead() ?? "") !== "" && (
                        <div className="w-half shadow-md ">
                            <div className="m-8 flex flex-row">
                                <div className="my-4">
                                    <FiBookmark className="basic-svg" />
                                </div>
                                <div className="border-r-2 mx-4" />
                                <div className="my-4 font-dm-mono-light text-lg overflow-auto">
                                    {phase.getTaskHead()}
                                </div>

                            </div>
                        </div>
                    )}
                    <div className="w-full flex flex-col justify-center space-y-4 ">
                        <UsageField usage={annotation?.instance?.getUsage()} />
                    </div>
                    <div className="w-full flex flex-col my-8 self-center items-left font-dm-mono-regular text-lg">
                        <div className="font-bold text-lg">
                            Judgement
                        </div>
                        <div className="w-full flex flex-row my-8 items-center justify-between xl:justify-center xl:space-x-6">
                            {annotation?.instance?.getLabelSet().concat(annotation?.instance?.getNonLabel()).map((label) => {
                                return (
                                    <div key={label}
                                        className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium"
                                        onClick={() => handleSubmitAnnotation(label)}
                                        style={{ minWidth: "0", overflow: "hidden" }}>
                                        <div className="w-auto min-w-8 h-8 m-6 text-center text-lg">
                                            {label}
                                        </div>
                                    </div>
                                );
                            })}
                        </div>
                    </div>
                    <div className="w-full flex flex-col self-center items-left font-dm-mono-regular text-lg">
                <div className="font-bold text-lg">
                    Comment
                </div>
                <div className="h-32 flex items-start border-l-2 py-2 px-3 mt-2">
                    <FiFeather className='basic-svg' />
                    <textarea
                        className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                        name="description"
                        placeholder={"Comment"}
                        value={annotation.comment}
                        onChange={(e: any) => setAnnotation({
                            ...annotation,
                            comment: e.target.value
                        })} />
                </div>
            </div>

                </div>
                :
                <SubmitStudyComponent phase={phase} />
            }

        </div>
    );

}

export default SentimentAnnotation;

function verifyResultCommand(phase: Phase, judgement: string, annotation: {
    instance: SentimentInstance;

    comment: string;
    initialLoad: boolean;
}): AddSentimentJudgementCommand | null {
    if (phase === undefined || phase === null) {
        toast.warning("This should not happen. Please contact the administrator.");
        return null;
    }

    if (annotation.instance === undefined || annotation.instance === null) {
        toast.warning("This should not happen. Please contact the administrator.");
        return null;
    }

    if (judgement === undefined || judgement === null || judgement === "") {
        toast.error("Please select a judgement.");
        return null;
    }

    return new AddSentimentJudgementCommand(
        phase.getId().getOwner(),
        phase.getId().getProject(),
        phase.getId().getPhase(),
        annotation.instance.getId().getInstanceId(),
        judgement,
        annotation.comment
    );
}