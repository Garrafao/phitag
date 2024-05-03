// react
import { useState } from "react";
import { FiFeather } from "react-icons/fi";

// model
import WSSIMInstance from "../../../../lib/model/instance/wssiminstance/model/WSSIMInstance";

// components
import UsageField from "../../annotation/usage/usagefield";
import WSSIMTagField from "../../annotation/wssimtag/wssimtagfield";
import WSSIMTagLemmasField from "../../annotation/wssimtag/wssimtaglemmasfield";

interface IWSSIMAnnotationInstance {
    instance: WSSIMInstance;

    handleSubmitAnnotation: (judgement: string, comment: string) => void;
}

const WSSIMTutorialAnnotation: React.FC<IWSSIMAnnotationInstance> = ({ instance, handleSubmitAnnotation }) => {

    const [judgement, setJudgement] = useState({
        comment: "",
    });

    return (
        <div className="w-full flex flex-col justify-between ">
            <div className="w-full flex flex-col justify-center space-y-2 ">
                <UsageField key={0} usage={instance.getUsage()} />
                <WSSIMTagField key={1} tag={instance.getTag()} />
            </div>

            <div className="w-full flex flex-row my-8 items-center justify-between xl:justify-center xl:space-x-6">
                {instance.getLabelSet().concat(instance.getNonLabel()).map((label) => {
                    return (
                        <div key={label}
                            className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium"
                            onClick={() => handleSubmitAnnotation(label, judgement.comment)}>
                            <div className="w-8 h-8 m-6 text-center text-lg">
                                {label}
                            </div>
                        </div>
                    );
                })}
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
                        value={judgement.comment}
                        onChange={(e: any) => setJudgement({
                            ...judgement,
                            comment: e.target.value
                        })} />
                </div>
            </div>

            <div className="my-8" />

            <h1 className="font-dm-mono-medium text-3xl font-black">
                Other Tags with lemma&nbsp;
                <span className="">
                    {instance.getTag().getLemma()}:
                </span>
            </h1>

            <WSSIMTagLemmasField tag={instance.getTag()} />
            
        </div>
    );

}

export default WSSIMTutorialAnnotation;