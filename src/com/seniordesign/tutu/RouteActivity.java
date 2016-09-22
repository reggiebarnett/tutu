package com.seniordesign.tutu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RouteActivity extends ActionBarActivity {
	 
	float lat, lng;
	float startLat, startLng; 
	float endLat, endLng;
	int numTrips = 0;
	SharedPreferences.Editor editor;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		
		//Creating Button variables
        Button navButton = (Button) findViewById(R.id.navigateB);      
        
        // Access the default SharedPreferences
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
	    // The SharedPreferences editor - must use commit() to submit changes
	    editor = preferences.edit();
	    // get variables from shared preferences
	    numTrips = preferences.getInt("savedTrips", 0);

        //Adding Listener to button
        navButton.setOnClickListener(new View.OnClickListener() {
           
            @Override
            public void onClick(View v) {       
                //Creating TextView Variable
            	EditText startPoint = (EditText) findViewById(R.id.startPoint);
            	EditText endPoint = (EditText) findViewById(R.id.endPoint);
               
            	//increment number of trips
            	numTrips++;
            	editor.putInt("savedTrips", numTrips);
            	
            	//start navigation method
            	navigate(startPoint.getText().toString(),endPoint.getText().toString());
            }
        });
	}
	
	private void navigate(String startPoint, String endPoint) {
		//build url with start/end points to pass into map
		String urlString = "https://maps.googleapis.com/maps/api/directions/json?origin="
				+ startPoint + "&destination=" + endPoint + "&mode=bicycling&waypoints=optimize:true"; 
		//for loop to iterate through each edittext for waypoints?
		// + "|" + edittext
		
		Intent intentMap = new Intent(this, PathGoogleMapActivity.class);
		intentMap.putExtra("urlString",urlString);
		
    	this.startActivity(intentMap);
    	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.route, menu);
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
	
	private void getStart() {
		//get reference to system location manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		//create listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				startLat = (float) location.getLatitude();
				startLng = (float) location.getLongitude();
			}
			public void onStatusChanged(String procider, int status, Bundle extras)
			{
			}
			public void onProviderEnabled(String provider)
			{
			}
			public void onProviderDisabled(String provider)
			{
			}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}
}
