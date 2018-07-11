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
	 * Get the specified team if it exists when the user enters the page
	 */
  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');

    if (!isNaN(id)) {
  	 this.getPlayer();
    }
  }

	/**
	 * Gets the team with the specified id
	 */
  getPlayer(): void {
  	const id = +this.route.snapshot.paramMap.get('id');
  	this.playerService.getPlayer(id)
    .subscribe(player => {
    	this.player = player;
    	}
    );
  }

	/**
	 * Create the specified team based on the user's input
	 */
  createPlayer(): void {
  	if (this.player.name.length > 0 && this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100) {
  		this.playerService.createPlayer(this.player.id, this.player.name, this.player.offensiveRating, this.player.defensiveRating)
  		.subscribe(() => {
				this.player = null;
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
  deletePlayer(): void {
  	const id = +this.route.snapshot.paramMap.get('id');
  	this.playerService.deletePlayer(id, this.player.id)
  	.subscribe(
  		() => {
				this.router.navigateByUrl('/team-details');
  		}
  	);

  }

	/**
	 * Update the team with the specified id
	 */
  updatePlayer(): void {
  	const id = +this.route.snapshot.paramMap.get('id');

  	if (this.player.offensiveRating > 0 && this.player.offensiveRating <= 100 && this.player.defensiveRating > 0 && this.player.defensiveRating <= 100) {
  	
	  	this.playerService.updatePlayer(id, this.player.name, this.player.offensiveRating, this.player.defensiveRating)
	  	.subscribe(
	  		() => {
	  			this.player = null;
					this.inputError = "";
					this.router.navigateByUrl('/team-details');
	  		}
	  	);	
	  
	} else {
		this.inputError = "Invalid Input";
	}
  }

}
