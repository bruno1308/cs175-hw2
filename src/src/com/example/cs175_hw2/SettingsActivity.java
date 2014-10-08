package com.example.cs175_hw2;

import java.text.NumberFormat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		 SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar1); 
	       final TextView seekBarValue = (TextView)findViewById(R.id.seek_bar_value); 
	       seekBarValue.setText("1.0s");
	       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	   @Override 
	   public void onProgressChanged(SeekBar seekBar, int progress, 
	     boolean fromUser) { 
	    // TODO Auto-generated method stub 
		   double total;
		   if(progress < 5) progress = 5;
		       total = progress / 50.0;
	    seekBarValue.setText(String.valueOf(total) + "s"); 
	   } 

	   @Override 
	   public void onStartTrackingTouch(SeekBar seekBar) { 
	    // TODO Auto-generated method stub 
	   } 

	   @Override 
	   public void onStopTrackingTouch(SeekBar seekBar) { 
	    // TODO Auto-generated method stub 
	   } 
	       }); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
}
