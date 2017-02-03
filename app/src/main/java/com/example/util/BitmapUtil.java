package com.example.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil 
{
	 public static Bitmap getBitmapfromUrl(String biturl)
	 { 
		 Bitmap bitmap=null;
		 try {
			URL myUrl=new URL(biturl);
			HttpURLConnection httpURLConnection=(HttpURLConnection) myUrl.openConnection();
			InputStream inputStream=httpURLConnection.getInputStream();
			BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
			bitmap=BitmapFactory.decodeStream(bufferedInputStream);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return bitmap;
		 
	 }
	 
	
}
