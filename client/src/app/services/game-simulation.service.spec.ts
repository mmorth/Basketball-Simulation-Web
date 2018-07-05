// import { TestBed, inject } from '@angular/core/testing';

// describe('GameSimulationService', () => {
//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       providers: [GameSimulationService]
//     });
//   });

//   it('should be created', inject([GameSimulationService], (service: GameSimulationService) => {
//     expect(service).toBeTruthy();
//   }));
// });

import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Team } from '../models/team';
import { TeamService } from './team.service';
import { GameSimulation } from '../models/game-simulation';
import { GameSimulationService } from './game-simulation.service';


describe('GameSimulation Tests', () => {
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeamService, GameSimulationService]
    });

    // Inject the http service and test controller for each test
    httpClient = TestBed.get(HttpClient);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  /// Tests begin ///
  it('can get a GameSimulation', () => {
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

    // Make an HTTP GET request
    httpClient.get<GameSimulation>('http://localhost:8080/api/game-simulation/1/')
      .subscribe(data =>
        // When observable resolves, result should match test data
        expect(data).toEqual(testData)
      );

    // The following `expectOne()` will match the request's URL.
    // If no requests or multiple requests matched that URL
    // `expectOne()` would throw.
    const req = httpTestingController.expectOne('http://localhost:8080/api/game-simulation/1/');

    // Assert that the request is a GET.
    expect(req.request.method).toEqual('GET');

    // Respond with mock data, causing Observable to resolve.
    // Subscribe callback asserts that correct data was returned.
    req.flush(testData);

    // Finally, assert that there are no outstanding requests.
    httpTestingController.verify();
  });

  afterEach(() => {
    // After every test, assert that there are no more pending requests.
    httpTestingController.verify();
  });

  it('should be created', inject([TeamService], (service: TeamService) => {
    expect(service).toBeTruthy();
  }));
});