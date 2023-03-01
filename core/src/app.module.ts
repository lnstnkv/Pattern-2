import {Module} from "@nestjs/common";
import {AccountsModule} from "./modules/accounts/accounts.module";
import {CurrenciesModule} from "./modules/currencies/currencies.module";
import {ConfigModule} from "@nestjs/config";
import {UsersModule} from "./modules/users/users.module";

@Module({
    imports: [
        AccountsModule,
        CurrenciesModule,
        UsersModule,
        ConfigModule.forRoot({
            envFilePath: ".env"
        }),
    ],
})
export class AppModule {
}
