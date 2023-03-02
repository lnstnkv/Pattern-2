import {Prop, Schema, SchemaFactory} from "@nestjs/mongoose";
import {HydratedDocument, Types} from "mongoose";

@Schema()
export class Account {
    _id: Types.ObjectId;
    @Prop({type: String, required: true})
    ownerId: string;
    @Prop({type: String, required: true})
    currency: string;
    @Prop({type: Number, required: true, default: 0})
    balance: number = 0;
    @Prop({type: Boolean, default: false})
    isBlocked: boolean = false;
    @Prop({type: Boolean, default: false})
    isDeleted: boolean = false;
}

export type AccountDocument = HydratedDocument<Account>;
export const AccountSchema = SchemaFactory.createForClass(Account);