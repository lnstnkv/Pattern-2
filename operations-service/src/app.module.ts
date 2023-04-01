import {MiddlewareConsumer, Module, RequestMethod} from "@nestjs/common";
import {ConfigModule} from "@nestjs/config";
import {LoggerMiddleware} from "~shared/utils/logger.middleware";
import {OperationsModule} from "./modules/operations/operations.module";

@Module({
    imports: [
        ConfigModule.forRoot({
            envFilePath: ".env"
        }),
        OperationsModule
    ],
})
export class AppModule {
    configure(consumer: MiddlewareConsumer): any {
        consumer
            .apply(LoggerMiddleware)
            .forRoutes({path: "*", method: RequestMethod.ALL});
    }
}
