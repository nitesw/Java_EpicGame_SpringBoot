import {useGetGamesQuery} from "../../../services/api.games.ts";
import {useEffect} from "react";
import {useDispatch} from "react-redux";
import {setSpinner} from "../../../redux/spinner/spinnerSlice.ts";
import {notification, Skeleton} from "antd";
import GamesDisplayList from "../../../components/common/games/games_display_list.tsx";

const GamesListPage = () => {
    const { data: list, isLoading, error } = useGetGamesQuery();
    const dispatch = useDispatch();

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

    console.log(list);

    return(
        <div style={{maxWidth: '1000px', margin: '0 auto'}}>
            {isLoading ? (
                <Skeleton paragraph={{rows: 4}}/>
            ) : (
                <GamesDisplayList list={list ?? []} />
            )}
        </div>
    )
}

export default GamesListPage;