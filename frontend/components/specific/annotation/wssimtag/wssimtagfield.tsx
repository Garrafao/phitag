// icons
import { FiTag } from "react-icons/fi";

// Tag
import WSSIMTag from "../../../../lib/model/instance/wssimtag/model/WSSIMTag";


const WSSIMTagField: React.FC<{ tag: WSSIMTag }> = ({ tag }) => {

    return (
        <div className="w-full shadow-md ">
            <div className="m-8 flex flex-row">

                <div className="my-4">
                    <FiTag className="basic-svg" />
                </div>

                <div className="border-r-2 mx-4" />

                <div className="flex-col">
                    <p className="my-4 font-dm-mono-light text-lg overflow-auto">
                        Lemma:&nbsp;
                        <span className="font-dm-mono-medium">
                            {tag.getLemma()}
                        </span>
                    </p>

                    <p className="my-4 font-dm-mono-light text-lg overflow-auto">
                        Definition:&nbsp;
                        <span className="font-dm-mono-medium">
                            {tag.getDefinition()}
                        </span>
                    </p>
                </div>
            </div>
        </div>

    );

}

export default WSSIMTagField;