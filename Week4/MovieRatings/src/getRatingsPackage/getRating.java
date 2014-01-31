// Christopher Rockwell
// read JSON data from rotten tomatoes api and set proper information to the UI 

package getRatingsPackage;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ChristopherRockwell.movieratings.MainActivity;

public class getRating {

	public static String critics;
	static Context context;

	// check to see if movie rating is bad good or great
	public static String getRatingString(int rating) {

		String ratingStr = "";

		if (rating < 50) {
			ratingStr = "bad";
		} else if (rating >= 50 && rating < 80) {
			ratingStr = "good";
		} else {
			ratingStr = "great";
		}


		return ratingStr;
	}

	// check to see if user have a valid internet connection
	public static boolean connectionStatus(Context context) {
		boolean isConnected = false;
		ConnectivityManager ConnectMngr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = ConnectMngr.getActiveNetworkInfo();
		if (netInfo != null) {
			if (netInfo.isConnected()) {
				isConnected = true;
			}
		}

		return isConnected;
	}

	// read JSON object
	public static String readJson(String selected) throws JSONException {

		String result;
		String title, year, criticScore, audienceScore;

		// set JSONOject and cast into array the back into an object to get movie proper info
		JSONObject obj = new JSONObject(selected);
		JSONArray movies = obj.getJSONArray("movies");
		JSONObject castObj = movies.getJSONObject(0);
		JSONObject antrCastObj = castObj.getJSONObject("ratings");
		JSONObject imgCast = castObj.getJSONObject("posters");

		// set smart ImageView image
		MainActivity.smrtImg.setImageUrl(imgCast.getString("profile"));

		//Log.i(TAG, castObj.toString());

		try {
			title = castObj.getString("title");
			year = castObj.getString("year");
			criticScore = antrCastObj.getString("critics_score");
			audienceScore = antrCastObj.getString("audience_score");

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
