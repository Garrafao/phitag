import { ReactNode } from "react";
import { FiCheckSquare, FiSquare } from "react-icons/fi";

interface IProps {
    selected: boolean;
    description: string | ReactNode;
    onClick: () => void;
}

const Checkbox: React.FC<IProps> = ({ selected, description, onClick }) => {

    return (
        <div className="flex flex-1 items-center my-1">
            <div className="mr-3" onClick={() => onClick()}>
                {selected ? <FiCheckSquare className="basic-svg" /> : <FiSquare className="basic-svg" />}
            </div>
            <div className="font-dm-mono-medium">
                {description}
            </div>
        </div>
    );

};

export default Checkbox;

