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
	 * Gets the player with the specified id
	 */
  getPlayer(): void {
  	const id = +this.route.snapshot.paramMap.get('playerID');
  	this.playerService.getPlayer(id)
    .subscribe(player => {
    	this.player = player;
    	}
    );
  }

	/**
	 * Create the specified player based on the user's input
	 */
  createPlayer(): void {
    const id = +this.route.snapshot.paramMap.get('teamID');

  	if (this.player.name.length > 0 && this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100) {
  		this.playerService.createPlayer(this.player.id, this.player.name, this.player.offensiveRating, this.player.defensiveRating)
  		.subscribe(() => {
				this.player = null;
				this.inputError = "";
				this.router.navigateByUrl('/team-details/' + id);
  		});
    } else {
    	this.inputError = "Invalid Input";
    }

  }

	/**
	 * Deletes the player with the specified id
	 */
  deletePlayer(): void {
    const teamID = +this.route.snapshot.paramMap.get('teamID');
    const playerID = +this.route.snapshot.paramMap.get('playerID');
    
  	this.playerService.deletePlayer(teamID, playerID)
  	.subscribe(
  		() => {
				this.router.navigateByUrl('/team-details/' + teamID);
  		}
  	);

  }

	/**
	 * Update the player with the specified id
	 */
  updatePlayer(): void {
  	const teamID = +this.route.snapshot.paramMap.get('teamID');
  	const playerID = +this.route.snapshot.paramMap.get('playerID');

  	if (this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100) {
  	
	  	this.playerService.updatePlayer(teamID, playerID, this.player.name, this.player.offensiveRating, this.player.defensiveRating)
	  	.subscribe(
	  		() => {
	  			this.player = null;
					this.inputError = "";
					this.router.navigateByUrl('/team-details/' + teamID);
	  		}
	  	);	
	  
	} else {
		this.inputError = "Invalid Input";
	}
  }

}
