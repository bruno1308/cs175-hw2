package com.example.cs175_hw2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
/* TODO Demo of code running on device	1pt
	TODO Launch screen as described with start and setting buttons working (1/2pt each).	1pt
	TODO Settings page as described.	1pt
	TODO Persistence of name, slider, and high score data.	1pt
	TODO Slider value used to set how long a person has to change the screen and click button during game play.	1pt
	TODO Game play as described.	1pt
	TODO Lives works as described.	1pt
	TODO Game scoring works as described.	1pt
	TODO Game over works as described.	1pt
	TODO 10 seconds then go back to the start screen
	TODO save high score if higher than highest
	TODO Log messages on screen orientation change.*/

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public void openSettingsActivity(View view){
    	Intent settings = new Intent(this, SettingsActivity.class);
    	startActivity(settings);
    }    
    
    public void openGameActivity(View view){
    	Intent game = new Intent(this, GameActivity.class);
    	startActivity(game);
    }
    
}
