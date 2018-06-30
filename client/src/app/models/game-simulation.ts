import { Team } from './team';

export class GameSimulation {
	id: number;
	awayTeam: Team;
	homeTeam: Team;
	possessionsRemaining: number;
	isOvertime: boolean;
	awayTeamScore: number;
	homeTeamScore: number;
	awayTeamPreviousQuarterScore: number;
	homeTeamPreviousQuarterScore: number;
}