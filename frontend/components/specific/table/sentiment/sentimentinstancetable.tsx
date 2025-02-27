import { useEffect, useState } from "react";
import { useFetchPagedLexSubInstance, useFetchPagedSentimentInstance } from "../../../../lib/service/instance/InstanceResource";
import Phase from "../../../../lib/model/phase/model/Phase";
import LoadingComponent from "../../../generic/loadingcomponent";
import AddInstanceToPhaseModal from "../../modal/addinstancetophasemodal";
import PageChange from "../../../generic/table/pagination";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";
import GenerateInstancesForPhaseModal from "../../modal/generateinstancesforphasemodal";
import ChoicesCard from "../../card/choicescard";

const SentimentInstanceTable: React.FC<{ phase: Phase, modalState: { openData: boolean, callbackData: Function, openGenerate: boolean, callbackGenerate: Function } }> = ({ phase, modalState }) => {

    const [page, setPage] = useState(0);
    const sentimentinstances = useFetchPagedSentimentInstance(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);


    // Reload the data on reload
    useEffect(() => {
        sentimentinstances.mutate();
    }, [phase]);

    if (!phase || sentimentinstances.isLoading || sentimentinstances.isError) {
        return <LoadingComponent />;
    }

    return (
        <div>
            <div className="flex flex-col font-dm-mono-medium">
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
                                    Sentence
                                </th>
                                
                              
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Label Set
                                </th> 

                               {/*  <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Non Label
                                </th> */}
                            </tr>
                        </thead>
                        <tbody className=" text-base16-gray-700">
                            {sentimentinstances.data.getContent().map((instance, i) => {
                                return (
                                    <tr key={instance.getId().getInstanceId()}>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {instance.getId().getInstanceId()}
                                        </td>

                                        <td className="px-6 py-4 overflow-auto font-dm-mono-bold">
                                            <span key={i} className="tooltip group w-fit">
                                                {getFormatedUsage(instance.getUsage())}
                                                <div className="tooltip-container group-hover:scale-100">
                                                    <div className="whitespace-nowrap mx-4 my-2">
                                                        Data ID: {instance.getUsage().getId().getDataid()}
                                                    </div>
                                                </div>
                                            </span>
                                        </td>
                                        <ChoicesCard isOpen={true} choiceInstance={instance} />


                                        {/*  <td className="px-6 py-4 whitespace-nowrap">
                                            {instance.getLabelSet().join(', ')}
                                        </td> 
 */}
                                        {/* <td className="px-6 py-4 whitespace-nowrap">
                                            {instance.getNonLabel()}
                                        </td> */}
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </div>
            </div>

            <PageChange page={page} maxPage={sentimentinstances.data.getTotalPages()} pageChangeCallback={(p: number) => { setPage(p) }} />

            <AddInstanceToPhaseModal isOpen={modalState.openData} closeModalCallback={modalState.callbackData} phase={phase} mutateCallback={sentimentinstances.mutate} />
            <GenerateInstancesForPhaseModal isOpen={modalState.openGenerate} closeModalCallback={modalState.callbackGenerate} phase={phase} mutateCallback={sentimentinstances.mutate} additional={false} additionalFileName="" />
    
        </div>
    );
}

export default SentimentInstanceTable;


// TODO: Move to lib

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