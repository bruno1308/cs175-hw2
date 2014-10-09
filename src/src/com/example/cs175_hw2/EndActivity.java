package com.example.cs175_hw2;

import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EndActivity extends ActionBarActivity {
	Timer t;
	TimerTask tt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);

		 t = new Timer();
		 tt = new TimerTask() {
			
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
				     @Override
				     public void run() {
				    	 Intent main = new Intent(EndActivity.this, MainActivity.class);
				    	 startActivity(main);
				    }
				});
				
				
			}
		};
		t.scheduleAtFixedRate(tt, 1000, 10000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.end, menu);
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

	protected void onStop() {
	    super.onStop();
	    t.purge();
	    t.cancel();
	    tt.cancel();
	}
	
	@Override
	public void onBackPressed() {
		Intent main = new Intent(EndActivity.this, MainActivity.class);
		startActivity(main);
	}
}

