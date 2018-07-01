import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { GameSimulationService } from '../services/game-simulation.service';
import { GameSimulation } from '../models/game-simulation';
import { Team } from '../models/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-game-simulation-input',
  templateUrl: './game-simulation-input.component.html',
  styleUrls: ['./game-simulation-input.component.css']
})
export class GameSimulationInputComponent implements OnInit {

  teams: Team[];

  @Input() gameSimulation: GameSimulation;
  @Output() gameSimulationUpdate = new EventEmitter<number>();

  constructor(private teamService: TeamService, private gameSimulationService: GameSimulationService) { }

  ngOnInit() {
    this.getTeams();
  }

  updateParentGameSimulation() {
    this.gameSimulationUpdate.next(this.gameSimulation.id);
  }

  getTeams(): void {
    this.teamService.getTeams()
    .subscribe(teams => this.teams = teams);
  }

  simulatePossession(): void {
    this.gameSimulationService.simulatePossession(this.gameSimulation.id)
    .subscribe(gameSimulation =>{
      this.getSimulation(gameSimulation.id);
      this.updateParentGameSimulation();
    });
  }

  simulateQuarter(): void {
    this.gameSimulationService.simulateQuarter(this.gameSimulation.id)
    .subscribe(gameSimulation =>{
      this.getSimulation(gameSimulation.id);
      this.updateParentGameSimulation();
    });
  }

  simulateGame(): void {
    this.gameSimulationService.simulateGame(this.gameSimulation.id)
    .subscribe(gameSimulation => {
      this.getSimulation(gameSimulation.id);
      this.updateParentGameSimulation();
    });
  }
  
  resetSimulation(): void {
    this.gameSimulationService.resetSimulation(this.gameSimulation.id)
    .subscribe(gameSimulation => {
      this.getSimulation(gameSimulation.id);
      this.updateParentGameSimulation();
    });
  }

  setAwayTeam(awayTeamID: number): void {
    this.gameSimulationService.setAwayteam(this.gameSimulation.id, awayTeamID)
    .subscribe(gameSimulation =>{
      this.getSimulation(gameSimulation.id);
      this.updateParentGameSimulation();
    });
  }

  setHomeTeam(homeTeamID: number): void {
    this.gameSimulationService.setHometeam(this.gameSimulation.id, homeTeamID)
    .subscribe(gameSimulation =>{
      this.getSimulation(gameSimulation.id);
      this.updateParentGameSimulation();
    });
  }

  getSimulation(gameSimID: number): void {
    this.gameSimulationService.getGameSimulation(gameSimID)
    .subscribe(gameSimulation => {
      this.gameSimulation = gameSimulation;
    	}
    );
  }

}
