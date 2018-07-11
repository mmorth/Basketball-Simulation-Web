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
  	return this.http.get<Player>('http://localhost:8080/api/teams/players' + id)
  }

  /**
   * Makes a REST API call to create a team with the given information from the back-end database
   * @param teamName The name of the team
   * @param offRating The offensive rating of the team
   * @param defRating The defensive rating of the team
   */
  createPlayer(teamNumber: number, teamName: string, offRating: number, defRating: number) {
  	return this.http.post('http://localhost:8080/api/teams/' + teamNumber + '/players',
  		JSON.stringify({ "name": teamName, "offensiveRating": offRating, "defensiveRating": defRating }), 
  		HEADERS
  	);
  }

  /**
   * Makes a REST API call to delete the team with the specified id from the back-end database
   * @param teamID The id of the team to delete
   */
  deletePlayer(teamID: number, playerID: number) {
  	return this.http.delete('http://localhost:8080/api/teams/' + teamID + '/' + playerID);
  }

  /**
   * Makes a REST API call to update the team with the given id from the back-end database
   * @param teamID The id of the team
   * @param teamName The name of the team
   * @param offRating The offensive rating of the team
   * @param defRating The defensive rating of the team
   */
  updatePlayer(teamID: number, teamName: string, offRating: number, defRating: number) {
  	return this.http.put('http://localhost:8080/api/teams/' + teamID,
  		JSON.stringify({ "name": teamName, "offensiveRating": offRating, "defensiveRating": defRating }), 
  		HEADERS
  	);
  }

}
