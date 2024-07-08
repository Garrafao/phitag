// Next
import Head from "next/head";

// Custom Controllers
import { getSpecificDataFromDirectory } from "../lib/hook/useData";

// Custom Models
import StaticData from "../lib/model/staticdata/staticdata";

// Custom Components
import BasicLayout from "../components/generic/layout/basiclayout";
import SingleContentLayout from "../components/generic/layout/singlecontentlayout";

export default function PrivacyPolicy({ staticData }: { staticData: StaticData }) {

    return (
        <BasicLayout>
            <Head>
                <title>PhiTag: Privacy Policy</title>
            </Head>

            <SingleContentLayout>
                <div className="markdown-page-uni" dangerouslySetInnerHTML={{ __html: staticData.content }} />
            </SingleContentLayout>

        </BasicLayout>
    );

}

export async function getStaticProps() {
    const staticData: StaticData = getSpecificDataFromDirectory('data/privacy-policy', 'privacy-policy');
    return {
        props: {
            staticData: JSON.parse(JSON.stringify(staticData)),
        },
    };
}