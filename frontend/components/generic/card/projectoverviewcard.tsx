//React Modules
import React, { FC } from "react";

//Next Modules
import Link from 'next/link'
import { useRouter } from "next/router";

// Custom Views
import Project from "../../../lib/model/project/model/Project";

interface IProps {
    project?: Project;
}

const ProjectOverviewCard: React.FC<IProps> = ({ project }) => {

    if (!project) {
        return <div />;
    }

    return (
        <div className="flex flex-col grow shadow-md p-8">
            <ul>
                <li className="flex flex-row justify-between items-center">
                    <div className="font-dm-mono-medium text-base">
                        Language:
                    </div>
                    <div className="ml-8 font-dm-mono-regular text-base16-gray-600 text-sm">
                        {project?.getLanguage().getVisiblename()}
                    </div>
                </li>
                <li className="flex flex-row justify-between items-center">
                    <div className="font-dm-mono-medium text-base">
                        Visibility:
                    </div>
                    <div className="ml-8 font-dm-mono-regular text-base16-gray-600 text-sm">
                        {project?.getVisibility().getVisiblename()}

                    </div>
                </li>
                <li className="flex flex-row justify-between items-center">
                    <div className="font-dm-mono-medium text-base">
                        Is Active:
                    </div>
                    <div className="ml-8 font-dm-mono-regular text-base16-gray-600 text-sm">
                        {project?.isActive() ? "Active" : "Archived"}
                    </div>
                </li>
            </ul>

            <div className="mx-16 my-4 border-b-2" />

            <div className="flex flex-col mb-auto">
                <div className="font-dm-mono-medium">
                    Description:
                </div>
                <div className="mt-2 font-dm-mono-regular text-base16-gray-600 text-sm break-words ">
                    {project?.getDescription() === "" ? "No description provided." : project?.getDescription()}
                </div>
            </div>
        </div>
    );
};

export default ProjectOverviewCard;
