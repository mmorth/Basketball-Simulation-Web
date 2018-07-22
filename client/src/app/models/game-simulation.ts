import { Team } from './team';
import { Player } from './player';

/**
 * Represents the GameSimulation object received from the back-end
 */
export class GameSimulation {
	id: number;
	awayTeam: Team;
	homeTeam: Team;
	possessionsRemaining: number;
	isOvertime: boolean;
	awayTeamScore: number;
	homeTeamScore: number;
	awayTeamFirstQuarterScore: number;
	awayTeamSecondQuarterScore: number;
	awayTeamThirdQuarterScore: number;
	awayTeamFourthQuarterScore: number;
	awayTeamOvertimeScore: number;
	homeTeamFirstQuarterScore: number;
	homeTeamSecondQuarterScore: number;
	homeTeamThirdQuarterScore: number;
	homeTeamFourthQuarterScore: number;
	homeTeamOvertimeScore: number;
	awayPlayers: Array<Player>;
	homePlayers: Array<Player>;

	constructor(awayTeam: Team, homeTeam: Team) {
		this.awayTeam = awayTeam;
		this.homeTeam = homeTeam;
		this.possessionsRemaining = 99;
		this.isOvertime = false;
		this.awayTeamScore = 0;
		this.homeTeamScore = 0;
		this.awayTeamFirstQuarterScore = 0;
		this.awayTeamSecondQuarterScore = 0;
		this.awayTeamThirdQuarterScore = 0;
		this.awayTeamFourthQuarterScore = 0;
		this.awayTeamOvertimeScore = 0;
		this.homeTeamFirstQuarterScore = 0;
		this.homeTeamSecondQuarterScore = 0;
		this.homeTeamThirdQuarterScore = 0;
		this.homeTeamFourthQuarterScore = 0;
		this.homeTeamOvertimeScore = 0;
		this.awayPlayers = [];
		this.homePlayers = [];
	}

}