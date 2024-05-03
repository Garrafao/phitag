// Next
import Link from "next/link";
import { useState } from "react";
// Next Components

// hooks
import useStorage from "../../../lib/hook/useStorage";

// React Icons
import { FiAperture, FiCheckCircle, FiCode, FiCpu, FiDatabase, FiDelete, FiDroplet, FiEdit3, FiGitPullRequest, FiLayers, FiPackage, FiPaperclip, FiPenTool, FiPieChart, FiPlay, FiPlayCircle, FiPocket, FiStopCircle, FiTrash, FiTrash2 } from "react-icons/fi";


//Resource

import { deletePhase, deleteRequirementsFromPhase, setCode } from "../../../lib/service/phase/PhaseResource";
// Models
import Phase from "../../../lib/model/phase/model/Phase";
import ENTITLEMENTS from "../../../lib/model/entitlement/Entitlements";

// Custom Libraries
import IconButtonOnClick from "../../generic/button/iconbuttononclick";
import IconButtonWithTooltip from "../../generic/button/iconbuttonwithtooltip";
import { closePhase } from "../../../lib/service/phase/PhaseResource";
import PhaseStatusEnum from "../../../lib/model/phase/data/PhaseStatusEnum";
import { toast } from "react-toastify";
import DeletePhaseModal from "../../specific/modal/deletephasemodal";

interface IPhaseCard {
    phase: Phase;

    onClickRequirements: () => void;
    onClickComputation: () => void;

    refreshCallback: () => void;

    entitlement: string;
}

