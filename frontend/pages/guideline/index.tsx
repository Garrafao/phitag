// Next
import Head from "next/head";
import { useState } from "react";

// Icon
import { FiGrid, FiList, FiSearch } from "react-icons/fi";

// Services
import { getMatterlessData } from "../../lib/hook/useData";

// Models
import StaticData from "../../lib/model/staticdata/staticdata";

// Custom Components
import StaticGuidelineCard from "../../components/generic/card/staticguidelinecard"; 

//Layout
import ContentLayout from "../../components/generic/layout/contentlayout";
import Layout from "../../components/generic/layout/layout";
import BasicLayout from "../../components/generic/layout/basiclayout";

export default function GuidesAndGuidelines({ guidelines }: { guidelines: StaticData[] }) {

    const [search, setSearch] = useState('');
    const [grid, setGrid] = useState(false);

    return (
        <BasicLayout>
            <Head>
                <title>PhiTag: Guidelines</title>
            </Head>

            <ContentLayout>
                <div className='flex flex-col w-full'>

                    <div className="flex flex-col md:flex-row md:items-center md:space-x-6">
                        <div className="flex font-dm-mono-medium font-bold text-2xl">
                            Guidelines
                        </div>

                        <div className='flex flex-row w-full'>
                            <div className="flex basis-full items-center border-b-2 py-2 px-3 my-4">
                                <input className="pr-3 flex flex-auto outline-none border-none font-dm-sans-medium font-bold" placeholder="Search Term" type={"text"} value={search} onChange={(e) => setSearch(e.target.value)} />
                                <FiSearch className='basic-svg' />
                            </div>
                            <div className="hidden xl:visible xl:flex items-center my-4 ml-4">
                                {grid ?
                                    <button className="flex flex-auto font-dm-mono-medium font-black" onClick={() => setGrid(false)}>
                                        <FiGrid className='basic-svg' />
                                    </button>
                                    :
                                    <button className="flex flex-auto font-dm-mono-medium font-black" onClick={() => setGrid(true)}>
                                        <FiList className='basic-svg' />
                                    </button>}
                            </div>
                        </div>
                    </div>

                    <div className={grid ? "project-grid" : "project-list"}>
                        {
                            guidelines.filter(guideline => guideline.id.toLowerCase().includes(search.toLowerCase())).map((guideline) => (
                                <StaticGuidelineCard key={guideline.id} guideline={guideline} />
                            ))
                        }
                    </div>

                </div>
            </ContentLayout>

        </BasicLayout>
    );

}

export async function getStaticProps() {
    const staticData: StaticData[] = getMatterlessData("data/guidelines");
    return {
        props: {
            guidelines: JSON.parse(JSON.stringify(staticData))
        }
    };
}