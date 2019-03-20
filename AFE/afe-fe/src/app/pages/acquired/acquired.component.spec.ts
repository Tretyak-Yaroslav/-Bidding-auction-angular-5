import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcquiredComponent } from './acquired.component';

describe('AcquiredComponent', () => {
  let component: AcquiredComponent;
  let fixture: ComponentFixture<AcquiredComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcquiredComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcquiredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
