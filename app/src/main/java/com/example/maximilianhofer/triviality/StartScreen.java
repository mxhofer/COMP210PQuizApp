package com.example.maximilianhofer.triviality;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviality.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartScreen extends Activity {

    Button inputName;
    Button resetStart;
    public static ArrayList<String> leaderboardName = new ArrayList<String>(); //array of the form [name1, name2, ...]
    public static ArrayList<Integer> leaderboardScore = new ArrayList<Integer>(); //array of the form [score1, score2, ...]
    public final static String EXTRA_MESSAGE = "com.example.maximilianhofer.triviality";

    EditText userNameInput;
    String userName;

    // onCreate method starts the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        userNameInput = (EditText) findViewById(R.id.edit_message);
        inputName = (Button)findViewById(R.id.button_name);
        resetStart = (Button)findViewById(R.id.button_resetStart);

        TextView textViewStart=(TextView)findViewById(R.id.textViewStart);

        inputName.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                SharedPreferences sharedPref = getSharedPreferences("user_name_file", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", userNameInput.getText().toString());
                editor.apply();

                userName = sharedPref.getString("username", "");

                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE); //got this from stackoverflow
                Matcher m = p.matcher(userName);
                boolean b = m.find();

                //conditionals output toast message if user input is empty, a white space or any special symbols
                if (userName.isEmpty() || userName.equals(" ") || b){
                    Toast.makeText(StartScreen.this, "No special characters. Enter your proper name.", Toast.LENGTH_SHORT).show();
                }
                else if (leaderboardName.size() > 4){
                    Toast.makeText(StartScreen.this, "The maximum number of players is 5. Reset the leaderboard to start a new game.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(leaderboardName.contains(userName)){
                    Toast.makeText(StartScreen.this, "This username already exists.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    leaderboardName.add(userName);
                    Toast.makeText(StartScreen.this, "Beautiful name. Saved it.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StartScreen.this, QuizActivity.class);
                    startActivity(intent);
                    finish();
                }

                //used only for better understanding app behaviour while testing
                System.out.println(leaderboardName);
            }
        });

        //resets the leaderboard and posts a toast message to communicate with the user
        resetStart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                StartScreen.leaderboardName.clear();
                StartScreen.leaderboardScore.clear();
                Toast.makeText(StartScreen.this, "Success! Leaderboard has been reset. Start a new game.", Toast.LENGTH_SHORT).show();
            }

        });

        //static text message on the start screen
        textViewStart.setText("\nWelcome to our multi-player Java Quiz. Click on top right option button for Moodle resources." +
                "\n\nHave fun!");
    }

    //if menu item meny_my.xml is contacted, it directs user to moodle page
    public void openResources(MenuItem item){
        String url = "https://moodle.ucl.ac.uk/course/view.php?id=35305";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    // override tells the phone to override super class method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
