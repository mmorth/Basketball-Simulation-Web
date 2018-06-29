import { Component, OnInit } from '@angular/core';
import { Team } from '../models/team'

@Component({
  selector: 'app-scoreboard',
  templateUrl: './scoreboard.component.html',
  styleUrls: ['./scoreboard.component.css']
})
export class ScoreboardComponent implements OnInit {

  homeTeam: Team = {
  	id: 1,
  	name: 'Home',
    offensiveRating: 100,
    defensiveRating: 100,
  };

  awayTeam: Team = {
  	id: 2,
  	name: 'Away',
    offensiveRating: 90,
    defensiveRating: 90,
  }

  constructor() { }

  ngOnInit() {
  }

}
