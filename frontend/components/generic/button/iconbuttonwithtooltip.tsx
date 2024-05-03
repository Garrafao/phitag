// Next Componenets
import Link from "next/link";

interface IProps {
    icon: any;
    reference?: string;
    tooltip: string;
    hide?: boolean;
}

const IconButtonWithTooltip: React.FC<IProps> = ({ icon, reference, tooltip, hide = false }) => {

    let hidden = hide ? " hidden" : "";

    return (
        <button className={"icon-button group" + hidden }>
            <Link href={reference ? reference : ""}>
                <a>
                    {icon}
                </a>
            </Link>
            <div className="icon-button-tooltip group-hover:scale-100">
                <div className="whitespace-nowrap mx-4 my-2">
                    {tooltip}
                </div>
            </div>
        </button>
    );

};

export default IconButtonWithTooltip;