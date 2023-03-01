import {OperationType} from "./OperationType";

export abstract class Operation {
    id: string
    type: OperationType
    date: Date
    callerId: string
    targetAccountIds: []
}

export class TransferOperation {
    type: OperationType.transfer
    senderAccountId: string
    payeeAccountId: string
    amountOfMoney: number
}

export class WithdrawOperation {
    type: OperationType.withdraw
    amountOfMoney: number
}

export class TopUpOperation {
    type: OperationType.topUp
    amountOfMoney: number
}

export class BlockAccountOperation {
    type: OperationType.blockAccount
}

export class UnblockAccountOperation {
    type: OperationType.unblockAccount
}

export class CreateAccountOperation {
    type: OperationType.createAccount
}

export class DeleteAccountOperation {
    type: OperationType.deleteAccount
}