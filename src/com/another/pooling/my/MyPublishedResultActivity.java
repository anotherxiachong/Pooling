package com.another.pooling.my;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;



import com.hulefei.android.MySimpleAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.another.pooling.*;

public class MyPublishedResultActivity extends Activity {
	//private int user_icon[] = null;
	private String username[] = null;
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
	
		getData();
	}

	
	private void getData(){
		// TODO Auto-generated method stub
		BmobQuery<BillInfo> bmobQuery = new BmobQuery<BillInfo>();
		BmobUser bmobUser = BmobUser.getCurrentUser(this);
		String user = bmobUser.getUsername();
		bmobQuery.addWhereEqualTo("username", user);
		bmobQuery.setLimit(100);    //获取最接近用户地点的100条数据
		bmobQuery.findObjects(MyPublishedResultActivity.this, new FindListener<BillInfo>() {
		    @Override
		    public void onSuccess(List<BillInfo> object) {
		        // TODO Auto-generated method stub
		        //Toast.makeText(NearResultActivity.this, "查询成功：共" + object.size() + "条数据。"/*  + mPosition.getLatitude() + " " + mPosition.getLongitude()*/, Toast.LENGTH_LONG).show();
		    	if(object.size() == 0) {
		    		Toast.makeText(MyPublishedResultActivity.this, "没有找到哦~快去发起拼单吧~", Toast.LENGTH_LONG).show();
		    		MyPublishedResultActivity.this.finish();
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
		    	Toast.makeText(MyPublishedResultActivity.this, "查询失败。+" + msg, Toast.LENGTH_LONG).show();
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
			Intent intent = new Intent(MyPublishedResultActivity.this, DetailResultActivity.class);
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
				map.put("user_icon", image_uri[x]/*String.valueOf(R.drawable.pooling_ic*/); // 与data_list.xml中的TextView组加匹配
				map.put("username", this.username[x]); // 与data_list.xml中的TextView组加匹配
				map.put("no", this.no[x]);
				map.put("image", image_uri[x]/*image_uri*/); // 与data_list.xml中的TextView组加匹配
				map.put("pre_describe", this.pre_desString[x]);
				mylist.add(map); // 保存了所有的数据行
			} 
			return mylist;
		}
}


