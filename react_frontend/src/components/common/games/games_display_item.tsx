import {DeleteTwoTone, EditTwoTone, MoreOutlined} from "@ant-design/icons";
import {Button, Dropdown, MenuProps, notification} from "antd";
import {Link} from "react-router-dom";
import {APP_ENV} from "../../../env";
import {GameModel} from "../../../models/games.ts";
import {useDeleteGameMutation} from "../../../services/api.games.ts";
import noImage from "../../../assets/images/no_image.jpg";

const GamesDisplayItem = ({item, index}: {item: GameModel, index: number}) => {
    const [deleteGame] = useDeleteGameMutation();

    const handleDelete = async (id: number) => {
        try {
            await deleteGame(id).unwrap();
            notification.success({
                message: "Game deleted",
                description: "Game has been deleted successfully!",
                placement: "top"
            });
        } catch {
            notification.error({
                message: "Error deleting game",
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
                <img src={item.images[0].imageUrl ? `${APP_ENV.REMOTE_MEDIUM_IMAGES_URL}${item.images[0].imageUrl}` : noImage}
                     alt={item.title}
                     style={{ height: "48px", width: "48px", borderRadius: "10px" }}
                     draggable={false}
                     className="object-cover"
                />
            </th>
            <td className="px-6 py-4">{item.title}</td>
            <td className="px-6 py-4">{item.price}$</td>
            <td className="px-6 py-4">{item.genreName}</td>
            <td className="px-6 py-4">
                <div className="flex gap-1 justify-center">
                    {renderActions()}
                </div>
            </td>
        </tr>
    )
}

export default GamesDisplayItem;