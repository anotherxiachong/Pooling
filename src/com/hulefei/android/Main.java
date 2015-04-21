package com.hulefei.android;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView list = (ListView) findViewById(R.id.listviewimg01); 
		
		ArrayList<HashMap<String, Object>> mylist = buildList(); 
		MySimpleAdapter mSchedule = new MySimpleAdapter(this, //没什么解释  
	            mylist,//数据来源   
	            R.layout.item,//ListItem的XML实现  
	            //动态数组与ListItem对应的子项          
	            new String[] {"title","imgsrc"},   
	            //ListItem的XML文件里面的两个TextView ID  
	            new int[] {R.id.listview_item_textview01,R.id.listview_item_imageview01});
		
	    list.setAdapter(mSchedule);  */
    }
    
    /**
     * 装载数据
     * @return
     */
    private ArrayList<HashMap<String, Object>> buildList() {
    	
    	ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>(); 
		//添加list内容
    		HashMap<String, Object> map = new HashMap<String, Object>();
    		map.put("title", "Hello World0");  
    		map.put("imgsrc", "http://www.baidu.com/img/baidu_logo_jr_1003_315.gif");
    		mylist.add(map); 
		
    		HashMap<String, Object> map1 = new HashMap<String, Object>();
    		map1.put("title", "Hello World1");  
    		map1.put("imgsrc", "http://www.google.cn/intl/zh-CN/images/logo_cn.gif");
    		mylist.add(map1); 
		return mylist;
	}
}