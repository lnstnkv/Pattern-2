import {Module} from "@nestjs/common";
import {AccountsController} from "./accounts.controller";
import {AccountsServiceInterface} from "./accounts.service.interface";
import {AccountsService} from "./accounts.service";
import {AccountsRepositoryInterface} from "./accounts.repository.interface";
import {AccountsMongodbRepository} from "./accounts.mongodb.repository";
import {OperationsModule} from "../operations/operations.module";
import {MongooseModule} from "@nestjs/mongoose";
import {Account, AccountSchema} from "../../schemas/AccountSchema";

@Module({
    providers: [
        {
            provide: AccountsServiceInterface,
            useClass: AccountsService
        },
        {
            provide: AccountsRepositoryInterface,
            useClass: AccountsMongodbRepository
        }

    ],
    controllers: [AccountsController],
    exports: [{
        provide: AccountsServiceInterface,
        useClass: AccountsService
    }, {
        provide: AccountsRepositoryInterface,
        useClass: AccountsMongodbRepository
    }],
    imports: [
        OperationsModule,
        MongooseModule.forFeature([
            {name: Account.name, schema: AccountSchema},
        ])
    ]
})
export class AccountsModule {
}