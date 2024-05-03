import { useState } from "react";


import Link from "next/link";

// services
import { useFetchReport } from "../../../lib/service/report/ReportResource";

import { FiArrowLeft, FiArrowRight, FiEdit } from "react-icons/fi";

// model
import Report from "../../../lib/model/report/model/Report";

// components
import LoadingComponent from "../loadingcomponent";
import EditReportModal from "../modal/editreportmodal";


const ReportTable: React.FC<{}> = ({ }) => {

    const [search, setSearch] = useState({
        user: "",
        status: "",

        page: 0,
    })

    const [modalState, setModalState] = useState({
        isOpen: false,
        report: null as unknown as Report,
    });

    const { data: reports, isError, isLoading, mutate: reportmutate } = useFetchReport(search.user, search.status, search.page);

    if (isLoading) {
        return <LoadingComponent />;
    }

    return (
        <div className="flex flex-col font-dm-mono-medium">
            <div className="overflow-auto">
                <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                    <thead className="font-bold text-lg">
                        <tr>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                ID
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                User
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Description
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider">
                                Status
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider">
                                Edit
                            </th>
                        </tr>
                    </thead>
                    <tbody className=" text-base16-gray-700">
                        {reports.getContent().map((report) => (
                            <tr key={report.getId()}>

                                <td className="px-6 py-4 whitespace-nowrap">
                                    {report.getId()}
                                </td>

                                <td className="px-6 py-4 whitespace-nowrap">
                                    <Link href={`/phi/${report.getUser()}`}>
                                        {report.getUser()}
                                    </Link>
                                </td>

                                <td className="px-6 py-4 whitespace-nowrap">
                                    {report.getDescription()}
                                </td>

                                <td className="px-6 py-4 whitespace-nowrap">
                                    <StatusDot status={report.getStatus().getName()} /> {report.getStatus().getVisiblename()}
                                </td>

                                <td className="px-6 py-4">
                                    <div className="flex justify-center" onClick={() => {
                                        setModalState({
                                            isOpen: true,
                                            report: report,
                                        })
                                    }}>
                                        <FiEdit className="text-base16-gray-700 hover:text-base16-gray-900 cursor-pointer" />
                                    </div>
                                </td>

                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <EditReportModal 
                isOpen={modalState.isOpen}
                report={modalState.report}
                closeModalCallback={() => {
                    setModalState({
                        isOpen: false,
                        report: null as unknown as Report,
                    })
                }}
                mutateCallback={reportmutate}
            />
        </div>
    );

}

export default ReportTable;

/**
 * Creates a colored dot for the given status
 * - dark green for completed
 * - light green for in progress
 * - orange for pending
 * - red for failed
 * @param status
 * @returns {JSX.Element}
 */
const StatusDot: React.FC<{ status: string }> = ({ status }) => {

    let color = "bg-red-500";
    if (status === "TASK_COMPLETED") {
        color = "bg-green-500";
    } else if (status === "TASK_STARTED") {
        color = "bg-green-900";
    } else if (status === "TASK_PENDING") {
        color = "bg-orange-500";
    }

    return (
        <span className={`inline-block h-2 w-2 rounded-full ${color}`} />
    );
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