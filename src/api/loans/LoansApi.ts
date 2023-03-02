import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query/react";

import {
  GetLoanPayload,
  PostLoanPayload,
  GetTariffPayload,
} from "./LoansModels";

export const loansApi = createApi({
  reducerPath: "loansApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `${process.env.REACT_APP_LOANS_API_HOST}/`,
  }),

  endpoints: (build) => {
    return {
      getLoans: build.query<GetLoanPayload[], void>({
        query: () => ({
          url: "credits/",
          method: "GET",
        }),
      }),
      getTariffs: build.query<GetTariffPayload[], void>({
        query: () => ({
          url: "tariffs/",
          method: "GET",
        }),
      }),
      postLoan: build.mutation<GetLoanPayload, PostLoanPayload>({
        query: (body) => ({
          url: "credits/",
          method: "POST",
          body,
        }),
      }),
    };
  },
});

export const { useGetLoansQuery, usePostLoanMutation, useGetTariffsQuery } =
  loansApi;
