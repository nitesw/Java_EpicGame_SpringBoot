import {Form, Input, Button, notification, Upload} from 'antd';
import {useCreateGenreMutation} from "../../../services/api.genres.ts";
import {GenrePostModel} from "../../../models/genres.ts";
import {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {UploadOutlined} from "@ant-design/icons";
import {useDispatch} from "react-redux";
import {setSpinner} from "../../../redux/spinner/spinnerSlice.ts";

const { Item } = Form;

const GenresCreateForm = () => {
    const [createGenre, {isLoading: isGenreCreating}] = useCreateGenreMutation();
    const [form] = Form.useForm<GenrePostModel>();
    const navigate = useNavigate();

    const [imageFile, setImageFile] = useState<File | undefined>(undefined);
    const dispatch = useDispatch();

    const onFinish = async (values: GenrePostModel) => {
        try {
            const genreData: GenrePostModel = {
                name: values.name,
                description: values.description || '',
                image: imageFile,
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

    const beforeUpload = (file: File) => {
        const isImage = file.type.startsWith("image");
        if(!isImage) {
            notification.error({
                message: "Error adding an image",
                description: "File can only be an image",
                placement: "top"
            })
            return false;
        }
        setImageFile(file);
        return false;
    };

    useEffect(() => {
        dispatch(setSpinner(isGenreCreating));
    }, [isGenreCreating, dispatch]);

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
                    name="image"
                    label="Image"
                    rules={[{ required: true, message: 'Image is required!' }]}
                >
                    <>
                        <Upload beforeUpload={beforeUpload} showUploadList={false}>
                            <Button icon={<UploadOutlined/>}>Upload Image</Button>
                        </Upload>
                        {imageFile && (
                            <img
                                src={URL.createObjectURL(imageFile)}
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

export default GenresCreateForm;