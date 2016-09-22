package com.seniordesign.tutu;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SaveVariables extends IntentService {

	long startTime = System.currentTimeMillis(), endTime = System.currentTimeMillis();
	int numTrips = 0, totalMi = 0, totalCals = 0, weightInKg = 0;
	float pace = 0;
	double oldLat = 0, oldLon = 0, lat = 0, lon = 0, locationLat = 0, locationLon = 0;
	Handler handler=new Handler();  
	protected static final long TIME_DELAY = 60000;
	
	protected SaveVariables() {
		super("SaveVariablesService");
	}
	
	private void saveAllVars() {
		// Access the default SharedPreferences
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		// The SharedPreferences editor - must use commit() to submit changes
	    SharedPreferences.Editor editor = preferences.edit();
	    
		long seconds = endTime - startTime;
		double hour = seconds/3600;
		//set new start time to current time
		startTime = System.currentTimeMillis();
	    
		// get variables from shared preferences
	    totalMi = preferences.getInt("savedMiles", 0);
	    totalCals = preferences.getInt("savedCals", 0);
	    weightInKg = preferences.getInt("userWeight", 0);
	    numTrips = preferences.getInt("savedTrips", 0);
	    
	    //CaloriesBurned = Math.round((kg * pace) * hr);
		getPace();
    	long currentCal = Math.round((weightInKg * pace) * hour);
    	
    	//get new latitude and longitude 
    	oldLat = lat;
    	oldLon = lon;
    	lat = locationLat; //get from google
    	lon = locationLon; //get from google
    	// update variable values
    	totalCals += currentCal;
    	totalMi += distance(lat,oldLat,lon,oldLon);
    	
	    // Edit the saved preferences
	    editor.putInt("savedCals", totalCals);
	    editor.putInt("savedTrips", numTrips);
	    editor.putInt("savedMiles", totalMi);
	    editor.commit();
	}
	
	public double distance(double lat1, double lat2, double lng1, double lng2) {
		//calculates distance between two latitude longitude locations
		//using the "haversine" formula
		double d = 0;
		double R = 6371; //mean radius km
		double pi = 3.14159265359;
		double lt1 = lat1*pi/180;
		double lt2 = lat2*pi/180;
		double clt = (lat2-lat1)*pi/180;
		double cln = (lng2-lng1)*pi/180;
		double a = 0;
		double c = 0;
			
		a = Math.sin(clt/2) * Math.sin(clt/2) +
				Math.cos(lt1) * Math.cos(lt2) *
				Math.sin(cln/2) * Math.sin(cln/2);
			
		c = 2 * Math.atan2(Math.sqrt(a) , Math.sqrt(1-a));
			
		d = R * c;
			
		return d;
	}
	
	private void getPace() {
		//get reference to system location manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		//create listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				pace = location.getSpeed();
				locationLat = location.getLatitude();
				locationLon = location.getLongitude();
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

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO while lat != dest lat and lon != dest lon 
		
		getPace();
		lat = locationLat;
		lon = locationLon;
		handler.post(saveAllVarsRunnable);
	}

	Runnable saveAllVarsRunnable = new Runnable(){  
	  public void run() {  
	      saveAllVars();
	      handler.postDelayed(this, TIME_DELAY);  
	     }  
	 };  
}
