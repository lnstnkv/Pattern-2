import {ApiProperty} from "@nestjs/swagger";

export class CurrencyCreateModel{
    @ApiProperty()
    name: string
}