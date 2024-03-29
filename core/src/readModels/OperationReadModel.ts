import {ApiProperty} from "@nestjs/swagger";
import {Operation} from "../modules/databases/mongodb/schemas/OperationSchema";

export class OperationReadModel {
    @ApiProperty()
    id: string
    @ApiProperty()
    type: string
    @ApiProperty()
    date: Date
    @ApiProperty()
    status: string
    @ApiProperty()
    callerId: string
    @ApiProperty()
    payload: object

    constructor(operation?: Operation) {
        if (operation) {
            this.id = operation._id.toString();
            this.type = operation.type;
            this.date = operation.date;
            this.callerId = operation.callerId;
            this.payload = operation.payload;
            this.status = operation.status;
        }
    }

    static fromObject(input) {
        const newModel = new this();
        Object.assign(newModel, input);
        return newModel;
    }
}