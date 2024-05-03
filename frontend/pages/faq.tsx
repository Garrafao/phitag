// Next
import Head from "next/head";

// Custom Components
import ExpandableCard from "../components/generic/card/expandablecard";
import Layout from "../components/generic/layout/layout";
import ContentLayout from "../components/generic/layout/contentlayout";
import BasicLayout from "../components/generic/layout/basiclayout";


export function FAQ() {

    return (
        <BasicLayout>

            <Head>
                <title>PhiTag: FAQ</title>
            </Head>

            <ContentLayout>
                <div className='flex flex-col w-full'>

                    <div className="flex flex-col md:flex-row md:items-center md:space-x-6">

                        <div className="flex font-dm-mono-medium font-bold text-2xl">
                            FAQ
                        </div>

                    </div>

                    <div className="flex flex-col justify-center px-4 my-8 space-y-8">
                        <ExpandableCard
                            title="What is PhiTag's general idea?"
                            content="
                                PhiTag is an web-based annotation tool for text annotation. 
                                It is designed to be modular and extensible, so that it can be adapted to a variety of annotation tasks, including your own.
                                Take a look at [introductory guide](/guide/introduction) for more information."
                        />

                        <ExpandableCard
                            title="How can I contribute to PhiTag?"
                            content="
                                PhiTag is an open-source project, and we welcome contributions from the community.
                                If you are interested in contributing, please take a look at our repository on [GitHub](https://github.com/Garrafao/phitag)."
                        />

                        <ExpandableCard
                            title="Is my annotation task supported by PhiTag?"
                            content="
                                PhiTag is designed to be modular and extensible, so that it can be adapted to a variety of annotation tasks, including your own.
                                The supported tasks currently grow quickly, and we are working on adding more.
                                Take a look at the [supported tasks](/guide/supported-tasks) for more information."
                        />

                        <ExpandableCard
                            title="I have a question that is not answered here. What should I do?"
                            content="
                                If you have a question that is not answered here, please feel free to contact us with your question.
                                You can find our contact information on the [About](/about-us) page."
                        />

                    </div>
                </div>
            </ContentLayout>
        </BasicLayout>
    )

}

export default FAQ;