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

	/**
	 * REST API call to create a new game simulation
	 * @param awayTeamID The id of the away team
	 * @param homeTeamID The id of the home team
	 */
  createGameSimulation(gameSimulation: GameSimulation): Observable<GameSimulation> {
  	return this.http.post<GameSimulation>('http://localhost:8080/api/game-simulation/',
  		JSON.stringify(gameSimulation), 
		{
			headers: { 'Content-Type': 'application/json' },
			observe: 'body',
		}
  	);
  }

	/**
	 * REST API call to delete the game simulation
	 * @param gameSimID The simulation of the game simulation to delete
	 */
  deleteGameSimulation(gameSimID: number) {
  	return this.http.delete('http://localhost:8080/api/game-simulation/' + gameSimID);
  }

	/**
	 * REST API call to return the game simulation with the given id
	 * @param gameSimID The simulation of the game simulation to receive
	 */
  getGameSimulation(gameSimID: number): Observable<GameSimulation> {
  	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID);
  }

	/**
	 * REST API call to simulate a possession
	 * @param gameSimID The game simulation id
	 */
  simulatePossession(gameSimID: number): Observable<GameSimulation> { 
	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/simulate-possession');
  }

	/**
	 * REST API call to simulate a quarter
	 * @param gameSimID The id of the game simulation
	 */
  simulateQuarter(gameSimID: number): Observable<GameSimulation> {
  	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/simulate-quarter');
  }

	/**
	 * REST API call to simulate a game
	 * @param gameSimID The id of the game simulation
	 */
  simulateGame(gameSimID: number): Observable<GameSimulation> {
  	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/simulate-game');
  }

	/**
	 * REST API call to reset the simulation
	 * @param gameSimID The id of the game simulation
	 */
  resetSimulation(gameSimID: number): Observable<GameSimulation> {
	return this.http.get<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/reset-simulation');
  }

	/**
	 * REST API call to set the away team
	 * @param gameSimID The id of the game simulation
	 * @param awayTeamID The id of the away team
	 */
  setAwayteam(gameSimID: number, awayTeamID: number): Observable<GameSimulation> {
	return this.http.put<GameSimulation>('http://localhost:8080/api/game-simulation/' + gameSimID + '/away-team', 
	awayTeamID, 
	{
		headers: { 'Content-Type': 'application/json' },
		observe: 'body',
	}
	);
  }

	/**
	 * REST API call to set the home team
	 * @param gameSimID The id of the game simulation
	 * @param homeTeamID The id of the home team
	 */
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
