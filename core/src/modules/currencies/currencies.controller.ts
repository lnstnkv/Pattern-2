import {Body, Controller, Delete, Get, Param, Post} from "@nestjs/common";
import {ApiOperation, ApiResponse, ApiTags} from "@nestjs/swagger";
import {CurrencyCreateModel} from "../../writeModels/CurrencyCreateModel";
import {CurrencyReadModel} from "../../readModels/CurrencyReadModel";

@Controller("currencies")
@ApiTags("Currencies service")
export class CurrenciesController {

    @Post("/")
    @ApiOperation({
        summary: "Add new currency"
    })
    @ApiResponse({
        status: 201,
        description: "success"
    })
    async create(@Body() currencyCreateModel: CurrencyCreateModel) {

    }

    @Get("/")
    @ApiOperation({
        summary: "Get list of currencies"
    })
    @ApiResponse({
        status: 200,
        description: "success",
        type: [CurrencyReadModel]
    })
    async getList(): Promise<CurrencyReadModel[]> {
        return [];
    }

    @Delete("/:name")
    @ApiOperation({
        summary: "Delete currency"
    })
    @ApiResponse({
        status: 201,
        description: "success"
    })
    async delete(@Param("name") name: string) {

    }
}