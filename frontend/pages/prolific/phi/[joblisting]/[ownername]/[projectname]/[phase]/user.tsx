// Next Modules
import Head from 'next/head'
import Image from "next/image";
import Link from "next/link";

// Images
import prolific from "../../../../../../../public/image/prolific-logo.png"
import tags24x7 from "../../../../../../../public/image/tags24x7-logo.png";


// Custom Components
import BasicNavbar from '../../../../../../../components/specific/navbar/basicnavbar';
import BasicFooter from '../../../../../../../components/specific/navbar/basicfooter';

import Router, { useRouter } from 'next/router';


const ConfirmUser = () => {

    const router = useRouter();
    const { joblisting, ownername, projectname, phase } = router.query as {
        joblisting: string;
        ownername: string;
        projectname: string;
        phase: string
    };

    const newUser = () => {
        Router.push(`/prolific/phi/${joblisting}/${ownername}/${projectname}/${phase}/register`);
    };

    const existingUser = () => {
        Router.push(`/prolific/phi/${joblisting}/${ownername}/${projectname}/${phase}/login`);
    };




    return (
        <div className='flex flex-col'>

            <BasicNavbar />

            <Head>
                <title>PhiTag</title>
            </Head>


            {/* div taking full height of screen */}
            <div className='w-full my-32 xl:my-0 xl:h-screen flex' >
                <div className='flex flex-col m-auto space-y-8'>
                    <div className='sm:px-16 md:px-64 xl:px-96 text-center' >
                        <div className='font-uni-corporate-bold font-bold text-4xl'>
                            Welcome to PhiTag
                        </div>
                        <div className='font-uni-corporate-light text-base16-gray-900 py-2'>
                            The platform for your text annotation needs
                        </div>
                    </div>
                    <div className='flex shadow-md p-8 justify-center cursor-pointer hover:scale-105 transform transition-all duration-500'
                        onClick={newUser}>
                        <div className='font-uni-corporate-bold font-bold text-2xl '>
                            I am new to phitag
                        </div>
                    </div>
                    <div className='flex shadow-md p-8 justify-center cursor-pointer hover:scale-105 transform transition-all duration-500'
                        onClick={existingUser}>
                        <div className='font-uni-corporate-bold font-bold text-2xl'>
                            I have registerd before
                        </div>
                    </div>
                </div>
            </div>
            <div className='w-full my-32 xl:my-0 xl:h-screen flex ' >
                <div className='flex flex-col m-2 xl:w-2/3 xl:m-auto'>

                    {/* First Card */}
                    <div className='flex flex-col shadow-md py-6 px-8 sm:py-12 sm:px-16'>

                        <h1 className='mb-2 font-uni-corporate-bold font-bold text-4xl'>
                            Collaborations
                        </h1>
                        <div className="hidden sm:flex flex-1 h-full py-2 justify-center">
                            <Link href="https://www.prolific.co/">
                                <div className="mx-4 h-64 w-64 cursor-pointer relative">
                                    <Image src={prolific} alt="Prolific" layout="fill" objectFit="contain" />
                                </div>
                            </Link>
                            <Link href="https://tags24x7.ai/">
                                <div className="mx-4 h-64 w-64 cursor-pointer relative">
                                    <Image src={tags24x7} alt="Tags24x7" layout="fill" objectFit="contain" />
                                </div>
                            </Link>
                        </div>
                    </div>

                </div>
            </div>

            <BasicFooter />
        </div>

    )

}


export default ConfirmUser;
