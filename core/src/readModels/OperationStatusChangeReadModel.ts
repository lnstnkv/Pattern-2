import {OperationStatus} from "~shared/entities/OperationStatus";

export class OperationStatusChangeReadModel {
    id: string
    status: OperationStatus

    static fromObject(input) {
        const newModel = new this();
        Object.assign(newModel, input);
        return newModel;
    }
}