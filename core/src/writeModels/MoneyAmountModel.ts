import {ApiProperty} from "@nestjs/swagger";
import {IsInt} from "class-validator";

export class MoneyAmountModel {
    @ApiProperty()
    @IsInt()
    amountOfMoney: number
}