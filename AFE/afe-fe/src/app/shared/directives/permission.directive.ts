import {Directive, Input, OnDestroy, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {Permission, UserService} from '../services/user.service';

@Directive({
  selector: '[afePermission]'
})
export class PermissionDirective implements OnInit, OnDestroy {

  @Input('afePermission') afePermission: Permission;

  private permission$: Subscription;

  constructor(private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.applyPermission();
  }

  private applyPermission(): void {
    this.permission$ = this.userService.getPermissions()
      .subscribe(perms => {
        if (this.userService.hasPermission(this.afePermission, perms)) {
          this.viewContainer.createEmbeddedView(this.templateRef);
        } else {
          this.viewContainer.clear();
        }
      });
  }

  ngOnDestroy(): void {
    this.permission$.unsubscribe();
  }

}
