import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Team } from '../models/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-team-input',
  templateUrl: './team-input.component.html',
  styleUrls: ['./team-input.component.css']
})
export class TeamInputComponent implements OnInit {

  team: Team;

  teamName: string;
  offensiveRating: number;
  defensiveRating: number;

  error: string;

  constructor(private route: ActivatedRoute, private teamService: TeamService, private location: Location) { }

  ngOnInit(): void {
  	this.getTeam();
  }

  getTeam(): void {
  	const id = +this.route.snapshot.paramMap.get('id');
  	this.teamService.getTeam(id)
    .subscribe(team => {
    	this.teamName = team.name; 
    	this.offensiveRating = team.offensiveRating; 
    	this.defensiveRating = team.defensiveRating
    	}
    );
  }

  createTeam(): void {
  	if (this.teamName != "" && this.offensiveRating > 0 && this.offensiveRating <= 100 && this.defensiveRating > 0 && this.defensiveRating <= 100) {
  		this.teamService.createTeam(this.teamName, this.offensiveRating, this.defensiveRating)
  		.subscribe(() => {
  			this.teamName = "";
  			this.offensiveRating = 0;
  			this.defensiveRating = 0;
  		});
    }
  }

  deleteTeam(): void {
  	const id = +this.route.snapshot.paramMap.get('id');
  	this.teamService.deleteTeam(id)
  	.subscribe(
  		() => {
  			
  		}
  	);
  }

}
