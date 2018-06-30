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
  			"id": 47
  		}, 
  		"homeTeam": {
  			"id": 48
  		}, 
  		"possessionsRemaining": 99, 
  		"isOvertime": 0, 
  		"awayTeamScore": 0, 
  		"homeTeamScore": 0, 
  		"awayTeamPreviousQuarterScore": 0, 
  		"homeTeamPreviousQuarterScore": 0 }), 
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


}
