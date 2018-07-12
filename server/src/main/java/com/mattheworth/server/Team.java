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
//	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
//	@JoinColumn(name="coach_id")
//	private Player coach;
	
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
		
		if (players.size() != 0) {
			this.offensiveRating /= players.size();
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
		
		if (players.size() != 0) {
			this.defensiveRating /= players.size();
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