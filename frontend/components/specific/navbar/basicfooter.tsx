// Next Componenets
import Link from "next/link";

const BasicFooter: React.FC<{}> = () => {

    return (
        <div className="w-full py-4 flex justify-center bg-base16-gray-900 text-base16-gray-200 font-dm-mono-medium font-bold">

            <div className="w-full 2xl:w-3/5 self-center">

                <div className="py-2 px-6">
                    <div className="flex flex-col space-y-2 xl:flex-row justify-between">
                        <Link href="/">
                            <a className="flex items-center ">
                                Homepage
                            </a>
                        </Link>

                        <Link href="/about-us">
                            <a className="flex items-center">
                                About Us
                            </a>
                        </Link>

                        <Link href="/guide">
                            <a className="flex items-center">
                                Guides
                            </a>
                        </Link>

                        <Link href="/guideline">
                            <a className="flex items-center">
                                Guidelines
                            </a>
                        </Link>

                        <Link href="/use-cases">
                            <a className="flex items-center">
                                Use Cases
                            </a>
                        </Link>

                        <Link href="/faq">
                            <a className="flex items-center">
                                FAQ
                            </a>
                        </Link>

                        <Link href="/privacy-policy">
                            <a className="flex items-center">
                                Privacy Policy
                            </a>
                        </Link>

                        <Link href="https://www.uni-stuttgart.de/en/legal-notice/">
                            <a className="flex items-center">
                                Legal Notice
                            </a>
                        </Link>

                    </div>


                </div>

            </div>
        </div>
    );
}

export default BasicFooter;