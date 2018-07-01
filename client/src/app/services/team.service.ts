import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Team } from '../models/team';

const HEADERS: any = { headers: { 'Content-Type': 'application/json' }};

@Injectable({
  providedIn: 'root'
})

/**
 * A service used to make REST API calls to the back-end for the Team
 */
export class TeamService {

  /**
   * Constructs a new service
   * @param http Inject http into the service
   */
  constructor(private http: HttpClient) { }

  /**
   * Makes a REST API call to get all the teams from the back-end database
   */
  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>('http://localhost:8080/api/teams')
  }

  /**
   * Makes a REST API call to get a team with the specified id from the back-end database
   * @param id The id of the team to get
   */
  getTeam(id: number): Observable<Team> {
  	return this.http.get<Team>('http://localhost:8080/api/teams/' + id)
  }

  /**
   * Makes a REST API call to create a team with the given information from the back-end database
   * @param teamName The name of the team
   * @param offRating The offensive rating of the team
   * @param defRating The defensive rating of the team
   */
  createTeam(teamName: string, offRating: number, defRating: number) {
  	return this.http.post('http://localhost:8080/api/teams/',
  		JSON.stringify({ "name": teamName, "offensiveRating": offRating, "defensiveRating": defRating }), 
  		HEADERS
  	);
  }

  /**
   * Makes a REST API call to delete the team with the specified id from the back-end database
   * @param teamID The id of the team to delete
   */
  deleteTeam(teamID: number) {
  	return this.http.delete('http://localhost:8080/api/teams/' + teamID);
  }

  /**
   * Makes a REST API call to update the team with the given id from the back-end database
   * @param teamID The id of the team
   * @param teamName The name of the team
   * @param offRating The offensive rating of the team
   * @param defRating The defensive rating of the team
   */
  updateTeam(teamID: number, teamName: string, offRating: number, defRating: number) {
  	return this.http.put('http://localhost:8080/api/teams/' + teamID,
  		JSON.stringify({ "name": teamName, "offensiveRating": offRating, "defensiveRating": defRating }), 
  		HEADERS
  	);
  }

}
