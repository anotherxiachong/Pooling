package com.another.pooling;

import java.util.concurrent.CountDownLatch;

import android.support.v7.app.ActionBarActivity;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {
	
	private SlidingMenu mLeftMenu ; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
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
