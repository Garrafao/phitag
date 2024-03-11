// icons
import { FiAlignLeft } from "react-icons/fi";

// Usage
import Usage from "../../../../lib/model/phitagdata/usage/model/Usage";


const UsageField: React.FC<{ usage: Usage }> = ({ usage }) => {

    return (
        <div className="w-full shadow-md ">
            <div className="m-8 flex flex-row">

                <div className="my-4">
                    <FiAlignLeft className="basic-svg" />
                </div>

                <div className="border-r-2 mx-4" />

                <div className="my-4 font-dm-mono-light text-lg overflow-auto">
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
            </div>
        </div>

    );

}

export default UsageField;

/**
 * Constructs a context array with Pairs of the form {sentence: string, highlight: "none" | "bold" | "color"}
 * 
 * @param usage usage to construct the context from
 */
function usageContextBuilder(usage: Usage): { sentence: string, highlight: "none" | "bold" | "color" }[] {

    const context = usage?.getContext();

    const contextArray: { sentence: string, highlight: "none" | "bold" | "color" }[] = [];

    // Add the first sentence
    contextArray.push({
        sentence: context?.substring(0, usage.getIndexTargetSentenceStart()),
        highlight: "none"
    });

    let lastTargetTokenEnd = 0;

    usage?.getIndexTargetSentence().forEach((sentence, index) => {
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
        sentence: context?.substring(usage.getIndexTargetSentenceEnd()),
        highlight: "none"
    });

    return contextArray;

}