import { NextPage } from "next";
import Head from "next/head";
import { FiTerminal } from "react-icons/fi";
import Layout from "../../components/generic/layout/layout";
import SingleContentLayout from "../../components/generic/layout/singlecontentlayout";
import LinkHead from "../../components/generic/linker/linkhead";
import ReportTable from "../../components/generic/table/reporttable";
import AdminTabBar from "../../components/specific/tab/admintabbar";

const Reports: NextPage = () => {

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
                        {
                            href: `/admin/reports`,
                            name: "Reports",
                        }
                    ]}
                />

                <AdminTabBar />

                <ReportTable />
            </SingleContentLayout>

        </Layout>
    );
};

export default Reports;