// services
import { useFetchInstances, useFetchPagedUseRankInstance } from "../../../../lib/service/instance/InstanceResource";

// models
import Phase from "../../../../lib/model/phase/model/Phase";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";
import UseRankInstance, { UseRankInstanceConstructor } from "../../../../lib/model/instance/userankinstance/model/UseRankInstance";

//icon
import { IoIosArrowDown, IoIosArrowUp } from "react-icons/io";
// components
import LoadingComponent from "../../../generic/loadingcomponent";
import AddInstanceToPhaseModal from "../../modal/addinstancetophasemodal";
import { useEffect, useState } from "react";
import PageChange from "../../../generic/table/pagination";
import { data } from "autoprefixer";
import GenerateInstancesForPhaseModal from "../../modal/generateinstancesforphasemodal";
import UsageModal from "../../card/usagecard";
import UsageCard from "../../card/usagecard";

const UseRankInstanceTable: React.FC<{ phase: Phase, modalState: { openData: boolean, callbackData: Function, openGenerate: boolean, callbackGenerate: Function } }> = ({ phase, modalState }) => {

    const [page, setPage] = useState(0);
    const userankinstances = useFetchPagedUseRankInstance(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

    const [expandedInstances, setExpandedInstances] = useState<boolean[]>(
        new Array(userankinstances.data.getContent().length).fill(false)
    );

    const toggleExpansion = (index: number) => {
        setExpandedInstances((prevExpanded) => {
            const newExpanded = [...prevExpanded];
            newExpanded[index] = !newExpanded[index];
            return newExpanded;
        });
    };
    // Reload the data on reload
    useEffect(() => {
        userankinstances.mutate();
    }, [phase]);

    if (!phase || userankinstances.isLoading || userankinstances.isError) {
        return <LoadingComponent />;
    }

    return (
        <div>
            <div className="flex flex-col font-dm-mono-medium ">
                <div className="overflow-auto">
                    <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                        <thead className="font-bold text-lg">
                            <tr>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Instance ID
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Usage
                                </th>

                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Label Set
                                </th>

                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Non Label
                                </th>
                            </tr>

                        </thead>
                        <tbody className=" text-base16-gray-700">
                            {userankinstances.data.getContent().map((instance, i) => {
                                //@ts-ignore, TODO: fix this
                                let userankinstance: UseRankInstance = instance;
                                return (
                                    <><tr>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {userankinstance.getId().getInstanceId()}
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

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {userankinstance.getLabelSet().join(', ')}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {userankinstance.getNonLabel()}
                                        </td>
                                    </tr>
                                        {expandedInstances[i] && (
                                            <tr key={`${i}-expanded`}
                                                className="transition-max-h transition-property: transform duration-0 ease-in-out overflow-hidden max-h-0 ">
                                                <td colSpan={4}>
                                                    <UsageCard isOpen={true} userankinstance={userankinstance} />
                                                </td>
                                            </tr>
                                        )}
                                    </>

                                );
                            })}
                        </tbody>
                    </table>

                </div>
            </div>


            <PageChange page={page} maxPage={userankinstances.data.getTotalPages()} pageChangeCallback={(p: number) => { setPage(p) }} />

            <AddInstanceToPhaseModal isOpen={modalState.openData} closeModalCallback={modalState.callbackData} phase={phase} mutateCallback={userankinstances.mutate} />
            <GenerateInstancesForPhaseModal isOpen={modalState.openGenerate} closeModalCallback={modalState.callbackGenerate} phase={phase}
                mutateCallback={userankinstances.mutate} additional={false} additionalFileName="" />
            <div className="border-base16-gray-200 divide-y divide-base16-gray-200">
            </div>
        </div>
    );

}

export default UseRankInstanceTable;

function getFormatedUsage(usage: Usage) {
    return (
        <div className="">
            {usageContextBuilder(usage).map((sentence, index) => {
                return (
                    <span
                        key={index}
                        className={sentence.highlight === "bold" ? "font-dm-mono-medium" : sentence.highlight === "color" ? "inline font-dm-sans-bold text-lg text-base16-green" : ""}>
                        {sentence.sentence}
                    </span>
                );
            })}
        </div>
    )
}

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