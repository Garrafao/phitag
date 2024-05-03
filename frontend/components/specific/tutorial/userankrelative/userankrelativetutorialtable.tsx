
import AddUseRankRelativeJudgementCommand from "../../../../lib/model/judgement/userankrelativejudgement/command/AddUseRankRelativeJudgementCommand";
import UseRankRelativeInstance from "../../../../lib/model/instance/userankreltiveinstance/model/UseRankRelativeInstance";
import UsageCardRelative from "../../card/usagecardrelative";
import { useState } from "react";
import { IoIosArrowDown, IoIosArrowUp } from "react-icons/io";


interface IResult {
    data: AddUseRankRelativeJudgementCommand[];
    instances: UseRankRelativeInstance[];
}

const UseRankRelativeTutorialTable: React.FC<IResult> = ({ data, instances }) => {
    
    const [expandedInstances, setExpandedInstances] = useState<boolean[]>(
        new Array(instances.length).fill(false)
    );

    const toggleExpansion = (index: number) => {
        setExpandedInstances((prevExpanded: any) => {
            const newExpanded = [...prevExpanded];
            newExpanded[index] = !newExpanded[index];
            return newExpanded;
        });
    };
return (
        <div className="flex flex-col font-dm-mono-medium">
            <div className="overflow-auto">
                <table className="min-w-full divide-y divide-base16-gray-200 ">
                    <thead className="font-bold text-lg">
                        <tr>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider">
                                Instance Id
                            </th>
                           
        

                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Usage
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider">
                                Labels
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider">
                                Judgement
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider">
                                Comment
                            </th>
                        </tr>
                    </thead>
                    <tbody className=" text-base16-gray-700">
                        {data.map((judgement, i) => {
                            return (
                                <tr key={i}>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {instances[i].getId().getInstanceId()}
                                    </td>

                                    
                                    
                                    <td className="px-6 py-4 whitespace-nowrap">
                                            <div className="flex items-center">
                                                {expandedInstances[i] ? (
                                                    <div>
                                                        Hide
                                                        <IoIosArrowUp
                                                            className="ml-2 cursor-pointer"
                                                            onClick={() => toggleExpansion(i)}
                                                        />
                                                    </div>
                                                ) : (
                                                    <div>
                                                        Show
                                                        <IoIosArrowDown
                                                            className="ml-2 cursor-pointer"
                                                            onClick={() => toggleExpansion(i)}
                                                        />
                                                    </div>
                                                )}
                                            </div>
                                        </td>


                                    {/* <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                        <span key={i} className="tooltip group w-fit">
                                            {getFormatedShortUsage(instances[i].getFirstusage())}
                                            <div className="tooltip-container group-hover:scale-100">
                                                <div className="whitespace-nowrap mx-4 my-2">
                                                    Data ID: {instances[i].getFirstusage().getId().getDataid()}
                                                </div>
                                            </div>
                                        </span>
                                    </td>

                                    <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                        <span key={i} className="tooltip group w-fit">
                                            {getFormatedShortUsage(instances[i].getSecondusage())}
                                            <div className="tooltip-container group-hover:scale-100">
                                                <div className="whitespace-nowrap mx-4 my-2">
                                                    Data ID: {instances[i].getSecondusage().getId().getDataid()}
                                                </div>
                                            </div>
                                        </span>
                                    </td>
                         */}


                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {instances[i].getLabelSet().join(", ")}, {instances[i].getNonLabel()}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {judgement.label}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {judgement.comment}
                                    </td>
                                    {expandedInstances[i] && (
                                        <tr key={`${i}-expanded`}
                                            className="transition-max-h transition-property: transform duration-0  max-h-0 ">
                                            <td colSpan={6}>
                                                <UsageCardRelative isOpen={true} userankrelativeinstance={instances[i]} />
                                            </td>
                                        </tr>
                                    )}
                                </tr>
                            )
                        })}
                    </tbody>
                </table>
            </div>
        </div>
    );

}

export default UseRankRelativeTutorialTable;


