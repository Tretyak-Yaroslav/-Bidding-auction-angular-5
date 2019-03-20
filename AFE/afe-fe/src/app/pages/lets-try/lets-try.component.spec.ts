import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LetsTryComponent } from './lets-try.component';

describe('LetsTryComponent', () => {
  let component: LetsTryComponent;
  let fixture: ComponentFixture<LetsTryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LetsTryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LetsTryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
