import { IPersonInfo } from "./IPersonInfo"

export class IDept {
    amount: string
approvedByReceiver: boolean
approvedBySender: boolean
comment: string
createdAt: string
groupId: string
groupName: string
payer : IPersonInfo
receiver: IPersonInfo
}