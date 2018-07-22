import { Component, OnInit } from '@angular/core';

import { GameSimulationService } from '../services/game-simulation.service';
import { GameSimulation } from '../models/game-simulation';
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
   * The id of the away team for the game simulation
   */
  awayTeamID: number;

  /**
   * The id of the home team for the game simulation
   */
  homeTeamID: number;

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
    console.log("NGINIT")
    this.createSimulation();
    this.statDisplay = 1;
  }

  /**
   * Deletes the current game simulation when the user leaves the page
   */
  ngOnDestroy() {
    this.deleteSimulation();
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
    let gameSim = new GameSimulation();
    this.gameSimulationService.createGameSimulation(gameSim)
      .subscribe(gameSimulation => {
        console.log();
        this.getSimulation(gameSimulation.id);
      }
      );
  }

  /**
   * Deletes the current game simulation
   */
  deleteSimulation(): void {
    this.gameSimulationService.deleteGameSimulation(this.gameSimulation.id)
      .subscribe(
        () => {
          console.log("DELETE")
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
