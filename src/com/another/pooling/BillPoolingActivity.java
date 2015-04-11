package com.another.pooling;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class BillPoolingActivity extends FragmentActivity  implements OnClickListener{
	
	private SlidingMenu mLeftMenu ; 
	private LinearLayout mTabWeixin;
	private LinearLayout mTabFrd;
	private LinearLayout mTabAddress;
	private LinearLayout mTabSettings;

	private ImageButton mImgWeixin;
	private ImageButton mImgFrd;
	private ImageButton mImgAddress;
	private ImageButton mImgSettings;

	private Fragment mTab01;
	private Fragment mTab02;
	private Fragment mTab03;
	private Fragment mTab04;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bill_pooling);
		initView();
		initEvent();
		setSelect(0);
	}
	
	public void toggleMenu(View view)
	{
		mLeftMenu.toggle();
	}
	
	private void initEvent()
	{
		mTabWeixin.setOnClickListener(this);
		mTabFrd.setOnClickListener(this);
		mTabAddress.setOnClickListener(this);
		mTabSettings.setOnClickListener(this);
	}

	private void initView()
	{
		mTabWeixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
		mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
		mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
		mTabSettings = (LinearLayout) findViewById(R.id.id_tab_settings);

		mImgWeixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		mImgFrd = (ImageButton) findViewById(R.id.id_tab_frd_img);
		mImgAddress = (ImageButton) findViewById(R.id.id_tab_address_img);
		mImgSettings = (ImageButton) findViewById(R.id.id_tab_settings_img);
	}

	private void setSelect(int i)
	{
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
	
		switch (i)
		{
		case 0:
			if (mTab01 == null)
			{
				mTab01 = new WeixinFragment();
				transaction.add(R.id.id_content, mTab01);
			} else
			{
				transaction.show(mTab01);
			}
			mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
			break;
		case 1:
			if (mTab02 == null)
			{
				mTab02 = new FrdFragment();transaction.add(R.id.id_content, mTab02);
			} else
			{
				transaction.show(mTab02);
				
			}
			mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
			break;
		case 2:
			if (mTab03 == null)
			{
				mTab03 = new AddressFragment();
				transaction.add(R.id.id_content, mTab03);
			} else
			{
				transaction.show(mTab03);
			}
			mImgAddress.setImageResource(R.drawable.tab_address_pressed);
			break;
		case 3:
			if (mTab04 == null)
			{
				mTab04 = new SettingFragment();
				transaction.add(R.id.id_content, mTab04);
			} else
			{
				transaction.show(mTab04);
			}
			mImgSettings.setImageResource(R.drawable.tab_settings_pressed);
			break;

		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction)
	{
		if (mTab01 != null)
		{
			transaction.hide(mTab01);
		}
		if (mTab02 != null)
		{
			transaction.hide(mTab02);
		}
		if (mTab03 != null)
		{
			transaction.hide(mTab03);
		}
		if (mTab04 != null)
		{
			transaction.hide(mTab04);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bill_pooling, menu);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resetImgs();
		switch (v.getId())
		{
		case R.id.id_tab_weixin:
			setSelect(0);
			break;
		case R.id.id_tab_frd:
			setSelect(1);
			break;
		case R.id.id_tab_address:
			setSelect(2);
			break;
		case R.id.id_tab_settings:
			setSelect(3);
			break;

		default:
			break;
		}
	}
	
	private void resetImgs()
	{
		mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
		mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
		mImgAddress.setImageResource(R.drawable.tab_address_normal);
		mImgSettings.setImageResource(R.drawable.tab_settings_normal);
	}
}
