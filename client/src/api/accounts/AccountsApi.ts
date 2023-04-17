import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query/react";

import {
  GetAccountPayload,
  PostAccountsPayload,
  DeleteAccountPayload,
  TopUpAccountPayload,
  WithDrawAccountPayload,
  GetAccountHistoryPayload,
  GetAllAcoountsPayload,
} from "./AccountsModels";

const ACCOUNTS_API_HOST = "http://localhost:8000";

export const accountsApi = createApi({
  reducerPath: "accountsApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `${ACCOUNTS_API_HOST}/`,
  }),
  tagTypes: ['Accounts'],

  endpoints: (build) => {
    return {
      getAccounts: build.query<GetAccountPayload[], void>({
        query: () => ({
          url: "accounts/",
          method: "GET",
        }),
        providesTags: ['Accounts'],
        transformResponse: (response: GetAllAcoountsPayload, meta, arg) => response.accounts
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
        invalidatesTags: ['Accounts'],
      }),
      deleteAccount: build.mutation<any, DeleteAccountPayload>({
        query: ({ id }) => ({
          url: `accounts/${id}`,
          method: "DELETE",
        }),
        invalidatesTags: ['Accounts'],
      }),

      getAccountHistory: build.query<GetAccountHistoryPayload[], { id: string }>({
        query: ({ id }) => ({
          url: `accounts/${id}/history`,
          method: "GET",
        }),
        transformResponse: (response: any, meta, arg) => response.operations
      }),
    };
  },
});

export const {
  useGetAccountsQuery,
  useGetAccountQuery,
  useDeleteAccountMutation,
  usePostAccountMutation,
  useGetAccountHistoryQuery,
} = accountsApi;
