import {OperationsServiceInterface} from "./operations.service.interface";
import {OperationsService} from "./operations.service";
import {OperationsController} from "./operations.controller";
import {Module} from "@nestjs/common";

@Module({
    providers: [
        {
            provide: OperationsServiceInterface,
            useClass: OperationsService
        }

    ],
    controllers: [OperationsController],
    exports: [{
        provide: OperationsServiceInterface,
        useClass: OperationsService
    }]
})
export class OperationsModule {
}