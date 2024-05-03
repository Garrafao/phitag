import Router, { useRouter } from "next/router";
import useAuthenticated from "../../../../lib/hook/useAuthenticated";
import { useEffect, useState } from "react";
import { createDictionaryEntry, updateDictionaryEntry, deleteDictionaryEntry, useFetchDictionaryEntries } from "../../../../lib/service/dictionary/DictionaryEntryResource";
import { toast } from "react-toastify";
import Layout from "../../../../components/generic/layout/layout";
import Head from "next/head";
import { FiCheck, FiDownload, FiEdit2, FiFile, FiPlus, FiSearch, FiTrash, FiX } from "react-icons/fi";
import useStorage from "../../../../lib/hook/useStorage";
import HelpButton from "../../../../components/generic/button/helpbutton";
import DictionaryEntry from "../../../../lib/model/dictionary/entry/model/DictionaryEntry";
import DictionaryEntrySense from "../../../../lib/model/dictionary/sense/model/DictionaryEntrySense";
import { createDictionaryEntrySense, updateDictionaryEntrySense, deleteDictionaryEntrySense } from "../../../../lib/service/dictionary/DictionaryEntrySenseResource";
import DictionaryEntrySenseExample from "../../../../lib/model/dictionary/example/model/DictionaryEntrySenseExample";
import { createDictionaryEntrySenseExample, deleteDictionaryEntrySenseExample, updateDictionaryEntrySenseExample } from "../../../../lib/service/dictionary/DictionaryEntrySenseExampleResource";
import IconButtonOnClick from "../../../../components/generic/button/iconbuttononclick";
import { exportDictionary } from "../../../../lib/service/dictionary/DictionaryResource";
import DummySelectable from "../../../../lib/model/dummy/DummySelectable";
import DropdownSelect from "../../../../components/generic/dropdown/dropdownselect";
import PageChange from "../../../../components/generic/table/pagination";

const DictionaryPage = () => {

    const { get } = useStorage();

    // Data & Hooks
    const authenticated = useAuthenticated();
    const router = useRouter();
    const { user: uname, dname } = router.query as { user: string, dname: string };

    const [searchField, setSearchField] = useState({
        headword: "",
        page: 0,
        addModal: false,
        exportModal: false
    });

    const dictionaries = useFetchDictionaryEntries(dname, uname, searchField.headword, "", searchField.page, router.isReady);

    useEffect(() => {
        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated]);

    return (
        <Layout>

            <Head>
                <title>PhiTag: Dictionary</title>
            </Head>

            <div className="flex flex-col m-auto w-full xl:w-4/5 xl:py-16 px-16 justify-center items-center shadow-md my-8">

                <div className="flex flex-row w-full justify-center items-center mb-4">
                    <div className="flex font-dm-mono-medium font-bold text-2xl pr-4">
                        {dname}
                    </div>

                    <div className="flex flex-row w-full ">

                        <div className="flex flex-row w-full basis-full items-center border-b-2 py-2 px-3 my-4">
                            <input
                                className="pr-3 flex flex-auto outline-none border-none font-dm-sans-medium font-bold"
                                placeholder="Search headword"
                                type={"text"}
                                value={searchField.headword}
                                onChange={(e) => setSearchField({
                                    ...searchField,
                                    headword: e.target.value,
                                    page: 0
                                })} />
                            <FiSearch className='basic-svg' />
                        </div>

                        <div className="flex items-center my-4 ml-4">
                            <HelpButton
                                title="Help: Dictionary"
                                tooltip="Help: Dictionary"
                                text="This is the dictionary page. Here you can view and search all the entries in the dictionary. You can also create new entries by clicking the plus button in the bottom right corner."
                                reference="/guide/how-to-dictionary"
                            />
                        </div>


                        <div className="flex items-center my-4 ml-4">
                            <IconButtonOnClick
                                icon={<FiDownload className="basic-svg " />}
                                tooltip="Download"
                                onClick={() => setSearchField({ ...searchField, exportModal: true })}
                            />
                        </div>

                    </div>
                </div>

                <DictionaryView entries={dictionaries.data?.content || []} mutateCallback={() => dictionaries.mutate()} wordcallback={(word) => setSearchField({ ...searchField, headword: word })} />

                <div className="flex flex-row w-full justify-center items-center">
                    <PageChange page={dictionaries.data?.page || searchField.page} maxPage={dictionaries.data?.totalPages || 0} pageChangeCallback={(page) => setSearchField({ ...searchField, page: page })} />
                </div>

            </div>



            <div className="flex w-16 h-16 justify-center items-center rounded-full absolute bottom-10 right-10 shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200"
                onClick={() => setSearchField({ ...searchField, addModal: true })}>
                <FiPlus className="h-8 w-8" />
            </div>

            {searchField.addModal && (
                <CreateDictionaryEntryModal closeCallback={() => setSearchField({ ...searchField, addModal: false })} mutateCallback={() => dictionaries.mutate()} />
            )}

            {searchField.exportModal && (
                <ExportDictionaryModal closeCallback={() => setSearchField({ ...searchField, exportModal: false })} />
            )}

        </Layout>
    );
}

