package com.fw.search.database.model;

import java.util.List;

public class Movie {
	private String name;
	private String director;
	private int rank;
	private List<String> stars;
	
	public Movie(String name, String director, List<String> stars, int rank) {
		this.name = name;
		this.director = director;
		this.rank = rank;
		this.stars = stars;
	}

	public List<String> getStars() {
		return stars;
	}

	public String getName() {
		return name;
	}

	public String getDirector() {
		return director;
	}

	public int getRank() {
		return rank;
	}
}