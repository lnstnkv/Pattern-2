import {Body, Controller, Delete, Get, Inject, Param, Post, Query} from "@nestjs/common";
import {ApiOperation, ApiQuery, ApiResponse, ApiTags} from "@nestjs/swagger";
import {AccountCreateModel} from "../../writeModels/AccountCreateModel";
import {AccountDetailsReadModel} from "../../readModels/AccountDetailsReadModel";
import {MoneyAmountModel} from "../../writeModels/MoneyAmountModel";
import {PaginationParamsModel} from "../../writeModels/PaginationParamsModel";
import {OperationReadModel} from "../../readModels/OperationReadModel";
import {Connection} from "mongoose";
import {InjectConnection} from "@nestjs/mongoose";
import {AccountsServiceInterface} from "./accounts.service.interface";
import {AccountsDetailsWithTotalCountReadModel} from "../../readModels/AccountsDetailsWithTotalCountReadModel";


@Controller("accounts")
@ApiTags("Accounts service")
export class AccountsController {
    constructor(
        @InjectConnection() private readonly _mongoConnection: Connection,
        @Inject(AccountsServiceInterface) private readonly _accountsService: AccountsServiceInterface,
    ) {
    }

    @Post("/")
    @ApiOperation({
        summary: "Create new bank account"
    })
    @ApiResponse({
        status: 201,
        description: "success",
        type: AccountDetailsReadModel
    })
    async create(@Body() accountCreateModel: AccountCreateModel): Promise<AccountDetailsReadModel> {
        return await this._accountsService.create(accountCreateModel);
    }

    @Get("/")
    @ApiOperation({
        summary: "Get list of accounts"
    })
    @ApiResponse({
        status: 200,
        description: "success",
        type: [AccountsDetailsWithTotalCountReadModel]
    })
    @ApiQuery({name: "limit", required: false})
    @ApiQuery({name: "skip", required: false})
    async getList(@Query() paginationParams: PaginationParamsModel) {
        return this._accountsService.getList(paginationParams);
    }


    @Get("/:id")
    @ApiOperation({
        summary: "Get account info"
    })
    @ApiResponse({
        status: 200,
        description: "success",
        type: AccountDetailsReadModel
    })
    async get(@Param("id") id: string) {
        return this._accountsService.get(id);
    }

    @Post("/:id/withdraw")
    @ApiOperation({
        summary: "Withdraw money from account"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async withdraw(@Param("id") id: string, @Body() moneyAmountModel: MoneyAmountModel) {
        await this._accountsService.withdraw(moneyAmountModel.amountOfMoney, id, "testId");
    }

    @Post("/:id/topUp")
    @ApiOperation({
        summary: "Top up bank account"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async topUp(@Param("id") id: string, @Body() moneyAmountModel: MoneyAmountModel) {
        await this._accountsService.topUp(moneyAmountModel.amountOfMoney, id, "testId");
    }

    @Post("/:id/transfer/:receiverId")
    @ApiOperation({
        summary: "Transfer money"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async transfer(@Param("id") id: string, @Param("receiverId") receiverId: string, @Body() moneyAmountModel: MoneyAmountModel) {
        await this._accountsService.transfer(moneyAmountModel.amountOfMoney, id, receiverId, "testId");
    }

    @Post("/:id/block")
    @ApiOperation({
        summary: "Block account"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async block(@Param("id") id: string) {
    }

    @Post("/:id/unblock")
    @ApiOperation({
        summary: "Unblock account"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async unblock(@Param("id") id: string) {
    }

    @Get("/:id/history")
    @ApiOperation({
        summary: "Get history of operations"
    })
    @ApiResponse({
        status: 200,
        description: "success",
        type: [OperationReadModel]
    })
    @ApiQuery({name: "limit", required: false})
    @ApiQuery({name: "skip", required: false})
    async getOperationsHistory(@Param("id") id: string, @Query() paginationParams: PaginationParamsModel) {
        return this._accountsService.getHistory(paginationParams, id);
    }

    @Delete("/:id")
    @ApiOperation({
        summary: "Delete account"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async delete(@Param("id") id: string) {
        await this._accountsService.delete(id);
    }


}
