package com.example.weidu;



import java.util.ArrayList;

import java.util.List;

import com.example.adapter.TestViewPagerAdapter;
import com.example.fragment.DuanziFragment;
import com.example.fragment.DuzheFragment;
import com.example.fragment.GuoShiFragment;
import com.example.fragment.JiaShiFragment;
import com.example.fragment.JingdianFragment;
import com.example.fragment.KandianFragment;
import com.example.fragment.LishiFragment;
import com.example.fragment.NanrenFragment;
import com.example.fragment.NvrenFragment;
import com.example.fragment.ShuZhongFragment;
import com.example.fragment.TianxiaShiFragment;
import com.example.fragment.TiaokanFragment;
import com.example.fragment.XinShiFragment;
import com.example.fragment.YouquShiFragment;
import com.example.util.AppManager;
import com.example.util.ConfigManage;
import com.jaeger.library.StatusBarUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

	private int mScreenWidth;
	private HorizontalScrollView mHorizontalScrollView;
	private LinearLayout mLinearLayout;// 这里是用来添加文字的布局
	private ViewPager mViewpager1;
	private ImageView hsv_image_line;
	//private int mScreenWidth;// 屏幕的宽宽度
	private int item_width;// 横条图片的宽度
	private int endPosition;
	private int beginPosition;
	private int currentFragmentIndex;
	private boolean isEnd;

	private List<Fragment> fragmentList;
	private long exittime = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mScreenWidth = getResources().getDisplayMetrics().widthPixels;
		

		// 初始化导航条需要的控件
		initViewId1();
		// 初始化导航
		initDaohang();
		// 初始化手动切换viewPager
		initViewPager1();
		
	}


	

	public void initViewId1() {
	DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv_view);
		if (ConfigManage.getInstance().getBool("nightmode")==true)
		{
			mHorizontalScrollView.setBackgroundColor(Color.parseColor("#212121"));
			//StatusBarUtil.setColor(MainActivity.this, getResources().getColor(R.color.black));
		}
		mLinearLayout = (LinearLayout) findViewById(R.id.hsv_title);
		hsv_image_line = (ImageView) findViewById(R.id.hsv_image_line);
		item_width = (int) (mScreenWidth / 5.0);
		hsv_image_line.getLayoutParams().width = item_width;
		mViewpager1 = (ViewPager) findViewById(R.id.viewpager1);
	}

	
	private void initDaohang() {
		for (int i = 0; i < 15; i++) {
			RelativeLayout relativelayout = new RelativeLayout(this);
		TextView textview = new TextView(this);
			switch (i) {
			case 0:
				textview.setText("段子");
				break;
			case 1:
				textview.setText("读者");
				break;

			case 2:
				textview.setText("国事");
				break;

			case 3:
				textview.setText("家事");
				break;

			case 4:
				textview.setText("经典");
				break;
			case 5:
				textview.setText("看点");
				break;

			case 6:
				textview.setText("历史");
				break;
			case 7:
				textview.setText("男人");
				break;
			case 8:
				textview.setText("女人");
				break;

			case 9:
				textview.setText("书中");
				break;
			case 10:
				textview.setText("天下");
				break;
			case 11:
				textview.setText("调侃");
				break;
			case 12:
				textview.setText("心事");
				break;
			case 13:
				textview.setText("有趣");
				break;
			/*case 14:
				*//*SharedPreferences mysPreferences=getSharedPreferences("address",Context.MODE_PRIVATE);
				String province1=mysPreferences.getString("pro","");
				if (province1.endsWith("省") || province1.endsWith("自治区") || province1.endsWith("市"))
				{*//*
					//String province=province1.substring(0,province1.length()-1);
					textview.setText("浙江");
//				}
				
				break;*/
		    
			}
			
			
			//LayoutParams中两个参数分别为：子控件的宽(width)，子控件的高(height),
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//定义一个相对布局
			params.addRule(RelativeLayout.CENTER_IN_PARENT);// 相对于父控件完全居中
			relativelayout.addView(textview, params);//将要加入的控件和参数添加进去
			mLinearLayout.addView(relativelayout, (int) (mScreenWidth / 5), 50);
			relativelayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 手动滑动的Viewpager就会滚动到第(Integer)v.getTag()个位置上
					mViewpager1.setCurrentItem((Integer) v.getTag());
				}
			});
			relativelayout.setTag(i);
		}
	}
	
	
	private void initViewPager1()
	{
		fragmentList = new ArrayList<Fragment>();

		fragmentList.add(new DuanziFragment());
		fragmentList.add(new DuzheFragment());
		fragmentList.add(new GuoShiFragment());
		fragmentList.add(new JiaShiFragment());
		fragmentList.add(new JingdianFragment());
		fragmentList.add(new KandianFragment());
		fragmentList.add(new LishiFragment());
		fragmentList.add(new NanrenFragment());
		fragmentList.add(new NvrenFragment());
		fragmentList.add(new ShuZhongFragment());
		fragmentList.add(new TianxiaShiFragment());
		fragmentList.add(new TiaokanFragment());
		fragmentList.add(new XinShiFragment());
		fragmentList.add(new YouquShiFragment());

		
		TestViewPagerAdapter testViewPagerAdapter=new TestViewPagerAdapter(getSupportFragmentManager(),fragmentList);
		mViewpager1.setOffscreenPageLimit(15);
	
		mViewpager1.setAdapter(testViewPagerAdapter);
		mViewpager1.setOnPageChangeListener(new OnPageChangeListener() {

		
			@Override
			public void onPageSelected(final int position) {
			

				Animation animation = new TranslateAnimation(endPosition,
						position * item_width, 0, 0);

			    beginPosition= position * item_width;//结束后的位置，就是新的开始位置

				currentFragmentIndex = position;//当前flagment的下标值
				if (animation != null) {
					//如果fillAfter设为true，则动画执行后，控件将停留在动画结束的状态
					animation.setFillAfter(true);
					animation.setDuration(0);//设置动画持续时间 
					hsv_image_line.startAnimation(animation);//启动动画
					mHorizontalScrollView.smoothScrollTo(
							(currentFragmentIndex) * item_width, 0);
					
				
				}
			}

			//页面滑动的时候调用
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			
			//状态改变的时候调用
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		mViewpager1.setCurrentItem(0);
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



}
