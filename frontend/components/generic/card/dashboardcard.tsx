import Link from "next/link";

interface IProps {
    title: string;
    description: string;
    link: string;
}

const DashboardCard: React.FC<IProps> = ({ title, description, link }) => {

    let ratio = 'h-[100%] w-[100%]';
    let padingSize = "p-6 md:p-8";
    
    let fontsizeTitle = "text-sm lg:text-lg 2xl:text-xl";
    let fontsizeText = "text-xs lg:text-sm 2xl:text-base";


    return (
        <Link href={link} passHref>
            <div className={`shadow-md cursor-pointer hover:scale-[1.025] hover:transition-all duration-200 overflow-hidden ${ratio}`}>
                <div className={`flex flex-col ${padingSize}`}>
                    <div className="flex flex-row items-center">
                        {/* <div className="hidden 3xl:visible 3xl:flex">
                            {icon}
                        </div> */}

                        <div className={`flex font-dm-mono-medium font-bold : ${fontsizeTitle}`}>
                            {title}
                        </div>
                    </div>
                    <div className={`flex font-dm-mono-regular text-base16-gray-900 ${fontsizeText}`}>
                        {description}
                    </div>
                </div>

            </div>
        </Link>
    )

};

export default DashboardCard;