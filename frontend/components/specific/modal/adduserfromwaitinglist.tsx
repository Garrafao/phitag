// react
import { useState } from "react";

// toast
import { toast } from "react-toastify";

//icons
import { FiList, FiUserPlus } from "react-icons/fi";

// services
import useStorage from "../../../lib/hook/useStorage";

// models
import PersonalJoblisting from "../../../lib/model/joblisting/model/PersonalJoblisting";
import DummySelectable from "../../../lib/model/dummy/DummySelectable";

// components
import DropdownSelect from "../../generic/dropdown/dropdownselect";
import AddUsersFromWaitinglistCommand from "../../../lib/model/joblisting/command/AddUsersFromWaitinglistCommand";
import { addUsersFromWaitinglist } from "../../../lib/service/joblisting/JoblistingResource";


const AddUserFromWaitingListModal: React.FC<{ isOpen: boolean, closeModalCallback: Function, joblisting: PersonalJoblisting, mutateCallback: Function }> = ({ isOpen, closeModalCallback, joblisting, mutateCallback }) => {


    // storage hook
    const storage = useStorage();

    // Data and State
    const [modalState, setModalState] = useState({
        selectedUsers: [] as DummySelectable[],
    });


    const onConfirm = () => {
        const command: AddUsersFromWaitinglistCommand | null = verifyAndAddCommand(joblisting, modalState);
        if (command) {
            addUsersFromWaitinglist(command, storage.get).then(() => {
                toast.success("User/s added from the joblisting to the project");

                // close modal
                setModalState({
                    selectedUsers: [] as DummySelectable[],
                });
                mutateCallback();
                closeModalCallback();
            }).catch((error) => {
                if (error?.response?.status === 500) {
                    toast.error("Error while adding user: " + error.response.data.message + "!");
                } else {
                    toast.warning("The system is currently not available, please try again later!");
                }
            });
        }
    }

    const onCancel = () => {
        toast.info("Canceled adding users from the joblisting to the project.");
        setModalState({
            selectedUsers: [] as DummySelectable[],
        });
        closeModalCallback();
    }

    if (!isOpen || !joblisting) {
        return null;
    }

    return (
        <div className="relative z-10 font-dm-mono-medium overflow-auto" onClick={() => onCancel()}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                    Add Users from Waitinglist
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    You are about to add users from the waiting list to the project.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Select Users
                                    </div>
                                    <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                        <DropdownSelect
                                            icon={<FiUserPlus className="basic-svg" />}
                                            items={joblisting.getWaitinglist()}
                                            selected={modalState.selectedUsers}
                                            onSelectFunction={(user: DummySelectable) => setModalState({
                                                ...modalState,
                                                selectedUsers: calculateNewSelectedUserArray(user, modalState.selectedUsers)
                                            })}
                                            message={
                                                joblisting.getWaitinglist().length === 0 ? "No users in the waiting list" :
                                                    modalState.selectedUsers.length > 0 ? `Number selected Users: ${modalState.selectedUsers.length}` : "No users selected"
                                            } />
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

export default AddUserFromWaitingListModal;

function calculateNewSelectedUserArray(user: DummySelectable, selectedUser: Array<DummySelectable>) {
    const filtered = selectedUser.filter(l => l.getName() !== user.getName());

    if (filtered.length === selectedUser.length) {
        return [...selectedUser, user];
    }
    return filtered;
}

function verifyAndAddCommand(joblist: PersonalJoblisting, command: { selectedUsers: DummySelectable[]; }): AddUsersFromWaitinglistCommand | null {
    if (command.selectedUsers === null || command.selectedUsers.length === 0) {
        toast.warning("Please select a user to add to the joblisting");
        return null;
    }
    return new AddUsersFromWaitinglistCommand(
        joblist.getId().getName(),
        joblist.getId().getOwner(),
        joblist.getId().getProject(),
        command.selectedUsers.map(u => u.getName())
    );
}