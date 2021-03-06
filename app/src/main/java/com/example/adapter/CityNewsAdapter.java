package com.example.adapter;

import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bean.CityBean;
import com.example.bean.CurrentNews;
import com.example.weidu.R;


public class CityNewsAdapter extends BaseAdapter{
  private List<CityBean> lCurrentNews;
  private LayoutInflater layoutInflater;
 private Context mContext;


	@Override
	public int getCount() {
	
		return lCurrentNews.size();
	}
	
	public CityNewsAdapter(Context context,List<CityBean> list)
	{
		layoutInflater=LayoutInflater.from(context);
		
       this.mContext=context; 
       this.lCurrentNews=list;
		
	}
	
	/*public CurrentAdapter(Context context)
	{
		layoutInflater=LayoutInflater.from(context);
	}*/
	
	public void binddata(List<CityBean> list)
	{
		this.lCurrentNews=list;
	}
		

	@Override
	public CityBean getItem(int position) {
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
				  CityBean currentNews=lCurrentNews.get(position);
				  viewHolder.titleTextView.setText(currentNews.getTitle());
				  String myurlString=currentNews.getImg();
				  Glide.with(mContext).load(myurlString).into(viewHolder.imageView);


			
		
		
		
	
	   
	   
	   
	  
 
		
		return convertView;
	}
	
	
	
	class ViewHolder
	{
		
		private ImageView imageView;
	
		private TextView titleTextView;
		
		
	}
	
	
	
	

	

	
	
	
	

}
