import {AccountsServiceBaseDecorator} from "./accounts.service.base.decorator";
import {Connection} from "mongoose";
import {AccountDetailsReadModel} from "../../../readModels/AccountDetailsReadModel";
import {AccountsServiceInterface} from "./accounts.service.interface";
import {InjectConnection} from "@nestjs/mongoose";
import {AccountCreateModel} from "~shared/writeModels/AccountCreateModel";

export class AccountsServiceTransactionsDecorator extends AccountsServiceBaseDecorator {

    public constructor(
        accountsService: AccountsServiceInterface,
        @InjectConnection() private readonly _mongoConnection: Connection) {
        super(accountsService);
    }

    async create(accountCreateModel: AccountCreateModel): Promise<AccountDetailsReadModel> {
        let result;
        await this._mongoConnection.startSession()
            .then(async (clientSession) => {
                await clientSession.withTransaction(async () => {
                    result = await this._wrapped.create(accountCreateModel);
                });
                await clientSession.endSession();
            });

        return result;
    }

    async delete(id: string): Promise<void> {
        await this._mongoConnection.startSession()
            .then(async (clientSession) => {
                await clientSession.withTransaction(async () => {
                    await this._wrapped.delete(id);
                });
                await clientSession.endSession();
            });
    }

    async topUp(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        await this._mongoConnection.startSession()
            .then(async (clientSession) => {
                await clientSession.withTransaction(async () => {
                    await this._wrapped.topUp(amountOfMoney, id, callerId);
                });
                await clientSession.endSession();
            });
    }

    async transfer(amountOfMoney: number, id: string, receiverId: string, callerId: string): Promise<void> {
        await this._mongoConnection.startSession()
            .then(async (clientSession) => {
                await clientSession.withTransaction(async () => {
                    await this._wrapped.transfer(amountOfMoney, id, receiverId, callerId);
                });
                await clientSession.endSession();
            });
    }

    async withdraw(amountOfMoney: number, id: string, callerId: string): Promise<void> {
        await this._mongoConnection.startSession()
            .then(async (clientSession) => {
                await clientSession.withTransaction(async () => {
                    await this._wrapped.withdraw(amountOfMoney, id, callerId);
                });
                await clientSession.endSession();
            });
    }
}