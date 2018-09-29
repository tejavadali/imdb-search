package com.fw.search.resource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fw.search.database.MovieDatabase;

@RestController
public class Search {
	private MovieDatabase database = MovieDatabase.getInstance();

	@RequestMapping("/search")
	public List<String> greeting(@RequestParam(value = "query") String query) {
		String q = query.trim();
		List<String> terms = Stream.of(q.split("\\s+")).collect(Collectors.toList());
		
		return database.search(terms);
	}
}