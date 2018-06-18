import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { Team } from '../models/team';
import { TEAMS } from '../models/mock-teams';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor() { }

  getTeams(): Observable<Team[]> {
    return of(TEAMS);
  }
}
