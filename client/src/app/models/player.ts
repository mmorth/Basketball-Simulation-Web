/**
 * Represents the player object received from the back-end
 */
export class Player {
	id: number;
	name: string;
	offensiveRating: number;
	defensiveRating: number;
	overallRating: number;
	position: number;
	role: string;
	rotationMinutes: number;
	stamina: number;
	positionPlay: number;
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
		this.position = 0;
		this.role = "";
		this.rotationMinutes = 0;
		this.stamina = 100;
		this.positionPlay = 0;
		this.pointsPerGame = 0;
		this.reboundsPerGame = 0;
		this.assistsPerGame = 0;
		this.blocksPerGame = 0;
		this.stealsPerGame = 0;
		this.turnoversPerGame = 0;
	}
}