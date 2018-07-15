package com.mattheworth.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
	 * The players in the game for the away team
	 */
	@OneToMany(cascade=CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="away_game_sim_id")
	private List<Player> awayPlayers = new ArrayList<>();
	
	/**
	 * The players in the game for the home team
	 */
	@OneToMany(cascade=CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="home_game_sim_id")
	private List<Player> homePlayers = new ArrayList<>();
	
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
	 * Sets the starters for the home and away team for the simulation
	 */
	public void setStartingPlayers() {
		for (Player player : awayTeam.getPlayers()) {
			awayPlayers.add(player);
		}
		
		for (Player player : homeTeam.getPlayers()) {
			homePlayers.add(player);
		}
	}
	
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
		// Determine who will pass, shoot, and rebound for each team for this possession
		int awayPassPlayers = determinePasser(determineAwayOffense(), (ArrayList<Player>) awayPlayers);
		Player awayPasser = awayPlayers.get(awayPassPlayers);
		Player homePassDefender = homePlayers.get(awayPassPlayers);
		
		int homePassPlayers = determinePasser(determineHomeOffense(), (ArrayList<Player>) homePlayers);
		Player homePasser = homePlayers.get(homePassPlayers);
		Player awayPassDefender = awayPlayers.get(homePassPlayers);
		
		int awayScorePlayers = determineScorer(determineAwayOffense(), (ArrayList<Player>) awayPlayers);
		Player awayShooter = awayPlayers.get(awayScorePlayers);
		Player homeShotDefender = homePlayers.get(awayScorePlayers);
		
		int homeScorePlayers = determineScorer(determineHomeOffense(), (ArrayList<Player>) homePlayers);
		Player homeShooter = homePlayers.get(homeScorePlayers);
		Player awayShotDefender = awayPlayers.get(homeScorePlayers);
		
		int awayReboundPlayers = determineRebounder(determineAwayOffense(), (ArrayList<Player>) awayPlayers);
		Player awayRebounder = awayPlayers.get(awayReboundPlayers);
		Player homeReboundDefender = homePlayers.get(awayReboundPlayers);
		
		int homeReboundPlayers = determineRebounder(determineHomeOffense(), (ArrayList<Player>) homePlayers);
		Player homeRebounder = homePlayers.get(homeReboundPlayers);
		Player awayReboundDefender = awayPlayers.get(homeReboundPlayers);
		
		boolean[] reboundOutcomes = { true, true };
		
		do {
			boolean[] passingOutcomes = determinePassingOutcome(awayPasser, homePassDefender, homePasser, awayPassDefender, reboundOutcomes);
	
			boolean[] scoreOutcomes = determineScoringOutcome(awayShooter, homeShotDefender, homeShooter, awayShotDefender, awayPasser, homePasser, passingOutcomes);
	
			reboundOutcomes = determineReboundingOutcome(awayRebounder, homeReboundDefender, homeRebounder, awayReboundDefender, scoreOutcomes);
		} while (reboundOutcomes[0] | reboundOutcomes[1]);
		
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
		
		for (Player player: awayTeam.getPlayers()) {
			player.resetPlayerGameStats();
		}
		
		for (Player player: homeTeam.getPlayers()) {
			player.resetPlayerGameStats();
		}
		
		awayTeam.resetGameStats();
		homeTeam.resetGameStats();
		
		setStartingPlayers();
	}
	
	/**
	 * Determines the away team's total offensive rating for the players on the court
	 * @return The away team's total offensive rating for the players on the court
	 */
	public int determineAwayOffense() {
		int totalOffense = 0;
		
		for (Player player: awayPlayers) {
			totalOffense += player.getOffensiveRating();
		}
		
		return totalOffense;
	}
	
	/**
	 * Determines the away team's total defensive rating for the players on the court
	 * @return The away team's total defensive rating for the players on the court
	 */
	public int determineAwayDefense() {
		int totalOffense = 0;
		
		for (Player player: awayPlayers) {
			totalOffense += player.getDefensiveRating();
		}
		
		return totalOffense;
	}
	
	/**
	 * Determines the home team's total offensive rating for the players on the court
	 * @return The home team's total offensive rating for the players on the court
	 */
	public int determineHomeOffense() {
		int totalOffense = 0;
		
		for (Player player: homePlayers) {
			totalOffense += player.getOffensiveRating();
		}
		
		return totalOffense;
	}
	
	/**
	 * Determines the home team's total defensive rating for the players on the court
	 * @return The home team's total defensive rating for the players on the court
	 */
	public int determineHomeDefense() {
		int totalOffense = 0;
		
		for (Player player: homePlayers) {
			totalOffense += player.getDefensiveRating();
		}
		
		return totalOffense;
	}
	
	/**
	 * Determines the player who passes the ball and defends
	 * @param playerOffenseTotal The total offensive rating for the team with ball
	 * @return The integer of the player who passes the ball
	 */
	public int determinePasser(int playerOffenseTotal, ArrayList<Player> players) {
		Random rand = new Random();
		double passSelector = rand.nextDouble();
		
		double runningPassTotal = 0;
		
		for (int i = 0; i < players.size(); i++) {
			runningPassTotal += players.get(i).getOffensiveRating() / (double) playerOffenseTotal;
			
			if (passSelector < runningPassTotal) {
				return i;
			}
		}
		
		return 0;
	}
	
	/**
	 * Determines the player shoots the ball and defends
	 * @param playerOffenseTotal The total offensive rating for the team with ball
	 * @return The integer of the player who shoots the ball
	 */
	public int determineScorer(int playerOffenseTotal, ArrayList<Player> players) {
		Random rand = new Random();
		double passSelector = rand.nextDouble();
		
		double runningPassTotal = 0;
		
		for (int i = 0; i < players.size(); i++) {
			runningPassTotal += players.get(i).getOffensiveRating() / (double) playerOffenseTotal;
			
			if (passSelector < runningPassTotal) {
				return i;
			}
		}
		
		return 0;
	}
	
	/**
	 * Determines the player rebounds the ball and defends
	 * @param playerOffenseTotal The total offensive rating for the team with ball
	 * @return The integer of the player who rebounds the ball
	 */
	public int determineRebounder(int playerOffenseTotal, ArrayList<Player> players) {
		Random rand = new Random();
		double passSelector = rand.nextDouble();
		
		double runningPassTotal = 0;
		
		for (int i = 0; i < players.size(); i++) {
			runningPassTotal += players.get(i).getOffensiveRating() / (double) playerOffenseTotal;
			
			if (passSelector < runningPassTotal) {
				return i;
			}
		}
		
		return 0;
	}
	
	/**
	 * Determines the outcome of the pass
	 * @param awayOffense The away team's passer
	 * @param homeDefender The home team's defender
 	 * @param homeOffense The home team's passer
	 * @param awayDefender The away team's defender
	 * @return The pass outcomes for each team
	 */
	public boolean[] determinePassingOutcome(Player awayOffense, Player homeDefender, Player homeOffense, Player awayDefender, boolean[] reboundOutcomes) {
		Random rand = new Random();
		
		boolean passesOutcome[] = { false, false };

		// Determine the pass result for the away team
		if (reboundOutcomes[0]) {
			int awayTeamRandNum = rand.nextInt(100);

			int awayTeamOffense = awayOffense.getOffensiveRating() - homeDefender.getDefensiveRating();

			boolean awayTeamPassSuccess = determinePassIncrease(awayTeamRandNum, 500 + awayTeamOffense);
			
			if (awayTeamPassSuccess) {
				passesOutcome[0] = true;
			} else {
				passesOutcome[0] = false;
				homeDefender.setStealsPerGame(homeDefender.getAssistsPerGame() + 1);
				this.homeTeam.setStealsPerGame(this.homeTeam.getStealsPerGame() + 1);
				awayOffense.setTurnoversPerGame(awayOffense.getTurnoversPerGame() + 1);
				this.awayTeam.setTurnoversPerGame(this.awayTeam.getTurnoversPerGame() + 1);
			}
		}

		// Determine the pass result for the home team
		if (reboundOutcomes[1]) {
			int homeTeamRandNum = rand.nextInt(100);

			int homeTeamOffense = homeOffense.getOffensiveRating() - awayDefender.getDefensiveRating();

			boolean homeTeamPassSuccess = determinePassIncrease(homeTeamRandNum, 500 + homeTeamOffense);
			
			if (homeTeamPassSuccess) {
				passesOutcome[1] = true;
			} else {
				passesOutcome[1] = false;
				awayDefender.setStealsPerGame(awayDefender.getAssistsPerGame() + 1);
				this.awayTeam.setStealsPerGame(this.awayTeam.getStealsPerGame() + 1);
				homeOffense.setTurnoversPerGame(homeOffense.getTurnoversPerGame() + 1);
				this.homeTeam.setTurnoversPerGame(this.homeTeam.getTurnoversPerGame() + 1);
			}
		}
		
		return passesOutcome;
	}
	
	/**
	 * Determines the outcome of the pass for the current possessions
	 * @param randNum The random number associated with that team
	 * @param teamRatio The ratio of the team's offensive to defensive rating
	 * @return Whether or not the team with the ball successfully completes the pass
	 */
	public boolean determinePassIncrease(int randNum, int teamRatio) {
		int teamScoreDecision = randNum * teamRatio;

		if (teamScoreDecision > 5000) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Determines how much each team will score for the given possession
	 * @param awayScorer The away team's shooter
	 * @param homeDefender The home team's defender
	 * @param homeScorer The home team's shooter
	 * @param awayDefender The away team's defender
	 * @param awayPasser The away team's passer
	 * @param homePasser The home team's passer
	 * @return Whether or not either team scored
	 */
	public boolean[] determineScoringOutcome(Player awayScorer, Player homeDefender, Player homeScorer, Player awayDefender, Player awayPasser, Player homePasser, boolean[] passOutcomes) {
		Random rand = new Random();
		
		boolean[] scoreOutcomes = { false, false };

		// Determine the score increase for the away team
		if (passOutcomes[0]) {
			int awayTeamScoreIncrease = 0;
			
			int awayTeamRandNum = rand.nextInt(100);

			int awayTeamOffense = awayScorer.getOffensiveRating() - homeDefender.getDefensiveRating();

			awayTeamScoreIncrease = determineScoreIncrease(awayTeamRandNum, 500 + awayTeamOffense, awayScorer, homeDefender, awayTeam, homeTeam);

			awayScorer.setPointsPerGame(awayScorer.getPointsPerGame() + awayTeamScoreIncrease);
			this.awayTeam.setPointsPerGame(this.awayTeam.getPointsPerGame() + 1);
			awayTeamScore += awayTeamScoreIncrease;
			
			if (awayTeamScoreIncrease > 0) {
				scoreOutcomes[0] = true;
				if (!awayScorer.equals(awayPasser)) {
					awayPasser.setAssistsPerGame(awayPasser.getAssistsPerGame() + 1);
					this.awayTeam.setAssistsPerGame(this.awayTeam.getAssistsPerGame() + 1);
				}
			}
		}

		// Determine the score increase for the home team
		if (passOutcomes[1]) {
			int homeTeamScoreIncrease = 0;
			
			int homeTeamRandNum = rand.nextInt(100);
			
			int homeTeamOffense = homeScorer.getOffensiveRating() - awayDefender.getDefensiveRating();

			homeTeamScoreIncrease = determineScoreIncrease(homeTeamRandNum, 500 + homeTeamOffense, homeScorer, awayDefender, homeTeam, awayTeam);

			this.homeTeam.setPointsPerGame(this.homeTeam.getPointsPerGame() + 1);
			homeScorer.setPointsPerGame(homeScorer.getPointsPerGame() + homeTeamScoreIncrease);
			homeTeamScore += homeTeamScoreIncrease;
			
			if (homeTeamScoreIncrease > 0) {
				scoreOutcomes[1] = true;
				if (!homeScorer.equals(homePasser)) {
					homePasser.setAssistsPerGame(homePasser.getAssistsPerGame() + 1);
					this.homeTeam.setAssistsPerGame(this.homeTeam.getAssistsPerGame() + 1);
				}
			}
		}
		
		return scoreOutcomes;
	}
	
	/**
	 * Determines how much both teams will score for the given possession
	 * @param randNum The random team numbers
	 * @param teamRatio The ratio of the offensive team to the defensive team
	 * @param offensePlayer The shooter
	 * @param defensePlayer The defender
	 * @param offenseTeam The offensive team
	 * @param defenseTeam The defensive team
	 * @return
	 */
	public int determineScoreIncrease(int randNum, int teamRatio, Player offensePlayer, Player defensePlayer, Team offenseTeam, Team defenseTeam) {
		int teamScoreIncrease;

		int teamScoreDecision = randNum * teamRatio;

		if (teamScoreDecision > 45000) {
			teamScoreIncrease = 3;
		} else if (teamScoreDecision > 32500) {
			teamScoreIncrease = 2;
		} else if (teamScoreDecision > 25000) {
			teamScoreIncrease = 1;
		} else if (teamScoreDecision > 5000) {
			teamScoreIncrease = 0;
		} else {
			teamScoreIncrease = 0;
			defensePlayer.setBlocksPerGame(defensePlayer.getBlocksPerGame() + 1);
			defenseTeam.setBlocksPerGame(defenseTeam.getBlocksPerGame() + 1);
		}

		return teamScoreIncrease;
	}
	
	/**
	 * Determines the rebounding outcome for the current possession
	 * @param awayOffense The away team's offensive player
	 * @param homeDefender The home team's defender
	 * @param homeOffense The home team's offensive player
	 * @param awayDefender The away team's defender
	 * @return The rebound outcome for each team
	 */
	public boolean[] determineReboundingOutcome(Player awayOffense, Player homeDefender, Player homeOffense, Player awayDefender, boolean[] shotOutcomes) {
		Random rand = new Random();
		
		boolean reboundsOutcome[] = { false, false };

		// Determine the rebound result for the away team
		if (!shotOutcomes[0]) {
			int awayTeamRandNum = rand.nextInt(100);

			int awayTeamOffense = awayOffense.getOffensiveRating() - homeDefender.getDefensiveRating();

			boolean awayTeamReboundSuccess = determineReboundIncrease(awayTeamRandNum, 500 + awayTeamOffense);
			
			if (awayTeamReboundSuccess) {
				reboundsOutcome[0] = true;
				awayOffense.setReboundsPerGame(awayOffense.getReboundsPerGame() + 1);
				this.awayTeam.setReboundsPerGame(this.awayTeam.getReboundsPerGame() + 1);
			} else {
				homeDefender.setReboundsPerGame(homeDefender.getReboundsPerGame() + 1);
				this.homeTeam.setReboundsPerGame(this.homeTeam.getReboundsPerGame() + 1);
			}
		}

		// Determine the rebound result for the home team
		if (!shotOutcomes[1]) {
			int homeTeamRandNum = rand.nextInt(100);
			
			int homeTeamOffense = homeOffense.getOffensiveRating() - awayDefender.getDefensiveRating();

			boolean homeTeamReboundSuccess = determineReboundIncrease(homeTeamRandNum, 500 + homeTeamOffense);
			
			if (homeTeamReboundSuccess) {
				reboundsOutcome[1] = true;
				homeOffense.setReboundsPerGame(homeOffense.getReboundsPerGame() + 1);
				this.homeTeam.setReboundsPerGame(this.homeTeam.getReboundsPerGame() + 1);
			} else {
				awayDefender.setReboundsPerGame(awayDefender.getReboundsPerGame() + 1);
				this.awayTeam.setReboundsPerGame(this.awayTeam.getReboundsPerGame() + 1);
			}
		}
		
		return reboundsOutcome;
	}
	
	/**
	 * Determines how much a given team will rebound
	 * @param randNum The random number associated with that team
	 * @param teamRatio The ratio of the team's offensive to defensive rating
	 * @return An indicator for whether or not the offensive team got the rebound
	 */
	public boolean determineReboundIncrease(int randNum, int teamRatio) {
		int teamScoreDecision = randNum * teamRatio;

		if (teamScoreDecision > 40000) {
			return true;
		} else {
			return false;
		}
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

	public List<Player> getAwayPlayers() {
		return awayPlayers;
	}

	public void setAwayPlayers(List<Player> awayPlayers) {
		this.awayPlayers = awayPlayers;
	}

	public List<Player> getHomePlayers() {
		return homePlayers;
	}

	public void setHomePlayers(List<Player> homePlayers) {
		this.homePlayers = homePlayers;
	}

}
