package com.seniordesign.tutu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MaintenanceActivity extends ActionBarActivity {

    private int miles = 0;
    private int tirePressureMiles = 0, checkBrakesMiles = 0, checkChainMiles = 0;
    private int wheelTruenessMiles = 0, checkTiresMiles = 0, bikeFrameMiles = 0;
    private int checkWheelsMiles = 0, lubeCablesMiles = 0, checkFrayingMiles = 0, replaceBrakesMiles = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintenance);

		// Access the default SharedPreferences
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

	    // get variable for miles
	    miles = preferences.getInt("savedMiles", 0);
	    
	    // method to do all statistics to find miles left for each item
	    calculateStats();
	    
	    //set textviews
	    setTextviews();

	}

	private void setTextviews() {
		//get textviews
	    TextView totalMiles = (TextView) findViewById(R.id.totalMilesNumberID);
	    TextView tirePressure = (TextView) findViewById(R.id.tirePressureNumberID);
	    TextView checkBrakes = (TextView) findViewById(R.id.checkBrakesNumberID);
	    TextView checkChain = (TextView) findViewById(R.id.checkChainNumberID);
	    TextView wheelTrueness = (TextView) findViewById(R.id.wheelTruenessNumberID);
	    TextView bikeFrame = (TextView) findViewById(R.id.bikeFrameNumberID);
	    TextView checkWheels = (TextView) findViewById(R.id.checkWheelsNumberID);
	    TextView lubeCables = (TextView) findViewById(R.id.lubeCablesNumberID);
	    TextView checkTires = (TextView) findViewById(R.id.checkTiresNumberID);
	    TextView checkFraying = (TextView) findViewById(R.id.checkFrayingNumberID);
	    TextView replaceBrakes = (TextView) findViewById(R.id.replaceBrakesNumberID);

	    //set textviews
	    totalMiles.setText("\t" + miles + " miles");
	    tirePressure.setText("\t" + tirePressureMiles + " miles");
	    checkBrakes.setText("\t" + checkBrakesMiles + " miles");
	    checkChain.setText("\t" + checkChainMiles + " miles");
	    wheelTrueness.setText("\t" + wheelTruenessMiles + " miles");
	    bikeFrame.setText("\t" + bikeFrameMiles + " miles");
	    checkWheels.setText("\t" + checkWheelsMiles + " miles");
	    lubeCables.setText("\t" + lubeCablesMiles + " miles");
	    checkTires.setText("\t" + checkTiresMiles + " miles");
	    checkFraying.setText("\t" + checkFrayingMiles + " miles");
	    replaceBrakes.setText("\t" + replaceBrakesMiles + " miles");
	}

	private void calculateStats() {
		// calculate statistics
		tirePressureMiles = 0;
		checkBrakesMiles = 0;
		checkChainMiles = 0;
		wheelTruenessMiles = 0;
		// 500 - miles%500
		bikeFrameMiles = 500 - miles%500;
		checkWheelsMiles = 500 - miles%500;
		lubeCablesMiles = 500 - miles%500;
		//2500 - miles%2500
		checkTiresMiles = 2500 - miles%2500;
		checkFrayingMiles = 2500 - miles%2500;
		//6000 - miles%6000
		replaceBrakesMiles = 6000 - miles%6000;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maintenance, menu);
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
