//React Modules
import React, { FC } from "react";

//Next Modules
import Link from 'next/link'
import { useRouter } from "next/router";

interface IPropsTab {
    href: string;
    title: string;
    isSelected: boolean;
}

const AdminTabBar: React.FC<{}> = () => {

    const router = useRouter();
    const path = router.pathname;

    const isSelectedOverview = path == "/admin/reports";


    const urlprefix = `/admin`;

    return (
        <div className="my-2 mx-4 flex flex-col 2xl:flex-row justify-start space-y-2 2xl:space-x-10 2xl:space-y-0">
            <Tab href={`${urlprefix}/reports`} title="Reports" isSelected={isSelectedOverview} />
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

export default AdminTabBar;
