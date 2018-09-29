package com.fw.search.database;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fw.search.database.model.Movie;

public class CrawlingBuilder implements MovieDatabaseBuilder {
	private static int maxSize = 1000;
	private static final String IMDB_BASE_URI = "https://www.imdb.com/search/title?groups=top_1000&sort=user_rating&page=";

	public CrawlingBuilder(int size) {
		maxSize = size;
	}

	@Override
	public List<Movie> buildMoviedatabase() {
		boolean emptyResponse = false;
		List<Movie> movies = new ArrayList<>();
		int page = 1;
		while (movies.size() < maxSize && !emptyResponse) {
			List<Movie> onePage = findMoviesByPage(page, movies.size());
			emptyResponse = onePage.isEmpty();
			for (int j = 0; j < onePage.size() && movies.size() < maxSize; j++) {
				movies.add(onePage.get(j));
			}
			page++;
		}
		return movies;
	}

	public List<Movie> findMoviesByPage(int page, int next) {
		List<Movie> movies = new ArrayList<>();
		String nextUri = IMDB_BASE_URI + page;
		try {
			Document doc = Jsoup.connect(nextUri).get();
			Elements elms = doc.select("div.lister-item.mode-advanced");
			for (Element element : elms) {
				String movieName = extractMovieName(element);
				String director = extarctDirctorName(element);
				List<String> stars = extractStars(element);
				movies.add(new Movie(movieName, director, stars, ++next));
			}
		} catch (Exception ex) {
			System.out.println("Excpetion while hitting : " + nextUri );
		}

		return movies;
	}

	private List<String> extractStars(Element e) {
		Elements elms = e.selectFirst("div.lister-item-content").select("p").get(2).select("a[href]");
		List<String> stars = new ArrayList<>();
		for (Element elm : elms) {
			stars.add(elm.text());
		}

		return stars;
	}

	private String extractMovieName(Element e) {
		return e.selectFirst("div.lister-item-content").selectFirst("h3").selectFirst("a[href]").text();
	}

	private String extarctDirctorName(Element e) {
		return e.selectFirst("div.lister-item-content").select("p").get(2).selectFirst("a[href]").text();
	}
}
