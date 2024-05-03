import Link from "next/link";

import { useEffect, useState } from "react";

// services
import { deleteWssim, useFetchHistory, useFetchPagedHistoryWSSIMJudgements } from "../../../../lib/service/judgement/JudgementResource";

// models
import WSSIMJudgement, { WSSIMJudgementConstructor } from "../../../../lib/model/judgement/wssimjudgement/model/WSSIMJudgement";
import Phase from "../../../../lib/model/phase/model/Phase";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";

// components
import LoadingComponent from "../../../generic/loadingcomponent";
import IconButtonOnClick from "../../../generic/button/iconbuttononclick";
import { FiEdit, FiTool, FiTrash } from "react-icons/fi";
import EditWSSIMJudgementModal from "../../modal/editwssimjudgementmodal";
import DeleteWSSIMJudgementCommand from "../../../../lib/model/judgement/wssimjudgement/command/DeleteWSSIMJudgementCommand";
import useStorage from "../../../../lib/hook/useStorage";
import { toast } from "react-toastify";
import PageChange from "../../../generic/table/pagination";
import { useFetchPagedHistoryWSSIMTutorialJudgements } from "../../../../lib/service/tutorial/tutorialresources";

const WSSIMTutorialJudgementHistoryTable: React.FC<{ phase: Phase }> = ({ phase }) => {

    const storage = useStorage();
    const [page, setPage] = useState(0);
    const wssimjudgements = useFetchPagedHistoryWSSIMTutorialJudgements(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

  
    

    // Reload the data on reload
    useEffect(() => {
        wssimjudgements.mutate();
    }, [phase]);

    if (!phase || wssimjudgements.isLoading || wssimjudgements.isError) {
        return <LoadingComponent />;
    }

    return (
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
                                Usage
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Tag
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Label Set and Non Label
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Label
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Comment
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Annotator
                            </th>
                          
                        </tr>
                    </thead>
                    <tbody className=" text-base16-gray-700">
                        {wssimjudgements.data.getContent().map((judgement, i) => {
                            let castedJudgement: WSSIMJudgement = judgement;
                            return (
                                <tr key={castedJudgement.getId().getId()}>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {castedJudgement.getId().getInstanceId()}
                                    </td>


                                    <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                        <span key={i} className="tooltip group w-fit">
                                            {getFormatedShortUsage(castedJudgement.getInstance().getUsage())}
                                            <div className="tooltip-container group-hover:scale-100">
                                                <div className="whitespace-nowrap mx-4 my-2">
                                                    Data ID: {castedJudgement.getInstance().getUsage().getId().getDataid()}
                                                </div>
                                            </div>
                                        </span>
                                    </td>

                                    <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                        <span key={i} className="tooltip group w-fit">
                                            {castedJudgement.getInstance().getTag().getDefinition()}
                                            <div className="tooltip-container group-hover:scale-100">
                                                <div className="whitespace-nowrap mx-4 my-2">
                                                    Data ID: {castedJudgement.getInstance().getTag().getId().getInstanceId()}
                                                </div>
                                            </div>
                                        </span>
                                    </td>


                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {castedJudgement.getInstance().getLabelSet().join(", ")}, {castedJudgement.getInstance().getNonLabel()}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {castedJudgement.getLabel()}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {castedJudgement.getComment()}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        <Link href={`/phi/${castedJudgement.getId().getAnnotator()}`}>
                                            <a className="underline">
                                                {castedJudgement.getId().getAnnotator()}
                                            </a>
                                        </Link>
                                    </td>
                                    
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
            <PageChange page={wssimjudgements.data.getPage()} maxPage={wssimjudgements.data.getTotalPages()} pageChangeCallback={(p: number) => {setPage(p)}} />


        </div>
    );

}

export default WSSIMTutorialJudgementHistoryTable;

function getFormatedShortUsage(usage: Usage) {
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