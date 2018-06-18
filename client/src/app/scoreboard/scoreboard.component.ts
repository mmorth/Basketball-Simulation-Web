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
    offensiveRating: 100,
    defensiveRating: 100,
    overallRating: 100,
  	score: 0
  };

  awayTeam: Team = {
  	id: 2,
  	name: 'Gators',
    offensiveRating: 90,
    defensiveRating: 90,
    overallRating: 90,
  	score: 0
  }

  teams = TEAMS;

  constructor() { }

  ngOnInit() {
  }

}
