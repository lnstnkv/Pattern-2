import {Controller, Delete, Get, Param, Post} from "@nestjs/common";
import {ApiOperation, ApiTags} from "@nestjs/swagger";


@Controller("accounts")
@ApiTags("Accounts service")
export class AccountsController {

    @Post("/")
    @ApiOperation({
        summary: "Create new bank account"
    })
    async create() {

    }

    @Get("/")
    @ApiOperation({
        summary: "Get list of accounts"
    })
    async getList() {

    }

    @Get("/:id")
    @ApiOperation({
        summary: "Get account info"
    })
    async get(@Param('id') id: string) {

    }

    @Post("/:id/withdraw")
    @ApiOperation({
        summary: "Withdraw money from account"
    })
    async withdraw(@Param('id') id: string) {

    }

    @Post("/:id/topUp")
    @ApiOperation({
        summary: "Top up bank account"
    })
    async topUp(@Param('id') id: string) {

    }

    @Post("/:id/transfer/:receiverId")
    @ApiOperation({
        summary: "Transfer money"
    })
    async transfer(@Param('id') id: string, @Param('receiverId') receiverId: string) {

    }

    @Post("/:id/block")
    @ApiOperation({
        summary: "Block account"
    })
    async block(@Param('id') id: string) {

    }

    @Post("/:id/unblock")
    @ApiOperation({
        summary: "Unblock account"
    })
    async unblock(@Param('id') id: string) {

    }

    @Get("/:id/history")
    @ApiOperation({
        summary: "Get history of operations"
    })
    async getOperationsHistory(@Param('id') id: string) {

    }

    @Delete("/:id")
    @ApiOperation({
        summary: "Delete account"
    })
    async delete(@Param('id') id: string) {

    }
}
