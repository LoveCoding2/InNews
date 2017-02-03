package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTimeUtil
{
	public static String getnowtime()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		  Date date=new Date(System.currentTimeMillis());
		  String st=simpleDateFormat.format(date);
		  String ms=st.substring(st.indexOf(' '));
		  String []m1=ms.split(":");
		  String m2=st.substring(0,st.indexOf(' '));
		  String []m3=m2.split("-");
		   String str =m3[0]+m3[1]+m3[2]+m1[0]+m1[1]+m1[2] ;   
		      String str2 = str.replaceAll(" ", ""); 
		return str2;
		
	}
	
	public static String getpubtime(String st)
	{
		int m=st.lastIndexOf(' ');
		  String sp=st.substring(m);
		  String st1=st.substring(0,st.length()-sp.length());
		  String []st2=st1.split("-");
		  String []st3=sp.split(":");
		  String st5=st2[1]+"-"+st2[2]+"  "+st3[0]+":"+st3[1];
		return st5;
		
	}

}
