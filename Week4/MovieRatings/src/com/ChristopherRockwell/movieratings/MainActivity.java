package com.ChristopherRockwell.movieratings;

import getRatingsPackage.getRating;
import com.ChristopherRockwell.movieratings.R;
import com.ChristopherRockwell.movieratings.connection.getData;
import com.loopj.android.image.SmartImageView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// variables
	Button buybutton;
	LinearLayout layout;
	public static TextView ratingText;
	static TextView ratingMessage;
	static ImageView ratingsImg;
	static Spinner spinner;
	public static SmartImageView smrtImg;
	private static String badString;
	private static String goodString;
	private static String greatString;
	public static Context context; 
	public static String movieTitle;
	public static String _urlString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// set ActionBar color
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#545454")));
		
		// set action bar text color
		int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView Tv = (TextView)findViewById(titleId);
		Tv.setTextColor(Color.WHITE);

		// reference smart ImaveView to be used when setting the movie poster image
		smrtImg = (SmartImageView) this.findViewById(R.id.smrt_image);

		// set custom font for submit button
		Typeface customFont = Typeface.createFromAsset(this.getAssets(), "Audiowide-Regular.ttf");
		Button btn = (Button) findViewById(R.id.button1);
		btn.setTypeface(customFont);

		// set XML attributes to variables to use within code
		ratingText = (TextView) findViewById(R.id.textView2);
		ratingMessage = (TextView) findViewById(R.id.textView1);
		ratingsImg = (ImageView) findViewById(R.id.imageView1);
		spinner = (Spinner) findViewById(R.id.spinner1);

		// set custom font for TextViews
		Typeface customFont2 = Typeface.createFromAsset(this.getAssets(), "MontserratAlternates-Regular.ttf");
		ratingText.setTypeface(customFont2);
		ratingMessage.setTypeface(customFont2);
		
		// set Action Bar Font and size
		Tv.setTypeface(customFont2);
		Tv.setTextSize(22);

		// clear out TextViews and ImageView
		ratingText.setText("");
		ratingMessage.setText("");
		ratingsImg.setImageResource(android.R.color.transparent);

		// set string to values in resource strings
		badString = getResources().getString(R.string.bad_string);
		goodString = getResources().getString(R.string.good_string);
		greatString = getResources().getString(R.string.great_string);
		context = this;

		btn.setOnClickListener(new View.OnClickListener() {
			// handles radio button submit click event
			public void onClick(View v) {
				// get selected spinner item
				String selected = spinner.getSelectedItem().toString();

				// check selected spinner value and set the proper movie title
				if (selected.equals("Captain Phillips")) {
					movieTitle = "Captain%20Phillips";
				} else if (selected.equals("Despicable Me 2")) {
					movieTitle = "Despicable%20Me%202";
				} else {
					movieTitle = "The%20Lone%20Ranger";
				}

				_urlString = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=bf72tfc2zjfbdscenpwx2e2r&q=" + movieTitle + "&page_limit=1";

				// check to see if there's a valid connection
				if (getRating.connectionStatus(context)){
					// calling getData, which is located in my connection.jar file to connection to URL and get the proper information
					getData data = new getData();
					data.execute(_urlString);
				} else {
					// create alert dialog for users without a valid internet connection
					AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
					builder1.setTitle("No Connection");
					builder1.setMessage("You don't have a valid internet connection.");
					builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), "Check your connection and try again.", Toast.LENGTH_LONG).show();
						}
					});

					// show alert
					builder1.show();
				}

			}
		});
	}

	// set the proper text and icon for the current movie's rating
	public static void setCriticsText() {
		String critScore = getRating.critics;
		String myRatingStr = getRating.getRatingString(Integer.valueOf(critScore));
		// check critic score and display proper message and image
		if (myRatingStr == "bad") {
			ratingMessage.setText(badString);
			ratingsImg.setBackgroundResource(R.drawable.badrating);
		} else if (myRatingStr == "good") { 
			ratingMessage.setText(goodString);
			ratingsImg.setBackgroundResource(R.drawable.goodrating);
		} else {
			ratingMessage.setText(greatString);
			ratingsImg.setBackgroundResource(R.drawable.greatrating);
		}
	}

}
