import {AccountsServiceInterface} from "./accounts.service.interface";
import {AccountCreateModel} from "../../../writeModels/AccountCreateModel";
import {AccountDetailsReadModel} from "../../../readModels/AccountDetailsReadModel";
import {PaginationParamsModel} from "../../../writeModels/PaginationParamsModel";
import {AccountsDetailsWithTotalCountReadModel} from "../../../readModels/AccountsDetailsWithTotalCountReadModel";
import {Inject} from "@nestjs/common";
import {OperationsRepositoryInterface} from "../../operations/operations.repository.interface";
import {AccountsRepositoryInterface} from "../accounts.repository.interface";
import {InjectModel} from "@nestjs/mongoose";
import {Account, AccountDocument} from "../../../schemas/AccountSchema";
import {Model} from "mongoose";
import {AccountNotFoundError, NotEnoughMoneyError} from "../../../errors/errors";
import {OperationsHistoryModel} from "../../../readModels/OperationsHistoryModel";
import {OperationReadModel} from "../../../readModels/OperationReadModel";

export class AccountsService implements AccountsServiceInterface {
    constructor(@Inject(OperationsRepositoryInterface) private readonly _operationsRepository: OperationsRepositoryInterface,
                @Inject(AccountsRepositoryInterface) private readonly _accountsRepository: AccountsRepositoryInterface,
                @InjectModel(Account.name) private readonly _accountModel: Model<AccountDocument>) {
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
        const account = await this._accountsRepository.get(id);
        if (!account)
            throw new AccountNotFoundError();

        account.balance += amountOfMoney;

        let promises = [];
        promises.push(this._accountsRepository.update(account));
        promises.push(this._operationsRepository.topUp(amountOfMoney, id, callerId));
        await Promise.all(promises);
    }

    async transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string): Promise<void> {
        const account = await this._accountsRepository.get(id);
        if (!account)
            throw new AccountNotFoundError();

        const receiverAccount = await this._accountsRepository.get(id);
        if (!receiverAccount)
            throw new AccountNotFoundError();

        account.balance -= amountOfMoney;
        if (account.balance < 0)
            throw new NotEnoughMoneyError();
        receiverAccount.balance += amountOfMoney;

        let promises = [];
        promises.push(this._accountsRepository.update(account));
        promises.push(this._accountsRepository.update(receiverAccount));
        promises.push(this._operationsRepository.transfer(amountOfMoney, id, receiverId, callerId));
        await Promise.all(promises);
    }

    async withdraw(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        const account = await this._accountsRepository.get(id);
        if (!account)
            throw new AccountNotFoundError();

        account.balance -= amountOfMoney;

        if (account.balance < 0)
            throw new NotEnoughMoneyError();

        let promises = [];
        promises.push(this._accountsRepository.update(account));
        promises.push(this._operationsRepository.withdraw(amountOfMoney, id, callerId));
        await Promise.all(promises);
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

    private async _throwErrorIfAccountDoesNotExists(id: string) {
        const account = await this._accountsRepository.get(id);
        if (!account)
            throw new AccountNotFoundError();
    }
}