export default DictionaryPage;

const DictionaryView = ({ entries, wordcallback, mutateCallback }: { entries: DictionaryEntry[], wordcallback: (word: string) => void, mutateCallback: () => void }) => {

    const [selectedEntry, setSelectedEntry] = useState<string>("");

    return (
        <div className="flex flex-row w-full p-8">
            {/* Entries list */}
            <div className="w-1/5">
                <DictionaryEntriesListView entries={entries} onClick={(entry) => setSelectedEntry(entry)} selectedEntry={selectedEntry} />
            </div>

            {/* splitter */}
            <div className="border-r-2 mx-8"></div>

            {/* Full entry view */}
            <div className="flex flex-col w-full">
                <DictionaryEntryFullView entry={entries.find((entry) => entry.id.id === selectedEntry) ?? null} mutateCallback={mutateCallback} wordcallback={wordcallback} />
            </div>

        </div>
    )
}

const DictionaryEntriesListView = ({ entries, selectedEntry, onClick }: { entries: DictionaryEntry[], selectedEntry: string, onClick: (entry: any) => void }) => {

    // This presents the entries in a list view with headword and it is possible to click on the entry to view it in full.

    return (
        <div className="flex flex-col">
            {entries.map((entry) => (
                <div key={entry.id.id} onClick={() => onClick(entry.id.id)}
                    className={`flex border-b-2 hover:border-base16-green transition-all duration-700 cursor-pointer py-2 px-3 my-4 ` + (entry.id.id === selectedEntry ? " border-base16-gray-900" : "")}>
                    <div className="flex flex-auto font-dm-mono-medium font-bold text-xl">
                        {entry.headword}
                        <span className="font-dm-mono-regular font-bold text-base ml-4 text-base16-gray-500 self-center">
                            {entry.partofspeech}
                        </span>
                    </div>
                </div>
            ))}
        </div>
    )


}

const DictionaryEntryFullView = ({ entry, wordcallback, mutateCallback }: { entry: DictionaryEntry | null, wordcallback: (word: string) => void, mutateCallback: () => void }) => {

    const [options, setOptions] = useState<boolean>(false);

    // This presents the entry in full with all the information.
    if (!entry) {
        return (
            <div className="flex flex-col h-full">
                <div className="flex m-auto justify-center items-center font-dm-mono-medium font-bold text-2xl">
                    Select an entry to view it in full.
                </div>
            </div>
        )
    }

    return (
        <div key={entry.id.id} className="flex flex-col h-full">
            <DictionaryEntryHeaderView entry={entry} openOptionsCallback={() => setOptions(true)} mutateCallback={mutateCallback} />
            <DictionaryEntryBodyView entry={entry} newSense={options} closeNewSenseCallback={() => setOptions(false)} mutateCallback={mutateCallback} wordcallback={wordcallback} />
        </div>
    )
}

