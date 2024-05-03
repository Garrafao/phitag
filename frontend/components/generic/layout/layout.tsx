import React from "react";

// Next Components
import Head from "next/head";

import Navbar from "../../specific/navbar/navbar";
import Footer from "../../specific/navbar/footer";

const Layout: React.FC<{}> = ({ children }) => {

    return (
        <div className="h-screen flex flex-col justify-between bg-white text-base16-gray-900 overflow-auto">

            <Head>

                <title>PhiTag</title>
            </Head>

            <Navbar />

            {children}

            <Footer />

        </div>
    );
}

export default Layout;