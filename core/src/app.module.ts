import {Module} from "@nestjs/common";
import {AccountsModule} from "./modules/accounts/accounts.module";
import {CurrenciesModule} from "./modules/currencies/currencies.module";
import {ConfigModule} from "@nestjs/config";

@Module({
    imports: [
        AccountsModule,
        CurrenciesModule,
        ConfigModule.forRoot({
            envFilePath: ".env"
        }),
    ],
})
export class AppModule {
}
