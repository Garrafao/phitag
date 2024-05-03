import fs from 'fs';
import path from 'path';

// other
import matter from 'gray-matter';
import { micromark } from 'micromark';
import { gfm, gfmHtml } from 'micromark-extension-gfm';

// Models
import { IStaticData } from '../model/staticdata/istaticdata';
import StaticData from '../model/staticdata/staticdata';

// Shamfully stolen from: https://nextjs.org/learn
export function getSortedDirectoryData(directory: string): any {
    const fileNames = fs.readdirSync(directory);
    return fileNames.map(fileName => {

        const fullPath = path.join(directory, fileName);

        if (fs.statSync(fullPath).isDirectory()) {
            return getSortedDirectoryData(fullPath);
        }

        const fileContents = fs.readFileSync(fullPath, 'utf8');

        const matterResult = { ...matter(fileContents).data } as IStaticData;
        return new StaticData(fileName.replace(/\.md$/, ''), matterResult.title, matterResult.imagepath, matterResult.imagereversed, '', micromark(matter(fileContents).content, {
            extensions: [gfm()],
            htmlExtensions: [gfmHtml()],

            // Only allow for static markdown files
            allowDangerousHtml: true
        }));

    }).sort(({ id: a }, { id: b }) => {
        return (a < b) ? 1 : -1;
    }).reverse();
}

export function getSpecificDataFromDirectory(directory: string, id: string): any {
    return getSortedDirectoryData(path.join(process.cwd(), directory)).find(({ id: a }: { id: string }) => {
        return a === id;
    });
}

export function getMatterlessData(directory: string): any {
    return fs.readdirSync(path.join(process.cwd(), directory)).map(fileName => {

        const fileContents = fs.readFileSync(path.join(directory, fileName), 'utf8');

        const formattedContent = micromark(matter(fileContents).content, {
            extensions: [gfm()],
            htmlExtensions: [gfmHtml()],

            // Only allow for static markdown files
            allowDangerousHtml: true
        })

        return new StaticData(fileName.replace(/\.md$/, ''), fileName.replace(/\.md$/, '').toUpperCase(), '', false, formattedContent.substring(0, 300) + '...', formattedContent);

    }).sort(({ id: a }, { id: b }) => {
        return (a < b) ? 1 : -1;
    });
}

export function getMatterlessId(directory: string): any[] {
    return fs.readdirSync(path.join(process.cwd(), directory)).map(fileName => {
        return {
            params: {
                id: fileName.replace(/\.md$/, '')
            }
        };
    });
}

export function getSpecificMatterlessData(directory: string, id: string): any {
    return getMatterlessData(directory).find(({ id: a }: { id: string }) => {
        return (a) === (id);
    });
}