const DictionaryEntryHeaderView = ({ entry, openOptionsCallback, mutateCallback }: { entry: DictionaryEntry, openOptionsCallback: () => void, mutateCallback: () => void }) => {

    const { get } = useStorage();

    const [edit, setEdit] = useState<boolean>(false);
    const [newentry, setNewentry] = useState(entry.shallowAnnonymizedCopy());

    const onSubmit = () => {
        updateDictionaryEntry(newentry.id.id, newentry.id.dname, newentry.id.uname, newentry.headword, newentry.partofspeech, get)
            .then(() => {
                setEdit(false);
                mutateCallback();
                toast.success("Entry updated successfully!");
            })
            .catch((err) => {
                toast.error("Failed to update entry!");
            })
    }


    const onDelete = () => {
        deleteDictionaryEntry(entry.id.id, entry.id.dname, entry.id.uname, get)
            .then(() => {
                toast.success("Entry deleted successfully.");
                mutateCallback();
            })
            .catch((err) => {
                toast.error("Failed to delete entry.");
                console.error(err);
            })
    }

    const openEdit = () => {
        setNewentry(entry.shallowAnnonymizedCopy());
        setEdit(true);
    }

    if (edit) {
        return (
            <div className="flex flex-row items-center justify-between">
                <div className="flex items-end">
                    <input
                        className="outline-none border-b-2 font-dm-mono-medium font-black text-4xl"
                        placeholder="Headword"
                        type={"text"}
                        value={newentry.headword}
                        onChange={(e) => setNewentry({
                            ...newentry,
                            headword: e.target.value
                        })
                        } />
                    <input
                        className="outline-none border-b-2 font-dm-mono-regular font-bold text-2xl ml-4 text-base16-gray-500"
                        placeholder="Part of speech"
                        type={"text"}
                        value={newentry.partofspeech || entry.partofspeech}
                        onChange={(e) => setNewentry({
                            ...newentry,
                            partofspeech: e.target.value
                        })
                        } />
                </div>

                <div className="flex flex-row space-x-4">
                    <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                        onClick={() => onSubmit()}>
                        <FiCheck className="h-4 w-4" />
                    </div>

                    <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                        onClick={() => setEdit(false)}>
                        <FiX className="h-4 w-4" />
                    </div>
                </div>

            </div>
        )

    }

    return (
        <div className="flex flex-row items-center justify-between group">
            <div className="flex items-end">
                <div className="font-dm-mono-medium font-black text-4xl">
                    {entry.headword}
                </div>
                <span className="font-dm-mono-regular font-bold text-2xl ml-4 text-base16-gray-500">
                    {entry.partofspeech}
                </span>
            </div>

            <div className="flex flex-row space-x-4">
                <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer 
                    hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500 opacity-0 group-hover:opacity-100"
                    onClick={() => openEdit()}>
                    <FiEdit2 className="h-4 w-4" />
                </div>


                <div className="flex flex-row items-center justify-end group">
                    <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer 
                        hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500 opacity-0 group-hover:opacity-100"
                        onClick={() => openOptionsCallback()}>
                        <FiPlus className="h-4 w-4" />
                    </div>
                </div>

                <div className="mt-auto self-end flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer 
                    hover:bg-base16-red transition-all duration-500 opacity-0 group-hover:opacity-100"
                    onClick={() => onDelete()}>
                    <FiTrash className="h-4 w-4" />
                </div>
            </div>

        </div>
    )
}

const DictionaryEntryBodyView = ({ entry, wordcallback, newSense, closeNewSenseCallback, mutateCallback }: { entry: DictionaryEntry, wordcallback: (word: string) => void, newSense: boolean, closeNewSenseCallback: () => void, mutateCallback: () => void }) => {

    return (
        // Currently, the body is just a list of all senses in the entry.

        <div className="flex flex-col mt-4 ml-8 space-y-4">
            {entry.senses.map((sense, i) => (
                <DictionaryEntrySenseView key={sense.id.id} sense={sense} mutateCallback={mutateCallback} wordcallback={wordcallback} />
            ))}

            <DictionaryEntrySenseViewNew entry={entry} open={newSense} closeCallback={closeNewSenseCallback} mutateCallback={mutateCallback} />

        </div>
    )


}

