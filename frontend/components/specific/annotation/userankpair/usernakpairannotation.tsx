// react
import { useEffect, useState } from "react";

// icon
import { FiBookmark, FiFeather } from "react-icons/fi";

// Serrvice
import { fetchRandomInstance, useFetchAllocatedInstanceNumber, useFetchPagedUseRankPairInstance } from "../../../../lib/service/instance/InstanceResource";
import useStorage from "../../../../lib/hook/useStorage";

// model
import Phase from "../../../../lib/model/phase/model/Phase";
import { useFetchAnnotationAccess } from "../../../../lib/service/phase/PhaseResource";
import { toast } from "react-toastify";
import Router from "next/router";
import { annotateUserankpair, useFetchAttemptedJudgement, useFetchPagedUseRankPairJudgements } from "../../../../lib/service/judgement/JudgementResource";
import LoadingComponent from "../../../generic/loadingcomponent";
import ProgressBar from "../progressbar/progressbar";
import React from "react";
import Draggable from "../usage/userank/dragabbleusage";
import { useFetchPhaseStatistic } from "../../../../lib/service/statistic/StatisticResource";
import UseRankPairInstance, { UseRankPairInstanceConstructor } from "../../../../lib/model/instance/userankpairinstance/model/UseRankPairInstance";
import AddUseRankPairJudgementCommand from "../../../../lib/model/judgement/userankpairjudgement/command/AddUseRankPairJudgementCommand";
import DraggableUseRankPairUsage from "../usage/userankpair/dragabbleuserankpairusage";
import IconButtonOnClick from "../../../generic/button/iconbuttononclick";
import { MdFullscreen } from "react-icons/md";
import SubmitStudyComponent from "../../../generic/submitstudy";




