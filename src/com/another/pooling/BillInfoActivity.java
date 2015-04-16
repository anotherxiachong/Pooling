package com.another.pooling;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class BillInfoActivity extends Activity implements AMapLocationListener {
	
	private static final int CITY_SELECT = 0;
	private EditText decribe;
	private EditText deadline;
	private double longitude;
	private double latitude;
	private BmobGeoPoint mBmobGeoPoint;
	private EditText link;
	private EditText address;
	private EditText detailaddress;
	
	private String imgfilenameString = "";
	


	LocationManagerProxy mLocationManagerProxy;
	//private AMapLocation mAMapLocation;
	
	//String[] mProvinceDatas;
	
	 private String picturePath = "";
	 
	 private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	    private static final int PHOTO_REQUEST_CUT = 3;// 结果
	    private File tempFile = new File(Environment.getExternalStorageDirectory(),
	            getPhotoFileName());


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bill_info);
		
		Bmob.initialize(this, "dc417cd048f5197ba699440c13977f34");
		
		decribe = (EditText) findViewById(R.id.describe);
		deadline = (EditText) findViewById(R.id.deadline);
		link = (EditText) findViewById(R.id.link);
		address = (EditText) findViewById(R.id.online_address);
		detailaddress = (EditText) findViewById(R.id.offline_address);
		
		
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
	    mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork,-1, 15, this);

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
	
	public void select(View view) {
		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
	}
	
	

	public void post(View view) {
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
		
		BTPFileResponse response = BmobProFile.getInstance(BillInfoActivity.this).upload(picturePath, new UploadListener() {
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("err", "error");
			}
			
			@Override
			public void onSuccess(String arg0, String arg1) {
				// TODO Auto-generated method stub
				imgfilenameString = arg0;
				//Log.i("success", "success");
				Log.i("img", imgfilenameString);
			}
			
			@Override
			public void onProgress(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mBillInfo.setImgfilename(imgfilenameString);
		
		if(mBillInfo.getImgfilename() != null)
		Log.i("get", mBillInfo.getImgfilename());
		
		mBillInfo.save(BillInfoActivity.this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(BillInfoActivity.this, "submit success", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(BillInfoActivity.this, arg1, Toast.LENGTH_LONG).show();
			}
		});
		
	}


	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//if(requestCode == CITY_SELECT && resultCode == 3) {
			//address.setText(data.getExtras().getString("address"));
			//Log.i("result",data.getExtras().getString("address"));
		//}
		switch (requestCode) {
        case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
            startPhotoZoom(Uri.fromFile(tempFile));
            break;
        case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
            // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
            if (data != null) {
                System.out.println("11================");
                startPhotoZoom(data.getData());
            } else {
                System.out.println("================");
            }
            break;
        case PHOTO_REQUEST_CUT:// 返回的结果
            if (data != null)
                // setPicToView(data);
                //sentPicToNext(data);
            break;
        }
		
	}
	
	private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        System.out.println("22================");
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
	
	// 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
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
