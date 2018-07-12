import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Team } from '../models/team';

const HEADERS: any = { headers: { 'Content-Type': 'application/json' }};

@Injectable({
  providedIn: 'root'
})

/**
 * A service used to make REST API calls to the back-end for the Player
 */
export class TeamService {

  /**
   * Constructs a new service
   * @param http Inject http into the service
   */
  constructor(private http: HttpClient) { }

  /**
   * Makes a REST API call to get all the players from the back-end database
   */
  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>('http://localhost:8080/api/teams')
  }

  /**
   * Makes a REST API call to get a team with the specified id from the back-end database
   * @param id The id of the player to get
   */
  getTeam(id: number): Observable<Team> {
  	return this.http.get<Team>('http://localhost:8080/api/teams/' + id)
  }

  /**
   * Makes a REST API call to create a player with the given information from the back-end database
   * @param teamName The name of the player
   * @param offRating The offensive rating of the player
   * @param defRating The defensive rating of the player
   */
  createTeam(teamName: string) {
  	return this.http.post('http://localhost:8080/api/teams/',
  		JSON.stringify({ "name": teamName }), 
  		HEADERS
  	);
  }

  /**
   * Makes a REST API call to delete the player with the specified id from the back-end database
   * @param teamID The id of the player to delete
   */
  deleteTeam(teamID: number) {
  	return this.http.delete('http://localhost:8080/api/teams/' + teamID);
  }

  /**
   * Makes a REST API call to update the player with the given id from the back-end database
   * @param teamID The id of the player
   * @param teamName The name of the player
   * @param offRating The offensive rating of the player
   * @param defRating The defensive rating of the player
   */
  updateTeam(teamID: number, teamName: string) {
  	return this.http.put('http://localhost:8080/api/teams/' + teamID,
  		JSON.stringify({ "name": teamName }), 
  		HEADERS
  	);
  }

}
