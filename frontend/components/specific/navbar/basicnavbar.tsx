// Next Componenets
import Image from "next/image";
import Link from "next/link";

// Icons
import { FiLogIn, FiMenu, FiShare2, FiUsers } from 'react-icons/fi'

// Custom Components
import BasicDropdownMenu from "../../generic/dropdown/basicdropdownmenu";

const BasicNavbar: React.FC<{}> = () => {

    return (
        <div className="sticky top-0 z-50 w-full bg-base16-gray-900 text-base16-gray-200 flex flex-row justify-between items-center">
            <div className="">
                <Link href='/'>
                    <a className="flex flex-row items-center my-2 mx-4 grow">
                        <FiShare2 className="basic-svg" />
                        <div className="ml-2 font-dm-mono-medium">
                            PhiTag
                        </div>
                    </a>
                </Link>
            </div>

            <div className="flex-1 flex justify-end my-2 mx-4 ">
                <BasicDropdownMenu icon={<FiMenu className="w-8 h-8" />} items={
                    [
                        { icon: <FiLogIn className="basic-svg" />, text: 'Login', reference: '/login' },
                        { icon: <FiUsers className="basic-svg" />, text: 'Register', reference: '/register' },
                    ]
                } />

            </div>


        </div>
    );
}

export default BasicNavbar;