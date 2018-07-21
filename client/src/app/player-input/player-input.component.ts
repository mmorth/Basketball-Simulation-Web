import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

import { Player } from '../models/player';
import { PlayerService } from '../services/player.service';

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
	pcid: number;

	/**
	 * The name of the team
	 */
	playerName: string;

	/**
	 * 
	 */

	/**
	 * Stores the position options for the players dropdown
	 */
	positions: number[] = [1, 2, 3, 4, 5];

	/**
	 * Stores the player role options for the players dropdown
	 */
	roles: string[] = ["Starter", "Sixth Man", "Role Player", "Prospect", "Bench Warmer"];

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
		const playerID = +this.route.snapshot.paramMap.get('playerID');

		if (!isNaN(playerID)) {
			this.getPlayer();
		} else {
			this.player = new Player();
		}

		this.playerName = "";

		this.pcid = +this.route.snapshot.paramMap.get('pcid');

	}

	/**
	 * Sets the position of the player
	 */
	setPosition(inputPosition: number) {
		console.log(inputPosition)
		this.player.position = inputPosition;
	}

	/**
	 * Sets the role of the player / coach
	 */
	setRole(inputRole: string) {
		this.player.role = inputRole;
	}

	/**
	 * Gets the player with the specified id
	 */
	getPlayer(): void {
		const id = +this.route.snapshot.paramMap.get('playerID');
		this.playerService.getPlayer(id)
			.subscribe(player => {
				if (this.player!= null) {
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
		const id = +this.route.snapshot.paramMap.get('teamID');
		const pcid = +this.route.snapshot.paramMap.get('pcid');

		// Create a player
		if (pcid === 0) {
			if ((this.playerName.length > 0 || this.player.name.length > 0) && this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100 && this.player.position >= 1 && this.player.position <= 5 && this.player.rotationMinutes >= 0 && this.player.rotationMinutes <= 100 && this.player.role.length > 0) {
				if (this.playerName.length > 0) {
					this.player.name = this.playerName;
				}
				this.player.position = +this.player.position;
				this.playerService.createPlayer(id, this.player)
					.subscribe(validCreate => {
						if (validCreate) {
							this.player = null;
							this.playerName = "";
							this.inputError = "";
							this.router.navigateByUrl('/team-details/' + id);
						} else {
							this.inputError = "Invalid Player Role. There are already 5 starters."
						}
					});
			} else {
				this.inputError = "Invalid Input";
			}
		}

		// Create a coach
		if (pcid === 1) {
			if (this.playerName.length > 0 && this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100) {
				this.playerService.createCoach(id, this.player)
					.subscribe(() => {
						this.player = null;
						this.playerName = "";
						this.inputError = "";
						this.router.navigateByUrl('/team-details/' + id);
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
		const teamID = +this.route.snapshot.paramMap.get('teamID');
		const playerID = +this.route.snapshot.paramMap.get('playerID');

		const pcid = +this.route.snapshot.paramMap.get('pcid');

		// Delete a player
		if (pcid == 0) {
			if (window.confirm('Are sure you want to delete this player?')) {
				this.playerService.deletePlayer(teamID, playerID)
					.subscribe(
						() => {
							this.player = null;
							this.playerName = "";
							this.inputError = "";
							this.router.navigateByUrl('/team-details/' + teamID);
						}
					);
			}
		}

		// Delete a coach
		if (pcid == 1) {
			if (window.confirm('Are sure you want to delete this coach?')) {
				this.playerService.deleteCoach(teamID, playerID)
					.subscribe(
						() => {
							this.player = null;
							this.playerName = "";
							this.router.navigateByUrl('/team-details/' + teamID);
						}
					);
			}
		}

	}

	/**
	 * Update the player with the specified id
	 */
	updatePlayer(): void {
		const teamID = +this.route.snapshot.paramMap.get('teamID');
		const playerID = +this.route.snapshot.paramMap.get('playerID');

		const pcid = +this.route.snapshot.paramMap.get('pcid');

		// Update a player
		if (pcid === 0) {
			if (this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100 && this.player.position >= 1 && this.player.position <= 5 && this.player.rotationMinutes >= 0 && this.player.rotationMinutes <= 100 && this.player.role.length > 0 && this.player.stamina >= 0 && this.player.stamina <= 100) {

				this.playerService.updatePlayer(teamID, playerID, this.player)
					.subscribe(
						validChange => {
							if (validChange) {
								this.player = null;
								this.playerName = "";
								this.inputError = "";
								this.router.navigateByUrl('/team-details/' + teamID);
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
		if (pcid === 1) {
			if (this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100) {

				this.playerService.updateCoach(teamID, playerID, this.player)
					.subscribe(
						() => {
							this.player = null;
							this.playerName = "";
							this.inputError = "";
							this.router.navigateByUrl('/team-details/' + teamID);
						}
					);

			} else {
				this.inputError = "Invalid Input";
			}
		}

	}

}
