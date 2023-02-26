import {ApiProperty} from "@nestjs/swagger";

export class AccountDetailsReadModel {
    @ApiProperty()
    id: string
    @ApiProperty()
    ownerId: string
    @ApiProperty()
    currency: string
    @ApiProperty()
    balance: number
    @ApiProperty()
    isBlocked: boolean
    @ApiProperty()
    isDeleted: boolean
}