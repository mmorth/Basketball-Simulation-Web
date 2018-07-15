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
	 * The name of the player
	 */
	playerName: string;

	/**
	 * The offensive rating of the player
	 */
	offensiveRating: number;

	/**
	 * The defensive rating of the player
	 */
	defensiveRating: number;

	/**
	 * The position of the team
	 */
	position: number;

	/**
	 * The player's role on the team
	 */
	playerRole: string;

	/**
	 * Determines the rotation minutes of the player
	 */
	rotationMinutes: number;

	/**
	 * Represents the error message displayed when the user has invalid input
	 */
  inputError: string;

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
    const id = +this.route.snapshot.paramMap.get('playerID');

    if (!isNaN(id)) {
  	 this.getPlayer();
		}
		
	}
	
	/**
	 * Sets the position of the player
	 */
	setPosition(inputPosition: number) {
		this.position = inputPosition;
	}

	/**
	 * Sets the role of the player / coach
	 */
	setRole(inputRole: string) {
		this.playerRole = inputRole;
	}

	/**
	 * Gets the player with the specified id
	 */
  getPlayer(): void {
  	const id = +this.route.snapshot.paramMap.get('playerID');
  	this.playerService.getPlayer(id)
    .subscribe(player => {
			this.player = player;
			this.playerName = player.name;
			this.offensiveRating = player.offensiveRating;
			this.defensiveRating = player.defensiveRating;
			this.position = player.position;
			this.rotationMinutes = player.rotationMinutes;
			this.playerRole = player.role;
    	}
    );
  }

	/**
	 * Create the specified player based on the user's input
	 */
  createPlayer(): void {
		const id = +this.route.snapshot.paramMap.get('teamID');
		const pcid = +this.route.snapshot.paramMap.get('pcid');

		if (pcid === 0) {
			if (this.playerName.length > 0 && this.offensiveRating > 0 && this.offensiveRating <= 100 && this.defensiveRating > 0 && this.defensiveRating <= 100 && this.position >= 1 && this.position <= 5 && this.rotationMinutes >= 0 && this.rotationMinutes <= 100) {
				this.playerService.createPlayer(id, this.playerName, this.offensiveRating, this.defensiveRating, this.position, this.playerRole, this.rotationMinutes)
				.subscribe(() => {
						this.player = null;
						this.playerName = "";
						this.offensiveRating = 0;
						this.defensiveRating = 0;
						this.position = 0;
						this.rotationMinutes = 0;
						this.playerRole = "";
						this.inputError = "";
						this.router.navigateByUrl('/team-details/' + id);
				});
			} else {
				this.inputError = "Invalid Input";
			}
		}

		if (pcid === 1) {
			if (this.playerName.length > 0 && this.offensiveRating > 0 && this.offensiveRating <= 100 && this.defensiveRating > 0 && this.defensiveRating <= 100 && this.position >= 1 && this.position <= 5) {
				this.playerService.createCoach(id, this.playerName, this.offensiveRating, this.defensiveRating, this.position)
				.subscribe(() => {
						this.player = null;
						this.playerName = "";
						this.offensiveRating = 0;
						this.defensiveRating = 0;
						this.position = 0;
						this.rotationMinutes = 0;
						this.playerRole = "";
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

		if (pcid == 0) {
			if(window.confirm('Are sure you want to delete this player?')) {
				this.playerService.deletePlayer(teamID, playerID)
				.subscribe(
					() => {
						this.player = null;
						this.playerName = "";
						this.offensiveRating = 0;
						this.defensiveRating = 0;
						this.position = 0;
						this.rotationMinutes = 0;
						this.playerRole = "";
						this.inputError = "";
						this.router.navigateByUrl('/team-details/' + teamID);
					}
				);
			}
		}

		if (pcid == 1) {
			if(window.confirm('Are sure you want to delete this coach?')) {
				this.playerService.deleteCoach(teamID, playerID)
				.subscribe(
					() => {
						this.player = null;
						this.playerName = "";
						this.offensiveRating = 0;
						this.defensiveRating = 0;
						this.position = 0;
						this.rotationMinutes = 0;
						this.playerRole = "";
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

		if (pcid === 0) {
			if (this.offensiveRating > 0 && this.offensiveRating <= 100 && this.defensiveRating > 0 && this.defensiveRating <= 100 && this.position >= 1 && this.position <= 5 && this.rotationMinutes >= 0 && this.rotationMinutes <= 100) {
			
				this.playerService.updatePlayer(teamID, playerID, this.playerName, this.offensiveRating, this.defensiveRating, this.position, this.playerRole, this.rotationMinutes)
				.subscribe(
					() => {
						this.player = null;
						this.playerName = "";
						this.offensiveRating = 0;
						this.defensiveRating = 0;
						this.position = 0;
						this.rotationMinutes = 0;
						this.playerRole = "";
						this.inputError = "";
						this.router.navigateByUrl('/team-details/' + teamID);
					}
				);	
			
			} else {
				this.inputError = "Invalid Input";
			}
		}

		if (pcid === 1) {
			if (this.offensiveRating > 0 && this.offensiveRating <= 100 && this.defensiveRating > 0 && this.defensiveRating <= 100 && this.position >= 1 && this.position <= 5) {
			
				this.playerService.updateCoach(teamID, playerID, this.playerName, this.offensiveRating, this.defensiveRating, this.position)
				.subscribe(
					() => {
						this.player = null;
						this.playerName = "";
						this.offensiveRating = 0;
						this.defensiveRating = 0;
						this.position = 0;
						this.rotationMinutes = 0;
						this.playerRole = "";
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
