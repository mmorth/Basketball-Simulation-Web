import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

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

  inputError: string;

  constructor(private route: ActivatedRoute, private teamService: TeamService, private location: Location, private router: Router) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');

    if (!isNaN(id)) {
  	 this.getTeam();
    }
  }

  getTeam(): void {
  	const id = +this.route.snapshot.paramMap.get('id');
  	this.teamService.getTeam(id)
    .subscribe(team => {
    	this.team = team;
    	this.teamName = team.name; 
    	this.offensiveRating = team.offensiveRating; 
    	this.defensiveRating = team.defensiveRating
    	}
    );
  }

  createTeam(): void {
  	if (this.teamName.length > 0 && this.offensiveRating > 0 && this.offensiveRating <= 100 && this.defensiveRating > 0 && this.defensiveRating <= 100) {
  		this.teamService.createTeam(this.teamName, this.offensiveRating, this.defensiveRating)
  		.subscribe(() => {
  			this.teamName = "";
  			this.offensiveRating = 0;
  			this.defensiveRating = 0;
				this.inputError = "";
				this.router.navigateByUrl('/team-details');
  		});
    } else {
    	this.inputError = "Invalid Input";
    }

  }

  deleteTeam(): void {
  	const id = +this.route.snapshot.paramMap.get('id');
  	this.teamService.deleteTeam(id)
  	.subscribe(
  		() => {

  		}
  	);

  	this.router.navigateByUrl('/team-details');
  }

  updateTeam(): void {
  	const id = +this.route.snapshot.paramMap.get('id');

  	if (this.offensiveRating > 0 && this.offensiveRating <= 100 && this.defensiveRating > 0 && this.defensiveRating <= 100) {
  	
	  	this.teamService.updateTeam(id, this.teamName, this.offensiveRating, this.defensiveRating)
	  	.subscribe(
	  		() => {
	  			this.teamName = "";
	  			this.offensiveRating = 0;
	  			this.defensiveRating = 0;
	  			this.inputError = "";
	  		}
	  	);	
	  
		this.router.navigateByUrl('/team-details');
	} else {
		this.inputError = "Invalid Input";
	}
  }

}
