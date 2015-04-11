package com.another.pooling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.R.integer;
import android.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class GuideActivity extends Activity{
	
	private View view1, view2, view3, view4;
	private ViewPager mViewPager;
	private List<View> mImages; 
	private Button mButton;
	private Intent intent;
	private ViewPagerAdapter vpAdapter;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		initView();
	}
	
	private void initView() {
		mViewPager = (ViewPager)findViewById(R.id.viewpager);
		
		view1 = findViewById(R.layout.activity_guide_1);
		view2 = findViewById(R.layout.activity_guide_2);
		view3 = findViewById(R.layout.activity_guide_3);
		view4 = findViewById(R.layout.activity_guide_last);
		
		LayoutInflater lf = getLayoutInflater().from(this);  
		view1 = lf.inflate(R.layout.activity_guide_1,  null);
		view2 = lf.inflate(R.layout.activity_guide_2,  null);
		view3 = lf.inflate(R.layout.activity_guide_3,  null);
		view4 = lf.inflate(R.layout.activity_guide_last,  null);
		//mButton = (Button)view4. findViewById(R.id.enter);
		//mButton.setOnClickListener(mClickListener);
		
		mImages = new ArrayList<View>();
		mImages.add(view1);
		mImages.add(view2);
		mImages.add(view3);
		mImages.add(view4);
		
		//为ViewPager添加切换动画效果(3.0以上)
		mViewPager.setPageTransformer(true, new DepthPageTransformer());
		vpAdapter = new ViewPagerAdapter(mImages, this);
		mViewPager.setAdapter(vpAdapter);
	}
	
	PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public Object instantiateItem(View container, int position) {
				// TODO Auto-generated method stub
				
				((ViewGroup) container).addView(mImages.get(position));
				
				return mImages.get(position);
			}
			
			@Override
			public void destroyItem(View container, int position, Object object) {
				// TODO Auto-generated method stub
				((ViewGroup) container).removeView(mImages.get(position));
			}
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mImages.size();
			}
	};
	
	OnClickListener mClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};

	
}
