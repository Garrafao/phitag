// next
import Head from "next/head";

// services


// layout
import BasicLayout from "../components/generic/layout/basiclayout";
import SingleContentLayout from "../components/generic/layout/singlecontentlayout";
import { getAllGuidesId, getGuide } from "../lib/hook/useGuide";
import Guide from "../lib/model/guides/Guide";

export default function UseCase({ guide }: { guide: Guide }) {
    return (
        <BasicLayout>
            <Head>
                <title>PhiTag: Use Cases</title>
            </Head>

            <SingleContentLayout>
                <div className='flex flex-col w-full'>
                    <div className="markdown-page" dangerouslySetInnerHTML={{ __html: guide.content }} />
                </div>
            </SingleContentLayout>

        </BasicLayout>
    );
}

export async function getStaticProps() {
    const guide = await getGuide("../use-case/use-case");
    return {
        props: {
            guide: JSON.parse(JSON.stringify(guide)),
        },
    };
}
