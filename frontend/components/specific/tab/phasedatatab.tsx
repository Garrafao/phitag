//React Modules
import React, { FC } from "react";

//Next Modules
import Link from 'next/link'
import { useRouter } from "next/router";
import HelpButton from "../../generic/button/helpbutton";

interface IPropsTab {
    href: string;
    title: string;
    isSelected: boolean;
}

const PhaseTabBar: React.FC<{}> = () => {

    const router = useRouter();
    const path = router.pathname;
    const { user: username, project: projectname, phase: phasename } = router.query as { user: string, project: string, phase: string };

    const isSelectedOverview = path === "/phi/[user]/[project]/[phase]";
    const isSelectedInstance = path == "/phi/[user]/[project]/[phase]/instance";
    const isSelectedJudgement = path == "/phi/[user]/[project]/[phase]/judgement";
    const isSelectedHistory = path == "/phi/[user]/[project]/[phase]/history";
    const isSelectedTask = path == "/phi/[user]/[project]/[phase]/task";
    const isSelectedStatic = path == "/phi/[user]/[project]/[phase]/statistic";

    const urlprefix = `/phi/${username}/${projectname}/${phasename}`;

    return (

        <div className="w-full flex flex-col 2xl:flex-row justify-between">

            <div className="my-2 mx-4 flex flex-col 2xl:flex-row justify-start space-y-2 2xl:space-x-10 2xl:space-y-0">

                <Tab href={`${urlprefix}`} title="Overview" isSelected={isSelectedOverview} />
                <Tab href={`${urlprefix}/instance`} title="Instances" isSelected={isSelectedInstance} />
                <Tab href={`${urlprefix}/judgement`} title="Judgements" isSelected={isSelectedJudgement} />
                <Tab href={`${urlprefix}/history`} title="History" isSelected={isSelectedHistory} />
                <Tab href={`${urlprefix}/task`} title="Tasks" isSelected={isSelectedTask} />
                <Tab href={`${urlprefix}/statistic`} title="Statistics" isSelected={isSelectedStatic} />
            </div>

            <div className="my-2 mx-4 self-end 2xl:self-center">

                <HelpButton
                    title="Help: Phases"
                    tooltip="Help: Phases"
                    text="
                    This is the phase page. 
                    The overview tab is currently empty.
                    The instances tab shows you the instances of the phase and allows you to add new instances to the phase.
                    The judgements tab shows you the judgements of different annotators of the phase and the history tab shows your judgement history, 
                    also allowing to edit your judgements. 
                    The tasks tab shows the progress of computational annotators.
                    Finnaly, the statistic tab shows you the statistics of the phase.
                    "
                    reference="/guide/explained-phase"

                />
            </div>
        </div>
    );
};

const Tab: FC<IPropsTab> = ({ href, title, isSelected }) => (

    <Link href={href}>
        <a className={isSelected ? "project-tab-selected" : "project-tab-unselected"}>
            <div className="my-2 mx-10">
                {title}
            </div>
        </a>
    </Link>
);

export default PhaseTabBar;
