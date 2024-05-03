import { ReactChild } from "react";
import SelectableItem from "../../../lib/model/interfaces/selectableitem";

interface IProps<T> {
    icon: any;
    items: Array<T>;
    onSelectFunction: (a: T) => void;
    selected: Array<T>;
    message: string | ReactChild;
}

const BasicDropdownSelect = <T extends SelectableItem>({ icon, items, selected, onSelectFunction, message }: IProps<T>) => {
    return (
        <div className="w-full dropdown-uni-selector group font-uni-corporate-bold font-bold">
            <div className="flex flex-row cursor-pointer ">
                <div className="self-center">
                    {icon}
                </div>
                <div className="flex ml-4 self-center">
                    {message}
                </div>
            </div>
            <div className="dropdown-uni-selector-container group-hover:scale-100">
                <ul>
                    {items.map((item, index: number) => {
                        return (
                            <li key={index} className={selected.find(l => l.getName() === item.getName()) ? "dropdown-selector-item-selected" : "dropdown-selector-item"}>
                                <div className="ml-3 whitespace-nowrap" onClick={() => onSelectFunction(item)}>
                                    {item.getVisiblename()}
                                </div>
                            </li>
                        );
                    })}
                </ul>
            </div>
        </div>
    );
};

export default BasicDropdownSelect;