import { Form, Input, Button, notification } from 'antd';
import {useCreateGenreMutation} from "../../../services/api.genres.ts";
import {GenrePostModel} from "../../../models/genres.ts";
import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";

const { Item } = Form;

const GenreCreateForm = () => {
    const [createGenre] = useCreateGenreMutation();
    const [form] = Form.useForm<GenrePostModel>();
    const navigate = useNavigate();

    const [imageUrl, setImageUrl] = useState<string | null>(null);

    const onFinish = async (values: GenrePostModel) => {
        try {
            const genreData: GenrePostModel = {
                name: values.name,
                description: values.description || '',
                imageUrl: imageUrl || '',
            }

            await createGenre(genreData).unwrap();
            notification.success({
                message: "Successfully created",
                description: "Genre successfully created",
                placement: "top"
            })

            navigate("/genres");
        } catch {
            notification.error({
                message: "Error creating genre",
                description: "Something went wrong",
                placement: "top"
            })
        }
    }

    const handleImageUrlChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setImageUrl(e.target.value);
    };

    return (
        <div style={{maxWidth: '600px', margin: '0 auto'}}>
            <h1 className="text-center text-4xl font-bold leading-none tracking-tight text-gray-900 mb-5">Create Genre</h1>
            <Form form={form} onFinish={onFinish} layout="vertical">
            <Item
                    name="name"
                    label="Name"
                    rules={[
                        { required: true, message: 'Name is required!' },
                        {
                            min: 1,
                            max: 150,
                            message: 'Name must be greater than 1 character and less than 150 characters!',
                        },
                    ]}
                >
                    <Input placeholder="Enter name..." />
                </Item>

                <Item
                    name="imageUrl"
                    label="Image"
                    rules={[{ required: true, message: 'Image url is required!' }]}
                >
                    <>
                        <Input
                            placeholder="Enter genre image URL..."
                            value={imageUrl || ''}
                            onChange={handleImageUrlChange}
                        />
                        {imageUrl && (
                            <img
                                src={imageUrl}
                                alt="image"
                                className="mt-2 w-full max-h-48 object-cover rounded-lg"
                            />
                        )}
                    </>
                </Item>

                <Item name="description" label="Description">
                    <Input.TextArea rows={6} placeholder="Enter description..."/>
                </Item>

                <div style={{display: "flex", flexDirection: "row", width: "100%", gap: 10}}>
                    <Item style={{flex: 1}}>
                        <Link to="/genres">
                            <Button block variant="outlined">
                                Go Back
                            </Button>
                        </Link>
                    </Item>
                    <Item style={{flex: 1}}>
                        <Button type="primary" htmlType="submit" block>
                            Create Genre
                        </Button>
                    </Item>
                </div>
            </Form>
        </div>
    )
}

export default GenreCreateForm;