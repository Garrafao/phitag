// Next Modules
import Head from 'next/head'

// Data

// Custom Components
import BasicLayout from '../components/generic/layout/basiclayout'
import ContentLayout from '../components/generic/layout/contentlayout'

export default function Home({ staticData }: { staticData: Array<any> }) {

    const warning: string = " Please note, this is an early version of the finished app, " +
        "hence there will be frequent changes to API-structure and how data is handled. " +
        "In some cases, this might lead to data loss. " +
        "We do daily backups of the database, so if you experience any data loss, please contact us. ";

    return (
        <BasicLayout>
            <Head>
                <title>PhiTag</title>
            </Head>

            <ContentLayout>
                <div className="flex flex-col">
                    <div className="portrait:text-center font-uni-corporate-bold font-bold text-base16-gray-900 text-4xl">
                        A modern and intuitive platform for text annotation!
                    </div>
                    <div className='mt-8'>
                        <div className="mt-16 portrait:text-center font-uni-corporate-bold font-bold text-base16-gray-900 text-4xl">
                            WARNING - EARLY VERSION - DATA LOSS POSSIBLE
                        </div>

                        <div className="w-full shadow-md flex flex-col my-4 bg-base16-yellow">
                            <div className='my-8 mx-8'>
                                <div className="ml-4 font-uni-corporate-bold font-black text-lg">
                                    WARNING
                                </div>
                                <div className="my-2 ml-4 font-uni-corporate-regular text-base16-gray-900">
                                    <div className="" dangerouslySetInnerHTML={{ __html: warning }} />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </ContentLayout>
        </BasicLayout>

    )
}