package com.example.bean;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class CurrentNews extends RealmObject implements Parcelable
  {
	  @PrimaryKey
     private String id;
     public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	private String summary;
     private String time;
     private String title;
     private String link;
     private String img;

	  public String getReadtime() {
		  return readtime;
	  }

	  public void setReadtime(String readtime) {
		  this.readtime = readtime;
	  }

	  private String readtime;


	public CurrentNews() {

	}

	public CurrentNews(String id, String summary, String time, String title,String link, String img) {
	
		this.id = id;
		this.summary = summary;
		this.time = time;
		this.title = title;
		this.link = link;
		this.img = img;
	}

	@Override
	public int describeContents() {
	
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags)
	{
		parcel.writeString(id);
		parcel.writeString(summary);
		parcel.writeString(time);
		parcel.writeString(title);
		parcel.writeString(link);
		parcel.writeString(img);
	}
	
	public static final Parcelable.Creator<CurrentNews> CREATOR=new Parcelable.Creator<CurrentNews>()
	{

		@Override
		public CurrentNews createFromParcel(Parcel source) {
			CurrentNews currentNews=new CurrentNews();
            currentNews.id=source.readString();
            currentNews.summary=source.readString();
            currentNews.time=source.readString();
            currentNews.title=source.readString();
            currentNews.link=source.readString();
            currentNews.img=source.readString();
			return currentNews;
		}

		@Override
		public CurrentNews[] newArray(int size) {
		
			return new CurrentNews[size];
		}
		
	};

	
	
   
      
   
  }
