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

/**
 * A class that represents a basketball simulation
 * @author mmorth
 *
 */
@Entity
public class GameSimulation {

	// ============================= Fields ======================================== //
	
	/**
	 * The primary key
	 */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	/**
	 * The away team
	 */
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name="away_team_id")
	private Team awayTeam;
	
	/**
	 * The home team
	 */
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name="home_team_id")
	private Team homeTeam;
	
	/**
	 * The number of possession remaining
	 */
	private int possessionsRemaining;
	
	/**
	 * Stores whether or not the simulation is in overtime
	 */
	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isOvertime;
	
	/**
	 * The score of the away team
	 */
	private int awayTeamScore;
	
	/**
	 * The score of the home team
	 */
	private int homeTeamScore;
	
	/**
	 * The away team's first quarter score
	 */
	private int awayTeamFirstQuarterScore;
	
	/**
	 * The away team's second quarter score
	 */
	private int awayTeamSecondQuarterScore;
	
	/**
	 * The away team's third quarter score
	 */
	private int awayTeamThirdQuarterScore;
	
	/**
	 * The away team's fourth quarter score
	 */
	private int awayTeamFourthQuarterScore;
	
	/**
	 * The away team's overtime score
	 */
	private int awayTeamOvertimeScore;
	
	/**
	 * The home team's first quarter score
	 */
	private int homeTeamFirstQuarterScore;
	
	/**
	 * The home team's second quarter score
	 */
	private int homeTeamSecondQuarterScore;
	
	/*
	 * The home team's third quarter score
	 */
	private int homeTeamThirdQuarterScore;
	
	/**
	 * The home team's fourth quarter score
	 */
	private int homeTeamFourthQuarterScore;
	
	/**
	 * The home team's overtime score
	 */
	private int homeTeamOvertimeScore;

	// ======================================== Constructors =================================== //
	
	/**
	 * Constructs a new default GameSimulation object
	 */
	public GameSimulation() {
		
	}
	
	/**
	 * Constructs a new GameSimulation with the two given teams
	 * @param awayTeam The away team 
	 * @param homeTeam The home team
	 */
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

	// ========================================= Logic Methods ========================== //
	
	/**
	 * Simulations the given number of possessions
	 * @param possessions The number of possessions to simulate
	 */
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

	/**
	 * Simulates the given number of possessions and calculates the points scored for each quarter for each team
	 * @param quarter The quarter the simulation is in
	 * @param possessions the number of possessions to simlate
	 * @return The number of possessions remaining
	 */
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

	/**
	 * Determines how much each team will score for the current possession
	 */
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

	/**
	 * Determines how much a given team will score
	 * @param randNum The random number associated with that team
	 * @param teamRatio The ratio of the team's offensive to defensive rating
	 * @return The score increase for the team
	 */
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

	/**
	 * Resets the game simulation
	 */
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

	// ====================================== Getters and Setters ============================ //
	
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
