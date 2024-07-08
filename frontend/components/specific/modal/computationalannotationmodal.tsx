// react
import { useState } from "react";

// toast
import { toast } from "react-toastify";
// icons
import { FiBook, FiMonitor } from "react-icons/fi";
import { SiOpenai } from "react-icons/si";
import { RiShieldKeyholeFill } from "react-icons/ri";
import { TbPrompt } from "react-icons/tb";


// hooks
import useStorage from "../../../lib/hook/useStorage";

// services
import { useFetchComputationAnnotatorsOfPhase } from "../../../lib/service/annotator/AnnotatorResource";

// models
import Phase from "../../../lib/model/phase/model/Phase";
import Annotator from "../../../lib/model/annotator/model/Annotator";
import StartComputationalAnnotationCommand from "../../../lib/model/phase/command/StartComputationalAnnotationCommand";

// components
import DropdownSelect from "../../generic/dropdown/dropdownselect";
import { startComputationalAnnotation, useFetchPhases } from "../../../lib/service/phase/PhaseResource";
import OpenAIModel from "../../../lib/model/computationalannotator/openaimodel/model/OpenAIModel";
import { chatGptLexsubAnnotation, chatGptSentimentAnnotation, chatGptUsePairAnnotation, chatLexSubTutorialAnnotation, chatUsePairTutorialAnnotation, chatWSSIMAnnotation, chatWSSIMTutorialAnnotation, tinyAnnotate, useFetchAllOpenAIModel, UseFetchPagedUsePairJudgementsTutorials } from "../../../lib/service/computationalAnnotator/ComputationalAnnotatorResource";
import BasicCheckbox from "../../generic/checkbox/basiccheckbox";
import router, { Router, useRouter } from "next/router";
import ANNOTATIONTYPES from "../../../lib/AnnotationTypes";
import ComputationalAnnotatorCommand from "../../../lib/model/computationalannotator/ComputationalAnnotatorCommand";
import IconButtonOnClick from "../../generic/button/iconbuttononclick";
import { useFetchGuideline, useFetchGuidelines } from "../../../lib/service/guideline/GuidelineResource";
import { useFetchProject } from "../../../lib/service/project/ProjectResource";
import { useFetchPagedUsePairJudgements } from "../../../lib/service/judgement/JudgementResource";
import { MdOutlineAutoDelete } from "react-icons/md";
import Guideline from "../../../lib/model/guideline/model/Guideline";
import HelpButton from "../../generic/button/helpbutton";



