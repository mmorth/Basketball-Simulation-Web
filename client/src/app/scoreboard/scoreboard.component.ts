import { Component, OnInit, Input } from '@angular/core';

import { GameSimulation } from '../models/game-simulation';
import { Team } from '../models/team'

@Component({
  selector: 'app-scoreboard',
  templateUrl: './scoreboard.component.html',
  styleUrls: ['./scoreboard.component.css']
})
export class ScoreboardComponent implements OnInit {
  
  @Input() gameSimulation: GameSimulation;

  constructor() { }

  ngOnInit() {
  }

}
