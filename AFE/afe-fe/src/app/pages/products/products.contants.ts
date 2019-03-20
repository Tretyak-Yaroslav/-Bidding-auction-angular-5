import {IAlertButton} from '../../modules/alert/alert.interfaces';

export const PRODUCT_APPROVE_DEAL_POST_URL = '/api/v1/product/approve-deal/';
export const PRODUCT_APPROVE_POST_URL = '/api/v1/product/approve/';
export const PRODUCT_REJECT_POST_URL = '/api/v1/product/reject/';

export const BUTTONS = {
  applyReject: Object.assign(new IAlertButton(), {classes: 'btn btn-danger', isConfirm: true, title: 'Reject product'}),
  cancelReject: Object.assign(new IAlertButton(), {classes: 'btn btn-link', isConfirm: false, title: 'Cancel'}),
  apply: Object.assign(new IAlertButton(), {
    classes: 'btn btn-primary',
    isConfirm: true,
    title: 'Apply'
  })
};
