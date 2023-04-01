import {OperationType} from "./OperationType";
import {OperationPayloadType} from "./OperationPayloadType";
import {OperationStatus} from "./OperationStatus";

export class OperationEntity {
    id: string
    type: OperationType
    status: OperationStatus
    date: Date
    callerId: string
    targetAccountIds: []
    payload: OperationPayloadType
}
