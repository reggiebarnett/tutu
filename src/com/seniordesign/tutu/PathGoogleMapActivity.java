package com.seniordesign.tutu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class PathGoogleMapActivity extends FragmentActivity {

	private String url;
	LatLng startPoint, endPoint;
	List<List<HashMap<String, String>>> routes;
	ArrayList<String> maneuverArr = new ArrayList<String>(); 
	ArrayList<String> htmlInstArr = new ArrayList<String>();
	ArrayList<String> startLatArr = new ArrayList<String>(); 
	ArrayList<String> startLngArr = new ArrayList<String>(); 
	ArrayList<String> endLatArr = new ArrayList<String>();
	ArrayList<String> endLngArr = new ArrayList<String>();
	ArrayList<String> startLatLegs = new ArrayList<String>();
	ArrayList<String> startLngLegs = new ArrayList<String>();
	ArrayList<String> endLatLegs = new ArrayList<String>();
	ArrayList<String> endLngLegs = new ArrayList<String>();
	GoogleMap googleMap;
	final String TAG = "PathGoogleMapActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("*************past","inside pathgooglemapactivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path_google_map);
		Log.i("*************past","made xml");
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		Log.i("*************past","made support map frag");
		googleMap = fm.getMap();

		Log.i("*************past","google map create");
		
		//get passed in starting/ending lat and lng vars
		Intent mapIntent = getIntent();
		url = mapIntent.getStringExtra("urlString");
		
		Log.i("************url from intent create",url);
		
		ReadTask downloadTask = new ReadTask();
		downloadTask.execute(url);
		/*
		MarkerOptions options = new MarkerOptions();
		for(int i=0; i<startLatLegs.size(); i++)
		{
			startPoint = new LatLng(Double.parseDouble(startLatLegs.get(i)), Double.parseDouble(startLngLegs.get(i)));
			endPoint = new LatLng(Double.parseDouble(endLatLegs.get(i)), Double.parseDouble(endLngLegs.get(i)));
			options.position(startPoint);
			options.position(endPoint);
		}
		googleMap.addMarker(options);
*/
		//googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint,13));
		//addMarkers();

	}

	private void addMarkers() {
		if (googleMap != null) {
			googleMap.addMarker(new MarkerOptions().position(startPoint)
					.title("First Point"));
			googleMap.addMarker(new MarkerOptions().position(endPoint)
					.title("Second Point"));
		}
	}

	private class ReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				Log.i("*************url is ",url[0]);
				data = http.readUrl(url[0]);
				Log.i("*********data from url is", data);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				ParseJSON parser = new ParseJSON();
				routes = parser.parse(jObject);
				endLatArr = parser.endLatArr;
				endLatLegs = parser.endLatLegs;
				endLngArr = parser.endLngArr;
				endLngLegs = parser.endLngLegs;
				htmlInstArr = parser.htmlInstArr;
				maneuverArr = parser.maneuverArr;
				startLatArr = parser.startLatArr;
				startLatLegs = parser.startLatLegs;
				startLngArr = parser.startLngArr;
				startLngLegs = parser.startLngLegs;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;

			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				polyLineOptions.addAll(points);
				polyLineOptions.width(2);
				polyLineOptions.color(Color.BLUE);
			}

			googleMap.addPolyline(polyLineOptions);
		}
	}
}
