import {ApiProperty} from "@nestjs/swagger";

export class AccountCreateModel {
    @ApiProperty()
    currency: string
}