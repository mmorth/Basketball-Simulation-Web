import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamInputComponent } from './team-input.component';

describe('TeamInputComponent', () => {
  let component: TeamInputComponent;
  let fixture: ComponentFixture<TeamInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TeamInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
