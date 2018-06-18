import { Injectable } from '@angular/core';

import { Team } from '../models/team';
import { TEAMS } from '../models/mock-teams';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor() { }

  getTeams(): Team[] {
  	return TEAMS;
  }
}
