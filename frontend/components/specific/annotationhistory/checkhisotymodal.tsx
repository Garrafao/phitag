import { useState } from "react";
import { FiUser} from "react-icons/fi";
import { useFetchAnnotatorsByProject } from "../../../lib/service/annotator/AnnotatorResource";
import Annotator from "../../../lib/model/annotator/model/Annotator";
import StatisticAnnotationMeasure from "../../../lib/model/statistic/statisticannotationmeasure/model/StatisticAnnotationMeasure";
import {  useFetchStatHistory } from "../../../lib/service/statistic/StaticHistory";
import useStorage from "../../../lib/hook/useStorage";

interface CheckHistoryModalProps {
    isOpen: boolean;
    ownername: string;
    projectname: string;
    phasename: string;

}

const CheckHistoryModal: React.FC<CheckHistoryModalProps> = ({ isOpen, ownername, projectname, phasename }) => {


    const storage = useStorage();

    const [annotator, setAnnotator] = useState<string>("");

    const annotators = useFetchAnnotatorsByProject(ownername, projectname, true);


    const agreementHistory = useFetchStatHistory(annotator, ownername, projectname, phasename);

    const changeCallback = (selectedAnnotator: string) => {
        setAnnotator(selectedAnnotator);
    };

    const [checkAgreement, setCheckAgreement] = useState({
        annotatorname: null as unknown as Annotator,
        goldannotator: null as unknown as Annotator,
        agreementStrategy: null as unknown as StatisticAnnotationMeasure

    })
    const convertDate = (timestamp: number) => {
        if (timestamp === 0) {
            return 'No deadline';
        }
        const date = new Date(timestamp);
        return date.toLocaleDateString('en-GB', { day: 'numeric', month: '2-digit', year: 'numeric' }) + ' ' + date.toLocaleTimeString('en-GB', { hour: 'numeric', minute: 'numeric' });
    }



    if (!isOpen) {
        return null;
    }
    return (
        <div className="relative z-10 font-dm-mono-medium p-4 bg-white rounded-lg shadow-lg">
            <div className="w-full max-w-lg mx-4 dropdown-search group font-dm-mono-medium">
                <div className="flex flex-row w-full basis-full items-center border-b-2 py-2 px-3 my-4">
                    <FiUser className='basic-svg' />
                    <input className="pl-3 flex flex-auto outline-none border-none font-dm-sans-medium font-bold"
                        type={"text"}
                        placeholder={annotator || "Annotator Selection"}
                        value={annotator}
                        contentEditable={false}
                        onChange={() => { }}
                    />
                </div>


                <div className="dropdown-search-container group-hover:scale-100">
                    <ul>
                        {annotators.annotators.map((item: Annotator) => {
                            return (
                                <li className="py-1 px-4 cursor-pointer hover:bg-base16-gray-100" key={item.getId().getUser()} onClick={() => changeCallback(item.getId().getUser())}>
                                    &gt; {item.getId().getUser()}
                                </li>
                            )
                        })}
                    </ul>
                </div>
            </div>
            <div className="overflow-auto">
                <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                    <thead className="font-bold text-lg ">
                        <tr>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Timestamp
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Annotator 1
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Annotator 2
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Agreement Strategy
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Measurement
                            </th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200 text-sm text-gray-700">
                        {agreementHistory.res.map(measurement => (
                            <tr key={measurement.getUUID()}>
                                <td className="px-6 py-4 whitespace-nowrap">
                                    {convertDate(Number.parseInt(measurement.getUUID()))}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap">
                                    {measurement.getAnnotatorName()}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap">
                                    {measurement.getGoldAnnotator()}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap">
                                    {measurement.getAnnotationmeasure()}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap">
                                    {measurement.getAgreeement()}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );

};

export default CheckHistoryModal;

