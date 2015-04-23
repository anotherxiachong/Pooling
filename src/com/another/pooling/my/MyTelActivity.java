package com.another.pooling.my;

import android.app.Activity;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.another.pooling.*;
import com.another.pooling.offline.PublishedActivityOffLine;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyTelActivity extends Activity {
	private EditText mytelnum;
	private Button save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tel);
		
		mytelnum = (EditText) findViewById(R.id.mytel_et);
		save = (Button) findViewById(R.id.mytel_bt);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String num = mytelnum.getText().toString();
				BmobUser bmobUser = BmobUser.getCurrentUser(MyTelActivity.this);
				String username = bmobUser.getUsername();
				Tel tel = new Tel();
				tel.setTel(num);
				tel.setUsername(username);
				tel.save(MyTelActivity.this, new SaveListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(MyTelActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(MyTelActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_tel, menu);
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
