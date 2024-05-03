import { FiLock } from "react-icons/fi";

interface IProps {
    isOpen: boolean;
    onConfirm: () => void;
    onCancel: () => void;
    
    password: string;
    setPassword: (password: string) => void;
}

const PasswordConfirmationModal: React.FC<IProps> = ({ isOpen, onConfirm, onCancel, password, setPassword }) => {
    if (!isOpen) {
        return null;
    }
    
    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={onCancel}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75"/>

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col items-left mt-6">
                                <div className="font-black text-xl">
                                    Confirm Changes
                                </div>
                                <div className="font-dm-mono-regular my-2">
                                    Please enter your current password to confirm changes. Any changes will require you to login again.
                                </div>
                                <div className="flex flex-auto border-b-2 py-2 px-3 ">
                                    <FiLock className="basic-svg" />
                                    <input className="pl-3 flex flex-auto outline-none border-none " name="password" id="passwordConfirmation" placeholder="Password" type={"password"}
                                        value={password} onChange={(e: any) => setPassword(e.target.value)} />
                                </div>
                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8">
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onCancel}>Cancel</button>
                            <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={onConfirm}>Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )
}

export default PasswordConfirmationModal;