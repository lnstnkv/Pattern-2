import { configureStore } from "@reduxjs/toolkit";

import { accountsApi } from "api/accounts/AccountsApi";
import { usersApi } from "api/users/UsersApi";
import { loansApi } from "api/loans/LoansApi";

const store = configureStore({
  reducer: {
    [usersApi.reducerPath]: usersApi.reducer,
    [accountsApi.reducerPath]: accountsApi.reducer,
    [loansApi.reducerPath]: loansApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      immutableCheck: { warnAfter: 128 },
      serializableCheck: false,
    })
      .concat(loansApi.middleware)
      .concat(usersApi.middleware)
      .concat(accountsApi.middleware),
});

export default store;

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
