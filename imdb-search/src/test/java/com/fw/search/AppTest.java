package com.fw.search;

import java.util.ArrayList;
import java.util.List;

import com.fw.search.database.MovieDatabase;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	//@org.junit.Test
	public void test() {
		MovieDatabase md = MovieDatabase.getInstance();
		List<String> l = new ArrayList<>();
		l.add("spielberg");
		List<String> res = md.search(l);
		for (String s : res) {
			System.out.println(s);
		}
	}
}
