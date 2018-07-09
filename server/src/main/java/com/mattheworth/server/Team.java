package com.mattheworth.server;

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
	 * The team's offensive rating
	 */
	private int offensiveRating;
	
	/**
	 * The team's defensive rating
	 */
	private int defensiveRating;
	
	/**
	 * The home team
	 */
	@OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name="team_id")
	private List<Player> players = new ArrayList<>();
	
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

	public void setOffensiveRating(int offensiveRating) {
		this.offensiveRating = offensiveRating;
	}

	public int getDefensiveRating() {
		return defensiveRating;
	}

	public void setDefensiveRating(int defensiveRating) {
		this.defensiveRating = defensiveRating;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}