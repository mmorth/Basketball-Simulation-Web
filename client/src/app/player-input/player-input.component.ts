import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

import { Player } from '../models/player';
import { PlayerService } from '../services/player.service';

/**
 * A class that represents the player and coach create, update, and delete form
 */
@Component({
	selector: 'app-player-input',
	templateUrl: './player-input.component.html',
	styleUrls: ['./player-input.component.css']
})
export class PlayerInputComponent implements OnInit {

	/**
	 * The current player
	 */
	player: Player;

	/**
	 * Represents the error message displayed when the user has invalid input
	 */
	inputError: string;

	/**
	 * Determines whether a coach or a player is selected
	 */
	playerCoachSelector: number;

	/**
	 * The name of the team
	 */
	playerName: string;

	/**
	 * Stores the position options for the players dropdown
	 */
	positions: number[] = [1, 2, 3, 4, 5];

	/**
	 * Stores the player role options for the players dropdown
	 */
	roles: string[] = ["Starter", "Sixth Man", "Role Player", "Prospect", "Bench Warmer"];

	/**
	 * Stores the id of the team from the url
	 */
	teamID: number;

	/**
	 * Stores the id of the player from the url
	 */
	playerID: number;

	/**
	 * Constructs a new TeamInputComponent with the following injections
	 * @param route Inject ActivatedRoute into the component
	 * @param playerService Inject PlayerService into the component
	 * @param location Inject Location into the component
	 * @param router Inject Router into the component
	 */
	constructor(private route: ActivatedRoute, private playerService: PlayerService, private location: Location, private router: Router) { }

	/**
	 * Get the specified player if it exists when the user enters the page
	 */
	ngOnInit(): void {
		this.playerID = +this.route.snapshot.paramMap.get('playerID');
		this.teamID = +this.route.snapshot.paramMap.get('teamID');

		this.player = new Player();

		if (!isNaN(this.playerID)) {
			this.getPlayer();
		}

		this.playerName = "";

		this.playerCoachSelector = +this.route.snapshot.paramMap.get('playerCoachSelector');

	}

	/**
	 * Gets the player with the specified id
	 */
	getPlayer(): void {
		this.playerService.getPlayer(this.playerID)
			.subscribe(player => {
				if (this.player != null) {
					this.player = player;
					this.playerName = player.name;
				} else {
					this.player = new Player();
				}
			}
			);
	}

	/**
	 * Create the specified player based on the user's input
	 */
	createPlayer(): void {
		// Create a player
		if (this.playerCoachSelector === 0) {
			if ((this.playerName.length > 0 || this.player.name.length > 0) && this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100 && this.player.position >= 1 && this.player.position <= 5 && this.player.rotationMinutes >= 0 && this.player.rotationMinutes <= 100 && this.player.role.length > 0) {
				if (this.playerName.length > 0) {
					this.player.name = this.playerName;
				}
				this.player.position = +this.player.position;
				this.playerService.createPlayer(this.teamID, this.player)
					.subscribe(validCreate => {
						if (validCreate) {
							this.player = null;
							this.playerName = "";
							this.inputError = "";
							this.router.navigateByUrl('/team-details/' + this.teamID);
						} else {
							this.inputError = "Invalid Player Role. There are already 5 starters."
						}
					});
			} else {
				this.inputError = "Invalid Input";
			}
		}

		// Create a coach
		if (this.playerCoachSelector === 1) {
			if (this.playerName.length > 0 && this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100) {
				if (this.playerName.length > 0) {
					this.player.name = this.playerName;
				}
				this.playerService.createCoach(this.teamID, this.player)
					.subscribe(() => {
						this.player = null;
						this.playerName = "";
						this.inputError = "";
						this.router.navigateByUrl('/team-details/' + this.teamID);
					});
			} else {
				this.inputError = "Invalid Input";
			}
		}

	}

	/**
	 * Deletes the player with the specified id
	 */
	deletePlayer(): void {
		// Delete a player
		if (this.playerCoachSelector == 0) {
			if (window.confirm('Are sure you want to delete this player?')) {
				this.playerService.deletePlayer(this.teamID, this.playerID)
					.subscribe(
						() => {
							this.player = null;
							this.playerName = "";
							this.inputError = "";
							this.router.navigateByUrl('/team-details/' + this.teamID);
						}
					);
			}
		}

		// Delete a coach
		if (this.playerCoachSelector == 1) {
			if (window.confirm('Are sure you want to delete this coach?')) {
				this.playerService.deleteCoach(this.teamID, this.playerID)
					.subscribe(
						() => {
							this.player = null;
							this.playerName = "";
							this.router.navigateByUrl('/team-details/' + this.teamID);
						}
					);
			}
		}

	}

	/**
	 * Update the player with the specified id
	 */
	updatePlayer(): void {
		// Update a player
		if (this.playerCoachSelector === 0) {
			if (this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100 && this.player.position >= 1 && this.player.position <= 5 && this.player.rotationMinutes >= 0 && this.player.rotationMinutes <= 100 && this.player.role.length > 0 && this.player.stamina >= 0 && this.player.stamina <= 100) {
				this.playerService.updatePlayer(this.teamID, this.playerID, this.player)
					.subscribe(
						validChange => {
							if (validChange) {
								this.player = null;
								this.playerName = "";
								this.inputError = "";
								this.router.navigateByUrl('/team-details/' + this.teamID);
							} else {
								this.inputError = "Invalid Change."
							}
						}
					);

			} else {
				this.inputError = "Invalid Input";
			}
		}

		// Update a coach
		if (this.playerCoachSelector === 1) {
			if (this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100) {
				this.playerService.updateCoach(this.teamID, this.playerID, this.player)
					.subscribe(
						() => {
							this.player = null;
							this.playerName = "";
							this.inputError = "";
							this.router.navigateByUrl('/team-details/' + this.teamID);
						}
					);

			} else {
				this.inputError = "Invalid Input";
			}
		}

	}

}
