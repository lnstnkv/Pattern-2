import {PaginationParamsModel} from "../../../writeModels/PaginationParamsModel";
import {AccountCreateModel} from "../../../writeModels/AccountCreateModel";
import {AccountDetailsReadModel} from "../../../readModels/AccountDetailsReadModel";
import {AccountsDetailsWithTotalCountReadModel} from "../../../readModels/AccountsDetailsWithTotalCountReadModel";
import {OperationsHistoryModel} from "../../../readModels/OperationsHistoryModel";

export interface AccountsServiceInterface {
    get(accountId: string): Promise<AccountDetailsReadModel>

    getList(paginationParams: PaginationParamsModel, ownerId?: string): Promise<AccountsDetailsWithTotalCountReadModel>

    create(accountCreateModel: AccountCreateModel): Promise<AccountDetailsReadModel>

    delete(id: string): Promise<void>

    topUp(amountOfMoney: number, id: string, callerId: string): Promise<void>

    withdraw(amountOfMoney: number, id: string, callerId: string): Promise<void>

    transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string): Promise<void>

    getHistory(paginationParams: PaginationParamsModel, accountId):Promise<OperationsHistoryModel>
}

export const AccountsServiceInterface = Symbol("AccountsServiceInterface");