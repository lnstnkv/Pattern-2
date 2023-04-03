import {Operation} from "../databases/mongodb/schemas/OperationSchema";
import {OperationStatus} from "~shared/entities/OperationStatus";
import {Types} from "mongoose";

export interface OperationsRepositoryInterface {
    transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string, status: OperationStatus, operationId: Types.ObjectId): Promise<void>;

    topUp(amountOfMoney: number, id: string, callerId: string, status: OperationStatus, operationId: Types.ObjectId): Promise<void>;

    withdraw(amountOfMoney: number, id: string, callerId: string, status: OperationStatus, operationId: Types.ObjectId): Promise<void>;

    getList(limit: number, skip: number, accountId: string): Promise<Operation[]>

    getTotalCount(accountId?: string): Promise<number>

    generateId(): Types.ObjectId;
}

export const OperationsRepositoryInterface = Symbol("OperationsRepositoryInterface");