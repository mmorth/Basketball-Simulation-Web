package com.mattheworth.server;

import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GameSimulation {

	/* fields */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name="away_team_id")
	private Team awayTeam;
	
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name="home_team_id")
	private Team homeTeam;
	
	private int possessionsRemaining;
	
	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isOvertime;
	private int awayTeamScore;
	private int homeTeamScore;
	private int awayTeamPreviousQuarterScore;
	private int homeTeamPreviousQuarterScore;

	public GameSimulation() {
		
	}
	
	/* constructor */
	public GameSimulation(Team awayTeam, Team homeTeam) {
		this.awayTeam = awayTeam;
		this.homeTeam = homeTeam;
		this.possessionsRemaining = 0;
		this.isOvertime = false;
		this.awayTeamScore = 0;
		this.homeTeamScore = 0;
		this.awayTeamPreviousQuarterScore = 0;
		this.homeTeamPreviousQuarterScore = 0;
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
		if (!isOvertime && awayTeamScore == homeTeamScore && possessionsRemaining == -1) {
			possessionsRemaining = 9;
			isOvertime = true;
		}

		// Simulate overtime if the score is tied after regulation
		while (possessionsRemaining > -1 && possessions > 0 && isOvertime) {
			possessions = simulatePossession(5, possessions);
		}

		if (awayTeamScore == homeTeamScore && possessionsRemaining == -1 && isOvertime) {
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
			awayTeamPreviousQuarterScore = awayTeamScore;
			homeTeamPreviousQuarterScore = homeTeamScore;
		}

		return possessions;
	}

	public void determineScoringOutcome() {
		Random rand = new Random();
		
		int awayTeamScoreIncrease = 0;
		int homeTeamScoreIncrease = 0;

		// Determine the score increase for the away team
		int awayTeamRandNum = rand.nextInt(100);

		int awayTeamOffense = awayTeam.getOffensiveRating() - homeTeam.getDefensiveRating();
		int homeTeamOffense = homeTeam.getOffensiveRating() - awayTeam.getDefensiveRating();

		awayTeamScoreIncrease = determineScoreIncrease(awayTeamRandNum, 500 + awayTeamOffense);

		awayTeamScore += awayTeamScoreIncrease;

		// Determine the score increase for the home team
		int homeTeamRandNum = rand.nextInt(100);

		homeTeamScoreIncrease = determineScoreIncrease(homeTeamRandNum, 500 + homeTeamOffense);

		homeTeamScore += homeTeamScoreIncrease;
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

		this.awayTeamScore = 0;
		this.homeTeamScore = 0;

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

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
