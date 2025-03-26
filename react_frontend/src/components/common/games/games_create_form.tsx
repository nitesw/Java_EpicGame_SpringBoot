import {Form, Input, Button, notification, Upload, DatePicker, InputNumber, Select, UploadFile} from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { useGetGenresQuery } from '../../../services/api.genres.ts';
import { useCreateGameMutation } from '../../../services/api.games.ts';
import { GamePostModel } from '../../../models/games.ts';
import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setSpinner } from '../../../redux/spinner/spinnerSlice.ts';
import {DragDropContext, Droppable, Draggable, DropResult} from '@hello-pangea/dnd';
import dayjs from 'dayjs';

const { Item } = Form;
const MAX_COUNT = 5;

const GamesCreateForm = () => {
    const [createGame, { isLoading: isGameCreating }] = useCreateGameMutation();
    const [form] = Form.useForm<GamePostModel>();
    const { data: genres, isLoading: isGenresLoading } = useGetGenresQuery();
    const [fileList, setFileList] = useState<UploadFile[]>([]);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(setSpinner(isGenresLoading));
    }, [isGenresLoading, dispatch]);

    useEffect(() => {
        dispatch(setSpinner(isGameCreating));
    }, [isGameCreating, dispatch]);

    const beforeUpload = () => false;

    const handleUploadChange = (info: { fileList: UploadFile[] }) => {
        const availableSlots = MAX_COUNT - fileList.length;
        if (availableSlots <= 0) {
            notification.error({
                message: 'Maximum images reached',
                description: `You can upload a maximum of ${MAX_COUNT} images.`,
                placement: 'top',
            });
            return;
        }
        const newFiles = info.fileList
            .slice(0, availableSlots)
            .map((file, index) => ({
                ...file,
                uid: file.uid || `${Date.now()}-${index}`,
                order: index,
            }));
        const updatedFileList = [...fileList, ...newFiles];
        setFileList(updatedFileList);
        form.setFieldsValue({ images: updatedFileList });
    };

    const handleRemove = (file: UploadFile) => {
        const newFileList = fileList.filter(f => f.uid !== file.uid);
        setFileList(newFileList);
        form.setFieldsValue({ images: newFileList });
    };

    const onDragEnd = (result: DropResult) => {
        if (!result.destination) return;
        const reorderedFiles = Array.from(fileList);
        const [removed] = reorderedFiles.splice(result.source.index, 1);
        reorderedFiles.splice(result.destination.index, 0, removed);
        const finalList = reorderedFiles.map((file, index) => ({
            ...file,
            order: index,
        }));
        setFileList(finalList);
        form.setFieldsValue({ images: finalList });
    };

    const onFinish = async (values: GamePostModel) => {
        try {
            const gameData: GamePostModel = {
                ...values,
                releaseDate: dayjs(values.releaseDate).format('YYYY-MM-DD'),
                isFree: false,
                images: fileList.map((f) => f.originFileObj as File),
            };

            console.log(gameData);
            await createGame(gameData).unwrap();
            notification.success({
                message: 'Successfully created',
                description: 'Game successfully created',
                placement: 'top',
            });
            navigate('/games');
        } catch {
            notification.error({
                message: 'Error creating game',
                description: 'Something went wrong',
                placement: 'top',
            });
        }
    };

    return (
        <div style={{ maxWidth: '600px', margin: '0 auto' }}>
            <h1 className="text-center text-4xl font-bold mb-5">Create Game</h1>
            <Form form={form} onFinish={onFinish} layout="vertical">
                <Item
                    name="title"
                    label="Title"
                    rules={[
                        { required: true, message: 'Title is required!' },
                        { min: 1, max: 100, message: 'Title must be between 1 and 100 characters!' },
                    ]}
                >
                    <Input placeholder="Enter title..." />
                </Item>

                <div style={{ display: 'flex', gap: 10 }}>
                    <Item
                        name="developer"
                        label="Developer"
                        rules={[
                            { required: true, message: 'Developer is required!' },
                            { min: 1, max: 100, message: 'Developer must be between 1 and 100 characters!' },
                        ]}
                        style={{ flex: 1 }}
                    >
                        <Input placeholder="Enter developer..." />
                    </Item>
                    <Item
                        name="publisher"
                        label="Publisher"
                        rules={[
                            { required: true, message: 'Publisher is required!' },
                            { min: 1, max: 100, message: 'Publisher must be between 1 and 100 characters!' },
                        ]}
                        style={{ flex: 1 }}
                    >
                        <Input placeholder="Enter publisher..." />
                    </Item>
                </div>

                <div style={{display: 'flex', gap: 10}}>
                    <Item
                        name="genreId"
                        label="Genre"
                        rules={[{ required: true, message: 'Genre is required!' }]}
                        style={{ flex: 1 }}
                    >
                        <Select
                            placeholder="Choose the genre..."
                            loading={isGenresLoading}
                            options={
                                genres?.map((genre) => ({
                                    value: genre.id,
                                    label: genre.name,
                                })) ?? []
                            }
                        />
                    </Item>
                    <Item
                        name="releaseDate"
                        label="Release Date"
                        rules={[{required: true, message: 'Release Date is required!'}]}
                        style={{flex: 1}}
                    >
                        <DatePicker placeholder="Enter release date..." style={{width: '100%'}}/>
                    </Item>
                    <Item
                        name="price"
                        label="Price"
                        rules={[
                            { required: true, message: 'Price is required!' },
                            { type: 'number', min: 1, message: 'Price must be greater than 1!' },
                        ]}
                        style={{ flex: 1 }}
                    >
                        <InputNumber placeholder="Enter price..." style={{ width: '100%' }} />
                    </Item>
                </div>

                <Item name="images" label="Images" rules={[{required: true, message: 'Images is required!'}]}>
                    <DragDropContext onDragEnd={onDragEnd}>
                        <Droppable droppableId="upload-list" direction="horizontal">
                            {(provided) => (
                                <div
                                    ref={provided.innerRef}
                                    {...provided.droppableProps}
                                    className="flex flex-wrap"
                                    style={{width: "100%", gap: "22px"}}
                                >
                                    {fileList.map((file, index) => (
                                        <Draggable key={file.uid} draggableId={file.uid} index={index}>
                                            {(provided) => (
                                                <div
                                                    ref={provided.innerRef}
                                                    {...provided.draggableProps}
                                                    {...provided.dragHandleProps}
                                                >
                                                    <Upload
                                                        listType="picture-card"
                                                        fileList={[file]}
                                                        onRemove={() => handleRemove(file)}
                                                    />
                                                </div>
                                            )}
                                        </Draggable>
                                    ))}
                                    {provided.placeholder}
                                    {fileList.length < 5 ? (
                                        <Upload
                                            multiple
                                            listType="picture-card"
                                            beforeUpload={beforeUpload}
                                            onChange={handleUploadChange}
                                            fileList={[]}
                                            maxCount={MAX_COUNT}
                                            accept="image/*"
                                        >
                                            <div>
                                                <UploadOutlined />
                                                <div style={{ marginTop: 8 }}>Upload</div>
                                            </div>
                                        </Upload>
                                    ) : (
                                        ""
                                    )}
                                </div>
                            )}
                        </Droppable>
                    </DragDropContext>
                </Item>

                <div style={{ display: 'flex', gap: 10, marginTop: '10px' }}>
                    <Item style={{ flex: 1 }}>
                        <Link to="/games">
                            <Button block>Go Back</Button>
                        </Link>
                    </Item>
                    <Item style={{ flex: 1 }}>
                        <Button type="primary" htmlType="submit" block disabled={isGameCreating}>
                            {isGameCreating ? 'Saving...' : 'Create Game'}
                        </Button>
                    </Item>
                </div>
            </Form>
        </div>
    );
};

export default GamesCreateForm;
