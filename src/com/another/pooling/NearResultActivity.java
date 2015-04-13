package com.another.pooling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class NearResultActivity extends Activity  implements AMapLocationListener {
	private int user_icon[] = null;
	private String username[] = null;
	private int image[] = null;
	private String pre_desString[] = null;
	private String string_dec;
	private String string_username;
	private int length = 0;

	private ListView datalist = null; // 定义ListView组件
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // 定义显示的内容包装
	private SimpleAdapter simpleAdapter = null; // 进行数据的转换操作
	
	LocationManagerProxy mLocationManagerProxy;
	private double longitude;
	private double latitude;
	private BmobGeoPoint mPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_result);
		
		Bmob.initialize(this, "dc417cd048f5197ba699440c13977f34");
		mPosition = new BmobGeoPoint();
		
		getPosition();
		
	}
	
	private void getPosition() {
		// TODO Auto-generated method stub
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
	    mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork,-1, 15, this);
		
	}
	
	private void getData() {
		// TODO Auto-generated method stub
		BmobQuery<BillInfo> bmobQuery = new BmobQuery<BillInfo>();
		bmobQuery.addWhereNear("position", mPosition);
		bmobQuery.setLimit(100);    //获取最接近用户地点的10条数据
		bmobQuery.findObjects(NearResultActivity.this, new FindListener<BillInfo>() {
		    @Override
		    public void onSuccess(List<BillInfo> object) {
		        // TODO Auto-generated method stub
		        //Toast.makeText(NearActivity.this, "查询成功：共" + object.size() + "条数据。"/*  + mPosition.getLatitude() + " " + mPosition.getLongitude()*/, Toast.LENGTH_LONG).show();
		    	string_dec= "";
		    	string_username="";
		    	for(BillInfo billInfo : object) {
		    		string_username = string_username + billInfo.getUsername() + " ";
		    		if(billInfo.getDescribe().equals("")) {
		    			string_dec= string_dec + "暂无" + " ";
		    		} else {
		    			string_dec= string_dec + billInfo.getDescribe() + " ";
		    		}
		    	}
		    	username = string_username.trim().split(" ");
		    	pre_desString = string_dec.trim().split(" ");
		    	length = username.length;
		    	Log.i("username", length+"");
		    	initView();
		    }
		    @Override
		    public void onError(int code, String msg) {
		        // TODO Auto-generated method stub
		    	Toast.makeText(NearResultActivity.this, "查询失败。+" + msg, Toast.LENGTH_LONG).show();
		    }
		});
	}
	
	
	private void initView() {
		this.datalist = (ListView) super.findViewById(R.id.datalist); // 取得组件
		for (int x = 0; x < length; x++) {
			Map<String, String> map = new HashMap<String, String>(); // 定义Map集合，保存每一行数据
			map.put("user_icon", String.valueOf(R.drawable.ic_launcher)); // 与data_list.xml中的TextView组加匹配
			map.put("username", this.username[x]); // 与data_list.xml中的TextView组加匹配
			map.put("image", String.valueOf(R.drawable.guide_image1)); // 与data_list.xml中的TextView组加匹配
			map.put("pre_describe", this.pre_desString[x]);
			this.list.add(map); // 保存了所有的数据行
		} 
		this.simpleAdapter = new SimpleAdapter(this, this.list,
				R.layout.bill_info_layout, new String[] { "user_icon", "username", "image",
						"pre_describe"} // Map中的key的名称
				, new int[] { R.id.user_icon, R.id.username, R.id.image, R.id.pre_describe }); // 是data_list.xml中定义的组件的资源ID
		this.datalist.setAdapter(this.simpleAdapter);
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
			mPosition.setLatitude(latitude);
			mPosition.setLongitude(longitude);
			//Toast.makeText(NearActivity.this, latitude + " " + longitude, Toast.LENGTH_LONG).show();
			getData();
			//Log.e("position", arg0.getLatitude() + " " + arg0.getLongitude());
		}
	}
}


