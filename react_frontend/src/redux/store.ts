import { configureStore } from "@reduxjs/toolkit";
import {apiGenre} from "../services/api.genres.ts";
import {spinnerSlice} from "./spinner/spinnerSlice.ts";

export const store = configureStore({
    reducer: {
        spinner: spinnerSlice.reducer,
        [apiGenre.reducerPath]: apiGenre.reducer,
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(apiGenre.middleware),
});


export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;