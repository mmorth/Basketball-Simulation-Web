import { Component, OnInit } from '@angular/core';

import { Team } from '../models/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-game-simulation-input',
  templateUrl: './game-simulation-input.component.html',
  styleUrls: ['./game-simulation-input.component.css']
})
export class GameSimulationInputComponent implements OnInit {

  teams: Team[];

  constructor(private teamService: TeamService) { }

  ngOnInit() {
    this.getTeams();
  }

  getTeams(): void {
    this.teamService.getTeams()
    .subscribe(teams => this.teams = teams);
  }


}
