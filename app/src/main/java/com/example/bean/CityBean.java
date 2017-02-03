package com.example.bean;

import android.os.Parcel;
import android.os.Parcelable;


  public class CityBean implements Parcelable
  {
    

	
	public String getPubDate() {
		return pubDate;
	}



	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}



	public String getDesc() {
		return desc;
	}



	public void setDesc(String desc) {
		this.desc = desc;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getLink() {
		return link;
	}



	public void setLink(String link) {
		this.link = link;
	}



	public String getImg() {
		return img;
	}



	public void setImg(String img) {
		this.img = img;
	}



	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public static Parcelable.Creator<CityBean> getCreator() {
		return CREATOR;
	}

	private String pubDate;
     private String desc;
     private String title;
     private String link;
     private String img;
     private String source;

	
     
	

	public CityBean(String pubDate, String desc, String title, String link,
			String img, String source) {

		this.pubDate = pubDate;
		this.desc = desc;
		this.title = title;
		this.link = link;
		this.img = img;
		this.source = source;
	}



	public CityBean()
	{

	}

	

	@Override
	public int describeContents() {
	
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags)
	{
		parcel.writeString(pubDate);
		parcel.writeString(desc);
		parcel.writeString(title);
		parcel.writeString(link);
		parcel.writeString(img);
		parcel.writeString(source);
	}
	
	public static final Parcelable.Creator<CityBean> CREATOR=new Parcelable.Creator<CityBean>()
	{

		@Override
		public CityBean createFromParcel(Parcel source) {
			CityBean currentNews=new CityBean();
            currentNews.pubDate=source.readString();
            currentNews.desc=source.readString();
            currentNews.title=source.readString();
            currentNews.link=source.readString();
            currentNews.img=source.readString();
            currentNews.source=source.readString();
			return currentNews;
		}

		@Override
		public CityBean[] newArray(int size) {
		
			return new CityBean[size];
		}
		
	};

	
	
   
      
   
  }
