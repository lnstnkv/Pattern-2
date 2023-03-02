import {CurrencyReadModel} from "../../readModels/CurrencyReadModel";

export interface CurrenciesServiceInterface {
    getList(): Promise<CurrencyReadModel[]>

    create(name: string): Promise<void>

    delete(name: string): Promise<void>
}
export const CurrenciesServicesInterface = Symbol("CurrenciesServiceInterface");