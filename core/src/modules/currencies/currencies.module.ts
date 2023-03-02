import {Module} from "@nestjs/common";
import {CurrenciesController} from "./currencies.controller";
import {CurrenciesRepositoryInterface} from "./currencies.repository.interface";
import {CurrenciesMongodbRepository} from "./currencies.mongodb.repository";
import {CurrenciesServiceInterface} from "./currencies.service.interface";
import {CurrenciesService} from "./currencies.service";

@Module({
    providers:[
        {
            provide: CurrenciesRepositoryInterface,
            useClass: CurrenciesMongodbRepository
        },
        {
            provide: CurrenciesServiceInterface,
            useClass: CurrenciesService
        }
    ],
    controllers: [CurrenciesController],

})
export class CurrenciesModule {
}