import { Component, OnInit } from '@angular/core';

import { GameSimulationService } from '../services/game-simulation.service';

@Component({
  selector: 'app-game-simulation',
  templateUrl: './game-simulation.component.html',
  styleUrls: ['./game-simulation.component.css']
})
export class GameSimulationComponent implements OnInit {

  awayTeamID: number;
  homeTeamID: number;

  constructor(private gameSimulationService: GameSimulationService) { }

  ngOnInit() {
    this.createSimulation()
  }

  createSimulation(): void {
	this.gameSimulationService.createGameSimulation(6, 16)
		.subscribe(() => { });
  }
}
