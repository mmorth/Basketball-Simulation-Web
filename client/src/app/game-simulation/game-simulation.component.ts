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
    this.createSimulation();
  }

  ngOnDestroy() {
  	this.deleteSimulation();
  }

  createSimulation(): void {
	  this.gameSimulationService.createGameSimulation(this.awayTeamID, this.homeTeamID)
		.subscribe(gameSimulation => {
        console.log(gameSimulation.id);
        console.log(gameSimulation.id);
        console.log(gameSimulation.awayTeam);
        console.log(gameSimulation.homeTeam);
        console.log(gameSimulation.possessionsRemaining);
        console.log(gameSimulation.isOvertime);
        console.log(gameSimulation.awayTeamScore);
        console.log(gameSimulation.homeTeamScore);
        console.log(gameSimulation.awayTeamPreviousQuarterScore);
        console.log(gameSimulation.homeTeamPreviousQuarterScore);
        this.getSimulation(gameSimulation.id);
    	}
    );
  }

  deleteSimulation(): void {
  	this.gameSimulationService.deleteGameSimulation(this.gameSimulation.id)
  	.subscribe(
  		() => {
        console.log("DELETE")
  		}
  	);
  }

  getSimulation(gameSimID: number): void {
    this.gameSimulationService.getGameSimulation(gameSimID)
    .subscribe(gameSimulation => {
      this.gameSimulation = gameSimulation;
    	}
    );
  }

}
