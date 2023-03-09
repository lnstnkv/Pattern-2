export interface IAccount{
    id: string
    ownerId: string
    currency: string
    balance: number
    isBlocked: boolean
    isDeleted: boolean
}

export interface IAccounts{
    totalCount: number
    accounts: IAccount[]
}
