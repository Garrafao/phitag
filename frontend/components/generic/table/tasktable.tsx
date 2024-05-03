// services
import { useFetchUsages } from "../../../lib/service/phitagdata/PhitagDataResource";

// model
import Task from "../../../lib/model/tasks/model/Task";

// components
import LoadingComponent from "../loadingcomponent";
import Link from "next/link";


const TaskTable: React.FC<{ tasks: Task[], showPhase: boolean }> = ({ tasks, showPhase = false }) => {

    if (!tasks) {
        return <LoadingComponent />;
    }

    return (
        <div className="flex flex-col font-dm-mono-medium">
            <div className="overflow-auto">
                <table className="min-w-full border-b-[1px] border-base16-gray-200 divide-y divide-base16-gray-200">
                    <thead className="font-bold text-lg">
                        <tr>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider whitespace-nowrap">
                                Computational Annotator
                            </th>
                            {showPhase && <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Phase
                            </th>}
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider ">
                                Task Owner
                            </th>
                            <th scope="col"
                                className="px-6 py-3 text-left uppercase tracking-wider">
                                Status
                            </th>
                        </tr>
                    </thead>
                    <tbody className=" text-base16-gray-700">
                        {tasks.map((task, i) => (
                            <tr key={i}>

                                <td className="px-6 py-4 whitespace-nowrap">
                                    {task.getBotname()}
                                </td>

                                {showPhase && <td className="px-6 py-4 whitespace-nowrap">
                                    <Link href={`/phi/${task.getPhase().getId().getOwner()}/${task.getPhase().getId().getProject()}/${task.getPhase().getId().getPhase()}`}>
                                        {task.getPhase().getName()}
                                    </Link>
                                </td>}

                                <td className="px-6 py-4 whitespace-nowrap">
                                    {task.getTaskowner()}
                                </td>

                                <td className="px-6 py-4 whitespace-nowrap">
                                    <StatusDot status={task.getStatus().getName()} /> {task.getStatus().getVisiblename()}
                                </td>

                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );

}

export default TaskTable;

/**
 * Creates a colored dot for the given status
 * - dark green for completed
 * - light green for in progress
 * - orange for pending
 * - red for failed
 * @param status
 * @returns {JSX.Element}
 */
const StatusDot: React.FC<{ status: string }> = ({ status }) => {
    
        let color = "bg-red-500";
        if (status === "TASK_COMPLETED") {
            color = "bg-green-500";
        } else if (status === "TASK_STARTED") {
            color = "bg-green-900";
        } else if (status === "TASK_PENDING") {
            color = "bg-orange-500";
        }
    
        return (
            <span className={`inline-block h-2 w-2 rounded-full ${color}`}/>
        );
    }