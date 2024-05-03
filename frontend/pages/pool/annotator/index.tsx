// React Modules
import React, { useEffect, useState } from 'react';

// Next Modules
import { NextPage } from 'next';
import Head from 'next/head';
import Router from 'next/router';

// Toast
import { toast } from 'react-toastify';

// React Icons
import { FiGrid, FiList, FiSearch } from 'react-icons/fi';

// Services
import useAuthenticated from '../../../lib/hook/useAuthenticated';
import { useQueryUsers } from "../../../lib/service/user/UserResource";

// Models
import User from '../../../lib/model/user/model/User';

// Layout
import Layout from "../../../components/generic/layout/layout";
import ContentLayout from "../../../components/generic/layout/contentlayout";

// Components
import UserCard from '../../../components/generic/card/usercard';
import AddUserToProjectModal from '../../../components/specific/modal/addusertoprojectmodal';
import LoadingComponent from '../../../components/generic/loadingcomponent';
import HelpButton from '../../../components/generic/button/helpbutton';

const HumanPool: NextPage = () => {

    // State and Data
    const [searchField, setSearchField] = useState({
        grid: false,
        fieldQuery: "",
    });

    const [modalState, setModalState] = useState({
        isOpen: false,
        user: null as unknown as User,
    });

    const authenticated = useAuthenticated();
    const users = useQueryUsers(searchField.fieldQuery, false);

    // Hooks

    useEffect(() => {
        if (users.isError) {
            toast.error("An error occurred while fetching users");
            Router.push("/dashboard");
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, users.isError]);

    // Page

    return (
        <Layout>

            <Head>
                <title>PhiTag: User Overview</title>
            </Head>
            <ContentLayout>
                <div className='flex flex-col w-full'>

                    <div className="flex flex-col md:flex-row md:items-center md:space-x-6">


                        <div className="flex font-dm-mono-medium font-bold text-2xl">
                            Human Annotators
                        </div>

                        <div className="flex flex-row w-full ">

                            <div className="flex flex-row w-full basis-full items-center border-b-2 py-2 px-3 my-4">
                                <input
                                    className="pr-3 flex flex-auto outline-none border-none font-dm-sans-medium font-bold"
                                    type={"text"}
                                    placeholder="Search Term"
                                    value={searchField.fieldQuery}
                                    onChange={(e) => setSearchField({
                                        ...searchField,
                                        fieldQuery: e.target.value,
                                    })} />
                                <FiSearch className='basic-svg' />
                            </div>
                            <div className="hidden xl:visible xl:flex items-center my-4 ml-4">
                                {searchField.grid ?
                                    <button className="flex flex-auto font-dm-mono-medium font-black" onClick={() => setSearchField({
                                        ...searchField,
                                        grid: false,
                                    })}>
                                        <FiGrid className='basic-svg' />
                                    </button>
                                    :
                                    <button className="flex flex-auto font-dm-mono-medium font-black" onClick={() => setSearchField({
                                        ...searchField,
                                        grid: true,
                                    })}>
                                        <FiList className='basic-svg' />
                                    </button>}
                            </div>

                            <div className="flex items-center my-4 ml-4">
                                <HelpButton
                                    title="Help: Human Annotators"
                                    tooltip="Help: Human Annotators"
                                    text="
                                        Here you can find all human annotators that are currently registered in the system.
                                        You can add them to your project by clicking on the plus icon on the user card.
                                        You can also search for specific users by typing in the search field.
                                    "
                                    reference=""
                                    linkage={false}
                                />
                            </div>
                        </div>
                    </div>

                    {
                        users.isLoading ? <LoadingComponent /> :
                            <div className={searchField.grid ? "user-grid" : "user-list"}>
                                {

                                    users.users.map((user) => (
                                        <UserCard
                                            key={user.getUsername()}
                                            grid={searchField.grid}
                                            user={user}
                                            onClick={() => setModalState({
                                                isOpen: true,
                                                user: user,
                                            })} />
                                    ))
                                }
                            </div>
                    }
                </div>
            </ContentLayout>

            <AddUserToProjectModal
                isOpen={modalState.isOpen}
                closeModalCallback={() => setModalState({
                    isOpen: false,
                    user: null as unknown as User,
                })}
                user={modalState.user} mutateCallback={() => {}} />
        </Layout>
    );
};

export default HumanPool;
