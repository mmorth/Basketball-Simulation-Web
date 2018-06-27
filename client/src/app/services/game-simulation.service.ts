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

  createGameSimulation(awayTeamID: number, homeTeamID: number) {
  	return this.http.post('http://localhost:8080/api/game-simulation/' + awayTeamID + '/' + homeTeamID);
  }



}
