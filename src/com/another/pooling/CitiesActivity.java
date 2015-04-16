package com.another.pooling;


import java.util.List;





import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 
 * @author zhy
 * 
 */
public class CitiesActivity extends Activity implements OnWheelChangedListener
{
	/**
	 * 省的WheelView控件
	 */
	private WheelView mProvince;
	/**
	 * 市的WheelView控件
	 */
	private WheelView mCity;
	/**
	 * 区的WheelView控件
	 */
	private WheelView mArea;
	/**
	 * 街道的WheelView控件
	 */
	private WheelView mStreet;

	/**
	 * 所有省
	 */
	private String[] mProvinceDatas = {""};
	private String[] mCitiesDatas= {""};
	private String[] mAreaDatas= {""};
	private String[] mStreetDatas= {""};
	/**
	 * key - 省 value - 市s
	 */
	//private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区s
	 */
	//private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 区 values - 街道s
	 */
	//private Map<String, String[]> mStreetDatasMap = new HashMap<String, String[]>();

	/**
	 * 当前省的名称
	 */
	private String mCurrentProviceName="";
	/**
	 * 当前市的名称
	 */
	private String mCurrentCityName="";
	/**
	 * 当前区的名称
	 */
	private String mCurrentAreaName ="";
	/**
	 * 当前街道的名称
	 */
	private String mCurrentStreetName ="";
	
