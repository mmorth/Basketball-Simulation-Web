package com.mattheworth.server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames="NAME"))
public class Team {

	/* fields */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private int offensiveRating;
	private int defensiveRating;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private GameSimulation gameSimulation;

	/* constructor */
//	public Team(String name, int offensiveRating, int defensiveRating) {
//		this.name = name;
//		this.offensiveRating = offensiveRating;
//		this.defensiveRating = defensiveRating;
//	}

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