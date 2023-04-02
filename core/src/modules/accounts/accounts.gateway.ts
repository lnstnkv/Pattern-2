import {ConnectedSocket, MessageBody, SubscribeMessage, WebSocketGateway} from "@nestjs/websockets";
import {Socket} from "socket.io"
import {Logger} from "@nestjs/common";

@WebSocketGateway(80, {
    cors: {
        origin: "*",
    },
})
export class AccountsGateway {
    private readonly _channelName = "operations-history";

    handleDisconnect(client: Socket) {
        Logger.log(`Client disconnected: ${client.id}`);
    }


    @SubscribeMessage("operations-history")
    getOnlineOperationsHistory(@MessageBody() data: string,
                               @ConnectedSocket() client: Socket) {
        client.emit(this._channelName, {message: "test"});

    }
}