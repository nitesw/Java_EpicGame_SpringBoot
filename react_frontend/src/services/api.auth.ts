import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { APP_ENV } from "../env";
import {UserRegisterModel} from "../models/users.ts";

export const apiAuth = createApi({
    reducerPath: "auth",
    baseQuery: fetchBaseQuery({ baseUrl: `${APP_ENV.REMOTE_BASE_URL}` }),
    tagTypes: ["Auth"],
    endpoints: (builder) => ({
        register: builder.mutation<{ message: string }, UserRegisterModel>({
            query: (user) => ({
                url: "auth/register",
                method: "POST",
                body: user,
            }),
            invalidatesTags: ["Auth"],
        }),
    }),
});

export const { useRegisterMutation } = apiAuth;
