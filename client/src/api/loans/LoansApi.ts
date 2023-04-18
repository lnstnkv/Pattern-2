import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query/react";

import {
  GetLoanPayload,
  PostLoanPayload,
  GetTariffPayload,
} from "./LoansModels";

const LOANS_API_HOST = "http://localhost:8082";

export const loansApi = createApi({
  reducerPath: "loansApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `${LOANS_API_HOST}/api/`,
    prepareHeaders: (headers) => {
      const token = localStorage.getItem('token');
      if (token) {
        headers.set('authorization', `Bearer ${token}`)
      }
      return headers
    }
  }),
  tagTypes: ["Credits"],

  endpoints: (build) => {
    return {
      getLoans: build.query<GetLoanPayload[], void>({
        query: () => ({
          url: "credits",
          method: "GET",
        }),
        providesTags: [{ type: 'Credits' }],

      }),
      getTariffs: build.query<GetTariffPayload[], void>({
        query: () => ({
          url: "tariffs",
          method: "GET",
        }),
      }),
      getRating: build.query<any, { id: number }>({
        query: ({ id }) => ({
          url: `ratings/${id}`,
          method: "GET",
        }),
      }),
      getPayments: build.query<any, { id: string }>({
        query: ({ id }) => ({
          url: `payments/${id}`,
          method: "GET",
        }),
      }),
      getOverduePayments: build.query<any, { id: string }>({
        query: ({ id }) => ({
          url: `payments/overdue/${id}`,
          method: "GET",
        }),
      }),
      postLoan: build.mutation<GetLoanPayload, PostLoanPayload>({
        query: (body) => ({
          url: "credits",
          method: "POST",
          body,
        }),
        invalidatesTags: [{ type: "Credits" }],
      }),
    };
  },
});

export const { useGetLoansQuery, usePostLoanMutation, useGetTariffsQuery, useGetRatingQuery, useGetOverduePaymentsQuery, useGetPaymentsQuery } =
  loansApi;
