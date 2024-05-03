// react
import { useState } from "react";

// next
import { useRouter } from "next/router";

// react toast
import { toast } from "react-toastify";

// icons
import { FiCreditCard, FiEdit2, FiEdit3, FiFeather, FiGlobe, FiInfo, FiLayers, FiSliders, FiUnderline } from "react-icons/fi";
import AnnotationType from "../../../lib/model/annotationtype/model/AnnotationType";

// models
import Project from "../../../lib/model/project/model/Project";
import { useFetchAllAnnotationTypes } from "../../../lib/service/annotationtype/AnnotationTypeResource";

// services
import useStorage from "../../../lib/hook/useStorage";
import { createPhase } from "../../../lib/service/phase/PhaseResource";

// components
import DropdownSelect from "../../generic/dropdown/dropdownselect";
import CreatePhaseCommand from "../../../lib/model/phase/command/CreatePhaseCommand";
import Checkbox from "../../generic/checkbox/checkbox";
import { useFetchAllSamplingMethods } from "../../../lib/service/sampling/SamplingResource";
import Sampling from "../../../lib/model/sampling/model/Sampling";
import { useFetchAllStatisticAnnotationMeasureResource } from "../../../lib/service/statistic/statistisannotationmeasure/StatisticAnnotationMeasureResource";
import StatisticAnnotationMeasure from "../../../lib/model/statistic/statisticannotationmeasure/model/StatisticAnnotationMeasure";
import HelpButton from "../../generic/button/helpbutton";
import IconButtonWithTooltip from "../../generic/button/iconbuttonwithtooltip";

const CreatePhaseModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, project: Project, mutateCallback: Function }> = ({ isOpen, closeModalCallback, project, mutateCallback }) => {

    // hooks
    const storage = useStorage();

    // data
    const annotationTypes = useFetchAllAnnotationTypes(isOpen);
    const sampling = useFetchAllSamplingMethods(isOpen);
    const annotationagreement = useFetchAllStatisticAnnotationMeasureResource(isOpen);

    // state and handlers
    const [modalState, setModalState] = useState({
        phasename: "",
        annotationType: null as unknown as AnnotationType,
        sampling: null as unknown as Sampling,
        isTutorial: false,
        agreementStrategy: null as unknown as StatisticAnnotationMeasure,
        threshold: 0,
        description: "",
        taskhead: "",
        instancePerSample: null as unknown as number
    })

    const onConfirm = () => {
        const command = validateAndCreatePhase(project, modalState);

        if (command) {
            createPhase(command, storage.get)
                .then((phase) => {
                    toast.success("Phase Created!");

                    // close modal
                    setModalState({
                        phasename: "",
                        annotationType: null as unknown as AnnotationType,
                        sampling: null as unknown as Sampling,
                        isTutorial: false,
                        agreementStrategy: null as unknown as StatisticAnnotationMeasure,
                        threshold: 0,
                        description: "",
                        taskhead: "",
                        instancePerSample: null as unknown as number
                    });
                    mutateCallback();
                    closeModalCallback();
                }).catch((error) => {
                    if (error?.response?.status === 500) {
                        toast.error("Error while creating phase: " + error.response.data.message + "!");
                    } else {
                        toast.warning("The system is currently not available, please try again later!");
                    }
                });
        }
    }

    const onCancel = () => {
        toast.info("Canceled creating a new phase.");
        setModalState({
            phasename: "",
            annotationType: null as unknown as AnnotationType,
            sampling: null as unknown as Sampling,
            isTutorial: false,
            agreementStrategy: null as unknown as StatisticAnnotationMeasure,
            threshold: 0,
            description: "",
            taskhead: "",
            instancePerSample: null as unknown as number
        });
        closeModalCallback();
    }


    if (!isOpen || !project) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" style={{ maxHeight: "80vh", overflowY: "auto" }} onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                    Project: {project.getName()}
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You are about to add a new phase to your project.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Phase
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiLayers className='basic-svg' />
                                        <input
                                            id="phasename"
                                            name="phasename"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder="Name"
                                            type={"text"}
                                            value={modalState.phasename}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                phasename: e.target.value
                                            })} />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Annotation Type
                                    </div>
                                    <div className="py-2 px-3 border-b-2 mt-2">
                                        <DropdownSelect
                                            icon={<FiEdit2 className="basic-svg" />}
                                            items={annotationTypes.annotationTypes.sort((a, b) => a.getVisiblename().localeCompare(b.getVisiblename()))}
                                            selected={modalState.annotationType ? [modalState.annotationType] : []}
                                            onSelectFunction={(annotationType: AnnotationType) => setModalState({
                                                ...modalState,
                                                annotationType: annotationType
                                            })}
                                            message={modalState.annotationType ? modalState.annotationType.getVisiblename() : "None selected yet"} />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="flex items-center">
                                        <div className="font-bold text-lg mr-2">
                                            Sampling Strategy
                                        </div>
                                        <div className="px-12">
                                            <HelpButton
                                                title="Help: Choosing Sampling Startegy"
                                                tooltip="Choosing Sampling Startegy"
                                                text="When assigning tasks to annotators, number of instances annotator will received is denoted as 'n'. Once 'n' is set, it cannot be changed. Therefore, it's crucial 
                                                to carefully select the task sampling strategy as it will remain fixed throughout the annotation process of the phase"
                                                reference=""
                                                linkage={false}
                                            />
                                        </div>
                                    </div>
                                    <div className="py-2 px-3 border-b-2 mt-2 flex items-center">
                                        <DropdownSelect
                                            icon={<FiSliders className="basic-svg" />}
                                            items={sampling.sampling.sort((a, b) => a.getVisiblename().localeCompare(b.getVisiblename()))}
                                            selected={modalState.sampling ? [modalState.sampling] : []}
                                            onSelectFunction={(sampling: Sampling) => setModalState({
                                                ...modalState,
                                                sampling: sampling
                                            })}
                                            message={modalState.sampling ? modalState.sampling.getVisiblename() : "None selected yet"} />
                                        {modalState.sampling && (modalState.sampling.getName() === "N_SAMPLING_RANDOM_WITH_REPLACEMENT" || modalState.sampling.getName() === "N_SAMPLING_RANDOM_WITHOUT_REPLACEMENT") && (
                                            <div className="ml-auto">
                                                <input
                                                    id="instancePerSample"
                                                    name="instancePerSample"
                                                    className="pl-1 outline-none border-2 border-gray-700 h-auto w-auto max-w-20"
                                                    placeholder="0"
                                                    type={"number"}
                                                    value={modalState.instancePerSample}
                                                    onChange={(e: any) => setModalState({
                                                        ...modalState,
                                                        instancePerSample: e.target.value
                                                    })} />
                                            </div>
                                        )}
                                    </div>


                                </div>


                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Tutorial
                                    </div>


                                    <div className="flex w-full items-center py-2 px-3">
                                        <Checkbox
                                            selected={modalState.isTutorial}
                                            description={"Phase is a tutorial"}
                                            onClick={() => setModalState({
                                                ...modalState,
                                                isTutorial: !modalState.isTutorial
                                            })} />
                                    </div>

                                    <div className="py-2 px-3 border-b-2 mt-2 ">
                                        <div className="font-light mb-2 -ml-3">
                                            Annotation Agreement Strategy
                                        </div>
                                        <DropdownSelect
                                            icon={<FiSliders className="basic-svg" />}
                                            items={annotationagreement.statisticAnnotationMeasures.sort((a, b) => a.getVisiblename().localeCompare(b.getVisiblename()))}
                                            selected={modalState.agreementStrategy ? [modalState.agreementStrategy] : []}
                                            onSelectFunction={(agreement: StatisticAnnotationMeasure) => setModalState({
                                                ...modalState,
                                                agreementStrategy: agreement
                                            })}
                                            message={modalState.agreementStrategy ? modalState.agreementStrategy.getVisiblename() : "None selected yet"} />
                                    </div>

                                    <div className="py-2 px-3 border-b-2 mt-2 ">
                                        <input
                                            id="threshold"
                                            name="threshold"
                                            className="pl-3 flex flex-auto outline-none border-none w-full"
                                            placeholder="Threshold"
                                            type={"number"}
                                            step={0.01}
                                            value={modalState.threshold}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                threshold: e.target.value
                                            })} />
                                    </div>
                                </div>
                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Task Head
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiEdit3 className='basic-svg' />
                                        <input
                                            id="taskhead"
                                            name="taskhead"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder="Task Heading"
                                            type={"text"}
                                            value={modalState.taskhead}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                taskhead: e.target.value
                                            })} />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Description
                                    </div>
                                    <div className="h-32 flex items-start border-l-2 py-2 px-3 my-6">
                                        <FiFeather className='basic-svg' />
                                        <textarea
                                            className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                                            name="description"
                                            placeholder="Description"
                                            value={modalState.description}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                description: e.target.value
                                            })} />
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8">
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={() => onCancel()}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onConfirm}>Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )
}

export default CreatePhaseModal;

function validateAndCreatePhase(project: Project, modalState: {
    phasename: string;
    annotationType: AnnotationType;
    sampling: Sampling;
    isTutorial: boolean;
    agreementStrategy: StatisticAnnotationMeasure;
    threshold: number;
    description: string;
    taskhead: string;
    instancePerSample: number;
}): CreatePhaseCommand | null {
    if (project === undefined || project === null) {
        toast.warning("Project is undefined. This should not happen. Please try again later.");
        return null;
    }

    if (!modalState.phasename) {
        toast.warning("Please enter a name for the phase.");
        return null;
    }

    if (!modalState.annotationType) {
        toast.warning("Please select an annotation type.");
        return null;
    }

    if (!modalState.sampling) {
        toast.warning("Please select a sampling strategy.");
        return null;
    }

    if (modalState.isTutorial && (!modalState.agreementStrategy || !modalState.threshold)) {
        toast.warning("Please select an annotation agreement strategy for the tutorial phase.");
        return null;
    }

    return new CreatePhaseCommand(
        modalState.phasename,
        project.getId().getOwner(),
        project.getId().getName(),
        modalState.annotationType.getName(),
        modalState.sampling.getName(),
        modalState.isTutorial,
        modalState.agreementStrategy ? modalState.agreementStrategy.getId() : "",
        modalState.threshold,
        modalState.description,
        modalState.taskhead,
        modalState.instancePerSample
    );
}
