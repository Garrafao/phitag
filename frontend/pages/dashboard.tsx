// React Modules
import { useEffect, useState } from "react";

// Next Modules
import { NextPage } from "next";
import Router from 'next/router';
import Head from "next/head";

import { toast } from "react-toastify";

// Custom Hooks
import USECASES from "../lib/model/usecase/Usecases";
import useAuthenticated from "../lib/hook/useAuthenticated";
import useStorage from "../lib/hook/useStorage";

// Custom Components
import DashboardCard from "../components/generic/card/dashboardcard";

// Custom Layouts
import Layout from "../components/generic/layout/layout";
import CenteredLayout from "../components/generic/layout/centeredlayout";
import HelpButton from "../components/generic/button/helpbutton";

const Dashboard: NextPage = () => {

    // Hooks & Fetching
    const authenticated = useAuthenticated();
    const storage = useStorage();

    const [user, setUser] = useState<string | null>("");

    useEffect(() => {
        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }

        if (authenticated.isReady && authenticated.isAuthenticated) {
            setUser(storage.get("USER"));
        }
    }, [authenticated, storage]);

    // default view, i,e, USECASES_DEFAULT  
    return (
        <Layout>

            <Head>
                <title>PhiTag: Home</title>
            </Head>


            <CenteredLayout>
                <div className="w-full flex flex-col 3xl:w-2/3">
                    <div className="flex flex-row items-center justify-between mb-2">
                        <div className="invisible" />
                        <div className="flex font-dm-mono-medium font-bold text-xl lg:text-2xl 2xl:text-3xl">
                            Project
                        </div>
                        <div className="">
                            <HelpButton
                                title="Help: Project Section"
                                tooltip="Help: Project Section"
                                text="The project section is where you can create new projects, browse public projects and your own projects."
                                reference="guide/explained-dashboard"
                            />
                        </div>
                    </div>

                    <div className="grid grid-flow-row grid-cols-2 md:grid-cols-3 gap-4">
                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Create a new Project"
                                description="Create a new Project"
                                link="/phi/create" />
                        </div>
                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Public Projects"
                                description="Browse all public projects"
                                link="/pool/project" />
                        </div>

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="My Projects"
                                description="Browse all your projects"
                                link="/pool/project/personal" />
                        </div>

                    </div>

                    <div className="flex items-center justify-between mt-8 mb-2">
                        <div className="invisible" />
                        <div className="flex font-dm-mono-medium font-bold text-xl lg:text-2xl 2xl:text-3xl">
                            Pools
                        </div>
                        <div className="">
                            <HelpButton
                                title="Help: Pool Section"
                                tooltip="Help: Pool Section"
                                text="The pool section is a quick access to the different pools, i.e. the human annotator pool, the computational annotator pool, the corpus and the joblisting pool."
                                reference="/guide/explained-dashboard"
                            />
                        </div>
                    </div>
                    <div className="grid grid-flow-row grid-cols-2 md:grid-cols-3 gap-4">


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

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Joblisting"
                                description="Browse joblistings or create new ones"
                                link="/pool/joblisting" />
                        </div>

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Corpus"
                                description="Browse the corpus and create custom usages for your project"
                                link="/corpus" />
                        </div>

                      {/*   <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Dictionary Overview"
                                description="Browse your dictionaries and create new ones"
                                link={user ? `/phi/${user}/dictionary` : "/dashboard"} />
                        </div> */}

                    </div>

                    <div className="flex items-center justify-between mt-8 mb-2">
                        <div className="invisible" />
                        <div className="flex font-dm-mono-medium font-bold text-xl lg:text-2xl 2xl:text-3xl">
                            Guides
                        </div>
                        <div className="">
                            <HelpButton
                                title="Help: Guide Section"
                                tooltip="Help: Guide Section"
                                text="The guides section is a quick access to important guides, which will help you to use this tool effectively."
                                reference="/guide/explained-dashboard"
                            />
                        </div>
                    </div>
                    <div className="grid grid-flow-row grid-cols-2 md:grid-cols-3 gap-4">

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Introduction"
                                description="This guide will give you the core idea of Phitag."
                                link="/guide/introduction" />
                        </div>

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Supported Tasks"
                                description="Current overview of Phitag's supported annotation tasks."
                                link="/guide/supported-tasks" />
                        </div>

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Browse Guides"
                                description="Browse all available guides"
                                link="/guide" />
                        </div>

{/* 
                        <div className="hidden sm:visible col-span-1 row-span-1 aspect-square " />

                        <div className="col-span-1 row-span-1 aspect-square">
                            <DashboardCard
                                title="Quick Start: Lexicography"
                                description="Read up onto how to use this tool as a lexicographer"
                                link="guide/how-to-lexicography" />
                        </div> */}
                    </div>
                </div>

            </CenteredLayout>

        </Layout>
    );
}

export default Dashboard;