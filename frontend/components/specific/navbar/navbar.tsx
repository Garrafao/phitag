// Next Componenets
import Link from "next/link";
import { useState } from "react";

// Icons
import { FiAlertCircle, FiBox, FiFolder, FiHelpCircle, FiLogOut, FiMenu, FiMessageSquare, FiSettings, FiShare2, FiUser } from 'react-icons/fi'

// Custom Hooks
import useStorage from "../../../lib/hook/useStorage";
import { logout } from "../../../lib/service/auth/AuthenticationController";

// Custom Components
import IconButtonOnClickBottom from "../../generic/button/iconbuttononclickbottom";
import DropdownMenu from "../../generic/dropdown/dropdownmenu";
import AddReportModal from "../../generic/modal/addreportmodal";

const Navbar: React.FC<{}> = () => {

    const { remove } = useStorage();

    const [showAddReportModal, setShowAddReportModal] = useState(false);

    return (
        <div className="sticky top-0 z-50 w-full bg-base16-gray-900 text-base16-gray-200 flex flex-row items-center">

            <Link href='/dashboard'>
                <a className="flex flex-row items-center my-2 mx-4 grow">
                    <FiShare2 className="basic-svg" />
                    <div className="ml-2 font-dm-mono-medium"> 
                        PhiTag
                    </div>
                </a>
            </Link>

            <div className="my-2 mx-4 flex flex-row self-end space-x-4">
                <IconButtonOnClickBottom
                    icon={<FiAlertCircle className="basic-svg" />}
                    onClick={() => setShowAddReportModal(true)}
                    tooltip="Report a Problem"
                    />
                <DropdownMenu icon={<FiMenu className="basic-svg" />} items={
                    [
                        {
                            icon: <FiUser className="basic-svg" />,
                            text: "Profile",
                            reference: "/user/profile",
                        },
                        {
                            icon: <FiMessageSquare className="basic-svg" />,
                            text: "Notifications",
                            reference: "/user/notification"
                        },
                        {
                            icon: <FiFolder className="basic-svg" />,
                            text: "Your Projects",
                            reference: "/pool/project/personal",
                        },
                        {
                            icon: null,
                            text: "bar",
                            reference: "",
                            bar: true,
                        },
                        {
                            icon: <FiBox className="basic-svg" />,
                            text: "Pools",
                            reference: "/pool"
                        },

                        {
                            icon: null,
                            text: "bar-2",
                            reference: "",
                            bar: true,
                        },
                        {
                            icon: <FiSettings className="basic-svg" />,
                            text: "Settings",
                            reference: "/user/setting"
                        },
                        {
                            icon: <FiLogOut className="basic-svg" />,
                            text: "Logout",
                            onClick: () => { logout(remove) },
                            reference: "/"
                        }
                    ]
                }/>

            </div>

            <AddReportModal isOpen={showAddReportModal} closeModalCallback={() => setShowAddReportModal(false)} />

            
        </div>
    );
}

export default Navbar;