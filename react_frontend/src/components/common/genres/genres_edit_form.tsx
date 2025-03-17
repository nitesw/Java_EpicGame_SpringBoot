import {Form, Input, Button, notification, Skeleton, Upload} from 'antd';
import {useGetGenreQuery, useUpdateGenreMutation} from "../../../services/api.genres.ts";
import {GenrePutModel} from "../../../models/genres.ts";
import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {setSpinner} from "../../../redux/spinner/spinnerSlice.ts";
import {useDispatch} from "react-redux";
import {APP_ENV} from "../../../env";
import {UploadOutlined} from "@ant-design/icons";

const { Item } = Form;

const GenreEditForm = () => {
    const { id } = useParams<{ id: string }>();
    const [form] = Form.useForm<GenrePutModel>();
    const navigate = useNavigate();
    const { data: genre, error, isLoading } = useGetGenreQuery(Number(id));
    const [updateGenre] = useUpdateGenreMutation();
    const dispatch = useDispatch();

    const [imageFile, setImageFile] = useState<File | undefined>(undefined);

    useEffect(() => {
        dispatch(setSpinner(isLoading));
    }, [isLoading, dispatch]);

    useEffect(() => {
        if (error) {
            notification.error({
                message: "Error has occurred",
                description: "Something went wrong",
                placement: "top"
            });

            navigate("/genres");
        }
    }, [error, navigate]);

    useEffect(() => {
        if (genre) {
            setImageFile(undefined);
            form.setFieldsValue({
                name: genre.name,
                description: genre.description
            });
        }
    }, [genre, form]);

    const onFinish = async (values: GenrePutModel) => {
        try {
            const genreData: GenrePutModel = {
                ...values,
                id: Number(id),
                image: imageFile,
            };

            console.log(genreData);

            await updateGenre(genreData).unwrap();
            notification.success({
                message: "Successfully edited",
                description: "Genre successfully edited",
                placement: "top"
            });

            navigate("/genres");
        } catch (err) {
            console.error("Error editing genre", err);
            notification.error({
                message: "Error editing genre",
                description: "Something went wrong",
            });
        }
    }

    const beforeUpload = (file: File) => {
        const isImage = file.type.startsWith("image");
        if (!isImage) {
            notification.error({
                message: "Error changing an image",
                description: "File can only be an image",
                placement: "top"
            });
            return false;
        }
        setImageFile(file);
        return false;
    };

    return (
        <div style={{maxWidth: '600px', margin: '0 auto'}}>
            {isLoading ? (
                <Skeleton paragraph={{rows: 4}}/>
            ) : (
                <>
                    <h1 className="text-center text-4xl font-bold leading-none tracking-tight text-gray-900 mb-5">Edit Genre</h1>
                    <Form
                        form={form}
                        onFinish={onFinish}
                        layout="vertical"
                    >
                        <Item
                            name="name"
                            label="Name"
                            rules={[
                                {required: true, message: 'Name is required!'},
                                {
                                    min: 1,
                                    max: 150,
                                    message: 'Name must be greater than 1 character and less than 150 characters!',
                                },
                            ]}
                        >
                            <Input placeholder="Enter name..."/>
                        </Item>

                        <Item
                            name="image"
                            label="Image"
                        >
                            <>
                                <Upload
                                    beforeUpload={beforeUpload}
                                    showUploadList={false}
                                >
                                    <Button icon={<UploadOutlined />}>Upload Image</Button>
                                </Upload>
                                {imageFile && (
                                    <img
                                        src={URL.createObjectURL(imageFile)}
                                        alt="image"
                                        className="mt-2 w-full max-h-48 object-cover rounded-lg"
                                    />
                                )}
                                {genre?.imageUrl && !imageFile && (
                                    <img
                                        src={`${APP_ENV.REMOTE_LARGE_IMAGES_URL}${genre.imageUrl}`}
                                        alt="image"
                                        className="mt-2 w-full max-h-48 object-cover rounded-lg"
                                        draggable="false"
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
                                    Edit Genre
                                </Button>
                            </Item>
                        </div>
                    </Form>
                </>
            )}
        </div>
    )
}

export default GenreEditForm;
