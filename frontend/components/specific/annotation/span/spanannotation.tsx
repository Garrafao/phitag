import { useEffect, useState } from "react";
import Phase from "../../../../lib/model/phase/model/Phase";
import useStorage from "../../../../lib/hook/useStorage";
import { useFetchAnnotationAccess } from "../../../../lib/service/phase/PhaseResource";
import { toast } from "react-toastify";
import { annotateSentiment, annotateSpan, useFetchAttemptedJudgement } from "../../../../lib/service/judgement/JudgementResource";
import Router from "next/router";
import { fetchRandomInstance, useFetchAllocatedInstanceNumber } from "../../../../lib/service/instance/InstanceResource";
import LoadingComponent from "../../../generic/loadingcomponent";
import { FiArrowRight, FiBookmark, FiChevronRight, FiEdit3, FiFeather } from "react-icons/fi";
import ProgressBar from "../progressbar/progressbar";
import SubmitStudyComponent from "../../../generic/submitstudy";
import SpanUsageField from "../usage/spanusagefield";
import SentimentInstance, { SentimentInstanceConstructor } from "../../../../lib/model/instance/sentimentinstance/model/SentimentInstance";
import AddSentimentJudgementCommand from "../../../../lib/model/judgement/sentiment/command/AddSentimentJudgementCommand";

