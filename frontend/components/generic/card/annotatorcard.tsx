// Next Components
import Link from "next/link";

// React Icons
import { FiUser } from "react-icons/fi";

// models
import Annotator from "../../../lib/model/annotator/model/Annotator";
import ENTITLEMENTS from "../../../lib/model/entitlement/Entitlements";


interface IAnnotatorCardProps {
    annotator: Annotator;
    onClickEdit: Function;

    entitlement: string;
}

const AnnotatorCard: React.FC<IAnnotatorCardProps> = ({ annotator, onClickEdit, entitlement }) => {

    const hidden = entitlement !== ENTITLEMENTS.ADMIN ? " hidden" : "";

    return (
        <Link href={`/phi/${annotator.getId().getUser()}`} passHref>
            <div className="w-full shadow-md cursor-pointer hover:scale-[1.025] hover:transition-all duration-200">
                <div className="h-full grow flex flex-row p-8 xl:px-10 justify-between">
                    <div className="w-full flex flex-col break-words font-dm-mono-regular text-base16-gray-900">


                        {/* Username */}
                        <h1 className="font-dm-mono-medium font-bold text-2xl">
                            {annotator.getUser().getDisplayname()}
                        </h1>

                        <div className="my-2 text-sm">
                            <div className="">
                                Languages: {annotator.getUser().getLanguages().length > 0 ? annotator.getUser().getLanguages().map(l => l.getName()).join(", ") : "All supported languages"}
                            </div>
                            {annotator.getUser().isBot() && (
                                <div className="">
                                    Annotation Types: {annotator.getUser().getAnnotationTypes().length > 0 ? annotator.getUser().getAnnotationTypes().map(a => a.getVisiblename()).join(", ") : "All supported annotation types"}
                                </div>
                            )}
                            <div className="">
                                Entitlements: {annotator.getEntitlement().getVisiblename()}
                            </div>
                            <div>
                                Is Bot: {annotator.getUser().isBot() ? "Yes" : "No"}
                            </div>
                        </div>

                        {/* User Bio */}
                        <p className="my-2 text-sm line-clamp-5">
                            {annotator.getUser().getDescription()}
                        </p>

                        {/* Icons/Buttons */}

                        <div className="flex flex-row mt-auto space-x-8 xl:w-1/2 xl:self-start" onClick={(e: any) => e.stopPropagation()}>
                            <button type="button" className={"block w-full mt-8 py-2 font-dm-mono-medium bg-base16-gray-900 text-base16-gray-100 " + hidden} onClick={(e: any) => onClickEdit(e)}>Edit</button>
                        </div>

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

export default AnnotatorCard;
