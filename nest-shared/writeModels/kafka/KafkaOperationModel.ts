class KafkaOperationModel {
    callerId: string;

    static fromObject(input) {
        const newModel = new this();
        Object.assign(newModel, input)
        return newModel;
    }
}

export class KafkaTopUpOperationModel extends KafkaOperationModel {
    amountOfMoney: number;
    id: string;
}

export class KafkaWithdrawOperationModel extends KafkaOperationModel {
    amountOfMoney: number;
    id: string;
}

export class KafkaTransferOperationModel extends KafkaOperationModel {
    amountOfMoney: number;
    id: string;
    receiverId: string;
}