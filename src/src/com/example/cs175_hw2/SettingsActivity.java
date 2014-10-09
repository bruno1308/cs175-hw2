package com.example.cs175_hw2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.database.sqlite.*;
import android.database.*;
import android.app.AlertDialog;
import android.content.*;

public class SettingsActivity extends ActionBarActivity {
	SeekBar seekBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		   seekBar = (SeekBar)findViewById(R.id.seekBar1); 
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
	       final DBConnection my_connection = new DBConnection(this, "Game", 3);
	       SQLiteDatabase db = my_connection.getReadableDatabase();
       	Cursor c = db.rawQuery("SELECT * FROM utils", null);
       	if(c.getCount()!= 0){
       		c.moveToLast();
       		String name = c.getString(1);
       		int value = c.getInt(3);
       		((EditText)findViewById(R.id.editText1)).setText(name);
       		((SeekBar)findViewById(R.id.seekBar1)).setProgress(value);
       	}
       	db.close();
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
	
	public void launchDialog(View view){
		final DBConnection my_connection = new DBConnection(this, "Game", 3);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
		builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	SQLiteDatabase db = my_connection.getWritableDatabase();
        		String name = ((EditText)findViewById(R.id.editText1)).getText().toString(); 
        		double value = seekBar.getProgress();
	        	db.execSQL("INSERT OR REPLACE INTO utils (id, name, slider) VALUES (0, '"+name+"',"+value+")");
	        	db.close();
	        	finish();
	           }
	       });
		builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               finish();
	           }
	       });
		AlertDialog dialog = builder.create();
		dialog.show();
		        
		
	}
}
