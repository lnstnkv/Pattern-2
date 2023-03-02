import {Operation} from "../../schemas/OperationSchema";

export interface OperationsRepositoryInterface {
    transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string): Promise<void>;

    topUp(amountOfMoney: number, id: string, callerId: string): Promise<void>;

    withdraw(amountOfMoney: number, id: string, callerId: string): Promise<void>;

    getList(limit: number, skip: number,accountId: string): Promise<Operation[]>

    getTotalCount(accountId?: string): Promise<number>
}

export const OperationsRepositoryInterface = Symbol("OperationsRepositoryInterface");