package com.another.pooling;


import java.io.IOException;
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


import com.hulefei.android.MySimpleAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class NearResultActivity extends Activity  implements AMapLocationListener {
	private int user_icon[] = null;
	private String username[] = null;
	private String image[] = null;
	private String image_uri[] = null;
	private List<String> uri;
	private String pre_desString[] = null;
	private String no[] = null;
	private String string_dec;
	private String string_username;
	private String string_no;
	private String string_file_name;
	private int length = 0;

	private ListView datalist = null; // 定义ListView组件
	
	LocationManagerProxy mLocationManagerProxy;
	private double longitude;
	private double latitude;
	private BmobGeoPoint mPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_result);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
        .detectDiskReads().detectDiskWrites().detectNetwork()  
        .penaltyLog().build());  
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
        .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()  
        .penaltyLog().penaltyDeath().build());  
		Bmob.initialize(this, "dc417cd048f5197ba699440c13977f34");
		mPosition = new BmobGeoPoint();
		
		getPosition();
		
	}
	
	private void getPosition() {
		// TODO Auto-generated method stub
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
	    mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork,-1, 15, this);
		
	}
	
	private void getData(){
		// TODO Auto-generated method stub
		BmobQuery<BillInfo> bmobQuery = new BmobQuery<BillInfo>();
		bmobQuery.addWhereNear("position", mPosition);
		bmobQuery.setLimit(100);    //获取最接近用户地点的10条数据
		bmobQuery.findObjects(NearResultActivity.this, new FindListener<BillInfo>() {
		    @Override
		    public void onSuccess(List<BillInfo> object) {
		        // TODO Auto-generated method stub
		        //Toast.makeText(NearResultActivity.this, "查询成功：共" + object.size() + "条数据。"/*  + mPosition.getLatitude() + " " + mPosition.getLongitude()*/, Toast.LENGTH_LONG).show();
		    	if(object.size() == 0) {
		    		Toast.makeText(NearResultActivity.this, "没有找到哦~快去发起拼单吧~", Toast.LENGTH_LONG).show();
		    		NearResultActivity.this.finish();
		    	}
		    	string_dec= "";
		    	string_username="";
		    	string_no="";
		    	string_file_name="";
		    	uri = new ArrayList<String>();
		    	//myApplication.configPath(NearResultActivity.this);
		    	for(BillInfo billInfo : object) {
		    		string_username = string_username + billInfo.getUsername() + " ";
		    		string_no = string_no + billInfo.getObjectId() + " ";
		    		uri.add(billInfo.getImgfilename()[0]);
		    		if(billInfo.getDescribe().equals("")) {
		    			string_dec= string_dec + "暂无" + " ";
		    		} else {
		    			string_dec= string_dec + billInfo.getDescribe() + " ";
		    		}
		    	}
		    	Log.e("url", string_file_name);
		    	username = string_username.trim().split(" ");
		    	no = string_no.trim().split(" ");
		    	pre_desString = string_dec.trim().split(" ");
		    	image_uri = uri.toArray(new String[uri.size()]);
		    	length = username.length;
		    	//Log.i("username", length+"");
		    	try {
					initView();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    @Override
		    public void onError(int code, String msg) {
		        // TODO Auto-generated method stub
		    	Toast.makeText(NearResultActivity.this, "查询失败。+" + msg, Toast.LENGTH_LONG).show();
		    }
		});
	}

	
	private void initView() throws IOException {
		this.datalist = (ListView) super.findViewById(R.id.datalist); // 取得组件
		ArrayList<HashMap<String, Object>> mylist = buildList(); 
		MySimpleAdapter mySimpleAdapter = new MySimpleAdapter(this, mylist,
				R.layout.bill_info_layout, new String[] { "user_icon", "username", "no", "image",
						"pre_describe"} // Map中的key的名称
				, new int[] { R.id.user_icon, R.id.username, R.id.no, R.id.image, R.id.pre_describe }); // 是data_list.xml中定义的组件的资源ID
	
		this.datalist.setAdapter(mySimpleAdapter);
		datalist.setOnItemClickListener(new OnItemClickListener(){  
			@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
			ListView listView = (ListView)parent;  
			@SuppressWarnings("unchecked")
			HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);  
			String no = map.get("no");
			//Toast.makeText(SQLiteCRUDActivity.this, userid +" , "+ name +" , "+ age ,Toast.LENGTH_LONG).show(); 
			Intent intent = new Intent(NearResultActivity.this, DetailResultActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("objectId", no);
			intent.putExtras(bundle);
			startActivity(intent);      
		}  
	});  
	}
	
	 private ArrayList<HashMap<String, Object>> buildList() {
	    	
	    	ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>(); 
			//添加list内容
	    	for (int x = 0; x < length; x++) {
				HashMap<String, Object> map = new HashMap<String, Object>(); // 定义Map集合，保存每一行数据
				map.put("user_icon",  "http://file.bmob.cn/M00/45/6D/oYYBAFU2RoaAJzVfAAB7kPLEw1U56.JPEG"/*String.valueOf(R.drawable.pooling_ic*/); // 与data_list.xml中的TextView组加匹配
				map.put("username", this.username[x]); // 与data_list.xml中的TextView组加匹配
				map.put("no", this.no[x]);
				map.put("image",  "http://file.bmob.cn/M00/45/6D/oYYBAFU2RoaAJzVfAAB7kPLEw1U56.JPEG"/*image_uri*/); // 与data_list.xml中的TextView组加匹配
				map.put("pre_describe", this.pre_desString[x]);
				mylist.add(map); // 保存了所有的数据行
			} 
			return mylist;
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


