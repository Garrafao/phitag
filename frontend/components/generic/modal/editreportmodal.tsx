import { useState } from "react";
import { FiDisc, FiFeather } from "react-icons/fi";
import { toast } from "react-toastify";
import useStorage from "../../../lib/hook/useStorage";
import Report from "../../../lib/model/report/model/Report";
import Status from "../../../lib/model/status/model/Status";
import { updateReport } from "../../../lib/service/report/ReportResource";
import { useFetchAllStatus } from "../../../lib/service/status/StatusResource";
import BasicDropdownSelect from "../dropdown/basicdropdownselect";

const EditReportModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, report: Report, mutateCallback: Function }> = ({ isOpen, closeModalCallback, report, mutateCallback }) => {

    // storage hook
    const storage = useStorage();
    const statuses = useFetchAllStatus(isOpen)

    const [modalState, setModalState] = useState({
        description: "",
        status: null as unknown as Status,
        
    });


    const onConfirm = () => {
        updateReport(report.getId(), modalState.description || report.getDescription(), modalState.status?.getName() || report.getStatus().getName(), storage.get)
            .then((res) => {
                toast.success("Edited report!");

                // close modal
                setModalState({
                    description: "",
                    status: null as unknown as Status,
                });
                mutateCallback();
                closeModalCallback();
            }).catch((error) => {
                if (error?.response?.status === 500) {
                    toast.error("Error while updating report: " + error.response.data.message + "!");
                } else {
                    toast.warning("The system is currently not available, please try again later!");
                }
            });
    }


    const onCancel = () => {
        toast.info("Canceled creating a new joblisting.");
        setModalState({
            description: "",
            status: null as unknown as Status,
        });
        closeModalCallback();
    }

    if (!isOpen || !report) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6 text-base16-gray-900">
                                <div className="font-black text-xl">
                                    Report a Problem
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Description
                                    </div>
                                    <div className="h-32 flex items-start border-l-2 py-2 px-3 my-6">
                                        <FiFeather className='basic-svg' />
                                        <textarea
                                            className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                                            name="description"
                                            placeholder={report.getDescription()}
                                            value={modalState.description}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                description: e.target.value
                                            })} />
                                    </div>
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Status
                                    </div>

                                    <div className="flex w-full items-center border-b-2 py-2 px-3 my-6">
                                        <BasicDropdownSelect
                                            icon={<FiDisc className="basic-svg" />}
                                            items={statuses.statuses}
                                            selected={[modalState.status || report.getStatus()]}
                                            onSelectFunction={(selected: Status) => {setModalState({
                                                ...modalState,
                                                status: selected
                                            })}}
                                            message={modalState.status?.getVisiblename() || report.getStatus().getVisiblename()} />
                                    </div>
                                </div>


                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8">
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={() => onCancel()}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onConfirm}>Send</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EditReportModal;