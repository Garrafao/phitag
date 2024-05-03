import React from "react";

interface IProps {
    children: React.ReactNode;
}

const ContentLayout: React.FC<IProps> = ({ children }) => {
    return (
        <div className="grow w-full xl:w-4/5 self-center md:my-10 my-6 px-4 flex">
            {children}
        </div>
    );
}

export default ContentLayout;
