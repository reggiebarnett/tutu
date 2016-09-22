package com.seniordesign.tutu;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InstallActivity extends ActionBarActivity {

	int weightInKg = 0;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_install);
		
		// Access the default SharedPreferences
	    preferences = PreferenceManager.getDefaultSharedPreferences(this);
	    
	    // The SharedPreferences editor - must use commit() to submit changes
	    editor = preferences.edit();
	    
		//Creating Button variables
        Button kgButton = (Button) findViewById(R.id.kgB);      
        Button lbButton = (Button) findViewById(R.id.lbB);   
        
		//Adding Listener to button
        kgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {              
            	EditText userWeight = (EditText) findViewById(R.id.userWeightID);
            	weightInKg = Integer.parseInt(userWeight.getText().toString());
            	editor.putInt("userWeight",weightInKg);
            	editor.commit();
            	openMainPage();
            }
        });
        lbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	EditText userWeight = (EditText) findViewById(R.id.userWeightID);
            	weightInKg = (int) (Integer.parseInt(userWeight.getText().toString()) * 0.45359237);
            	editor.putInt("userWeight",weightInKg);
            	editor.commit();
            	openMainPage();
            }
        });
	}
	
	public void openMainPage()
	{
		Intent mainIntent = new Intent(this, MainActivity.class);
		this.startActivity(mainIntent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.install, menu);
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
