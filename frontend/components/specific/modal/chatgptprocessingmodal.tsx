// react
import { useState } from "react";

// icons
import { RiEmotionHappyLine } from "react-icons/ri";
import { TfiFaceSad } from "react-icons/tfi";

// services

// models
import Phase from "../../../lib/model/phase/model/Phase";

// components
import useStorage from "../../../lib/hook/useStorage";


const ChatGptProcssingModal: React.FC<{
    phase: Phase, isOpen: boolean, closeModalCallBack: Function, tutorialHistory: any []
}> = ({ phase, isOpen, closeModalCallBack, tutorialHistory }) => {

    // hooks
    const storage = useStorage();
    const onClose = () => {
        closeModalCallBack();
    }
    const convertDate = (timestamp: number) => {
        if (timestamp === 0) {
            return 'No deadline';
        }

        const date = new Date(timestamp);
        return date.toLocaleDateString('en-GB', { day: 'numeric', month: '2-digit', year: 'numeric' }) + ' ' + date.toLocaleTimeString('en-GB', { hour: 'numeric', minute: 'numeric' });
    }


    // data and state
    const [modalState, setModalState] = useState({
        selectedFile: null as unknown as File,
    })

    if (!isOpen) {
        return null;
    }

    return (
        <div className="relative  font-dm-mono-medium" onClick={() => { }}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-2 max-w w-half" onClick={(e: any) => e.stopPropagation()}>

                        <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                            <thead className="font-mono-bold text-sm">
                                <tr>
                                    <th scope="col"
                                        className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                        Timestamp
                                    </th>
                                    <th scope="col"
                                        className="px-6 py-3 text-left uppercase tracking-wider ">
                                        Annotator
                                    </th>
                                    <th scope="col"
                                        className="px-6 py-3 text-left uppercase tracking-wider">
                                        Measurement
                                    </th>
                                    <th scope="col"
                                        className="px-6 py-3 text-left uppercase tracking-wider">
                                        Status
                                    </th>
                                </tr>
                            </thead>
                    
                            <tbody className="text-base16-gray-700">
                                {tutorialHistory?.map((measurement:any, index: any) => (
                                    <tr key={index}>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {convertDate(Number.parseInt(measurement.timestamp))}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {measurement.annotatorId.user}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {measurement.measure}
                                        </td>
                                       
                                        <td className="px-8 py-6 whitespace-nowrap flex justify-end">
                                        {measurement.passed ? (
                                            <>
                                                <span className="text-green-700">Passed</span>
                                                <span className="ml-4 py-1">
                                                    <RiEmotionHappyLine />
                                                </span>
                                            </>
                                        ) : (
                                            <>
                                                <span className="text-red-700">Failed</span>

                                                <span className="ml-4 py-1">
                                                    <TfiFaceSad />
                                                </span>
                                            </>
                                        )}
                                    </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>

                        <div className="flex flex-col">
                            <button
                                type="button"
                                className="active:transform active:scale-95 block w-20 mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 self-end"
                                onClick={onClose} >
                                Close
                            </button>
                        </div>
                    </div>

                </div>

            </div>
        </div >

    )
}

export default ChatGptProcssingModal;
