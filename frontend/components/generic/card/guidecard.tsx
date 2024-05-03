// Next Components
import Link from "next/link";

// React Icons

// Custom Libraries
import GuideHeader from "../../../lib/model/guides/GuideHeader";

// TODO: refactor when project
export default function GuideCard({ guide }: { guide: GuideHeader }) {

    if (!guide) {
        return <div />;
    }

    return (
        <Link href={`/guide/${guide.id}`} passHref>
            <div className="w-full shadow-md cursor-pointer hover:scale-[1.025] hover:transition-all duration-200">
                <div className="h-full flex flex-col grow p-8 xl:px-10 break-words font-dm-mono-regular text-base16-gray-900">
                    <h1 className="font-dm-mono-medium font-bold text-2xl">
                        {guide.title}
                    </h1>

                    <div className="my-2">
                        Description:
                        <div className="markdown-preview" dangerouslySetInnerHTML={{ __html: guide.description }} />
                    </div>

                </div>
            </div>
        </Link>
    );

}