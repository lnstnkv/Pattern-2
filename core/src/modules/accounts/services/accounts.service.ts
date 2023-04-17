import {Inject} from "@nestjs/common";
import {Account, AccountDocument} from "../../databases/mongodb/schemas/AccountSchema";
import {OperationsHistoryModel} from "../../../readModels/OperationsHistoryModel";
import {AccountsDetailsWithTotalCountReadModel} from "../../../readModels/AccountsDetailsWithTotalCountReadModel";
import {OperationReadModel} from "../../../readModels/OperationReadModel";
import {InjectModel} from "@nestjs/mongoose";
import {AccountNotFoundError, NotEnoughMoneyError, SameAccountsInTransferError} from "~shared/errors/errors";
import {AccountsServiceInterface} from "./accounts.service.interface";
import {AccountCreateModel} from "~shared/writeModels/AccountCreateModel";
import {PaginationParamsModel} from "~shared/writeModels/PaginationParamsModel";
import {OperationsRepositoryInterface} from "../../operations/operations.repository.interface";
import {Model} from "mongoose";
import {AccountDetailsReadModel} from "../../../readModels/AccountDetailsReadModel";
import {AccountsRepositoryInterface} from "../accounts.repository.interface";
import {OperationStatus} from "~shared/entities/OperationStatus";
import {AccountsGateway} from "../accounts.gateway";
import {OperationStatusChangeReadModel} from "../../../readModels/OperationStatusChangeReadModel";
import {Operation} from "../../databases/mongodb/schemas/OperationSchema";
import {OperationType} from "~shared/entities/OperationType";
import {
    TopUpOperationPayload,
    TransferOperationPayload,
    WithdrawOperationPayload
} from "~shared/entities/OperationPayloadType";


export class AccountsService implements AccountsServiceInterface {
    private _accountsGateway: AccountsGateway;

    constructor(@Inject(OperationsRepositoryInterface) private readonly _operationsRepository: OperationsRepositoryInterface,
                @Inject(AccountsRepositoryInterface) private readonly _accountsRepository: AccountsRepositoryInterface,
                @InjectModel(Account.name) private readonly _accountModel: Model<AccountDocument>,) {
    }

    async create(accountCreateModel: AccountCreateModel): Promise<AccountDetailsReadModel> {
        const account = new this._accountModel({
            ownerId: accountCreateModel.ownerId,
            currency: accountCreateModel.currency
        })

        await this._accountsRepository.create(account);

        return new AccountDetailsReadModel(account);
    }

    async delete(id: string): Promise<void> {
        await this._throwErrorIfAccountDoesNotExists(id);

        await this._accountsRepository.delete(id);
    }

    async get(id: string): Promise<AccountDetailsReadModel> {
        const account = await this._accountsRepository.get(id);
        if (!account)
            throw new AccountNotFoundError();

        return new AccountDetailsReadModel(account);
    }

    async getList(paginationParams: PaginationParamsModel, ownerId?: string): Promise<AccountsDetailsWithTotalCountReadModel> {
        const promises = [];
        promises.push(this._accountsRepository.getList(paginationParams.limit, paginationParams.skip, ownerId));
        promises.push(this._accountsRepository.getTotalCount(ownerId));

        const readModel = new AccountsDetailsWithTotalCountReadModel();
        await Promise.all(promises).then((promisesResults) => {
            for (const account of promisesResults[0]) {
                readModel.accounts.push(new AccountDetailsReadModel(account));
            }
            readModel.totalCount = promisesResults[1];
        });
        return readModel;
    }

    async topUp(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        const operationId = this._operationsRepository.generateId();
        const awaitedOperation = new Operation();
        awaitedOperation.type = OperationType.topUp;
        awaitedOperation._id = operationId;
        awaitedOperation.payload = new TopUpOperationPayload(id, amountOfMoney);
        awaitedOperation.callerId = callerId;
        await this._accountsGateway.sendNewOperation(id, new OperationReadModel(awaitedOperation));
        try {
            const account = await this._accountsRepository.get(id);
            if (!account)
                throw new AccountNotFoundError();

            account.balance += amountOfMoney;

            let promises = [];
            promises.push(this._accountsRepository.update(account));
            promises.push(this._operationsRepository.topUp(amountOfMoney, id, callerId, OperationStatus.accepted, operationId));
            await Promise.all(promises);
        } catch (error) {
            await this._operationsRepository.topUp(amountOfMoney, id, callerId, OperationStatus.rejected, operationId);
            await this._accountsGateway.sendOperationStatusChange(id, OperationStatusChangeReadModel.fromObject({
                id: operationId.toString(),
                status: OperationStatus.rejected
            }))
            throw error;
        }
        await this._accountsGateway.sendOperationStatusChange(id, OperationStatusChangeReadModel.fromObject({
            id: operationId.toString(),
            status: OperationStatus.accepted
        }))
    }

