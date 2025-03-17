import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { APP_ENV } from "../env";
import {GenreModel, GenrePostModel, GenrePutModel} from "../models/genres.ts";
import {serialize} from "object-to-formdata";

export const apiGenre = createApi({
    reducerPath: 'genre',
    baseQuery: fetchBaseQuery({ baseUrl: `${APP_ENV.REMOTE_BASE_URL}` }),
    tagTypes: ['Genre'],
    endpoints: (builder) => ({
        getGenres: builder.query<GenreModel[], void>({
            query: () => 'genres',
            providesTags: ['Genre'],
        }),
        getGenre: builder.query<GenreModel, number>({
            query: (id) => `genres/${id}`,
            providesTags: (_, __, id) => [{ type: 'Genre', id }]
        }),
        createGenre: builder.mutation<GenreModel, GenrePostModel>({
            query: (newGenre) => {
                try {
                    const formData = serialize(newGenre);
                    return {
                        url: 'genres',
                        method: 'POST',
                        body: formData,
                    };
                } catch (error) {
                    console.error('Error while creating genre:', error);
                    throw error;
                }
            },
            invalidatesTags: ['Genre'],
        }),
        updateGenre: builder.mutation<GenreModel, GenrePutModel>({
            query: (updatedGenre) => {
                try {
                    const formData = serialize(updatedGenre);
                    return {
                        url: 'genres',
                        method: 'PUT',
                        body: formData,
                    };
                } catch (error) {
                    console.error('Error while updating genre:', error);
                    throw error;
                }
            },
            invalidatesTags: ['Genre']
        }),
        deleteGenre: builder.mutation<{ success: boolean }, number>({
            query: (id) => ({
                url: `genres/${id}`,
                method: 'DELETE'
            }),
            invalidatesTags: ['Genre']
        })
    }),
});

export const { useGetGenresQuery, useGetGenreQuery, useCreateGenreMutation, useUpdateGenreMutation, useDeleteGenreMutation } = apiGenre;
