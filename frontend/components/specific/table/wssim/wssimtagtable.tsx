// services
import { useFetchInstances, useFetchPagedWSSIMTAG as useFetchPagedWSSIMTag } from "../../../../lib/service/instance/InstanceResource";

// models
import Phase from "../../../../lib/model/phase/model/Phase";

// components
import LoadingComponent from "../../../generic/loadingcomponent";
import WSSIMTag, { WSSIMTagConstructor } from "../../../../lib/model/instance/wssimtag/model/WSSIMTag";
import AddWSSIMInstanceToPhaseModal from "../../modal/addwssiminstancetophasemodal";
import { useEffect, useState } from "react";
import PageChange from "../../../generic/table/pagination";

const WSSIMTagTable: React.FC<{ phase: Phase, modalState: { open: boolean, callback: Function } }> = ({ phase, modalState }) => {

    const [page, setPage] = useState(0);
    const wssimtag = useFetchPagedWSSIMTag(phase?.getId().getOwner(), phase?.getId().getProject(), phase?.getId().getPhase(), page, !!phase);

    // Reload the data on reload
    useEffect(() => {
        wssimtag.mutate();
    }, [phase]);

    if (!phase || wssimtag.isLoading || wssimtag.isError) {
        return <LoadingComponent />;
    }

    return (
        <div>
            <div className="flex flex-col font-dm-mono-medium">
                <div className="overflow-auto">
                    <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                        <thead className="font-bold text-lg">
                            <tr>
                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Sense ID
                                </th>

                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Definition
                                </th>

                                <th scope="col"
                                    className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                    Lemma
                                </th>
                            </tr>
                        </thead>
                        <tbody className=" text-base16-gray-700">
                            {wssimtag.data.getContent().map((instance, i) => {
                                //@ts-ignore, TODO: fix this
                                let castedInstance: WSSIMTag = instance;
                                return (<tr key={castedInstance.getId().getInstanceId()}>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {castedInstance.getId().getInstanceId()}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {castedInstance.getDefinition()}
                                    </td>

                                    <td className="px-6 py-4 whitespace-nowrap">
                                        {castedInstance.getLemma()}
                                    </td>
                                </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </div>
            </div>
            <PageChange page={page} maxPage={wssimtag.data.getTotalPages()} pageChangeCallback={(p: number) => {setPage(p)}} />

            <AddWSSIMInstanceToPhaseModal isOpen={modalState.open} closeModalCallback={modalState.callback} phase={phase} mutateCallback={wssimtag.mutate} />
        </div>
    );

}

export default WSSIMTagTable;
