// Next
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from "next/router";

// React
import { useEffect } from "react";

// Markdown
import matter from "gray-matter";
import { micromark } from "micromark";
import { gfm, gfmHtml } from "micromark-extension-gfm";

// Toast
import { toast } from "react-toastify";

// Services
import useAuthenticated from "../../../../../lib/hook/useAuthenticated";
import { useFetchGuideline } from "../../../../../lib/service/guideline/GuidelineResource";
import FullLoadingPage from "../../../../../components/pages/fullloadingpage";
import Layout from "../../../../../components/generic/layout/layout";
import SingleContentLayout from "../../../../../components/generic/layout/singlecontentlayout";
import { FiBook } from "react-icons/fi";
import Link from "next/link";
import LinkHead from "../../../../../components/generic/linker/linkhead";

// Models

// Custom Components

const GuidelinePageSlug: NextPage = () => {


    const authenticated = useAuthenticated();
    const router = useRouter();

    const { user : username, project : projectname, guideline : guidelinename } = router.query as { user: string, project: string, guideline: string };

    const guideline = useFetchGuideline(username, projectname, guidelinename, router.isReady);

    const renderContent = (content: string) => {
        return micromark(matter(content).content, {
            extensions: [gfm()],
            htmlExtensions: [gfmHtml()],
        })
    }

    useEffect(() => {
        if (router.isReady && guideline.isError) {
            toast.error("An error occurred while fetching data");
            Router.push(`/phi/${username}/${projectname}`);
        }

        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }
    }, [authenticated, guideline.isError, router.isReady]);

    if (guideline.isLoading || guideline.guideline === null) {
        return <FullLoadingPage headtitle="Guideline" />;
    }

    return (
        <Layout>
            <Head>
                <title>PhiTag: Guideline : {guideline.guideline.getId().getName()}</title>
            </Head>

            <SingleContentLayout>

            <LinkHead icon={<FiBook className="stroke-2" />}
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
                        href: `/phi/${username}/${projectname}/guideline`,
                        name: "guideline",
                    },
                    {
                        href: `/phi/${username}/${projectname}/guideline/${guidelinename}`,
                        name: guidelinename,
                    }
                ]}
            />

                <div className="markdown-page" dangerouslySetInnerHTML={{ __html: renderContent(guideline.guideline.getContent()) }} />
            </SingleContentLayout>

        </Layout>
    );

}

export default GuidelinePageSlug;