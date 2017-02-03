package com.example.util;

import java.io.BufferedReader;






import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtil {
  public static String getjsontext(String url)
  {
	  StringBuffer stringBuffer=new StringBuffer();
	  try {
		URL myurl=new URL(url);
		HttpURLConnection httpURLConnection=(HttpURLConnection)myurl.openConnection();
/*		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setRequestProperty("apikey","adea9ecb996f001bf24ae7efdaacb360");*/
		InputStream inputStream=httpURLConnection.getInputStream();
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String line;
		while ((line=bufferedReader.readLine())!=null)
		{
		   stringBuffer.append(line);
		}
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	return stringBuffer.toString();
	}

 public static boolean hasInternet(Activity activity)
  {
	  ConnectivityManager connectivityManager=(ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
	  NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
	  if (networkInfo==null || !networkInfo.isConnected())
	  {
		  return false;
		
	  }
	  if (networkInfo.isRoaming())
	  {
		return true;
	  }
	  return true;
	
	  
  }
  
 
}
