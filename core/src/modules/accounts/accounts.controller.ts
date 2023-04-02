import {Body, Controller, Delete, Get, Inject, Param, Post, Query} from "@nestjs/common";
import {ApiOperation, ApiQuery, ApiResponse, ApiTags} from "@nestjs/swagger";
import {AccountCreateModel} from "~shared/writeModels/AccountCreateModel";
import {AccountsServiceTransactionsDecorator} from "./services/accounts.service.transactions.decorator";
import {PaginationParamsModel} from "~shared/writeModels/PaginationParamsModel";
import {AccountsDetailsWithTotalCountReadModel} from "../../readModels/AccountsDetailsWithTotalCountReadModel";
import {OperationReadModel} from "../../readModels/OperationReadModel";
import {ObjectIdValidationPipe} from "~shared/utils/database.pipes";
import {Connection} from "mongoose";
import {AccountDetailsReadModel} from "../../readModels/AccountDetailsReadModel";
import {AccountsServiceInterface} from "./services/accounts.service.interface";
import {InjectConnection} from "@nestjs/mongoose";
import {Client, ClientKafka, MessagePattern, Payload, Transport} from "@nestjs/microservices"
import {KafkaMoneyOperationsMessagePatterns} from "~shared/constants/KafkaMessagePatterns";
import {KafkaMessageInterface} from "~shared/writeModels/kafka/KafkaMessageInerface";
import {
    KafkaTopUpOperationModel,
    KafkaTransferOperationModel,
    KafkaWithdrawOperationModel
} from "~shared/writeModels/kafka/KafkaOperationModel";


@Controller("accounts")
@ApiTags("Accounts")
export class AccountsController {
    @Client({
        transport: Transport.KAFKA,
        options: {
            client: {
                clientId: "operations",
                brokers: ["localhost:9092"],
            },
            consumer: {
                groupId: "operations-consumer"
            }
        }
    })
    client: ClientKafka;

    constructor(
        @InjectConnection() private readonly _mongoConnection: Connection,
        @Inject(AccountsServiceInterface) private readonly _accountsService: AccountsServiceInterface,
    ) {
        this._accountsService = new AccountsServiceTransactionsDecorator(this._accountsService, this._mongoConnection);
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
    async get(@Param("id", ObjectIdValidationPipe) id: string) {
        return this._accountsService.get(id);
    }

    @MessagePattern(KafkaMoneyOperationsMessagePatterns.WITHDRAW)
    async withdraw(@Payload() writeModel: KafkaMessageInterface<KafkaWithdrawOperationModel>) {
        await this._accountsService.withdraw(writeModel.value.amountOfMoney, writeModel.value.id, writeModel.value.callerId);
    }

    @MessagePattern(KafkaMoneyOperationsMessagePatterns.TOP_UP)
    async topUp(@Payload() writeModel: KafkaMessageInterface<KafkaTopUpOperationModel>) {
        await this._accountsService.topUp(writeModel.value.amountOfMoney, writeModel.value.id, writeModel.value.callerId);
    }

    @MessagePattern(KafkaMoneyOperationsMessagePatterns.TRANSFER)
    async transfer(@Payload() writeModel: KafkaMessageInterface<KafkaTransferOperationModel>) {
        await this._accountsService.transfer(writeModel.value.amountOfMoney, writeModel.value.id, writeModel.value.receiverId, writeModel.value.callerId);
    }

    @Post("/:id/block")
    @ApiOperation({
        summary: "Block account",
        deprecated: true
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async block(@Param("id", ObjectIdValidationPipe) id: string) {
    }

    @Post("/:id/unblock")
    @ApiOperation({
        summary: "Unblock account",
        deprecated: true
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async unblock(@Param("id", ObjectIdValidationPipe) id: string) {
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
    async delete(@Param("id", ObjectIdValidationPipe) id: string) {
        await this._accountsService.delete(id);
    }


}
