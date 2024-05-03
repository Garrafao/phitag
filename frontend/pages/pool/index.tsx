// Next
import { NextPage } from "next";
import Head from "next/head";
import Router from "next/router";

// React Modules
import { useEffect } from "react";

// Toast
import { toast } from "react-toastify";

// Components
import DashboardCard from "../../components/generic/card/dashboardcard";
import CenteredLayout from "../../components/generic/layout/centeredlayout";

// Layout
import Layout from "../../components/generic/layout/layout";
import useAuthenticated from "../../lib/hook/useAuthenticated";
import HelpButton from "../../components/generic/button/helpbutton";

const Dashboard: NextPage = () => {

    // Hooks & Fetching
    const authenticated = useAuthenticated();

    useEffect(() => {
        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated]);

    return (
        <Layout>

            <Head>
                <title>PhiTag: Pool</title>
            </Head>


            <CenteredLayout>
                <div className="w-full flex flex-col 3xl:w-1/3">
                    <div className="flex items-center justify-center mb-2">
                        <div className="flex font-dm-mono-medium font-bold text-xl lg:text-2xl 2xl:text-3xl">
                            Pools
                        </div>
                    </div>

                    <div className="flex flex-row items-center justify-between mb-2">
                        <div className="invisible" />
                        <div className="flex font-dm-mono-medium font-bold text-xl lg:text-2xl 2xl:text-3xl">
                            Annotators
                        </div>
                        <div className="">
                            <HelpButton
                                title="Help: Annotator Selection"
                                tooltip="Help: Annotator Selection"
                                text="The annotator section is where you can browse the annotator pool, which is split into human and computational annotators."
                                reference="guide/explained-pools"
                            />
                        </div>
                    </div>

                    <div className="grid grid-flow-row grid-cols-2 gap-4">

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Human Annotators"
                                description="Browse the human annotator pool"
                                link="/pool/annotator" />
                        </div>

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Computational Annotators"
                                description="Browse the computational annotator pool"
                                link="/pool/annotator/computational" />
                        </div>
                    </div>


                    <div className="flex flex-row items-center justify-between mt-8 mb-2">
                        <div className="invisible" />
                        <div className="flex font-dm-mono-medium font-bold text-xl lg:text-2xl 2xl:text-3xl">
                            Projects
                        </div>
                        <div className="">
                            <HelpButton
                                title="Help: Project Selection"
                                tooltip="Help: Project Selection"
                                text="The project section is where you can browse personal and public projects."
                                reference="guide/explained-pools"
                            />
                        </div>
                    </div>

                    <div className="grid grid-flow-row grid-cols-2 gap-4">
                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Public Projects"
                                description="Brows all public projects"
                                link="/pool/project" />
                        </div><div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="My Projects"
                                description="Browse all your projects"
                                link="/pool/project/personal" />
                        </div>
                    </div>

                    <div className="flex flex-row items-center justify-between mt-8 mb-2">
                        <div className="invisible" />
                        <div className="flex font-dm-mono-medium font-bold text-xl lg:text-2xl 2xl:text-3xl">
                            Jobs
                        </div>
                        <div className="">
                            <HelpButton
                                title="Help: Jobs Selection"
                                tooltip="Help: Jobs Selection"
                                text="The jobs section is where you can browse your personal jobs or public ones."
                                reference="guide/explained-pools"
                            />
                        </div>
                    </div>


                    <div className="grid grid-flow-row grid-cols-2 gap-4">

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Joblisting"
                                description="Browse joblistings for new projects"
                                link="/pool/joblisting" />
                        </div>
                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Joblisting"
                                description="Browse your personal joblistings or create new ones"
                                link="/pool/joblisting" />
                        </div>
                    </div>

                </div>
            </CenteredLayout>
        </Layout >
    );
};

export default Dashboard;