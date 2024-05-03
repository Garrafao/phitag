// components
import LoadingComponent from "../loadingcomponent";
import CorpusText from "../../../lib/model/corpus/model/CorpusText";
import { useState } from "react";
import { addUsagesFromCorpusToProject, useFetchByQuery, useFetchCorpusnamesShort, useFetchPoSOfLemma, useFetchPossibleLemma, useFetchPossibleToken } from "../../../lib/service/corpus/CorpusResource";
import { FiArrowLeft, FiArrowRight, FiCalendar, FiCheckSquare, FiFolder, FiLink2, FiPlus, FiSearch, FiSliders, FiSquare } from "react-icons/fi";
import IconButtonOnClick from "../button/iconbuttononclick";
import Checkbox from "../checkbox/checkbox";
import Project from "../../../lib/model/project/model/Project";
import AddUsagesFromCorpusCommand from "../../../lib/model/corpus/command/AddUsagesFromCorpusCommand";
import { toast } from "react-toastify";
import DropdownSelect from "../dropdown/dropdownselect";
import { useFetchPersonalProject } from "../../../lib/service/project/ProjectResource";
import useStorage from "../../../lib/hook/useStorage";
import HelpButton from "../button/helpbutton";


const CorpusTable: React.FC<{}> = ({ }) => {

    // State and Data
    const [searchField, setSearchField] = useState({
        open: false,

        lemma: "",
        pos: "",
        corpus: "",
        from: 0,
        to: 2023,

        context: false,
        normalized: false,

        page: 0,
        size: 10,

        selected: [] as string[],
        addModal: false,
    });

    const selectCallback = (id: string) => {
        if (searchField.selected.includes(id)) {
            setSearchField({ ...searchField, selected: searchField.selected.filter(e => e !== id) });
        } else {
            setSearchField({ ...searchField, selected: [...searchField.selected, id] });
        }
    }

    // Fetch Data
    const lemmaSelection = useFetchPossibleLemma(searchField.lemma, searchField.lemma.length > 2);
    const posSelection = useFetchPoSOfLemma(searchField.lemma, searchField.lemma.length > 2);
    const possibletokens = useFetchPossibleToken(searchField.lemma, searchField.lemma.length > 2);
    const possibleCorpus = useFetchCorpusnamesShort();
    const corpusresults = useFetchByQuery(searchField.lemma, searchField.pos, searchField.corpus, searchField.context, searchField.from, searchField.to, searchField.page, searchField.size, searchField.lemma.length > 2);

    return (

        <div className='flex flex-col w-full'>
            <div className="flex flex-col md:flex-row md:items-center md:space-x-6">
                <div className="flex font-dm-mono-medium font-bold text-2xl">
                    Corpus
                </div>

                <CorpusTableSearch search={searchField.lemma} changeCallback={(e: string) => setSearchField({ ...searchField, lemma: e, page: 0, selected: [] })} possibleLemmas={lemmaSelection.data} />
                <IconButtonOnClick icon={<FiSliders className="basic-svg" />} tooltip="Expand Search" onClick={() => setSearchField({ ...searchField, open: true })} />
                <HelpButton
                    title="Help: Corpus"
                    tooltip="Help: Corpus"
                    text="The corpus is a collection of texts. You can search for a specific lemma and filter the results by PoS and year. You can also search for a specific token. The corpus is not editable. You can add the results to your project as usages."
                    reference="/guide/how-to-corpus"
                    linkage={true}
                />
            </div>

            <CorpusTableEntries texts={corpusresults.data?.getContent() || []} context={searchField.context} normalized={searchField.normalized} tokens={possibletokens.data} selected={searchField.selected} selectCallback={selectCallback} />

            <CorpusPagination page={searchField.page} pageChangeCallback={(e: number) => setSearchField({ ...searchField, page: e })} maxPage={corpusresults.data?.getTotalPages() || 1} />
            <CorpusTableExpandSearchModal
                open={searchField.open}
                openChangeCallback={(e: boolean) => setSearchField({ ...searchField, open: e })}
                lemma={searchField.lemma}
                lemmaChangeCallback={(e: string) => setSearchField({ ...searchField, lemma: e, pos: "", corpus: "", page: 0, selected: [] })} lemmaSelection={lemmaSelection.data}

                pos={searchField.pos} posChangeCallback={(e: string) => setSearchField({ ...searchField, pos: e, page: 0 })} posSelection={posSelection.data}
                
                corpus={searchField.corpus} 
                corpusChangeCallback={(e: string) => setSearchField({ ...searchField, corpus: e, page: 0 })} corpusSelection={possibleCorpus.data}

                from={searchField.from} fromChangeCallback={(e: number) => setSearchField({ ...searchField, from: e || 0 })}
                to={searchField.to} toChangeCallback={(e: number) => setSearchField({ ...searchField, to: e || 0 })}

                context={searchField.context} contextChangeCallback={(e: boolean) => setSearchField({ ...searchField, context: e })}
                normalized={searchField.normalized} normalizedChangeCallback={(e: boolean) => setSearchField({ ...searchField, normalized: e })}
            />

            <AddUsageFromCorpusModal
                open={searchField.addModal}
                closeCallback={() => setSearchField({ ...searchField, addModal: false })}
                ids={searchField.selected}
                lemma={searchField.lemma}
                pos={searchField.pos}
            />

            <div className="flex w-16 h-16 justify-center items-center rounded-full absolute bottom-10 right-10 shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200"
                onClick={() => setSearchField({ ...searchField, addModal: true })}>
                <FiPlus className="h-8 w-8" />
            </div>


        </div>
    )

}

