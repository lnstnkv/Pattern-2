import {AccountsRepositoryInterface} from "./accounts.repository.interface";
import {Account, AccountDocument} from "../../schemas/AccountSchema";
import {Model} from "mongoose";
import {InjectModel} from "@nestjs/mongoose";

export class AccountsMongodbRepository implements AccountsRepositoryInterface {
    constructor(@InjectModel(Account.name) private readonly _accountModel: Model<AccountDocument>) {
    }

    async create(account: Account): Promise<void> {
        await this._accountModel.create(account);
    }

    async delete(id: string): Promise<void> {
        await this._accountModel.findByIdAndDelete(id);
    }

    async get(id: string): Promise<Account> {
        return this._accountModel.findById(id);
    }

    async getList(limit: number, skip: number, ownerId: string = null): Promise<Account[]> {
        if (ownerId !== null) {
            if (limit !== null) {
                return this._accountModel.find({ownerId: ownerId}).skip(skip).limit(limit);
            }
            return this._accountModel.find({ownerId: ownerId}).skip(skip);
        }
        if (limit !== null) {
            return this._accountModel.find().skip(skip).limit(limit);
        }
        return this._accountModel.find().skip(skip);
    }

    async getTotalCount(ownerId: string = null): Promise<number> {
        if (ownerId)
            return this._accountModel.count({ownerId: ownerId});
        return this._accountModel.count();
    }

    async update(account: Account): Promise<void> {
        await this._accountModel.findByIdAndUpdate(account._id, account);
    }
}