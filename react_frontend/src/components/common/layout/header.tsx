import React from "react";
import {Link} from "react-router-dom";

const Header: React.FC = () => {
    return (
        <header className="bg-white shadow p-4 flex justify-between items-center">
            <Link to="/">
                <h3 className="text-2xl font-bold leading-none tracking-tight text-gray-900">
                    Epic Games
                </h3>
            </Link>
            <nav>
                <ul className="flex gap-4">
                    <li>
                        <Link to="/register">
                            <span className="text-gray-600 hover:text-blue-500" style={{cursor: "pointer"}}>
                                Register
                            </span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/login">
                            <span className="text-gray-600 hover:text-blue-500" style={{cursor: "pointer"}}>
                                Login
                            </span>
                        </Link>
                    </li>
                </ul>
            </nav>
        </header>
    );
};

export default Header;