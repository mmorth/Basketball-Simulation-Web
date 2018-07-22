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

/**
 * A Component representing a single game simulation
 */
export class GameSimulationComponent implements OnInit {

  /**
   * The current game simulation
   */
  gameSimulation: GameSimulation;

  /**
   * Stores the created teams
   */
  teams: Team[];

  /**
   * Controls which stat page gets displayed on the bottom of the page
   * 1=Team Stats, 2=Away Players, 3=Home Players
   */
  statDisplay: number;

  /**
   * Constructs a new GameSimulationComponet object
   * @param gameSimulationService Inject the GameSimulationService into the component
   * @param teamService Inject the TeamService into the component
   */
  constructor(private gameSimulationService: GameSimulationService, private teamService: TeamService) { }

  /**
   * Creates a new game simulation when the user enters the page
   */
  ngOnInit() {
    this.getTeams();
    this.statDisplay = 1;
  }

  /**
 * Gets the list of team
 */
  getTeams(): void {
    this.teamService.getTeams()
      .subscribe(teams => { this.teams = teams; this.createSimulation() });
  }

  /**
   * Changes which stat is displayed at the bottom of the page based on what button the user clicks
   * @param stat The new stat to display
   */
  changeStatDisplay(stat: number) {
    this.statDisplay = stat;
  }

  /**
   * Creates a new game simulation
   */
  createSimulation(): void {
    let gameSim = new GameSimulation(this.teams[0], this.teams[1]);
    this.gameSimulationService.createGameSimulation(gameSim)
      .subscribe(gameSimulation => {
        this.getSimulation(gameSimulation.id);
      }
      );
  }

  /**
   * Gets the current game simulation
   * @param gameSimID The id of the game simulation to retrieve
   */
  getSimulation(gameSimID: number): void {
    this.gameSimulationService.getGameSimulation(gameSimID)
      .subscribe(gameSimulation => {
        this.gameSimulation = gameSimulation;
      }
      );
  }

}
