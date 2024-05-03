// Next Components
import Link from "next/link";
import IconButtonWithTooltip from "../button/iconbuttonwithtooltip";
import { FiClipboard, FiSearch, FiToggleLeft, FiToggleRight, FiEdit, FiFilePlus } from "react-icons/fi";

// React Icons

// models
import Project from "../../../lib/model/project/model/Project";
import { useState } from "react";
import IconButtonOnClick from "../button/iconbuttononclick";

interface IProjectCardProps {
    project: Project;
}

// TODO: refactor when project
const ProjectCard: React.FC<IProjectCardProps> = ({ project }) => {


    if (!project) {
        return <div />;
    }


    return (
        <Link href={`/phi/${project.getId().getOwner()}/${project.getId().getName()}`} passHref>
            <div className="w-full shadow-md cursor-pointer hover:scale-[1.025] hover:transition-all duration-200">
                <div className="h-full flex flex-col grow p-8 xl:px-10 break-words font-dm-mono-regular text-base16-gray-900">
                  
                    <h1 className="font-dm-mono-medium font-bold text-2xl">
                        {project.getDisplayname()}
                    </h1>


                    <div className="mt-2 text-sm">
                        <div className="">
                            Owner: {project.getId().getOwner()}
                        </div>
                        <div className="">
                            Language: {project.getLanguage().getVisiblename()}
                        </div>
                        <div className="">
                            Visibility: {project.getVisibility().getVisiblename()}
                        </div>
                    </div>

                    {/* User Bio */}
                    <p className="my-2 text-sm line-clamp-5">
                        Description: {project.getDescription() ? project.getDescription() : "No description"}
                    </p>


                </div>

                

            </div>
        </Link>
    );

}

export default ProjectCard;