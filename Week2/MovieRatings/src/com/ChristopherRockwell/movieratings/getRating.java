package com.ChristopherRockwell.movieratings;

import org.json.JSONException;
import org.json.JSONObject;
import myEnum.myEnumClass.Movies;

public class getRating {

	int ratingNum;
	public static String critics;

	// check to see if movie rating is bad good or great
	public static String getRatingString(int rating) {
		
		String myString = "";
		
		if (rating < 50) {
			myString = "bad";
		} else if (rating >= 50 && rating < 80) {
			myString = "good";
		} else {
			myString = "great";
		}
		
		
		return myString;
	}

	// build JSON using ENUM data
	public static JSONObject buildJson() {

		// create movie JSON object
		JSONObject movieObject = new JSONObject();

		try {

			// create query JSON object
			JSONObject queryObject = new JSONObject();

			// create movie objects in query
			for (Movies movie : Movies.values()) {

				// create movieObject JSON object
				JSONObject myMovieObject = new JSONObject();

				// add movie to object
				myMovieObject.put("title", movie.setTitle());
				myMovieObject.put("year", movie.setYear());
				myMovieObject.put("criticScore", movie.setCriticScore());
				myMovieObject.put("audienceScore", movie.setAudienceScore());

				// put movie object into query object
				queryObject.put(movie.name().toString(), myMovieObject);
			}

			// add query to movies
			movieObject.put("query", queryObject);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movieObject;
	}

	// read JSON object
	public static String readJson(String selected) {

		String result, title, year, criticScore, audienceScore;
		
		JSONObject object = buildJson();
		
		try {
			title = object.getJSONObject("query").getJSONObject(selected).getString("title");
			year = object.getJSONObject("query").getJSONObject(selected).getString("year");
			criticScore = object.getJSONObject("query").getJSONObject(selected).getString("criticScore");
			audienceScore = object.getJSONObject("query").getJSONObject(selected).getString("audienceScore");
			
			result = "Title: " + title + "\r\n"
					+ "Year Released: " + year + "\r\n"
					+ "Critic Score: " + criticScore + "%\r\n"
					+ "Audience Score: " + audienceScore + "%\r\n";
			critics = criticScore;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.toString();
		}
		
		return result;
	}

}
