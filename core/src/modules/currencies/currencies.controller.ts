import {Body, Controller, Delete, Get, Param, Post} from "@nestjs/common";
import {ApiOperation, ApiResponse, ApiTags} from "@nestjs/swagger";
import {CurrencyCreateModel} from "~shared/writeModels/CurrencyCreateModel";
import {CurrencyReadModel} from "../../readModels/CurrencyReadModel";

@Controller("currencies")
@ApiTags("Currencies")
export class CurrenciesController {

    @Post("/")
    @ApiOperation({
        summary: "Add new currency",
        deprecated: true
    })
    @ApiResponse({
        status: 201,
        description: "success"
    })
    async create(@Body() currencyCreateModel: CurrencyCreateModel) {

    }

    @Get("/")
    @ApiOperation({
        summary: "Get list of currencies",
        deprecated: true
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
        summary: "Delete currency",
        deprecated: true
    })
    @ApiResponse({
        status: 201,
        description: "success"
    })
    async delete(@Param("name") name: string) {

    }
}