//React Modules
import React, { useState } from "react";

//Next Modules

// Icons
import { FiBook, FiFilePlus } from "react-icons/fi";

// Services
import { useFetchGuidelines } from "../../../lib/service/guideline/GuidelineResource";

// Models
import Project from "../../../lib/model/project/model/Project";

// components
import IconButtonOnClick from "../../generic/button/iconbuttononclick";
import IconButtonWithTooltip from "../../generic/button/iconbuttonwithtooltip";
import HelpButton from "../../generic/button/helpbutton";
import AddGuidelineToProjectModal from "../modal/addguidelinetoprojectmodal";


interface IProps {
    project: Project;
    showAddGuideline: boolean | undefined;
}

const ProjectGuidelinesCard: React.FC<IProps> = ({ project, showAddGuideline }) => {

    // Data & Hooks
    const guidelines = useFetchGuidelines(project?.getId().getOwner(), project?.getId().getName(), !!project);

    // modals
    const [showAddGuidelineModal, setShowAddGuidelineModal] = useState(false);

    if (!project || guidelines.isLoading) {
        return <div />
    }

    return (
        <div className="flex flex-col grow shadow-md p-8 font-dm-mono-medium">
            <div className="flex flex-row justify-between items-center">
                <div className="font-bold text-base">
                    Guidelines:
                </div>
                <div className="flex flex-row space-x-2">
                    <IconButtonOnClick icon={<FiFilePlus className="basic-svg" />} onClick={() => setShowAddGuidelineModal(true)} tooltip="Add Guideline" hide={!showAddGuideline} />
                    {/* TOOD: placeholder */}
                    <HelpButton
                        title="Help: Guidelines"
                        tooltip="Help: Guidelines"
                        text="Guidelines can be added to a project, to help you and your team to follow a certain set of rules."
                        reference=""
                        linkage={false}
                    />
                </div>
            </div>
            <div className="flex flex-col mt-2">
                {!guidelines.guidelines || guidelines.guidelines.length == 0 ? (
                    <div className="flex flex-row items-center">
                        <div className="font-dm-mono-medium text-base mr-auto">
                            No Guidelines
                        </div>
                    </div>
                )
                    :
                    guidelines.guidelines.map((item, index) => {
                        return (
                            <div className="flex flex-row justify-between items-center space-x-12" key={index}>
                                <div className="font-dm-mono-light text-base mr-auto">
                                    {/* {item.getTutorialName()}: */}
                                    {item.getId().getName()}
                                </div>
                                <div className="flex space-x-2 ml-2">
                                    <IconButtonWithTooltip
                                        icon={<FiBook className="basic-svg" />}
                                        reference={`/phi/${item.getId().getOwner()}/${item.getId().getProject()}/guideline/${item.getId().getName()}`}
                                        tooltip="Read this Guideline"
                                    />
                                </div>
                            </div>
                        );
                    }
                    )}
            </div>

            <AddGuidelineToProjectModal isOpen={showAddGuidelineModal} closeModalCallback={() => {
                setShowAddGuidelineModal(false);
            }} project={project} mutateCallback={guidelines.mutate} />
        </div>
    );
};

export default ProjectGuidelinesCard;
