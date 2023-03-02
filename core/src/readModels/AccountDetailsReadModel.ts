import {ApiProperty} from "@nestjs/swagger";
import {Account} from "../schemas/AccountSchema";

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


    constructor(account?: Account) {
        if(account){
            this.id = account._id.toString();
            this.ownerId = account.ownerId;
            this.balance = account.balance;
            this.isBlocked = account.isBlocked;
            this.isDeleted = account.isDeleted;
        }
    }
}