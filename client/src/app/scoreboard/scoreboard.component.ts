import { Component, OnInit } from '@angular/core';
import { Team } from '../models/team'
import { TEAMS } from '../models/mock-teams'

@Component({
  selector: 'app-scoreboard',
  templateUrl: './scoreboard.component.html',
  styleUrls: ['./scoreboard.component.css']
})
export class ScoreboardComponent implements OnInit {

  homeTeam: Team = {
  	id: 1,
  	name: 'Dragons',
  	score: 0
  };

  awayTeam: Team = {
  	id: 2,
  	name: 'Gators',
  	score: 0
  }

  teams = TEAMS;

  constructor() { }

  ngOnInit() {
  }

}
