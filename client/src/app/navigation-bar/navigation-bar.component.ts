import { Component, OnInit } from '@angular/core';
import { Team } from '../models/team';
import { TeamService } from '../services/team.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})

/**
 * A component that represents the top navigation bar
 */
export class NavigationBarComponent implements OnInit {

  /**
   * Stores the teams
   */
  teams: Team[];

  /**
   * Construct a default NavigationBarComponent
   */
  constructor(private teamService: TeamService, private router: Router) { }

  /**
   * Initialize the component
   */
  ngOnInit() {

  }

  /**
   * Registers a click on the game simulation page
   */
  clickGameSimulation() {
    this.getTeams();
    console.log("post teams")
  }

  /**
   * Returns a list of created teams
   */
  getTeams() {
    this.teamService.getTeams()
    .subscribe(teams => {this.teams = teams; this.routeGameSim()});
  }

  /**
   * Routes to the Game Simulation page if there is at least 2 teams created
   */
  routeGameSim() {
    if (this.teams.length >= 2) {
      this.router.navigateByUrl('/game-simulation');
      console.log("success")
    } else {
      window.confirm('You must have at least 2 teams created to go to the Game Simulation page.')
      console.log("fail")
    }
  }

}
