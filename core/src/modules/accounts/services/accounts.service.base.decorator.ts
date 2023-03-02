import {AccountsServiceInterface} from "./accounts.service.interface";
import {PaginationParamsModel} from "../../../writeModels/PaginationParamsModel";
import {OperationsHistoryModel} from "../../../readModels/OperationsHistoryModel";
import {AccountsDetailsWithTotalCountReadModel} from "../../../readModels/AccountsDetailsWithTotalCountReadModel";
import {AccountDetailsReadModel} from "../../../readModels/AccountDetailsReadModel";
import {AccountCreateModel} from "../../../writeModels/AccountCreateModel";

export abstract class AccountsServiceBaseDecorator implements AccountsServiceInterface {
    protected readonly _wrapped: AccountsServiceInterface;

    protected constructor(accountsService: AccountsServiceInterface) {
        this._wrapped = accountsService;
    }

    create(accountCreateModel: AccountCreateModel): Promise<AccountDetailsReadModel> {
        return this._wrapped.create(accountCreateModel);
    }

    delete(id: string): Promise<void> {
        return this._wrapped.delete(id);
    }

    get(accountId: string): Promise<AccountDetailsReadModel> {
        return this._wrapped.get(accountId);
    }

    getHistory(paginationParams: PaginationParamsModel, accountId): Promise<OperationsHistoryModel> {
        return this._wrapped.getHistory(paginationParams, accountId);
    }

    getList(paginationParams: PaginationParamsModel, ownerId?: string): Promise<AccountsDetailsWithTotalCountReadModel> {
        return this._wrapped.getList(paginationParams, ownerId);
    }

    topUp(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        return this._wrapped.topUp(amountOfMoney, id, callerId);
    }

    transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string): Promise<void> {
        return this._wrapped.transfer(amountOfMoney, id, receiverId, callerId);
    }

    withdraw(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        return this._wrapped.withdraw(amountOfMoney, id, callerId);
    }

}