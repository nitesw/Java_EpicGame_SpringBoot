import {Button, Empty} from "antd";
import {PlusCircleTwoTone} from "@ant-design/icons";
import {Link} from "react-router-dom";
import {GameModel} from "../../../models/games.ts";
import GamesDisplayItem from "./games_display_item.tsx";

const GamesDisplayList = ({list} : {list: GameModel[]}) => {
    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-center gap-2 items-center">
                <h1 className="text-center text-4xl font-bold leading-none tracking-tight text-gray-900">Games</h1>
                <Link to="create">
                    <Button icon={<PlusCircleTwoTone />} />
                </Link>
            </div>

            <div className="relative overflow-x-auto shadow-lg rounded-lg border border-gray-200">
                <table className="w-full text-sm text-left text-gray-700">
                    <thead
                        className="text-xs font-semibold uppercase bg-gray-100 text-gray-800 border-b border-gray-300">
                    <tr>
                        <th scope="col" className="px-6 py-3">Image</th>
                        <th scope="col" className="px-6 py-3">Title</th>
                        <th scope="col" className="px-6 py-3">Price</th>
                        <th scope="col" className="px-6 py-3">Genre</th>
                        <th scope="col" className="px-6 py-3 text-center">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                        {list.length > 0 ? (
                            list.map((item, index) => (
                                <GamesDisplayItem key={item.id} item={item} index={index}/>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={5}
                                    style={{textAlign: 'center', padding: '20px', height: '400px'}}>
                                    <Empty />
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default GamesDisplayList;
