import {ApiProperty} from "@nestjs/swagger";

export class MoneyAmountModel {
    @ApiProperty()
    amountOfMoney: number
}