import {GenreModel} from "../../../models/genres.ts";
import {DeleteTwoTone, EditTwoTone} from "@ant-design/icons";
import {Button, notification} from "antd";
import {useDeleteGenreMutation} from "../../../services/api.genres.ts";
import {Link} from "react-router-dom";
import {APP_ENV} from "../../../env";

const GenresDisplayItem = ({item, index}: {item: GenreModel, index: number}) => {
    const [deleteGenre] = useDeleteGenreMutation();

    const handleDelete = async (id: number) => {
        try {
            await deleteGenre(id).unwrap();
            notification.success({
                message: "Genre deleted",
                description: "Genre has been deleted successfully!",
                placement: "top"
            });
        } catch {
            notification.error({
                message: "Error deleting genre",
                description: "Something went wrong",
                placement: "top"
            });
        }
    };

    return (
        <tr key={index} className={`${index % 2 === 0 ? "bg-gray-50" : "bg-white"}`}>
            <th scope="row" className="px-6 py-4 font-medium text-gray-900">
                <img src={`${APP_ENV.REMOTE_MEDIUM_IMAGES_URL}${item.imageUrl}`} alt={item.name} style={{ width: "48px", borderRadius: "10px" }} draggable={false} />
            </th>
            <td className="px-6 py-4">{item.name}</td>
            <td className="px-6 py-4">{item.description}</td>
            <td className="px-6 py-4">
                <div className="flex gap-1 justify-center">
                    <Link to={`edit/${item.id}`}>
                        <Button icon={<EditTwoTone />} />
                    </Link>
                    <Button icon={<DeleteTwoTone />} onClick={() => handleDelete(item.id)} />
                </div>
            </td>
        </tr>
    )
}

export default GenresDisplayItem;