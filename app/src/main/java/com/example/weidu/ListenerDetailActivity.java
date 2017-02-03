package com.example.weidu;




import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import com.example.bean.VideoBean;
import com.example.util.ConfigManage;
import com.jaeger.library.StatusBarUtil;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ListenerDetailActivity extends AppCompatActivity
{
	/*private ImageView listenimageView;
	private TextView listennameteTextView;
	private TextView listentimetTextView;
	private TextView messageTextView;*/
	
	private VideoView videoView;
   private MediaController mMediaController;
	
   @Override
protected void onCreate(Bundle savedInstanceState)
   {

	super.onCreate(savedInstanceState);
	if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
	{
		 return;
	}
	setContentView(R.layout.listenerdetail);
/*	listenimageView=(ImageView)findViewById(R.id.listenerimage);
	listennameteTextView=(TextView)findViewById(R.id.listenername);
	listentimetTextView=(TextView)findViewById(R.id.listenertime);
	messageTextView=(TextView)findViewById(R.id.message);*/
	videoView=(VideoView)findViewById(R.id.surface_view);

	  /* if (ConfigManage.getInstance().getBool("nightmode")==true)
	   {
		   StatusBarUtil.setColor(ListenerDetailActivity.this, getResources().getColor(R.color.black));
	   }*/
	
	 Bundle bundle=getIntent().getExtras();
	 VideoBean videoBean=(VideoBean) bundle.get("videolistener");
/*	 listennameteTextView.setText(videoBean.getName());
	 listentimetTextView.setText(videoBean.getCreate_time());
	 messageTextView.setText(videoBean.getText());*/
	 
	 
	if (Vitamio.initialize(this))
	{
	      videoView.setVideoPath(videoBean.getVideo_uri());
	     
	        videoView.setOnPreparedListener(new OnPreparedListener()
	        {
			
			@Override
			public void onPrepared(MediaPlayer mp)
			{
				
			    mp.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {
					
					@Override
					public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
						  
				        mMediaController = new MediaController(ListenerDetailActivity.this);
				        
				        videoView.setMediaController(mMediaController);
				        mMediaController.setAnchorView(videoView);
					}
				});
					
			}
		});
	      
	}

	        
	        
	    /*   videoView.setOnCompletionListener(new OnCompletionListener()
	       {
			
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				
				    mp.seekTo(0);  
	                mp.start(); 
			}
		});*/
	       
	       /* videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_LOW);
	        videoView.requestFocus();*/
	        
	               /*Glide.with(ListenerDetailActivity.this).load(videoBean.getProfile_image()).asBitmap().transform(new GlideCircleTransform(ListenerDetailActivity.this)).into(new SimpleTarget<Bitmap>()
	        		{
	        			@Override
	        			public void onResourceReady(Bitmap bitmap,
	        					GlideAnimation<? super Bitmap> arg1)
	        			{
	        				
	        				listenimageView.setImageBitmap(bitmap);
	        			}
	        			
	        		});*/
	 
     }
}
