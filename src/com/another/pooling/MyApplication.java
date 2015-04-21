package com.another.pooling;

import java.io.File;
import java.io.IOException;

import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

public class MyApplication extends Application{
	private static String IMAGEPATH ; //图的存储路径
	private BmobConfiguration config;
    
	
	public MyApplication() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

	}

	public String getIMAGEPATH() {
		return IMAGEPATH;
	}

	public void setIMAGEPATH(String iMAGEPATH) {
		IMAGEPATH = iMAGEPATH;
	}

	public BmobConfiguration getConfig() {
		return config;
	}

	public void setConfig(BmobConfiguration config) {
		this.config = config;
	}
	
	public void configPath(Context context) {
		init();
		config = new BmobConfiguration.Builder(context).customExternalCacheDir(IMAGEPATH).build();
		BmobPro.getInstance(context).initConfig(config);
	}
	
	
	public static void init() {
		IMAGEPATH = Environment.getExternalStorageDirectory()
				+ "/pooling/"; 
		if (!isFileExist(""))  //目录不存在
		{
			try {
				File tempf = createSDDir("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	
	public static boolean isFileExist(String fileName)
	{
		File file = new File(IMAGEPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static File createSDDir(String dirName) throws IOException
	{
		File dir = new File(IMAGEPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}
	
}
