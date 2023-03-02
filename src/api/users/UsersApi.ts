import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query/react";

import { GetUserPayload } from "./UsersModels";

export const usersApi = createApi({
  reducerPath: "usersApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `${process.env.REACT_APP_USERS_API_HOST}/api/`,
  }),

  endpoints: (build) => {
    return {
      getUsers: build.query<GetUserPayload[], void>({
        query: () => ({
          url: "users/",
          method: "GET",
        }),
      }),
      getUser: build.query<GetUserPayload, { id: string }>({
        query: ({ id }) => ({
          url: `users/${id}`,
          method: "GET",
        }),
      }),
    };
  },
});
