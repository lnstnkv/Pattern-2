import {Module} from "@nestjs/common";
import {AccountsController} from "./accounts.controller";

@Module({
    providers:[

    ],
    controllers: [AccountsController],

})
export class AccountsModule {
}