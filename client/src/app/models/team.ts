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
}