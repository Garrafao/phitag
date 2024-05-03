import { FiCheckCircle } from "react-icons/fi";

interface IProps {
    icon: any;
    tooltip: string;
    open: boolean;
    handleOpen: any;
    handleUploadAccepted: any;
    fileChange: any;
}

const IconUploadButton: React.FC<IProps> = ({ icon, tooltip, handleOpen, open, handleUploadAccepted, fileChange }) => {

    // TODO: refactor when project

    const visibility = open ? 'upload-visible' : 'upload-hidden';

    // is hacky for now
    return (
        <div className="flex flex-row transition-all">
            <div className={`${visibility} `}>
                <div className="flex flex-row-reverse mr-5">
                    <form className="flex items-center border-b-2">
                        <input type="file" className="hide-upload-button pl-3 flex flex-auto outline-none border-none text-sm" onChange={e => fileChange(e)}/>
                    </form>
                    <div className="cursor-pointer icon-button group mr-3" onClick={() => handleUploadAccepted()}>
                        <div>
                            <FiCheckCircle className="small-svg" />
                        </div>

                        <div className="icon-button-tooltip group-hover:scale-100">
                            <div className="whitespace-nowrap mx-4 my-2">
                                Upload File
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className="cursor-pointer icon-button group" onClick={() => handleOpen()}>
                <div>
                    {icon}
                </div>

                <div className="icon-button-tooltip group-hover:scale-100">
                    <div className="whitespace-nowrap mx-4 my-2">
                        {tooltip}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default IconUploadButton;