import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query/react";

import {
  GetLoanPayload,
  PostLoanPayload,
  GetTariffPayload,
} from "./LoansModels";

const LOANS_API_HOST =  'http://localhost:8181';

export const loansApi = createApi({
  reducerPath: "loansApi",
  baseQuery: fetchBaseQuery({ baseUrl: `${LOANS_API_HOST}/api/` }),

  endpoints: (build) => {
    return {
      getLoans: build.query<GetLoanPayload[], void>({
        query: () => ({
          url: "credits",
          method: "GET",
        }),
      }),
      getTariffs: build.query<GetTariffPayload[], void>({
        query: () => ({
          url: "tariffs",
          method: "GET",
        }),
      }),
      postLoan: build.mutation<GetLoanPayload, PostLoanPayload>({
        query: (body) => ({
          url: "credits",
          method: "POST",
          body,
        }),
      }),
    };
  },
});

export const { useGetLoansQuery, usePostLoanMutation, useGetTariffsQuery } =
  loansApi;