const UseRankPairAnnotation: React.FC<{ phase: Phase }> = ({ phase }) => {


    // States
    const [annotation, setAnnotation] = useState({
        instance: null as unknown as UseRankPairInstance,
        comment: "",

        initialLoad: true

    });
    const user = useStorage().get("USER");



    const page: number = 0;
    const userankinstances = useFetchPagedUseRankPairInstance(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);


    const { data: userAnnotationCount, mutate: mutateCountJudgements } = useFetchAttemptedJudgement(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);



    const { data: userAllocatedInstance, mutate: mutateInstanceCount } = useFetchAllocatedInstanceNumber(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);




    const labelArray = annotation.instance ? annotation.instance.getLabelSet() : null;
    const noLabel = annotation.instance ? annotation.instance.getNonLabel() : null;

    const rank = {
        first: labelArray ? labelArray[0] : "First Usage, Second Usage",
        second: labelArray ? labelArray[1] : "Third Usage, Fourth Usage",
        third: labelArray ? labelArray[2] : "Fifth Usage, Sixth Usage",
        fourth: labelArray ? labelArray[3] : "Seventh Usage, Eight Usage",
        fifth: labelArray ? labelArray[4]: "Ninth Usage, Tenth Usage",
    };
    
    

    const nonLabelSet = {
        nonlabel: noLabel ? noLabel[0] : "-"
    }
    const [judgementData, setJudgementData] = useState("");

    const handleUsagesReordered = (rank: string) => {

        setJudgementData(rank);
    };

    const usages = [
    
        {
            key: rank.first,
            usage1: annotation.instance?.getFirstusage(),
            usage2: annotation.instance?.getSecondusage(),
          },
          {
            key: rank.second,
            usage1: annotation.instance?.getThirdusage(),
            usage2: annotation.instance?.getFourthusage(),
          },
          {
            key: rank.third,
            usage1: annotation.instance?.getFifthusage(),
            usage2: annotation.instance?.getSixthusage(),
          },
          {
            key: rank.fourth,
            usage1: annotation.instance?.getSeventhusage(),
            usage2: annotation.instance?.getEightusage(),
          },
          {
            key: rank.fifth,
            usage1: annotation.instance?.getNinthusage(),
            usage2: annotation.instance?.getTenthusage(),
          },
           
    ];

    // Filter out null usages
    const filteredUsages = usages.filter(item => item.usage1 && item.usage2.getContext() !== undefined);


    // Hooks
    const storage = useStorage();
    const annotationAccess = useFetchAnnotationAccess(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), !!phase);



    const handleSubmitAnnotation = () => {
       mutateCountJudgements(); 
        const judgement: string = judgementData;
        if (!judgement) {
            toast.info("Please rank first or choose non label")
            return;
        }

        if (userankinstances.data.getTotalElements()) {
            const resultCommand = verifyResultCommand(phase, judgement, annotation);
            setAnnotation({
                ...annotation,
                instance: null as unknown as UseRankPairInstance,
                comment: ""
            });
            if (resultCommand !== null) {
                annotateUserankpair(resultCommand, storage.get)
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
            setJudgementData("");
        }


    }

    const handleSkip = () => {
        mutateCountJudgements();
 
        const judgement: string = annotation.instance.getNonLabel().toString();
       
            const resultCommand = verifyResultCommand(phase, judgement, annotation);
            setAnnotation({
                ...annotation,
                instance: null as unknown as UseRankPairInstance,
                comment: ""
            });
            if (resultCommand !== null) {
                annotateUserankpair(resultCommand, storage.get)
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
            fetchRandomInstance<UseRankPairInstance, UseRankPairInstanceConstructor>(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), (new UseRankPairInstanceConstructor()), storage.get)
                .then((instance) => {
                    if (instance) {
                        setAnnotation({
                            ...annotation,

                            instance: instance,
                            comment: "",
                        });
                    }
                })
                .catch((error) => {
                    toast.error("Could not fetch new annotation. Check if instances are provided for annotation.");
                    Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
                });
    }

    const [selectedSize, setSelectedSize] = useState<number>(400);
    const possibleValues = [150, 200, 300, 400, 500, 600, 700, 800, 900, 1000];

    const handleScreenSize = () => {
        const currentIndex = possibleValues.indexOf(selectedSize);

        const nextIndex = (currentIndex + 1) % possibleValues.length;

        setSelectedSize(possibleValues[nextIndex]);
    };


    useEffect(() => {

        if (annotationAccess.isError) {
            Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
            return;
        }
    
        if (!annotationAccess.isError && annotationAccess.hasAccess && annotation.initialLoad ) {
            fetchRandomInstance<UseRankPairInstance, UseRankPairInstanceConstructor>(
                phase.getId().getOwner(),
                phase.getId().getProject(),
                phase.getId().getPhase(),
                new UseRankPairInstanceConstructor(),
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
           <div className="relative w-full">
              <ProgressBar
                    currentValue={userAnnotationCount}
                    minValue={0}
                    maxValue={userAllocatedInstance}
                /> 
                <div className="absolute bottom-10 right-0">
                    <div > {/* Adjust the size as needed */}
                        <IconButtonOnClick icon={<MdFullscreen onClick={() => { handleScreenSize() }} className="text-3xl" />} tooltip={"Click here to increase the size"} />
                    </div>
                </div>
            </div>
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

            <DraggableUseRankPairUsage usages={filteredUsages} handleUsagesReordered={handleUsagesReordered} containerSize={selectedSize} />


            <div className="w-full flex flex-col justify-center space-y-4 ">

            </div>

            <div className="w-full flex flex-row my-8 items-center justify-between xl:justify-center xl:space-x-6">
                <div
                    className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium"
                    onClick={handleSubmitAnnotation}>
                    <div className="w-10 h-8 m-6 text-center text-lg">
                        Save
                    </div>
                </div>
                <div
                    className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium"
                    onClick={handleSkip}>
                    <div className="w-10 h-8 m-6 text-center text-lg">
                        {nonLabelSet.nonlabel}
                    </div>
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
    );


};

export default UseRankPairAnnotation;

function verifyResultCommand(phase: Phase, judgement: string, annotation: {
    instance: UseRankPairInstance;

    comment: string;
    initialLoad: boolean;
}): AddUseRankPairJudgementCommand | null {
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

    return new AddUseRankPairJudgementCommand(
        phase.getId().getOwner(),
        phase.getId().getProject(),
        phase.getId().getPhase(),
        annotation.instance.getId().getInstanceId(),
        judgement,
        annotation.comment
    );
}