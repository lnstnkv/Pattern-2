import {
    ConnectedSocket,
    MessageBody,
    OnGatewayConnection,
    OnGatewayDisconnect,
    OnGatewayInit,
    SubscribeMessage,
    WebSocketGateway,
    WsException
} from "@nestjs/websockets";
import {Socket} from "socket.io"
import {Inject, Logger, UseFilters, UsePipes, ValidationPipe} from "@nestjs/common";
import {InjectConnection} from "@nestjs/mongoose";
import {Connection} from "mongoose";
import {AccountsServiceInterface} from "./services/accounts.service.interface";
import {AccountsServiceTransactionsDecorator} from "./services/accounts.service.transactions.decorator";
import {WebSocketGetOperationsHistoryModel} from "~shared/writeModels/websockets/WebSocketGetOperationsHistoryModel";
import {BadRequestTransformationFilter} from "~shared/utils/exceptions.ws.filter";

@WebSocketGateway(8008, {
    cors: {
        origin: "*"
    },
})
export class AccountsGateway implements OnGatewayInit, OnGatewayConnection, OnGatewayDisconnect {
    private readonly _logger = new Logger(AccountsGateway.name);
    private readonly _channelName = "operations-history";

    constructor(
        @InjectConnection() private readonly _mongoConnection: Connection,
        @Inject(AccountsServiceInterface) private readonly _accountsService: AccountsServiceInterface,
    ) {
        this._accountsService = new AccountsServiceTransactionsDecorator(this._accountsService, this._mongoConnection);
    }

    afterInit(server: any): any {
        this._logger.log(`${AccountsGateway.name} initialized`);
    }

    handleConnection(client: any, ...args: any[]): any {
        this._logger.log(`Client connected: ${client.id}`);
    }

    handleDisconnect(client: Socket) {
        this._logger.log(`Client disconnected: ${client.id}`);
    }

    @UseFilters(BadRequestTransformationFilter)
    @UsePipes(new ValidationPipe())
    @SubscribeMessage("history")
    async getOnlineOperationsHistory(@MessageBody() requestModel: WebSocketGetOperationsHistoryModel,
                                     @ConnectedSocket() client: Socket) {
        try {
            client.emit("history", await this._accountsService.getHistory(requestModel.pagination, requestModel.id));
        } catch (err) {
            client.emit("history", new WsException("Bad Request"));
        }
    }


}


