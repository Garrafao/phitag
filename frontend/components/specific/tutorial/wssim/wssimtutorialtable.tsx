
// models
import WSSIMInstance from "../../../../lib/model/instance/wssiminstance/model/WSSIMInstance";
import AddWSSIMJudmentCommand from "../../../../lib/model/judgement/wssimjudgement/command/AddWSSIMJudgementCommand";
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";

interface IResult {
    data: AddWSSIMJudmentCommand[];
    instances: WSSIMInstance[];
}

const WSSIMTutorialTable: React.FC<IResult> = ({ data, instances }) => {
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

                        </tr>
                    </thead>
                    <tbody className=" text-base16-gray-700">
                        {data.map((judgement, i) => {
                            return (
                                <tr key={i}>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {instances[i].getId().getInstanceId()}
                                    </td>


                                    <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                        <span key={i} className="tooltip group w-fit">
                                            {getFormatedShortUsage(instances[i].getUsage())}
                                            <div className="tooltip-container group-hover:scale-100">
                                                <div className="whitespace-nowrap mx-4 my-2">
                                                    Data ID: {instances[i].getUsage().getId().getDataid()}
                                                </div>
                                            </div>
                                        </span>
                                    </td>

                                    <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                        <span key={i} className="tooltip group w-fit">
                                            {instances[i].getTag().getDefinition()}
                                            <div className="tooltip-container group-hover:scale-100">
                                                <div className="whitespace-nowrap mx-4 my-2">
                                                    Data ID: {instances[i].getTag().getId().getInstanceId()}
                                                </div>
                                            </div>
                                        </span>
                                    </td>


                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {instances[i].getLabelSet().join(", ")}, {instances[i].getNonLabel()}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {judgement.label}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {judgement.comment}
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default WSSIMTutorialTable;

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
