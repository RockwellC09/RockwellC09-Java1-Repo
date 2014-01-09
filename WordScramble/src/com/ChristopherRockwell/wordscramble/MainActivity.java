package com.ChristopherRockwell.wordscramble;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends Activity {

	String[] scrWordsArray;
	String[] unscrWordsArray;
	int count = 0;
	int score = 0;
	boolean gameOver = false;
	TextView currentWord;
	TextView myScore;
	TextView myEditText;
	TextView myWordsView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set resource variable to scrambled and unscrambled words variables
        scrWordsArray = getResources().getStringArray(R.array.scr_words);
        unscrWordsArray = getResources().getStringArray(R.array.unscr_words);
        currentWord = (TextView)findViewById(R.id.textView1);
        currentWord.setText(scrWordsArray[0]);
        myScore = (TextView)findViewById(R.id.textView2);
        myEditText = (TextView)findViewById(R.id.editText1);
        myWordsView = (TextView)findViewById(R.id.textView3);
        myWordsView.setText("");
        
        // setup button click event handler
        Button myButton = (Button) findViewById(R.id.button1);
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	myWordsView.setText("");
                // Perform actions on click
            	
            	// if player types "view" into the editText they will get the correct answers
            	if (myEditText.getText().toString().toLowerCase().equals("view")) {
            		// loop through the words and output them so player can see the correct answers
            		for(int arrayCount = 0; arrayCount <= 6; arrayCount++) {
            			String oldText = myWordsView.getText().toString() + " ";
            			String newText = oldText + unscrWordsArray[arrayCount];
            			myWordsView.setText(newText);
            		}
            	} else {
            		// check to see if word was unscrambled correctly
            		checkWords();
            	}
            }
        });
    }
    
 // create function/method that checks to see if words are correctly unscrambled
    void checkWords() {
    	String word1 = myEditText.getText().toString().toLowerCase();
    	String word2 = unscrWordsArray[count].toString();
    	if (word1.equals(word2)) {
    		score++;
    		myScore.setText(score + "/7 Correct");
    		
    		// create correct scramble alert dialog
    		AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setTitle("Correct");
            builder1.setMessage("You've unscrambled the word correctly!");
            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "Your score is now " + score, Toast.LENGTH_LONG).show();
                    count++;
                	
                	// check from game over
                	if (count > 6) {
                		gameOver = true;
                	} else {
                		currentWord.setText(scrWordsArray[count]);
                	}
                }
                
            });
            
            // show alert
            builder1.show();
            myEditText.setText("");
          // check for blank editText
    	} else if (word1.trim().equals("")) {
    		// create blank editText alert dialog
    		AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
            builder2.setTitle("No Answer");
            builder2.setMessage("You haven't entered an answer.");
            builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "Give it a shot", Toast.LENGTH_LONG).show();
                }
            });
            
         // show alert
            builder2.show();
            myEditText.setText("");
    	} else {
    		// create incorrect scramble alert dialog
    		AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
            builder3.setTitle("Incorrect");
            builder3.setMessage("You've unscrambled the word incorrectly.");
            builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "Better luck next time", Toast.LENGTH_LONG).show();
                    count++;
                	
                	// check from game over
                	if (count > 6) {
                        gameOver = true;
                	} 
                	isGameOver();
                }
            });
            
         // show alert
            builder3.show();
            myEditText.setText("");
    	}
    }
    void isGameOver() {
    	if (gameOver == true) {

	    	// create game over alert dialog
			AlertDialog.Builder builder4 = new AlertDialog.Builder(MainActivity.this);
	        builder4.setTitle("Game Over");
	        builder4.setMessage("You total score is " + score + " out of 7. Type \"view\" into the text box and submit to view the list of correctly unscrambled words.");
	        builder4.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface arg0, int arg1) {
	                // TODO Auto-generated method stub
	                Toast.makeText(getApplicationContext(), "Thanks for playing!", Toast.LENGTH_SHORT).show();
	            }
	        });
	        
	     // show alert
	        builder4.show();
	        // reset defaults
	        count = 0;
	        score = 0;
	        currentWord.setText(scrWordsArray[0]);
	        myEditText.setText("");
	        myScore.setText("0/7 Correct");
	        gameOver = false;
    	} else {
    		currentWord.setText(scrWordsArray[count]);
    	}
    }
}
