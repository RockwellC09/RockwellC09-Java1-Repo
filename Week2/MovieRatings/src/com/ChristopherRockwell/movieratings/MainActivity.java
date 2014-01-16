package com.ChristopherRockwell.movieratings;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.view.View;
import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	// variables
	Button buybutton;
	LinearLayout layout;
	String enumText;
	TextView ratingText;
	TextView ratingMessage;
	ImageView ratingsImg;
	private static String badString;
	private static String goodString;
	private static String greatString;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set string to values in resource strings
        badString = getResources().getString(R.string.bad_string);
		goodString = getResources().getString(R.string.good_string);
		greatString = getResources().getString(R.string.great_string);
        setTheme(android.R.style.Theme_Holo_Light);
        setContentView(R.layout.activity_main);
        
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
        
        // create radio group and 3 radio button and set properties
        final RadioButton[] rb = new RadioButton[3];
        final RadioGroup rg = new RadioGroup(this); //create the RadioGroup
        rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
        for(int i=0; i<3; i++){
            rb[i]  = new RadioButton(this);
            rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
            if (i == 0) {
            	rb[i].setText("White House Down");
            	rb[i].setPadding(0, 50, 0, 25);
            } else if (i == 1) {
            	rb[i].setText("Fast & Furious 6");
            	rb[i].setPadding(0, 25, 0, 25);
            } else {
            	rb[i].setText("The Family");
            	rb[i].setPadding(0, 25, 0, 50);
            }
        }
        // add the whole RadioGroup to the layout
        layout.addView(rg);
        layout.addView(submit); 
        submit.setOnClickListener(new View.OnClickListener() {
        	// handles radio button submit click event
            public void onClick(View v) {
            	// get selected radio button id
                int rgId = rg.getCheckedRadioButtonId();
                RadioButton myRb = (RadioButton) findViewById(rgId);
                String selected = myRb.getText().toString();
                // check selected radio button text and set proper ENUM text value
                if (selected == "White House Down") {
                	enumText = "Movie1";
                } else if (selected == "Fast & Furious 6") {
                	enumText = "Movie2";
                } else {
                	enumText = "Movie3";
                }
                
                // read JSON and set Text View text
                ratingText.setText(getRating.readJson(enumText));
                String critScore = getRating.critics;
                String myRatingStr = getRating.getRatingString(Integer.valueOf(critScore));
                // check critic score and display proper message and image
                if (myRatingStr == "bad") {
                	ratingMessage.setText(badString);
                	ratingsImg.setBackgroundResource(R.drawable.rotten);
                } else if (myRatingStr == "good") { 
                	ratingMessage.setText(goodString);
                	ratingsImg.setBackgroundResource(R.drawable.fresh);
                } else {
                	ratingMessage.setText(greatString);
                	ratingsImg.setBackgroundResource(R.drawable.certified);
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
    
}
