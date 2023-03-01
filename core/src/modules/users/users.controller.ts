import {Controller, Get, Query} from "@nestjs/common";
import {ApiOperation, ApiQuery, ApiResponse, ApiTags} from "@nestjs/swagger";
import {AccountDetailsReadModel} from "../../readModels/AccountDetailsReadModel";
import {PaginationParamsModel} from "../../writeModels/PaginationParamsModel";

@Controller("users")
@ApiTags("Accounts service")
export class UsersController {
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
    async getUserAccountsList(@Query() paginationParams: PaginationParamsModel): Promise<AccountDetailsReadModel[]> {
        return [];
    }
}