import {ApiOperation, ApiResponse, ApiTags} from "@nestjs/swagger";
import {Body, Controller, Inject, Logger, Param, Post} from "@nestjs/common";
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
                brokers: ["kafka:19092"],
            },
            consumer: {
                groupId: "operations-consumer"
            }
        }
    })
    _client: ClientKafka;

    _logger: Logger = new Logger(OperationsController.name);

    constructor(
        @Inject(OperationsServiceInterface) private readonly _operationsService: OperationsServiceInterface,
    ) {
    }

    async onModuleInit() {
        for (const messagePattern in KafkaMoneyOperationsMessagePatterns) {
            this._client.subscribeToResponseOf(KafkaMoneyOperationsMessagePatterns[messagePattern]);
            this._logger.log(`Subscribed to messagePattern ${KafkaMoneyOperationsMessagePatterns[messagePattern]}`);
        }
        await this._client.connect();

        this._logger.log("Kafka client connected");
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
        this._logger.log(`Sent withdraw msg ${operationModel}`);
        await this._client.emit(KafkaMoneyOperationsMessagePatterns.WITHDRAW, operationModel);
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
        this._logger.log(`Sent topUp msg ${operationModel}`);
        await this._client.emit(KafkaMoneyOperationsMessagePatterns.TOP_UP, operationModel);
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
        this._logger.log(`Sent transfer msg ${operationModel}`);
        await this._client.emit(KafkaMoneyOperationsMessagePatterns.TRANSFER, operationModel);
    }

}