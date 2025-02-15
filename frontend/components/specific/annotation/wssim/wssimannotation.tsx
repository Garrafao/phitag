// react
import { useEffect, useState } from "react";

// Next
import Router from "next/router";

// icon
import { FiBookmark, FiFeather } from "react-icons/fi";

// Toast
import { toast } from "react-toastify";

// Service
import { fetchRandomInstance, useFetchAllocatedInstanceNumber, useFetchPagedWSSIMInstance } from "../../../../lib/service/instance/InstanceResource";
import { useFetchAnnotationAccess } from "../../../../lib/service/phase/PhaseResource";
import useStorage from "../../../../lib/hook/useStorage";

// model
import Phase from "../../../../lib/model/phase/model/Phase";
import WSSIMInstance, { WSSIMInstanceConstructor } from "../../../../lib/model/instance/wssiminstance/model/WSSIMInstance";

// components
import UsageField from "../usage/usagefield";
import LoadingComponent from "../../../generic/loadingcomponent";

import { annotateWSSIM,  useFetchAttemptedJudgement,  useFetchPagedWSSIMJudgements } from "../../../../lib/service/judgement/JudgementResource";
import WSSIMTagField from "../wssimtag/wssimtagfield";
import AddWSSIMJudmentCommand from "../../../../lib/model/judgement/wssimjudgement/command/AddWSSIMJudgementCommand";
import WSSIMTagLemmasField from "../wssimtag/wssimtaglemmasfield";
import ProgressBar from "../progressbar/progressbar";
import { useFetchPhaseStatistic } from "../../../../lib/service/statistic/StatisticResource";
import SubmitStudyComponent from "../../../generic/submitstudy";


const WSSIMAnnotation: React.FC<{ phase: Phase }> = ({ phase }) => {


    // States
    const [annotation, setAnnotation] = useState({
        instance: null as unknown as WSSIMInstance,

        comment: "",
        initialLoad: true

    });
    const page = 0;

    // Hooks
    const storage = useStorage();
    const annotationAccess = useFetchAnnotationAccess(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);
    const { data: useFetchPagedWSSIMInstanceData, mutate: mutateUseFetchWSSIMInstances } = useFetchPagedWSSIMInstance(
        phase?.getId().getOwner(),
        phase?.getId().getProject(),
        phase?.getId().getPhase(),
        page,
        !!phase
    );

    const { data: userAnnotationCount, mutate: mutateCountJudgements } = useFetchAttemptedJudgement(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);



    const { data: userAllocatedInstance, mutate: mutateInstanceCount } = useFetchAllocatedInstanceNumber(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);




    // Handlers

    const handleSubmitAnnotation = (judgement: string) => {

        mutateCountJudgements();

     
        if (annotation.instance === null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }

        const resultCommand = verifyResultCommand(phase, judgement, annotation);
        setAnnotation({
            ...annotation,

            instance: null as unknown as WSSIMInstance,
            comment: "",
        });

        if (resultCommand !== null) {
            annotateWSSIM(resultCommand, storage.get)
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

        fetchRandomInstance<WSSIMInstance, WSSIMInstanceConstructor>(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), (new WSSIMInstanceConstructor()), storage.get)
            .then((instance) => {
                if (instance) {
                    setAnnotation({
                        ...annotation,

                        instance: instance,
                        comment: "",
                    });
                }

            }).catch((error) => {
                toast.error("Currently there are no more instances available for annotation.");
                Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
            });
    }

    // Hook

    useEffect(() => {

        if (annotationAccess.isError) {
            Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
        }

        if (!annotationAccess.isError && annotationAccess.hasAccess && annotation.initialLoad) {
            fetchRandomInstance<WSSIMInstance, WSSIMInstanceConstructor>(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), (new WSSIMInstanceConstructor()), storage.get)
                .then((instance) => {
                    if (instance) {
                        setAnnotation({
                            instance: instance,
                            comment: "",

                            initialLoad: false
                        });
                    }

                }).catch((error) => {
                    toast.error("Currently there are no more instances available for annotation.");
                    Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
                });





        }
    }, [ annotationAccess, annotation.initialLoad, storage, phase]);

    if ((!phase || !annotation.instance || annotation.initialLoad) && (userAllocatedInstance !== userAnnotationCount)) {

        return <LoadingComponent />;
    }

    if ((!annotation.instance && annotation.initialLoad && (userAllocatedInstance === userAnnotationCount))) {
        return <SubmitStudyComponent phase={phase} />;
    }
    return (
        <div className="w-full flex flex-col justify-between ">

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
                <UsageField key={0} usage={annotation?.instance?.getUsage()} />
                <WSSIMTagField key={1} tag={annotation?.instance?.getTag()} />
            </div>

            <div className="w-full flex flex-row my-8 items-center justify-between xl:justify-center xl:space-x-6">
                {annotation?.instance?.getLabelSet().concat(annotation?.instance?.getNonLabel()).map((label) => {
                    return (
                        <div key={label}
                            className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium"
                            onClick={() => handleSubmitAnnotation(label)}>
                            <div className="w-8 h-8 m-6 text-center text-lg">
                                {label}
                            </div>
                        </div>
                    );
                })}
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

            <div className="my-8" />

            <h1 className="font-dm-mono-medium text-3xl font-black">
                Other Tags with lemma&nbsp;
                <span className="">
                    {annotation?.instance?.getTag().getLemma()}:
                </span>
            </h1>

            <WSSIMTagLemmasField tag={annotation?.instance?.getTag()} />
            </div>
            : (
                <SubmitStudyComponent phase={phase} />
            )}
        </div>
    );

};

export default WSSIMAnnotation;

function verifyResultCommand(phase: Phase, judgement: string, annotation: {
    instance: WSSIMInstance;

    comment: string;
    initialLoad: boolean;
}): AddWSSIMJudmentCommand | null {
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

    return new AddWSSIMJudmentCommand(
        phase.getId().getOwner(),
        phase.getId().getProject(),
        phase.getId().getPhase(),
        annotation.instance.getId().getInstanceId(),
        judgement,
        annotation.comment
    );
}