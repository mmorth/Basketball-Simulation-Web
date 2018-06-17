import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameSimulationInputComponent } from './game-simulation-input.component';

describe('GameSimulationInputComponent', () => {
  let component: GameSimulationInputComponent;
  let fixture: ComponentFixture<GameSimulationInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameSimulationInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameSimulationInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
