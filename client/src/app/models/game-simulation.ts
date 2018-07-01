import { Team } from './team';

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
}