package com.mattheworth.server;

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
	 * The defensive rating of theplayer
	 */
	private int defensiveRating;
	
	/**
	 * The position the player player
	 */
	private int position;
	
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
	private int possessionsPlayed;
	
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
	}

	public int getDefensiveRating() {
		return defensiveRating;
	}

	public void setDefensiveRating(int defensiveRating) {
		this.defensiveRating = defensiveRating;
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

	public int getPossessionsPlayed() {
		return possessionsPlayed;
	}

	public void setPossessionsPlayed(int possessionsPlayed) {
		this.possessionsPlayed = possessionsPlayed;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
}
