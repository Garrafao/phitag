import Head from "next/head";
import Router from "next/router";
import { useEffect, useState } from "react"
import { FiArchive, FiArrowLeft, FiArrowRight, FiCheckCircle, FiCheckSquare, FiCircle, FiToggleLeft, FiToggleRight } from "react-icons/fi";
import { toast } from "react-toastify";
import IconButtonOnClick from "../../components/generic/button/iconbuttononclick";
import ContentLayout from "../../components/generic/layout/contentlayout";
import Layout from "../../components/generic/layout/layout";
import LoadingComponent from "../../components/generic/loadingcomponent";
import Pagination from "../../components/generic/table/pagination";
import useAuthenticated from "../../lib/hook/useAuthenticated";
import useStorage from "../../lib/hook/useStorage";
import { markAsRead, useFetchNotifications } from "../../lib/service/notification/NotificationResource"
import HelpButton from "../../components/generic/button/helpbutton";

const NotificationsPage = () => {

    const storage = useStorage();
    const authenticated = useAuthenticated();
    const [search, setSearch] = useState({
        page: 0,
        onlyunread: false,

        selected: [] as Array<number>,
    })

    const { data, isError, isLoading, mutate } = useFetchNotifications(search.page, search.onlyunread);

    const markSelectedAsRead = () => {

        if (search.selected.length === 0) {
            toast.warning("Please select at least one notification.");
            return;
        }

        markAsRead(search.selected, storage.get).then(() => {
            toast.success("Successfully added data to project.");

            // close modal
            setSearch({
                ...search,
                selected: [],
            });
            mutate();
        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while adding data: " + error.response.data.message + "!");
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        });
    }

    useEffect(() => {
        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated]);

    return (
        <Layout>

            <Head>
                <title>PhiTag: Notification</title>
            </Head>

            <ContentLayout>
                <div className='flex flex-col w-full'>

                    <div className="flex flex-col md:flex-row md:items-center md:space-x-6">


                        <div className="flex font-dm-mono-medium font-bold text-2xl">
                            <h1>Notifications</h1>
                        </div>

                        <div className="flex flex-row w-full justify-end">
                            <div className="flex items-center my-4 ml-4 font-dm-mono-medium">
                                <div className="mr-2">
                                    Unread Messages
                                </div>
                                {search.onlyunread ?
                                    <IconButtonOnClick
                                        icon={<FiToggleLeft className="basic-svg" />}
                                        tooltip={"Unread Messages"}
                                        onClick={() => setSearch({
                                            ...search,
                                            onlyunread: false
                                        })} />
                                    :
                                    <IconButtonOnClick
                                        icon={<FiToggleRight className="basic-svg" />}
                                        tooltip={"All Messages"}
                                        onClick={() => setSearch({
                                            ...search,
                                            onlyunread: true
                                        })} />
                                }
                                <div className="ml-2">
                                    All Messages
                                </div>
                            </div>

                            <div className="flex items-center my-4 ml-8">
                                <IconButtonOnClick
                                    icon={<FiCheckSquare className="basic-svg" />}
                                    tooltip={"Select All"}
                                    onClick={() => {
                                        setSearch({
                                            ...search,
                                            selected: data?.getContent().map((notification) => notification.getId()) ?? []
                                        })
                                    }} />
                            </div>

                            <div className="flex items-center my-4 ml-4">
                                <IconButtonOnClick
                                    icon={<FiArchive className="basic-svg" />}
                                    tooltip={"Mark as Read"}
                                    onClick={() => {
                                        markSelectedAsRead();
                                    }} />
                            </div>

                            <div className="flex items-center my-4 ml-4">
                                <HelpButton
                                    title="Help: Notifications"
                                    tooltip="Help: Notifications"
                                    text="Notifications are messages that are sent to you by the system. You can mark them as read or unread. You can also select multiple notifications and mark them as read at once."
                                    reference=""
                                    linkage={false}
                                />
                            </div>
                        </div>
                    </div>


                    {
                        isLoading ? <LoadingComponent /> :
                            <div className="flex flex-col space-y-2">
                                {
                                    data?.getContent().map((notification) => {
                                        return (
                                            <div key={notification.getId()} className="w-full shadow-md cursor-pointer hover:scale-[1.005] hover:transition-all duration-200">
                                                <div className="h-full grow flex flex-row p-8 xl:px-10 justify-between">
                                                    <div className="w-full flex flex-col break-words font-dm-mono-regular text-base16-gray-900">
                                                        <div className="flex flex-row justify-between">
                                                            {notification.getMessage()}
                                                            {notification.isRead() ? null : search.selected.filter((id) => id === notification.getId()).length > 0 ?
                                                                <IconButtonOnClick
                                                                    icon={<FiCheckCircle className="basic-svg" />}
                                                                    tooltip={"Selected"}
                                                                    onClick={() => {
                                                                        setSearch({
                                                                            ...search,
                                                                            selected: select(notification.getId(), search.selected)
                                                                        })
                                                                    }} />
                                                                : <IconButtonOnClick
                                                                    icon={<FiCircle className="basic-svg" />}
                                                                    tooltip={"Select"}
                                                                    onClick={() => {
                                                                        setSearch({
                                                                            ...search,
                                                                            selected: select(notification.getId(), search.selected)
                                                                        })
                                                                    }} />
                                                            }
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        );
                                    })}
                            </div>
                    }

                    <Pagination page={search.page} pageChangeCallback={(page) => setSearch({
                        ...search,
                        page: page
                    })}
                        maxPage={data?.getTotalPages() ?? 0}
                    />
                </div>
            </ContentLayout>

        </Layout>
    )
}

export default NotificationsPage;


function select(id: number, selected: Array<number>) {
    const filtered = selected.filter(i => i !== id);

    if (filtered.length === selected.length) {
        return [...selected, id];
    }
    return filtered;
}


