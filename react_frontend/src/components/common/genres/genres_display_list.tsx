import {GenreModel} from "../../../models/genres.ts";
import GenresDisplayItem from "./genres_display_item.tsx";
import {Button} from "antd";
import {PlusCircleTwoTone} from "@ant-design/icons";
import {Link} from "react-router-dom";

const GenresDisplayList = ({list} : {list: GenreModel[]}) => {
    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-center gap-2 items-center">
                <h1 className="text-center text-4xl font-bold leading-none tracking-tight text-gray-900">Genres</h1>
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
                        <th scope="col" className="px-6 py-3">Genre</th>
                        <th scope="col" className="px-6 py-3">Description</th>
                        <th scope="col" className="px-6 py-3 text-center">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {list.map((item, index: number) => (
                        <GenresDisplayItem key={item.id} item={item} index={index}/>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default GenresDisplayList;
