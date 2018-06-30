import { Component, OnInit } from '@angular/core';

import { GameSimulationService } from '../services/game-simulation.service';
import { Team } from '../models/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-game-simulation',
  templateUrl: './game-simulation.component.html',
  styleUrls: ['./game-simulation.component.css']
})
export class GameSimulationComponent implements OnInit {

  gameSimID: number;
  awayTeamID: number;
  homeTeamID: number;
  awayTeam: Team;
  homeTeam: Team;

  constructor(private gameSimulationService: GameSimulationService, private teamService: TeamService) { }

  ngOnInit() {
    this.getAwayTeam();
    this.getHomeTeam();
    this.createSimulation();
  }

  ngOnDestroy() {
  	this.deleteSimulation();
  }

  createSimulation(): void {
	this.gameSimulationService.createGameSimulation(this.awayTeam, this.homeTeam)
		.subscribe(() => { });
  }

  deleteSimulation(): void {
  	this.gameSimulationService.deleteGameSimulation(this.gameSimID)
  	.subscribe(
  		() => {

  		}
  	);
  }

  getAwayTeam(): void {
  	this.teamService.getTeam(47)
    .subscribe(team => {
    	this.homeTeam = team;
    	}
    );
  }

  getHomeTeam(): void {
  	this.teamService.getTeam(48)
    .subscribe(team => {
    	this.homeTeam = team;
    	}
    );
  }

}
