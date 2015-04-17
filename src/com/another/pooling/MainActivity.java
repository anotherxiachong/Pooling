package com.another.pooling;

import java.util.ArrayList;
import java.util.List;

import com.example.testpic.PublishedActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenuItem;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private SlidingMenu mLeftMenu ; 
	private TextView onOff;
	boolean isExit;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
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
				case 6:
					Intent intent6 = new Intent(MainActivity.this, NearResultActivity.class);
					startActivity(intent6);
					break;
					
				case 5:
					Intent intent5 = new Intent(MainActivity.this, PublishedActivity.class);
					startActivity(intent5);
					break;

				default:
					break;
				}
			}
		});
        /**
        onOff = (TextView) findViewById(R.id.activity_selectimg_switch);
        onOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = "";
				content = onOff.getText().toString();
				Intent intent = new Intent(MainActivity.this, NearResultActivity.class);
				if(content.equals("线上")) {
					onOff.setText("线下");
					startActivity(intent);
				} else {
					onOff.setText("线上");
					startActivity(intent);
				}
			}
		});
		*/
	}
	
	public void toggleMenu(View view)
	{
		mLeftMenu.toggle();
	}
	
	public void EnterBillPooling(View view) {
		Intent intent = new Intent(this, BillPoolingActivity.class);
		startActivity(intent);
	}
	
	public void EnterSetting(View view) {
	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {  
			exit();  
			return false;  
        } else {  
            return super.onKeyDown(keyCode, event);  
        } 
	}
	
	public void exit(){  
        if (!isExit) {  
            isExit = true;  
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();  
            mHandler.sendEmptyMessageDelayed(0, 2000);  
        } else {  
            finish(); 
            System.exit(0);  
        }  
    }  
	
	Handler mHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            // TODO Auto-generated method stub   
            super.handleMessage(msg);  
            isExit = false;  
        }  
    };  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
