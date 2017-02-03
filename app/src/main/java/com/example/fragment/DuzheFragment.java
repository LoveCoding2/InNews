package com.example.fragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.bumptech.glide.Glide;
import com.example.adapter.CurrentAdapter;
import com.example.bean.CurrentNews;
import com.example.fragment.DuanziFragment.MyAsynctask;
import com.example.fragment.DuanziFragment.MyScrollListener;
import com.example.util.BitmapUtil;
import com.example.util.ConfigManage;
import com.example.util.HttpUtil;
import com.example.util.JsonUtil;
import com.example.util.RealmHelper;
import com.example.weidu.DetailActivity;
import com.example.weidu.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class DuzheFragment extends Fragment 
{
private ListView listView1;
    
    private View footer;
    private View view;

	RealmHelper realmHelper;
     
    private boolean isLoading;
    private  int page=1;
    private CurrentAdapter currentAdapter;
    private List<CurrentNews> total=new ArrayList<CurrentNews>();
    private String urlString="https://route.showapi.com/990-2?id=12&showapi_appid=15867&showapi_sign=0a84571c00634b9588ed1f8b8ff7c51a&page=";
    
   @Override  
   public View onCreateView(LayoutInflater inflater, ViewGroup container,  
           Bundle savedInstanceState)
   {
	 super.onCreateView(inflater,container,savedInstanceState);
		
	     if (view==null) 
			{
		      view=inflater.inflate(R.layout.duzhe,container,false);
			}	 
			
			 ViewGroup parent = (ViewGroup)view.getParent();  
		     if (parent != null)
		     {  
		         parent.removeView(view);  
		     } 
	 
	
     return view;
	   
   }
   
   @Override
public void onActivityCreated(Bundle savedInstanceState) {

	super.onActivityCreated(savedInstanceState);
	   realmHelper=new RealmHelper(getActivity());
	   listView1=(ListView)getActivity().findViewById(R.id.duzhe);
    LayoutInflater inflater1 = LayoutInflater.from(getActivity());
    footer = inflater1.inflate(R.layout.footer_layout, null);
    footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
    listView1.addFooterView(footer);
    
   listView1.setOnItemClickListener(new OnItemClickListener()
   {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		/*for (int k=0;k<currentAdapter.getCount();k++)
		{
			if (k==position)
			{
				realmHelper.addCollcet(currentAdapter.getItem(k));
			}

		}
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String str = formatter.format(curDate);
		ConfigManage.getInstance().setString("currenttime",str);*/

		Intent intent=new Intent(getActivity(),DetailActivity.class);
		Bundle bundle=new Bundle();
		bundle.putParcelable("mynews", currentAdapter.getItem(position));
		intent.putExtras(bundle);
		startActivity(intent);
		
	}
	   
   });
   
    currentAdapter=new CurrentAdapter(getActivity(),total);
	listView1.setAdapter(currentAdapter);
	listView1.setOnScrollListener(new MyScrollListener());
	 new MyAsynctask().execute(urlString+page);
}
   
   

   
   class MyScrollListener implements OnScrollListener 
   {

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) 
	{
		switch (scrollState)
		{
		case SCROLL_STATE_FLING:
			 Glide.with(getActivity()).pauseRequests();
			break;
		case SCROLL_STATE_IDLE:
			 Glide.with(getActivity()).resumeRequests();
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			 Glide.with(getActivity()).resumeRequests();
			break;
				
		default:
			break;
		}
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		
		if(firstVisibleItem+visibleItemCount==totalItemCount&&isLoading==false)
		{
			isLoading=true;
			page++;
			new MyAsynctask().execute(urlString+page);
		}
		
	}
	   
   }
   
   
   class  MyAsynctask extends AsyncTask<String,Void,List<CurrentNews>>
   {
       @Override
       protected List<CurrentNews> doInBackground(String... params)
       {
           String web= HttpUtil.getjsontext(params[0]);
           List<CurrentNews> currentNewsList= JsonUtil.getcurnews(web);
           return currentNewsList;
       }

       @Override
       protected void onPostExecute(List<CurrentNews> currentNewses)
       {
           super.onPostExecute(currentNewses);
           total.addAll(currentNewses);
           currentAdapter.binddata(total);
           currentAdapter.notifyDataSetChanged();
			isLoading = false;
			if(currentNewses.size()==0)
			{
				
				listView1.removeFooterView(footer);
				isLoading = true;
			}
        	

       }

      
   }

}
