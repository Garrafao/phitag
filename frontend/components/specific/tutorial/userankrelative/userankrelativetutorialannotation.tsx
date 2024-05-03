import { useEffect, useState } from "react";
import { FiAlignLeft, FiBookmark, FiFeather } from "react-icons/fi";
import UseRankInstance from "../../../../lib/model/instance/userankinstance/model/UseRankInstance";
import DraggableUsage from "../../annotation/usage/userank/dragabbleusage";
import UseRankRelativeInstance from "../../../../lib/model/instance/userankreltiveinstance/model/UseRankRelativeInstance";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";
import DraggableUsageTutorial from "../userank/dragabbleusagetutorial";
import Phase from "../../../../lib/model/phase/model/Phase";

interface IUseRankAnnotationInstance {
    instance: UseRankRelativeInstance;
    phase: Phase

    handleSubmitAnnotation: (judgement: string, comment: string) => void;
}

const UseRankRelativeTutorialAnnotation: React.FC<IUseRankAnnotationInstance> = ({ instance, phase, handleSubmitAnnotation }) => {

    const [judgement, setJudgement] = useState({
        comment: "",
    });

    const handleSkip = () => {
        setJudgementData("-");
        judgement.comment

    }

    const labelArray = instance ? instance.getLabelSet() : null;
    const noLabel = instance ? instance.getNonLabel() : null;

    const rank = {
        first: labelArray ? labelArray[0] : "First Usage",
        second: labelArray ? labelArray[1] : "Second Usage",
        third: labelArray ? labelArray[2] : "Third Usage",
        fourth: labelArray ? labelArray[3] : "Fourth Usage",
        fifth: labelArray ? labelArray[4] : "Fifth Usage",
        sixth: labelArray ? labelArray[5] : "Sixth Usage",
        seventh: labelArray ? labelArray[6] : "Seventh Usage",
        eight: labelArray ? labelArray[7] : "Eight Usage",
        ninth: labelArray ? labelArray[8] : "Ninth Usage",
        tenth: labelArray ? labelArray[9] : "Tenth Usage",
    }

    const nonLabelSet = {
        nonlabel: noLabel ? noLabel[0] : "-"
    }
    const [judgementData, setJudgementData] = useState("");

    const [usages, setUsages] = useState<any[]>([{
        key:"",
        usage: null as unknown as Usage
    }]); // Update with your usage type

    useEffect(() => {
      if (instance) {
        const updatedUsages = [
            {
                key: rank.first,
                usage: instance?.getSecondusage(),
            },
            {
                key: rank.second,
                usage: instance?.getThirdusage(),
            },
            {
                key: rank.third,
                usage: instance?.getFourthusage(),
            }, {
    
                key: rank.fourth,
                usage: instance?.getFifthusage(),
            }, {
                key: rank.fifth,
                usage: instance?.getSixthusage(),
            }, {
                key: rank.sixth,
                usage: instance?.getSeventhusage(),
            }, {
                key: rank.seventh,
                usage: instance?.getEightusage(),
            },
            {
                key: rank.eight,
                usage: instance?.getNinthusage(),
            },
            {
                key: rank.ninth,
                usage: instance?.getTenthusage(),
            },
        ];
  
        setUsages(updatedUsages);
      }
    }, [instance]);



    const handleUsagesReordered = (rank: string) => {

        setJudgementData(rank);
    };

    // Filter out null usages
    const filteredUsages = usages.filter(item => item.usage && item.usage.getContext() !== undefined);


    return (
        <div className="w-auto flex flex-col 2xl:flex-row justify-between 2xl:px-2">
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
            <div className="w-full bg-gray-600 shadow-md text-white">
                <div className="px-10 m-0 flex flex-row">
                    <div className="my-4">
                        <FiAlignLeft className="basic-svg" />
                    </div>
                    <div className="border-r-2 mx-4" />
                    <div className="my-4 font-dm-mono-light text-lg overflow-auto">
                        {usageContextBuilder(instance?.getFirstusage()).map((sentence, index) => {
                            return (
                                <span
                                    key={index}
                                    className={
                                        sentence.highlight === "bold"
                                            ? "font-dm-mono-medium"
                                            : sentence.highlight === "color"
                                                ? "inline font-dm-sans-bold text-lg text-base16-orange"
                                                : ""
                                    }>
                                    {sentence.sentence}
                                </span>
                            );
                        })}
                    </div>
                </div>
            </div>
            <DraggableUsageTutorial usages={filteredUsages} handleUsagesReordered={handleUsagesReordered} containerSize={600} />
            </div>
            <div className="w-auto flex flex-col my-2 items-center justify-between xl:justify-center">


                <div className="w-full 2xl:w-1/2 flex flex-col self-center items-left font-dm-mono-regular text-lg">
                    <div className="font-bold text-lg">
                        Comment
                    </div>
                    <div className="h-32 flex items-start border-l-2 py-2 px-3 mt-2">
                        <FiFeather className='basic-svg' />
                        <textarea
                            className="w-auto h-full resize-none pl-3 flex flex-auto outline-none border-none"
                            name="description"
                            placeholder={"Comment"}
                            value={judgement.comment}
                            onChange={(e: any) => setJudgement({
                                ...judgement,
                                comment: e.target.value
                            })}
                        />
                    </div>
                </div>
                <div className="flex justify-between xl:justify-center xl:space-x-2 py-5">
                    <div className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium" 
                     onClick={() => handleSubmitAnnotation(judgementData, judgement.comment)}>
                        <div className="w-8 h-8 m-6 text-center text-lg">
                            Save
                        </div>
                    </div>
                    <div className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium" onClick={handleSkip}>
                        <div className="w-8 h-8 m-6 text-center text-lg">
                            {nonLabelSet.nonlabel}
                        </div>
                    </div>
                </div>

            </div>
        </div>
    );

};

export default UseRankRelativeTutorialAnnotation;
/**
 * Constructs a context array with Pairs of the form {sentence: string, highlight: "none" | "bold" | "color"}
 * 
 * @param usage usage to construct the context from
 */
function usageContextBuilder(usage: Usage): { sentence: string, highlight: "none" | "bold" | "color" }[] {

    const context = usage.getContext();

    const contextArray: { sentence: string, highlight: "none" | "bold" | "color" }[] = [];

    // Add the first sentence
    contextArray.push({
        sentence: context.substring(0, usage.getIndexTargetSentenceStart()),
        highlight: "none"
    });

    let lastTargetTokenEnd = 0;

    usage.getIndexTargetSentence().forEach((sentence, index) => {
        lastTargetTokenEnd = sentence.left;
        usage.getIndexTargetToken().forEach((token, index) => {
            if (token.left >= sentence.left && token.right <= sentence.right) {
                // Add the sentence till the target token
                contextArray.push({
                    sentence: context.substring(lastTargetTokenEnd, token.left),
                    highlight: "bold"
                });
                // Add the target token
                contextArray.push({
                    sentence: context.substring(token.left, token.right),
                    highlight: "color"
                });
                lastTargetTokenEnd = token.right;
            }
        });
        // Add the sentence after the target token
        contextArray.push({
            sentence: context.substring(lastTargetTokenEnd, sentence.right),
            highlight: "bold"
        });
    });

    // Add the last sentence
    contextArray.push({
        sentence: context.substring(usage.getIndexTargetSentenceEnd()),
        highlight: "none"
    });

    return contextArray;

}