const CorpusTableEntries: React.FC<{ texts: CorpusText[], context: boolean, normalized: boolean, tokens: string[], selected: string[], selectCallback: (arg0: string) => void }> = ({ texts, context, normalized, tokens, selected, selectCallback }) => {

    if (!texts) {
        return <LoadingComponent />;
    }

    if (texts.length === 0) {
        return (
            <div className="flex mt-20 justify-center font-dm-mono-medium font-bold text-2xl">
                No Results
            </div>
        )
    }

    return (
        <div className="flex flex-col font-dm-mono-medium">
            <div className="overflow-auto">
                <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                    <thead className="font-bold text-lg">
                        <tr>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Text {normalized ? "Normalized" : ""}
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Title
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Author
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Language
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Year
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Corpus
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Resource
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Selected
                            </th>


                        </tr>
                    </thead>
                    <tbody className=" text-base16-gray-700">
                        {texts.map((text, i) => (
                            <tr key={i}>
                                <td className="px-6 py-4 break-words">
                                    {sentenceBuilder(text, normalized, tokens).map((sentence, index) => {
                                        return (
                                            <span
                                                key={text.getId() + index}
                                                className={sentence.highlight === "bold" ? "font-dm-mono-medium" : sentence.highlight === "color" ? "inline font-dm-sans-bold text-lg text-base16-green" : "font-dm-mono-light"}>
                                                {sentence.sentence}
                                            </span>
                                        );
                                    })}
                                </td>

                                <td className="px-6 py-4 break-words font-dm-mono-light">
                                    {text.getInformation().getTitle()}
                                </td>
                                <td className="px-6 py-4 break-words">
                                    {text.getInformation().getAuthor()}
                                </td>
                                <td className="px-6 py-4 break-words">
                                    {text.getInformation().getLanguage()}
                                </td>
                                <td className="px-6 py-4 break-words">
                                    {text.getInformation().getDate()}
                                </td>

                                <td className="px-6 py-4 break-words">
                                    <div className="tooltip-resource group flex justify-center">
                                        {text.getInformation().getCorpusnameShort()}
                                        <div className="tooltip-resource-container group-hover:scale-100">
                                            {text.getInformation().getCorpusnameFull()}
                                        </div>
                                    </div>
                                </td>

                                <td className="px-6 py-4">
                                    <a className="tooltip-resource group flex justify-center" href={text.getInformation().getResource()} target="_blank" rel="noreferrer">
                                        <FiLink2 className='basic-svg' />
                                        <div className="tooltip-resource-container group-hover:scale-100">
                                            {text.getInformation().getResource()}
                                        </div>
                                    </a>
                                </td>

                                <td className="px-6 py-4">
                                    <div className="flex justify-center" onClick={() => selectCallback(text.getId())}>
                                        {selected.includes(text.getId()) ? <FiCheckSquare className='basic-svg' /> : <FiSquare className='basic-svg' />}
                                    </div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

const CorpusTableSearch: React.FC<{ search: string, changeCallback: Function, possibleLemmas: string[] }> = ({ search, changeCallback, possibleLemmas }) => {

    return (
        <div className="w-full dropdown-search group font-dm-mono-medium">
            <div className="flex flex-row w-full basis-full items-center border-b-2 py-2 px-3 my-4">
                <input className="pr-3 flex flex-auto outline-none border-none font-dm-sans-medium font-bold"
                    type={"text"}
                    placeholder="Search Term"
                    value={search}
                    onChange={(e) => changeCallback(e.target.value)} />
                <FiSearch className='basic-svg' />
            </div>


            <div className="dropdown-search-container group-hover:scale-100">
                <ul>
                    {possibleLemmas.map((item: string) => {
                        return (
                            <li className="py-1 px-4 cursor-pointer hover:bg-base16-gray-100" key={item} onClick={() => changeCallback(item)}>
                                &gt; {item}
                            </li>
                        )
                    })}
                </ul>
            </div>
        </div>
    );


}

const CorpusTableExpandSearchModal: React.FC<{
    open: boolean,
    openChangeCallback: (arg0: boolean) => void,

    lemma: string,
    lemmaChangeCallback: (arg0: string) => void,
    lemmaSelection: string[]

    pos: string,
    posChangeCallback: (arg0: string) => void,
    posSelection: string[],

    corpus: string,
    corpusChangeCallback: (arg0: string) => void,
    corpusSelection: string[],

    from: number,
    fromChangeCallback: (arg0: number) => void,
    to: number,
    toChangeCallback: (arg0: number) => void,

    context: boolean,
    contextChangeCallback: (arg0: boolean) => void,
    normalized: boolean,
    normalizedChangeCallback: (arg0: boolean) => void,


}> = ({
    open, openChangeCallback,
    lemma, lemmaChangeCallback, lemmaSelection,
    pos, posChangeCallback, posSelection,
    corpus, corpusChangeCallback, corpusSelection,
    from, fromChangeCallback, to, toChangeCallback,

    context, contextChangeCallback, normalized, normalizedChangeCallback
}) => {

        if (!open) {
            return null;
        }

        return (
            <div className="relative z-10 font-dm-mono-medium" onClick={() => openChangeCallback(false)}>
                <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

                <div className="fixed z-10 inset-0 overflow-y-auto">
                    <div className="flex items-center justify-center min-h-full">
                        <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                            <div className="mx-4">
                                <div className="flex flex-col items-left mt-6">
                                    <div className="font-black text-xl">
                                        Expand Search
                                    </div>
                                    <div className="font-dm-mono-regular my-2">
                                        Expand your search by adding additional filters.
                                    </div>

                                    <div className="flex flex-col items-left mb-3">
                                        <div className="font-bold text-lg -mb-4">
                                            Lemma
                                        </div>
                                        <CorpusTableSearch search={lemma} changeCallback={(e: string) => lemmaChangeCallback(e)} possibleLemmas={lemmaSelection} />
                                    </div>

                                    <div className="flex flex-col items-left mb-3">
                                        <div className="font-bold text-lg -mb-4">
                                            PoS Tag
                                        </div>
                                        <CorpusTableSearch search={pos} changeCallback={(e: string) => posChangeCallback(e)} possibleLemmas={posSelection} />
                                    </div>

                                    <div className="flex flex-col items-left mb-3">
                                        <div className="font-bold text-lg -mb-4">
                                            Corpus
                                        </div>
                                        <CorpusTableSearch search={corpus} changeCallback={(e: string) => corpusChangeCallback(e)} possibleLemmas={corpusSelection} />
                                    </div>

                                    <div className="flex flex-col items-left mb-3">
                                        <div className="font-bold text-lg">
                                            Date Range
                                        </div>

                                        <div className="flex flex-col justify-between">
                                            <div className="flex items-center justify-between">
                                                From:
                                                <div className="flex flex-row w-64 items-center border-b-2 py-2 px-3 ">
                                                    <input className="pr-3 flex grow outline-none border-none font-dm-sans-medium font-bold"
                                                        type={"text"}
                                                        placeholder="From"
                                                        value={from}
                                                        onChange={(e) => fromChangeCallback(parseInt(e.target.value))} />
                                                    <FiCalendar className='basic-svg' />
                                                </div>
                                            </div>
                                            <div className="flex items-center justify-between">
                                                To:
                                                <div className="flex flex-row w-64 items-center border-b-2 py-2 px-3">
                                                    <input className="pr-3 flex outline-none border-none font-dm-sans-medium font-bold"
                                                        type={"text"}
                                                        placeholder="To"
                                                        value={to}
                                                        onChange={(e) => toChangeCallback(parseInt(e.target.value))} />
                                                    <FiCalendar className='basic-svg' />
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div className="flex w-full items-center mb-2">
                                        <Checkbox
                                            selected={context}
                                            description={"Include Context"}
                                            onClick={() => contextChangeCallback(!context)} />
                                    </div>
                                    <div className="flex w-full items-center mb-2">
                                        <Checkbox
                                            selected={normalized}
                                            description={"Show Normalized Form"}
                                            onClick={() => normalizedChangeCallback(!normalized)} />
                                    </div>


                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        )
    }

const CorpusPagination: React.FC<{
    page: number,
    pageChangeCallback: (arg0: number) => void,
    maxPage: number,
}> = ({
    page, pageChangeCallback, maxPage
}) => {

        return (
            <div className="flex flex-row justify-between mt-8">
                <div className="flex items-center" onClick={() => {
                    if (page === 0) {
                        pageChangeCallback(maxPage - 1);
                    } else {
                        pageChangeCallback(page - 1);
                    }
                }}>
                    <div className="shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200">
                        <div className="m-6">
                            <FiArrowLeft className="h-8 w-8" />
                        </div>
                    </div>
                </div>

                <div className="flex items-center">
                    <div className="shadow-md cursor-pointer">
                        <div className="m-6">
                            <div className="font-dm-mono-regular text-base16-gray-900">
                                {page + 1} / {maxPage}
                            </div>
                        </div>
                    </div>
                </div>

                <div className="flex items-center" onClick={() => {
                    if (page === maxPage - 1) {
                        pageChangeCallback(0);
                    } else {
                        pageChangeCallback(page + 1);
                    }
                }}>
                    <div className="shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200">
                        <div className="m-6">
                            <FiArrowRight className="h-8 w-8" />
                        </div>
                    </div>
                </div>
            </div>
        )
    }

const AddUsageFromCorpusModal: React.FC<{
    open: boolean,
    closeCallback: () => void,

    ids: string[],
    lemma: string,
    pos: string,
}> = ({
    open, closeCallback, ids, lemma, pos
}) => {

        const projects = useFetchPersonalProject("", open);
        const storage = useStorage();

        const [modalState, setModalState] = useState({
            project: null as unknown as Project,

            includeContext: true,
            normalized: true,
        })

        const onConfirm = () => {
            if (modalState.project === null) {
                toast.info("Please select a project to add the usages to.");
                return;
            }
            let command = new AddUsagesFromCorpusCommand(
                modalState.project.getId().getName(),
                ids,
                lemma,
                pos,
                modalState.includeContext,
                modalState.normalized
            )

            addUsagesFromCorpusToProject(command, storage.get)
                .then(() => {
                    toast.success("Successfully added usages to project.");
                    setModalState({
                        project: null as unknown as Project,
                        includeContext: true,
                        normalized: true,
                    })
                    closeCallback();
                })
                .catch(() => {
                    toast.error("Failed to add usages to project.");
                })
        }

        const onCancel = () => {
            toast.info("Cancelled adding usages from corpus.");
            setModalState({
                project: null as unknown as Project,
                includeContext: true,
                normalized: true,
            })
            closeCallback();
        }

        if (!open) {
            return null;
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
                                        Add Usages from Corpus
                                    </div>
                                    <div className="font-dm-mono-regular my-2">
                                        You are about to add {ids.length} usages to a project.
                                    </div>

                                    <div className="flex flex-col items-left my-6">
                                        <div className="font-bold text-lg">
                                            Project
                                        </div>
                                        <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                            <DropdownSelect
                                                icon={<FiFolder className="basic-svg" />}
                                                items={projects.projects}
                                                selected={modalState.project ? [modalState.project] : []}
                                                onSelectFunction={(project: Project) => setModalState({
                                                    ...modalState,
                                                    project: project
                                                })}
                                                message={modalState.project ? modalState.project.getDisplayname() : "No Project Selected"}
                                            />
                                        </div>
                                    </div>





                                    <div className="flex w-full items-center mb-2">
                                        <Checkbox
                                            selected={modalState.includeContext}
                                            description={"Include Context"}
                                            onClick={() => setModalState({ ...modalState, includeContext: !modalState.includeContext })} />
                                    </div>
                                    <div className="flex w-full items-center mb-2">
                                        <Checkbox
                                            selected={modalState.normalized}
                                            description={"Show Normalized Form"}
                                            onClick={() => setModalState({ ...modalState, normalized: !modalState.normalized })} />
                                    </div>

                                </div>
                            </div>
                            <div className="flex flex-row divide-x-8">
                                <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={() => onCancel()}>Cancel</button>
                                <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onConfirm}>Confirm</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }





function sentenceBuilder(text: CorpusText, normalized: boolean, tokens: string[]): { sentence: string, highlight: "none" | "bold" | "color" }[] {

    const sentenceArray: { sentence: string, highlight: "none" | "bold" | "color" }[] = [];

    // Add the first sentence
    sentenceArray.push({
        sentence: (normalized ? text.getPrevious().getOrthography() : text.getPrevious().getText()) + " ",
        highlight: "none"
    });

    let target = normalized ? text.getOrthography() : text.getText();

    target.split(" ").forEach((token, index) => {
        if (tokens.includes(token)) {
            sentenceArray.push({
                sentence: token + " ",
                highlight: "color"
            });
        } else {
            sentenceArray.push({
                sentence: token + " ",
                highlight: "bold"
            });
        };
    });

    // Add the last sentence
    sentenceArray.push({
        sentence: normalized ? text.getNext().getOrthography() : text.getNext().getText(),
        highlight: "none"
    });

    return sentenceArray;

}

export default CorpusTable;