package com.example.maximilianhofer.triviality;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.triviality.R;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

//activity to display the results screen, including a leaderboard, a rating bar, two buttons and a menu action to share the current score
public class ResultActivity extends Activity {

	Button newGame;
	Button resetLeaderboard;
	int yourScore;

	// onCreate method starts the activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		//get rating bar object
		RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1);
		bar.setNumStars(5);
		bar.setStepSize(0.5f);
		//get text view
		TextView textResult=(TextView)findViewById(R.id.textResult);
		TextView textLeaderboard=(TextView)findViewById(R.id.textLeaderboard);
		//get score
		Bundle b = getIntent().getExtras();
		int score= b.getInt("score");
		StartScreen.leaderboardScore.add(score); //adds score to the corresponding array
		//display score
		bar.setRating(score);
		String yourName = StartScreen.leaderboardName.get(StartScreen.leaderboardName.size() - 1);
		yourScore = StartScreen.leaderboardScore.get(StartScreen.leaderboardScore.size() - 1);

		//set text field to tell you your name and score out of the 5 questions displayed
		textResult.setText("Your name: " + yourName
				+ "\nYour score: " + yourScore
				+ " out of " + 5); //hard-coded number of questions per quiz!!! be careful

		if (yourScore < 3){
			Toast.makeText(ResultActivity.this, "Good effort, keep going!", Toast.LENGTH_SHORT).show();
		}
		else if (yourScore < 5){
			Toast.makeText(ResultActivity.this, "Almost a Java whiz!", Toast.LENGTH_SHORT).show();
		}
		else if (yourScore == 5){
			Toast.makeText(ResultActivity.this, "You legend!", Toast.LENGTH_SHORT).show();
		}

		int length = StartScreen.leaderboardName.size();
		int temp = 0;
		String tempname = "";

		//bubble sort algorithm sorting both arraylists on scores and the respective user name
		for(int i=0; i < length-1; i++){
			for(int j=1; j < length-i; j++){
				if(StartScreen.leaderboardScore.get(j-1) < StartScreen.leaderboardScore.get(j))
				{
					temp = StartScreen.leaderboardScore.get(j-1);
					StartScreen.leaderboardScore.set(j-1,StartScreen.leaderboardScore.get(j));
					StartScreen.leaderboardScore.set(j, temp);

					tempname = StartScreen.leaderboardName.get(j-1);
					StartScreen.leaderboardName.set(j-1,StartScreen.leaderboardName.get(j) );
					StartScreen.leaderboardName.set(j, tempname);
				}
			}
		}

		textLeaderboard.setText("Leaderboard:");

		//populate list with names
		ListAdapter leaderboardNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				StartScreen.leaderboardName);
		ListView nameListView = (ListView) findViewById(R.id.nameListView);
		nameListView.setAdapter(leaderboardNameAdapter);

		//populate list with scores
		ListAdapter leaderboardScoreAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1,
				StartScreen.leaderboardScore);
		ListView scoreListView = (ListView) findViewById(R.id.scoreListView);
		scoreListView.setAdapter(leaderboardScoreAdapter);


		System.out.println(StartScreen.leaderboardName); // just to check output in console during testing
		System.out.println(StartScreen.leaderboardScore); // just to check output in console during testing

		newGame = (Button)findViewById(R.id.button_newGame); //start new game when clicking the button
		newGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ResultActivity.this, StartScreen.class);
				startActivity(intent);
				finish();
			}
		});

		resetLeaderboard = (Button)findViewById(R.id.button_reset); //resets the leaderboard and returns toast to confirm to user
		resetLeaderboard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				StartScreen.leaderboardName.clear();
				StartScreen.leaderboardScore.clear();
				Toast.makeText(ResultActivity.this, "Success! Leaderboard has been reset. Start a new game.", Toast.LENGTH_SHORT).show();
			}
		});
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_result, menu);
		return true;
	}

	//share your score out of a max of 5
	public void shareEmail(MenuItem item){
		String shareBody = "My score is <" +
				yourScore + "> out of 5 on the awesome Java quiz app. Get your copy in the Play Store: victorminsumax";
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
	}
}
