package com.example.weidu;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.bean.CollectBean;
import com.example.bean.Content;
import com.example.bean.CurrentNews;
import com.example.util.ConfigManage;
import com.example.util.HttpUtil;
import com.example.util.JsonUtil;
import com.example.util.NoDoubleClickUtils;
import com.example.util.OkManager;
import com.example.util.RealmHelper;
import com.example.util.ToastUtil;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity
{
	private TextView titleTextView;
	private TextView timeTextView;
	private TextView contentTextView;
	private ImageView pictureImageView;
	private ImageView voiceiImageView;

    ImageView collect;
	ImageView share;
	ImageView detailback;

	RealmHelper realmHelper;
	List<CurrentNews> collectBeanList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
  
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.detail);
    	SpeechUtility.createUtility(DetailActivity.this, SpeechConstant.APPID +"=56c9633f ");

		/*if (ConfigManage.getInstance().getBool("nightmode")==true)
		{
			StatusBarUtil.setColor(DetailActivity.this, getResources().getColor(R.color.black));
		}*/

	    collect=(ImageView)findViewById(R.id.mycollect);
		share=(ImageView)findViewById(R.id.myshare);
		detailback=(ImageView)findViewById(R.id.detail_back);
        collect.setSaveEnabled(true);

    	 Bundle bundle=getIntent().getExtras();
    	 final CurrentNews cNews=(CurrentNews)bundle.get("mynews");

		//文章详情url
		final String myurl="https://route.showapi.com/990-3?showapi_appid=15867&showapi_sign=0a84571c00634b9588ed1f8b8ff7c51a&id="+cNews.getId();
    	  titleTextView=(TextView)findViewById(R.id.title);
    	  voiceiImageView=(ImageView)findViewById(R.id.voice);
    	   timeTextView=(TextView)findViewById(R.id.time);
    	   contentTextView=(TextView)findViewById(R.id.content);
    	   pictureImageView=(ImageView)findViewById(R.id.picture);

		timeTextView.setText(cNews.getTime());//时间
		titleTextView.setText(cNews.getTitle());//标题
		//contentTextView.setText(cNews.getSummary());
        voiceiImageView.setSaveEnabled(true);
		Call call= OkManager.getInstance().getCall(myurl);
		call.enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
               String json=response.body().string();
				try
				{
					JSONObject jsonObject=new JSONObject(json);
					if (jsonObject.getInt("showapi_res_code")==0 && jsonObject.getString("showapi_res_error").equals(""))
					{
						JSONObject contentJsonObject=jsonObject.getJSONObject("showapi_res_body").getJSONObject("item");
						String mycontent=contentJsonObject.getString("content");
						final String newscontentString= Html.fromHtml(mycontent).toString();//文章内容
						runOnUiThread(new Runnable() {
							@Override
							public void run()
							{
								contentTextView.setText(newscontentString);
								if (ConfigManage.getInstance().getBool("voicemode")==true)
								{
									voiceiImageView.setVisibility(View.VISIBLE);
									voiceiImageView.setOnClickListener(new OnClickListener()
									{

										@Override
										public void onClick(View v)
										{
											//防止按钮重复点击
											if (!NoDoubleClickUtils.isDoubleClick())
											{
												SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(DetailActivity.this, null);
												mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
												mTts.setParameter(SpeechConstant.SPEED, "20");
												mTts.setParameter(SpeechConstant.VOLUME, "80");
												mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
												mTts.startSpeaking(cNews.getTitle()+newscontentString, mSynListener);
											}

										}
									});
								}
								else
								{
									voiceiImageView.setVisibility(View.GONE);
								}


							}
						});
					}
				} catch (JSONException e)
				{
					e.printStackTrace();
				}

			}
		});

		detailback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		//分享
		share.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, cNews.getTitle());
				intent.putExtra(Intent.EXTRA_TEXT, cNews.getSummary());
				startActivity(Intent.createChooser(intent,getTitle()));
			}
		});

		realmHelper=new RealmHelper(DetailActivity.this);

		/*final CollectBean collectBean=new CollectBean();
		collectBean.setId(cNews.getId());
		collectBean.setContent(contentTextView.getText().toString());
		collectBean.setTime(cNews.getTime());
		collectBean.setTitle(cNews.getTitle());
		collectBean.setImg(cNews.getImg());*/

		Logger.d(cNews.getId());

		if (realmHelper.isCollectExist(cNews.getId()))
		{
			Log.d("a1",realmHelper.isCollectExist(cNews.getId())+"");
			collect.setSelected(true);

		}
		else
		{
			Log.d("a2",realmHelper.isCollectExist(cNews.getId())+"");//false
			collect.setSelected(false);
		}

		Logger.d(cNews.getId());


		//收藏
		collect.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				//使用Realm添加到数据库中
			/*	collectBeanList=realmHelper.queryAllCollect();


			  if (collectBeanList.contains(collectBean)==false)
				{
					collect.setBackgroundResource(R.drawable.icon_like);
					realmHelper.addCollcet(collectBean);
					ToastUtil.showShortToast(DetailActivity.this,"收藏成功");
				}
				else
				{
					collect.setBackgroundResource(R.drawable.icon_unlike);
					realmHelper.deleteCollect(collectBean.getId());
					ToastUtil.showShortToast(DetailActivity.this,"取消收藏成功");
				}
			*/


				if (collect.isSelected())
				{
					Log.d("b1",collect.isSelected()+"");//true
					collect.setSelected(false);
					realmHelper.deleteCollect(cNews.getId());
					ToastUtil.showShortToast(DetailActivity.this,"取消收藏成功");

				}
				else
				{
					Log.d("b2",collect.isSelected()+"");//false
					collect.setSelected(true);
					realmHelper.addCollcet(cNews);
					ToastUtil.showShortToast(DetailActivity.this,"收藏成功");

				}

				ConfigManage.getInstance().setString("myid",cNews.getId());

