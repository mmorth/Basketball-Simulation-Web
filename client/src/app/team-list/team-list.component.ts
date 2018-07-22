import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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
   * Stores the list of new teams to be created
   */
  newTeams: Team[];

  /**
   * Constructs a new TeamListComponent
   * @param teamService Inject TeamService into the component
   */
  constructor(private teamService: TeamService, private router: Router) { }

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

  /**
   * Obtains the contents of the file to create the teams from
   */
  obtainFileContents() {
    this.teamService.readTeamFile().subscribe(data => {
      this.newTeams = data;
      this.deleteTeams()
    });
  }

  /**
   * Creates the teams from a file
   * @param teams The teams to create
   */
  createFileTeams(teams: Team[]) {
    this.teamService.createTeams(teams).subscribe(() => this.getTeams());
  }

  /**
   * Deletes all teams
   */
  deleteTeams() {
    this.teamService.deleteAllTeams().subscribe(() => this.createFileTeams(this.newTeams);
  }

}
