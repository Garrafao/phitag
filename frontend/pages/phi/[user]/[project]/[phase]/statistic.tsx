// Next
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React Modules
import { useEffect } from "react";

// Toast
import { toast } from "react-toastify";

// Icons

// services
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";
import Layout from "../../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../../components/generic/layout/singlecontentlayout";
import PhaseTabBar from "../../../../../components/specific/tab/phasedatatab";
import LinkHead from "../../../../../components/generic/linker/linkhead";
import { FiLayers } from "react-icons/fi";
import LoadingComponent from "../../../../../components/generic/loadingcomponent";
import CountUp from "react-countup";
import useFetchPhaseStatistic from "../../../../../lib/service/statistic/phasestatistic/PhaseStatisticRepository";
import PhaseStatistic from "../../../../../lib/model/statistic/phasestatistic/model/PhaseStatistic";
import { translateMapToChartJSData } from "../../../../../lib/service/statistic/StatisticChartJSTranslator";
import { Doughnut } from "react-chartjs-2";
import StatisticComponent from "../../../../../components/generic/other/StatisticComponent";
import Phase from "../../../../../lib/model/phase/model/Phase";
import { useFetchPhase, useFetchTutorialMeasurementHistory } from "../../../../../lib/service/phase/PhaseResource";


const Statistic: NextPage = () => {

    // fetch data & hooks
    const authenticated = useAuthenticated();

    const router = useRouter();
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };

    // hooks
    useEffect(() => {
        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated]);

    return (
        <Layout>

            <StatisticComponent />

            <Head>
                <title>PhiTag : {phasename} : Statistics</title>
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
                        {
                            href: `/phi/${username}/${projectname}/${phasename}/statistic`,
                            name: "Statistics",
                        }
                    ]}
                />


                <div className="font-dm-mono-medium">
                    <PhaseTabBar />


                    <div className="mt-8 flex flex-col w-full justify-center">
                        <PhaseStatisticComponent />
                    </div>

                </div>

            </SingleContentLayout>

        </Layout>
    )
}

export default Statistic;

const PhaseStatisticComponent: NextPage = () => {

    const router = useRouter();
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };

    const phaseStatistic = useFetchPhaseStatistic(username, projectname, phasename, router.isReady);
    const phase = useFetchPhase(username, projectname, phasename, router.isReady);

    if (!router.isReady || phaseStatistic.isLoading) {
        return <LoadingComponent />
    }

    if (phaseStatistic.isError) {
        return <div>Error</div>
    }

    return (
        <div className="w-full flex flex-col xl:mx-auto ">
            <div className="flex flex-col xl:flex-row mx-16 justify-between xl:space-x-16">
                <div className="flex flex-col space-y-8 self-center sm:flex-row sm:space-y-0 justify-between my-8 xl:justify-center xl:flex-col xl:space-y-8">
                    <CountUpComponent count={phaseStatistic.statistic.getAnnotations()} label="Annotations: " />
                    <NumberComponent count={phaseStatistic.statistic.getKrippendorffalpha()} label="Krippendorff's Alpha: " />
                </div>
                <AnnotationsPerAnnotator statistic={phaseStatistic.statistic} />
            </div>

            {
                phase?.phase?.isTutorial() && <TutorialMeasurementsHistory phase={phase.phase} />
            }
        </div>
    );

}


const CountUpComponent = ({ count, label }: { count: number, label: string }) => {
    return (
        <CountUp
            start={0}
            end={count}
            duration={3}
            separator=","
            prefix={label}
            className="text-2xl sm:text-3xl font-dm-mono-medium whitespace-nowrap shadow-md p-6"
        />
    );
}

const NumberComponent = ({ count, label }: { count: number, label: string }) => {
    return (
        <div className="text-2xl sm:text-3xl font-dm-mono-medium whitespace-nowrap shadow-md p-6">
            {label} {count.toPrecision(3)}
        </div>
    );
}

const AnnotationsPerAnnotator = ({ statistic }: { statistic: PhaseStatistic }) => {

    const options = {
        plugins: {
            legend: {
                display: true,
                position: 'right' as const,
                labels: {
                    fontColor: '#151515',
                    fontSize: 16,
                    boxWidth: 16,
                    padding: 16,
                    usePointStyle: true,
                },
            },
        },
    };

    const config = {
        type: 'doughnut',
        data: translateMapToChartJSData("Annotations", statistic.getAnnotatorAnnotationCountMap()),

    };

    return (
        <div className="flex flex-col">
            <div className="font-dm-mono-medium text-2xl sm:text-4xl">
                Annotations per Annotator:
            </div>
            <div className="flex flex-col justify-center items-center mx-auto max-w-sm pl-16">
                <Doughnut {...config} options={options} />
            </div>
        </div>
    );
}

const TutorialMeasurementsHistory = ({ phase }: { phase: Phase }) => {

    const measurements = useFetchTutorialMeasurementHistory(phase.getId().getOwner(), phase.getId().getProject(), phase.getId().getPhase());

    if (measurements.isLoading) {
        return <LoadingComponent />
    }

    if (measurements.isError) {
        return <div>Error</div>
    }

    const convertDate = (timestamp: number) => {
        if (timestamp === 0) {
            return 'No deadline';
        }
    
        const date = new Date(timestamp);
        return date.toLocaleDateString('en-GB', {day: 'numeric', month: '2-digit', year: 'numeric' }) + ' ' + date.toLocaleTimeString('en-GB', { hour: 'numeric', minute: 'numeric' });
    }

    // 
    return (
        <div>
            <div className="flex flex-col font-dm-mono-medium">
                <div className="overflow-auto">
                    <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                        <thead className="font-bold text-lg">
                            <tr>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Timestamp
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider ">
                                    Annotator
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider">
                                    Measurement
                                </th>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider">
                                    Passed
                                </th>
                            </tr>
                        </thead>
                        <tbody className=" text-base16-gray-700">
                            {measurements.tutorialHistory.map(measurement => {
                                return (
                                    <tr key={measurement.getTimestamp() + measurement.getAnnotatorId().getUser()}>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {/* Convert to date */}
                                            {convertDate(Number.parseInt(measurement.getTimestamp()))}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {measurement.getAnnotatorId().getUser()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {measurement.getMeasure()}
                                        </td>

                                        <td className="px-6 py-4 whitespace-nowrap">
                                            {measurement.isPassed() ? "Passed" : "Failed"}
                                        </td>

                                    </tr>
                                )

                            })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}
