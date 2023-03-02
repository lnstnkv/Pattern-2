import {Injectable} from "@nestjs/common";
import {InjectModel} from "@nestjs/mongoose";
import {Operation, OperationDocument} from "../../schemas/OperationSchema";
import {Model, Types} from "mongoose";
import {OperationsRepositoryInterface} from "./operations.repository.interface";
import {OperationType} from "../../entities/OperationType";
import {
    TopUpOperationPayload,
    TransferOperationPayload,
    WithdrawOperationPayload
} from "../../entities/OperationPayloadType";

@Injectable()
export class OperationsMongodbRepository implements OperationsRepositoryInterface {
    constructor(@InjectModel(Operation.name) private readonly _operationModel: Model<OperationDocument>) {
    }

    async topUp(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        const payload = new TopUpOperationPayload(id, amountOfMoney);

        await this._operationModel.create(new this._operationModel({
            callerId: callerId,
            type: OperationType.topUp,
            targetAccountIds: [new Types.ObjectId(id)],
            payload: payload
        }))
    }

    async transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string): Promise<void> {
        const payload = new TransferOperationPayload(id, receiverId, amountOfMoney);

        await this._operationModel.create(new this._operationModel({
            callerId: callerId,
            type: OperationType.transfer,
            targetAccountIds: [new Types.ObjectId(id), new Types.ObjectId(receiverId)],
            payload: payload
        }))
    }

    async withdraw(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        const payload = new WithdrawOperationPayload(id, amountOfMoney);

        await this._operationModel.create(new this._operationModel({
            callerId: callerId,
            type: OperationType.withdraw,
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


}