const SpanAnnotation: React.FC<{ phase: Phase }> = ({ phase }) => {


    const [selectionStart, setSelectionStart] = useState<number | null>(null);
    const [selectionEnd, setSelectionEnd] = useState<number | null>(null);
    const [labelPosition, setLabelPosition] = useState<{ top: number, left: number } | null>(null);

    const [onSelect, setOnSelect] = useState<{ text: string | null, startIndex?: number, endIndex?: number }>({
        text: null,
        startIndex: undefined,
        endIndex: undefined
    });

    const [judgements, setJudgements] = useState<Array<{ text: string | null, startIndex: number | null, endIndex: number | null, label: string | null }>>([]);

    const [annotation, setAnnotation] = useState({
        instance: null as unknown as SentimentInstance,
        judgements: "",
        comment: "",
        initialLoad: true

    });

    const handleMouseUp = () => {
        const selection = window.getSelection();
        const selectedText = selection?.toString();
        const parentElement = document.getElementById('text');
        const fullText = parentElement?.textContent;

        if (selectedText && fullText) {
            const startIndex = fullText.indexOf(selectedText);
            const endIndex = startIndex + selectedText.length;
            setSelectionStart(startIndex);
            setSelectionEnd(endIndex);
            // Get coordinates of the selected text
            const range = selection?.getRangeAt(0);
            if (range) {
                const rect = range.getBoundingClientRect();
                if (rect) {
                    // Calculate position relative to the selected text
                    const position = {
                        top: rect.top + window.scrollY,
                        left: rect.right + window.scrollX + 10 // Adjust 10px for spacing
                    };
                    setLabelPosition(position);
                }
                setOnSelect({
                    text: selectedText,
                    startIndex: startIndex,
                    endIndex: endIndex
                });

            }
        } else {
            toast.info('No text selected.');
            setLabelPosition(null);
            setOnSelect({
                text: null,
                startIndex: 0,
                endIndex: 0
            })
        }
    };


    const page = 0;
    const storage = useStorage();

    const annotationAccess = useFetchAnnotationAccess(
        phase?.getId().getOwner(),
        phase?.getId().getProject(),
        phase?.getId().getPhase(),
        !!phase
    );

    const { data: userAnnotationCount, mutate: mutateCountJudgements } = useFetchAttemptedJudgement(
        phase?.getId().getOwner(),
        phase?.getId().getProject(),
        phase?.getId().getPhase(),
        !!phase
    );

    const { data: userAllocatedInstance, mutate: mutateInstanceCount } = useFetchAllocatedInstanceNumber(
        phase?.getId().getOwner(),
        phase?.getId().getProject(),
        phase?.getId().getPhase(),
        !!phase
    );

    const handleClickLabel = (label: string) => {
        if (label === null) {
            toast.warning("Label should not be empty");
            return;
        }
        if (onSelect.text === "" || (onSelect.endIndex === 0 && onSelect.startIndex === 0)) {
            toast.info("No text slected.");
            return;
        }

        setJudgements(prevJudgements => {
            const newJudgement = {
                text: onSelect.text,
                startIndex: onSelect.startIndex !== undefined ? onSelect.startIndex : null,
                endIndex: onSelect.endIndex !== undefined ? onSelect.endIndex : null,
                label: label,
            };
            const updatedJudgements = [...prevJudgements, newJudgement];

            // Now, set the annotation based on the updated judgements
            setAnnotation({
                ...annotation,
                judgements: updatedJudgements.map(judgement => `${judgement.text} [${judgement.startIndex},${judgement.endIndex},${judgement.label}]`).join(', ')
            });

            return updatedJudgements;
        });
    };


    const fetchNewAnnotation = () => {
        mutateCountJudgements();
        setOnSelect({
            ...onSelect,
            text: "",
            startIndex: 0,
            endIndex: 0
        })
        fetchRandomInstance<SentimentInstance, SentimentInstanceConstructor>(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), (new SentimentInstanceConstructor()), storage.get)
            .then((instance) => {
                if (instance) {
                    setAnnotation({
                        ...annotation,
                        instance: instance,
                        judgements: "",
                        comment: "",
                    });
                }

            }).catch((error) => {
                toast.error("Could not fetch new annotation. Check if instances are provided for annotation.");
                Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}`);
            });


    }


    const handleSubmitAnnotation = () => {
        if (annotation.judgements === null || annotation.judgements === "") {
            toast.info("Judgements cannot be empty.")
            return;
        }
        mutateCountJudgements();
        const resultCommand = verifyResultCommand(phase, annotation);
        setAnnotation({
            ...annotation,
            instance: null as unknown as SentimentInstance,
            judgements: "",
            comment: ""
        });

        if (resultCommand !== null) {
            annotateSentiment(resultCommand, storage.get)
                .then((result) => {
                    setJudgements([]);
                    setLabelPosition(null);
                    setAnnotation({
                        ...annotation,
                        judgements: "",
                        comment: ""
                    });
                    setOnSelect({
                        ...onSelect,
                        text: "",
                        startIndex: 0,
                        endIndex: 0
                    })

                    fetchNewAnnotation();
                })
                .catch((error) => {
                    if (error?.response?.status === 500) {
                        toast.error("Error while adding judgement: " + error.response.data.message + "!");
                    } else {
                        toast.warning("The system is currently not available, please try again later!");
                    }
                }
                );

        }

    }


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
            <div className="w-full flex flex-col justify-center space-y-4" onMouseUp={handleMouseUp}>
                <SpanUsageField usage={annotation?.instance?.getUsage()} />
            </div>

            {labelPosition && (
                <div className="w-full flex flex-col my-8 self-center items-left font-dm-mono-regular text-lg">
                    <div className="font-bold text-lg">
                        Judgement
                    </div>
                    <div className="font-mono text-lg">
                        Seletected text: {onSelect.text}
                    </div>
                    <div className="font-mono text-lg">
                        Text position: {`${onSelect.startIndex}, ${onSelect.endIndex}`}
                    </div>

                    <div className="w-full flex flex-row my-8 items-center justify-between xl:justify-center xl:space-x-6">
                        {annotation?.instance?.getLabelSet().concat(annotation?.instance?.getNonLabel()).map((label) => {
                            return (
                                <div key={label}
                                    className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium"
                                    onClick={() => handleClickLabel(label)}
                                    style={{ minWidth: "0", overflow: "hidden" }}>
                                    <div className="w-auto min-w-8 h-8 m-6 text-center text-lg">
                                        {label}
                                    </div>
                                </div>
                            );
                        })}
                    </div>


                </div>

            )}
            <div className="h-32 flex items-start border-l-2 py-2 px-3 mt-2">
                <FiFeather className='basic-svg' />
                <textarea
                    className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                    name="judgement"
                    placeholder={"judgement"}
                    value={annotation.judgements}
                    onChange={(e: any) => setAnnotation({
                        ...annotation,
                        judgements: e.target.value
                    })} />
            </div>

            <div className="flex self-end">
                <button className="text-base16-gray-100 bg-base16-gray-900 hover:text-base16-green transition-all duration-500"
                    onClick={handleSubmitAnnotation}>
                    <div className="flex my-2 mx-8 font-dm-mono-medium font-bold text-xl items-center">
                        Submit <FiChevronRight className='basic-svg stroke-[3]' />
                    </div>
                </button>
            </div>


        </div>
    );
}

export default SpanAnnotation;

function verifyResultCommand(phase: Phase, annotation: {
    instance: SentimentInstance;
    judgements: string;
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

    if (annotation.judgements === undefined || annotation.judgements === null || annotation.judgements === "") {
        toast.error("Label cannot be empty.");
        return null;
    }

    return new AddSentimentJudgementCommand(
        phase.getId().getOwner(),
        phase.getId().getProject(),
        phase.getId().getPhase(),
        annotation.instance.getId().getInstanceId(),
        annotation.judgements,
        annotation.comment
    );
}