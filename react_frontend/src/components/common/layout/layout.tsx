import React from "react";
import { Outlet } from "react-router-dom";
import Sidebar from "./sidebar.tsx";
import Header from "./header.tsx";

const Layout: React.FC = () => {
    return (
        <div className="flex h-screen bg-gray-100">
            <div className="flex-shrink-0">
                <Sidebar/>
            </div>
            <div className="flex flex-col flex-1 overflow-y-auto">
                <div className="sticky top-0 z-10">
                    <Header/>
                </div>
                <main className="flex-1 p-5 flex justify-center items-center w-full">
                    <div className="w-full">
                        <Outlet/>
                    </div>
                </main>
            </div>
        </div>
    );
};

export default Layout;