package com.example.testpic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.SaveListener;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.another.pooling.BillInfo;
import com.another.pooling.BillPoolingActivity;
import com.another.pooling.CitiesActivity;
import com.another.pooling.R;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadBatchListener;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PublishedActivity extends Activity implements AMapLocationListener
{

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private TextView activity_selectimg_send;
	private static final int CITY_SELECT = 1;
	private EditText decribe;
	private EditText deadline;
	private double longitude;
	private double latitude;
	private BmobGeoPoint mBmobGeoPoint;
	private EditText link;
	private EditText address;
	private EditText detailaddress;
	LocationManagerProxy mLocationManagerProxy;
	private String[] savefilename, savefileuri;
	

	private static String[] names =null;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectimg);
		Bmob.initialize(this, "dc417cd048f5197ba699440c13977f34");
		decribe = (EditText) findViewById(R.id.describe);
		deadline = (EditText) findViewById(R.id.deadline);
		link = (EditText) findViewById(R.id.link);
		address = (EditText) findViewById(R.id.address);
		detailaddress = (EditText) findViewById(R.id.detail_address);
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
	    mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork,-1, 15, this);
		Init();
	}
	
	public void input(View view) {
		Intent intent  = new Intent(this, CitiesActivity.class);
		Bundle classes = new Bundle();
		classes.putString("classes", "post");
		intent.putExtras(classes);
		startActivityForResult(intent, CITY_SELECT);
	}
	
	public void cancel(View view) {
		Intent intent  = new Intent(this, BillPoolingActivity.class);
		startActivity(intent);
	}
	
	public void post() {
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
		
		mBillInfo.addAll("imgfilename", Arrays.asList(savefilename));
		
		mBillInfo.save(PublishedActivity.this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(PublishedActivity.this, "发布成功", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(PublishedActivity.this, "请检查网络", Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	public void getURL() {
		if(savefilename.length == 0) {
			return;
		} else {
			for(int i = 0; i < savefilename.length; i++) {
				savefilename[i] = "http://newfile.codenow.cn:8080/" + BmobProFile.getInstance(PublishedActivity.this).signURL(null, 
						savefilename[i], "404fbf032078806733460a0b0cd4e6ab", 0, savefileuri[i]);
			}
		}
	}

	public void Init()	
	{
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT)); //点击时背景色设置为透明
		adapter = new GridAdapter(this);
		adapter.update1();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				if (arg2 == Bimp.bmp.size())
				{
					new PopupWindows(PublishedActivity.this, noScrollgridview);
				} else
				{
					Intent intent = new Intent(PublishedActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
		activity_selectimg_send.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < Bimp.drr.size(); i++)
				{
					String Str = Bimp.drr.get(i).substring(
							Bimp.drr.get(i).lastIndexOf("/") + 1,
							Bimp.drr.get(i).lastIndexOf("."));
					list.add(FileUtils.SDPATH + Str + ".JPEG");
					//Log.i("path", FileUtils.SDPATH + Str + ".JPEG");
				}
				// 高清的压缩图片全部就在 list 路径里面了
				// 高清的压缩过的 bmp 对象 都在 Bimp.bmp里面
				// 完成上传服务器后 .........
				names = list.toArray(new String[1]);
				BmobProFile.getInstance(PublishedActivity.this).uploadBatch(names, 
						new UploadBatchListener() {
					
					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(PublishedActivity.this, "请检查网络", Toast.LENGTH_LONG).show();
					}
					
					@Override
					public void onSuccess(boolean arg0, String[] arg1, String[] arg2) {
						// TODO Auto-generated method stub
						//Toast.makeText(PublishedActivity.this, "success"+arg0, Toast.LENGTH_LONG).show();
						if(arg0) {
							//Toast.makeText(PublishedActivity.this, "上传完成"+arg1.length, Toast.LENGTH_LONG).show();
							FileUtils.deleteDir();
							savefilename = arg1;
							savefileuri = arg2;
							//Toast.makeText(PublishedActivity.this, "上传"+savefilename.length, Toast.LENGTH_LONG).show();
							getURL();
							post();
						}
					}
					
					@Override
					public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
						// TODO Auto-generated method stub
						//Toast.makeText(PublishedActivity.this,
							//	"正在上传第"+curIndex+"张  进度"+curPercent+"\n总共"+total+"张  进度"+totalPercent, Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter
	{
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape()
		{
			return shape;
		}

		public void setShape(boolean shape)
		{
			this.shape = shape;
		}

		public GridAdapter(Context context)
		{
			inflater = LayoutInflater.from(context);
		}

		public void update1()
		{
			loading1();
		}

		public int getCount()
		{
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0)
		{

			return null;
		}

		public long getItemId(int arg0)
		{

			return 0;
		}

		public void setSelectedPosition(int position)
		{
			selectedPosition = position;
		}

		public int getSelectedPosition()
		{
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent)  //设置grid的view
		{
			//final int coord = position;
			ViewHolder holder = null; //ImageView
			
			System.out.println("测试下表="+position);
			if (convertView == null)
			{

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder); //绑定数据
			} else
			{
				holder = (ViewHolder) convertView.getTag();//取得绑定的数据
			}
			
			holder.image.setVisibility(View.VISIBLE);

			if (position == Bimp.bmp.size())
			{
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));//加号
				
			} else
			{
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}
			
			if (position == 9)
			{
				holder.image.setVisibility(View.GONE);
			}

			return convertView;
		}

		public class ViewHolder
		{
			public ImageView image;
		}

		Handler handler = new Handler()  //主要接受子线程发送的数据, 并用此数据配合主线程更新UI.
		{
			public void handleMessage(Message msg)
			{
				switch (msg.what)
				{
				case 1:
					adapter.notifyDataSetChanged(); //动态更新，不刷新Activity
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading1()
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					while (true)
					{
						if (Bimp.max == Bimp.drr.size())  //初始 = 0
						{
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else
						{
							try
							{
								String path = Bimp.drr.get(Bimp.max);//取得最新添加的图片的路径
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path); //压缩
								Bimp.bmp.add(bm);  //存储到bm中
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);   //newStr = cover#
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);  //动态更新grid
							} catch (IOException e)
							{

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s)
	{
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++)
		{
			s.charAt(i);
		}
		//Log.i("path", path);
		return path;
	}

	protected void onRestart()
	{
		adapter.update1();
		super.onRestart();
	}

	public class PopupWindows extends PopupWindow
	{

		public PopupWindows(Context mContext, View parent)
		{
			
			 super(mContext);

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);  //选择拍照相册..
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins)); //设置渐变出现动画
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));  //按钮按下的动画效果

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.MATCH_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					photo();  //调用相机
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					Intent intent = new Intent(PublishedActivity.this,
							TestPicActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	public void onConfigurationChanged(Configuration config) 
	{ 
	    super.onConfigurationChanged(config); 
	} 
	
	public void photo()
	{
		String status=Environment.getExternalStorageState(); 
		if(status.equals(Environment.MEDIA_MOUNTED)) 
		{//创建照片存储目录
			File dir=new File(Environment.getExternalStorageDirectory() + "/myimage/"); 
			if(!dir.exists())dir.mkdirs(); 
			
			Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File file = new File(dir, String.valueOf(System.currentTimeMillis())  //命名
					+ ".jpg");
			path = file.getPath();
			Uri imageUri = Uri.fromFile(file);
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);  //照片质量
			startActivityForResult(openCameraIntent, TAKE_PICTURE);
		}
		else{ 
			Toast.makeText(PublishedActivity.this, "没有储存卡",Toast.LENGTH_LONG).show(); 
			} 
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);  
		switch (requestCode)
		{
		case TAKE_PICTURE:
			if (Bimp.drr.size() < 9 && resultCode == -1)
			{
				Bimp.drr.add(path);  //将照片路径添加到drr中
			}
			break;
			
		case CITY_SELECT:
			if ( resultCode == 3)
			{
				address.setText(data.getExtras().getString("address"));
			}
			break;
		}
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
