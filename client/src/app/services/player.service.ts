import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Player } from '../models/player';

const HEADERS: any = { headers: { 'Content-Type': 'application/json' } };

/**
 * This class makes REST API calls to the back-end related to the Player object
 */
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
   * Makes a REST API call to get all the players from the back-end database
   */
  getPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>('http://localhost:8080/api/players')
  }

  /**
   * Makes a REST API call to get a player with the specified id from the back-end database
   * @param playerID The id of the team to get
   */
  getPlayer(playerID: number): Observable<Player> {
    return this.http.get<Player>('http://localhost:8080/api/players/' + playerID)
  }

  /**
   * Makes a REST API call to create a player with the given information from the back-end database
   * @param teamNumber The number of the team the player will be created on
   * @param player The player to create
   */
  createPlayer(teamNumber: number, player: Player) {
    return this.http.post('http://localhost:8080/api/teams/' + teamNumber + '/players',
      JSON.parse(JSON.stringify(player)),
      HEADERS
    );
  }

  /**
   * Makes a REST API call to delete the player with the specified id from the back-end database
   * @param teamID The id of the team to delete the player from
   * @param playerID The id of the player to delete
   */
  deletePlayer(teamID: number, playerID: number) {
    return this.http.delete('http://localhost:8080/api/teams/' + teamID + '/' + playerID);
  }

  /**
   * Makes a REST API call to update the player with the given id from the back-end database
   * @param teamID The id of the team the player is on
   * @param playerID The id of the player
   * @param player The player object
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
   * @param teamID The id of the team the coach is on
   * @param coach The coach object to create
   */
  createCoach(teamID: number, coach: Player) {
    return this.http.post('http://localhost:8080/api/teams/' + teamID + '/coach',
      JSON.stringify(coach),
      HEADERS
    );
  }

  /**
   * Makes a REST API call to delete the coach with the specified id from the back-end database
   * @param teamID The id of the team the coach is on
   * @param coachID The id of the coach to delete
   */
  deleteCoach(teamID: number, coachID: number) {
    return this.http.delete('http://localhost:8080/api/teams/' + teamID + '/coach/' + coachID);
  }

  /**
   * Makes a REST API call to update the coach with the given id from the back-end database
   * @param teamID The id of the team the coach is on
   * @param coachID The id of the coach
   * @param coach The coach object
   */
  updateCoach(teamID: number, coachID: number, coach: Player) {
    return this.http.put('http://localhost:8080/api/teams/' + teamID + '/coach/' + coachID,
      JSON.stringify(coach),
      HEADERS
    );
  }

}
