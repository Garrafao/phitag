// Next
import Head from "next/head";

// Custom Components
import BasicLayout from "../components/generic/layout/basiclayout";
import SingleContentLayout from "../components/generic/layout/singlecontentlayout";

export default function PrivacyPolicy() {

    return (
        <BasicLayout>
            <Head>
                <title>PhiTag: Privacy Policy</title>
            </Head>

            <SingleContentLayout>
                <div className="flex flex-col">
                    <h1 className="text-4xl font-bold">Privacy Policy</h1>
                </div>
            </SingleContentLayout>

        </BasicLayout>
    );

}
