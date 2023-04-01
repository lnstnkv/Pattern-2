import {OperationsServiceInterface} from "./operations.service.interface";

export class OperationsService implements OperationsServiceInterface{
    topUp(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        return Promise.resolve(undefined);
    }

    transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string): Promise<void> {
        return Promise.resolve(undefined);
    }

    withdraw(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        return Promise.resolve(undefined);
    }

}