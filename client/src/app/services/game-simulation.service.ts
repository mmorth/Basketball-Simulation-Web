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

  createGameSimulation() {
  	return this.http.post<Team[]>('http://localhost:8080/api')
  }

  selectTeams(awayTeam: Team, homeTeam: Team) {
  	return this.http.put<Team[]>('http://localhost:8080/api')
  }

  deleteGameSimulation(gameSimID: number) {
  	return this.http.delete<Team[]>('http://localhost:8080/api')
  }

  getTeamScores: Observable<number> {
  	return this.http.get<Team[]>('http://localhost:8080/api')
  }

  getQuarterScores(): Observable<number> {
  	return this.http.get<Team[]>('http://localhost:8080/api')
  }

  getPossessionsRemaining(): Observable<number> {
  	return this.http.get<Team[]>('http://localhost:8080/api')
  }

  simulationPossession(): Observable<number> {
  	return this.http.get<Team[]>('http://localhost:8080/api')
  }

  simulateQuarter(): Observable<number> {
  	return this.http.get<Team[]>('http://localhost:8080/api')
  }

  simulateGame(): Observable<number> {
  	return this.http.get<Team[]>('http://localhost:8080/api')
  }

  resetSimulation(): Observable<number> {
  	return this.http.get<Team[]>('http://localhost:8080/api')
  }

}
