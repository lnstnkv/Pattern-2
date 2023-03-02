import {Controller, Get, Inject, Param, Query} from "@nestjs/common";
import {ApiOperation, ApiQuery, ApiResponse, ApiTags} from "@nestjs/swagger";
import {AccountDetailsReadModel} from "../../readModels/AccountDetailsReadModel";
import {PaginationParamsModel} from "../../writeModels/PaginationParamsModel";
import {AccountsServiceInterface} from "../accounts/services/accounts.service.interface";

@Controller("users")
@ApiTags("Accounts service")
export class UsersController {
    constructor(@Inject(AccountsServiceInterface) private readonly _accountsService: AccountsServiceInterface,
    ) {
    }

    @Get("/:id/accounts")
    @ApiOperation({
        summary: "Get list of accounts of special user"
    })
    @ApiResponse({
        status: 200,
        description: "success",
        type: [AccountDetailsReadModel]
    })
    @ApiQuery({name: "limit", required: false})
    @ApiQuery({name: "skip", required: false})
    async getUserAccountsList(@Query() paginationParams: PaginationParamsModel, @Param("id") id: string) {
        return this._accountsService.getList(paginationParams, id);
    }
}