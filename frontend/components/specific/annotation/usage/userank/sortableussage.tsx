// icons
import { FiAlignLeft } from "react-icons/fi";
import { CSS } from "@dnd-kit/utilities";

// Usage
import Usage from "../../../../../lib/model/phitagdata/usage/model/Usage";
import { useSortable } from "@dnd-kit/sortable";
import { useState } from "react";

interface IProps {
    usage: Usage;
    id: string,
  
}
const SortableUsage: React.FC<IProps> = ({ usage, id }) => {

    const [isHovered, setIsHovered] = useState(false);

    const {
        attributes,
        listeners,
        setNodeRef,
        transform,
        transition,
    } = useSortable({
        id: id,
    });

    const styles = {
        transform: CSS.Transform.toString(transform),
        transition,
        cursor: 'grab',
        boxShadow: ( isHovered) ? '0 0 5px 2px #36c2f9' : 'none',
    };

  
    return (
        <div
            className="w-full shadow-md"
            style={styles}
            ref={setNodeRef}
            {...attributes}
            {...listeners}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <div className="m-8 flex flex-row active:transform active:scale-95" >

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
    )

}

export default SortableUsage;

/**
 * Constructs a context array with Pairs of the form {sentence: string, highlight: "none" | "bold" | "color"}
 * 
 * @param usage usage to construct the context from
 */
export function usageContextBuilder(usage: Usage): { sentence: string, highlight: "none" | "bold" | "color" }[] {

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
