import {IsNotEmpty, IsOptional, IsString} from "class-validator";
import {PaginationParamsModel} from "../PaginationParamsModel";

export class WebSocketGetOperationsHistoryModel {
    @IsOptional()
    pagination: PaginationParamsModel
    @IsString()
    @IsNotEmpty()
    id: string
}