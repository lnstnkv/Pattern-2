export interface GetAccountPayload {
  id: string;
  ownerId: string;
  currency: string;
  balance: number;
  isBlocked: boolean;
  isDeleted: boolean;
}

export interface PostAccountsPayload {
  currency: string;
}

export interface DeleteAccountPayload {
  id: string;
}

export interface TopUpAccountPayload {
  id: string;
  amountOfMoney: number;
}

export interface WithDrawAccountPayload {
  id: string;
  amountOfMoney: number;
}

export interface GetAccountHistoryPayload {
  id: string;
  type: string;
  date: string;
  callerId: string;
  // todo: replace objet
  payload: object;
}
