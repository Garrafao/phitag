import Link from "next/link";

interface ILinkHeadProps {
    icon: any,
    links: ILinkItemProps[],
}

interface ILinkItemProps {
    href: string,
    name: string,
}

const LinkHead: React.FC<ILinkHeadProps> = ({ icon, links }) => {
    return (
        <h1 className="ml-4 flex font-dm-mono-medium font-bold text-2xl mb-8 items-center overflow-auto no-scrollbar">
            <div className="mr-2">
                {icon}
            </div>
            {links.map((link, index) => {
                return (
                    <Link href={link.href} key={index}>
                        <a className="hover:text-base16-gray-500 m-2 whitespace-nowrap">
                            {link.name} {index !== links.length - 1 ? "/" : ""}
                        </a>
                    </Link>
                );
            })}
        </h1>
    )
}

export default LinkHead;