import {Operation} from "../databases/mongodb/schemas/OperationSchema";
import {OperationStatus} from "~shared/entities/OperationStatus";

export interface OperationsRepositoryInterface {
    transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string, status: OperationStatus): Promise<void>;

    topUp(amountOfMoney: number, id: string, callerId: string, status: OperationStatus): Promise<void>;

    withdraw(amountOfMoney: number, id: string, callerId: string, status: OperationStatus): Promise<void>;

    getList(limit: number, skip: number, accountId: string): Promise<Operation[]>

    getTotalCount(accountId?: string): Promise<number>
}

export const OperationsRepositoryInterface = Symbol("OperationsRepositoryInterface");