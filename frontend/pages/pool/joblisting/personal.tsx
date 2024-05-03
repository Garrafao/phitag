// Next
import { NextPage } from "next";
import Head from "next/head";
import Router from "next/router";

// React Modules
import { useEffect, useState } from "react";

// Icons
import { FiEye, FiEyeOff, FiPlus, FiSearch, FiToggleLeft, FiToggleRight } from "react-icons/fi";

// Toast
import { toast } from "react-toastify";

// Custom Controllers
import useAuthenticated from "../../../lib/hook/useAuthenticated";
import { useFetchPersonalJobListing } from "../../../lib/service/joblisting/JoblistingResource";

// Models
import PersonalJoblisting from "../../../lib/model/joblisting/model/PersonalJoblisting";

// Layout
import ContentLayout from "../../../components/generic/layout/contentlayout";
import Layout from "../../../components/generic/layout/layout";

// Custom Components
import PersonalJoblistingCard from "../../../components/generic/card/personaljoblistingcard";
import LoadingComponent from "../../../components/generic/loadingcomponent";
import IconButtonOnClick from "../../../components/generic/button/iconbuttononclick";
import CreateJoblistingModal from "../../../components/specific/modal/createjoblistingmodal";
import AddUserFromWaitingListModal from "../../../components/specific/modal/adduserfromwaitinglist";
import HelpButton from "../../../components/generic/button/helpbutton";

const PersonalJoblistingPage: NextPage = () => {

    // Data & Hooks
    const authenticated = useAuthenticated();

    const [searchField, setSearchField] = useState({
        fieldQuery: "",
        open: true,
        active: true
    });

    const joblisting = useFetchPersonalJobListing(searchField.fieldQuery, searchField.open, searchField.active);

    // create joblisting modal
    const [createModalState, setCreateModalState] = useState({
        isOpen: false,
    });

    const closeCreateModalCallback = () => {
        setCreateModalState({
            isOpen: false,
        });
    }


    // Modal add user from waiting list
    const [addModalState, setAddModalState] = useState({
        isOpen: false,
        joblisting: null as unknown as PersonalJoblisting
    });


    const closeAddModalCallback = () => {
        setAddModalState({
            isOpen: false,
            joblisting: null as unknown as PersonalJoblisting
        });
    }

    // effects
    useEffect(() => {
        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated]);

    return (
        <Layout>

            <Head>
                <title>PhiTag: Job Overview</title>
            </Head>

            <ContentLayout>
                <div className='flex flex-col w-full'>
                    <div className="flex flex-col md:flex-row md:items-center md:space-x-6">
                        <div className=" flex-row font-dm-mono-medium font-bold text-2xl">
                            Personal Joblistings
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
                                {searchField.open ?
                                    <IconButtonOnClick
                                        icon={<FiToggleLeft className="basic-svg" />}
                                        tooltip={"Open Listings"}
                                        onClick={() => setSearchField({
                                            ...searchField,
                                            open: false
                                        })} />
                                    :
                                    <IconButtonOnClick
                                        icon={<FiToggleRight className="basic-svg" />}
                                        tooltip={"Listings with Waitinglist"}
                                        onClick={() => setSearchField({
                                            ...searchField,
                                            open: true
                                        })} />
                                }
                            </div>
                            <div className="flex items-center my-4 ml-4">
                                {searchField.active ?
                                    <IconButtonOnClick
                                        icon={<FiEye className="basic-svg" />}
                                        tooltip={"Active Listings"}
                                        onClick={() => setSearchField({
                                            ...searchField,
                                            active: false
                                        })} />
                                    :
                                    <IconButtonOnClick
                                        icon={<FiEyeOff className="basic-svg" />}
                                        tooltip={"Inactive Listings"}
                                        onClick={() => setSearchField({
                                            ...searchField,
                                            active: true
                                        })} />
                                }
                            </div>
                            <div className="flex flex-row items-center my-4 ml-4">
                                <IconButtonOnClick
                                    icon={<FiPlus className="basic-svg" />}
                                    onClick={() => { setCreateModalState({ isOpen: true }) }}
                                    tooltip={"Create new Joblisting"} />
                            </div>

                            <div className="flex items-center my-4 ml-4">
                                <HelpButton
                                    title="Help: Personal Joblistings"
                                    tooltip="Help: Personal Joblistings"
                                    text="
                                        Here you can see all your personal joblistings. You can create new joblistings by clicking on the plus button.
                                        You can also add users from the waiting list to the joblisting by clicking on the add button.
                                        "
                                        
                                    reference=""
                                    linkage={false}
                                />
                            </div>
                        </div>
                    </div>


                    {
                        joblisting.isLoading ? <LoadingComponent /> :
                            <div className={"joblist-grid"}>
                                {
                                    joblisting.data.map((joblisting, index) => {
                                        return <PersonalJoblistingCard key={index} joblisting={joblisting} onClick={() => {
                                            setAddModalState({
                                                isOpen: true,
                                                joblisting: joblisting
                                            })
                                        }} />
                                    })
                                }
                            </div>
                    }
                </div>
            </ContentLayout>
            <CreateJoblistingModal isOpen={createModalState.isOpen} closeModalCallback={closeCreateModalCallback} mutateCallback={joblisting.mutate} />

            <AddUserFromWaitingListModal isOpen={addModalState.isOpen} closeModalCallback={closeAddModalCallback} joblisting={addModalState.joblisting} mutateCallback={() => {}} />

        </Layout>
    );
}

export default PersonalJoblistingPage;



