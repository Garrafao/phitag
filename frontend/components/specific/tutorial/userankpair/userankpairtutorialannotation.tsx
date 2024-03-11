import { useEffect, useState } from "react";
import { FiBookmark, FiFeather } from "react-icons/fi";
import UseRankInstance from "../../../../lib/model/instance/userankinstance/model/UseRankInstance";
import DraggableUsage from "../../annotation/usage/userank/dragabbleusage";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";
import UseRankPairInstance from "../../../../lib/model/instance/userankpairinstance/model/UseRankPairInstance";
import DraggableUsagePairTutorial from "./dragabbleusagepairtutorial";
import Phase from "../../../../lib/model/phase/model/Phase";

interface IUseRankPairAnnotationInstance {
    instance: UseRankPairInstance;
    phase: Phase
    handleSubmitAnnotation: (judgement: string, comment: string) => void;
}

const UseRankPairTutorialAnnotation: React.FC<IUseRankPairAnnotationInstance> = ({ instance, phase, handleSubmitAnnotation }) => {

    const [judgement, setJudgement] = useState({
        comment: "",
    });

    const handleSkip = () => {
        setJudgementData("-");
        // You might also want to reset the comment to an empty string
        setJudgement(prevState => ({
            ...prevState,
            comment: "",
        }));
    };
    

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
        usage1: null as unknown as Usage,
        usage2: null as unknown as Usage

    }]); 

    useEffect(() => {
      if (instance) {
        const updatedUsages = [
            {
                key: rank.first,
                usage1: instance?.getFirstusage(),
                usage2: instance?.getSecondusage(),
              },
              {
                key: rank.second,
                usage1: instance?.getThirdusage(),
                usage2: instance?.getFourthusage(),
              },
              {
                key: rank.third,
                usage1: instance?.getFifthusage(),
                usage2: instance?.getSixthusage(),
              },
              {
                key: rank.fourth,
                usage1: instance?.getSeventhusage(),
                usage2: instance?.getEightusage(),
              },
              {
                key: rank.fifth,
                usage1: instance?.getNinthusage(),
                usage2: instance?.getTenthusage(),
              },
        ];
  
        setUsages(updatedUsages);
      }
    }, [instance]);




    const handleUsagesReordered = (rank: string) => {

        setJudgementData(rank);
    };
    const filteredUsages = usages.filter(item => item.usage1 && item.usage2.getContext() !== undefined);


    return (
        <div className="w-full flex flex-col 2xl:flex-row justify-between 2xl:px-2">
            <div className="w-full flex flex-col justify-center space-y-2">
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
                <DraggableUsagePairTutorial usages={filteredUsages} handleUsagesReordered={handleUsagesReordered} containerSize={600} />
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

export default UseRankPairTutorialAnnotation;