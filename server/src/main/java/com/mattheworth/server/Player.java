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
	
	/**
	 * The offensive rating of the player
	 */
	private int offensiveRating;
	
	/**
	 * The defensive rating of theplayer
	 */
	private int defensiveRating;
	
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
	
}
