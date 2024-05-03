// react
import { useState } from "react";
import { FiLayers } from "react-icons/fi";

//toast
import { toast } from "react-toastify";
import useStorage from "../../../lib/hook/useStorage";
import AddRequirementsCommand from "../../../lib/model/phase/command/AddRequirementsCommand";

// modal
import Phase from "../../../lib/model/phase/model/Phase"
import { addRequirementsToPhase, useFetchPhases } from "../../../lib/service/phase/PhaseResource";
import DropdownSelect from "../../generic/dropdown/dropdownselect";

const AddRequirementsModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, phase: Phase, mutateCallback: Function }> = ({ isOpen, closeModalCallback, phase, mutateCallback }) => {

    const storage = useStorage();

    // fetch tutorial of project
    const tutorials = useFetchPhases(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getAnnotationType().getName(), true, isOpen && !!phase);

    const [modalState, setModalState] = useState({
        selectedRequirements: [] as Phase[],
    });

    const onConfirm = () => {
        // if (modalState.selectedRequirements.length == 0) {
        //     toast.error("Please select at least one requirement");
        //     return;
        // }

        const command = new AddRequirementsCommand(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase(),
            modalState.selectedRequirements.map((phase) => phase.getId().getPhase()));

        addRequirementsToPhase(command, storage.get).then((res) => {
            toast.success("Successfully added requirements");
            setModalState({
                selectedRequirements: [] as Phase[],
            });
            mutateCallback();
            closeModalCallback();
        }).catch((error) => {
            if (error?.response?.status === 500) {
                toast.error("Error while adding requirements: " + error.response.data.message + "!");
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        });
    }

    const onCancel = () => {
        toast.info("Canceled adding requirements");
        setModalState({
            ...modalState,
            selectedRequirements: [] as Phase[],
        });
        closeModalCallback();
    }

    if (!isOpen || tutorials.isLoading || tutorials.isError) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium overflow-auto" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white  shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                    Phase: {phase.getDisplayname()}
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You are about to add tutorial requirements to the phase.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Select Tutorials
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2 ">
                                        <DropdownSelect
                                            icon={<FiLayers className="basic-svg" />}
                                            items={tutorials.phases}
                                            selected={modalState.selectedRequirements}
                                            onSelectFunction={(selected: Phase) => setModalState({ ...modalState, selectedRequirements: calculateNewRequirementsArray(modalState.selectedRequirements, selected) })}
                                            message={tutorials.phases.length === 0 ? "No tutorials available" :
                                                modalState.selectedRequirements.length === 0 ? "Select tutorial phases" : `${modalState.selectedRequirements.length} phases selected`}
                                        />

                                    </div>
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

export default AddRequirementsModal;

function calculateNewRequirementsArray(selectedRequirements: Phase[], phase: Phase): Phase[] {
    const filtered = selectedRequirements.filter(p => p.getId().getPhase() !== phase.getId().getPhase());

    if (filtered.length === selectedRequirements.length) {
        return [...selectedRequirements, phase];
    }
    return filtered;
}