// Next Modules
import { NextPage } from "next";
import Head from "next/head";
import Router from "next/router";

// React Modules
import { useEffect, useState } from "react";

// React Icons
import { FiClipboard, FiSearch, FiToggleLeft, FiToggleRight } from "react-icons/fi";

// Toast
import { toast } from "react-toastify";

// Custom Controllers
import useAuthenticated from "../../../lib/hook/useAuthenticated";
import useStorage from "../../../lib/hook/useStorage";
import { joinJoblisting, useQueryJoblisting } from "../../../lib/service/joblisting/JoblistingResource";

// Models
import Joblisting from "../../../lib/model/joblisting/model/Joblisting";
import JoinJoblistingCommand from "../../../lib/model/joblisting/command/JoinJoblistingCommand";

// Custom Components
import IconButtonOnClick from "../../../components/generic/button/iconbuttononclick";
import IconButtonWithTooltip from "../../../components/generic/button/iconbuttonwithtooltip";
import LoadingComponent from "../../../components/generic/loadingcomponent";
import JoblistingCard from "../../../components/generic/card/joblistingcard";

// Layout
import Layout from "../../../components/generic/layout/layout";
import ContentLayout from "../../../components/generic/layout/contentlayout";
import HelpButton from "../../../components/generic/button/helpbutton";

const JoblistingPage: NextPage = () => {

    // Data & Hooks
    const storage = useStorage();
    const authenticated = useAuthenticated();

    const [searchField, setSearchField] = useState({
        fieldQuery: "",
        openListing: true,
    });

    const joblistings = useQueryJoblisting(searchField.fieldQuery, searchField.openListing);

    const handleOnClickJoin = (joblisting: Joblisting) => {
        joinJoblisting(new JoinJoblistingCommand(joblisting.getId().getName(), joblisting.getId().getOwner(), joblisting.getId().getProject()), storage.get)
            .then((res) => {
                if (joblisting.isOpen()) {
                    toast.success("You have successfully joined as a annotator of the project.");
                } else {
                    toast.success("You have successfully joined the joblisting!");
                }
            }).catch((error) => {
                if (error?.response?.status === 500) {
                    toast.error("Error while joining joblisting: " + error.response.data.message + "!");
                } else {
                    toast.warning("The system is currently not available, please try again later!");
                }
            });
    }


    useEffect(() => {
        if (joblistings.isError) {
            toast.error("An error occurred while fetching data.");
            Router.push("/dashboard");
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, joblistings.isError]);

    return (
        <Layout>

            <Head>
                <title>PhiTag: Job Overview</title>
            </Head>

            <ContentLayout>
                <div className='flex flex-col w-full'>
                    <div className="flex flex-col md:flex-row md:items-center md:space-x-6">
                        <div className="flex font-dm-mono-medium font-bold text-2xl">
                            Joblistings
                        </div>

                        <div className="flex flex-row w-full ">
                            <div className="flex flex-row w-full basis-full items-center border-b-2 py-2 px-3 my-4">
                                <input
                                    className="pr-3 flex flex-auto outline-none border-none font-dm-sans-medium font-bold"
                                    placeholder="Search Term"
                                    type={"text"}
                                    value={searchField.fieldQuery}
                                    onChange={(e) => setSearchField({
                                        ...searchField,
                                        fieldQuery: e.target.value
                                    })} />
                                <FiSearch className='basic-svg' />
                            </div>



                            <div className="flex items-center my-4 ml-4">
                                {searchField.openListing ?
                                    <IconButtonOnClick
                                        icon={<FiToggleLeft className="basic-svg" />}
                                        tooltip={"Open Listings"}
                                        onClick={() => setSearchField({
                                            ...searchField,
                                            openListing: false
                                        })} />
                                    :
                                    <IconButtonOnClick
                                        icon={<FiToggleRight className="basic-svg" />}
                                        tooltip={"Listings with Waitinglist"}
                                        onClick={() => setSearchField({
                                            ...searchField,
                                            openListing: true
                                        })} />
                                }
                            </div>

                            <div className="flex items-center my-4 ml-4">
                                <IconButtonWithTooltip
                                    icon={<FiClipboard className="basic-svg" />}
                                    tooltip={"See Personal Joblisting"}
                                    reference={"/pool/joblisting/personal"} />
                            </div>

                            <div className="flex items-center my-4 ml-4">
                                <HelpButton
                                    title="Help: Joblistings"
                                    tooltip="Help: Joblistings"
                                    text="
                                        A joblisting allows you to join a project as an annotator. 
                                        You can either join an open joblisting or join a joblisting with a waitinglist. 
                                        If you join a joblisting with a waitinglist, you will be added to the waitinglist and will be notified if you are accepted. 
                                        If you join an open joblisting, you will be added to the project as an annotator immediately.
                                        You can see your personal joblistings on the 'Personal Joblistings' page."

                                    reference=""
                                    linkage={false}
                                />
                            </div>

                        </div>
                    </div>

                    {
                        joblistings.isLoading ? <LoadingComponent /> :
                            <div className={"joblist-grid"}>
                                {
                                    joblistings.data.map((joblisting, index) => {
                                        return <JoblistingCard key={index} joblisting={joblisting} onClick={() => { handleOnClickJoin(joblisting) }} />
                                    })
                                }
                            </div>
                    }
                </div>
            </ContentLayout>

        </Layout>
    );
}

export default JoblistingPage;