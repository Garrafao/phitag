// Next Components
import Link from "next/link";

// React Icons
import { FiShare2, FiUser, FiUserPlus } from "react-icons/fi";
import useStorage from "../../../lib/hook/useStorage";

// Custom Libraries
import User from "../../../lib/model/user/model/User";
import IconButtonOnClick from "../../generic/button/iconbuttononclick";

import ProlificIcon from "../../../public/image/Prolific.svg"


interface IUserCardProps {
    grid?: boolean;
    user?: User;
    onClick?: (user: User) => void;
}

const UserCard: React.FC<IUserCardProps> = ({ grid, user, onClick }) => {

    const username = useStorage().get("USER");

    const hidden = username == user?.getUsername();

    if (!user) {
        return <div />;
    }

    return (
        <Link href={`/phi/${user.getUsername()}`} passHref>
            <div className="w-full shadow-md cursor-pointer hover:scale-[1.025] hover:transition-all duration-200">
                <div className="h-full grow flex flex-row p-8 xl:px-10 justify-between">
                    <div className="w-full flex flex-col break-words font-dm-mono-regular text-base16-gray-900">

                        <h1 className="font-dm-mono-medium font-bold text-2xl">
                            {user.getDisplayname()}
                        </h1>

                        <div className="my-2 text-sm">
                            <div className="">
                                Languages: {user.getLanguages().length > 0 ? user.getLanguages().map(l => l.getName()).join(", ") : "All supported languages"}
                            </div>
                            {user.isBot() && (
                                <div className="">
                                    Annotation Types: {user.getAnnotationTypes().length > 0 ? user.getAnnotationTypes().map(a => a.getVisiblename()).join(", ") : "All supported annotation types"}
                                </div>
                            )}
                        </div>

                        {/* User Bio */}
                        <p className="my-2 text-sm line-clamp-5">
                            {user.getDescription() || "No description provided."}
                        </p>

                        {/* Icons/Buttons */}

                        <div className="flex flex-row mt-auto justify-start">
                            <IconButtonOnClick icon={<FiUserPlus className="w-full h-full  stroke-base16-gray-500" />} tooltip="Add to Project" onClick={(e: any) => { e.stopPropagation(); onClick ? onClick(user) : {}; }} hide={hidden} />
                        </div>

                    </div>


                 
                    
                        <div className="flex flex-row items-center">
                            <FiShare2 className="my-2" style={{ width: "50%", height: "24%" }} />
                            <div className="ml-1 font-dm-mono-medium"> {/* Adjusted margin to ml-1 */}
                                PhiTag
                            </div>
                            {user.getProlificId() !== null && (
                            <div>
                                <img src="/image/Prolific.svg" alt="Prolific Icon" style={{ width: "70%", height: "70%" }} />
                            </div>
                             )}
                        </div>
                   



                    {/* User Avatar */}
                    <div className="hidden xl:visible xl:w-1/12 xl:flex flex-col justify-center items-center">
                        <FiUser className="w-full h-full aspect-square stroke-base16-gray-500" />

                    </div>
                </div>

            </div>
        </Link>
    );

}

export default UserCard;
