package com.mattheworth.server;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

/**
 * A class that represents a Team object
 * @author mmorth
 *
 */
@Entity
public class Team {

	// ======================================= Fields ================================= //
	
	/**
	 * The primary key
	 */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	/**
	 * The team name
	 */
	@Column(unique = true)	
	private String name;
	
	/**
	 * The team's overall rating
	 */
	private int overallRating;
	
	/**
	 * The team's offensive rating
	 */
	private int offensiveRating;
	
	/**
	 * The team's defensive rating
	 */
	private int defensiveRating;
	
	/**
	 * The list of players on the team
	 */
	@OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name="team_id")
	private List<Player> players = new ArrayList<>();
	
	/**
	 * The coach of the team
	 */
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name="coach_id")
	private Player coach;
	
	// The team's game stats
	
	/**
	 * The player's points per game
	 */
	private int pointsPerGame;
	
	/**
	 * The player's rebounds per game
	 */
	private int reboundsPerGame;
	
	/**
	 * The player's assists per game
	 */
	private int assistsPerGame;
	
	/**
	 * The player's block per game
	 */
	private int blocksPerGame;
	
	/**
	 * The player's steals per game
	 */
	private int stealsPerGame;
	
	/**
	 * The player's turnovers per game
	 */
	private int turnoversPerGame;
	
	// ===================================== Constructor ============================ //
	
	/**
	 * Constructs a new default team
	 */
	public Team() {
		
	}

	// ====================================== Getters and Setters ============================= //
	
	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOffensiveRating() {
		return offensiveRating;
	}

	public void setOffensiveRating() {
		this.offensiveRating = 0;
		
		for (int i = 0; i < players.size(); i++) {
			this.offensiveRating += players.get(i).getOffensiveRating();
		}
		
		if (this.players.size() != 0) {
			this.offensiveRating /= players.size();
		}
		
		if (!(this.coach == null)) {
			this.offensiveRating *= (1 + (this.coach.getOffensiveRating()/1000.0));
		}
	}

	public int getDefensiveRating() {
		return defensiveRating;
	}

	public void setDefensiveRating() {
		this.defensiveRating = 0;
		
		for (int i = 0; i < players.size(); i++) {
			this.defensiveRating += players.get(i).getDefensiveRating();
		}
		
		if (this.players.size() != 0) {
			this.defensiveRating /= players.size();
		}
		
		if (!(this.coach == null)) {
			this.defensiveRating *= (1 + (this.coach.getDefensiveRating()/1000.0));
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
		this.setOffensiveRating();
		this.setDefensiveRating();
		this.setOverallRating();
	}
	
	public Player getCoach() {
		return coach;
	}

	public void setCoach(Player coach) {
		this.coach = coach;
		this.setOffensiveRating();
		this.setDefensiveRating();
		this.setOverallRating();
	}

	public int getOverallRating() {
		return overallRating;
	}

	public void setOverallRating() {
		this.overallRating =  (this.getOffensiveRating() + this.getDefensiveRating()) / 2;
	}

	public int getPointsPerGame() {
		return pointsPerGame;
	}

	public void setPointsPerGame(int pointsPerGame) {
		this.pointsPerGame = pointsPerGame;
	}

	public int getReboundsPerGame() {
		return reboundsPerGame;
	}

	public void setReboundsPerGame(int reboundsPerGame) {
		this.reboundsPerGame = reboundsPerGame;
	}

	public int getAssistsPerGame() {
		return assistsPerGame;
	}

	public void setAssistsPerGame(int assistsPerGame) {
		this.assistsPerGame = assistsPerGame;
	}

	public int getBlocksPerGame() {
		return blocksPerGame;
	}

	public void setBlocksPerGame(int blocksPerGame) {
		this.blocksPerGame = blocksPerGame;
	}

	public int getStealsPerGame() {
		return stealsPerGame;
	}

	public void setStealsPerGame(int stealsPerGame) {
		this.stealsPerGame = stealsPerGame;
	}

	public int getTurnoversPerGame() {
		return turnoversPerGame;
	}

	public void setTurnoversPerGame(int turnoversPerGame) {
		this.turnoversPerGame = turnoversPerGame;
	}

	public void setOffensiveRating(int offensiveRating) {
		this.offensiveRating = offensiveRating;
	}

	public void setDefensiveRating(int defensiveRating) {
		this.defensiveRating = defensiveRating;
	}
	
	// ======================================== Logic Methods =============================================== //

	/**
	 * Adds a player to the team
	 * @param player The player
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
		this.setOffensiveRating();
		this.setDefensiveRating();
		this.setOverallRating();
	}
	
	/**
	 * Removes a player from the team
	 * @param player The player
	 */
	public void removePlayer(Player player) {
		this.players.remove(player);
		this.setOffensiveRating();
		this.setDefensiveRating();
		this.setOverallRating();
	}
	
	/**
	 * Resets the player's game stats
	 */
	public void resetGameStats() {
		this.assistsPerGame = 0;
		this.pointsPerGame = 0;
		this.turnoversPerGame = 0;
		this.reboundsPerGame = 0;
		this.stealsPerGame = 0;
		this.blocksPerGame = 0;
	}
	
	/**
	 * Sorts the team's players by their player role and position played
	 */
	public void sortPlayers() {
		Collections.sort(this.players, new SortPlayers());
	}
	
	/**
	 * Sorts the team's players by their player role and position played
	 */
	public void sortPlayersRotation() {
//		System.out.println("\nPre-Sorted:");
//		for (int i = 0; i < players.size(); i++) {
//			System.out.println(players.get(i).getName() + ": " + players.get(i).getPossessionsRemaining() + ": " + players.get(i).getStamina());
//		}
		
		Collections.sort(this.players, new SortPlayersRotation());
//		System.out.println("\nPost-Sorted:");
//		for (int i = 0; i < players.size(); i++) {
//			System.out.println(players.get(i).getName() + ": " + players.get(i).getPossessionsRemaining() + ": " + players.get(i).getStamina());
//		}
	}
	
	/**
	 * Sorts the team's players by their player role and position played
	 */
	public void sortPlayersPosition() {
		Collections.sort(this.players, new SortPlayersPosition());
	}
	
	/**
	 * Determines whether the new player role is valid (checks that there are no more than 5 starters(
	 * @param playerID The id of the new player
	 * @param playerRole The role of the new player
	 * @return Whether the new player role is valid
	 */
	public boolean validPlayerRole(long playerID, String playerRole) {
		int numStarters = 0;
		
		for (Player player: this.players) {
			if (playerID != player.getId() && playerRole.equals("Starter") && player.getRole().equals("Starter")) {
				numStarters++;
			}
			
		}
		
		return numStarters < 5;
	}
	
	/**
	 * Changes the game position of players
	 * @param playerID The id of the player to change the position of
	 * @param playerRole The role of the player
	 * @param newPosition The new game position
	 * @param oldPosition The old game position
	 */
	public void changeGamePosition(long playerID, String playerRole, int newPosition, int oldPosition) {
		for (Player player: this.players) {
			if (playerID != player.getId() && playerRole.equals("Starter") && player.getRole().equals("Starter")) {
				if (newPosition == player.getPositionPlay()) {
					player.setPositionPlay(oldPosition);
				}
			}
			
		}
		
	}
	
	/**
	 * Determines the default game position for the new starter
	 * @param playerID The id of the player to find the position of
	 * @param playerRole The role of the new player
	 * @return The position the player will play
	 */
	public int determineDefaultGamePosition(long playerID, String playerRole) {
		boolean positionsFilled[] = { false, false, false, false, false, false };
		
		for (int i = 0; i < players.size(); i++) {
			if (playerID != players.get(i).getId() && playerRole.equals("Starter") && players.get(i).getRole().equals("Starter")) {
				positionsFilled[players.get(i).getPositionPlay()] = true;
			}
			
		}
		
		for (int i = 0; i < positionsFilled.length; i++) {
			if (positionsFilled[i] == false && i != 0) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Sets the game positions of the starters
	 */
	public void setStartersPositions() {
		int numPlayers = this.players.size();
		int loop = Math.min(5, numPlayers);
		
		for (int i = 0; i < loop; i++) {
			players.get(i).setPositionPlay(i+1);
		}
	}

}

/**
 * Sorts the players by role and position
 * @author mmorth
 *
 */
class SortPlayers implements Comparator<Player>{

	@Override
	public int compare(Player player1, Player player2) {
		int player1NumPlayerRole = player1.numericPlayerRole(player1.getRole());
		int player2NumPlayerRole = player2.numericPlayerRole(player2.getRole());
		
		if (player1NumPlayerRole < player2NumPlayerRole || (player1NumPlayerRole == player2NumPlayerRole && player1.getPositionPlay() < player2.getPositionPlay())) {
			return -1;
		} else {
			return 1;
		}
	}
	
}

/**
 * Sorts the players by rotation minutes
 * @author mmorth
 *
 */
class SortPlayersRotation implements Comparator<Player>{

	@Override
	public int compare(Player player1, Player player2) {
		if (player1.getPossessionsRemaining() > player2.getPossessionsRemaining()) {
			return -1;
		} else {
			return 1;
		}
	}
	
}

/**
 * Sorts the players by their position
 * @author mmorth
 *
 */
class SortPlayersPosition implements Comparator<Player>{

	@Override
	public int compare(Player player1, Player player2) {
		if (player1.getPosition() < player2.getPosition()) {
			return -1;
		} else {
			return 1;
		}
	}
	
}
