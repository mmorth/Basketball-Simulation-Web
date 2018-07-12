import { Player } from './player';

/**
 * Represents the team object received from the back-end
 */
export class Team {
	id: number;
	name: string;
	offensiveRating: number;
	defensiveRating: number;
	players: Array<Player>;
	coach: Player;
}