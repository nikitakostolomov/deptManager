import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerWorkflowComponent } from './manager-workflow.component';

describe('ManagerWorkflowComponent', () => {
  let component: ManagerWorkflowComponent;
  let fixture: ComponentFixture<ManagerWorkflowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerWorkflowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerWorkflowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
