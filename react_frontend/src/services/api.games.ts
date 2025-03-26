import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {APP_ENV} from "../env";
import {GameModel, GamePostModel, GamePutModel} from "../models/games";
import {serialize} from "object-to-formdata";

export const apiGames = createApi({
    reducerPath: 'game',
    baseQuery: fetchBaseQuery({ baseUrl: `${APP_ENV.REMOTE_BASE_URL}` }),
    tagTypes: ['Game'],
    endpoints: (builder) => ({
        getGames: builder.query<GameModel[], void>({
            query: () => 'games',
            providesTags: ['Game']
        }),
        getGame: builder.query<GameModel, number>({
            query: (id) => `games/${id}`,
            providesTags: (_, __, id) => [{ type: 'Game', id }]
        }),
        createGame: builder.mutation<GameModel, GamePostModel>({
            query: (newGame) => {
                try {
                    const formData = serialize(newGame);
                    return {
                        url: 'games',
                        method: 'POST',
                        body: formData,
                    };
                } catch (error) {
                    console.error('Error while creating game:', error);
                    throw error;
                }
            },
            invalidatesTags: ['Game']
        }),
        updateGame: builder.mutation<GameModel, GamePutModel>({
            query: (updatedGame) => {
                try {
                    const formData = serialize(updatedGame);
                    return {
                        url: 'games',
                        method: 'PUT',
                        body: formData,
                    };
                } catch (error) {
                    console.error('Error while updating game:', error);
                    throw error;
                }
            },
            invalidatesTags: ['Game']
        }),
        deleteGame: builder.mutation<{ success: boolean }, number>({
            query: (id) => ({
                url: `games/${id}`,
                method: 'DELETE'
            }),
            invalidatesTags: ['Game']
        })
    })
})

export const { useGetGamesQuery, useDeleteGameMutation, useCreateGameMutation, useUpdateGameMutation, useGetGameQuery } = apiGames;