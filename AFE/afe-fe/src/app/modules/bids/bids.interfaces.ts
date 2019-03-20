export enum BidStatus {
  SUBMITTED
}

export enum BidType {
  BID
}

export enum BidStatusType {
  SUBMITTED = 'SUBMITTED', // make bid
  ACCEPTED = 'ACCEPTED', // own accept
  CLOSED = 'CLOSED', // hided clients
  COMPLETED = 'COMPLETED', // manager submit
  WITHDRAWA = 'WITHDRAWAL' // cancel
}

export class Bid {
  amount: number;
  clientId: number;
  productId: number;
  status: BidStatusType;
  timestamp: Date;
  txId: number;
  type: BidType;
}

