package com.mattheworth.server;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.ArrayList;
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
	}
	
	public Player getCoach() {
		return coach;
	}

	public void setCoach(Player coach) {
		this.coach = coach;
		this.setOffensiveRating();
		this.setDefensiveRating();
	}

	public int getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(int overallRating) {
		this.overallRating = overallRating;
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

	public void addPlayer(Player player) {
		this.players.add(player);
		this.setOffensiveRating();
		this.setDefensiveRating();
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player);
		this.setOffensiveRating();
		this.setDefensiveRating();
	}

}