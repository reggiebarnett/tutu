package com.seniordesign.tutu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class FitnessActivity extends ActionBarActivity {
	double currentCal = 0.0, currentTime = 0.0;
	int numTrips = 0;
	int totalMi = 0, avgMi = 0, totalCals = 0, avgCals = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fitness);
		
		// Access the default SharedPreferences
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
	    // get variables from shared preferences
	    numTrips = preferences.getInt("savedTrips", 0);
	    totalMi = preferences.getInt("savedMiles", 0);
	    totalCals = preferences.getInt("savedCals", 0);
	    
		//calculate other stuff
	    if(numTrips != 0){
	    	avgMi = totalMi/numTrips;
	    	avgCals = totalCals/numTrips;
	    }
	    else{
	    	avgMi = 0;
	    	avgCals = 0;
	    }

        //create and set textview boxes
        setTextViews();
        
	}

	private void setTextViews() {
		//Creating TextView Variable
        TextView totalTrips = (TextView) findViewById(R.id.totalTripsNumberID);
        TextView totalMiles = (TextView) findViewById(R.id.totalMilesNumberID);
        TextView avgDist = (TextView) findViewById(R.id.avgDistNumberID);
        TextView totalCal = (TextView) findViewById(R.id.totalCalNumberID);
        TextView avgCal = (TextView) findViewById(R.id.avgCalNumberID);
        //set TextViews
        totalTrips.setText("\t" + numTrips + " trips");
        totalMiles.setText("\t" + totalMi + " miles");
        avgDist.setText("\t" + avgMi + " miles");
        totalCal.setText("\t" + totalCals + " calories");
        avgCal.setText("\t" + avgCals + " calories");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fitness, menu);
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
