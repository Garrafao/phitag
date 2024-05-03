
// toast
import { toast } from "react-toastify";


import Project from "../../../lib/model/project/model/Project";

// components
interface IProps {
    isOpen: boolean;
    closeModalCallBack: () => void;
    project: Project;
    deleteProject: () => void;
}


const DeleteProjectModal: React.FC<IProps> = ({ isOpen, closeModalCallBack, project, deleteProject }) => {

    const clear = () => {
        closeModalCallBack();
    }
    const onCancel = () => {
        clear();
        toast.info(`Canceled deleteing project: ${project.getName()}`);
    }

    if (!isOpen || !project) {
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
                                    You are about to delete project:{project.getDisplayname()}.
                                </div>

                                <div className="flex flex-col items-left my-6">
                                    <div className="font-bold text-lg">
                                        Warning: Deleting a project: {project.getDisplayname()} will result in the removal of associated data with this project.
                                    </div>

                                </div>

                            </div>
                        </div>
                        <div className="flex flex-row divide-x-8 bg">
                            <button type="button"
                                className="active:transform active:scale-95
                            block w-full  mt-8 bg-base16-gray-900 text-base16-gray-100 " onClick={onCancel}>Cancel</button>

                            <button type="button"
                                className="active:transform active:scale-95 
                            block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 " onClick={deleteProject}>Delete</button>
                        </div>
                    </div>
                </div>
            </div>
          
        </div>

    )
}

export default DeleteProjectModal;
