
// toast
import { toast } from "react-toastify";


import Phase from "../../../lib/model/phase/model/Phase";

// components
interface IProps {
    isOpen: boolean;
    closeModalCallBack: () => void;
    phase: Phase;
    deletePhase: () => void;
}


const DeletePhaseModal: React.FC<IProps> = ({ isOpen, closeModalCallBack, phase, deletePhase }) => {

    const clear = () => {
        closeModalCallBack();
    }
    const onCancel = () => {
        clear();
        toast.info(`Canceled deleteing pahse: ${phase.getName()}`);
    }

    if (!isOpen || !phase) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={() => onCancel()}>


            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75 " />
            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You are about to delete phase:{phase.getDisplayname()}.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Warning: Deleting a {phase.getDisplayname()} will result in the removal of all associated instances and judgements data.
                                    </div>

                                </div>

                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8 bg">
                            {/*  <button className=" m-4 flex items-center  hover:bg-blue-300 focus:outline-none
                                             focus:ring focus:border-blue-300 active:transform active:scale-95 text-black  font-bold py-2 px-4 rounded">
                                                Test
                                             </button> */}
                            <button type="button"
                                className="active:transform active:scale-95
                            block w-full  mt-8 bg-base16-gray-900 text-base16-gray-100 " onClick={onCancel}>Cancel</button>

                            <button type="button"
                                className="active:transform active:scale-95 
                            block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={deletePhase}>Delete</button>
                        </div>
                    </div>
                </div>
            </div>
           {/*  <div className="relative z-10" aria-labelledby="modal-title" role="dialog" aria-modal="true" onClick={(e: any) => e.stopPropagation()}>

                <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>

                <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
                    <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">

                        <div className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                            <div className="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                                <div className="sm:flex sm:items-start">
                                    <div className="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                                        <svg className="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z" />
                                        </svg>
                                    </div>
                                    <div className="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                        <h3 className="text-base font-semibold leading-6 text-gray-900" id="modal-title">Delete   {phase.getDisplayname()}</h3>
                                        <div className="mt-2">
                                            <p className="text-sm text-gray-600">Are you sure you want to delete your phase: {phase.getDisplayname()}? All of your data will be permanently removed. This action cannot be undone.</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className=" px-10 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                <button type="button" className="inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-red-500 sm:ml-3 sm:w-auto"
                                    onClick={deletePhase}>Delete</button>
                                <button type="button" className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                    onClick={onCancel}>Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div> */}
        </div>

    )
}

export default DeletePhaseModal;
