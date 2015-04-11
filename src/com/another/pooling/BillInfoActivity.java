package com.another.pooling;



import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.SaveListener;
import android.support.v7.app.ActionBarActivity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BillInfoActivity extends ActionBarActivity implements AMapLocationListener {
	
	private EditText decribe;
	private EditText deadline;
	private double longitude;
	private double latitude;
	private BmobGeoPoint mBmobGeoPoint;
	private EditText link;
	private EditText address;
	
	LocationManagerProxy mLocationManagerProxy;
	//private AMapLocation mAMapLocation;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_info);
		
		Bmob.initialize(this, "dc417cd048f5197ba699440c13977f34");
		
		decribe = (EditText) findViewById(R.id.describe);
		deadline = (EditText) findViewById(R.id.deadline);
		link = (EditText) findViewById(R.id.link);
		address = (EditText) findViewById(R.id.online_address);
		
		
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
	    mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork,-1, 15, this);
		

	}
	
	

	public void post(View view) {
		BmobUser bmobUser = BmobUser.getCurrentUser(this);
		String username = bmobUser.getUsername();
		String descibeString = decribe.getText().toString();
		String deadlineString = deadline.getText().toString();
		String linkString = link.getText().toString();
		String addressString = address.getText().toString();
		mBmobGeoPoint = new BmobGeoPoint(longitude, latitude);
		
		BillInfo mBillInfo = new BillInfo();
		mBillInfo.setUsername(username);
		mBillInfo.setDescribe(descibeString);
		mBillInfo.setDeadline(deadlineString);
		mBillInfo.setLink(linkString);
		mBillInfo.setAddress(addressString);
		mBillInfo.setPosition(mBmobGeoPoint);
		
		mBillInfo.save(BillInfoActivity.this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(BillInfoActivity.this, "submit success", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(BillInfoActivity.this, arg1, Toast.LENGTH_LONG).show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bill_info, menu);
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocationManagerProxy.destory();
	}



	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onLocationChanged(AMapLocation arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0.getAMapException().getErrorCode() == 0) {
			latitude = arg0.getLatitude();
			longitude = arg0.getLongitude();
			//Log.e("position", arg0.getLatitude() + " " + arg0.getLongitude());
		}
	}
	
	
}
