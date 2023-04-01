import {ApiOperation, ApiResponse, ApiTags} from "@nestjs/swagger";
import {Body, Controller, Inject, Param, Post} from "@nestjs/common";
import {ObjectIdValidationPipe} from "~shared/utils/database.pipes";
import {MoneyAmountModel} from "~shared/writeModels/MoneyAmountModel";
import {OperationsServiceInterface} from "./operations.service.interface";

@Controller("operations")
@ApiTags("Operations")
export class OperationsController {
    constructor(
        @Inject(OperationsServiceInterface) private readonly _operationsService: OperationsServiceInterface,
    ) {
    }

    @Post("/:id/withdraw")
    @ApiOperation({
        summary: "Withdraw money from account"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async withdraw(@Param("id", ObjectIdValidationPipe) id: string, @Body() moneyAmountModel: MoneyAmountModel) {
        await this._operationsService.withdraw(moneyAmountModel.amountOfMoney, id, "testId");
    }

    @Post("/:id/topUp")
    @ApiOperation({
        summary: "Top up bank account"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async topUp(@Param("id", ObjectIdValidationPipe) id: string, @Body() moneyAmountModel: MoneyAmountModel) {
        await this._operationsService.topUp(moneyAmountModel.amountOfMoney, id, "testId");
    }

    @Post("/:id/transfer/:receiverId")
    @ApiOperation({
        summary: "Transfer money"
    })
    @ApiResponse({
        status: 200,
        description: "success"
    })
    async transfer(@Param("id", ObjectIdValidationPipe) id: string, @Param("receiverId") receiverId: string, @Body() moneyAmountModel: MoneyAmountModel) {
        await this._operationsService.transfer(moneyAmountModel.amountOfMoney, id, receiverId, "testId");
    }

}