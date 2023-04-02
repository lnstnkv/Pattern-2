import {ApiOperation, ApiResponse, ApiTags} from "@nestjs/swagger";
import {Body, Controller, Inject, Param, Post} from "@nestjs/common";
import {ObjectIdValidationPipe} from "~shared/utils/database.pipes";
import {MoneyAmountModel} from "~shared/writeModels/MoneyAmountModel";
import {OperationsServiceInterface} from "./operations.service.interface";
import {Client, ClientKafka, Transport} from "@nestjs/microservices";
import {KafkaMoneyOperationsMessagePatterns} from "~shared/constants/KafkaMessagePatterns";
import {
    KafkaTopUpOperationModel,
    KafkaTransferOperationModel,
    KafkaWithdrawOperationModel
} from "~shared/writeModels/kafka/KafkaOperationModel";

@Controller("operations")
@ApiTags("Operations")
export class OperationsController {
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
        @Inject(OperationsServiceInterface) private readonly _operationsService: OperationsServiceInterface,
    ) {
    }

    async onModuleInit() {
        for (const messagePattern in KafkaMoneyOperationsMessagePatterns) {
            this.client.subscribeToResponseOf(KafkaMoneyOperationsMessagePatterns[messagePattern]);
        }

        await this.client.connect();
    }

    @Post("/:id/withdraw")
    @ApiOperation({
        summary: "Withdraw money from account"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async withdraw(@Param("id", ObjectIdValidationPipe) id: string, @Body() moneyAmountModel: MoneyAmountModel) {
        const operationModel = KafkaWithdrawOperationModel.fromObject({
            id: id,
            amountOfMoney: moneyAmountModel.amountOfMoney,
            callerId: "testId"
        });

        await this.client.send(KafkaMoneyOperationsMessagePatterns.WITHDRAW, operationModel);
    }

    @Post("/:id/topUp")
    @ApiOperation({
        summary: "Top up bank account"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async topUp(@Param("id", ObjectIdValidationPipe) id: string, @Body() moneyAmountModel: MoneyAmountModel) {
        const operationModel = KafkaTopUpOperationModel.fromObject({
            id: id,
            amountOfMoney: moneyAmountModel.amountOfMoney,
            callerId: "testId"
        });

        await this.client.send(KafkaMoneyOperationsMessagePatterns.TOP_UP, operationModel);
    }

    @Post("/:id/transfer/:receiverId")
    @ApiOperation({
        summary: "Transfer money"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async transfer(@Param("id", ObjectIdValidationPipe) id: string, @Param("receiverId") receiverId: string, @Body() moneyAmountModel: MoneyAmountModel) {
        const operationModel = KafkaTransferOperationModel.fromObject({
            id: id,
            receiverId: receiverId,
            amountOfMoney: moneyAmountModel.amountOfMoney,
            callerId: "testId"
        });

        await this.client.send(KafkaMoneyOperationsMessagePatterns.TRANSFER, operationModel);
    }

}