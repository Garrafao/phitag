import { micromark } from "micromark";
import { gfm, gfmHtml } from "micromark-extension-gfm";
import { useState } from "react";

interface IExpandableCardProps {
    title: string;
    content: string;
}

const ExpandableCard: React.FC<IExpandableCardProps> = ({ title, content }) => {

    const [isExpanded, setIsExpanded] = useState(false);

    return (
        <div className="flex flex-col w-full shadow-lg p-8">
            <div className="flex flex-row w-full items-center justify-between cursor-pointer" onClick={() => setIsExpanded(!isExpanded)}>
                <div className="flex font-dm-mono-medium font-bold text-2xl">
                    {title}
                </div>
                <div className="flex flex-row items-center space-x-2">
                    <div className="flex font-dm-mono-medium font-bold text-2xl">
                        {isExpanded ? "-" : "+"}
                    </div>
                </div>
            </div>

            {
                isExpanded &&
                <div className="flex flex-col justify-center my-8 text-lg">

                    <div className="prose-a:underline" dangerouslySetInnerHTML={{
                        __html:
                            micromark(content, {
                                extensions: [gfm()],
                                htmlExtensions: [gfmHtml()],

                                // Only allow for static markdown files
                                allowDangerousHtml: true
                            })
                    }} />
                </div>
            }
        </div>
    )
}

export default ExpandableCard;