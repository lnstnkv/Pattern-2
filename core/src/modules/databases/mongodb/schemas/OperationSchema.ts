import {Prop, Schema, SchemaFactory} from "@nestjs/mongoose";
import {HydratedDocument, Types} from "mongoose";
import {OperationStatus} from "~shared/entities/OperationStatus";


@Schema()
export class Operation {
    _id: Types.ObjectId;
    @Prop({type: String, required: true})
    callerId: string;
    @Prop({type: Date, default: new Date()})
    date: Date = new Date();
    @Prop({type: String, required: true})
    type: string;
    @Prop({type: [Types.ObjectId], default: []})
    targetAccountIds: Types.ObjectId[] = [];
    @Prop({type: Object, required: true, default: {}})
    payload: object = {}
    @Prop({type: String, required: true})
    status: string = OperationStatus.pending
}

export type OperationDocument = HydratedDocument<Operation>;
export const OperationSchema = SchemaFactory.createForClass(Operation);