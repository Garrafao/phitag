import { FiChevronRight, FiEdit3, FiFeather } from "react-icons/fi";
import LexSubInstance from "../../../../lib/model/instance/lexsubinstance/model/LexSubInstance";
import { useState } from "react";
import UsageField from "../../annotation/usage/usagefield";

interface IInstance {
    instance: LexSubInstance;

    handleSubmitAnnotation: (judgement: string, comment: string) => void;
}

const LexSubTutorialAnnotation: React.FC<IInstance> = ({ instance, handleSubmitAnnotation }) => {

    const [annotation, setAnnotation] = useState({
        judgement: "",
        comment: "",
    });


    return (
        <div className="w-full flex flex-col justify-between">
            <div className="w-full flex flex-col justify-center space-y-4 ">
                <UsageField usage={instance.getUsage()} />
            </div>

            <div className="w-full flex flex-col my-8 self-center items-left font-dm-mono-regular text-lg">
                <div className="font-bold text-lg">
                    Judgement
                </div>
                <div className="h-32 flex items-start border-l-2 py-2 px-3 mt-2">
                    <FiEdit3 className='basic-svg' />
                    <textarea
                        className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                        name="description"
                        placeholder={"Judgement"}
                        value={annotation.judgement}
                        onChange={(e: any) => setAnnotation({
                            ...annotation,
                            judgement: e.target.value
                        })} />
                </div>
            </div>

            <div className="w-full flex flex-col self-center items-left font-dm-mono-regular text-lg">
                <div className="font-bold text-lg">
                    Comment
                </div>
                <div className="h-32 flex items-start border-l-2 py-2 px-3 mt-2">
                    <FiFeather className='basic-svg' />
                    <textarea
                        className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                        name="description"
                        placeholder={"Comment"}
                        value={annotation.comment}
                        onChange={(e: any) => setAnnotation({
                            ...annotation,
                            comment: e.target.value
                        })} />
                </div>
            </div>

            <div className="flex self-end">
                <button className="text-base16-gray-100 bg-base16-gray-900 hover:text-base16-green transition-all duration-500"
                    onClick={() => handleSubmitAnnotation(annotation.judgement, annotation.comment)}>
                    <div className="flex my-2 mx-8 font-dm-mono-medium font-bold text-xl items-center">
                        Submit <FiChevronRight className='basic-svg stroke-[3]' />
                    </div>
                </button>
            </div>
        </div>
    );
}

export default LexSubTutorialAnnotation;