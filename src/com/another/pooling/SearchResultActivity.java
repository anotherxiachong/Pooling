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


public class SearchResultActivity extends Activity {
	private int user_icon[] = null;
	private String username[] = null;
	private int image[] = null;
	private String pre_desString[] = null;
	private String no[] = null;
	private String string_dec;
	private String string_username;
	private String string_no;
	private int length = 0;

	private ListView datalist = null; // 定义ListView组件
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // 定义显示的内容包装
	private SimpleAdapter simpleAdapter = null; // 进行数据的转换操作
	
	
	private String address;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_result);
		
		Bmob.initialize(this, "dc417cd048f5197ba699440c13977f34");
		
		address = getIntent().getExtras().getString("address");
		
		getData();
	}
	
	private void getData() {
		// TODO Auto-generated method stub
		BmobQuery<BillInfo> bmobQuery = new BmobQuery<BillInfo>();
		bmobQuery.addWhereEqualTo("address", address);
		bmobQuery.setLimit(100);    //获取最接近用户地点的10条数据
		bmobQuery.findObjects(SearchResultActivity.this, new FindListener<BillInfo>() {
		    @Override
		    public void onSuccess(List<BillInfo> object) {
		        // TODO Auto-generated method stub
		        //Toast.makeText(NearActivity.this, "查询成功：共" + object.size() + "条数据。"/*  + mPosition.getLatitude() + " " + mPosition.getLongitude()*/, Toast.LENGTH_LONG).show();
		    	string_dec= "";
		    	string_username="";
		    	string_no="";
		    	for(BillInfo billInfo : object) {
		    		string_username = string_username + billInfo.getUsername() + " ";
		    		string_no = string_no + billInfo.getObjectId() + " ";
		    		if(billInfo.getDescribe().equals("")) {
		    			string_dec= string_dec + "暂无" + " ";
		    		} else {
		    			string_dec= string_dec + billInfo.getDescribe() + " ";
		    		}
		    	}
		    	username = string_username.trim().split(" ");
		    	no = string_no.trim().split(" ");
		    	pre_desString = string_dec.trim().split(" ");
		    	length = username.length;
		    	//Log.i("username", length+"");
		    	initView();
		    }
		    @Override
		    public void onError(int code, String msg) {
		        // TODO Auto-generated method stub
		    	Toast.makeText(SearchResultActivity.this, "查询失败。+" + msg, Toast.LENGTH_LONG).show();
		    }
		});
	}

	datalist.setOnItemClickListener(new OnItemClickListener(){  

		@SuppressWarnings("unchecked")  
		@Override  

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
			ListView listView = (ListView)parent;  
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
	
	
	private void initView() {
		this.datalist = (ListView) super.findViewById(R.id.datalist); // 取得组件
		for (int x = 0; x < length; x++) {
			Map<String, String> map = new HashMap<String, String>(); // 定义Map集合，保存每一行数据
			map.put("user_icon", String.valueOf(R.drawable.ic_launcher)); // 与data_list.xml中的TextView组加匹配
			map.put("username", this.username[x]); // 与data_list.xml中的TextView组加匹配
			map.put("no", this.no[x]);
			map.put("image", String.valueOf(R.drawable.guide_image1)); // 与data_list.xml中的TextView组加匹配
			map.put("pre_describe", this.pre_desString[x]);
			this.list.add(map); // 保存了所有的数据行
		} 
		this.simpleAdapter = new SimpleAdapter(this, this.list,
				R.layout.bill_info_layout, new String[] { "user_icon", "username", "no", "image",
						"pre_describe"} // Map中的key的名称
				, new int[] { R.id.user_icon, R.id.username, R.id.no, R.id.image, R.id.pre_describe }); // 是data_list.xml中定义的组件的资源ID
		this.datalist.setAdapter(this.simpleAdapter);
	}
}


	


