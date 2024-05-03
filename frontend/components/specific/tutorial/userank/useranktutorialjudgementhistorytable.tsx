// next
import Link from "next/link";



// models
import Phase from "../../../../lib/model/phase/model/Phase";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";

// components
import LoadingComponent from "../../../generic/loadingcomponent";
import { useEffect, useState } from "react";
import useStorage from "../../../../lib/hook/useStorage";
import PageChange from "../../../generic/table/pagination";
import { useFetchPagedHistoryUsePairTutorialJudgements, useFetchPagedHistoryUseRankTutorialJudgements } from "../../../../lib/service/tutorial/tutorialresources";
import UseRankJudgement from "../../../../lib/model/judgement/userankjudgement/model/UseRankJudgement";
import { IoIosArrowDown, IoIosArrowUp } from "react-icons/io";
import UsageCard from "../../card/usagecard";

const UseRankTutorialJudgementHistoryTable: React.FC<{ phase: Phase }> = ({ phase }) => {

    const storage = useStorage();
    const [page, setPage] = useState(0);
    const judgements = useFetchPagedHistoryUseRankTutorialJudgements(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

    /* const [editModal, setEditModal] = useState({
        open: false,
        judgement: null as unknown as UsePairJudgement,
    }); */

    /*  const deleteCallback = (usepairjudgement: UsePairJudgement) => {
         deleteUsepair(new DeleteUsePairJudgementCommand(
             usepairjudgement.getId().getOwner(),
             usepairjudgement.getId().getProject(),
             usepairjudgement.getId().getPhase(),
             usepairjudgement.getId().getInstanceId(),
             usepairjudgement.getId().getAnnotator(),
             usepairjudgement.getId().getId(),
         ), storage.get).then((res) => {
             toast.success("Judgement deleted");
             usepairjudgements.mutate();
         }).catch((err) => {
             if (err?.response?.status === 500) {
                 toast.error("Error deleting judgement: " + err.response.data.message);
             } else {
                 toast.error("Error deleting judgement!");
             }
         });
     } */
    const [expandedInstances, setExpandedInstances] = useState<boolean[]>(
        new Array(judgements.data.getContent().length).fill(false)
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
        judgements.mutate();
    }, [phase]);

    if (!phase || judgements.isLoading || judgements.isError) {
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
                                Rank
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Comment
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Annotator
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider">
                                Edit
                            </th>

                        </tr>
                    </thead>
                    <tbody className=" text-base16-gray-700">
                        {judgements.data.getContent().map((judgement, i) => {
                            let userankjudgement: UseRankJudgement = judgement;
                            return (
                                <>
                                    <tr key={userankjudgement.getId().getId()}>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {userankjudgement.getId().getInstanceId()}
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

                                        <td className="px-9 py-4 whitespace-nowrap" key={i}>
                                            {judgement.getLabel()}

                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {userankjudgement.getComment()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <Link href={`/phi/${userankjudgement.getId().getAnnotator()}`}>
                                                <a className="underline">
                                                    {userankjudgement.getId().getAnnotator()}
                                                </a>
                                            </Link>
                                        </td>


                                    </tr>
                                    {expandedInstances[i] && (
                                        <tr key={`${i}-expanded`}
                                            className="transition-max-h transition-property: transform duration-0  max-h-0 ">
                                            <td colSpan={6}>
                                                <UsageCard isOpen={true} userankinstance={userankjudgement.getInstance()} />
                                            </td>
                                        </tr>
                                    )}
                                </>
                            );
                        })}
                    </tbody>
                </table>
            </div>
            <PageChange page={judgements.data.getPage()} maxPage={judgements.data.getTotalPages()} pageChangeCallback={(p: number) => { setPage(p) }} />
        </div >
    );
}

export default UseRankTutorialJudgementHistoryTable;

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