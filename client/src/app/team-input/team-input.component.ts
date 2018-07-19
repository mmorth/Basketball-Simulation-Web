import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

import { Team } from '../models/team';
import { TeamService } from '../services/team.service';
import { Player } from '../models/player';
import { PlayerService } from '../services/player.service';

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
	 * The overall rating of the team
	 */
	overallRating: number;

	/**
	 * Represents the error message displayed when the user has invalid input
	 */
	inputError: string;
		
	/**
	 * Stores the position options for the players dropdown
	 */
	positions: number[] = [1, 2, 3, 4, 5];

	/**
	 * Constructs a new TeamInputComponent with the following injections
	 * @param route Inject ActivatedRoute into the component
	 * @param teamService Inject TeamService into the component
	 * @param location Inject Location into the component
	 * @param router Inject Router into the component
	 */
  constructor(private route: ActivatedRoute, private playerService: PlayerService, private teamService: TeamService, private location: Location, private router: Router) { }

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
	 * Updates the position of the player
	 * @param player The player to update the position for
	 */
	updatePosition(player: Player): void {
			this.playerService.updatePlayer(this.team.id, player.id, player.name, player.offensiveRating, player.defensiveRating, player.position, player.role, player.rotationMinutes, player.stamina, player.positionPlay)
			.subscribe(validChange => {
				if (validChange) {
					this.sortPlayers();
					this.inputError = "";
				} else {
					this.sortPlayers();
					this.inputError = "Invalid player game position. There is already a starter with that position."
				}
			 });
	}

	updatePlayerRating(player: Player, playerRating: number): number {
		console.log()
		console.log(player)
		console.log(player.stamina)
		return ((player.stamina/100.0) * (1-Math.abs(player.position-player.positionPlay)*.05)) * playerRating;
	}

	/**
	 * Updates the displayed offensive rating of the player
	 * @param player The player to update the displayed rating of 
	 */
	updatedOffensiveRating(player: Player): void {
		this.offensiveRating = ((player.stamina/100.0) * (1-Math.abs(player.position-player.positionPlay)*.05)) * player.offensiveRating;
	}

	/**
	 * Updates the displayed defensive rating of the player
	 * @param player The player to update the displayed rating of 
	 */
	updatedDefensiveRating(player: Player): void {
		this.offensiveRating = ((player.stamina/100.0) * (1-Math.abs(player.position-player.positionPlay)*.05)) * player.offensiveRating;
	}

	/**
	 * Updates the displayed overall rating of the player
	 * @param player The player to update the displayed rating of 
	 */	
	updatedOverallRating(player: Player): void {
		this.offensiveRating = ((player.stamina/100.0) * (1-Math.abs(player.position-player.positionPlay)*.05)) * player.offensiveRating;
	}

	/**
	 * Sorts the players by their player role and game position
	 */
	sortPlayers(): void {
		this.teamService.sortPlayers(this.team.id)
		.subscribe( players => {
			this.team.players = players;
		 });
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
		this.defensiveRating = team.defensiveRating;
		this.sortPlayers();
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
		this.router.navigateByUrl('/team-details/' + teamID + '/create/0');
	}

		/**
	 * Creates a new coach
	 */
	createCoach(teamID: number): void {
		if (this.team.coach == null) {
			this.router.navigateByUrl('/team-details/' + teamID + '/create/1');
		} else {
			this.inputError = "Coach already created";
		}
	}

}