const DictionaryEntrySenseView = ({ sense, wordcallback, mutateCallback }: { sense: DictionaryEntrySense, wordcallback: (word: string) => void, mutateCallback: () => void }) => {

    const { get } = useStorage();

    const [edit, setEdit] = useState(false);
    const [newExample, setNewExample] = useState(false);

    const [newsense, setNewsense] = useState(sense.shallowAnnonymizedCopy());

    const onSubmit = () => {
        updateDictionaryEntrySense(newsense.id.id, newsense.id.entryId, newsense.id.dname, newsense.id.uname, newsense.definition, newsense.order, get)
            .then(() => {
                setEdit(false);
                mutateCallback();
                toast.success("Sense updated successfully!");
            })
            .catch((err) => {
                toast.error("Failed to update sense!");
            })
    }

    const onDelete = () => {
        deleteDictionaryEntrySense(newsense.id.id, newsense.id.entryId, newsense.id.dname, newsense.id.uname, get)
            .then(() => {
                toast.success("Sense deleted successfully.");
                mutateCallback();
            })
            .catch((err) => {
                toast.error("Failed to delete sense.");
                console.error(err);
            })
    }

    const openEdit = () => {
        setNewsense(sense.shallowAnnonymizedCopy());
        setEdit(true);
    }

    return (
        <div className="flex flex-col">
            {
                edit ? (
                    <div className="flex flex-row items-center justify-between group">
                        <div className="flex items-end">
                            <input
                                className="outline-none border-b-2 font-dm-mono-regular font-bold text-md mr-2 text-base16-gray-500 flex w-8 "
                                placeholder="Order"
                                type={"text"}
                                value={newsense.order}
                                onChange={(e) => setNewsense({
                                    ...newsense,
                                    order: parseInt(e.target.value)
                                })
                                } />
                            <input
                                className="outline-none border-b-2 font-dm-mono-medium font-black text-xl"
                                placeholder="Definition"
                                type={"text"}
                                value={newsense.definition}
                                onChange={(e) => setNewsense({
                                    ...newsense,
                                    definition: e.target.value
                                })
                                } />
                        </div>

                        <div className="flex flex-row space-x-4">
                            <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                                onClick={() => onSubmit()}>
                                <FiCheck className="h-4 w-4" />
                            </div>
                            <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                                onClick={() => setEdit(false)}>
                                <FiX className="h-4 w-4" />
                            </div>
                        </div>
                    </div>
                ) : (
                    <div className="flex flex-row items-center justify-between group">
                        <div className="flex">
                            <span className="font-dm-mono-regular font-bold text-md mr-2 text-base16-gray-500 self-center">
                                {sense.order}.
                            </span>
                            <DictionarySelfReference text={sense.definition} callback={wordcallback} />

                        </div>

                        <div className="flex flex-row space-x-4">
                            <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer
                                hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500 opacity-0 group-hover:opacity-100"
                                onClick={() => openEdit()}>
                                <FiEdit2 className="h-4 w-4" />
                            </div>

                            <div className="flex flex-row items-center justify-end group">
                                <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer 
                                    hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500 opacity-0 group-hover:opacity-100"
                                    onClick={() => setNewExample(true)}>
                                    <FiPlus className="h-4 w-4" />
                                </div>
                            </div>

                            <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer
                                hover:bg-base16-red transition-all duration-500 opacity-0 group-hover:opacity-100"
                                onClick={() => onDelete()}>
                                <FiTrash className="h-4 w-4" />
                            </div>
                        </div>
                    </div>
                )
            }

            <div className="flex flex-col mt-4 ml-8 space-y-4">
                {sense.examples.map((example, i) => (
                    <DictionaryEntrySenseBodyView key={example.id.id} example={example} mutateCallback={mutateCallback} />
                ))}

                <DictionaryEntrySenseBodyViewNew sense={sense} open={newExample} closeCallback={() => setNewExample(false)} mutateCallback={mutateCallback} />

            </div>

        </div>
    );

}

const DictionarySelfReference = ({ text, callback }: { text: string, callback: (word: string) => void }) => {
    // Generates a text, where words wrapped in [[]] are replaced with the word underlined and clickable to the callback
    // Callback is called with the word as a string
    const regex = /\[\[(.*?)\]\]/g;
    const matches = text.matchAll(regex);
    let lastIndex = 0;
    let result = [];

    // @ts-ignore
    for (const match of matches) {
        const word = match[1];
        const index = match.index;

        result.push(text.substring(lastIndex, index));
        result.push(<span key={word + index} className="underline cursor-pointer" onClick={() => callback(word)}>{word}</span>);
        lastIndex = index + word.length + 4;
    }

    result.push(text.substring(lastIndex));

    return (
        <span className="font-dm-mono-medium font-black text-xl" id="definition">
            {result}
        </span>
    );
}

