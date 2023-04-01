import {ApiProperty} from "@nestjs/swagger";
import {IsInt, IsPositive} from "class-validator";

export class MoneyAmountModel {
    @ApiProperty()
    @IsInt()
    @IsPositive()
    amountOfMoney: number
}