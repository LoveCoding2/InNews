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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bean.CurrentNews;
import com.example.weidu.R;


public class CurrentAdapter extends BaseAdapter{
  private List<CurrentNews> lCurrentNews;
  private LayoutInflater layoutInflater;
 private Context mContext;


	@Override
	public int getCount() {
	
		return lCurrentNews.size();
	}
	
	public CurrentAdapter(Context context,List<CurrentNews> list)
	{
		layoutInflater=LayoutInflater.from(context);
		
       this.mContext=context; 
       this.lCurrentNews=list;
		
	}
	
	/*public CurrentAdapter(Context context)
	{
		layoutInflater=LayoutInflater.from(context);
	}*/
	
	public void binddata(List<CurrentNews> list)
	{
		this.lCurrentNews=list;
	}
		

	@Override
	public CurrentNews getItem(int position) {
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
				  CurrentNews currentNews=lCurrentNews.get(position);
				  viewHolder.titleTextView.setText(currentNews.getTitle());
				  String myurlString=currentNews.getImg();
//		          if (myurlString.contains("gif"))
//				  {
//					  viewHolder.imageView.setBackgroundResource(R.drawable.ic_launcher);
//				  }
		          /*else
				  {*/
					  Glide.with(mContext).load(myurlString).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageView);
				  /*}*/


		
		return convertView;
	}
	
	
	
	class ViewHolder
	{
		
		private ImageView imageView;
	
		private TextView titleTextView;
		
		
	}
	
	
	
	

	

	
	
	
	

}
