import {Currency} from "../../schemas/CurrencySchema";

export interface CurrenciesRepositoryInterface {
    getList(): Promise<Currency>

    create(name: string): Promise<void>

    delete(name: string): Promise<void>

    isCurrencyExists(name: string): Promise<boolean>
}

export const CurrenciesRepositoryInterface = Symbol("CurrenciesRepositoryInterface");