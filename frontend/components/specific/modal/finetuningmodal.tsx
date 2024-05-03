// React
import { useState } from "react";

// toast
import { toast } from "react-toastify";

// icons
import { FiFileMinus, FiFilePlus, FiFileText, FiUpload } from "react-icons/fi";

// services
import useStorage from "../../../lib/hook/useStorage";

import OpenAI, { ClientOptions, toFile } from 'openai';

// models
import { RiFile4Fill, RiFile4Line, RiFileAddFill, RiShieldKeyholeFill } from "react-icons/ri";
import BasicCheckbox from "../../generic/checkbox/basiccheckbox";
import { error } from "console";


// components

const FineTunigModal: React.FC<{ key: string, isOpen: boolean, closeFineTuneModal: Function }> = ({ key, isOpen, closeFineTuneModal }) => {

    const { get } = useStorage();
    const [selectedFile, setSelectedFile] = useState(null as File | null);

    const [modal, setModal] = useState({
        apiKey: "",
        fileId: ""


    })

    const [fileController, setFileControler] = useState({
        response: "",
        finetuneModel: ""
    })

    const [status, setStatus] = useState({
        message: "",
        show: false
    })

    const resetModal =async ()=>{
        setModal({
            ...modal,
            apiKey:"",
            fileId:""
        })
    }


    const [showApiKey, setShowApiKey] = useState(false);
    /*   training_file: 'file-tn6yKEIqsE4RjtfwwQkuLLH9', */
    


    const handleFineTuning = async () => {
        try {
            const openai = new OpenAI({ apiKey: modal.apiKey, dangerouslyAllowBrowser: true });

            // Create fine-tuning job
            const fineTune = await openai.fineTuning.jobs.create({
                training_file: modal.fileId,
                model: 'gpt-3.5-turbo',
            });

            if (fineTune.error) {
                toast.info(fineTune.error);
                return;
            }
            // Check if fine-tuning was successful
            if (fineTune.error) {

                    setFileControler({
                        ...fileController,
                        finetuneModel: fineTune.id
                    });
            } else {
                throw new Error('Fine-tuning failed');
            }

    /*         // Retrieve file status
            const fileStatus = await openai.files.retrieve('file-tn6yKEIqsE4RjtfwwQkuLLH9');
            console.log(fileStatus, 'file status'); */

            // Retrieve fine-tuning job status
            const status = await openai.fineTuning.jobs.retrieve(modal.fileId);
        } catch (error) {
            console.error('Error:', error);
            toast.error('An error occurred during fine-tuning.');
        }
    };



    if (!isOpen) {
        return null;
    }

    const onClose = () => {
        closeFineTuneModal();
    }


    return (
        <div className="relative z-10 font-dm-mono-medium">
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            {!fileController.finetuneModel ? (
                                <div>
                                    <div className="font-bold-mono text-m">OpenAI Key</div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <RiShieldKeyholeFill className="basic-svg" />
                                        <input
                                            id="apiKey"
                                            name="apiKey"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder="Api key"
                                            type={showApiKey ? "text" : "password"}
                                            value={modal.apiKey}
                                            onChange={(e) =>
                                                setModal({
                                                    ...modal,
                                                    apiKey: e.target.value,
                                                })
                                            }
                                        />
                                    </div>
                                    <div className="py-2 px-3 text-sm">
                                        <BasicCheckbox
                                            selected={showApiKey}
                                            description={"Show API KEY"}
                                            onClick={() => setShowApiKey(!showApiKey)}
                                        />
                                    </div>
                                    <div className="font-bold-mono text-m">File id</div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <RiFile4Fill className="basic-svg" />
                                        <input
                                            id="fileId"
                                            name="fileId"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder="Enter file id"
                                            type={"text"}
                                            value={modal.fileId}
                                            onChange={(e) =>
                                                setModal({
                                                    ...modal,
                                                    fileId: e.target.value,
                                                })
                                            }
                                        />
                                    </div>
                                </div>
                            ) : (
                                <div>
                                    <div className="font-bold-mono text-m mt-2">Fine tuned model</div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <input
                                            id="fileId"
                                            name="fileId"
                                            className="pl-3 flex flex-auto outline-none border-none"
                                            placeholder="Enter file id"
                                            type={"text"}
                                            value={fileController.finetuneModel}
                                            onChange={(e) => { /* handle change if needed */ }}
                                        />
                                    </div>
                                </div>
                            )}


                            {fileController.response && (
                                <div>
                                    <div className="font-bold-mono text-m">File id</div>

                                    <div className="py-2 px-3 flex items-center border-b-2 mt-2">
                                        <FiFilePlus className="basic-svg" />
                                        {fileController.response}
                                    </div>
                                </div>
                            )}

                            <div className="flex justify-end space-x-4 mt-2">


                                {!fileController.finetuneModel ? (
                                    <div>
                                        <button
                                            type="button"
                                            className="rounded-md active:transform active:scale-95 w-20 py-2 bg-base16-gray-900 text-base16-gray-100"
                                            onClick={handleFineTuning}
                                        >
                                            start
                                        </button>
                                    </div>
                                ) : (
                                    <button
                                        type="button"
                                        className="rounded-md active:transform active:scale-95 w-20 py-2 bg-base16-gray-900 text-base16-gray-100"
                                        onClick={onClose}
                                    >
                                        copy
                                    </button>
                                )}

                                <button
                                    type="button"
                                    className="rounded-md active:transform active:scale-95 w-20 py-2 bg-base16-gray-900 text-base16-gray-100"
                                    onClick={onClose}
                                >
                                    close
                                </button>
                            </div>


                        </div>

                    </div>
                </div>
            </div>
        </div>

    )
}

export default FineTunigModal;
