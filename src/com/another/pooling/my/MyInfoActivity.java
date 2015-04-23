package com.another.pooling.my;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenuItem;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.another.pooling.*;
import com.another.pooling.offline.BillPoolingActivityOffLine;
import com.example.testpic.PublishedActivity;
public class MyInfoActivity extends Activity {
	
	private SlidingMenu mLeftMenu; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		
SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
		
		mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
		
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
				switch (id) {
				case 1:
					Intent intent1 = new Intent(MyInfoActivity.this, MyFollowResultActivity.class);
					startActivity(intent1);
					break;
					
				case 5:
					Intent intent5 = new Intent(MyInfoActivity.this, MyPublishedResultActivity.class);
					startActivity(intent5);
					break;
					
				case 2:
					Intent intent2  = new Intent(MyInfoActivity.this, MyTelActivity.class);
					startActivity(intent2);
					break;

				default:
					break;
				}
			}
		});
	}
	
	public void toggleMenu(View view)
	{
		mLeftMenu.toggle();
	}
	
	public void EnterBillPoolingOnLine(View view) {
		Intent intent = new Intent(this, BillPoolingActivity.class);
		startActivity(intent);
	}
	
	public void EnterBillPoolingOffLine(View view) {
		Intent intent = new Intent(this, BillPoolingActivityOffLine.class);
		startActivity(intent);
	}
	
	public void EnterMyInfo(View view) {
		Intent intent = new Intent(this, MyInfoActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_info, menu);
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
