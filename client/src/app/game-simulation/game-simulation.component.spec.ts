import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameSimulationComponent } from './game-simulation.component';
import { GameSimulationService } from '../services/game-simulation.service';
import { GameSimulation } from '../models/game-simulation';
import { Team } from '../models/team';
import { TeamService } from '../services/team.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

describe('Component: Login', () => {

  let component: GameSimulationComponent;
  let gsService: GameSimulationService;
  let tService: TeamService;
  let spy: any;
  let httpClient: HttpClient;

  beforeEach(() => { 
    gsService = new GameSimulationService(httpClient);
    tService = new TeamService(httpClient);
    component = new GameSimulationComponent(gsService, tService);
  });

  afterEach(() => { 
    gsService = null;
    tService = null;
    component = null;
  });


  it('creates, gets, and sets the gameSimulation on init', () => {
    const testData: GameSimulation = { 
        "id": 1,
        "awayTeam": {
            id: 1, name: 'Test Team', offensiveRating: 90, defensiveRating: 90
        }, 
        "homeTeam": {
            id: 1, name: 'Test Team', offensiveRating: 90, defensiveRating: 90
        }, 
        "possessionsRemaining": 99, 
        "isOvertime": false, 
        "awayTeamScore": 0, 
        "homeTeamScore": 0, 
        "awayTeamFirstQuarterScore": 0,
        "awayTeamSecondQuarterScore": 0,
        "awayTeamThirdQuarterScore": 0,
        "awayTeamFourthQuarterScore": 0,
        "awayTeamOvertimeScore": 0,
        "homeTeamFirstQuarterScore": 0,
        "homeTeamSecondQuarterScore": 0,
        "homeTeamThirdQuarterScore": 0,
        "homeTeamFourthQuarterScore": 0,
        "homeTeamOvertimeScore": 0,
    };

    spy = spyOn(gsService, 'createGameSimulation').and.returnValue(""); 

  spy = spyOn(gsService, 'createGameSimulation').and.returnValue({ 
    "awayTeam": {
        id: 1, name: 'Test Team', offensiveRating: 90, defensiveRating: 90 
    }, 
    "homeTeam": {
        id: 1, name: 'Test Team', offensiveRating: 90, defensiveRating: 90 
    }, 
    "possessionsRemaining": 99, 
    "isOvertime": false, 
    "awayTeamScore": 0, 
    "homeTeamScore": 0, 
    "awayTeamFirstQuarterScore": 0,
    "awayTeamSecondQuarterScore": 0,
    "awayTeamThirdQuarterScore": 0,
    "awayTeamFourthQuarterScore": 0,
    "awayTeamOvertimeScore": 0,
    "homeTeamFirstQuarterScore": 0,
    "homeTeamSecondQuarterScore": 0,
    "homeTeamThirdQuarterScore": 0,
    "homeTeamFourthQuarterScore": 0,
    "homeTeamOvertimeScore": 0,
    });

    component.ngOnInit();

    expect(component.gameSimulation).toEqual(testData);
  });
});
