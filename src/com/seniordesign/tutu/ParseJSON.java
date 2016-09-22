package com.seniordesign.tutu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class ParseJSON {

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
	
	public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
		List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
		JSONArray jRoutes = null;
		JSONArray jLegs = null;
		JSONArray jSteps = null;
				
		try {           
		        jRoutes = jObject.getJSONArray("routes");

		        /** Traversing all routes */
		        for(int i=0;i<jRoutes.length();i++){            
		        	jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
		            List path = new ArrayList<HashMap<String, String>>();

		            /** Traversing all legs */
		            for(int j=0;j<jLegs.length();j++){
		            	jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
		            	
		            	String end_location_lat = jLegs.getJSONObject(j).getJSONObject("end_location").getString("lat");
		            	String end_location_lng = jLegs.getJSONObject(j).getJSONObject("end_location").getString("lng");
		            	String start_location_lat = jLegs.getJSONObject(j).getJSONObject("start_location").getString("lat");
		            	String start_location_lng = jLegs.getJSONObject(j).getJSONObject("start_location").getString("lng");
		            	endLatLegs.add(end_location_lat);
		            	endLngLegs.add(end_location_lng);
		            	startLatLegs.add(start_location_lat);
		            	startLngLegs.add(start_location_lng);
		            	
		                /** Traversing all steps */
		                for(int k=0;k<jSteps.length();k++){
		                	
		                    String html_instructions = jSteps
									.getJSONObject(k).getString("html_instructions");
		                    String travel_mode = jSteps
									.getJSONObject(k).getString("travel_mode");
		                    String maneuver = jSteps
									.getJSONObject(k).getString("maneuver");
		                    
		                    String distance_text = jSteps
									.getJSONObject(k).getJSONObject("distance").getString("text");
		                    String distance_value = jSteps
									.getJSONObject(k).getJSONObject("distance").getString("value");

		                    String duration_text = jSteps
									.getJSONObject(k).getJSONObject("duration").getString("text");
		                    String duration_value = jSteps
									.getJSONObject(k).getJSONObject("duration").getString("value");

		                    String start_lat = jSteps
									.getJSONObject(k).getJSONObject("start_location").getString("lat");
		                    String start_lon = jSteps
									.getJSONObject(k).getJSONObject("start_location").getString("lng");

		                    String end_lat = jSteps
									.getJSONObject(k).getJSONObject("end_location").getString("lat");
		                    String end_lon = jSteps
									.getJSONObject(k).getJSONObject("end_location").getString("lng");

		                    String polyline = "";
		                    polyline = jSteps
									.getJSONObject(k).getJSONObject("polyline").getString("points");
		                    List<LatLng> list = decodePoly(polyline);


		                    /** Traversing all points */
		                    for(int l=0;l<list.size();l++){
		                        HashMap<String, String> hm = new HashMap<String, String>();
		                        hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
		                        hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
		                        path.add(hm);                       
		                    }    
		                    
		                    maneuverArr.add(maneuver);
			            	htmlInstArr.add(html_instructions);
			            	startLatArr.add(start_lat);
			            	startLngArr.add(start_lon);
			            	endLatArr.add(end_lat);
			            	endLngArr.add(end_lon);
		                }
		                routes.add(path);
		            }
		        }
		    
		    } catch (JSONException e) {         
		        e.printStackTrace();
		    }catch (Exception e){           
		    }
		return routes;
	}

	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}
		return poly;
	}
}
