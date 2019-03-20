import { IAlertButton, AlertButtonType, IAlertOptions, IAlertConfig } from './alert.interfaces';
import { AlertComponent } from './alert.component';

export const ALERT_BUTTONS: IAlertButton[] = [
  {isConfirm: false, classes: 'd-none', type: AlertButtonType.NONE},
  {isConfirm: true, classes: 'btn btn-primary', title: 'Yes', type: AlertButtonType.YES},
  {isConfirm: true, classes: 'btn btn-primary', title: 'Ok', type: AlertButtonType.OK},
  {isConfirm: true, classes: 'btn btn-primary', title: 'Retry', type: AlertButtonType.RETRY},
  {isConfirm: false, classes: 'btn btn-primary', title: 'No', type: AlertButtonType.NO},
  {isConfirm: false, classes: 'btn btn-primary', title: 'Abort', type: AlertButtonType.ABORT},
  {isConfirm: false, classes: 'btn btn-secondary', title: 'Cancel', type: AlertButtonType.CANCEL},
  {isConfirm: false, classes: 'btn btn-secondary', title: 'Ignore', type: AlertButtonType.IGNORE},
  {isConfirm: true, classes: 'btn btn-primary', title: 'Confirm', type: AlertButtonType.CONFIRM},
  {isConfirm: true, classes: 'btn btn-primary', title: 'Allow', type: AlertButtonType.ALLOW},
  {isConfirm: false, classes: 'btn btn-primary', title: 'Deny', type: AlertButtonType.DENY},
];

export const DEFAULT_OPTIONS: IAlertOptions = {
  overlay: true,
  overlayClickToCancel: true,
  showCloseButton: true,
  duration: null
};


export const DEFAULT_CONFIG: IAlertConfig = {
  componentRef: AlertComponent,
  options: DEFAULT_OPTIONS,
  alertButtonsResource: ALERT_BUTTONS,
  rootElement: document.body,
  timeoutBeforeDestroy: 600
};
