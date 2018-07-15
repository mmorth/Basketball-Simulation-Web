import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Player } from '../models/player';

const HEADERS: any = { headers: { 'Content-Type': 'application/json' }};

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

    /**
   * Constructs a new service
   * @param http Inject http into the service
   */
  constructor(private http: HttpClient) { }

  /**
   * Makes a REST API call to get all the teams from the back-end database
   */
  getPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>('http://localhost:8080/api/players')
  }

  /**
   * Makes a REST API call to get a team with the specified id from the back-end database
   * @param id The id of the team to get
   */
  getPlayer(id: number): Observable<Player> {
  	return this.http.get<Player>('http://localhost:8080/api/players/' + id)
  }

  /**
   * Makes a REST API call to create a player with the given information from the back-end database
   * @param playerName The name of the player
   * @param offRating The offensive rating of the player
   * @param defRating The defensive rating of the player
   */
  createPlayer(teamNumber: number, playerName: string, offRating: number, defRating: number, position: number, isStarter: boolean, rotationMinutes: number) {
  	return this.http.post('http://localhost:8080/api/teams/' + teamNumber + '/players',
  		JSON.stringify({ "name": playerName, "offensiveRating": offRating, "defensiveRating": defRating, "position": position, "isStarter": isStarter, "rotationMinutes": rotationMinutes, "possessionsPlayed": 0 }), 
  		HEADERS
  	);
  }

  /**
   * Makes a REST API call to delete the team with the specified id from the back-end database
   * @param playerID The id of the player to delete
   */
  deletePlayer(teamID: number, playerID: number) {
  	return this.http.delete('http://localhost:8080/api/teams/' + teamID + '/' + playerID);
  }

  /**
   * Makes a REST API call to update the team with the given id from the back-end database
   * @param playerID The id of the player
   * @param playerName The name of the player
   * @param offRating The offensive rating of the player
   * @param defRating The defensive rating of the player
   */
  updatePlayer(teamID: number, playerID: number, playerName: string, offRating: number, defRating: number, position: number, isStarter: boolean, rotationMinutes: number) {
  	return this.http.put('http://localhost:8080/api/teams/' + teamID + '/' + playerID,
  		JSON.stringify({ "name": playerName, "offensiveRating": offRating, "defensiveRating": defRating, "position": position, "isStarter": isStarter, "rotationMinutes": rotationMinutes, "possessionsPlayed": 0 }), 
  		HEADERS
  	);
  }

  /**
   * Makes a REST API call to create a coach with the given information from the back-end database
   * @param coachID The id of the coach
   * @param coachName The name of the coach
   * @param offRating The offensive rating of the coach
   * @param defRating The defensive rating of the coach
   */
  createCoach(teamNumber: number, coachName: string, offRating: number, defRating: number, position: number) {
  	return this.http.post('http://localhost:8080/api/teams/' + teamNumber + '/coach',
  		JSON.stringify({ "name": coachName, "offensiveRating": offRating, "defensiveRating": defRating, "position": position }), 
  		HEADERS
  	);
  }

  /**
   * Makes a REST API call to delete the team with the specified id from the back-end database
   * @param playerID The id of the player to delete
   */
  deleteCoach(teamID: number, coachID: number) {
  	return this.http.delete('http://localhost:8080/api/teams/' + teamID + '/coach/' + coachID);
  }

  /**
   * Makes a REST API call to update the team with the given id from the back-end database
   * @param coachID The id of the coach
   * @param coachName The name of the coach
   * @param offRating The offensive rating of the coach
   * @param defRating The defensive rating of the coach
   */
  updateCoach(teamID: number, coachID: number, coachName: string, offRating: number, defRating: number, position: number) {
  	return this.http.put('http://localhost:8080/api/teams/' + teamID + '/coach/' + coachID,
  		JSON.stringify({ "name": coachName, "offensiveRating": offRating, "defensiveRating": defRating, "position": position }), 
  		HEADERS
  	);
  }

}
