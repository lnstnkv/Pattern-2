import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query/react";

import {
  GetAccountPayload,
  PostAccountsPayload,
  DeleteAccountPayload,
  TopUpAccountPayload,
  WithDrawAccountPayload,
  GetAccountHistoryPayload,
} from "./AccountsModels";

const ACCOUNTS_API_HOST = "http://localhost:8000";

export const accountsApi = createApi({
  reducerPath: "accountsApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `${ACCOUNTS_API_HOST}/`,
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
        query: ({ id, ...rest }) => ({
          url: `accounts/${id}`,
          method: "POST",
          body: { rest },
        }),
      }),
      withDrawAccount: build.mutation<
        GetAccountPayload,
        WithDrawAccountPayload
      >({
        query: ({ id, ...rest }) => ({
          url: `accounts/${id}`,
          method: "POST",
          body: { rest },
        }),
      }),
      getAccountHistory: build.query<GetAccountHistoryPayload[], { id: string }>({
        query: ({ id }) => ({
          url: `accounts/${id}`,
          method: "GET",
        }),
      }),
    };
  },
});

export const {
  useGetAccountsQuery,
  useGetAccountQuery,
  useDeleteAccountMutation,
  usePostAccountMutation,
  useTopUpAccountMutation,
  useWithDrawAccountMutation,
  useGetAccountHistoryQuery,
} = accountsApi;
