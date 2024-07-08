import { useState } from "react";
import { FiFolder, FiSliders, FiUser, FiUsers } from "react-icons/fi";
import { useFetchAnnotatorsByProject } from "../../../lib/service/annotator/AnnotatorResource";
import Annotator from "../../../lib/model/annotator/model/Annotator";
import DropdownSelect from "../../generic/dropdown/dropdownselect";
import { useFetchAllStatisticAnnotationMeasureResource } from "../../../lib/service/statistic/statistisannotationmeasure/StatisticAnnotationMeasureResource";
import StatisticAnnotationMeasure from "../../../lib/model/statistic/statisticannotationmeasure/model/StatisticAnnotationMeasure";
import AddStaticHistoryCommand from "../../../lib/model/stataticsistory/command/AddStaticHistoryCommand";
import { toast } from "react-toastify";
import { saveAnnotation } from "../../../lib/service/statistic/StaticHistory";
import useStorage from "../../../lib/hook/useStorage";
import HelpButton from "../../generic/button/helpbutton";

interface CheckAgreementModalProps {
    isOpen: boolean;
    ownername: string;
    projectname: string;
    phasename: string;
}

const CheckAgreementModal: React.FC<CheckAgreementModalProps> = ({ isOpen, ownername, projectname, phasename }) => {


    const storage = useStorage();

    const annotators = useFetchAnnotatorsByProject(ownername, projectname, true);
    const annotationagreement = useFetchAllStatisticAnnotationMeasureResource(isOpen);

    const [checkAgreement, setCheckAgreement] = useState({
        annotatorname: null as unknown as Annotator,
        goldannotator: null as unknown as Annotator,
        agreementStrategy: null as unknown as StatisticAnnotationMeasure

    })

    const onConfirm = () => {
        const command = validateAgrrement(ownername, projectname, phasename, checkAgreement);

         if (command) {
            saveAnnotation(command, storage.get)
                .then((phase) => {
                    toast.success("Sucess!");

                    // close modal
                    setCheckAgreement({
                        annotatorname: null as unknown as Annotator,
                        goldannotator: null as unknown as Annotator,
                        agreementStrategy: null as unknown as StatisticAnnotationMeasure
                    });
                   /*  mutateCallback();
                    closeModalCallback(); */
                }).catch((error) => {
                    if (error?.response?.status === 500) {
                        toast.error("Error: " + error.response.data.message + "!");
                    } else {
                        toast.warning("The system is currently not available, please try again later!");
                    }
                });
        } 
    }

    if (!isOpen || !ownername || !projectname || !phasename) {
        return null;
    }
    return (
        <div className="relative z-10 font-dm-mono-medium" >

            <form className="font-dm-mono-medium">

                <div className="flex flex-row justify-between">

                    <h1 className="font-bold text-2xl">
                        Check Annotator Agreement
                    </h1>
                    <div className="px-12">
                                            <HelpButton
                                                title="Help: Agreeement"
                                                tooltip="Results"
                                                text="To check the results, please click on the history button and select the user whose results you want to see."
                                                reference=""
                                                linkage={false}
                                            />
                                        </div>

                </div>
                <div className="flex flex-col xl:flex-row justify-between my-6 space-y-4 xl:space-y-0 xl:space-x-8">

                    <div className="xl:w-1/2 flex flex-col grow items-left xl:mr-4">
                        <div className="font-bold text-lg">
                            Annotator 1
                        </div>
                        <div className="py-2 px-3 border-b-2 mt-2">
                            <DropdownSelect
                                icon={<FiUser className="basic-svg" />}
                                items={annotators.annotators}
                                selected={checkAgreement.annotatorname ? [checkAgreement.annotatorname] : []}
                                onSelectFunction={(annotatorname: Annotator) => setCheckAgreement({
                                    ...checkAgreement,
                                    annotatorname: annotatorname
                                })}
                                message={checkAgreement.annotatorname ? checkAgreement.annotatorname.getVisiblename() : "None selected yet"} />
                        </div>
                    </div>

                    <div className="xl:w-1/2 flex flex-col grow items-left xl:ml-4">
                        <div className="font-bold text-lg">
                            Annotator 2
                        </div>
                        <div className="py-2 px-3 border-b-2 mt-2">
                            <DropdownSelect
                                icon={<FiUser className="basic-svg" />}
                                items={annotators.annotators}
                                selected={checkAgreement.goldannotator ? [checkAgreement.goldannotator] : []}
                                onSelectFunction={(goldannotator: Annotator) => setCheckAgreement({
                                    ...checkAgreement,
                                    goldannotator: goldannotator
                                })}
                                message={checkAgreement.goldannotator ? checkAgreement.goldannotator.getVisiblename() : "None selected yet"} />
                        </div>
                    </div>
                    <div className="xl:w-1/2 flex flex-col grow items-left xl:ml-4">
                        <div className="font-bold text-lg">
                            Agreement Strategy
                        </div>
                        <div className="py-2 px-3 border-b-2 mt-2">
                            <DropdownSelect
                                icon={<FiSliders className="basic-svg" />}
                                items={annotationagreement.statisticAnnotationMeasures.sort((a, b) => a.getVisiblename().localeCompare(b.getVisiblename()))}
                                selected={checkAgreement.agreementStrategy ? [checkAgreement.agreementStrategy] : []}
                                onSelectFunction={(agreement: StatisticAnnotationMeasure) => setCheckAgreement({
                                    ...checkAgreement,
                                    agreementStrategy: agreement
                                })}
                                message={checkAgreement.agreementStrategy ? checkAgreement.agreementStrategy.getVisiblename() : "None selected yet"} />
                        </div>
                    </div>
                </div>



                <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100" onClick={onConfirm}>
                    Check Agreement
                </button>
            </form>

        </div>
    )
};

export default CheckAgreementModal;


function validateAgrrement(ownername: string, projectname: string, phasename: string, checkAgreement: {
    annotatorname: Annotator;
    goldannotator: Annotator;
    agreementStrategy: StatisticAnnotationMeasure;

}): AddStaticHistoryCommand | null {
    if (!ownername || !projectname || !phasename) {
        toast.warning("This shouldn't happen. Please try again.")
        return null;
    }

    if (!checkAgreement.annotatorname) {
        toast.warning("Please select first annotator.");
        return null;
    }

    if (!checkAgreement.goldannotator) {
        toast.warning("Please select second annotator.");
        return null;
    }
    if (!checkAgreement.agreementStrategy) {
        toast.warning("Please select agreement strategy.");
        return null;
    }
    if (checkAgreement.goldannotator === checkAgreement.annotatorname) {
        toast.warning("Please select different annotator.");
        return null;
    }
    
    return new AddStaticHistoryCommand(
        checkAgreement.annotatorname.getName(),
        checkAgreement.goldannotator.getName(),
        ownername,
        projectname,
        phasename,
        checkAgreement.agreementStrategy,
    );
}
