// next
import Head from "next/head";

// services
import { getMatterlessId, getSpecificMatterlessData } from "../../lib/hook/useData";

// layout
import BasicLayout from "../../components/generic/layout/basiclayout";
import SingleContentLayout from "../../components/generic/layout/singlecontentlayout";

export default function Guideline({ id, content }: { id: string, content: string }) {
    return (
        <BasicLayout>
            <Head>
                <title>PhiTag: {id.toUpperCase()}</title>
            </Head>

            <SingleContentLayout>
                <div className='flex flex-col w-full'>
                <div className="markdown-page" dangerouslySetInnerHTML={{ __html: content }} />
                </div>
            </SingleContentLayout>

        </BasicLayout>
    );
}

export async function getStaticPaths() {
    const paths = getMatterlessId("data/guidelines");
    return {
        paths,
        fallback: false
    };
}

export async function getStaticProps({ params }: any) {
    const guideline = getSpecificMatterlessData("data/guidelines", params.id);
    return {
        props: {
            id: guideline.id,
            content: guideline.content
        }
    };
}

