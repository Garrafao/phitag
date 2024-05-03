// Next Componenets
import Link from "next/link";

interface IProps {
    icon: any;
    tooltip: string;
    onClick?: Function;
    hide?: boolean;
}

const IconButtonOnClickBottom: React.FC<IProps> = ({ icon, onClick, tooltip, hide = false }) => {

    let hidden = hide ? " hidden" : "";

    return (
        <button className={"icon-button group" + hidden } onClick={(e: any) => onClick ? onClick(e) : () => {}}>
            {icon}
            <div className="icon-button-tooltip-bottom group-hover:scale-100">
                <div className="whitespace-nowrap mx-4 my-2">
                    {tooltip}
                </div>
            </div>
        </button>
    );

};

export default IconButtonOnClickBottom;