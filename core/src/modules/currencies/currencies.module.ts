import {Module} from "@nestjs/common";
import {CurrenciesController} from "./currencies.controller";

@Module({
    providers:[

    ],
    controllers: [CurrenciesController],

})
export class CurrenciesModule {
}