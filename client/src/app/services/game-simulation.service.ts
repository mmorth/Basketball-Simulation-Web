import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Team } from '../models/team';

const HEADERS: any = { headers: { 'Content-Type': 'application/json' }};

@Injectable({
  providedIn: 'root'
})
export class GameSimulationService {

  constructor(private http: HttpClient) { }

  createGameSimulation(awayTeam: Team, homeTeam: Team) {
  	return this.http.post('http://localhost:8080/api/game-simulation/',
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
  		HEADERS
  	);
  }
}
