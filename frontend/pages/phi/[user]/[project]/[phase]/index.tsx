// Next
import { NextPage } from "next";
import Router, { useRouter } from "next/router";

// React
import { useEffect } from "react";

// Toast
import { toast } from "react-toastify";

// Hooks
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";

// Components
import FullLoadingPage from "../../../../../components/pages/fullloadingpage";
import Layout from "../../../../../components/generic/layout/layout";
import Head from "next/head";
import SingleContentLayout from "../../../../../components/generic/layout/singlecontentlayout";
import ProjectTabBar from "../../../../../components/specific/tab/projecttabbar";
import PhaseTabBar from "../../../../../components/specific/tab/phasedatatab";
import { useFetchPhase } from "../../../../../lib/service/phase/PhaseResource";
import LinkHead from "../../../../../components/generic/linker/linkhead";
import { FiLayers } from "react-icons/fi";
import { useFetchComputationAnnotatorsOfPhase } from "../../../../../lib/service/annotator/AnnotatorResource";
import { useFetchAllSamplingMethods } from "../../../../../lib/service/sampling/SamplingResource";
import { route } from "next/dist/server/router";


const PhasePage: NextPage = () => {

    // TODO: Phase overview page

    // auth
    const authenticated = useAuthenticated();
    const router = useRouter();
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };
    const phase = useFetchPhase(username, projectname, phasename, router.isReady);
    const annotator = useFetchComputationAnnotatorsOfPhase(username, projectname, phasename, router.isReady)

    useEffect(() => {

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        } else if (router.isReady && phase.isError) {
            toast.error("Phase not found");
            Router.push(`/phi/${username}/${projectname}`);
        }

    }, [authenticated, router.isReady, phase.isError]);

    if (phase.isLoading || phase.phase === null || !router.isReady) {
        return <FullLoadingPage headtitle="Instance Data" />
    }

    return (
        <Layout>

            <Head>
                <title>PhiTag : {phase.phase.getName()}</title>
            </Head>


            <SingleContentLayout>

                <LinkHead icon={<FiLayers className="stroke-2" />}
                    links={[
                        {
                            href: `/phi/${username}`,
                            name: username,
                        },
                        {
                            href: `/phi/${username}/${projectname}`,
                            name: projectname,
                        },
                        {
                            href: `/phi/${username}/${projectname}/${phasename}`,
                            name: phasename,
                        },
                    ]}
                />

                <PhaseTabBar />

                {/* Currently no overview warning */}
                <div className="flex flex-col items-center justify-center mt-16">
                    <h1 className="font-dm-mono-medium font-bold text-3xl mb-8">
                        Currently no overview available
                    </h1>
                    <p className="font-dm-mono-regular font-bold text-xl mb-16">
                        Please select a tab to view the data, history or statistic of this phase.
                    </p>
                </div>


            </SingleContentLayout>

        </Layout>
    );

}

export default PhasePage;