import { FiChevronRight, FiEdit3, FiFeather } from "react-icons/fi";
import { useState } from "react";
import UsageField from "../../annotation/usage/usagefield";
import SentimentInstance from "../../../../lib/model/instance/sentimentinstance/model/SentimentInstance";

interface IInstance {
    instance: SentimentInstance;

    handleSubmitAnnotation: (judgement: string, comment: string) => void;
}

const ChoiceTutorialAnnotation: React.FC<IInstance> = ({ instance, handleSubmitAnnotation }) => {

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

                <div className="m-8 flex flex-row">
                    <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                        {instance?.getLabelSet().concat(instance?.getNonLabel()).map((label) => {

                            return (
                                <div key={label}
                                    className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium"
                                    onClick={() => handleSubmitAnnotation(label, annotation.comment)}
                                    style={{ minWidth: "0", overflow: "hidden" }}>
                                    <div className="w-auto min-w-8 h-4 m-6 text-center text-lg">
                                        {label}
                                    </div>
                                </div>
                            );
                        })}
                    </div>
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

           
        </div>
    );
}

export default ChoiceTutorialAnnotation;