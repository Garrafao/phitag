// Next Components
import Link from "next/link";

// React Icons

// Custom Libraries
import StaticData from "../../../lib/model/staticdata/staticdata";

interface IProjectCardProps {
    guideline: StaticData;
}

// TODO: refactor when project
const StaticGuidelineCard: React.FC<IProjectCardProps> = ({ guideline }) => {

    if (!guideline) {
        return <div />;
    }

    return (
        <Link href={`/guideline/${guideline.id}`} passHref>
            <div className="w-full shadow-md cursor-pointer hover:scale-[1.025] hover:transition-all duration-200">
                <div className="h-full flex flex-col grow p-8 xl:px-10 break-words font-dm-mono-regular text-base16-gray-900">
                    <h1 className="font-dm-mono-medium font-bold text-2xl">
                        {guideline.title}
                    </h1>

                    <div className="my-2">
                        <div className="mb-2">
                            Preview:
                        </div>
                        <div className="markdown-preview" dangerouslySetInnerHTML={{ __html: guideline.description }} />
                    </div>

                </div>
            </div>
        </Link>
    );

}

export default StaticGuidelineCard;