const DictionaryEntrySenseViewNew = ({ entry, open, closeCallback, mutateCallback }: { entry: DictionaryEntry, open: boolean, closeCallback: () => void, mutateCallback: () => void }) => {

    const { get } = useStorage();

    const [newSense, setNewSense] = useState({
        definition: "",
        order: -1,

        show: false,
    })

    const onSubmit = () => {
        createDictionaryEntrySense(entry.id.id, entry.id.dname, entry.id.uname, newSense.definition, newSense.order, get)
            .then(() => {
                toast.success("Sense created!");
                mutateCallback();
                cleanUp();
            }).catch((err) => {
                toast.error("Failed to create sense!");
            });

    }

    const onCancel = () => {
        cleanUp();
    }

    const cleanUp = () => {
        setNewSense({
            ...newSense,
            definition: "",
            order: -1,
        })
        closeCallback();
    }

    if (open) {

        return (
            <div className="flex flex-row items-center justify-between group">

                <div className="flex items-end">
                    <span className="font-dm-mono-regular font-bold text-md mr-2 text-base16-gray-500">
                        {entry.senses.length}.
                    </span>
                    <input
                        className="outline-none border-b-2 font-dm-mono-medium font-black text-xl"
                        placeholder="Definition"
                        type={"text"}
                        value={newSense.definition}
                        onChange={(e) => setNewSense({
                            ...newSense,
                            definition: e.target.value
                        })
                        } />
                </div>

                <div className="flex flex-row self-center space-x-4">
                    <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer
                        hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                        onClick={() => onSubmit()}>
                        <FiCheck className="h-4 w-4" />
                    </div>
                    <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                        onClick={() => onCancel()}>
                        <FiX className="h-4 w-4" />
                    </div>
                </div>

            </div>
        )

    }

    return (
        <div />
    )

}

const DictionaryEntrySenseBodyView = ({ example, mutateCallback }: { example: DictionaryEntrySenseExample, mutateCallback: () => void }) => {

    const { get } = useStorage();

    const [edit, setEdit] = useState(false);

    const [newExample, setNewExample] = useState(example.shallowAnnonymizedCopy());

    const onDelete = () => {
        deleteDictionaryEntrySenseExample(example.id.id, example.id.senseId, example.id.entryId, example.id.dname, example.id.uname, get)
            .then(() => {
                toast.success("Example deleted!");
                mutateCallback();
            }).catch((err) => {
                toast.error("Failed to delete example!");
            });
    }

    const onSubmit = () => {
        updateDictionaryEntrySenseExample(example.id.id, example.id.senseId, example.id.entryId, example.id.dname, example.id.uname, newExample.example, newExample.order, get)
            .then(() => {
                toast.success("Example updated!");
                mutateCallback();
                cleanUp();
            }).catch((err) => {
                toast.error("Failed to update example!");
            });
    }

    const onCancel = () => {
        cleanUp();
    }

    const cleanUp = () => {
        setEdit(false);
        setNewExample(example.shallowAnnonymizedCopy());
    }

    const onEdit = () => {
        setNewExample(example.shallowAnnonymizedCopy());
        setEdit(true);
    }

    if (edit) {
        return (
            <div className="flex flex-row items-center justify-between group">
                <div className="flex flex-row border-l-2 items-end">
                    <div className="mx-2" />
                    <input
                        className="outline-none border-b-2 font-dm-mono-regular font-bold text-md mr-2 text-base16-gray-500 flex w-8 "
                        placeholder="Order"
                        type={"text"}
                        value={newExample.order}
                        onChange={(e) => setNewExample({
                            ...newExample,
                            order: parseInt(e.target.value)
                        })
                        } />
                    <input
                        className="outline-none border-b-2 font-dm-mono-italic text-base16-gray-500"
                        placeholder="Example"
                        value={newExample.example}
                        onChange={(e) => setNewExample({
                            ...newExample,
                            example: e.target.value
                        })} />
                </div>

                <div className="flex flex-row self-center space-x-4">
                    <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer
                    hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                        onClick={() => onSubmit()}>
                        <FiCheck className="h-4 w-4" />
                    </div>
                    <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                        onClick={() => onCancel()}>
                        <FiX className="h-4 w-4" />
                    </div>
                </div>
            </div>
        )
    }

    return (

        <div className="flex flex-row items-center justify-between group">
            <div className="flex flex-row border-l-2 items-end">
                <div className="mx-2" />
                <span className="font-dm-mono-italic text-base16-gray-500">
                    {example.example}
                </span>
            </div>

            <div className="flex flex-row space-x-4">
                <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer
                    hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500 opacity-0 group-hover:opacity-100"
                    onClick={() => onEdit()}>
                    <FiEdit2 className="h-4 w-4" />
                </div>

                <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer
                    hover:bg-base16-red transition-all duration-500 opacity-0 group-hover:opacity-100"
                    onClick={() => onDelete()}>
                    <FiTrash className="h-4 w-4" />
                </div>
            </div>
        </div>
    );

}

