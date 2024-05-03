// icons
import { FiTag } from "react-icons/fi";

// Tag
import WSSIMTag from "../../../../lib/model/instance/wssimtag/model/WSSIMTag";
import { useFetchWSSIMTagsOfLemma } from "../../../../lib/service/instance/InstanceResource";


const WSSIMTagLemmasField: React.FC<{ tag: WSSIMTag }> = ({ tag }) => {

    const wssimtags = useFetchWSSIMTagsOfLemma(tag.getId().getOwner(), tag.getId().getProject(), tag.getId().getPhase(), tag.getLemma(), !!tag);

    return (
        <div className="flex flex-col">
            {
                wssimtags.data.map((wssimtag, index) => {
                    return (
                        <div key={wssimtag.getId().getInstanceId()} className="w-full shadow-md my-2 ">
                            <div className="m-8 flex flex-row">

                                <div className="my-4">
                                    <FiTag className="basic-svg" />
                                </div>

                                <div className="border-r-2 mx-4" />

                                <div className="flex-col">
                                    <p className="my-4 font-dm-mono-light text-lg overflow-auto">
                                        Definition:&nbsp;
                                        <span className="font-dm-mono-medium">
                                            {wssimtag.getDefinition()}
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    );
                })
            }
        </div>
    );

}

export default WSSIMTagLemmasField;
