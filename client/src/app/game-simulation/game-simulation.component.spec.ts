import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameSimulationComponent } from './game-simulation.component';

describe('GameSimulationComponent', () => {
  let component: GameSimulationComponent;
  let fixture: ComponentFixture<GameSimulationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameSimulationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameSimulationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
