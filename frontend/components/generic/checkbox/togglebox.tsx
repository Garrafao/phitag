import { FiCheckSquare, FiSquare, FiToggleLeft, FiToggleRight } from "react-icons/fi";

interface IProps {
    selected: boolean;
    left: string;
    right: string;
    onClick: () => void;
}

const Togglebox: React.FC<IProps> = ({ selected, left, right, onClick }) => {

    return (
        <div className="flex flex-1 items-center my-1">
            <div className="font-dm-mono-medium">
                {left}
            </div>
            <div className="mx-2" onClick={() => onClick()}>
                {!selected ? <FiToggleLeft className="basic-svg" /> : <FiToggleRight className="basic-svg" />}
            </div>
            <div className="font-dm-mono-medium">
                {right}
            </div>
        </div>
    );

};

export default Togglebox;

