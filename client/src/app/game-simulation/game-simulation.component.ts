import { Component, OnInit } from '@angular/core';

import { GameSimulationService } from '../services/game-simulation.service';
import { GameSimulation } from '../models/game-simulation';
import { Team } from '../models/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-game-simulation',
  templateUrl: './game-simulation.component.html',
  styleUrls: ['./game-simulation.component.css']
})
export class GameSimulationComponent implements OnInit {

  gameSimulation: GameSimulation;
  awayTeamID: number;
  homeTeamID: number;

  constructor(private gameSimulationService: GameSimulationService, private teamService: TeamService) { }

  ngOnInit() {
    this.getSimulation(100);
  }

  ngOnDestroy() {
  	this.deleteSimulation();
  }

  createSimulation(): void {
	  this.gameSimulationService.createGameSimulation(this.awayTeamID, this.homeTeamID)
		.subscribe(gameSimulation => {
        this.gameSimulation = gameSimulation;
    	}
    );
  }

  deleteSimulation(): void {
  	this.gameSimulationService.deleteGameSimulation(this.gameSimulation.id)
  	.subscribe(
  		() => {

  		}
  	);
  }

  getSimulation(gameSimID: number): void {
    this.gameSimulationService.getGameSimulation(gameSimID)
    .subscribe(gameSimulation => {
      this.gameSimulation = gameSimulation;
      console.log(this.gameSimulation.id);
      console.log(this.gameSimulation.awayTeam);
      console.log(this.gameSimulation.homeTeam);
      console.log(this.gameSimulation.possessionsRemaining);
      console.log(this.gameSimulation.isOvertime);
      console.log(this.gameSimulation.awayTeamScore);
      console.log(this.gameSimulation.homeTeamScore);
      console.log(this.gameSimulation.awayTeamPreviousQuarterScore);
      console.log(this.gameSimulation.homeTeamPreviousQuarterScore);
    	}
    );
  }

}
