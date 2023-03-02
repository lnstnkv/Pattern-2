import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query/react";

import {
  GetAccountPayload,
  PostAccountsPayload,
  DeleteAccountPayload,
  TopUpAccountPayload,
  WithDrawAccountPayload,
  GetAccountHistoryPayload,
} from "./AccountsModels";

export const accountsApi = createApi({
  reducerPath: "recordsApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `${process.env.REACT_APP_ACCOUNTS_API_HOST}/`,
  }),

  endpoints: (build) => {
    return {
      getAccounts: build.query<GetAccountPayload[], void>({
        query: () => ({
          url: "accounts/",
          method: "GET",
        }),
      }),
      getAccount: build.query<GetAccountPayload, { id: string }>({
        query: ({ id }) => ({
          url: `accounts/${id}`,
          method: "GET",
        }),
      }),
      postAccount: build.mutation<GetAccountPayload, PostAccountsPayload>({
        query: (body) => ({
          url: "accounts/",
          method: "POST",
          body,
        }),
      }),
      deleteAccount: build.mutation<any, DeleteAccountPayload>({
        query: ({ id }) => ({
          url: `accounts/${id}`,
          method: "DELETE",
        }),
      }),
      topUpAccount: build.mutation<GetAccountPayload, TopUpAccountPayload>({
        query: ({ id }) => ({
          url: `accounts/${id}`,
          method: "POST",
        }),
      }),
      withDrawAccount: build.mutation<
        GetAccountPayload,
        WithDrawAccountPayload
      >({
        query: ({ id }) => ({
          url: `accounts/${id}`,
          method: "POST",
        }),
      }),
      getAccountHistory: build.mutation<
        GetAccountPayload,
        GetAccountHistoryPayload
      >({
        query: ({ id }) => ({
          url: `accounts/${id}`,
          method: "GET",
        }),
      }),
    };
  },
});
