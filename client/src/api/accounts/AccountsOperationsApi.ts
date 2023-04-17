import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query/react";

import {
    GetAccountPayload,
    TopUpAccountPayload,
    WithDrawAccountPayload,
    TransferPayload,
} from "./AccountsModels";

const ACCOUNTS_API_HOST = "http://localhost:8001";

export const operationsApi = createApi({
    reducerPath: "operationsApi",
    baseQuery: fetchBaseQuery({
        baseUrl: `${ACCOUNTS_API_HOST}/`,
    }),
    tagTypes: ['Accounts'],

    endpoints: (build) => {
        return {
            topUpAccount: build.mutation<GetAccountPayload, TopUpAccountPayload>({
                query: ({ id, ...rest }) => ({
                    url: `operations/${id}/topUp`,
                    method: "POST",
                    body: { ...rest },
                }),
            }),
            withDrawAccount: build.mutation<
                GetAccountPayload,
                WithDrawAccountPayload
            >({
                query: ({ id, ...rest }) => ({
                    url: `operations/${id}/withdraw`,
                    method: "POST",
                    body: { ...rest },
                }),
            }),
            transfer: build.mutation<
                GetAccountPayload,
                TransferPayload
            >({
                query: ({ id, receiverId, ...rest }) => ({
                    url: `operations/${id}/transfer/${receiverId}`,
                    method: "POST",
                    body: { ...rest },
                }),
            }),
        };
    },
});

export const {
    useTopUpAccountMutation,
    useWithDrawAccountMutation,
    useTransferMutation
} = operationsApi;
