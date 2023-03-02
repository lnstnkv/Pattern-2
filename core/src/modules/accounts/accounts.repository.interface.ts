import {Account} from "../../schemas/AccountSchema";

export interface AccountsRepositoryInterface {
    create(account: Account): Promise<void>

    get(id: string): Promise<Account>

    getList(limit: number, skip: number, ownerId: string): Promise<Account[]>

    getTotalCount(ownerId: string): Promise<number>

    delete(id: string): Promise<void>

    update(account: Account): Promise<void>
}

export const AccountsRepositoryInterface = Symbol("AccountsRepositoryInterface");