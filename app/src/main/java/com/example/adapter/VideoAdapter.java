package com.example.adapter;

import java.util.List;



import com.bumptech.glide.Glide;
import com.example.bean.CurrentNews;
import com.example.bean.VideoBean;
import com.example.weidu.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class VideoAdapter extends BaseAdapter{
  private List<VideoBean> lCurrentNews;
  private LayoutInflater layoutInflater;
 private Context mContext;


	@Override
	public int getCount() {
	
		return lCurrentNews.size();
	}
	
	public VideoAdapter(Context context,List<VideoBean> list)
	{
		layoutInflater=LayoutInflater.from(context);
		
       this.mContext=context; 
       this.lCurrentNews=list;
		
	}
	
	/*public CurrentAdapter(Context context)
	{
		layoutInflater=LayoutInflater.from(context);
	}*/
	
	public void binddata(List<VideoBean> list)
	{
		this.lCurrentNews=list;
	}
		

	@Override
	public VideoBean getItem(int position) {
		// TODO Auto-generated method stub
		return lCurrentNews.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		  
		ViewHolder viewHolder;
			if (convertView==null)
			{
				viewHolder=new ViewHolder();		
			    convertView=layoutInflater.inflate(R.layout.item,null);		    
                viewHolder.imageView=(ImageView)convertView.findViewById(R.id.newsurl);
				viewHolder.titleTextView=(TextView)convertView.findViewById(R.id.newstitle);	    
			    convertView.setTag(viewHolder);
				
			}
			
			else 
			{
				viewHolder=(ViewHolder)convertView.getTag();
				
			}
				 VideoBean currentNews=lCurrentNews.get(position);
				 viewHolder.titleTextView.setText(currentNews.getName());
				 String myurlString=currentNews.getProfile_image();
				 Glide.with(mContext).load(myurlString).into(viewHolder.imageView);
			

			
		
		
		
	
	   
	   
	   
	  
 
		
		return convertView;
	}
	
	
	
	class ViewHolder
	{
		
		private ImageView imageView;
	
		private TextView titleTextView;
		
		
	}
	
	
	
	

	

	
	
	
	

}
