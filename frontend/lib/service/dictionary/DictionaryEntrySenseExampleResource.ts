import axios from "axios";
import BACKENDROUTES from "../../BackendRoutes";

export function createDictionaryEntrySenseExample(
    senseid: string,
    entryid: string,
    dname: string,
    uname: string,
    example: string,
    order: number,
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('senseid', senseid);
    formData.append('entryid', entryid);
    formData.append('dname', dname);
    formData.append('uname', uname);
    formData.append('example', example);
    formData.append('order', order.toString());

    return axios.post(`${BACKENDROUTES.DICTIONARY}/entry/sense/example/create`, formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(res => res.data);
}

export function updateDictionaryEntrySenseExample(
    exampleid: string,
    senseid: string,
    entryid: string,
    dname: string,
    uname: string,
    example: string,
    order: number,
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('exampleid', exampleid);
    formData.append('senseid', senseid);
    formData.append('entryid', entryid);
    formData.append('dname', dname);
    formData.append('uname', uname);
    formData.append('example', example);
    formData.append('order', order.toString());

    return axios.post(`${BACKENDROUTES.DICTIONARY}/entry/sense/example/update`, formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(res => res.data);
}

export function deleteDictionaryEntrySenseExample(
    exampleid: string,
    senseid: string,
    entryid: string,
    dname: string,
    uname: string,
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('exampleid', exampleid);
    formData.append('senseid', senseid);
    formData.append('entryid', entryid);
    formData.append('dname', dname);
    formData.append('uname', uname);

    return axios.post(`${BACKENDROUTES.DICTIONARY}/entry/sense/example/delete`, formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(res => res.data);

}