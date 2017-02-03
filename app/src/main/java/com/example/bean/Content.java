package com.example.bean;

import android.os.Parcel;
import android.os.Parcelable;


  public class Content implements Parcelable
  {
     

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

	
	
     private String time;
     private String title;
     
     public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	private String content;
 
    

	
     
	

	public Content() {

	}

	public Content(String time, String title, String content) {
	
		this.time = time;
		this.title = title;
		this.content=content;
	}

	@Override
	public int describeContents() {
	
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags)
	{
		
		parcel.writeString(time);
		parcel.writeString(title);
		parcel.writeString(content);
		
	}
	
	public static final Parcelable.Creator<Content> CREATOR=new Parcelable.Creator<Content>()
	{

		@Override
		public Content createFromParcel(Parcel source) {
			Content currentNews=new Content();
            currentNews.time=source.readString();
            currentNews.title=source.readString();
            currentNews.content=source.readString();
			return currentNews;
		}

		@Override
		public Content[] newArray(int size) {
		
			return new Content[size];
		}
		
	};

	
	
   
      
   
  }
