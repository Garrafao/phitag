import React from "react";

interface IProps {
    children: React.ReactNode;
}

const CenteredLayout: React.FC<IProps> = ({ children }) => {
    return (
        <div className="grow w-full xl:w-4/5 self-center md:my-10 my-6 px-4 flex justify-center">
            {children}
        </div>
    );
}

export default CenteredLayout;
