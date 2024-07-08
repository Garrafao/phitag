import { useState } from "react";

// toast
import { toast } from "react-toastify";

// service
import { deleteUsage, editUsage } from "../../../lib/service/phitagdata/PhitagDataResource";
import useStorage from "../../../lib/hook/useStorage";

// model
import Usage from "../../../lib/model/phitagdata/usage/model/Usage";
import EditUsageCommand from "../../../lib/model/phitagdata/usage/command/EditUsageCommand";
import { FiBold, FiFeather, FiHash, FiUnderline } from "react-icons/fi";
import DeleteUsageCommand from "../../../lib/model/phitagdata/usage/command/DeleteUsageCommand";

interface DeleteUsageModalProps {
    usage: Usage;

    isOpen: boolean;
    closeModalCallback: Function;
    mutateCallback: Function;
}

const DeleteUsageModal: React.FC<DeleteUsageModalProps> = ({ usage, isOpen, closeModalCallback, mutateCallback }) => {

    // data and state
    const storage = useStorage();

    const [modalState, setModalState] = useState({
        context: "",

        indexTargetToken: "",
        indexTargetSentence: "",

        lemma: "",
        group: "",
    });

    const onConfirm = () => {
        if (usage == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }

        const command: DeleteUsageCommand = new DeleteUsageCommand(usage.getId().getDataid(), usage.getId().getProject(), usage.getId().getOwner());
        deleteUsage(command, storage.get).then(() => {
            toast.success("Usage deleted");
            // close modal
            mutateCallback();
            closeModalCallback();

        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while deleting usage: " + error.response.data.message + "!");
            } else {
                toast.error("Error while deleting usage!");
            }
        });
    }

    const onCancel = () => {
        toast.info("Cancled deleting a usage");
        closeModalCallback();
    }

    if (!isOpen || !usage) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                        <div className="flex flex-col items-start mt-4">
                            <div className="font-black text-2xl mb-2">
                                Delete Usage
                            </div>
                            <div className="font-dm-mono-regular my-2 text-gray-700">
                                Warning: Deleting usage will lead to deleting the associated instances and judgments.
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

export default DeleteUsageModal;
