import {ILoading} from './index';

export enum ClientStatus {
  PENDING = 'PENDING',
  ACTIVE = 'ACTIVE'
}

export class Client implements ILoading {
  _loading: boolean;
  address: string;
  avatar: string;
  avatarSrc?: string;
  atr: number;
  birthday: Date | string;
  clientId: number;
  email: string;
  hidden: boolean;
  name: string;
  phoneNumber: string;
  postcode: string;
  status: ClientStatus;
  questionnaire: {
    id: number;
    questions: {
      answers: [
        {
          code: number;
          text: string
        }
        ];
      code: number;
      text: string
    }[];
  };
}

