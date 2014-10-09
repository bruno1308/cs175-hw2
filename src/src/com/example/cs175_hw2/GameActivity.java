package com.example.cs175_hw2;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends ActionBarActivity {
	TextView highscore_label;
	TextView lives_label;
	TextView score_label;
	TextView orientation_label;
	Timer t;
	TimerTask tt;
	Button click;
	int orientation;
	int lives = 11;
	int highscore=0;
	int score = 0;
	String current_name, best_name;
	double seeker;
	Boolean hasClicked=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		highscore_label = (TextView)findViewById(R.id.highScore);
		lives_label = (TextView)findViewById(R.id.lives);
		score_label = (TextView)findViewById(R.id.currentScore);
		orientation_label = (TextView)findViewById(R.id.orientation);
		click = (Button)findViewById(R.id.button1);
		loadHighScore();
		t = new Timer();
		tt = new TimerTask() {
			
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
				     @Override
				     public void run() {
				    	 if(!hasClicked) lives--;
				    	 checkGameOver();
				    	 startRound();
				    	 updateScoreLives();
				    	 hasClicked = false;

				    }
				});
				
				
			}
		};
		t.scheduleAtFixedRate(tt, 0, (long)seeker);
				
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	Log.i("Game", "Orientation changed to LANDSCAPE");
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	Log.i("Game", "Orientation changed to PORTRAIT");
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);
	  // Save UI state changes to the savedInstanceState.
	  // This bundle will be passed to onCreate if the process is
	  // killed and restarted.
	  savedInstanceState.putInt("score", Integer.parseInt(score_label.getText().toString()));
	  savedInstanceState.putInt("lives", Integer.parseInt(lives_label.getText().toString()));
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
	  // Restore UI state from the savedInstanceState.
	  // This bundle has also been passed to onCreate.
	   lives = savedInstanceState.getInt("lives");
	   score = savedInstanceState.getInt("score");
	   updateScoreLives();
	   
	}	
	
	public void loadHighScore(){
		
		final DBConnection my_connection = new DBConnection(this, "Game", 3);
		SQLiteDatabase db = my_connection.getReadableDatabase();
    	Cursor c = db.rawQuery("SELECT * FROM utils where id = 0", null);
    	if(c.getCount()== 0){
    		db.execSQL("INSERT INTO utils VALUES (0, 'Player1',0,"+1.0+")");
    		highscore_label.setText("0");
    		seeker = 1;
    	}
    	else{
    		c.moveToNext();
    		seeker = c.getDouble(3);
    		if(seeker < 5) seeker = 5;
    		seeker /= 50;
    		seeker *= 1000;
    		current_name = c.getString(1);
    		c = db.rawQuery("SELECT * FROM utils where id = 1", null);
    		if(c.getCount() != 0){
    		c.moveToNext();
    		best_name = c.getString(1);
    		highscore = c.getInt(2);
    		}else{
    			best_name = "";
    			highscore = 0;
    		}
    		highscore_label.setText(Integer.toString(highscore)+" by "+best_name);
    	}
    	db.close();
	}
	
	public void buttonClick(View view){
   	 hasClicked = true;
		if(orientation == getOrientation()){
			score++;
		}
		else{
			lives--;
			checkGameOver();
		}
		updateScoreLives();
		
	}
	
	public int getOrientation(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
        {
        	 return Configuration.ORIENTATION_PORTRAIT;
        	 
        }
        else
        {
        	return Configuration.ORIENTATION_LANDSCAPE;      	
        }
	}
	public void updateScoreLives(){
		score_label.setText(Integer.toString(score));
		lives_label.setText(Integer.toString(lives));
	}
	
	public void startRound(){
		Random r = new Random();
		orientation = r.nextInt(2) + 1;
		if(orientation == 1){
			orientation_label.setText("PORTRAIT");
		}else{
			orientation_label.setText("LANDSCAPE");
		}
	}
	
	public void saveScore(){
		if (score>highscore){
			final DBConnection my_connection = new DBConnection(this, "Game", 3);
			SQLiteDatabase db = my_connection.getReadableDatabase();
        	db = my_connection.getWritableDatabase();
        	db.execSQL("INSERT OR REPLACE INTO utils VALUES(1,'"+current_name+"', "+score+", "+seeker+")");
        	db.close();
		}
	}
	
	public void checkGameOver(){
		if (lives == 0){
			Intent game_over = new Intent(this, EndActivity.class);
			t.cancel();
			t.purge();
			tt.cancel();			
			saveScore();
			startActivity(game_over);
		}
	}
}