	private String classes;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citys);
		
		Bmob.initialize(this, "dc417cd048f5197ba699440c13977f34");
		
		classes = getIntent().getExtras().getString("classes");

		//initJsonData();

		mProvince = (WheelView) findViewById(R.id.id_province);
		mCity = (WheelView) findViewById(R.id.id_city);
		mArea = (WheelView) findViewById(R.id.id_area);
		mStreet = (WheelView) findViewById(R.id.id_street);
		
		

		//initDatas();
		
		initProvince();

		mProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
	
		// 添加change事件
		mProvince.addChangingListener(this);
		// 添加change事件
		mCity.addChangingListener(this);
		// 添加change事件
		mArea.addChangingListener(this);
		// 添加change事件
		mStreet.addChangingListener(this);

		mProvince.setVisibleItems(5);
		mCity.setVisibleItems(5);
		mArea.setVisibleItems(5);
		mStreet.setVisibleItems(5);
		
		updateCities();
		mCity.setViewAdapter(new ArrayWheelAdapter<String>(this, mCitiesDatas));
		updateAreas();
		mArea.setViewAdapter(new ArrayWheelAdapter<String>(this, mAreaDatas));
		updateStreets();
		mStreet.setViewAdapter(new ArrayWheelAdapter<String>(this, mStreetDatas));

	}
	
	private void initProvince() {
		BmobQuery<citys> query = new BmobQuery<citys>();
		query.addWhereEqualTo("NodeType", 1);
		query.setLimit(1000);
		query.findObjects(this, new FindListener<citys>() {
			
			@Override
			public void onSuccess(List<citys> arg0) {
				// TODO Auto-generated method stub
				String str = "";
				for(citys city : arg0) {
					str = str + city.getCITY() + " ";
				}
				Log.i("province", str);
				mProvinceDatas = str.trim().split(" ");
				mProvince.setViewAdapter(new ArrayWheelAdapter<String>(CitiesActivity.this, str.trim().split(" ")));
				mProvince.setCurrentItem(0);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	

	private void updateStreets() {
		// TODO Auto-generated method stub
		int pCurrent = mArea.getCurrentItem();
		mCurrentAreaName = mAreaDatas[pCurrent];
		BmobQuery<citys> query = new BmobQuery<citys>();
		query.addWhereEqualTo("CITY", mCurrentAreaName);
		query.addWhereEqualTo("NodeType", 3);
		query.setLimit(1000);
		query.findObjects(this, new FindListener<citys>() {
			
			@Override
			public void onSuccess(List<citys> arg0) {
				// TODO Auto-generated method stub
				int id = 0;
				for(citys city : arg0) {
					id = city.getID();
				}
				
				BmobQuery<citys> queryin = new BmobQuery<citys>();
				queryin.addWhereEqualTo("ParentID", id);
				queryin.addWhereEqualTo("NodeType", 4);
				queryin.setLimit(1000);
				queryin.findObjects(CitiesActivity.this, new FindListener<citys>() {
					
					@Override
					public void onSuccess(List<citys> arg0) {
						// TODO Auto-generated method stub
						String str = "";
						for(citys city : arg0) {
							str = str + city.getCITY() + " ";
						}
						mStreetDatas = str.trim().split(" ");
						mStreet.setViewAdapter(new ArrayWheelAdapter<String>(CitiesActivity.this, str.trim().split(" ")));
						mStreet.setCurrentItem(0);
						updateResult();
					}
					
					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		if (mStreetDatas == null)
		{
			mStreetDatas = new String[] { "" };
		}
		
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas()
	{
		int pCurrent = mCity.getCurrentItem();
		/*mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mAreaDatasMap.get(mCurrentCityName);

		if (areas == null)
		{
			areas = new String[] { "" };
		}*/
		mCurrentCityName = mCitiesDatas[pCurrent];
		BmobQuery<citys> query = new BmobQuery<citys>();
		query.addWhereEqualTo("CITY", mCurrentCityName);
		query.addWhereEqualTo("NodeType", 2);
		query.setLimit(1000);
		query.findObjects(this, new FindListener<citys>() {
			
			@Override
			public void onSuccess(List<citys> arg0) {
				// TODO Auto-generated method stub
				int id = 0;
				for(citys city : arg0) {
					id = city.getID();
				}
				
				BmobQuery<citys> queryin = new BmobQuery<citys>();
				queryin.addWhereEqualTo("ParentID", id);
				queryin.addWhereEqualTo("NodeType", 3);
				queryin.setLimit(1000);
				queryin.findObjects(CitiesActivity.this, new FindListener<citys>() {
					
					@Override
					public void onSuccess(List<citys> arg0) {
						// TODO Auto-generated method stub
						String str = "";
						for(citys city : arg0) {
							str = str + city.getCITY() + " ";
						}
						mAreaDatas = str.trim().split(" ");
						mArea.setViewAdapter(new ArrayWheelAdapter<String>(CitiesActivity.this,str.trim().split(" ")));
						mArea.setCurrentItem(0);
						updateStreets();
						
					}
					
					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		if (mAreaDatas == null)
		{
			mAreaDatas = new String[] { "" };
		}
		
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities()
	{
		int pCurrent = mProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		/*String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null)
		{
			cities = new String[] { "" };
		}*/
		BmobQuery<citys> query = new BmobQuery<citys>();
		query.addWhereEqualTo("ParentID", pCurrent+1);
		query.setLimit(1000);
		query.findObjects(this, new FindListener<citys>() {
			
			@Override
			public void onSuccess(List<citys> arg0) {
				// TODO Auto-generated method stub
				String str = "";
				for(citys city : arg0) {
					str = str + city.getCITY() + " ";
				}
				mCitiesDatas = str.trim().split(" ");
				mCity.setViewAdapter(new ArrayWheelAdapter<String>(CitiesActivity.this, str.trim().split(" ")));
				mCity.setCurrentItem(0);
				updateAreas();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		if (mCitiesDatas == null)
		{
			mCitiesDatas = new String[] { "" };
		}
		
	}

	/**
	 * 解析整个Json对象，完成后释放Json对象的内存
	 */
	

	

	/**
	 * change事件的处理
	 */
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue)
	{
		if (wheel == mProvince)
		{
			updateCities();
		} else if (wheel == mCity)
		{
			updateAreas();
		} else if (wheel == mArea)
		{
			updateStreets();
		}else if (wheel == mStreet)
		{
			updateResult();
		}
	}

	private void updateResult() {
		// TODO Auto-generated method stub
		int pCurrent = mStreet.getCurrentItem();
		mCurrentStreetName = mStreetDatas[pCurrent];
		TextView demo = (TextView) findViewById(R.id.guide);
		demo.setText(mCurrentProviceName + mCurrentCityName + mCurrentAreaName + mCurrentStreetName);
	}

	public void sure(View view)
	{
		if(classes.equals("post")) {
			Intent intent = new Intent(CitiesActivity.this, BillInfoActivity.class);
			Bundle address = new Bundle();
			address.putString("address", mCurrentProviceName + mCurrentCityName + mCurrentAreaName + mCurrentStreetName);
			intent.putExtras(address);
			setResult(3, intent);
			finish();
		} else if(classes.equals("search")) {
			Intent intent = new Intent(CitiesActivity.this, SearchResultActivity.class);
			Bundle address = new Bundle();
			address.putString("address", mCurrentProviceName + mCurrentCityName + mCurrentAreaName + mCurrentStreetName);
			intent.putExtras(address);
			startActivity(intent);
		}
		//Toast.makeText(this, mCurrentProviceName + mCurrentCityName + mCurrentAreaName + mCurrentStreetName, 1).show();
	}
}
