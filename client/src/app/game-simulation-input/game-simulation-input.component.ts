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

/**
* Represents the game simulation options for the user
*/
export class GameSimulationInputComponent implements OnInit {

  /**
  * Represents the list of teams
  */
  teams: Team[];

  /**
  * The current game simulation
  */
  @Input() gameSimulation: GameSimulation;

  /**
  * Used to send the id of the game simulation to the parent to display
  */
  @Output() gameSimulationUpdate = new EventEmitter<number>();

  /**
  * Displays an error message if exists
  */
  errorString: string;

  /**
  * Constructs a new GameSimulationInputComponent
  * @param teamService Inject the TeamService into the component
  * @param gameSimulationService Inject the GameSimulationService into the component
  */
  constructor(private teamService: TeamService, private gameSimulationService: GameSimulationService) { }

  /**
  * Get the list of teams when the user enters the page
  */
  ngOnInit() {
    this.getTeams();
  }

  /**
  * Update the current game simulation
  */
  updateParentGameSimulation() {
    this.gameSimulationUpdate.next(this.gameSimulation.id);
  }

  /**
  * Gets the list of team
  */
  getTeams(): void {
    this.teamService.getTeams()
      .subscribe(teams => this.teams = teams);
  }

  /**
  * Simulates a possession
  */
  simulatePossession(): void {
    this.gameSimulationService.simulatePossession(this.gameSimulation.id)
      .subscribe(gameSimulation => {
        this.getSimulation(gameSimulation.id);
        this.updateParentGameSimulation();
      });
  }

  /**
  * Simulates a quarter
  */
  simulateQuarter(): void {
    this.gameSimulationService.simulateQuarter(this.gameSimulation.id)
      .subscribe(gameSimulation => {
        this.getSimulation(gameSimulation.id);
        this.updateParentGameSimulation();
      });
  }

  /**
  * Simulates the game
  */
  simulateGame(): void {
    this.gameSimulationService.simulateGame(this.gameSimulation.id)
      .subscribe(gameSimulation => {
        this.getSimulation(gameSimulation.id);
        this.updateParentGameSimulation();
      });
  }

  /**
  * Resets the simulation
  */
  resetSimulation(): void {
    this.gameSimulationService.resetSimulation(this.gameSimulation.id)
      .subscribe(gameSimulation => {
        this.getSimulation(gameSimulation.id);
        this.updateParentGameSimulation();
      });
  }

  /**
  * Sets the away team for the current simulation
  * @param awayTeamID The id of the team to set as the away team for the simulation
  */
  setAwayTeam(awayTeamID: number, awayTeam: Team): void {
    if (awayTeam.players.length >= 5) {
      this.gameSimulationService.setAwayteam(this.gameSimulation.id, awayTeamID)
        .subscribe(gameSimulation => {
          this.getSimulation(gameSimulation.id);
          this.updateParentGameSimulation();
          this.errorString = "";
        });
    } else {
      this.errorString = "Error: Team needs at least 5 players"
    }
  }

  /**
  * Sets the home team for the game simulation
  * @param homeTeamID The id of the team to set as the home team for the simulation
  */
  setHomeTeam(homeTeamID: number, homeTeam: Team): void {
    if (homeTeam.players.length >= 5) {
      this.gameSimulationService.setHometeam(this.gameSimulation.id, homeTeamID)
        .subscribe(gameSimulation => {
          this.getSimulation(gameSimulation.id);
          this.updateParentGameSimulation();
          this.errorString = "";
        });
    } else {
      this.errorString = "Error: Team needs at least 5 players"
    }
  }

  /**
  * Gets the simulation with the given id
  * @param gameSimID The id of the game simulation to get
  */
  getSimulation(gameSimID: number): void {
    this.gameSimulationService.getGameSimulation(gameSimID)
      .subscribe(gameSimulation => {
        this.gameSimulation = gameSimulation;
      }
      );
  }

}
