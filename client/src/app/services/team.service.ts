import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Team } from '../models/team';
import { Player } from '../models/player';

const HEADERS: any = { headers: { 'Content-Type': 'application/json' } };

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
   * Makes a REST API call to get all the team from the back-end database
   */
  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>('http://localhost:8080/api/teams')
  }

  /**
   * Makes a REST API call to get a team with the specified id from the back-end database
   * @param teamID The id of the team to get
   */
  getTeam(teamID: number): Observable<Team> {
    return this.http.get<Team>('http://localhost:8080/api/teams/' + teamID)
  }

  /**
   * Makes a REST API call to create a team with the given information from the back-end database
   * @param teamName The name of the team
   */
  createTeam(teamName: string) {
    return this.http.post('http://localhost:8080/api/teams/',
      JSON.stringify({ "name": teamName }),
      HEADERS
    );
  }

  /**
   * Creates the list of teams
   * @param teams The teams to create
   */
  createTeams(teams: Team[]) {
    return this.http.post('http://localhost:8080/api/teams/file',
      JSON.stringify(teams),
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
   * Makes a REST API call to delete all teams from the back-end database
   */
  deleteAllTeams() {
    return this.http.delete('http://localhost:8080/api/teams');
  }

  /**
   * Makes a REST API call to update the team with the given id from the back-end database
   * @param teamID The id of the team
   */
  updateTeam(teamID: number) {
    return this.http.put('http://localhost:8080/api/teams/' + teamID,
      JSON.stringify({}),
      HEADERS
    );
  }

  /**
   * Makes a REST API call to sorts the players on the team with the given id from the back-end database
   * @param teamID The team id
   */
  sortPlayers(teamID: number): Observable<Array<Player>> {
    return this.http.get<Array<Player>>('http://localhost:8080/api/teams/' + teamID + '/sort/players');
  }

  /**
   * Reads the list of teams and players to create from the file
   */
  readTeamFile(): Observable<Team[]> {
    return this.http.get<Team[]>("./assets/teams.json")
}

}
