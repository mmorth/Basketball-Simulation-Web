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
	
	private int awayTeamFirstQuarterScore;
	private int awayTeamSecondQuarterScore;
	private int awayTeamThirdQuarterScore;
	private int awayTeamFourthQuarterScore;
	private int awayTeamOvertimeScore;
	
	private int homeTeamFirstQuarterScore;
	private int homeTeamSecondQuarterScore;
	private int homeTeamThirdQuarterScore;
	private int homeTeamFourthQuarterScore;
	private int homeTeamOvertimeScore;

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
		// Determine the points scored for each team
		determineScoringOutcome();

		// Decrease the number of possessions to simulate and possessions remaining in
		// the game
		possessionsRemaining--;
		possessions--;
		
		if (quarter == 1) {
			awayTeamFirstQuarterScore = awayTeamScore;
			homeTeamFirstQuarterScore = homeTeamScore;
		} else if (quarter == 2) {
			awayTeamSecondQuarterScore = awayTeamScore - awayTeamFirstQuarterScore;
			homeTeamSecondQuarterScore = homeTeamScore - homeTeamFirstQuarterScore;
		} else if (quarter == 3) {
			awayTeamThirdQuarterScore = awayTeamScore - awayTeamSecondQuarterScore - awayTeamFirstQuarterScore;
			homeTeamThirdQuarterScore = homeTeamScore - homeTeamSecondQuarterScore - homeTeamFirstQuarterScore;
		} else if (quarter == 4) {
			awayTeamFourthQuarterScore = awayTeamScore - awayTeamThirdQuarterScore - awayTeamSecondQuarterScore - awayTeamFirstQuarterScore;
			homeTeamFourthQuarterScore = homeTeamScore - homeTeamThirdQuarterScore - homeTeamSecondQuarterScore - homeTeamFirstQuarterScore;
		} else if (isOvertime) {
			awayTeamOvertimeScore = awayTeamScore - awayTeamFourthQuarterScore - awayTeamThirdQuarterScore - awayTeamSecondQuarterScore - awayTeamFirstQuarterScore;
			homeTeamOvertimeScore = homeTeamScore - homeTeamFourthQuarterScore - homeTeamThirdQuarterScore - homeTeamSecondQuarterScore - homeTeamFirstQuarterScore;
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

	public int getAwayTeamFirstQuarterScore() {
		return awayTeamFirstQuarterScore;
	}

	public void setAwayTeamFirstQuarterScore(int awayTeamFirstQuarterScore) {
		this.awayTeamFirstQuarterScore = awayTeamFirstQuarterScore;
	}

	public int getAwayTeamSecondQuarterScore() {
		return awayTeamSecondQuarterScore;
	}

	public void setAwayTeamSecondQuarterScore(int awayTeamSecondQuarterScore) {
		this.awayTeamSecondQuarterScore = awayTeamSecondQuarterScore;
	}

	public int getAwayTeamThirdQuarterScore() {
		return awayTeamThirdQuarterScore;
	}

	public void setAwayTeamThirdQuarterScore(int awayTeamThirdQuarterScore) {
		this.awayTeamThirdQuarterScore = awayTeamThirdQuarterScore;
	}

	public int getAwayTeamFourthQuarterScore() {
		return awayTeamFourthQuarterScore;
	}

	public void setAwayTeamFourthQuarterScore(int awayTeamFourthQuarterScore) {
		this.awayTeamFourthQuarterScore = awayTeamFourthQuarterScore;
	}

	public int getAwayTeamOvertimeScore() {
		return awayTeamOvertimeScore;
	}

	public void setAwayTeamOvertimeScore(int awayTeamOvertimeScore) {
		this.awayTeamOvertimeScore = awayTeamOvertimeScore;
	}

	public int getHomeTeamFirstQuarterScore() {
		return homeTeamFirstQuarterScore;
	}

	public void setHomeTeamFirstQuarterScore(int homeTeamFirstQuarterScore) {
		this.homeTeamFirstQuarterScore = homeTeamFirstQuarterScore;
	}

	public int getHomeTeamSecondQuarterScore() {
		return homeTeamSecondQuarterScore;
	}

	public void setHomeTeamSecondQuarterScore(int homeTeamSecondQuarterScore) {
		this.homeTeamSecondQuarterScore = homeTeamSecondQuarterScore;
	}

	public int getHomeTeamThirdQuarterScore() {
		return homeTeamThirdQuarterScore;
	}

	public void setHomeTeamThirdQuarterScore(int homeTeamThirdQuarterScore) {
		this.homeTeamThirdQuarterScore = homeTeamThirdQuarterScore;
	}

	public int getHomeTeamFourthQuarterScore() {
		return homeTeamFourthQuarterScore;
	}

	public void setHomeTeamFourthQuarterScore(int homeTeamFourthQuarterScore) {
		this.homeTeamFourthQuarterScore = homeTeamFourthQuarterScore;
	}

	public int getHomeTeamOvertimeScore() {
		return homeTeamOvertimeScore;
	}

	public void setHomeTeamOvertimeScore(int homeTeamOvertimeScore) {
		this.homeTeamOvertimeScore = homeTeamOvertimeScore;
	}

}
