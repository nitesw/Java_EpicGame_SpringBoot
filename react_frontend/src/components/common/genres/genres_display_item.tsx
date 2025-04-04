import {GenreModel} from "../../../models/genres.ts";
import {DeleteTwoTone, EditTwoTone, MoreOutlined} from "@ant-design/icons";
import {Button, Dropdown, MenuProps, notification} from "antd";
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

    const renderActions = () => {
        const items: MenuProps["items"] = [
            {
                key: "edit",
                icon: <EditTwoTone />,
                label: (
                    <Link to={`edit/${item.id}`}>Edit</Link>
                )
            },
            {
                key: "delete",
                icon: <DeleteTwoTone />,
                label: (
                    <span onClick={() => handleDelete(item.id)}>Delete</span>
                ),
            },
        ];

        return (
            <Dropdown menu={{ items }} trigger={["click"]}>
                <Button icon={<MoreOutlined />} />
            </Dropdown>
        );
    };


    return (
        <tr key={index} className={`${index % 2 === 0 ? "bg-gray-50" : "bg-white"}`}>
            <th scope="row" className="px-6 py-4 font-medium text-gray-900">
                <img src={`${APP_ENV.REMOTE_MEDIUM_IMAGES_URL}${item.imageUrl}`}
                     alt={item.name}
                     style={{ height: "48px", width: "48px", borderRadius: "10px" }}
                     draggable={false}
                     className="object-cover"
                />
            </th>
            <td className="px-6 py-4">{item.name}</td>
            <td className="px-6 py-4">{item.description}</td>
            <td className="px-6 py-4">
                <div className="flex gap-1 justify-center">
                    {renderActions()}
                </div>
            </td>
        </tr>
    )
}

export default GenresDisplayItem;