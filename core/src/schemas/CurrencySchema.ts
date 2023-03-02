import {Prop, Schema, SchemaFactory} from "@nestjs/mongoose";
import {HydratedDocument} from "mongoose";

@Schema()
export class Currency {
    @Prop({type: String, required: true, unique: true})
    name: string
}

export type CurrencyDocument = HydratedDocument<Currency>;
export const CurrencySchema = SchemaFactory.createForClass(Currency);