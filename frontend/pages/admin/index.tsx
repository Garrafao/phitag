import { NextPage } from "next";
import Head from "next/head";
import { FiTerminal } from "react-icons/fi";
import ContentLayout from "../../components/generic/layout/contentlayout";
import Layout from "../../components/generic/layout/layout";
import SingleContentLayout from "../../components/generic/layout/singlecontentlayout";
import LinkHead from "../../components/generic/linker/linkhead";
import AdminTabBar from "../../components/specific/tab/admintabbar";

const Admin: NextPage = () => {

    return (
        <Layout>

            <Head>
                <title>Phitag: Admin Panel</title>
            </Head>

            

            <SingleContentLayout>

                <LinkHead icon={<FiTerminal className="stroke-2" />}
                    links={[
                        {
                            href: `/admin`,
                            name: "Admin Panel",
                        },
                    ]}
                />

                <AdminTabBar />
            </SingleContentLayout>

        </Layout>
    );
};

export default Admin;