import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpEvent } from '@angular/common/http';

import { Team } from '../models/team';
import { GameSimulation } from '../models/game-simulation';

const HEADERS: any = { headers: { 'Content-Type': 'application/json' }};

@Injectable({
  providedIn: 'root'
})
export class GameSimulationService {

  gameSimulation: GameSimulation;

  constructor(private http: HttpClient) { }

  createGameSimulation(awayTeamID: number, homeTeamID: number): Observable<GameSimulation> {
  	return this.http.post<GameSimulation>('http://localhost:8080/api/game-simulation/',
  		JSON.stringify({ 
  		"awayTeam": {
  			"id": 9
  		}, 
  		"homeTeam": {
  			"id": 1
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
	}), 
		{
			headers: { 'Content-Type': 'application/json' },
			observe: 'body',
		}
  	);
  }

  deleteGameSimulation(gameSimID: number) {
  	return this.http.delete('http://localhost:8080/api/game-simulation/' + gameSimID);
  }

  getGameSimulation(gameSimID: number): Observable<GameSimulation> {
  	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID);
  }

  simulatePossession(gameSimID: number): Observable<GameSimulation> { 
	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/simulate-possession');
  }

  simulateQuarter(gameSimID: number): Observable<GameSimulation> {
  	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/simulate-quarter');
  }

  simulateGame(gameSimID: number): Observable<GameSimulation> {
  	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/simulate-game');
  }

  resetSimulation(gameSimID: number): Observable<GameSimulation> {
	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/reset-simulation');
  }

  setAwayteam(gameSimID: number, awayTeamID: number): Observable<GameSimulation> {
	return this.http.put<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/away-team', 
	awayTeamID, 
	{
		headers: { 'Content-Type': 'application/json' },
		observe: 'body',
	}
	);
  }

  setHometeam(gameSimID: number, homeTeamID: number): Observable<GameSimulation> {
	return this.http.put<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/home-team', 
	homeTeamID, 
	{
		headers: { 'Content-Type': 'application/json' },
		observe: 'body',
	}
	);
  }

}
