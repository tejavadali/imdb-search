package com.fw.search.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fw.search.database.index.Indexer;
import com.fw.search.database.model.Movie;

public class MovieDatabase {
	private static int MAX_SIZE = 1000;
	private static MovieDatabase database;
	private List<Movie> movies;
	private Indexer indexer;

	private MovieDatabase() {
		MovieDatabaseBuilder builder = new CrawlingBuilder(MAX_SIZE);
		this.movies = Collections.unmodifiableList(builder.buildMoviedatabase());
		this.indexer = Indexer.getInstance();
		indexer.buildIndex(movies);
	}

	public synchronized static MovieDatabase getInstance() {
		if (database == null) {
			database = new MovieDatabase();
		}
		return database;
	}

	public List<String> search(List<String> queryTerms) {
		List<Integer> indxes = (!queryTerms.isEmpty()) ? indexer.getIndexes(queryTerms) : new ArrayList<>();
		List<String> names = new ArrayList<>();
		for (int idx : indxes) {
			names.add(movies.get(idx).getName());
		}

		return names;
	}

	public List<Movie> getAll() {
		return this.movies;
	}
}
