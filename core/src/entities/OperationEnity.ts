import {OperationType} from "./OperationType";
import {OperationPayloadType} from "./OperationPayloadType";

export class OperationEntity {
    id: string
    type: OperationType
    date: Date
    callerId: string
    targetAccountIds: []
    payload: OperationPayloadType
}
