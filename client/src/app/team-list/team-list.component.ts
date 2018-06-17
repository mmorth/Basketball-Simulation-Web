import { Component, OnInit } from '@angular/core';

import { Team } from '../models/team';
import { TEAMS } from '../models/mock-teams'

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css']
})
export class TeamListComponent implements OnInit {

  teams = TEAMS;
  selectedTeam: Team;
  
  constructor() { }

  ngOnInit() {
  }

  onSelect(team: Team): void {
    this.selectedTeam = team;
  }

}
