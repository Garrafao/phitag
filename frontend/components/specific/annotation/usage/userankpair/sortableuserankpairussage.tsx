// icons
import { FiAlignLeft } from "react-icons/fi";
import { CSS } from "@dnd-kit/utilities";

// Usage
import Usage from "../../../../../lib/model/phitagdata/usage/model/Usage";
import { useSortable } from "@dnd-kit/sortable";
import { useRef, useState } from "react";

interface IProps {
    usage: {
        usage1: Usage; // Replace YourUsageType with the actual type of your usage
        usage2: Usage;
    };
    id: string;
    selected?: boolean;
    onSelect?: (id: string) => void;
}


const SortableUseRankPairUsage: React.FC<IProps> = ({ usage, id, selected = false, onSelect }) => {

    const [isHovered, setIsHovered] = useState(false);
    const parentDivRef = useRef(null);
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
          <hr className="my-2 mx-2 border-t-2 border-gray-900" />
      
          <div className="m-2 flex flex-row">
            <div className="my-2">
              <FiAlignLeft className="basic-svg" />
            </div>
            <div className="border-r-2 mx-2" />
            <div className="my-2 font-dm-mono-light text-lg overflow-auto">
              {usageContextBuilder(usage.usage1).map((sentence, index) => (
                <span
                  key={index}
                  className={
                    sentence.highlight === 'bold'
                      ? 'font-dm-mono-medium'
                      : sentence.highlight === 'color'
                      ? 'inline font-dm-sans-bold text-lg text-base16-green'
                      : ''
                  }
                >
                  {sentence.sentence}
                </span>
              ))}
            </div>
          </div>
      
          <div className="m-2 flex flex-row">
            <div className="my-2">
              <FiAlignLeft className="basic-svg" />
            </div>
            <div className="border-r-2 mx-2" />
            <div className="my-2 font-dm-mono-light text-lg overflow-auto">
              {usageContextBuilder(usage.usage2).map((sentence, index) => (
                <span
                  key={index}
                  className={
                    sentence.highlight === 'bold'
                      ? 'font-dm-mono-medium'
                      : sentence.highlight === 'color'
                      ? 'inline font-dm-sans-bold text-lg text-base16-green'
                      : ''
                  }
                >
                  {sentence.sentence}
                </span>
              ))}
            </div>
          </div>
      
          <hr className="my-2 mx-2 border-t-2 border-gray-900" />
        </div>
      );
      
};



export default SortableUseRankPairUsage;

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
