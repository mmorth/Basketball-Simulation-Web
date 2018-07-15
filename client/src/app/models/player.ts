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
	possessionsPlayed: number;
	pointsPerGame: number;
	reboundsPerGame: number;
	assistsPerGame: number;
	blocksPerGame: number;
	stealsPerGame: number;
	turnoversPerGame: number;
}