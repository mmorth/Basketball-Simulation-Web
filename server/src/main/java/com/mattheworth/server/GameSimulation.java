package com.mattheworth.server;

import java.util.Random;

public class GameSimulation {

	/* fields */
	private Team awayTeam;
	private Team homeTeam;
	private int possessionsRemaining;
	private boolean isOvertime;
	private int awayTeamPreviousQuarterScore;
	private int homeTeamPreviousQuarterScore;
	private Random rand;

	/* constructor */
	public GameSimulation(Team awayTeam, Team homeTeam) {
		this.awayTeam = awayTeam;
		this.homeTeam = homeTeam;
		this.possessionsRemaining = 0;
		this.isOvertime = false;
		this.awayTeamPreviousQuarterScore = 0;
		this.homeTeamPreviousQuarterScore = 0;
		this.rand = new Random();
		
		awayTeam.setScore(0);
		homeTeam.setScore(0);
	}

	/* logic methods */
	public void simulationPossession(int possessions) {
		// Simulate the first quarter if it is not over yet
		while (possessionsRemaining > 74 && possessions > 0) {
			possessions = simulatePossession(1, possessions);
		}

		// Simulate the second quarter if it is not over yet
		while (possessionsRemaining > 49 && possessions > 0) {
			possessions = simulatePossession(2, possessions);
		}

		// Simulate the third quarter if it is not over yet
		while (possessionsRemaining > 24 && possessions > 0) {
			possessions = simulatePossession(3, possessions);
		}

		// Simulate the fourth quarter if it is not over yet
		while (possessionsRemaining > -1 && possessions > 0 && !isOvertime) {
			possessions = simulatePossession(4, possessions);
		}

		// Check if game is tied after regulation
		if (!isOvertime && awayTeam.getScore() == homeTeam.getScore() && possessionsRemaining == -1) {
			possessionsRemaining = 9;
			isOvertime = true;
		}

		// Simulate overtime if the score is tied after regulation
		while (possessionsRemaining > -1 && possessions > 0 && isOvertime) {
			possessions = simulatePossession(5, possessions);
		}

		if (awayTeam.getScore() == homeTeam.getScore() && possessionsRemaining == -1 && isOvertime) {
			possessionsRemaining = 9;
			isOvertime = true;
		}

	}

	public int simulatePossession(int quarter, int possessions) {
		// Set the possessionsRemaining condition and table columns based on what
		// quarter is being simulated
		int possessionsRemainingCondition = 0;

		if (quarter == 1) {
			possessionsRemainingCondition = 74;
		} else if (quarter == 2) {
			possessionsRemainingCondition = 49;
		} else if (quarter == 3) {
			possessionsRemainingCondition = 24;
		} else if (quarter == 4) {
			possessionsRemainingCondition = -1;
		} else if (isOvertime) {
			quarter = 5;
			possessionsRemainingCondition = -2; // The overtime scores should not reset after each overtime
		}

		// Determine the points scored for each team
		determineScoringOutcome();

		// Decrease the number of possessions to simulate and possessions remaining in
		// the game
		possessionsRemaining--;
		possessions--;

		// Store the previous quarter scores if the quarter just ended
		// Used to calculate the scores for following quarters
		if (possessionsRemaining == possessionsRemainingCondition) {
			awayTeamPreviousQuarterScore = awayTeam.getScore();
			homeTeamPreviousQuarterScore = homeTeam.getScore();
		}

		return possessions;
	}

	public void determineScoringOutcome() {
		int awayTeamScoreIncrease = 0;
		int homeTeamScoreIncrease = 0;

		// Determine the score increase for the away team
		int awayTeamRandNum = rand.nextInt(100);

		int awayTeamOffense = awayTeam.getOffensiveRating() - homeTeam.getDefensiveRating();
		int homeTeamOffense = homeTeam.getOffensiveRating() - awayTeam.getDefensiveRating();

		awayTeamScoreIncrease = determineScoreIncrease(awayTeamRandNum, 500 + awayTeamOffense);

		awayTeam.setScore(awayTeam.getScore() + awayTeamScoreIncrease);

		// Determine the score increase for the home team
		int homeTeamRandNum = rand.nextInt(100);

		homeTeamScoreIncrease = determineScoreIncrease(homeTeamRandNum, 500 + homeTeamOffense);

		homeTeam.setScore(homeTeam.getScore() + homeTeamScoreIncrease);
	}

	public int determineScoreIncrease(int randNum, int teamRatio) {
		int teamScoreIncrease;

		int teamScoreDecision = randNum * teamRatio;

		if (teamScoreDecision > 45000) {
			teamScoreIncrease = 3;
		} else if (teamScoreDecision > 32500) {
			teamScoreIncrease = 2;
		} else if (teamScoreDecision > 25000) {
			teamScoreIncrease = 1;
		} else {
			teamScoreIncrease = 0;
		}

		return teamScoreIncrease;
	}

	public void resetGame() {
		// Update the possession and score information
		possessionsRemaining = 99;

		awayTeamPreviousQuarterScore = 0;
		homeTeamPreviousQuarterScore = 0;

		awayTeam.setScore(0);
		homeTeam.setScore(0);

		isOvertime = false;

	}

	/* getters and setters */
	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public int getPossessionsRemaining() {
		return possessionsRemaining;
	}

	public void setPossessionsRemaining(int possessionsRemaining) {
		this.possessionsRemaining = possessionsRemaining;
	}

	public boolean isOvertime() {
		return isOvertime;
	}

	public void setOvertime(boolean isOvertime) {
		this.isOvertime = isOvertime;
	}

	public int getAwayTeamPreviousQuarterScore() {
		return awayTeamPreviousQuarterScore;
	}

	public void setAwayTeamPreviousQuarterScore(int awayTeamPreviousQuarterScore) {
		this.awayTeamPreviousQuarterScore = awayTeamPreviousQuarterScore;
	}

	public int getHomeTeamPreviousQuarterScore() {
		return homeTeamPreviousQuarterScore;
	}

	public void setHomeTeamPreviousQuarterScore(int homeTeamPreviousQuarterScore) {
		this.homeTeamPreviousQuarterScore = homeTeamPreviousQuarterScore;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

}
