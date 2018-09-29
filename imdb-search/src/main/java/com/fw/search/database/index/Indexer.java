package com.fw.search.database.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fw.search.database.model.Movie;

public class Indexer {
	private Map<String, Set<Integer>> termToIndexMap;
	private static Indexer indexer;
	
	private Indexer() {
		termToIndexMap = new HashMap<>();
	}
	
	public static Indexer getInstance() {
		if (indexer == null) {
			indexer = new Indexer();
		}
		
		return indexer;
	}
	
	public void buildIndex(List<Movie> movies) {
		for (int i = 0; i < movies.size(); i++) {
			Movie m = movies.get(i);
			indexOneMovie(m, i);
		}
	}
	
	public List<Integer> getIndexes(List<String> queryTerms) {
		Set<Integer> one = termToIndexMap.getOrDefault(queryTerms.get(0).toLowerCase(), new HashSet());
		for (int i = 1; i < queryTerms.size() && one.size() > 0; i++) {
			Set<Integer> two = termToIndexMap.getOrDefault(queryTerms.get(i).toLowerCase(), new HashSet<>());
			Set<Integer> tmp = new  HashSet<>();
			for(Integer x : one) {
				if (two.contains(x)) {
					tmp.add(x);
				}
			}
			one = tmp;
		}
		
		List<Integer> l = new ArrayList<>(one);
		Collections.sort(l, (a, b) -> a - b);
		return l;
	}

	private void indexOneMovie(Movie m, int idx) {
		for(String s : m.getStars()) {
			String[] parts = s.split("\\s+");
			for(String p : parts) {
				String lower = p.toLowerCase();
				Set<Integer> set = termToIndexMap.getOrDefault(lower, new HashSet<>());
				set.add(idx);
				termToIndexMap.put(lower, set);
			}
		}
	}
}
