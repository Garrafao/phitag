// Icons
import { FiCheck, FiCopy, FiPlus, FiRss } from "react-icons/fi";

// services
import { useFetchProject } from "../../../lib/service/project/ProjectResource";

// model
import PersonalJoblisting from "../../../lib/model/joblisting/model/PersonalJoblisting";
import IconButtonOnClick from "../button/iconbuttononclick";
import { useState } from "react";
import { Router } from "next/router";



interface IJoblistingCardProps {
    joblisting: PersonalJoblisting;
    onClick: Function;
}

const PersonalJoblistingCard: React.FC<IJoblistingCardProps> = ({ joblisting, onClick }) => {


    const project = useFetchProject(joblisting?.getId().getOwner(), joblisting?.getId().getProject(), !!joblisting);
    
    const [copy, setCopy] = useState(false);

    const copyLink = () => {
        const baseURL = "https://phitag.ims.uni-stuttgart.de/prolific/phi";
        const joblistingName = joblisting?.getId().getName();
        const ownername = joblisting?.getId().getOwner();
        const projectname = joblisting?.getId().getProject();
        const phasename =joblisting?.getPhase();
        const prolificLink = `${baseURL}/${joblistingName}/${ownername}/${projectname}/${phasename}/user`;
        navigator.clipboard.writeText(prolificLink).then(() => {
            setCopy(true);
            // Clear the copied link after 10 seconds
            setTimeout(() => {
                setCopy(false);
            }, 10000); // 10 seconds
        }).catch((error) => {
            console.error('Failed to copy link to clipboard: ', error);
        });
    };

    if (!joblisting || project.isLoading || !project.project) {
        return <div />;
    }

    return (
        <div className="w-full shadow-md cursor-pointer hover:scale-[1.025] hover:transition-all duration-200">
            {/* TODO: Smaller screens, shrink! */}
            <div className="h-full flex flex-col grow p-8 xl:px-10 break-words font-dm-mono-regular text-base16-gray-900">
                {/* Project Name */}
                <div className="flex justify-between">
                    <h1 className="font-dm-mono-medium font-bold text-2xl">
                        {joblisting.getDisplayname()}
                    </h1>
                    <div className="flex flex-row items-center my-4 ml-4">
                        {!copy ?
                            <IconButtonOnClick
                                icon={<FiCopy className="basic-svg" />}
                                tooltip={"Copy project link"}
                                onClick={copyLink
                                } />
                            :
                            <IconButtonOnClick
                                icon={<FiCheck className="basic-svg" />}
                                tooltip={"Link Copied"}
                                onClick={
                                    copyLink
                                } />
                        }
                    </div>
                </div>

                <div className="my-2 text-sm">
                    <div>
                        Project: {project.project.getDisplayname()}
                    </div>
                    <div className="">
                        Language: {project.project.getLanguage().getVisiblename()}
                    </div>
                </div>


                <p className="my-2 text-sm">
                    Is Open to join for all: {joblisting.isOpen() ? "Yes" : "No"}
                </p>
                <p className="my-2 text-sm">
                    Phase: {joblisting.getPhase() ? joblisting.getPhase() : "No Phase"}
                </p>
                {/* User Bio */}
                <p className="my-2 text-sm">
                    Description: {joblisting.getDescription() ? joblisting.getDescription() : "No description"}
                </p>
               
                {joblisting.isOpen() ? <div /> :
                    <button type="button" className="block w-full mt-auto py-2 font-dm-mono-medium bg-base16-gray-900 text-base16-gray-100 " onClick={() => onClick()}>
                        Waitinglist
                    </button>
                }

            </div>
        </div>
    )
}

export default PersonalJoblistingCard;