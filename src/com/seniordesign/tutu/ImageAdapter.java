package com.seniordesign.tutu;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int height, width;

    public ImageAdapter(Context c, int height, int width) {
        mContext = c;
        this.height = height;
        this.width = width;
    }

    public int getCount() {
        return menuStrings.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	Button b;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            b = new Button(mContext);
            b.setLayoutParams(new GridView.LayoutParams(width/2, height/2));
            b.setPadding(8, 8, 8, 8);
            b.setTextColor(0xFF000000);
            b.setTextSize(40);
        } else {
            b = (Button) convertView;
        }
        //set actions for each button to open new pages
        if(position == 0)
        {
        	b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	Toast.makeText(mContext, "SPAGHETTI", Toast.LENGTH_SHORT).show();
                	//route
                	RouteClicked(mContext);
                }
            });
        }
        else if(position == 1)
        {
        	b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	Toast.makeText(mContext, "SPAGHETTI", Toast.LENGTH_SHORT).show();
                	 //social
                	SocialClicked(mContext);
                }
            });
        }
        else if(position == 2)
        {
        	b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	Toast.makeText(mContext, "SPAGHETTI", Toast.LENGTH_SHORT).show();
                	 //fitness
                	FitnessClicked(mContext);
                }
            });
        }
        else
        {
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                 	Toast.makeText(mContext, "SPAGHETTI", Toast.LENGTH_SHORT).show();
                 	 //maintenance
                 	MaintenanceClicked(mContext);
                }
            });
        }
        //set button attributes
        b.setBackgroundColor(menuColors[position]);
        b.setText(menuStrings[position]);
    	
    	return b;
    }

    //methods called to open new pages
    public void RouteClicked(Context context){
    	Intent intentRoute = new Intent(mContext, RouteActivity.class);
    	context.startActivity(intentRoute);
    }
    public void SocialClicked(Context context){
    	Intent intentSocial = new Intent(mContext, SocialActivity.class);
    	context.startActivity(intentSocial);
    }
    public void FitnessClicked(Context context){
    	Intent intentFitness = new Intent(mContext, FitnessActivity.class);
    	context.startActivity(intentFitness);
    }
    public void MaintenanceClicked(Context context){
    	Intent intentMaintenance = new Intent(mContext, MaintenanceActivity.class);
    	context.startActivity(intentMaintenance);
    }
    //arrays to hold colors and text for buttons
    private String[] menuStrings = {
    		"Route", "Social", "Fitness", "Maintenance"
    };
    private int[] menuColors = {
    		//0xFF3DCED3, 0xFF1C31ED, 0xFF36BC3D, 0xFF1CC1EA
    		0xFFFFFF00, 0xFFFF7F00, 0xFFA020F0, 0xFF7FFF00
    };
}