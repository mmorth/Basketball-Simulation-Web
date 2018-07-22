import { Player } from './player';

/**
 * Represents the team object received from the back-end
 */
export class Team {
	id: number;
	name: string;
	overallRating: number;
	offensiveRating: number;
	defensiveRating: number;
	players: Array<Player>;
	coach: Player;
	pointsPerGame: number;
	reboundsPerGame: number;
	assistsPerGame: number;
	blocksPerGame: number;
	stealsPerGame: number;
	turnoversPerGame: number;

	constructor() {
		this.name = "";
		this.offensiveRating = 0;
		this.defensiveRating = 0;
		this.overallRating = 0;
		this.players = [];
		this.coach = new Player();
		this.pointsPerGame = 0;
		this.reboundsPerGame = 0;
		this.assistsPerGame = 0;
		this.blocksPerGame = 0;
		this.stealsPerGame = 0;
		this.turnoversPerGame = 0;
	}
}