// React
import { useEffect } from "react";

// Next
import { NextPage } from "next";

// Toast
import { toast } from "react-toastify";

// Services
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";
import Router, { useRouter } from "next/router";

// Components
import FullLoadingPage from "../../../../../components/pages/fullloadingpage";


const GuidelinePage: NextPage = () => {

    // auth
    const authenticated = useAuthenticated();
    const router = useRouter();
    const { user: username, project: projectname } = router.query as { user: string, project: string };


    useEffect(() => {

        if (router.isReady && authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        } else {
            Router.push(`/phi/${username}/${projectname}`);
        }

    }, [authenticated, router.isReady]);

    return <FullLoadingPage headtitle="Project Pool" />;

}

export default GuidelinePage;