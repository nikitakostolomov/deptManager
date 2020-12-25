import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDeptBudgetComponent } from './my-dept-budget.component';

describe('MyDeptBudgetComponent', () => {
  let component: MyDeptBudgetComponent;
  let fixture: ComponentFixture<MyDeptBudgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyDeptBudgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDeptBudgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
