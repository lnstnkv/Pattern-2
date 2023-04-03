import {Injectable} from "@nestjs/common";
import {InjectModel} from "@nestjs/mongoose";
import {Operation, OperationDocument} from "../databases/mongodb/schemas/OperationSchema";
import {Model, Types} from "mongoose";
import {OperationsRepositoryInterface} from "./operations.repository.interface";
import {OperationType} from "~shared/entities/OperationType";
import {
    TopUpOperationPayload,
    TransferOperationPayload,
    WithdrawOperationPayload
} from "~shared/entities/OperationPayloadType";
import {OperationStatus} from "~shared/entities/OperationStatus";

@Injectable()
export class OperationsMongodbRepository implements OperationsRepositoryInterface {
    constructor(@InjectModel(Operation.name) private readonly _operationModel: Model<OperationDocument>) {
    }

    async topUp(amountOfMoney: number, id: string, callerId: string, status: OperationStatus, operationId: Types.ObjectId): Promise<void> {
        const payload = new TopUpOperationPayload(id, amountOfMoney);

        await this._operationModel.create(new this._operationModel({
            _id: operationId,
            callerId: callerId,
            type: OperationType.topUp,
            status: status,
            targetAccountIds: [new Types.ObjectId(id)],
            payload: payload
        }))
    }

    async transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string, status: OperationStatus, operationId: Types.ObjectId): Promise<void> {
        const payload = new TransferOperationPayload(id, receiverId, amountOfMoney);

        await this._operationModel.create(new this._operationModel({
            _id: operationId,
            callerId: callerId,
            type: OperationType.transfer,
            status: status,
            targetAccountIds: [new Types.ObjectId(id), new Types.ObjectId(receiverId)],
            payload: payload
        }))
    }

    async withdraw(amountOfMoney: number, id: string, callerId: string, status: OperationStatus, operationId: Types.ObjectId): Promise<void> {
        const payload = new WithdrawOperationPayload(id, amountOfMoney);

        await this._operationModel.create(new this._operationModel({
            _id: operationId,
            callerId: callerId,
            type: OperationType.withdraw,
            status: status,
            targetAccountIds: [new Types.ObjectId(id)],
            payload: payload
        }))
    }

    async getList(limit: number, skip: number, accountId: string): Promise<Operation[]> {
        if (limit !== null) {
            return this._operationModel.find({targetAccountIds: accountId}).skip(skip).limit(limit);
        }
        return this._operationModel.find({targetAccountIds: accountId}).skip(skip);
    }

    async getTotalCount(accountId?: string): Promise<number> {
        if (accountId)
            return this._operationModel.count({targetAccountIds: accountId});
        return this._operationModel.count();
    }

    generateId(): Types.ObjectId {
        return new Types.ObjectId();
    }


}