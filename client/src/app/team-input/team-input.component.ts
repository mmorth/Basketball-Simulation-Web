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
	 * Represents the error message displayed when the user has invalid input
	 */
	inputError: string;

	/**
	 * Stores the position options for the players dropdown
	 */
	positions: number[] = [1, 2, 3, 4, 5];

	/**
	 * Stores the id of the team from the url
	 */
	teamID: number;

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
		this.teamID = +this.route.snapshot.paramMap.get('teamID');

		this.team = new Team();

		if (!isNaN(this.teamID)) {
			this.getTeam();
		}

		this.teamName = "";
	}

	/**
	 * Updates the position of the player
	 * @param player The player to update the position for
	 * @param pos The new game position of the player
	 */
	updatePosition(player: Player, pos: number): void {
		this.playerService.updatePlayer(this.team.id, player.id, player)
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

	/**
	 * Updates the starters player ratings based on their stamina and position
	 * @param player The player
	 * @param playerRating The player rating to update
	 */
	updatePlayerRating(player: Player, playerRating: number): number {
		return ((player.stamina / 100.0) * (1 - Math.abs(player.position - player.positionPlay) * .05)) * playerRating;
	}

	/**
	 * Sorts the players by their player role and game position
	 */
	sortPlayers(): void {
		this.teamService.sortPlayers(this.team.id)
			.subscribe(players => {
				this.team.players = players;
			});
	}

	/**
	 * Gets the team with the specified id
	 */
	getTeam(): void {
		this.teamService.getTeam(this.teamID)
			.subscribe(team => {
				this.team = team;
				this.teamName = team.name;
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
		if (window.confirm('Are sure you want to delete this team?')) {
			this.teamService.deleteTeam(this.teamID)
				.subscribe(
					() => {
						this.team = null;
						this.teamName = "";
						this.router.navigateByUrl('/team-details');
					}
				);
		}

	}

	/**
	 * Update the team with the specified id
	 */
	updateTeam(): void {
		this.teamService.updateTeam(this.teamID)
			.subscribe(
				() => {
					this.team = null;
					this.teamName = "";
					this.inputError = "";
					this.router.navigateByUrl('/team-details');
				}
			);

	}

	/**
	 * Routes to the form to create a new player for this team
	 * @param teamID The id of the team to create the player on
	 */
	createPlayer(teamID: number): void {
		this.router.navigateByUrl('/team-details/' + teamID + '/create/0');
	}

	/**
	 * Routes to the form to create a new coach for this team
	 * @param teamID The id of the team to create the coach on
	 */
	createCoach(teamID: number): void {
		if (this.team.coach == null) {
			this.router.navigateByUrl('/team-details/' + teamID + '/create/1');
		} else {
			this.inputError = "Coach already created";
		}
	}


	// Create a button that, when clicked, will reduce the player's age and contract years and update their overall ratings
		// Have a confirmation window appear to ensure the user wants to update this information
	// Call back-end method that reduces the player's age and contract years and updates their ratings
		// In the back-end, loop through each team by calling teamRepository.all() and reduce age and contract years
			// Create algorithm to reduce player's ratings. Increase ratings until 30 then reduce.
				// Calculate (potential/100) * randomNumber(between 0-3)
					// Result is how much a player's ratings changes.
	// Call back-end method to list all teams
	// Iterate through each team and print out only the desired JSON fields to the teams.JSON file

}
