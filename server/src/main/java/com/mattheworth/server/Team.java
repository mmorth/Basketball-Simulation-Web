package com.mattheworth.server;

public class Team {

	/* fields */
	private final long id;
	private String name;
	private int offensiveRating;
	private int defensiveRating;
	private int score;

	/* constructor */
	public Team(long id, String name, int offensiveRating, int defensiveRating) {
		this.id = id;
		this.name = name;
		this.offensiveRating = offensiveRating;
		this.defensiveRating = defensiveRating;
		this.score = 0;
	}

	/* getters and setters */
	public String getName() {
		return name;
	}

	public long getId() {
		return id;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}