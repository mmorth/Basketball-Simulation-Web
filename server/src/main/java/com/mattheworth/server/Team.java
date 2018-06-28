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
import javax.persistence.OneToMany;

@Entity
public class Team {
	/* fields */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)
	private String name;
	private int offensiveRating;
	private int defensiveRating;
	
	public Team() {
		
	}

	/* getters and setters */
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

}