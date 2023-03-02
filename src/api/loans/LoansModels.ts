export interface GetLoanPayload{
    "id": number,
    "creditStart": string,
    "creditDuration": number,
    "creditAmount": number,
    "tariff": GetTariffPayload
  }

export interface GetTariffPayload {
    "id": number,
    "name": string,
    "percentage": number,
    "credit": GetLoanPayload[]
}