import { Component, OnInit, Input } from '@angular/core';

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

  constructor(private teamService: TeamService, private gameSimulationSerivce: GameSimulationService) { }

  ngOnInit() {
    this.getTeams();
  }

  getTeams(): void {
    this.teamService.getTeams()
    .subscribe(teams => this.teams = teams);
  }

  simulatePossession(): void {
    this.gameSimulationSerivce.simulatePossession(this.gameSimulation.id)
    .subscribe(gameSimulation => this.gameSimulation = gameSimulation);
  }

  simulateQuarter(): void {
    this.gameSimulationSerivce.simulateQuarter(this.gameSimulation.id)
    .subscribe(gameSimulation => this.gameSimulation = gameSimulation);
  }

  simulateGame(): void {
    this.gameSimulationSerivce.simulateGame(this.gameSimulation.id)
    .subscribe(gameSimulation => this.gameSimulation = gameSimulation);
  }
  
  resetSimulation(): void {
    this.gameSimulationSerivce.resetSimulation(this.gameSimulation.id)
    .subscribe(gameSimulation => this.gameSimulation = gameSimulation);
  }

  setAwayTeam(awayTeamID: number): void {
    this.gameSimulationSerivce.setAwayteam(this.gameSimulation.id, awayTeamID)
    .subscribe(gameSimulation => this.gameSimulation = gameSimulation);
  }

  setHomeTeam(homeTeamID: number): void {
    this.gameSimulationSerivce.setAwayteam(this.gameSimulation.id, homeTeamID)
    .subscribe(gameSimulation => this.gameSimulation = gameSimulation);
  }

}
