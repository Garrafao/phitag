import React from "react";

// Next Components
import Head from "next/head";

import BasicNavbar from "../../specific/navbar/basicnavbar";
import BasicFooter from "../../specific/navbar/basicfooter";

const BasicLayout: React.FC<{}> = ({ children }) => {
    return (
        <div className="h-screen flex flex-col justify-between bg-white text-base16-gray-900 overflow-auto">


            <Head>
                <title>PhiTag</title>
            </Head>

            <BasicNavbar />

            {/* <ContentLayout> */}
                {children}
            {/* </ContentLayout> */}

            <BasicFooter />

        </div>
    );
}

export default BasicLayout;