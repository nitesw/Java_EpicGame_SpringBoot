import React from "react";
import { Link } from "react-router-dom";

const Sidebar: React.FC = () => {
    return (
        <aside className="w-64 bg-gray-800 text-white h-full shadow-lg">
            <div className="p-4 font-bold text-lg">Menu</div>
            <ul className="space-y-2 px-4">
                <li>
                    <Link to="/" style={{color:'white'}}>
                        <span className="block p-2 hover:bg-gray-700 rounded" style={{cursor: "pointer"}}>
                            Home
                        </span>
                    </Link>
                </li>
                <li>
                    <Link to="/genres" style={{color:'white'}}>
                        <span className="block p-2 hover:bg-gray-700 rounded" style={{cursor: "pointer"}}>
                            Genres
                        </span>
                    </Link>
                </li>
                <li>
                    <Link to="/about" style={{color:'white'}}>
                        <span className="block p-2 hover:bg-gray-700 rounded" style={{cursor: "pointer"}}>
                            About
                        </span>
                    </Link>
                </li>
                <li>
                    <Link to="/contact" style={{color:'white'}}>
                        <span className="block p-2 hover:bg-gray-700 rounded" style={{cursor: "pointer"}}>
                            Contact
                        </span>
                    </Link>
                </li>
            </ul>
        </aside>
    );
};

export default Sidebar;