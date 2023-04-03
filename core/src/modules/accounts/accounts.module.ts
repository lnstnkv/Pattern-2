import {Module} from "@nestjs/common";
import {AccountsController} from "./accounts.controller";
import {AccountsServiceInterface} from "./services/accounts.service.interface";
import {AccountsService} from "./services/accounts.service";
import {AccountsRepositoryInterface} from "./accounts.repository.interface";
import {AccountsMongodbRepository} from "./accounts.mongodb.repository";
import {OperationsModule} from "../operations/operations.module";
import {MongooseModule} from "@nestjs/mongoose";
import {Account, AccountSchema} from "../databases/mongodb/schemas/AccountSchema";
import {AccountsGateway} from "./accounts.gateway";

@Module({
    providers: [
        {
            provide: AccountsServiceInterface,
            useClass: AccountsService
        },
        {
            provide: AccountsRepositoryInterface,
            useClass: AccountsMongodbRepository
        },
        AccountsGateway
    ],
    controllers: [AccountsController],
    exports: [{
        provide: AccountsServiceInterface,
        useClass: AccountsService
    }, {
        provide: AccountsRepositoryInterface,
        useClass: AccountsMongodbRepository
    },
    AccountsGateway],
    imports: [
        OperationsModule,
        MongooseModule.forFeature([
            {name: Account.name, schema: AccountSchema},
        ])
    ]
})
export class AccountsModule {
}