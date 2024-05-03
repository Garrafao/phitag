import { useEffect, useState } from "react";

import { FiArrowLeft, FiArrowRight, FiEdit } from "react-icons/fi";

// services
import { useFetchUsages, useFetchUsagesWithPagination } from "../../../lib/service/phitagdata/PhitagDataResource";

// model
import Usage from "../../../lib/model/phitagdata/usage/model/Usage";
import Project from "../../../lib/model/project/model/Project";

// components
import LoadingComponent from "../../generic/loadingcomponent";
import AddDataToProjectModal from "../modal/adddatatoprojectmodal";
import EditUsageModal from "../modal/editusagemodal";
import IconButtonOnClick from "../../generic/button/iconbuttononclick";


const UsageTable: React.FC<{ project: Project, hideEdit: boolean, modalState: { open: boolean, callback: Function } }> = ({ project, hideEdit, modalState }) => {

    const [page, setPage] = useState(0);

    const usages = useFetchUsagesWithPagination(project?.getId().getOwner(), project?.getId().getName(), page, !!project);

    const [editModal, setEditModal] = useState({
        open: false,
        usage: null as unknown as Usage,
    });

    // Reload the data on reload
    useEffect(() => {
        usages.mutate();
    }, [project]);

    if (!project || usages.isLoading || usages.isError) {
        return <LoadingComponent />;
    }

    return (
        <div>

            <div className="flex flex-col font-dm-mono-medium">
                <div className="overflow-auto">
                    <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                        <thead className="font-bold text-lg">
                            <tr>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Data ID
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider ">
                                    Context
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider">
                                    Lemma
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider">
                                    Group
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider">
                                    Edit
                                </th>
                            </tr>
                        </thead>
                        <tbody className=" text-base16-gray-700">
                            {usages.data.getContent().map((usage, i) => (
                                <tr key={usage.getId().getDataid()}>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {usage.getId().getDataid()}
                                    </td>

                                    {/* TODO: ALL LEMMAS */}
                                    <td className="px-6 py-4 overflow-auto font-dm-mono-light">
                                        {usageContextBuilder(usage).map((sentence, index) => {
                                            return (
                                                <span
                                                    key={usage.getId().getDataid() + index}
                                                    className={sentence.highlight === "bold" ? "font-dm-mono-medium" : sentence.highlight === "color" ? "inline font-dm-sans-bold text-lg text-base16-green" : ""}>
                                                    {sentence.sentence}
                                                </span>
                                            );
                                        })}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {usage.getLemma()}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {usage.getGroup()}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        <IconButtonOnClick onClick={() => {
                                            setEditModal({
                                                open: true,
                                                usage: usage,
                                            });
                                        }}
                                            icon={<FiEdit className="basic-svg" />}
                                            tooltip="Edit Usage"
                                            hide={hideEdit}
                                        />
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <Pagination page={page} pageChangeCallback={(page: number) => {setPage(page)}} maxPage={usages.data.getTotalPages()} />

            <AddDataToProjectModal isOpen={modalState.open} closeModalCallback={modalState.callback} project={project} mutateCallback={usages.mutate} />
            <EditUsageModal isOpen={editModal.open} closeModalCallback={() => setEditModal({ open: false, usage: null as unknown as Usage })} usage={editModal.usage} mutateCallback={usages.mutate} />
        </div>
    );

}

export default UsageTable;


/**
 * Constructs a context array with Pairs of the form {sentence: string, highlight: "none" | "bold" | "color"}
 * 
 * @param usage usage to construct the context from
 */
function usageContextBuilder(usage: Usage): { sentence: string, highlight: "none" | "bold" | "color" }[] {

    const context = usage.getContext();

    const contextArray: { sentence: string, highlight: "none" | "bold" | "color" }[] = [];

    // Add the first sentence
    contextArray.push({
        sentence: context.substring(0, usage.getIndexTargetSentenceStart()),
        highlight: "none"
    });

    let lastTargetTokenEnd = 0;

    usage.getIndexTargetSentence().forEach((sentence, index) => {
        lastTargetTokenEnd = sentence.left;
        usage.getIndexTargetToken().forEach((token, index) => {
            if (token.left >= sentence.left && token.right <= sentence.right) {
                // Add the sentence till the target token
                contextArray.push({
                    sentence: context.substring(lastTargetTokenEnd, token.left),
                    highlight: "bold"
                });
                // Add the target token
                contextArray.push({
                    sentence: context.substring(token.left, token.right),
                    highlight: "color"
                });
                lastTargetTokenEnd = token.right;
            }
        });
        // Add the sentence after the target token
        contextArray.push({
            sentence: context.substring(lastTargetTokenEnd, sentence.right),
            highlight: "bold"
        });
    });

    // Add the last sentence
    contextArray.push({
        sentence: context.substring(usage.getIndexTargetSentenceEnd()),
        highlight: "none"
    });

    return contextArray;

}

const Pagination: React.FC<{
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
