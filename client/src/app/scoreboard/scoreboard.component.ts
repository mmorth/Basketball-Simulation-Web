import { Component, OnInit, Input } from '@angular/core';

import { GameSimulation } from '../models/game-simulation';
import { Team } from '../models/team'

@Component({
  selector: 'app-scoreboard',
  templateUrl: './scoreboard.component.html',
  styleUrls: ['./scoreboard.component.css']
})

/**
 * A component that represents a scoreboard
 */
export class ScoreboardComponent implements OnInit {
  
  /**
   * The game simulation object to pass to the child component
   */
  @Input() gameSimulation: GameSimulation;

  /**
   * Constructs a default ScoreboardComponent
   */
  constructor() { }

  /**
   * Initialize a new ScoreboardComponent
   */
  ngOnInit() {
  }

}
