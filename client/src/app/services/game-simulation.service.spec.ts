import { TestBed, inject } from '@angular/core/testing';

import { GameSimulationService } from './game-simulation.service';

describe('GameSimulationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GameSimulationService]
    });
  });

  it('should be created', inject([GameSimulationService], (service: GameSimulationService) => {
    expect(service).toBeTruthy();
  }));
});
