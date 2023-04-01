import {MiddlewareConsumer, Module, NestModule, RequestMethod} from "@nestjs/common";
import {AccountsModule} from "./modules/accounts/accounts.module";
import {CurrenciesModule} from "./modules/currencies/currencies.module";
import {ConfigModule} from "@nestjs/config";
import {UsersModule} from "./modules/users/users.module";
import {DatabaseConfigModule} from "./modules/databases/mongodb/database.module";
import {DatabaseConfigService} from "./modules/databases/mongodb/database.config";
import {MongooseModule} from "@nestjs/mongoose";
import {OperationsModule} from "./modules/operations/operations.module";
import {LoggerMiddleware} from "~shared/utils/logger.middleware";

@Module({
    imports: [
        AccountsModule,
        CurrenciesModule,
        UsersModule,
        OperationsModule,
        ConfigModule.forRoot({
            envFilePath: ".env"
        }),
        DatabaseConfigModule,
        MongooseModule.forRootAsync({
            inject: [DatabaseConfigService],
            useFactory: async (configService: DatabaseConfigService) => configService.getMongoConfig()
        }),

    ],
})
export class AppModule implements NestModule {
    configure(consumer: MiddlewareConsumer): any {
        consumer
            .apply(LoggerMiddleware)
            .forRoutes({path: "*", method: RequestMethod.ALL});
    }
}
