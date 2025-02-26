import {Form, Input, Button, notification, Skeleton} from 'antd';
import {useGetGenreQuery, useUpdateGenreMutation} from "../../../services/api.genres.ts";
import {GenrePutModel} from "../../../models/genres.ts";
import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {setSpinner} from "../../../redux/spinner/spinnerSlice.ts";
import {useDispatch} from "react-redux";

const { Item } = Form;

const GenreEditForm = () => {
    const { id } = useParams<{ id: string }>();
    const [form] = Form.useForm<GenrePutModel>();
    const navigate = useNavigate();
    const { data: genre, error, isLoading } = useGetGenreQuery(Number(id));
    const [updateGenre] = useUpdateGenreMutation();
    const dispatch = useDispatch();

    const [imageUrl, setImageUrl] = useState<string | null>(null);

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
            setImageUrl(genre.imageUrl);
            form.setFieldsValue({
                name: genre.name,
                imageUrl: genre.imageUrl,
                description: genre.description
            });
        }
    }, [genre, form]);

    const onFinish = async (values: GenrePutModel) => {
        try {
            const genre = await updateGenre({...values, id: Number(id)}).unwrap();
            console.log("Update Genre", genre);
            navigate("/genres");
        } catch (err) {
            console.error("Error editing genre", err);
            notification.error({
                message: "Error editing genre",
                description: "Something went wrong",
            })
        }
    }

    const handleImageUrlChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setImageUrl(e.target.value);
    };

    return (
        <div style={{maxWidth: '600px', margin: '0 auto'}}>
            {isLoading ? (
                <Skeleton paragraph={{rows: 4}}/>
            ) : (
                <>
                    <h1 className="text-center text-4xl font-bold leading-none tracking-tight text-gray-900 mb-5">Edit
                        Genre</h1>
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
                            name="imageUrl"
                            label="Image"
                            rules={[{required: true, message: 'Image url is required!'}]}
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