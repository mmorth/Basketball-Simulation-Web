import { Component, OnInit } from '@angular/core';

import { Team } from '../models/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css']
})

/**
 * A component that represents the list of teams
 */
export class TeamListComponent implements OnInit {

  /**
   * The list of teams
   */
  teams: Team[];
  
  /**
   * Constructs a new TeamListComponent
   * @param teamService Inject TeamService into the component
   */
  constructor(private teamService: TeamService) { }

  /**
   * Get the list of teams when the user enters the page
   */
  ngOnInit() {
    this.getTeams();
  }

  /**
   * Gets the list of teams
   */
  getTeams(): void {
  this.teamService.getTeams()
      .subscribe(teams => this.teams = teams);
  }

}
