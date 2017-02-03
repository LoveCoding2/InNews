package com.example.weidu;

import java.util.ArrayList;

import java.util.List;

import com.bumptech.glide.Glide;
import com.example.adapter.CurrentAdapter;
import com.example.adapter.VideoAdapter;
import com.example.bean.CurrentNews;
import com.example.bean.VideoBean;

import com.example.util.AppManager;
import com.example.util.ConfigManage;
import com.example.util.HttpUtil;
import com.example.util.JsonUtil;
import com.jaeger.library.StatusBarUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

public class ListenerActivity extends AppCompatActivity {
  private ListView listView;
  private View footer;
  private View view;
  private boolean isLoading;
  private int page=1;
  private VideoAdapter videoAdapter;
	private long exittime = 0;
  private List<VideoBean> total=new ArrayList<VideoBean>();
  private String urlString="https://route.showapi.com/255-1?showapi_appid=15867&type=41&showapi_sign=0a84571c00634b9588ed1f8b8ff7c51a&page=";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listener);
//		if (ConfigManage.getInstance().getBool("nightmode")==true)
//		{
//			StatusBarUtil.setColor(ListenerActivity.this, getResources().getColor(R.color.black));
//		}
		listView=(ListView)findViewById(R.id.videolist);
		 LayoutInflater inflater1 = LayoutInflater.from(ListenerActivity.this);
		  footer = inflater1.inflate(R.layout.footer_layout, null);
		  footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		  listView.addFooterView(footer);    
		 videoAdapter=new VideoAdapter(ListenerActivity.this,total);
		listView.setAdapter(videoAdapter);
		listView.setOnScrollListener(new MyScrollListener());
		new MyAsynctask().execute(urlString+page);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - exittime > 2000)
			{
				exittime = System.currentTimeMillis();
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
				return true;
			}
			else
			{
				AppManager.getAppManager().AppExit(this);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	 class MyScrollListener implements OnScrollListener 
	   {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) 
		{
			switch (scrollState)
			{
			case SCROLL_STATE_FLING:
				 Glide.with(ListenerActivity.this).pauseRequests();
				break;
			case SCROLL_STATE_IDLE:
				 Glide.with(ListenerActivity.this).resumeRequests();
				break;
			case SCROLL_STATE_TOUCH_SCROLL:
				 Glide.with(ListenerActivity.this).resumeRequests();
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
				 footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
				page++;
				new MyAsynctask().execute(urlString+page);
			}
			
		}
		   
	   }
	 
	  class  MyAsynctask extends AsyncTask<String,Void,List<VideoBean>>
	   {
	       @Override
	       protected List<VideoBean> doInBackground(String... params)
	       {
	           String web= HttpUtil.getjsontext(params[0]);
	           List<VideoBean> currentNewsList= JsonUtil.getvideoList(web);
	           return currentNewsList;
	       }

	       @Override
	       protected void onPostExecute(List<VideoBean> currentNewses)
	       {
	           super.onPostExecute(currentNewses);
	           total.addAll(currentNewses);
	           videoAdapter.binddata(total);
	           videoAdapter.notifyDataSetChanged();
				isLoading = false;
				if(currentNewses.size()==0)
				{
					
					listView.removeFooterView(footer);
					isLoading = true;
				}
	        
				listView.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
				 Intent intent=new Intent(ListenerActivity.this,ListenerDetailActivity.class);
				 Bundle bundle=new Bundle();
				 bundle.putParcelable("videolistener",videoAdapter.getItem(position));
				intent.putExtras(bundle);
				startActivity(intent);
				 
						
					}
					
				});


	       }

	      
	   }
}
