import { useGetGenresQuery } from "../../../services/api.genres.ts";
import GenresDisplayList from "../../../components/common/genres/genres_display_list.tsx";
import { useDispatch } from "react-redux";
import { setSpinner } from "../../../redux/spinner/spinnerSlice.ts";
import {useEffect, useState} from "react";
import { notification, Skeleton } from "antd";
import {GenreModel} from "../../../models/genres.ts";

const GenresListPage = () => {
    const { data: list, isLoading, error } = useGetGenresQuery();
    const dispatch = useDispatch();
    const [sortedList, setSortedList] = useState<GenreModel[]>([]);

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
        }
    }, [error]);

    useEffect(() => {
        if (list) {
            const tmpList = list ? [...list].sort((a, b) => a.id - b.id) : [];
            setSortedList(tmpList);
        }
    }, [list]);

    return (
        <div style={{maxWidth: '1000px', margin: '0 auto'}}>
            {isLoading ? (
                <Skeleton paragraph={{rows: 4}}/>
            ) : (
                (list?.length ?? 0) > 0 ? (
                    <GenresDisplayList list={sortedList} />
                ) : (
                    <h1 className="text-center text-5xl font-bold leading-none tracking-tight text-gray-900">No
                        Genres</h1>
                )
            )}
        </div>
    );
};

export default GenresListPage;

