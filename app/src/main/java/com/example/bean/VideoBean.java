package com.example.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoBean implements Parcelable
{
	private String text;
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHate() {
		return hate;
	}

	public void setHate(String hate) {
		this.hate = hate;
	}

	public String getVideotime() {
		return videotime;
	}

	public void setVideotime(String videotime) {
		this.videotime = videotime;
	}

	public String getVoicetime() {
		return voicetime;
	}

	public void setVoicetime(String voicetime) {
		this.voicetime = voicetime;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getVoiceuri() {
		return voiceuri;
	}

	public void setVoiceuri(String voiceuri) {
		this.voiceuri = voiceuri;
	}

	public String getLove() {
		return love;
	}

	public void setLove(String love) {
		this.love = love;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getVideo_uri() {
		return video_uri;
	}

	public void setVideo_uri(String video_uri) {
		this.video_uri = video_uri;
	}

	public String getVoicelength() {
		return voicelength;
	}

	public void setVoicelength(String voicelength) {
		this.voicelength = voicelength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	
	
	public VideoBean()
	{
	
	}


	

	public VideoBean(String text, String hate, String videotime,String voicetime, String profile_image, String width,
			String voiceuri, String love, String height, String video_uri,
			String voicelength, String name, String create_time) 
	{
	
		this.text = text;
		this.hate = hate;
		this.videotime = videotime;
		this.voicetime = voicetime;
		this.profile_image = profile_image;
		this.width = width;
		this.voiceuri = voiceuri;
		this.love = love;
		this.height = height;
		this.video_uri = video_uri;
		this.voicelength = voicelength;
		this.name = name;
		this.create_time = create_time;
	}




	private String hate;
	private String videotime;
	private String voicetime;
	private String profile_image;
	private String width;
	private String voiceuri;
	private String love;
	private String height;
	private String video_uri;
	private String voicelength;
	private String name;
	private String create_time;

	@Override
	public int describeContents()
	{

		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags)
	{
		parcel.writeString(text);
		parcel.writeString(hate);
		parcel.writeString(videotime);
		parcel.writeString(voicetime);
		parcel.writeString(profile_image);
		parcel.writeString(width);
		parcel.writeString(voiceuri);
		parcel.writeString(love);
		parcel.writeString(height);
		parcel.writeString(video_uri);
		parcel.writeString(voicelength);
		parcel.writeString(name);
		parcel.writeString(create_time);
		
	}
	
	
	public static final Parcelable.Creator<VideoBean> CREATOR=new Parcelable.Creator<VideoBean>()
	{

		@Override
		public VideoBean createFromParcel(Parcel source) {
			VideoBean videoBean=new VideoBean();
			videoBean.text=source.readString();
			videoBean.hate=source.readString();
			videoBean.videotime=source.readString();
			videoBean.voicetime=source.readString();
			videoBean.profile_image=source.readString();
			videoBean.width=source.readString();
			videoBean.voiceuri=source.readString();
			videoBean.love=source.readString();
			videoBean.height=source.readString();
			videoBean.video_uri=source.readString();
			videoBean.voicelength=source.readString();
			videoBean.name=source.readString();
			videoBean.create_time=source.readString();
			return videoBean;
		}

		@Override
		public VideoBean[] newArray(int size) {
			
			return new VideoBean[size];
		}
		
		
	};
}
