import Link from "next/link";

/* interface IProps {
    icon: any;
    items: IDropDownItem[];
}

interface IDropDownItem {
    icon: any;
    text: string;
    reference: string;
    bar?: boolean;
    onClick?: () => void;
} */

const Label = (props:any) => {

    return (
        <div style={props.style}>
            Helllooo

        <button className="dropdown-menu-button group">
           {/*  {icon} */}
            <div className="dropdown-menu-container group-hover:scale-100">
                <ul>
                   {/*  {items.map((item: IDropDownItem) => {
                        if (item.bar) {
                            return (
                                <li key={item.text} className="border-b-2 mx-2 border-base16-gray-100"></li>
                            );
                        }

                        if (item.onClick) {
                            return (
                                <li key={item.text} className="mx-4 my-2 flex flex-row" onClick={item.onClick}>
                                    {item.icon}
                                    <div className="ml-3 whitespace-nowrap">
                                        {item.text}
                                    </div>
                                </li>
                            );
                        }

                        return (
                            <li key={item.reference} className="mx-4 my-2">
                                <Link href={item.reference}>
                                    <a className="flex flex-row">
                                        {item.icon}
                                        <div className="ml-3 whitespace-nowrap">
                                            {item.text}
                                        </div>
                                    </a>
                                </Link>
                            </li>
                        );
                    })} */}
                    SDSDSD
                </ul>
                SFSADSALJKDJSJAKDAJKSH
            </div>
        </button>
        </div>

    );
};

export default Label;