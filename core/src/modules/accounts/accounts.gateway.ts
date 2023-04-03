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
import {Inject, Injectable, Logger, UseFilters, UsePipes, ValidationPipe} from "@nestjs/common";
import {InjectConnection} from "@nestjs/mongoose";
import {Connection} from "mongoose";
import {AccountsServiceInterface} from "./services/accounts.service.interface";
import {AccountsServiceTransactionsDecorator} from "./services/accounts.service.transactions.decorator";
import {WebSocketGetOperationsHistoryModel} from "~shared/writeModels/websockets/WebSocketGetOperationsHistoryModel";
import {BadRequestTransformationFilter} from "~shared/utils/exceptions.ws.filter";
import {OperationReadModel} from "../../readModels/OperationReadModel";
import {OperationStatusChangeReadModel} from "../../readModels/OperationStatusChangeReadModel";

@WebSocketGateway(8008, {
    cors: {
        origin: "*"
    },
})
@Injectable()
export class AccountsGateway implements OnGatewayInit, OnGatewayConnection, OnGatewayDisconnect {
    private _clients: SavedWSClient[];
    private readonly _logger = new Logger(AccountsGateway.name);

    constructor(
        @InjectConnection() private readonly _mongoConnection: Connection,
        @Inject(AccountsServiceInterface) private readonly _accountsService: AccountsServiceInterface,
    ) {
        this._accountsService = new AccountsServiceTransactionsDecorator(this._accountsService, this._mongoConnection);
        this._accountsService.setGateway(this);
    }

    afterInit(server: any): any {
        this._logger.log(`${AccountsGateway.name} initialized`);
        this._clients = [];
    }

    handleConnection(client: any, ...args: any[]): any {
        this._logger.log(`Client connected: ${client.id}`);
    }

    handleDisconnect(client: Socket) {
        this._logger.log(`Client disconnected: ${client.id}`);
        this._clients.filter(savedClient => !(client.id === savedClient.id));
    }

    @UseFilters(BadRequestTransformationFilter)
    @UsePipes(new ValidationPipe())
    @SubscribeMessage("history")
    async getOnlineOperationsHistory(@MessageBody() requestModel: WebSocketGetOperationsHistoryModel,
                                     @ConnectedSocket() client: Socket) {
        this._clients.push(SavedWSClient.fromObject({client: client, id: client.id, accountId: requestModel.id}));
        try {
            client.emit("history", await this._accountsService.getHistory(requestModel.pagination, requestModel.id));
        } catch (err) {
            client.emit("history", new WsException("Bad Request"));
        }
    }

    async sendNewOperation(accountId: string, operationReadModel: OperationReadModel) {
        for (const client of this._clients) {
            if (client.accountId === accountId) {
                client.client.emit("newOperation", operationReadModel);
            }
        }
    }

    async sendOperationStatusChange(accountId: string, operationStatusChangeReadModel: OperationStatusChangeReadModel) {
        for (const client of this._clients) {
            if (client.accountId === accountId) {
                client.client.emit("changeOperationStatus", operationStatusChangeReadModel);
            }
        }
    }

}

class SavedWSClient {
    id: string;
    client: Socket;
    accountId: string;

    static fromObject(input) {
        const newModel = new this();
        Object.assign(newModel, input);
        return newModel;
    }
}

