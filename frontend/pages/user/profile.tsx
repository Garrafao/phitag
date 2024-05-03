// Next
import { NextPage } from "next";
import Router from "next/router";

// React
import { useEffect } from "react";

// Toast
import { toast } from "react-toastify";

// Hooks
import useAuthenticated from "../../lib/hook/useAuthenticated";

// Custom Controllers

// Custom Components
import FullLoadingPage from "../../components/pages/fullloadingpage";
import useStorage from "../../lib/hook/useStorage";


const Profile: NextPage = () => {

    // auth
    const authenticated = useAuthenticated();
    const storage = useStorage();

    useEffect(() => {

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        } else {
            Router.push(`/phi/${storage.get("USER")}`);
        }

    }, [authenticated, storage]);

    return <FullLoadingPage headtitle="User Profile" />;

}

export default Profile;