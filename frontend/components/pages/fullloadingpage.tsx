import Head from "next/head";

// Layout
import Layout from "../generic/layout/layout";
import SingleContentLayout from "../generic/layout/singlecontentlayout";

// Component
import LoadingComponent from "../generic/loadingcomponent";

interface IProps {
    headtitle: string;
}

const FullLoadingPage: React.FC<IProps> = ({ headtitle }) => {

    return (
        <Layout>
            <Head>
                <title>PhiTag: {headtitle}</title>
            </Head>

            <SingleContentLayout>

                <LoadingComponent />

            </SingleContentLayout>
        </Layout>
    );
}

export default FullLoadingPage;