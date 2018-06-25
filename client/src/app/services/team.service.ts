import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Team } from '../models/team';

const HEADERS: any = { headers: { 'Content-Type': 'application/json' }};

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor(private http: HttpClient) { }

  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>('http://localhost:8080/api/teams')
  }

  getTeam(id: number): Observable<Team> {
  	return this.http.get<Team>('http://localhost:8080/api/teams/' + id)
  }

  createTeam(teamName: string, offRating: number, defRating: number) {
  	return this.http.post('http://localhost:8080/api/teams/',
  		JSON.stringify({ name: teamName, offensiveRating: offRating, defensiveRating: defRating }), 
  		HEADERS
  	);
  }

  deleteTeam(teamID: number) {
  	return this.http.delete('http://localhost:8080/api/teams/' + teamID)
  }

  updateTeam(teamID: number, teamName: string, offRating: number, defRating: number) {
  	return this.http.put('http://localhost:8080/api/teams/' + teamID,
  		JSON.stringify({ name: teamName, offensiveRating: offRating, defensiveRating: defRating }), 
  		HEADERS
  	);
  }

}
