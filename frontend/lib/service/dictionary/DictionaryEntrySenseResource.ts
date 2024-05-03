import axios from "axios";
import BACKENDROUTES from "../../BackendRoutes";

export function createDictionaryEntrySense(
    entryid: string,
    dname: string,
    uname: string,
    definition: string,
    order: number,
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('entryid', entryid);
    formData.append('dname', dname);
    formData.append('uname', uname);
    formData.append('definition', definition);
    formData.append('order', order.toString());

    return axios.post(`${BACKENDROUTES.DICTIONARY}/entry/sense/create`, formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(res => res.data);

}

export function updateDictionaryEntrySense(
    senseid: string,
    entryid: string,
    dname: string,
    uname: string,
    definition: string,
    order: number,
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('senseid', senseid);
    formData.append('entryid', entryid);
    formData.append('dname', dname);
    formData.append('uname', uname);
    formData.append('definition', definition);
    formData.append('order', order.toString());

    return axios.post(`${BACKENDROUTES.DICTIONARY}/entry/sense/update`, formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(res => res.data);
}

export function deleteDictionaryEntrySense(
    senseid: string,
    entryid: string,
    dname: string,
    uname: string,
    get: Function = () => { }
) {
    const token = get('JWT') ?? '';

    const formData = new FormData();
    formData.append('senseid', senseid);
    formData.append('entryid', entryid);
    formData.append('dname', dname);
    formData.append('uname', uname);

    return axios.post(`${BACKENDROUTES.DICTIONARY}/entry/sense/delete`, formData,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'multipart/form-data'
            }
        }).then(res => res.data);
}