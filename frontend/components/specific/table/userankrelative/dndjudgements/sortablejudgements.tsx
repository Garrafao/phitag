import { CSS } from "@dnd-kit/utilities";

// Usage
import { useSortable } from "@dnd-kit/sortable";
import { toast } from "react-toastify";

interface IProps {
    rank: any,
    rankId: string
}

const SortableJudgements: React.FC<IProps> = ({ rank, rankId }) => {



    const { attributes, listeners, setNodeRef, transform, transition } = useSortable({
        id: rankId
    });

    const styles = {
        transform: CSS.Transform.toString(transform),
        transition,
        cursor: 'grab',
    };
    return (
            
            <div style={styles} ref={setNodeRef} {...attributes} {...listeners} className="w-full flex flex-row my-2 items-center justify-between xl:justify-center xl:space-x-6">
                <div
                    className="flex shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200 font-dm-mono-medium"

                    style={{ minWidth: "0", overflow: "hidden" }}>
                    <div className="w-auto min-w-3 h-3 m-6 text-center text-lg ">
                        {rank}
                    </div>
                </div>

            </div>
    )

}

export default SortableJudgements;
