import {ApiProperty} from "@nestjs/swagger";
import {AccountDetailsReadModel} from "./AccountDetailsReadModel";

export class AccountsDetailsWithTotalCountReadModel {
    @ApiProperty()
    totalCount: number;
    @ApiProperty({type: [AccountDetailsReadModel]})
    accounts: AccountDetailsReadModel[] = [];
}