    async transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string): Promise<void> {
        const operationId = this._operationsRepository.generateId();
        const awaitedOperation = new Operation();
        awaitedOperation.type = OperationType.transfer;
        awaitedOperation._id = operationId;
        awaitedOperation.payload = new TransferOperationPayload(id, receiverId, amountOfMoney);
        awaitedOperation.callerId = callerId;
        await this._accountsGateway.sendNewOperation(id, new OperationReadModel(awaitedOperation));
        try {
            const account = await this._accountsRepository.get(id);
            if (!account)
                throw new AccountNotFoundError();

            const receiverAccount = await this._accountsRepository.get(receiverId);
            if (!receiverAccount)
                throw new AccountNotFoundError();

            if (receiverId === id)
                throw new SameAccountsInTransferError();

            account.balance -= amountOfMoney;
            if (account.balance < 0)
                throw new NotEnoughMoneyError();
            receiverAccount.balance += amountOfMoney;

            let promises = [];
            promises.push(this._accountsRepository.update(account));
            promises.push(this._accountsRepository.update(receiverAccount));
            promises.push(this._operationsRepository.transfer(amountOfMoney, id, receiverId, callerId, OperationStatus.accepted, operationId));
            await Promise.all(promises);
        } catch (error) {
            await this._operationsRepository.transfer(amountOfMoney, id, receiverId, callerId, OperationStatus.rejected, operationId)
            await this._accountsGateway.sendOperationStatusChange(id, OperationStatusChangeReadModel.fromObject({
                id: operationId.toString(),
                status: OperationStatus.rejected
            }))
            throw error;
        }
        await this._accountsGateway.sendOperationStatusChange(id, OperationStatusChangeReadModel.fromObject({
            id: operationId.toString(),
            status: OperationStatus.accepted
        }))
    }

    async withdraw(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        const operationId = this._operationsRepository.generateId();
        const awaitedOperation = new Operation();
        awaitedOperation.type = OperationType.withdraw;
        awaitedOperation._id = operationId;
        awaitedOperation.payload = new WithdrawOperationPayload(id, amountOfMoney);
        awaitedOperation.callerId = callerId;
        await this._accountsGateway.sendNewOperation(id, new OperationReadModel(awaitedOperation));
        try {
            const account = await this._accountsRepository.get(id);
            if (!account)
                throw new AccountNotFoundError();

            account.balance -= amountOfMoney;

            if (account.balance < 0)
                throw new NotEnoughMoneyError();

            let promises = [];
            promises.push(this._accountsRepository.update(account));
            promises.push(this._operationsRepository.withdraw(amountOfMoney, id, callerId, OperationStatus.accepted, operationId));
            await Promise.all(promises);
        } catch (error) {
            await this._operationsRepository.withdraw(amountOfMoney, id, callerId, OperationStatus.rejected, operationId);
            await this._accountsGateway.sendOperationStatusChange(id, OperationStatusChangeReadModel.fromObject({
                id: operationId.toString(),
                status: OperationStatus.rejected
            }))
            throw error;
        }
        await this._accountsGateway.sendOperationStatusChange(id, OperationStatusChangeReadModel.fromObject({
            id: operationId.toString(),
            status: OperationStatus.accepted
        }))
    }

    async getHistory(paginationParams: PaginationParamsModel, accountId): Promise<OperationsHistoryModel> {
        await this._throwErrorIfAccountDoesNotExists(accountId);

        const promises = [];
        promises.push(this._operationsRepository.getList(paginationParams.limit, paginationParams.skip, accountId));
        promises.push(this._operationsRepository.getTotalCount(accountId));

        const history = new OperationsHistoryModel();
        await Promise.all(promises).then((promisesResults) => {
            for (const operation of promisesResults[0]) {
                history.operations.push(new OperationReadModel(operation));
            }
            history.totalCount = promisesResults[1];
        });
        return history;

    }

    public setGateway(accountsGateway: AccountsGateway): void {
        this._accountsGateway = accountsGateway;
    }

    private async _throwErrorIfAccountDoesNotExists(id: string) {
        const account = await this._accountsRepository.get(id);
        if (!account)
            throw new AccountNotFoundError();
    }
}