const ComputationalAnnotationModal: React.FC<{
    guidelines: Guideline[],
    isOpen: boolean, closeModalCallback: Function, phase: Phase,
    mutateCallback: Function, setTutorialHistory: Function,
    openProcessingModal: Function, setLoadingStatus: Function
}> = ({ guidelines, isOpen, closeModalCallback, phase,
    mutateCallback, setTutorialHistory, openProcessingModal, setLoadingStatus }) => {

        // hooks
        const storage = useStorage();

        const Router = useRouter();

        const eligibleAnnotators = useFetchComputationAnnotatorsOfPhase(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), isOpen && !!phase);


        const tutorials = useFetchPhases(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getAnnotationType().getName(), true, isOpen && !!phase);

        const openaimodels = useFetchAllOpenAIModel();

        openaimodels.openaimodels.sort((a, b) => a.getName().localeCompare(b.getName()));
        // modal
        const [modalState, setModalState] = useState({
            selectedAnnotator: [] as Annotator[]
        });

        const [showApiKey, setShowApiKey] = useState(false);
        const [tutorial, setTutorial] = useState(false);
        const [guideline, setGuideline] = useState(false);
        const [guidelineAndTutorial, setGuidelineAndTutorial] = useState(false);

        const [chatGptModalState, setChatGptModalState] = useState({
            apiKey: "",
            model: null as unknown as OpenAIModel,
            temperature: null as unknown as number,
            topP: null as unknown as number,
            otherModel: "",
            prompt: "",
            system: "",
            finalMessage: "",
            lemma: ""
        });

        const usepairjudgement = (pname: string) => UseFetchPagedUsePairJudgementsTutorials(phase?.getId().getOwner(), phase?.getId().getProject(), pname, 0, !!phase);

        const [tutPrompt, setTutPrompt] = useState("");

        const resetChatGptModalState = () => {
            setChatGptModalState({
                ...chatGptModalState,
                apiKey: "",
                model: null as unknown as OpenAIModel,
                prompt: "",
            });

        }

        const onConfirm = () => {


            if (!modalState.selectedAnnotator.some(Annotator => Annotator.getVisiblename() === "ChatGpt")) {
                computationalAnnotation();
            }

          

            if (modalState.selectedAnnotator.some(Annotator => Annotator.getVisiblename() === "ChatGpt")) {
                chatGptAnnotation()
                return;
            }

            if (modalState.selectedAnnotator.some(Annotator => Annotator.getVisiblename() === "TinyLLM")) {
                tinyLLMAnnotate()
                return;
            }
            if (
                modalState.selectedAnnotator.some(Annotator => Annotator.getVisiblename() === "ChatGpt") &&
                (modalState.selectedAnnotator.some(Annotator => Annotator.getVisiblename() === "UREL") ||
                    modalState.selectedAnnotator.some(Annotator => Annotator.getVisiblename() === "deep"))
            ) {
                computationalAnnotation();
                chatGptAnnotation();
            }

        }

        const chatGptAnnotation = () => {
            const command = verifyComputationalAnnotation(chatGptModalState, phase)
            if (command == null) {
                return;
            }

            const username = storage.get('USER');

            if (!username) {
                toast.warning("This should not happen. Please contact the administrator.");
                return;
            }

            if (phase?.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USEPAIR) {
                closeModalCallback();
                setLoadingStatus(true);
                chatGptUsePairAnnotation(command, storage.get)
                    .then((data) => {
                        setLoadingStatus(false);
                        resetChatGptModalState();
                    })
                    .then(() => {
                        Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}/${phase.getId().getPhase()}/judgement`);
                    })
                    .catch((error) => {
                        if (error?.response?.status === 500) {
                            toast.error(error.response.data.message + "!");
                            resetChatGptModalState();
                            Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}/${phase.getId().getPhase()}/judgement`);
                        } else {
                            toast.warning(error.response.data.message);
                        }
                    });
            }
            if (phase?.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_SENTIMENT) {
                closeModalCallback();
                setLoadingStatus(true);
                chatGptSentimentAnnotation(command, storage.get)
                    .then(() => {
                        setLoadingStatus(false);
                        resetChatGptModalState();
                    })
                    .then(() => {
                        Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}/${phase.getId().getPhase()}/judgement`);
                    })
                    .catch((error) => {
                        if (error?.response?.status === 500) {
                            toast.error(error.response.data.message + "!");
                            resetChatGptModalState();
                            Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}/${phase.getId().getPhase()}/judgement`);

                        } else {
                            toast.warning(error.response.data.message);
                        }
                    });
            }


            if (phase?.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_WSSIM) {
                closeModalCallback();
                setLoadingStatus(true);
                chatWSSIMAnnotation(command, storage.get)
                    .then(() => {
                        setLoadingStatus(false);
                        resetChatGptModalState();
                    })
                    .then(() => {
                        Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}/${phase.getId().getPhase()}/judgement`);
                    })
                    .catch((error) => {
                        if (error?.response?.status === 500) {
                            toast.error(error.response.data.message + "!");
                            resetChatGptModalState();
                            Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}/${phase.getId().getPhase()}/judgement`);

                        } else {
                            toast.warning(error.response.data.message);
                        }
                    });
            }
        }

        const tinyLLMAnnotate = () => {


          
            const username = storage.get('USER');

            if (!username) {
                toast.warning("This should not happen. Please contact the administrator.");
                return;
            }

            if (phase?.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_SENTIMENT) {
                closeModalCallback();
                setLoadingStatus(true);
                tinyAnnotate(new ComputationalAnnotatorCommand(
                    phase?.getId().getOwner(),
                    phase?.getId().getProject(),
                    phase?.getId().getPhase(),
                    "keydfjsadjlksajdklsadjlksaj",
                    0.0,
                    0.0,
                    "model",
                    "prompt",
                    "system",
                    "finale"), storage.get)
                    .then(() => {
                        setLoadingStatus(false);
                        resetChatGptModalState();
                    })
                    .then(() => {
                        Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}/${phase.getId().getPhase()}/judgement`);
                    })
                    .catch((error) => {
                        if (error?.response?.status === 500) {
                            toast.error(error.response.data.message + "!");
                            resetChatGptModalState();
                            Router.push(`/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}/${phase.getId().getPhase()}/judgement`);

                        } else {
                            toast.warning(error.response.data.message);
                        }
                    });
            }
        }

        const computationalAnnotation = () => {
            const command = verifyAndAddCommand(phase, modalState.selectedAnnotator);

            if (command) {
                startComputationalAnnotation(command, storage.get).then(() => {
                    toast.success("Successfully started computational annotation.");
                    setModalState({
                        selectedAnnotator: []
                    });
                    mutateCallback();
                    closeModalCallback();
                }).catch((error) => {
                    if (error?.response?.status === 500) {
                        toast.error("Error while adding user: " + error.response.data.message + "!");
                    } else {
                        toast.warning("The system is currently not available, please try again later!");
                    }
                    setModalState({
                        selectedAnnotator: []
                    });
                });
            }

        }


        const onConfirmTutorial = () => {

            const command = verifyComputationalAnnotation(chatGptModalState, phase)
            if (command == null) {
                return;
            }

            const username = storage.get('USER');

            if (!username) {
                toast.warning("This should not happen. Please contact the administrator.");
                return;
            }

            if (phase?.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USEPAIR) {

                toast.success("Successfully started computational annotation.")
                closeModalCallback();
                setLoadingStatus(true);
                chatUsePairTutorialAnnotation(command, storage.get)
                    .then((data) => { setTutorialHistory(data) })
                    .then(() => { setLoadingStatus(false) })
                    .then(() => { openProcessingModal(true) })
                    .catch((error) => {
                        if (error?.response?.status === 500) {
                            toast.error(error.response.data.message + "!");
                            resetChatGptModalState();
                            setLoadingStatus(false);
                        } else {
                            toast.warning(error);
                            setLoadingStatus(false);

                        }
                    });
            }

            if (phase?.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_LEXSUB) {
                toast.success("Successfully started computational annotation.")
                closeModalCallback();
                setLoadingStatus(true);
                chatLexSubTutorialAnnotation(command, storage.get)
                    .then((data) => { setTutorialHistory(data) })
                    .then(() => { setLoadingStatus(false) })
                    .then(() => { openProcessingModal(true) })
                    .catch((error) => {
                        if (error?.response?.status === 500) {
                            toast.error(error.response.data.message + "!");
                            resetChatGptModalState();
                            setLoadingStatus(false);

                        } else {
                            toast.warning(error);
                            setLoadingStatus(false);

                        }
                    });
            }

            if (phase?.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_WSSIM) {

                toast.success("Successfully started computational annotation.")
                closeModalCallback();
                setLoadingStatus(true);
                chatWSSIMTutorialAnnotation(command, storage.get)
                    .then((data) => { setTutorialHistory(data) })
                    .then(() => { setLoadingStatus(false) })
                    .then(() => { openProcessingModal(true) })
                    .catch((error) => {
                        if (error?.response?.status === 500) {
                            toast.error(error.response.data.message + "!");
                            resetChatGptModalState();
                            setLoadingStatus(false);

                        } else {
                            toast.warning(error);
                            setLoadingStatus(false);

                        }
                    });
            }

        }

        const onCancel = () => {
            toast.info("Canceled computation.");
            setModalState({
                selectedAnnotator: []
            });
            resetChatGptModalState();
            closeModalCallback();
        }

        if (!isOpen || !phase) {
            return null;
        }

        return (
            <>
                <div className="relative z-10 font-dm-mono-medium" onClick={() => onCancel()}>
                    <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

                    <div className="fixed z-10 inset-0 overflow-y-auto">
                        <div className="flex items-center justify-center min-h-full">
                            <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                                <div className="mx-4">
                                    <div className="flex flex-col items-left mt-6">
                                        <div className="font-black text-xl">
                                            Start Computational Annotation
                                        </div>
                                        <div className="font-dm-mono-regular my-2">
                                            You are about to start a computational annotation for phase {phase.getName()}. Please select the computational annotators you want to use.
                                            Note that the this process might take some time depending on the amount of data and the computational annotators you selected.
                                        </div>

                                        <div className="flex flex-col items-left my-6">
                                            <div className="font-bold text-lg">
                                                Select Computational Annotators
                                            </div>
                                            <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                                <DropdownSelect
                                                    icon={<FiMonitor className="basic-svg" />}
                                                    items={eligibleAnnotators.annotators}
                                                    selected={modalState.selectedAnnotator}
                                                    onSelectFunction={(user: Annotator) => setModalState({
                                                        ...modalState,
                                                        selectedAnnotator: calculateNewSelectedUserArray(user, modalState.selectedAnnotator)
                                                    })}
                                                    message={
                                                        eligibleAnnotators.annotators.length === 0 ? "No Computational Annotators available" :
                                                            modalState.selectedAnnotator.length > 0 ?
                                                                `Number selected Computational annotators: ${modalState.selectedAnnotator.length}`
                                                                : "No Computational annotators selected"
                                                    } />
                                            </div>
                                            {
                                                modalState.selectedAnnotator.some(
                                                    (annotator) =>
                                                        annotator.getId().getUser() === "ChatGpt") && (
                                                    <div className="flex flex-col items-left my-6">

                                                        <div className="font-bold-mono text-m">OpenAI Key</div>
                                                        <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                                            <RiShieldKeyholeFill className="basic-svg" />
                                                            <input
                                                                id="apiKey"
                                                                name="apiKey"
                                                                className="pl-3 flex flex-auto outline-none border-none"
                                                                placeholder="Api key"
                                                                type={showApiKey ? "text" : "password"}
                                                                value={chatGptModalState.apiKey}
                                                                onChange={(e) =>
                                                                    setChatGptModalState({
                                                                        ...chatGptModalState,
                                                                        apiKey: e.target.value,
                                                                    })
                                                                }
                                                            />
                                                        </div>
                                                        <div className="py-2 px-3 text-sm">
                                                            <BasicCheckbox
                                                                selected={showApiKey}
                                                                description={"Show API KEY"}
                                                                onClick={() => setShowApiKey(!showApiKey)}
                                                            />
                                                        </div>

                                                        <div className="flex justify-between items-center">
                                                            <div className="font-bold-mono text-m">Choose Model</div>
                                                            {/* y */}
                                                        </div>
                                                        <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                                            <DropdownSelect
                                                                icon={<SiOpenai className="basic-svg" />}
                                                                items={openaimodels.openaimodels}
                                                                selected={chatGptModalState.model ? [chatGptModalState.model] : []}
                                                                onSelectFunction={(model) =>
                                                                    setChatGptModalState({
                                                                        ...chatGptModalState,
                                                                        model: model,
                                                                    })
                                                                }
                                                                message={chatGptModalState.model ? chatGptModalState.model.getVisiblename() : "None selected yet"}
                                                            />
                                                            {chatGptModalState.model &&
                                                                chatGptModalState.model.getVisiblename() === "others" && (
                                                                    <div>

                                                                        <input
                                                                            id="model"
                                                                            name="model"
                                                                            className="pl-3 flex flex-auto outline-none border-none"
                                                                            placeholder="Enter model name"
                                                                            type={"text"}
                                                                            value={chatGptModalState.otherModel}
                                                                            onChange={(e) =>
                                                                                setChatGptModalState({
                                                                                    ...chatGptModalState,
                                                                                    otherModel: e.target.value,
                                                                                })
                                                                            }
                                                                        />
                                                                    </div>
                                                                )}

                                                        </div>
                                                        <div className="flex items-center">
                                                            <div className="font-bold-mono text-m">
                                                                Temperature
                                                            </div>
                                                            <div className="px-12">
                                                                <HelpButton
                                                                    title="Help: "
                                                                    tooltip="Choosing Temperature"
                                                                    text=""
                                                                    reference=""
                                                                    linkage={false}
                                                                />
                                                            </div>
                                                            <div className="py-2 px-3 border-b-2 mt-2 ">
                                                                <input
                                                                    id="temperature"
                                                                    name="temperature"
                                                                    className="pl-3 flex flex-auto outline-none border-none w-full"
                                                                    placeholder="temperature"
                                                                    type={"number"}
                                                                    min={0.0}
                                                                    step={0.1}
                                                                    max={1.0}
                                                                    value={chatGptModalState.temperature}
                                                                    onChange={(e: any) => setChatGptModalState({
                                                                        ...chatGptModalState,
                                                                        temperature: e.target.value
                                                                    })} />
                                                            </div>
                                                        </div>
                                                        <div className="flex items-center">
                                                            <div className="font-bold-mono text-m">
                                                                topP
                                                            </div>
                                                            <div className="px-12">
                                                                <HelpButton
                                                                    title="Help: "
                                                                    tooltip="Choosing topP"
                                                                    text=""
                                                                    reference=""
                                                                    linkage={false}
                                                                />
                                                            </div>
                                                            <div className="py-2 px-3 border-b-2 mt-2 ">
                                                                <input
                                                                    id="topP"
                                                                    name="topP"
                                                                    className="pl-3 flex flex-auto outline-none border-none w-full"
                                                                    placeholder="topP"
                                                                    type={"number"}
                                                                    min={0.0}
                                                                    step={0.1}
                                                                    max={1.0}
                                                                    value={chatGptModalState.topP}
                                                                    onChange={(e: any) => setChatGptModalState({
                                                                        ...chatGptModalState,
                                                                        topP: e.target.value
                                                                    })} />
                                                            </div>
                                                        </div>
                                                        <div className="py-2 px-3 text-sm">
                                                            <BasicCheckbox
                                                                selected={tutorial}
                                                                description={"Test Model With Tutorial Phase"}
                                                                onClick={() => {
                                                                    setTutorial(!tutorial);
                                                                }}

                                                            />
                                                        </div>

                                                        <div className="flex items-center justify-between">
                                                            <div className="font-bold-mono text-m">
                                                                Upload {tutorial && (<span>Tutorial</span>)} Prompt
                                                            </div>
                                                            <IconButtonOnClick
                                                                icon={<MdOutlineAutoDelete className="basic-svg" />}
                                                                tooltip={"clear prompt"}
                                                                onClick={() => {
                                                                    setChatGptModalState({
                                                                        ...chatGptModalState,
                                                                        prompt: ""
                                                                    })
                                                                }}
                                                            />
                                                        </div>
                                                        <div className="py-2 px-3 flex items-center border-b-2 mt-2">
                                                            <TbPrompt className="basic-svg" />
                                                            <textarea
                                                                className="w-full h-full  pl-3 flex flex-auto outline-none border-none"
                                                                name="sytem"
                                                                placeholder="System Message"
                                                                value={chatGptModalState.system}
                                                                onChange={(e: any) => setChatGptModalState({
                                                                    ...chatGptModalState,
                                                                    system: e.target.value
                                                                })} />
                                                        </div>


                                                        <div className="py-2 px-3 flex items-center border-b-2 mt-2">
                                                            <TbPrompt className="basic-svg" />
                                                            <textarea
                                                                className="w-full h-full  pl-3 flex flex-auto outline-none border-none"
                                                                name="prompt"
                                                                placeholder="prompt"
                                                                value={chatGptModalState.prompt}
                                                                onChange={(e: any) => setChatGptModalState({
                                                                    ...chatGptModalState,
                                                                    prompt: e.target.value
                                                                })} />
                                                        </div>
                                                        <div className="py-2 px-3 flex items-center border-b-2 mt-2">
                                                            <TbPrompt className="basic-svg" />
                                                            <textarea
                                                                className="w-full h-full  pl-3 flex flex-auto outline-none border-none"
                                                                name="finalMessage"
                                                                placeholder="Final Message"
                                                                value={chatGptModalState.finalMessage}
                                                                onChange={(e: any) => setChatGptModalState({
                                                                    ...chatGptModalState,
                                                                    finalMessage: e.target.value
                                                                })} />
                                                        </div>




                                                        <div className="py-2 px-3 text-sm">
                                                            <BasicCheckbox
                                                                selected={guideline}
                                                                description={"Use guideline as a prompt"}
                                                                onClick={() => {
                                                                    setGuideline(!guideline);
                                                                    setGuidelineAndTutorial(false);

                                                                }}

                                                            />
                                                        </div>
                                                        <div className="py-2 px-3 text-sm">
                                                            <BasicCheckbox
                                                                selected={guidelineAndTutorial}
                                                                description={"Use guideline and tutorial as a prompt"}
                                                                onClick={() => {
                                                                    setGuideline(false);
                                                                    setGuidelineAndTutorial(!guidelineAndTutorial);

                                                                }}
                                                            />
                                                        </div>
                                                        {(guideline || guidelineAndTutorial) && (
                                                            <div className="flex flex-col mt-2">
                                                                {!guidelines || guidelines.length === 0 ? (
                                                                    <div className="flex flex-row items-center">
                                                                        <div className="font-dm-mono-medium text-base mr-auto">
                                                                            No Guidelines
                                                                        </div>
                                                                    </div>
                                                                ) : (
                                                                    guidelines.map((item, index) => (
                                                                        <div className="flex flex-row justify-between items-center space-x-12" key={index}>
                                                                            <div className="font-dm-mono-mdeium text-base mr-auto">
                                                                                {/* {item.getTutorialName()}: */}
                                                                                {item.getId().getName()}
                                                                            </div>
                                                                            <div className="flex space-x-2 ml-2">
                                                                                <IconButtonOnClick
                                                                                    icon={<FiBook className="basic-svg" />}
                                                                                    tooltip={`Use guideline:${item.getId().getName()}  as a prompt`}
                                                                                    onClick={() => {
                                                                                        if (guideline || guidelineAndTutorial) {
                                                                                            setChatGptModalState({
                                                                                                ...chatGptModalState,
                                                                                                prompt: item.getContent()
                                                                                            });
                                                                                            toast.info(`Guideline: ${item.getId().getName()} set as a prompt successfully!`);
                                                                                        }
                                                                                    }}
                                                                                />

                                                                            </div>
                                                                        </div>

                                                                    ))
                                                                )}
                                                            </div>
                                                        )}

                                                        {(guidelineAndTutorial) && (
                                                            <div className="flex flex-col mt-2">
                                                                <br />
                                                                {!tutorials.phases || tutorials.phases.length === 0 ? (
                                                                    <div className="flex flex-row items-center">
                                                                        <div className="font-dm-mono-medium text-base mr-auto">
                                                                            No Tutorials
                                                                        </div>
                                                                    </div>
                                                                ) : (

                                                                    tutorials.phases.map((item, index) => (

                                                                        <div className="flex flex-row justify-between items-center space-x-12" key={index}>

                                                                            <div className="font-dm-mono-mdeium text-base mr-auto">
                                                                                {/* {item.getTutorialName()}: */}
                                                                                {item.getName()}
                                                                            </div>
                                                                            <div className="flex space-x-2 ml-2">
                                                                                <IconButtonOnClick
                                                                                    icon={<FiBook className="basic-svg" />}
                                                                                    tooltip={`Use tutorial: ${item.getName()} as a prompt`}
                                                                                    onClick={async () => {
                                                                                        const prompt = chatGptModalState.prompt;
                                                                                        const usepairjudgements = await usepairjudgement(item.getName());
                                                                                        if (usepairjudgements && usepairjudgements.length > 0) {
                                                                                            const stringData = usepairjudgements.map(item =>
                                                                                                `Sentence 1: ${item.firstUsage}\nSentence 2: ${item.secondUsage}\nTarget word: ${item.lemma}\nJudgement: ${item.judgement}`
                                                                                            ).join('\n\n');

                                                                                            setTutPrompt(stringData);

                                                                                            setChatGptModalState({
                                                                                                ...chatGptModalState,
                                                                                                prompt: prompt + "\n\n" + "Here are the few examples:" + "\n\n" + tutPrompt
                                                                                            })
                                                                                            toast.info("Tutorial set as prompt successfully")
                                                                                        }
                                                                                    }}
                                                                                />
                                                                            </div>
                                                                        </div>

                                                                    ))
                                                                )}
                                                            </div>
                                                        )}


                                                    </div>
                                                )
                                            }


                                        </div>

                                    </div>
                                </div>
                                <div className="flex flex-row divide-x-8">
                                    <button type="button" className="active:transform active:scale-95 block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 "
                                        onClick={() => onCancel()}>Cancel</button>
                                    {!tutorial && (
                                        <button
                                            type="button"
                                            className="active:transform active:scale-95 block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100"
                                            onClick={onConfirm}
                                        >
                                            Confirm
                                        </button>
                                    )}
                                    {tutorial && (
                                        <button
                                            type="button"
                                            className="active:transform active:scale-95 block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100"
                                            onClick={onConfirmTutorial}
                                        >
                                            Start Tutorial
                                        </button>
                                    )}

                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </>

        )
    }

export default ComputationalAnnotationModal;

function calculateNewSelectedUserArray(user: Annotator, selectedUser: Array<Annotator>) {
    const filtered = selectedUser.filter(l => l.getName() !== user.getName());

    if (filtered.length === selectedUser.length) {
        return [...selectedUser, user];
    }
    return filtered;
}

function verifyAndAddCommand(phase: Phase, selectedUsers: Annotator[]): StartComputationalAnnotationCommand | null {
    if (selectedUsers === null || selectedUsers.length === 0) {
        toast.warning("Please select at least one computational annotator.");
        return null;
    }
    return new StartComputationalAnnotationCommand(
        phase.getId().getOwner(),
        phase.getId().getProject(),
        phase.getId().getPhase(),
        selectedUsers.map(u => u.getName())
    );
}

function verifyComputationalAnnotation(chatGptModalState: {
    apiKey: string,
    model: OpenAIModel,
    temperature: number,
    topP: number,
    otherModel: string,
    prompt: string,
    system: string,
    finalMessage: string
}, phase: Phase): ComputationalAnnotatorCommand | null {
    if (!chatGptModalState.apiKey) {
        toast.error("Enter api key.");
        return null;
    }

    if ((chatGptModalState.apiKey as string).includes(" ")) {
        toast.error("Api key should not contain any spaces.");
        return null;
    }

    if (!chatGptModalState.model) {
        toast.error("Please choose model.");
        return null;
    }

    if (!chatGptModalState.prompt) {
        toast.error("Prompt must not be blank.");
        return null;
    }

    return new ComputationalAnnotatorCommand(
        phase?.getId().getOwner(),
        phase?.getId().getProject(),
        phase?.getId().getPhase(),
        chatGptModalState.apiKey,
        chatGptModalState.temperature,
        chatGptModalState.topP,
        chatGptModalState.model.getName() === "others" ? chatGptModalState.otherModel : chatGptModalState.model.getVisiblename(),
        chatGptModalState.prompt,
        chatGptModalState.system,
        chatGptModalState.finalMessage
    );
}