// Next Componenets
import Link from "next/link";
import { useState } from "react";
import { FiChevronsRight, FiHelpCircle } from "react-icons/fi";

interface IProps {
    tooltip: string;
    title: string;
    text: string;
    reference: string;
    buttontext?: string;
    linkage?: boolean;
}

const HelpButton: React.FC<IProps> = ({ reference, tooltip, title, text, buttontext, linkage }) => {

    const [showTooltipBox, setShowTooltipBox] = useState<boolean>(false);

    return (
        <div className="icon-button group cursor-pointer">
            {/* <Link href={reference ? reference : ""}> */}
            <div onClick={() => setShowTooltipBox(true)}>
                <FiHelpCircle className="basic-svg" />
            </div>
            {/* </Link> */}
            <div className="icon-button-tooltip group-hover:scale-100">
                <div className="whitespace-nowrap mx-4 my-2">
                    {tooltip}
                </div>
            </div>

            <HelpButtonTooltipBox show={showTooltipBox} onCancel={() => setShowTooltipBox(false)} reference={reference} title={title} text={text} buttontext={buttontext} linkage={linkage} />
        </div>
    );

};

export default HelpButton;

interface HelpButtonTooltipBoxProps {
    show: boolean;
    onCancel: () => void;
    reference: string;
    title: string;
    text: string;
    buttontext?: string;
    linkage?: boolean;
}

const HelpButtonTooltipBox = ({ show, onCancel, reference, title, text, buttontext, linkage = true }: HelpButtonTooltipBoxProps) => {
    if (!show) return null;

    return (
        <div className="relative z-10 font-dm-mono-medium" onClick={onCancel}>
            <div className="fixed inset-0 bg-base16-gray-500 bg-opacity-75" />

            <div className="fixed z-10 inset-0 overflow-y-auto">
                <div className="flex items-center justify-center min-h-full">
                    <div className="relative bg-white overflow-hidden shadow-md py-4 px-8  max-w-xl w-full" onClick={(e: any) => e.stopPropagation()}>
                        <div className="mx-4">
                            <div className="flex flex-col my-6">
                                <div className="font-black text-xl text-left">
                                    {title}
                                </div>
                                <div className="font-dm-mono-regular my-2 text-left">
                                    {text}
                                </div>
                            </div>
                        </div>
                        <div className={linkage ? "" : "hidden"}>
                            <Link href={reference} passHref>
                                <div className="flex flex-row divide-x-8">
                                    <button type="button" className="w-full flex flex-row justify-center items-center font-dm-mono-medium text-lg bg-base16-gray-900 text-base16-gray-100 py-2 px-10" onClick={onCancel}>
                                        {buttontext ? buttontext : "Continue Reading"} <FiChevronsRight className="basic-svg" />
                                    </button>
                                </div>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};