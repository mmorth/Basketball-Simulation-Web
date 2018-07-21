import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Player } from '../models/player';

const HEADERS: any = { headers: { 'Content-Type': 'application/json' } };

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
  getPlayer(playerID: number): Observable<Player> {
    return this.http.get<Player>('http://localhost:8080/api/players/' + playerID)
  }

  /**
   * Makes a REST API call to create a player with the given information from the back-end database
   * @param playerName The name of the player
   * @param offRating The offensive rating of the player
   * @param defRating The defensive rating of the player
   */
  createPlayer(teamNumber: number, player: Player) {
    console.log(JSON.stringify(player))
    return this.http.post('http://localhost:8080/api/teams/' + teamNumber + '/players',
      JSON.parse(JSON.stringify(player)),
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
  updatePlayer(teamID: number, playerID: number, player: Player): Observable<boolean> {
    return this.http.put<boolean>('http://localhost:8080/api/teams/' + teamID + '/' + playerID,
      JSON.stringify(player),
      {
        headers: { 'Content-Type': 'application/json' },
        observe: 'body',
      }
    );
  }

  /**
   * Makes a REST API call to create a coach with the given information from the back-end database
   * @param coachID The id of the coach
   * @param coachName The name of the coach
   * @param offRating The offensive rating of the coach
   * @param defRating The defensive rating of the coach
   */
  createCoach(teamNumber: number, coach: Player) {
    return this.http.post('http://localhost:8080/api/teams/' + teamNumber + '/coach',
      JSON.stringify(coach),
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
  updateCoach(teamID: number, coachID: number, coach: Player) {
    return this.http.put('http://localhost:8080/api/teams/' + teamID + '/coach/' + coachID,
      JSON.stringify(coach),
      HEADERS
    );
  }

}
