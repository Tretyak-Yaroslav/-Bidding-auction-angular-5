import { TemplateRef } from '@angular/core';
import { AlertComponent } from './alert.component';

export enum IconType {
  NONE = 0,
  SUCCESS = 1,
  WARNING,
  DANGER,
  INFO,
}

export enum AlertButtonType {
  NONE = 0,
  YES = 1,
  OK,
  RETRY,
  NO,
  ABORT,
  CANCEL,
  IGNORE,
  CONFIRM,
  ALLOW,
  DENY
}

export class IAlertButton {
  title?: string;
  classes?: string; // css class
  isConfirm: boolean;
  type?: AlertButtonType;
}

export class IAlertIcon {
  src?: string;
  classes?: string;
}

export interface IAlertOptions {
  overlay?: boolean;
  overlayClickToCancel?: boolean;
  showCloseButton?: boolean;
  duration?: number; // after time out the alert will be closed with CANCEL trigger
}

export interface IAlertRequest {
  caption?: string;
  text?: string;
  iconType?: IconType | IAlertIcon;
  alertButtons?: (AlertButtonType | IAlertButton) [];
  options?: IAlertOptions;
  componentRef?: typeof AlertComponent;
  templateRef?: TemplateRef<any>;
  timeoutBeforeDestroy?: number;
}

export class IAlertResponse {
  request: IAlertRequest;
  responseButton: IAlertButton;
  confirm: boolean;
}

export class IAlertConfig {
  // caption?: string;
  // text?: string;
  // iconType?: IconType | IAlertIcon;
  // alertButtons?: (AlertButtonType | IAlertButton) [];
  options?: IAlertOptions;
  componentRef?: typeof AlertComponent;
  // templateRef?: TemplateRef<any>;
  alertButtonsResource?: IAlertButton[]; // sequential are the same as AlertButtonType enum
  rootElement?: any; // set parent of alertComponent
  timeoutBeforeDestroy?: number; // time for disappear animation
}
