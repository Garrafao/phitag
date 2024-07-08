import { toast } from "react-toastify";


const AddReportModal: React.FC<{ isOpen: boolean, closeModalCallback: Function }> = ({ isOpen, closeModalCallback }) => {

    // storage hook

    /*const [modalState, setModalState] = useState({
        description: "",
    });*/


    /*     const onConfirm = () => {
            addReport(modalState.description, storage.get)
                .then((res) => {
                    toast.success("Thank you for your report!");
    
                    // close modal
                    setModalState({
                        description: "",
                    });
                    closeModalCallback();
                }).catch((error) => {
                    if (error?.response?.status === 500) {
                        toast.error("Error while adding your report: " + error.response.data.message + "!");
                    } else {
                        toast.warning("The system is currently not available, please try again later!");
                    }
                });
        } */
    const onConfirm = () => {
        const url = "https://github.com/Garrafao/phitag/issues";
        window.location.href = url;
    }



    const onCancel = () => {
        toast.info("Canceled creating a new joblisting.");

        closeModalCallback();
    }

    if (!isOpen) {
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
                                    <div className="font-light text-lg">
                                        {/* eslint-disable-next-line react/no-unescaped-entities */}
                                        To report a problem, please create an issue in GitHub by clicking on "Report Issue" button below. Then, click "New issue" and provide a clear and concise title that summarizes the problem. In the description, detail the issue you're encountering, including any relevant steps to reproduce it and error messages.This will help the project maintainers understand and address the problem efficiently.
                                    </div>

                                </div>

                                {/* <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Description
                                    </div>
                                    <div className="h-32 flex items-start border-l-2 py-2 px-3 my-6">
                                        <FiFeather className='basic-svg' />
                                        <textarea
                                            className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                                            name="description"
                                            placeholder="Description"
                                            value={modalState.description}
                                            onChange={(e: any) => setModalState({
                                                ...modalState,
                                                description: e.target.value
                                            })} />
                                    </div>
                                </div> */}

                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8">
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={() => onCancel()}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onConfirm}>Report Issue</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AddReportModal;