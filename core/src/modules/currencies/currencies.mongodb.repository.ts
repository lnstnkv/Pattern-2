import {CurrenciesRepositoryInterface} from "./currencies.repository.interface";
import {Currency} from "../../schemas/CurrencySchema";

export class CurrenciesMongodbRepository implements CurrenciesRepositoryInterface {
    create(name: string): Promise<void> {
        return Promise.resolve(undefined);
    }

    delete(name: string): Promise<void> {
        return Promise.resolve(undefined);
    }

    getList(): Promise<Currency> {
        return Promise.resolve(undefined);
    }

    isCurrencyExists(name: string): Promise<boolean> {
        return Promise.resolve(false);
    }

}