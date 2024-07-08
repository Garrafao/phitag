// react
import { useState } from "react";

// toast
import { toast } from "react-toastify";

// icons
import { FiEdit3 } from "react-icons/fi";

// model
import Annotator from "../../../lib/model/annotator/model/Annotator";
import Entitlement from "../../../lib/model/entitlement/model/Entitlement";

// service
import { useFetchAllEntitlements } from "../../../lib/service/entitlement/EntitlementResource";

// component
import DropdownSelect from "../../generic/dropdown/dropdownselect";
import EditAnnotatorCommand from "../../../lib/model/annotator/command/EditAnnotatorCommand";
import { removeAnnotator, updateAnnotator } from "../../../lib/service/annotator/AnnotatorResource";
import useStorage from "../../../lib/hook/useStorage";
import RemoveAnnotatorCommand from "../../../lib/model/annotator/command/RemoveAnnotatorCommand";


const RemoveAnnotatorModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, annotator: Annotator, mutateCallback: Function }> = ({ isOpen, closeModalCallback, annotator, mutateCallback }) => {

    // data and state
    const storage = useStorage();
    const username = storage.get('USER') ?? ""
    const entitlements = useFetchAllEntitlements(isOpen);

    const [modalState, setModalState] = useState({
        selectedEntitlement: [] as unknown as Entitlement[],
    })


    const onConfirm = () => {
        if (annotator == null) {
            toast.warning("This should not happen. Please try again.");
            return;
        }

        const command: RemoveAnnotatorCommand = new RemoveAnnotatorCommand(username, annotator.getId().getProject(), annotator.getId().getOwner(),  annotator.getId().getUser(), annotator.getEntitlement().getName());
       
        removeAnnotator(command, storage.get).then(() => {
            toast.success("Annotator removed");
            mutateCallback();
            closeModalCallback();
        }
        ).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while removing annotator: " + error.response.data.message + "!");
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        });

    }

    const onCancel = () => {
        toast.info("Removing annotator cancelled");
        setModalState({
            ...modalState,
            selectedEntitlement: [] as unknown as Entitlement[],
        });
        closeModalCallback();
    }

    if (!isOpen || !annotator || entitlements.isLoading || entitlements.isError) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                        <div className="font-black text-xl">
                                    User: {annotator.getId().getUser()}
                                </div>
                            <div className="flex flex-col items-left mt-6">
                                Are you sure want to remove {annotator.getId().getUser()} from the project?
                               {/*  <div className="font-black text-xl">
                                    User: {annotator.getId().getUser()}
                                </div> */}
                              
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

export default RemoveAnnotatorModal;