const DictionaryEntrySenseBodyViewNew = ({ sense, open, closeCallback, mutateCallback }: { sense: DictionaryEntrySense, open: boolean, closeCallback: () => void, mutateCallback: () => void }) => {

    const { get } = useStorage();

    const [newExample, setNewExample] = useState({
        example: "",
        order: 0,
    });

    const onSubmit = () => {
        createDictionaryEntrySenseExample(sense.id.id, sense.id.entryId, sense.id.dname, sense.id.uname, newExample.example, newExample.order, get)
            .then(() => {
                toast.success("Example created!");
                mutateCallback();
                cleanUp();
            }).catch((err) => {
                toast.error("Failed to create example!");
            });
    }

    const onCancel = () => {
        cleanUp();
    }

    const cleanUp = () => {
        setNewExample({
            example: "",
            order: 0,
        });
        closeCallback();
    }

    if (open) {

        return (
            <div className="flex flex-col space-y-4">
                <div className="flex flex-row items-center group">
                    <div className="flex flex-row grow border-l-2 items-end">
                        <div className="mx-2" />
                        <input
                            className="outline-none border-b-2 font-dm-mono-italic text-base16-gray-500"
                            placeholder="New Example"
                            value={newExample.example}
                            onChange={(e) => setNewExample({
                                ...newExample,
                                example: e.target.value
                            })} />
                    </div>

                    <div className="flex flex-row self-center space-x-4">
                        <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer
                    hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                            onClick={() => onSubmit()}>
                            <FiCheck className="h-4 w-4" />
                        </div>
                        <div className="flex w-8 h-8 justify-center items-center rounded-full shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-500"
                            onClick={() => onCancel()}>
                            <FiX className="h-4 w-4" />
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div />
    );


}

const CreateDictionaryEntryModal = ({ closeCallback, mutateCallback }: { closeCallback: () => void, mutateCallback: () => void }) => {

    const { get } = useStorage();

    const router = useRouter();
    const { user: uname, dname } = router.query as { user: string, dname: string };

    const [entry, setEntry] = useState({
        headword: "",
        pos: "",
    });

    const onSubmit = () => {
        createDictionaryEntry(dname, uname, entry.headword, entry.pos, get)
            .then(() => {
                toast.success("Entry created!");
                mutateCallback();
                cleanUp();
            }).catch((err) => {
                toast.error("Failed to create dictionary!");
            });
    }

    const onCancel = () => {
        cleanUp();
    }

    const cleanUp = () => {
        setEntry({
            headword: "",
            pos: "",
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
                                    Create Dictionary Entry
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    Create a new dictionary entry.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Headword
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiEdit2 className="basic-svg" />
                                        <input
                                            id="headword"
                                            name="headword"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            type={"text"}
                                            placeholder="Headword"
                                            value={entry.headword}
                                            onChange={(e: any) => {
                                                setEntry({ ...entry, headword: e.target.value });
                                            }}
                                        />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Part of Speech
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <FiEdit2 className="basic-svg" />
                                        <input
                                            id="pos"
                                            name="Part of Speech"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            type={"text"}
                                            placeholder="Part of Speech"
                                            value={entry.pos}
                                            onChange={(e: any) => {
                                                setEntry({ ...entry, pos: e.target.value });
                                            }}
                                        />
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8">
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={() => onCancel()}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onSubmit}>Create</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

const ExportDictionaryModal = ({ closeCallback }: { closeCallback: () => void }) => {

    const { get } = useStorage();
    const router = useRouter();
    const { user: uname, dname } = router.query as { user: string, dname: string };

    const filetypes = [
        new DummySelectable("Custom-XML"),
        new DummySelectable("Wiktionary-XML-DE"),
        new DummySelectable("Wiktionary-XML-EN"),
    ];

    const [selected, setSelected] = useState(filetypes[0]);

    const onSubmit = () => {
        exportDictionary(uname, dname, selected.getName(), get)
            .then(() => {
                toast.success("Dictionary exported!");
                closeCallback();
            }).catch((err) => {
                toast.error("Failed to export dictionary!");
            });
    }

    const onCancel = () => {
        closeCallback();
    }

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                    Export Dictionary
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    Export your dictionary to a file format of your choice.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Select File Type
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <DropdownSelect
                                            icon={<FiFile className="basic-svg" />}
                                            items={filetypes}
                                            selected={[selected]}
                                            onSelectFunction={(item) => {
                                                setSelected(item);
                                            }}
                                            message={selected.getName()} />
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8">
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={() => onCancel()}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onSubmit}>Export</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
