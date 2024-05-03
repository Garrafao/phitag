import { NextPage } from "next";
import { createDictionary, useFetchDictionaries } from "../../../../lib/service/dictionary/DictionaryResource";
import { useEffect, useState } from "react";
import useAuthenticated from "../../../../lib/hook/useAuthenticated";
import { toast } from "react-toastify";
import Router, { useRouter } from "next/router";
import Layout from "../../../../components/generic/layout/layout";
import Head from "next/head";
import ContentLayout from "../../../../components/generic/layout/contentlayout";
import LoadingComponent from "../../../../components/generic/loadingcomponent";
import Dictionary from "../../../../lib/model/dictionary/dictionary/model/Dictionary";
import { FiEdit, FiEdit2, FiFile, FiFileText, FiPlus } from "react-icons/fi";
import useStorage from "../../../../lib/hook/useStorage";
import Link from "next/link";
import DummySelectable from "../../../../lib/model/dummy/DummySelectable";
import DropdownSelect from "../../../../components/generic/dropdown/dropdownselect";

const DictionaryOverviewPage: NextPage = () => {

    // Data & Hooks
    const authenticated = useAuthenticated();
    const router = useRouter();
    const { user: uname } = router.query as { user: string };

    const [searchField, setSearchField] = useState({
        page: 0,
        addModal: false,
    });

    const dictionaries = useFetchDictionaries(uname, searchField.page, router.isReady);

    useEffect(() => {
        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated]);


    return (
        <Layout>

            <Head>
                <title>PhiTag: Dictionaries</title>
            </Head>

            <ContentLayout>
                <div className='flex flex-col w-full'>

                    <div className="flex flex-col md:flex-row md:items-center md:space-x-6">

                        <div className="flex font-dm-mono-medium font-bold text-2xl">
                            <h1>Dictionaries</h1>
                        </div>
                    </div>

                    {dictionaries.isLoading && <LoadingComponent />}

                    {!dictionaries.isLoading && dictionaries.data && (
                        <div className="flex flex-col w-full space-y-4 mt-8">
                            {dictionaries.data.content.map((dictionary: Dictionary) => (
                                <DictionaryCard key={dictionary.id.dname} dictionary={dictionary} />
                            ))}
                        </div>
                    )}

                </div>
            </ContentLayout>


            <div className="flex w-16 h-16 justify-center items-center rounded-full absolute bottom-10 right-10 shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200"
                onClick={() => setSearchField({ ...searchField, addModal: true })}>
                <FiPlus className="h-8 w-8" />
            </div>

            {searchField.addModal && (
                <CreateDictionaryModal uname={uname} closeCallback={() => setSearchField({ ...searchField, addModal: false })} mutateCallback={() => dictionaries.mutate()} />
            )}

        </Layout>

    );
}

export default DictionaryOverviewPage;

const DictionaryCard = ({ dictionary }: { dictionary: Dictionary }) => {
    return (
        <Link href={`/phi/${dictionary.id.uname}/dictionary/${dictionary.id.dname}`} passHref>
            <div className="w-full shadow-md cursor-pointer hover:scale-[1.025] hover:transition-all duration-200">
                <div className="h-full flex flex-col grow p-8 xl:px-10 break-words font-dm-mono-regular text-base16-gray-900">
                    {/* Project Name */}
                    <h1 className="font-dm-mono-medium font-bold text-2xl">
                        {dictionary.id.dname}
                    </h1>


                    <div className="mt-2 text-sm">
                        <div className="">
                            Owner: {dictionary.id.uname}
                        </div>
                    </div>

                    {/* User Bio */}
                    <p className="my-2 text-sm line-clamp-5">
                        Description: {dictionary.description ? dictionary.description : "No description"}
                    </p>

                </div>
            </div>
        </Link>
    );
}

const CreateDictionaryModal = ({ uname, closeCallback, mutateCallback }: { uname: string, closeCallback: () => void, mutateCallback: () => void }) => {

    const { get } = useStorage();

    const filetypes = [
        new DummySelectable("Custom-XML"),
        new DummySelectable("Wiktionary-XML-DE"),
        new DummySelectable("Wiktionary-XML-EN"),
    ];

    const [dictionary, setDictionary] = useState({
        dname: "",
        description: "",
        filetype: filetypes[0],
        file: null
    });

    const onSubmit = async () => {
        createDictionary(uname, dictionary.dname, dictionary.description, dictionary.filetype.getName(), dictionary.file, get)
            .then(() => {
                toast.success("Dictionary created!");
                mutateCallback();
                cleanUp();
            }).catch((err) => {
                toast.error("Failed to create dictionary!");
            });

    }

    const onCancel = () => {
        toast.info("Cancelled dictionary creation.");
        cleanUp();
    }

    const cleanUp = () => {
        setDictionary({
            dname: "",
            description: "",
            filetype: filetypes[0],
            file: null
        });
        closeCallback();
    };

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                    Create Dictionary
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You can create a dictionary by uploading a file or by creating an empty dictionary.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Dictionary Name
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiEdit2 className="basic-svg" />
                                        <input
                                            id="dname"
                                            name="dname"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            type={"text"}
                                            placeholder="Dictionary Name"
                                            value={dictionary.dname}
                                            onChange={(e: any) => {
                                                setDictionary({ ...dictionary, dname: e.target.value });
                                            }}
                                        />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Description
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiEdit2 className="basic-svg" />
                                        <input
                                            id="description"
                                            name="description"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            type={"text"}
                                            placeholder="Description"
                                            value={dictionary.description}
                                            onChange={(e: any) => {
                                                setDictionary({ ...dictionary, description: e.target.value });
                                            }}
                                        />
                                    </div>
                                </div>

                                <div className="font-dm-mono-regular my-2">
                                    This is optional. If you do not want to upload a file, you can create an empty dictionary.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Select File Type
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <DropdownSelect
                                            icon={<FiFile className="basic-svg" />}
                                            items={filetypes}
                                            selected={[dictionary.filetype]}
                                            onSelectFunction={(item) => {
                                                setDictionary({
                                                    ...dictionary,
                                                    filetype: item,
                                                });
                                            }}
                                            message={dictionary.filetype.getName()} />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        File
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiFileText className='basic-svg' />
                                        <input
                                            type="file"
                                            className="hide-upload-button pl-3 flex flex-auto outline-none border-none text-sm"
                                            onChange={(e: any) => setDictionary({
                                                ...dictionary,
                                                file: e.target.files[0],
                                            })} />
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8">
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={() => onCancel()}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onSubmit}>Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}