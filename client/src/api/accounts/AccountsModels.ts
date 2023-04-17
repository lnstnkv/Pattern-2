export interface GetAllAcoountsPayload {
  accounts: GetAccountPayload[];
  total: number
}

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
  ownerId: number;
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

export interface TransferPayload {
  id: string;
  receiverId: string;
  amountOfMoney: number;
}
