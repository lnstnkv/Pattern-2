export interface OperationsServiceInterface {
    topUp(amountOfMoney: number, id: string, callerId: string): Promise<void>

    withdraw(amountOfMoney: number, id: string, callerId: string): Promise<void>

    transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string): Promise<void>
}

export const OperationsServiceInterface = Symbol("OperationsServiceInterface");