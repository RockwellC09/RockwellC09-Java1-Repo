// Christopher Rockwell
// read JSON data from rotten tomatoes api and set proper information to the UI 

package getRatingsPackage;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ChristopherRockwell.movieratings.MainActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class getRating {

	public static String critics;
	static Context context;
	static String TAG = "NETWORK DATA - MAINACTIVITY";

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
	
	public static String getResponse(URL url) {
		String response = "";
		try {
			URLConnection connect =  url.openConnection();
			BufferedInputStream buffIn = new BufferedInputStream(connect.getInputStream());
			byte[] contextByte = new byte[1024];
			int bytesRead = 0;
			StringBuffer responseBuffer = new StringBuffer();
			while ((bytesRead = buffIn.read(contextByte)) != -1) {
				 response = new String(contextByte, 0, bytesRead);
				 responseBuffer.append(response);
			}
			
			response = responseBuffer.toString();
		} catch (IOException e) {
			response = "Something went wrong";
			Log.e(TAG, "Error: ", e);
		}
		
		return response;
	}
	
	// create async task
	public static class getData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String responseString = "";
			try {
				URL url = new URL(MainActivity._urlString);
				responseString = getResponse(url);
			} catch (MalformedURLException e) {
				responseString = "Something went wrong";
				Log.e(TAG, "Error: ", e);
			}
			return responseString;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// read JSON and set Text View text
            try {
				MainActivity.ratingText.setText(getRating.readJson(result));
				MainActivity.setCriticsText();
			} catch (JSONException e) {
				Log.e(TAG, "Error: ", e);
			}
			super.onPostExecute(result);
		}
		
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
