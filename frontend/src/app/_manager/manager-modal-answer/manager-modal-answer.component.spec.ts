import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerModalAnswerComponent } from './manager-modal-answer.component';

describe('ManagerModalAnswerComponent', () => {
  let component: ManagerModalAnswerComponent;
  let fixture: ComponentFixture<ManagerModalAnswerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerModalAnswerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerModalAnswerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
