import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupManagmentComponent } from './group-managment.component';

describe('GroupManagmentComponent', () => {
  let component: GroupManagmentComponent;
  let fixture: ComponentFixture<GroupManagmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupManagmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupManagmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
