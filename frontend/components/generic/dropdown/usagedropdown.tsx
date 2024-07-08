import { ReactChild } from "react";
import SelectableItem from "../../../lib/model/interfaces/selectableitem";
import SelectableUsage from "../../../lib/model/interfaces/selectableusage";

interface IProps<T> {
    icon: any;
    items: Array<T>;
    onSelectFunction: (a: T) => void;
    selected: Array<T>;
    message: string | ReactChild;
}

const DropdownUsage = <T extends SelectableUsage>({ icon, items, selected, onSelectFunction, message }: IProps<T>) => {
    return (
        <div className="w-full dropdown-selector group font-dm-mono-medium ">
            <div className="flex flex-row cursor-pointer ">
                <div className="self-center">
                    {icon}
                </div>
                <div className="flex ml-4 self-center">
                    {message}
                </div>
            </div>
            <div className="dropdown-selector-container group-hover:scale-100">
                <ul>
                    {items.map((item, index: number) => {
                        return (
                            <li key={index} className={selected.find(l => l.getContext()) ? "dropdown-selector-item-selected" : "dropdown-selector-item"}>
                                <div className="ml-3 whitespace-nowrap" onClick={() => onSelectFunction(item)}>
                                    {item.getContext()}
                                </div>
                            </li>
                        );
                    })}
                </ul>
            </div>
        </div>
    );
};

export default DropdownUsage;