import React from "react";

interface IProps {
    children: React.ReactNode;
}

const AdminPanelLayout: React.FC<IProps> = ({ children }) => {
    return (
        <div className="w-screen h-screen flex items-center justify-center">
            <div className="w-full h-full shadow-md  p-3">
                {children}
            </div>
        </div>
    );
}

export default AdminPanelLayout;