/*			    collectBeanList=realmHelper.queryAllCollect();
			    CollectBean	collectBean1=realmHelper.queryCollectById(collectBean.getId());
				ConfigManage.getInstance().setBool("myfavor1",collectBeanList.contains(collectBean1));*/

			}
		});

    	  /* if (cNews.getImg().contains("gif"))
		   {
			   pictureImageView.setBackgroundResource(R.drawable.ic_launcher);
		   }
		   else
		   {*/
			   Glide.with(DetailActivity.this).load(cNews.getImg()).asBitmap().into(new SimpleTarget<Bitmap>()
			   {
				   @Override
				   public void onResourceReady(Bitmap bitmap,GlideAnimation<? super Bitmap> arg1)
				   {
					   pictureImageView.setImageBitmap(bitmap);
				   }
			   });
		   /*}*/
    }


    @Override
  	public boolean onCreateOptionsMenu(Menu menu) 
    {
  		getMenuInflater().inflate(R.menu.main, menu);
  		return true;
  	}


	private SynthesizerListener mSynListener = new SynthesizerListener()
    {

  	@Override
  	public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {

  	}

  	@Override
  	public void onCompleted(SpeechError arg0) {
  		
  	}

  	@Override
  	public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
  		
  	}

  	@Override
  	public void onSpeakBegin() {
  		
  	}

  	@Override
  	public void onSpeakPaused() {
  	}

  	@Override
  	public void onSpeakProgress(int arg0, int arg1, int arg2) {
  		
  	}

  	@Override
  	public void onSpeakResumed() {
  		
  		
  	}
  	  
    };
    
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
    	switch (item.getItemId()) {
    	case R.id.myshare:
    		Bundle bundle1=getIntent().getExtras();;
    		CurrentNews cNews1=(CurrentNews)bundle1.get("mynews");
    		Intent shareIntent=new Intent(Intent.ACTION_SEND);

    		shareIntent.putExtra(Intent.EXTRA_TEXT,cNews1.getTitle());
    		shareIntent.setType("text/plain");
    		startActivity(Intent.createChooser(shareIntent,"分享"));
    		break;
    	 case R.id.myfavor: 
    		 
    		    Bundle bundle2=getIntent().getExtras();;
    			CurrentNews cNews2=(CurrentNews)bundle2.get("mynews");
    			SharedPreferences preferences=getSharedPreferences("user",Context.MODE_PRIVATE);
    			SharedPreferences.Editor editor = preferences.edit(); 
    			
    			editor.putString("title",cNews2.getTitle()); 
    			editor.commit(); 
    			Toast.makeText(DetailActivity.this, "收藏成功" , Toast.LENGTH_LONG).show();
             break; 
    	 case android.R.id.home:  
             finish();  
             break; 

    	default:
    		break;
    	}
    	return true;
    }*/
}
