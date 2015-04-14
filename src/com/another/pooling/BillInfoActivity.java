package com.another.pooling;



import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import android.support.v7.app.ActionBarActivity;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class BillInfoActivity extends Activity implements AMapLocationListener {
	
	private EditText decribe;
	private EditText deadline;
	private double longitude;
	private double latitude;
	private BmobGeoPoint mBmobGeoPoint;
	private EditText link;
	private EditText address;
	private EditText detailaddress;
	
	private String temp;  
	


	LocationManagerProxy mLocationManagerProxy;
	//private AMapLocation mAMapLocation;
	
	//String[] mProvinceDatas;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {  
            temp = savedInstanceState.getString("temp");  
        }  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bill_info);
		
		Bmob.initialize(this, "dc417cd048f5197ba699440c13977f34");
		
		decribe = (EditText) findViewById(R.id.describe);
		deadline = (EditText) findViewById(R.id.deadline);
		link = (EditText) findViewById(R.id.link);
		address = (EditText) findViewById(R.id.online_address);
		detailaddress = (EditText) findViewById(R.id.offline_address);
		
		
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
	    mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork,-1, 15, this);
	    
	    address.setText(BillInfoActivity.this.getIntent().getExtras().getString("address"));
		

	}
	
	public void input(View view) {
		Intent intent  = new Intent(this, CitiesActivity.class);
		Bundle classes = new Bundle();
		classes.putString("classes", "post");
		intent.putExtras(classes);
		startActivity(intent);
	}
	
	public void cancel(View view) {
		Intent intent  = new Intent(this, BillPoolingActivity.class);
		startActivity(intent);
	}
	
	

	public void post(View view) {
		BmobUser bmobUser = BmobUser.getCurrentUser(this);
		String username = bmobUser.getUsername();
		String descibeString = decribe.getText().toString();
		String deadlineString = deadline.getText().toString();
		String linkString = link.getText().toString();
		String addressString = address.getText().toString();
		String detailAddresString = detailaddress.getText().toString();
		mBmobGeoPoint = new BmobGeoPoint(longitude, latitude);
		
		BillInfo mBillInfo = new BillInfo();
		mBillInfo.setUsername(username);
		mBillInfo.setDescribe(descibeString);
		mBillInfo.setDeadline(deadlineString);
		mBillInfo.setLink(linkString);
		mBillInfo.setAddress(addressString);
		mBillInfo.setDetailaddress(detailAddresString);
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		temp = "xing";  
        System.out.println("onResume: temp = " + temp);  
        // 切换屏幕方向会导致activity的摧毁和重建  
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {  
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
        } 
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("temp", temp);
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
