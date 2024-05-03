import Head from "next/head";

// Custom Components
import BasicLayout from "../components/generic/layout/basiclayout";
import ContentLayout from "../components/generic/layout/contentlayout";

export default function AboutUs() {

    return (
        <BasicLayout>
            <Head>
                <title>PhiTag: About Us</title>
            </Head>

            <ContentLayout>
                <div className="flex flex-col">
                    <h1 className="text-4xl font-bold">About Us</h1>
                </div>
            </ContentLayout>
        </BasicLayout>
    );

}