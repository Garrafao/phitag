import React from "react";

interface IProps {
    children: React.ReactNode;
}

const SingleContentLayout: React.FC<IProps> = ({ children }) => {
    return (
        <div className="w-full xl:w-4/5 self-center md:my-10 my-6 px-4 flex">
            <div className="mt-2 md:mt-4 w-full shadow-md p-6 md:p-12">
                {children}
            </div>
        </div>
    );
}

export default SingleContentLayout;