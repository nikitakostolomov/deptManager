import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupManagementChildComponent } from './group-management-child.component';

describe('GroupManagementChildComponent', () => {
  let component: GroupManagementChildComponent;
  let fixture: ComponentFixture<GroupManagementChildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupManagementChildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupManagementChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
