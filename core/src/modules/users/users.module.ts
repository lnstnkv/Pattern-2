import {Module} from "@nestjs/common";
import {UsersController} from "./users.controller";
import {AccountsModule} from "../accounts/accounts.module";

@Module({
    providers: [],
    controllers: [UsersController],
    imports: [
        AccountsModule
    ]
})
export class UsersModule {
}