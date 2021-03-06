package com.mattheworth.server;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

	// ======================================= Fields ================================= //
	
	/**
	 * The primary key
	 */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	/**
	 * The player's name
	 */
	private String name;
	
	// General Ratings
	
	/**
	 * The overall rating of the player
	 */
	private int overallRating;
	
	/**
	 * The offensive rating of the player
	 */
	private int offensiveRating;
	
	/**
	 * The defensive rating of the player
	 */
	private int defensiveRating;
	
	/**
	 * The position the player player
	 */
	private int position;
	
	/**
	 * The position the player is currently playing
	 */
	private int positionPlay;
	
	/**
	 * The player's role on the team
	 */
	private String role;
	
	// Game Stats
	
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
	
	/**
	 * The number of possessions remaining in the game for the player
	 */
	private int possessionsRemaining;
	
	/**
	 * The player's rotation minutes
	 */
	private int rotationMinutes;
	
	/**
	 * The player's stamina
	 */
	private int stamina;
	
	// ================================== Constructor========================================= //
	
	public Player() {

	}
	
	// ================================== Getters / Setters ================================ //

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOffensiveRating() {
		return offensiveRating;
	}

	public void setOffensiveRating(int offensiveRating) {
		this.offensiveRating = offensiveRating;
		setOverallRating();
	}

	public int getDefensiveRating() {
		return defensiveRating;
	}

	public void setDefensiveRating(int defensiveRating) {
		this.defensiveRating = defensiveRating;
		setOverallRating();
	}

	public int getOverallRating() {
		return overallRating;
	}

	public void setOverallRating() {
		this.overallRating = (this.offensiveRating + this.defensiveRating) / 2;
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

	public int getPossessionsRemaining() {
		return possessionsRemaining;
	}

	public void setPossessionsRemaining(int possessionsRemaining) {
		this.possessionsRemaining = possessionsRemaining;
	}

	public int getRotationMinutes() {
		return rotationMinutes;
	}

	public void setRotationMinutes(int rotationMinutes) {
		this.rotationMinutes = rotationMinutes;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	
	public int getPositionPlay() {
		return positionPlay;
	}

	public void setPositionPlay(int positionPlay) {
		this.positionPlay = positionPlay;
	}
	
	// ================================================= Logic Methods ==========================================

	/**
	 * Resets the player's game stats
	 */
	public void resetPlayerGameStats() {
		this.assistsPerGame = 0;
		this.pointsPerGame = 0;
		this.turnoversPerGame = 0;
		this.reboundsPerGame = 0;
		this.stealsPerGame = 0;
		this.blocksPerGame = 0;
		this.possessionsRemaining = this.rotationMinutes;
		this.stamina = 100;
	}
	
	/**
	 * Maps a numeric value to the player role for the comparator sorting
	 * @param playerRole The role of the player
	 * @return Returns 1 for Starter, 2 for Sixth Man, 3 for Role Player, 4 for Prospect, and 5 for Bench Warmer
	 */
	public int numericPlayerRole(String playerRole) {
		if (playerRole.equals("Starter")) {
			return 1;
		} else if (playerRole.equals("Sixth Man")) {
			return 2;
		}  else if (playerRole.equals("Role Player")) {
			return 3;
		} else if (playerRole.equals("Prospect")) {
			return 4;
		} else {
			return 5;
		}
	}
	
}
