import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component, ElementRef,
  HostBinding,
  HostListener, Injector,
  OnDestroy,
  OnInit
} from '@angular/core';
import { AlertButtonType, IconType, IAlertButton, IAlertConfig, IAlertOptions, IAlertRequest, IAlertResponse } from './alert.interfaces';
import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

@Component({
  selector: 'ks-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AlertComponent implements OnInit, AfterViewInit, OnDestroy {

  IconType = IconType;

  private _subscriptions: Subscription[];
  private _closeTimeOut;

  protected _resSubject: Subject<IAlertResponse>;
  public readonly eventResponse: Observable<IAlertResponse>;
  private _previousFocus: any;

  public cancelButton: IAlertButton = {isConfirm: false, type: AlertButtonType.CANCEL};
  public request: IAlertRequest;
  public options: IAlertOptions;
  public buttons: IAlertButton[];

  @HostBinding('class.disabled') public disabled: boolean;

  public appear = false;
  public disappear = false;

  protected elementRef: ElementRef;
  protected changeDetectorRef: ChangeDetectorRef;
  protected alertConfig: IAlertConfig;

  constructor(protected injector: Injector) {
    this.elementRef = injector.get<ElementRef>( ElementRef);
    this.changeDetectorRef = injector.get( ChangeDetectorRef);
    this.alertConfig = injector.get('alertConfig', {});

    this.disabled = true;
    this.request = {};
    this.options = Object.assign({}, this.alertConfig.options);
    this._subscriptions = [];

    this._resSubject = new Subject();
    this.eventResponse = this._resSubject.asObservable();
  }

  public config(request: IAlertRequest): void {
    clearTimeout(this._closeTimeOut);
    if (request) {
      this.request = request;

      if (request.options) {
        this.options = Object.assign({}, this.alertConfig.options, request.options);
        if (request.options.duration) {
          this._closeTimeOut = setTimeout(() => {
            this.buttonClick(this.cancelButton);
          }, request.options.duration);
        }
      } else {
        this.options = Object.assign({}, this.alertConfig.options);
      }

      this.buttons = [];
      if (this.request.alertButtons) {
        this.request.alertButtons.forEach((i) => {
          if (AlertButtonType.hasOwnProperty(i.toString())) {
            this.buttons.push(this.alertConfig.alertButtonsResource[+i]);
          } else if (i instanceof IAlertButton) {
            this.buttons.push(i as IAlertButton);
          } else {
            throw {
              message: 'Alert Button isn\'t recognize. Make sure that alert button is instance of IconType or IAlertButton.',
              object: i
            };
          }
        });
      }

      this.appear = true;
      this.disabled = false;
      this.changeDetectorRef.markForCheck();
      this.changeDetectorRef.detectChanges();
    }
  }

  ngOnInit() {
    this._subscriptions.push(
      this.eventResponse.subscribe((res) => {
        this.appear = false;
        this.disappear = true;
        this.disabled = true;
        if (this._previousFocus) {
          this._previousFocus.focus();
        }
        this.changeDetectorRef.markForCheck();
        this.changeDetectorRef.detectChanges();
      }));
  }

  ngAfterViewInit() {
    if (document.hasFocus() && document.activeElement) {
      this._previousFocus = document.activeElement;
    }
    const btnFocused = this.elementRef.nativeElement.querySelector('button');
    if (btnFocused) {
      btnFocused.focus();
    }
  }

  ngOnDestroy() {
    clearTimeout(this._closeTimeOut);
    this._subscriptions.forEach(i => i.unsubscribe());
  }

  buttonClick(buttonType: IAlertButton) {
    const alertResponse: IAlertResponse = {
      request: this.request,
      responseButton: buttonType,
      confirm: buttonType.isConfirm
    };

    this.emitResponse(alertResponse);
  }

  @HostListener('window:keyup', ['$event'])
  keyUp($event) {
    if ($event.keyCode === 27) {
      this.buttonClick(this.cancelButton);
    }
  }

  overlayClick() {
    if (this.options.overlayClickToCancel) {
      this.buttonClick(this.cancelButton);
    }
  }

  emitResponse(alertResponse: IAlertResponse) {
    this._resSubject.next(alertResponse);
    this._resSubject.complete();
  }

}
