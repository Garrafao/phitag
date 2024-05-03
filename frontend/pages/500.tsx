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


const Custom500: NextPage = () => {

    // auth
    const authenticated = useAuthenticated();

    useEffect(() => {

        if (authenticated.isReady) {
            if (!authenticated.isAuthenticated) {
                toast.info("Session expired, please login again.");
                Router.push("/");
            } else {
                toast.info("There seems to be an error on our end. Please try again later and if the problem persists, please contact us.");
                Router.push("/dashboard");
            }
        }

    }, [authenticated]);

    return <FullLoadingPage headtitle="500" />;

}

export default Custom500;