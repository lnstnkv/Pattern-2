import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query/react";

import { GetUserPayload } from "./UsersModels";

const USERS_API_HOST = "http://localhost:8080";

export const usersApi = createApi({
  reducerPath: "usersApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `${USERS_API_HOST}/api/`,
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
