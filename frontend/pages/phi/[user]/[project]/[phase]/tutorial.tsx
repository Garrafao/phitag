// Next
import { NextPage } from "next";
import Router, { useRouter } from "next/router";

// React
import { useEffect } from "react";

// Toast
import { toast } from "react-toastify";

//Custom Hooks
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";
import FullLoadingPage from "../../../../../components/pages/fullloadingpage";
import { useFetchPhase } from "../../../../../lib/service/phase/PhaseResource";
import UsePairTutorial from "../../../../../components/specific/tutorial/usepair/usepairtutorial";
import ANNOTATIONTYPES from "../../../../../lib/AnnotationTypes";
import WSSIMTutorial from "../../../../../components/specific/tutorial/wssim/wssimtutorial";
import LexSubTutorial from "../../../../../components/specific/tutorial/lexsub/lexsubtutorial";
import UseRankTutorial from "../../../../../components/specific/tutorial/userank/useranktutorial";
import UseRankRelativeTutorial from "../../../../../components/specific/tutorial/userankrelative/userankrelativetutorial";
import UseRankPairTutorial from "../../../../../components/specific/tutorial/userankpair/userankpairtutorial";

// Helper

// Models


const TutorialPage: NextPage = () => {

    const authenticated = useAuthenticated();

    const router = useRouter();
    const { user: username, project: projectname, phase: tutorialname } = router.query as { user: string, project: string, phase: string };

    const phase = useFetchPhase(username, projectname, tutorialname, router.isReady);

    useEffect(() => {
        if (phase.isError) {
            toast.error("Phase not found");
            Router.push(`/phi/${username}/${projectname}`);
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, phase.isError, username, projectname]);


    // Render

    if (phase.isLoading || phase.phase === null || !router.isReady) {
        return (
            <FullLoadingPage headtitle="Annotating Tutorial" />
        );
    }

    if (!phase.phase.isTutorial()) {
        toast.error("Phase is not a tutorial");
        Router.push(`/phi/${username}/${projectname}`);
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USEPAIR) {
        return <UsePairTutorial phase={phase.phase} />;
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_WSSIM) {
        return <WSSIMTutorial phase={phase.phase} />;
    }

    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_LEXSUB) {
        return <LexSubTutorial phase={phase.phase} />;
    }
    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK) {
        return <UseRankTutorial phase={phase.phase} />;
    }
    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_RELATIVE) {
        return <UseRankRelativeTutorial phase={phase.phase} />;
    }
    if (phase.phase.getAnnotationType().getName() === ANNOTATIONTYPES.ANNOTATIONTYPE_USERANK_PAIR) {
        return <UseRankPairTutorial phase={phase.phase} />;
    }



    return (
        <FullLoadingPage headtitle="Annotate" />
    );

}

export default TutorialPage;
