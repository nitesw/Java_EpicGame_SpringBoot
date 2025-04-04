import { configureStore } from "@reduxjs/toolkit";
import {apiGenre} from "../services/api.genres.ts";
import {spinnerSlice} from "./spinner/spinnerSlice.ts";
import {apiGames} from "../services/api.games.ts";
import {apiAuth} from "../services/api.auth.ts";

export const store = configureStore({
    reducer: {
        spinner: spinnerSlice.reducer,
        [apiGenre.reducerPath]: apiGenre.reducer,
        [apiGames.reducerPath]: apiGames.reducer,
        [apiAuth.reducerPath]: apiAuth.reducer,
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(apiGenre.middleware, apiGames.middleware, apiAuth.middleware),
});


export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;