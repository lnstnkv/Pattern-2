import {ApiProperty} from "@nestjs/swagger";

export class AccountShortenReadModel{
    @ApiProperty()
    id: string
    @ApiProperty()
    ownerId: string
}