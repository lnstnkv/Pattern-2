export interface GetLoanPayload {
  id: number;
  creditStart: string;
  creditDuration: number;
  creditAmount: number;
  tariff: string;
}

export interface PostLoanPayload {
  creditStart?: string;
  creditDuration: number;
  creditAmount: number;
  tariffName: string;
}

export interface GetTariffPayload {
  id: number;
  name: string;
  percentage: number;
  credit: GetLoanPayload[];
}
