package com.example.weidu;

import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.bean.CurrentNews;
import com.example.util.AppManager;
import com.example.util.ClearCacheUtil;
import com.example.util.ConfigManage;
import com.example.util.RealmHelper;
import com.example.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeActivity extends AppCompatActivity{
	ImageView setting;
/*	CircleImageView userimg;
	TextView username;
	ImageView edituser;*/
	TextView collectnum;
	RelativeLayout cachelayout;
	SwitchCompat night;
	SwitchCompat voiceread;
	Button exitlogin;
	//LinearLayout userinfo;
	LinearLayout divide;
	RelativeLayout collectlayout;
	UiModeManager uiModeManager;
   RealmHelper realmHelper;

	List<CurrentNews> list=new ArrayList<>();
	TextView cachesize;
	private long exittime = 0;
    //RelativeLayout weiboview;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me);

		 uiModeManager=(UiModeManager)getSystemService(Context.UI_MODE_SERVICE);
		initview();
		initevent();
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

	private void initview()
	{
		setting=(ImageView)findViewById(R.id.setting);
		/*userimg=(CircleImageView)findViewById(R.id.userimg);
		username=(TextView)findViewById(R.id.username);
		edituser=(ImageView)findViewById(R.id.edit_user);*/
		collectnum=(TextView)findViewById(R.id.collectnum);
		cachelayout=(RelativeLayout)findViewById(R.id.cache);
		night=(SwitchCompat)findViewById(R.id.night);
		voiceread=(SwitchCompat)findViewById(R.id.voiceread);
		//userinfo=(LinearLayout)findViewById(R.id.userinfo);
		divide=(LinearLayout)findViewById(R.id.divide);
		collectlayout=(RelativeLayout)findViewById(R.id.collectlayout);
		cachesize=(TextView)findViewById(R.id.cachesize);
		exitlogin=(Button)findViewById(R.id.exit_login);
		//weiboview=(RelativeLayout)findViewById(R.id.weiboview);
		cachesize.setText("当前缓存"+ClearCacheUtil.getTotalCacheSize(MeActivity.this));

		//username.setText(ConfigManage.getInstance().getString("myphone"));
	}

	private void initevent()
	{
		realmHelper=new RealmHelper(this);
		list=realmHelper.queryAllCollect();
		collectnum.setText(list.size()+"");

		//退出登录
		exitlogin.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view) {
				SharedPreferences.Editor editor= ConfigManage.getInstance().getMySharePreferences().edit();
				editor.clear();
				editor.commit();
				Intent exitintent=new Intent(MeActivity.this,LoginActivity.class);
				startActivity(exitintent);
				finish();
			}
		});

		//final boolean isauth = UMShareAPI.get(MeActivity.this).isAuthorize(MeActivity.this,SHARE_MEDIA.SINA);

//		//绑定微博
//		weiboview.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View view)
//			{
//				if (isauth)
//				{
//					UMShareAPI.get(MeActivity.this).deleteOauth(MeActivity.this,SHARE_MEDIA.SINA,umAuthListener);
//				}
//				else
//				{
//					UMShareAPI.get(MeActivity.this).doOauthVerify(MeActivity.this,SHARE_MEDIA.SINA,umAuthListener);
//				}
//
//			}
//		});

        //夜间模式
		night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
			{
				if (isChecked)
				{
					uiModeManager.enableCarMode(0);
					uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
					divide.setBackgroundColor(Color.parseColor("#212121"));
					//StatusBarUtil.setColor(MeActivity.this, getResources().getColor(R.color.black),0);
				}
				else
				{
					uiModeManager.disableCarMode(0);
					uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
				}
				ConfigManage.getInstance().setBool("nightmode",isChecked);
			}
		});


		//语音
		voiceread.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked)
			{
				ConfigManage.getInstance().setBool("voicemode",ischecked);

			}
		});

	/*	userinfo.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view) {
				Intent intent1=new Intent(MeActivity.this,UserActivity.class);
				startActivity(intent1);
			}
		});*/

		//我的收藏
		collectlayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view) {
				Intent intent2=new Intent(MeActivity.this,CollectActivity.class);
				startActivity(intent2);
			}
		});

		//缓存清理
		cachelayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

				AlertDialog.Builder builder=new AlertDialog.Builder(MeActivity.this);
				builder.setTitle("清除缓存");
				builder.setMessage("当前缓存"+ClearCacheUtil.getTotalCacheSize(MeActivity.this));
				builder.setNegativeButton("暂不清除", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i)
					{
						dialogInterface.dismiss();
					}
				});
				builder.setPositiveButton("确定清除", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialogInterface, int i)
					{
						ClearCacheUtil.clearAllCache(MeActivity.this);
						Logger.d(ClearCacheUtil.getTotalCacheSize(MeActivity.this));
						cachesize.setText("当前缓存0KB");
					}
				});
                builder.show();

			}
		});

		/*edituser.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view) {

			}
		});*/

	}


	/*UMAuthListener umAuthListener=new UMAuthListener()
	{
		@Override
		public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

		}

		@Override
		public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

		}

		@Override
		public void onCancel(SHARE_MEDIA share_media, int i) {

		}
	};
*/

	@Override
	protected void onResume()
	{
		super.onResume();
		night.setChecked(ConfigManage.getInstance().getBool("nightmode"));
		voiceread.setChecked(ConfigManage.getInstance().getBool("voicemode"));
		realmHelper=new RealmHelper(this);
		list=realmHelper.queryAllCollect();
		collectnum.setText(list.size()+"");
	}
}
