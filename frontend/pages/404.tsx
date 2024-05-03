// Next
import { NextPage } from "next";
import Router from "next/router";

// React
import { useEffect } from "react";

// Toast
import { toast } from "react-toastify";

// Hooks
import useAuthenticated from "../lib/hook/useAuthenticated";

// Custom Controllers

// Custom Components
import FullLoadingPage from "../components/pages/fullloadingpage";


const Custom404: NextPage = () => {

    // auth
    const authenticated = useAuthenticated();

    useEffect(() => {

        if (authenticated.isReady) {
            if (!authenticated.isAuthenticated) {
                toast.info("Session expired, please login again.");
                Router.push("/");
            } else {
                toast.info("Page not found.");
                Router.push("/dashboard");
            }
        }

    }, [authenticated]);

    return <FullLoadingPage headtitle="404" />;

}

export default Custom404;