import {CurrenciesServiceInterface} from "./currencies.service.interface";
import {CurrencyReadModel} from "../../readModels/CurrencyReadModel";


export class CurrenciesService implements CurrenciesServiceInterface {
    create(name: string): Promise<void> {
        return Promise.resolve(undefined);
    }

    delete(name: string): Promise<void> {
        return Promise.resolve(undefined);
    }

    getList(): Promise<CurrencyReadModel[]> {
        return Promise.resolve([]);
    }

}