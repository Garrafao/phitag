import { useState } from "react";
import { FiAlignLeft, FiBookmark, FiFeather } from "react-icons/fi";
import UsePairInstance from "../../../../lib/model/instance/usepairinstance/model/UsePairInstance";
import UsageField from "../../annotation/usage/usagefield";
import Phase from "../../../../lib/model/phase/model/Phase";

interface IUsePairAnnotationInstance {
    instance: UsePairInstance;
    phase: Phase

    handleSubmitAnnotation: (judgement: string, comment: string) => void;
}

const UsePairTutorialAnnotation: React.FC<IUsePairAnnotationInstance> = ({ instance, handleSubmitAnnotation, phase }) => {

    const [judgement, setJudgement] = useState({
        comment: "",
    });


    return (
        <div className="w-full flex flex-col 2xl:flex-row justify-between 2xl:px-32">

            <div className="w-full flex flex-col justify-center space-y-2 ">
            {(phase.getTaskHead() ?? "") !== "" && (
                <div className="w-half shadow-md ">
                    <div className="m-8 flex flex-row">
                        <div className="my-4">
                            <FiBookmark className="basic-svg" />
                        </div>
                        <div className="border-r-2 mx-4" />
                        <div className="my-4 font-dm-mono-light text-lg overflow-auto">
                            {phase.getTaskHead()}
                        </div>

                    </div>
                </div>
            )}

                <UsageField key={0} usage={instance.getFirstusage()} />
                <UsageField key={1} usage={instance.getSecondusage()} />
            </div>


            <div className="w-full 2xl:w-fit flex flex-row 2xl:flex-col 2xl:mx-16 my-8 items-center justify-between 2xl:justify-center 2xl:space-y-2">
                {instance.getLabelSet().concat(instance.getNonLabel()).map((label) => {
                    return (
                        <div key={label}
                            className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium"
                            onClick={() => handleSubmitAnnotation(label, judgement.comment)}
                            style={{ minWidth: "0", overflow: "hidden" }}>
                            <div className="w-auto min-w-8 h-10 m-4 text-center text-lg">
                                {label}
                            </div>
                        </div>
                    );
                })}
            </div>

            <div className="w-full 2xl:w-1/2 flex flex-col self-center items-left font-dm-mono-regular text-lg">
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

        </div>
    );

};

export default UsePairTutorialAnnotation;