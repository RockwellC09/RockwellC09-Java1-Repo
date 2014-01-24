package getRatingsPackage;

import java.util.ArrayList;

import com.ChristopherRockwell.movieratings.R;
import com.ChristopherRockwell.movieratings.getRating;
import com.ChristopherRockwell.movieratings.getRating.getData;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
	private static String badString;
	private static String goodString;
	private static String greatString;
	public static Context context; 
	public static String movieTitle;
	public static String _urlString;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set string to values in resource strings
        badString = getResources().getString(R.string.bad_string);
		goodString = getResources().getString(R.string.good_string);
		greatString = getResources().getString(R.string.great_string);
        setTheme(android.R.style.Theme_Holo_Light);
        context = this;
        
        // create linear layout
        layout = new LinearLayout(this);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        // create button and set properties
        final Button submit = new Button(this);
        submit.setText("Get Movie Rating");
        submit.setLayoutParams(new LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // button text color and background color
        submit.setBackgroundColor(Color.DKGRAY);
        submit.setTextColor(Color.WHITE);
        
        // create spinner for movie selection
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Captain Phillips");
        spinnerArray.add("Despicable Me 2");
        spinnerArray.add("The Lone Ranger");

        final Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        layout.addView(spinner);
        
        layout.addView(submit); 
        submit.setOnClickListener(new View.OnClickListener() {
        	// handles radio button submit click event
            public void onClick(View v) {
            	// get selected spinner item
            	String selected = spinner.getSelectedItem().toString();
            	
                // check selected spinner value and set the proper movie title
                if (selected == "Captain Phillips") {
                	movieTitle = "Captain%20Phillips";
                } else if (selected == "Despicable Me 2") {
                	movieTitle = "Despicable%20Me%202";
                } else {
                	movieTitle = "The%20Lone%20Ranger";
                }
                
                _urlString = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=bf72tfc2zjfbdscenpwx2e2r&q=" + movieTitle + "&page_limit=1";
                
                // check to see if there's a valid connection
            	if (getRating.connectionStatus(context)){
            		getRating.getData data = new getData();
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
        
        
        // create image view for tomato images
        ratingsImg = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 50, 0, 0);
        ratingsImg.setLayoutParams(params);
        layout.addView(ratingsImg);
        
     // create rating text view and add it you layout
        ratingMessage = new TextView(this);
        ratingMessage.setText("");
        ratingMessage.setId(5);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params2.setMargins(10, 20, 0, 0);
        ratingMessage.setLayoutParams(params2);
        ratingMessage.setTextSize(18);
        layout.addView(ratingMessage);
        
        // create rating text view and add it you layout
        ratingText = new TextView(this);
        ratingText.setText("");
        ratingText.setId(6);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params3.setMargins(10, 100, 0, 0);
        ratingText.setLayoutParams(params3);
        ratingText.setTextSize(18);
        layout.addView(ratingText);
        
        // set context view to linear layout
        setContentView(layout);
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
