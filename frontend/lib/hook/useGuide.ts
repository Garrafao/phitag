import fs from 'fs';
import path from 'path';

// Markdown
import matter from 'gray-matter';
import { micromark } from 'micromark';
import { gfm, gfmHtml } from 'micromark-extension-gfm';
import { IGuideHeader } from '../model/guides/IGuideHeader';
import GuideHeader from '../model/guides/GuideHeader';
import Guide from '../model/guides/Guide';

// Models

const guidesdir = path.join(process.cwd(), 'data/guides');

// Shamfully stolen from: https://nextjs.org/learn
export function getSortedGuideHeader() {
    const fileNames = fs.readdirSync(guidesdir);
    return fileNames.map(fileName => {
        const fullPath = path.join(guidesdir, fileName);
        const fileContents = fs.readFileSync(fullPath, 'utf8');

        const matterResult = { ...matter(fileContents).data } as IGuideHeader;
        return new GuideHeader(fileName.replace(/\.md$/, ''), 
            matterResult.title, 
            matterResult.priority,
            micromark(matterResult.description, {
                extensions: [gfm()],
                htmlExtensions: [gfmHtml()],

                // Only allow for static markdown files
                allowDangerousHtml: true
            }));
    }).sort(({ title: t1, priority: p1 }, { title: t2, priority: p2 }) => {
        return (p1 == p2) ? (t1 > t2) ? 1 : -1 : (p1 > p2) ? 1 : -1;
    }).reverse();
}

export function getAllGuidesId() {
    const fileNames = fs.readdirSync(guidesdir);
    return fileNames.map(fileName => {
        return {
            params: {
                id: fileName.replace(/\.md$/, '')
            }
        };
    });
}

export async function getGuide(id: string) {
    const fullPath = path.join(guidesdir, `${id}.md`);
    const fileContents = fs.readFileSync(fullPath, 'utf8');

    const matterResult = { ...matter(fileContents).data } as IGuideHeader;
    return new Guide(id, 
        matterResult.title,
        matterResult.priority,
        micromark(matterResult.description, {
            extensions: [gfm()],
            htmlExtensions: [gfmHtml()],

            // Only allow for static markdown files
            allowDangerousHtml: true
        }),
        micromark(matter(fileContents).content, {
            extensions: [gfm()],
            htmlExtensions: [gfmHtml()],

            // Only allow for static markdown files
            allowDangerousHtml: true
        }));
}
