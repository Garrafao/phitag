import { NextPage } from "next";
import Head from "next/head";

// Layout
import Layout from "../../components/generic/layout/layout";
import ContentLayout from "../../components/generic/layout/contentlayout";
import CorpusTable from "../../components/generic/table/corpustexttable";

const Corpus: NextPage = () => {

    return (
        <Layout>

            <Head>
                <title>Phitag: Corpus</title>
            </Head>

            <ContentLayout>

                <CorpusTable />
                
            </ContentLayout>
        </Layout>
    );
};

export default Corpus;