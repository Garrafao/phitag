//Next Modules
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React
import { useEffect, useState } from "react";

// Toast
import { toast } from "react-toastify";

// React Icons
import { FiFolder, FiSearch, FiUser } from "react-icons/fi";

//Custom Hooks
import useAuthenticated from "../../../../lib/hook/useAuthenticated";
// services
import useFetchProjectStatistic from "../../../../lib/service/statistic/projectstatistic/ProjectStatisticResource";


// Custom Components
import ProjectTabBar from "../../../../components/specific/tab/projecttabbar";

// Layouts
import Layout from "../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../components/generic/layout/singlecontentlayout";
import LinkHead from "../../../../components/generic/linker/linkhead";
import LoadingComponent from "../../../../components/generic/loadingcomponent";
import CountUp from "react-countup";
import ProjectStatistic from "../../../../lib/model/statistic/projectstatistic/model/ProjectStatistic";
import { translateMapToChartJSData } from "../../../../lib/service/statistic/StatisticChartJSTranslator";
import { Doughnut } from "react-chartjs-2";
import { useFetchAnnotatorsByProject } from "../../../../lib/service/annotator/AnnotatorResource";
import useFetchAnnotatorStatistic from "../../../../lib/service/statistic/annotatorstatistic/AnnotatorStatisticRepository";
import Annotator from "../../../../lib/model/annotator/model/Annotator";
import AnnotatorStatistic from "../../../../lib/model/statistic/annotatorstatistic/model/AnnotatorStatistic";
import StatisticComponent from "../../../../components/generic/other/StatisticComponent";

const Statistic: NextPage = () => {

    // fetch data & hooks
    const authenticated = useAuthenticated();

    const router = useRouter();
    const { user: username, project: projectname } = router.query as { user: string, project: string };

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
                <title>PhiTag : {projectname} : Statistics</title>
            </Head>

            <SingleContentLayout>


                <LinkHead icon={<FiFolder className="stroke-2" />}
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
                            href: `/phi/${username}/${projectname}/statistic`,
                            name: "Statistics",
                        },
                    ]}
                />

                <div className="font-dm-mono-medium">

                    <ProjectTabBar />

                    <div className="mt-8 flex flex-col w-full justify-center">
                        <ProjectStatisticComponent />
                        <AnnotatorStatisticComponent />
                    </div>
                </div>

            </SingleContentLayout>

        </Layout>
    );
}

export default Statistic;

const ProjectStatisticComponent = () => {
    const router = useRouter();
    const { user: username, project: projectname } = router.query as { user: string, project: string };
    const projectstatistic = useFetchProjectStatistic(username, projectname, router.isReady);

    if (projectstatistic.isLoading) {
        return <LoadingComponent />;
    }

    if (projectstatistic.isError) {
        return <div>Error</div>;
    }

    return (
        <div className="w-full flex flex-col xl:flex-row xl:mx-auto xl:justify-center xl:space-x-16">
            <div className="flex flex-col space-y-8 self-center sm:flex-row sm:space-y-0 justify-between my-8 xl:justify-center xl:flex-col xl:space-y-8">
                <CountUpComponent count={projectstatistic.statistic.getUsagecount()} label="Usages: " />
                <CountUpComponent count={projectstatistic.statistic.getLemmacount()} label="Lemmas: " />
            </div>
            <UsagesPerLemma statistic={projectstatistic.statistic} />

        </div>
    );
}

const AnnotatorStatisticComponent = () => {
    const router = useRouter();
    const { user: ownername, project: projectname } = router.query as { user: string, project: string };

    const [annotator, setAnnotator] = useState<string>("");

    const annotators = useFetchAnnotatorsByProject(ownername, projectname, router.isReady);

    return (
        <div className="w-full flex flex-col mx-auto justify-center">
            <h1 className="mx-4 text-2xl font-dm-mono-medium">Annotator Statistics</h1>
            <AnnotatorSelectionComponent annotators={annotators.annotators} selected={annotator} changeCallback={setAnnotator} />
            <AnnotatorStatisticStatisticComponent annotator={annotator} />
        </div>
    );

}

const AnnotatorSelectionComponent = ({ annotators, selected, changeCallback }: { annotators: Annotator[], selected: string, changeCallback: Function }) => {
    return (
        <div className="w-full max-w-lg mx-4 dropdown-search group font-dm-mono-medium">
            <div className="flex flex-row w-full basis-full items-center border-b-2 py-2 px-3 my-4">
                <FiUser className='basic-svg' />
                <input className="pl-3 flex flex-auto outline-none border-none font-dm-sans-medium font-bold"
                    type={"text"}
                    placeholder={selected || "Annotator Selection"}
                    value={selected}
                    contentEditable={false}
                    onChange={() => { }}
                />
            </div>


            <div className="dropdown-search-container group-hover:scale-100">
                <ul>
                    {annotators.map((item: Annotator) => {
                        return (
                            <li className="py-1 px-4 cursor-pointer hover:bg-base16-gray-100" key={item.getId().getUser()} onClick={() => changeCallback(item.getId().getUser())}>
                                &gt; {item.getId().getUser()}
                            </li>
                        )
                    })}
                </ul>
            </div>
        </div>
    );

}

const AnnotatorStatisticStatisticComponent = ({ annotator }: { annotator: string }) => {
    const router = useRouter();
    const { user: ownername, project: projectname } = router.query as { user: string, project: string };
    const annotatorStatistic = useFetchAnnotatorStatistic(ownername, projectname, annotator, router.isReady && !!annotator);

    if (!annotator) {
        return <div className="mx-auto mt-2">Please select an annotator</div>;
    }

    if (annotatorStatistic.isLoading) {
        return <LoadingComponent />;
    }

    if (annotatorStatistic.isError) {
        return <div>Error</div>;
    }

    return (
        <div className="w-full flex flex-col xl:flex-row xl:mx-auto xl:justify-center xl:space-x-16">
            <div className="flex flex-col space-y-8 self-center sm:flex-row sm:space-y-0 justify-between my-8 xl:justify-center xl:flex-col xl:space-y-8">
                <CountUpComponent count={annotatorStatistic.statistic.getAnnotations()} label="Annotations: " />
                <NumberComponent count={annotatorStatistic.statistic.getKrippendorffalpha()} label="Mean Krippendorf: " />
            </div>
            <AnnotationsPerPhases statistic={annotatorStatistic.statistic} />
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


const UsagesPerLemma = ({ statistic }: { statistic: ProjectStatistic }) => {

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
        data: translateMapToChartJSData("Usages", statistic.getUsagesPerLemmaCountMap()),

    };

    return (
        <div className="flex flex-col">
            <div className="font-dm-mono-medium text-2xl sm:text-4xl">
                Usages per Lemma:
            </div>
            <div className="flex flex-col justify-center items-center mx-auto max-w-sm pl-16">
                <Doughnut {...config} options={options} />
            </div>
        </div>
    );
}

const AnnotationsPerPhases = ({ statistic }: { statistic: AnnotatorStatistic }) => {

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
        data: translateMapToChartJSData("Annotations", statistic.getPhaseAnnotationCountMap()),

    };

    return (
        <div className="flex flex-col">
            <div className="font-dm-mono-medium text-2xl sm:text-4xl">
                Annotations per Phase:
            </div>
            <div className="flex flex-col justify-center items-center mx-auto max-w-sm pl-16">
                <Doughnut {...config} options={options} />
            </div>
        </div>
    );
}