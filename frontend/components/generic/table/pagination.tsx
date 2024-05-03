import { FiArrowLeft, FiArrowRight } from "react-icons/fi";

const PageChange: React.FC<{
    page: number,
    pageChangeCallback: (arg0: number) => void,
    maxPage: number,
}> = ({
    page, pageChangeCallback, maxPage
}) => {

        return (
            <div className="flex flex-row w-full justify-between mt-8">
                <div className="flex items-center" onClick={() => {
                    if (page === 0) {
                        pageChangeCallback(maxPage - 1);
                    } else {
                        pageChangeCallback(page - 1);
                    }
                }}>
                    <div className="shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200">
                        <div className="m-6">
                            <FiArrowLeft className="h-8 w-8" />
                        </div>
                    </div>
                </div>

                <div className="flex items-center">
                    <div className="shadow-md cursor-pointer">
                        <div className="m-6">
                            <div className="font-dm-mono-regular text-base16-gray-900">
                                {page + 1} / {maxPage}
                            </div>
                        </div>
                    </div>
                </div>

                <div className="flex items-center" onClick={() => {
                    if (page === maxPage - 1) {
                        pageChangeCallback(0);
                    } else {
                        pageChangeCallback(page + 1);
                    }
                }}>
                    <div className="shadow-md cursor-pointer hover:bg-base16-gray-900 hover:text-base16-gray-100 transition-all duration-200">
                        <div className="m-6">
                            <FiArrowRight className="h-8 w-8" />
                        </div>
                    </div>
                </div>
            </div>
        )
    }

export default PageChange;