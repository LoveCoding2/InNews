package com.example.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.Html;
import android.text.Spanned;

import com.example.bean.CityBean;
import com.example.bean.Content;
import com.example.bean.CurrentNews;
import com.example.bean.VideoBean;

public class JsonUtil 
{
	
	public static List<CityBean> getcityList(String city)
	{
		List<CityBean> lBeans=new ArrayList<CityBean>();
		try {
			JSONObject jsonObject=new JSONObject(city);
			if (jsonObject.getInt("showapi_res_code")==0 && jsonObject.getString("showapi_res_error").equals(""))
			{
				JSONArray jsonArray=jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
				for (int i = 0; i <jsonArray.length(); i++) 
				{
					JSONObject cityjJsonObject=jsonArray.getJSONObject(i);
					String pubDate=cityjJsonObject.getString("pubDate");
					String title=cityjJsonObject.getString("title");
					String desc=cityjJsonObject.getString("desc");
					String source=cityjJsonObject.getString("source");
					String link=cityjJsonObject.getString("link");
					
					JSONArray jsonArray2=cityjJsonObject.getJSONArray("imageurls");
					for (int j = 0; j <jsonArray2.length(); j++)
					{
						JSONObject imgJsonObject=jsonArray2.getJSONObject(j);
						String img=imgJsonObject.getString("url");
						CityBean cityBean=new CityBean(pubDate, desc, title, link, img, source);
						lBeans.add(cityBean);
						
					}
				}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lBeans;
		
	}
	
	
	
	public static List<VideoBean> getvideoList(String video)
	  {
		  List<VideoBean> videoBeans=new ArrayList<VideoBean>();
		  try {
			JSONObject jsonObject=new JSONObject(video);
			if (jsonObject.getInt( "showapi_res_code")==0 && jsonObject.getString("showapi_res_error").equals("")) 
			{
				JSONArray jsonArray=jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray( "contentlist");
				for (int i = 0; i <jsonArray.length(); i++)
				{
					JSONObject videoJsonObject=jsonArray.getJSONObject(i);
					String text=videoJsonObject.getString("text");
					String hate=videoJsonObject.getString("hate");
					String videotime=videoJsonObject.getString("videotime");
					String voicetime=videoJsonObject.getString("voicetime");
					String profile_image=videoJsonObject.getString("profile_image");
					String width=videoJsonObject.getString("width");
					String voiceuri=videoJsonObject.getString("voiceuri");
					String love=videoJsonObject.getString("love");
					String height=videoJsonObject.getString("height");
					String video_uri=videoJsonObject.getString("video_uri");
					String voicelength=videoJsonObject.getString("voicelength");
					String name=videoJsonObject.getString("name");
					String create_time=videoJsonObject.getString("create_time");
					VideoBean vBean=new VideoBean(text, hate, videotime, voicetime, profile_image, width, voiceuri, love, height, video_uri, voicelength, name, create_time);
					videoBeans.add(vBean);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return videoBeans;
		  
		  
	  }
  
	public static List<CurrentNews> getcurnews(String news)
	{
		List<CurrentNews> currentNews=new ArrayList<CurrentNews>();
		try {
			JSONObject jsonObject=new JSONObject(news);
			if (jsonObject.getInt("showapi_res_code")==0 && jsonObject.getString("showapi_res_error").equals("")) 
			{
				JSONArray jsonArray=jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
				for (int i = 0; i <jsonArray.length(); i++)
				{
					JSONObject newsjJsonObject=jsonArray.getJSONObject(i);
					String id=newsjJsonObject.getString("id");
					String summary=newsjJsonObject.getString("summary");
					String time=newsjJsonObject.getString("time");
					String title=newsjJsonObject.getString("title");
					String link=newsjJsonObject.getString("link");
					String img=newsjJsonObject.getString("img");
					if (img.equals("http://www.de99.cntu/2016/111401.jpg"))
					{
						continue;
					}
					if (!img.contains("gif"))
					{
						CurrentNews News2=new CurrentNews(id, summary, time, title, link, img);
						currentNews.add(News2);
					}

				}
			}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		return currentNews;
		
	}
	
	
	
	public static CurrentNews getnotinews(String news1)
	{
		CurrentNews currentNews=new CurrentNews();
		try {
			JSONObject jsonObject=new JSONObject(news1);
			if (jsonObject.getInt("showapi_res_code")==0 && jsonObject.getString("showapi_res_error").equals("")) 
			{
				JSONArray jsonArray=jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
				for (int i = 0; i <jsonArray.length(); i++)
				{
					JSONObject newsjJsonObject=jsonArray.getJSONObject(i);
					String id=newsjJsonObject.getString("id");
					String summary=newsjJsonObject.getString("summary");
					String time=newsjJsonObject.getString("time");
					String title=newsjJsonObject.getString("title");
					String link=newsjJsonObject.getString("link");
					String img=newsjJsonObject.getString("img");
					currentNews.setId(id);
					currentNews.setImg(img);
					currentNews.setLink(link);
					currentNews.setSummary(summary);
					currentNews.setTime(time);
					currentNews.setTitle(title);
					
				}
			}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		return currentNews;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public static Content getnewscontent(String content)
	{
		Content contentbean=new Content();
		try
		{
			JSONObject jsonObject=new JSONObject(content);
			if (jsonObject.getInt("showapi_res_code")==0 && jsonObject.getString("showapi_res_error").equals(""))
			{
				JSONObject contentJsonObject=jsonObject.getJSONObject("showapi_res_body").getJSONObject("item");
				String time=contentJsonObject.getString("time");
				String title=contentJsonObject.getString("title");
				String mycontent=contentJsonObject.getString("content");
				String newscontentString=Html.fromHtml(mycontent).toString();
				contentbean.setContent(newscontentString);
				contentbean.setTime(time);
				contentbean.setTitle(title);
			}
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
		
		
		return contentbean;
		
	}
}