const PhaseCard: React.FC<IPhaseCard> = ({ phase, onClickRequirements, onClickComputation, refreshCallback, entitlement = "NONE" }) => {

    const storage = useStorage();
    // modals
    const [showDeletePhaseModal, setShowDeletePhaseModal] = useState(false);



    const urlprefix = `/phi/${phase.getId().getOwner()}/${phase.getId().getProject()}/${phase.getId().getPhase()}`

    const onClickClosePhase = () => {
        closePhase(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), storage.get)
            .then(() => {
                refreshCallback();
            }).catch((error) => {
                console.log(error);
            });
    }
    const onClickDeleteRequirements = (requirement: string) => {
        deleteRequirementsFromPhase(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), requirement, storage.get)
            .then(() => {
                refreshCallback();
            }).catch((error) => {
                console.log(error);
            });
    }
    const onClickDeletePhase = () => {
        deletePhase(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(), storage.get)
            .then(() => {
                toast.info("Phase deleted sucessfully")
                refreshCallback();
                setShowDeletePhaseModal(false)
            }).catch((error) => {
                console.log(error);
            });
    }

    const [phasecode, setPhaseCode] = useState("");




    const onClickSetCode = async () => {
        try {
            const owner: string = phase.getId().getOwner();
            const project: string = phase.getId().getProject();
            const phasename: string = phase.getId().getPhase();

            const pastedData: string = await navigator.clipboard.readText();
            setPhaseCode(pastedData);
            if (pastedData) {
                setCode(owner, project, phasename, pastedData, storage.get).then((res) => {
                    toast.info(`code ${pastedData} added successfully.`);
                });
            } else {
                toast.info("Please copy code first.")
            }
        } catch (error) {
            toast.error("Error uploading code.")
        }
    };

    if (!phase) {
        return <div />;
    }

    return (
        <div>
            <Link href={`${urlprefix}/`}>
                <div className="flex flex-col h-auto w-82 shadow-md cursor-pointer hover:scale-[1.025] hover:transition-all duration-200 font-dm-mono-medium py-16 px-8">
                    <div className="flex flex-row justify-between items-center my-1 ">

                      <span className="font-bold text-2xl">{phase.isTutorial() ? "Tutorial" : "Phase"}: {phase.getDisplayname()}</span> 
                        <div className="ml-8" onClick={(e: any) => e.stopPropagation()}>
                            <IconButtonOnClick icon={<FiTrash2 className="basic-svg" />} onClick={() => setShowDeletePhaseModal(true)}
                                tooltip={"Delete" + " " + phase.getDisplayname()}
                                hide={phase.getStatus() === PhaseStatusEnum.CLOSED  || entitlement !== ENTITLEMENTS.ADMIN} />
                        </div>
                    </div>
                    <div className="flex flex-row justify-between items-center my-1">
                        <div className="text-sm text-left">
                            Annotation Type:
                        </div>
                        <div className="ml-8 font-dm-mono-regular text-base16-gray-600 text-sm text-right">
                            {phase.getAnnotationType().getVisiblename()}
                        </div>
                    </div>

                    <div className="flex flex-row justify-between items-center mb-1">
                        <div className="text-sm text-left">
                            Sampling Strategy:
                        </div>
                        <div className="ml-8 font-dm-mono-regular text-base16-gray-600 text-sm text-right">
                            {phase.getSampling().getVisiblename()}
                        </div>
                    </div>

                    <div className="flex flex-row justify-between items-center mb-1">
                        <div className="text-sm text-left">
                            Status:
                        </div>
                        <div className="ml-8 font-dm-mono-regular text-base16-gray-600 text-sm text-right">
                            {phase.getStatus()}
                        </div>
                    </div>


                    <div className="mx-16 my-4 border-b-2" />

                    <div className="flex flex-col mb-auto">
                        <div className="text-base">
                            Description:
                        </div>
                        <div className="mt-2 font-dm-mono-regular text-base16-gray-600 text-sm break-words ">
                            {phase.getDescription().length === 0 ? "No description provided." : phase.getDescription()}
                        </div>
                    </div>

                    {phase.getTutorialrequirements().length == 0 ?
                        (<div />)
                        :
                        (
                            <div>
                                <div className="mx-16 my-4 border-b-2" />

                                <div className="flex flex-col justify-between items-center mt-8">
                                    <div className="flex flex-row">
                                        Tutorials:
                                    </div>

                                    {phase.getTutorialrequirements().length == 0 ? (
                                        <div />
                                    ) : phase.getTutorialrequirements().map((tutorial, index) => {
                                        return (
                                            <div className="w-full flex flex-row justify-between items-center" key={index}>
                                                <div className="font-dm-mono-regular text-base mr-auto">
                                                    {tutorial.left}:
                                                </div>
                                                <div className="flex flex-row  items-center mb-1 font-dm-mono-regular text-base16-gray-600 text-sm text-right">
                                                    <div className="ml-1 font-dm-mono-light text-base16-gray-600 text-sm">
                                                        {tutorial.right ? "Completed" : "Not Passed"}
                                                    </div>
                                                </div>

                                                <div className="ml-2" onClick={(e: any) => e.stopPropagation()}>
                                                    <IconButtonOnClick icon={<FiTrash2 className="basic-svg" />} onClick={() => onClickDeleteRequirements(tutorial.left)}
                                                        tooltip={"Delete" + " " + tutorial.left + " " + "requirement"}
                                                        hide={phase.getStatus() === PhaseStatusEnum.CLOSED || phase.isTutorial() || entitlement !== ENTITLEMENTS.ADMIN} />
                                                </div>
                                            </div>
                                        );
                                    })}
                                </div>
                            </div>
                        )
                    }


                    <div className="mx-16 my-4 border-b-2" />

                    <div className="flex flex-row justify-end align-bottom space-x-4" onClick={(e: any) => e.stopPropagation()}>
                        <IconButtonOnClick icon={<FiCode className="basic-svg" />} onClick={onClickSetCode} tooltip="Paste Submission Code Here" hide={phase.getStatus() === PhaseStatusEnum.CLOSED || phase.isTutorial() || entitlement !== ENTITLEMENTS.ADMIN} />
                        <IconButtonOnClick icon={<FiPlayCircle className="basic-svg" />} onClick={onClickClosePhase} tooltip="Open Phase" hide={phase.getStatus() === PhaseStatusEnum.OPEN || phase.isTutorial() || entitlement !== ENTITLEMENTS.ADMIN} />
                        <IconButtonOnClick icon={<FiStopCircle className="basic-svg" />} onClick={onClickClosePhase} tooltip="Close Phase" hide={phase.getStatus() === PhaseStatusEnum.CLOSED || phase.isTutorial() || entitlement !== ENTITLEMENTS.ADMIN} />
                        <IconButtonOnClick icon={<FiLayers className="basic-svg" />} onClick={onClickRequirements} tooltip="Add Requirements" hide={phase.getStatus() === PhaseStatusEnum.CLOSED || entitlement !== ENTITLEMENTS.ADMIN || phase.isTutorial()} />
                        <IconButtonWithTooltip icon={<FiEdit3 className="basic-svg" />} reference={`${urlprefix}/${phase.isTutorial() ? "tutorial" : "annotate"}`} tooltip="Annotate" hide={phase.getStatus() === PhaseStatusEnum.CLOSED || entitlement !== ENTITLEMENTS.ADMIN && entitlement !== ENTITLEMENTS.USER} />
                        <IconButtonOnClick icon={<FiPlay className="basic-svg" />} onClick={onClickComputation} tooltip="Start Computational Annotation" hide={phase.getStatus() === PhaseStatusEnum.CLOSED || phase.isTutorial() || entitlement !== ENTITLEMENTS.ADMIN} />
                        {/* <IconButtonWithTooltip icon={<FiGitPullRequest className="basic-svg" />} reference={`${urlprefix}/history`} tooltip="View Personal History" hide={entitlement !== ENTITLEMENTS.ADMIN && entitlement !== ENTITLEMENTS.USER || phase.isTutorial()} />
                    <IconButtonWithTooltip icon={<FiTrendingUp className="basic-svg" />} reference={`${urlprefix}/statistic`} tooltip="View Statistic of Phase" hide={phase.isTutorial()} />
                    <IconButtonWithTooltip icon={<FiDatabase className="basic-svg" />} reference={`${urlprefix}/instance`} tooltip="View Data" hide={phase.isTutorial() && entitlement !== ENTITLEMENTS.ADMIN} /> */}
                    </div>


                </div>
            </Link>
            <DeletePhaseModal isOpen={showDeletePhaseModal} closeModalCallBack={() => {
                setShowDeletePhaseModal(false);
            }} phase={phase} deletePhase={onClickDeletePhase} />
        </div>

    );

}

export default PhaseCard;