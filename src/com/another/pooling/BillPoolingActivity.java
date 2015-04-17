package com.another.pooling;

import java.util.ArrayList;
import java.util.List;

import com.example.testpic.PublishedActivity;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenuItem;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


public class BillPoolingActivity extends Activity {
	
	private SlidingMenu mLeftMenu ; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		 SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
		WindowManager wm = this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		
		SatelliteMenu smenu = (SatelliteMenu) findViewById(R.id.menu);
		LinearLayout.LayoutParams params = (LayoutParams) smenu.getLayoutParams();
		params.topMargin = height - 1200;
		smenu.setLayoutParams(params);
		
		List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(6, R.drawable.ic_1));
        items.add(new SatelliteMenuItem(5, R.drawable.ic_3));
        items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
        items.add(new SatelliteMenuItem(3, R.drawable.ic_5));
        items.add(new SatelliteMenuItem(2, R.drawable.ic_6));
        items.add(new SatelliteMenuItem(1, R.drawable.ic_2));
//        items.add(new SatelliteMenuItem(5, R.drawable.sat_item));
        menu.addItems(items);        
        
        menu.setOnItemClickedListener(new SateliteClickedListener() {
			
			public void eventOccured(int id) {
				Log.i("sat", "Clicked on " + id);
			}
		});
	}
	
	public void toggleMenu(View view)
	{
		mLeftMenu.toggle();
	}
	
	public void post(View view) {
		Intent intent = new Intent(this, PublishedActivity.class);
		//Bundle address = new Bundle();
		//address.putString("address", "");
		//intent.putExtras(address);
		startActivity(intent);
		//Intent intent = new Intent(this, PublishedActivity.class);
		//startActivityForResult(intent, RESULT_OK);
	}
	
	public void near(View view) {
		Intent intent = new Intent(this, NearResultActivity.class);
		startActivity(intent);
	}
	
	public void search(View view) {
		Intent intent  = new Intent(this, CitiesActivity.class);
		Bundle classes = new Bundle();
		classes.putString("classes", "search");
		intent.putExtras(classes);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bill_pooling, menu);
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
