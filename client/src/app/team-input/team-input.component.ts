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

/**
 * A component that represents creating / updating teams
 */
export class TeamInputComponent implements OnInit {

	/**
	 * The Team
	 */
	team: Team;

	/**
	 * The name of the team
	 */
	teamName: string;
	
	/**
	 * The offensive rating of the team
	 */
	offensiveRating: number;
	
	/**
	 * The defensive rating of the team
	 */
  defensiveRating: number;

	/**
	 * Represents the error message displayed when the user has invalid input
	 */
  inputError: string;

	/**
	 * Constructs a new TeamInputComponent with the following injections
	 * @param route Inject ActivatedRoute into the component
	 * @param teamService Inject TeamService into the component
	 * @param location Inject Location into the component
	 * @param router Inject Router into the component
	 */
  constructor(private route: ActivatedRoute, private teamService: TeamService, private location: Location, private router: Router) { }

	/**
	 * Get the specified team if it exists when the user enters the page
	 */
  ngOnInit(): void {
		const id = +this.route.snapshot.paramMap.get('id');

    if (!isNaN(id)) {
  	 this.getTeam();
    } else {
			this.teamName = "";
			this.offensiveRating = 0;
			this.defensiveRating = 0;
		}
  }

	/**
	 * Gets the team with the specified id
	 */
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

	/**
	 * Create the specified team based on the user's input
	 */
  createTeam(): void {
  	if (this.teamName.length > 0) {
  		this.teamService.createTeam(this.teamName)
  		.subscribe(() => {
				this.team = null;
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

	/**
	 * Deletes the team with the specified id
	 */
  deleteTeam(): void {
		const id = +this.route.snapshot.paramMap.get('id');
		
		if(window.confirm('Are sure you want to delete this team?')){
			this.teamService.deleteTeam(id)
			.subscribe(
				() => {
					this.team = null;
					this.teamName = ""; 
					this.offensiveRating = 0; 
					this.defensiveRating = 0;
					this.router.navigateByUrl('/team-details');
				}
			);
		 }

  }

	/**
	 * Update the team with the specified id
	 */
  updateTeam(): void {
  	const id = +this.route.snapshot.paramMap.get('id');
  	
		this.teamService.updateTeam(id, this.teamName)
		.subscribe(
			() => {
				this.team = null;
				this.teamName = ""; 
				this.offensiveRating = 0; 
				this.defensiveRating = 0;
				this.inputError = "";
				this.router.navigateByUrl('/team-details');
			}
		);	

	}
	
	/**
	 * Creates a new player
	 */
	createPlayer(teamID: number): void {
		this.router.navigateByUrl('/team-details/' + teamID + '/create');
	}

}
