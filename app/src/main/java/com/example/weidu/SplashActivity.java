package com.example.weidu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.CommonFieldUtil;
import com.example.util.ConfigManage;
import com.example.util.RxJavaCustomTransform;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class SplashActivity extends AppCompatActivity {
    boolean isShowingRubberEffect = false;
    Intent intent;
    ImageView mLogoBgIv;
    ImageView mLogoWordIv;
    ImageView mLogoTrumpetIv;
    TextView mAppNameTv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoomin, 0);
        setContentView(R.layout.activity_splash);
        mLogoBgIv=(ImageView)findViewById(R.id.logo_bg);
        mLogoWordIv=(ImageView)findViewById(R.id.logo_word);
        mLogoTrumpetIv=(ImageView)findViewById(R.id.logo_trumpet);
        mAppNameTv=(TextView)findViewById(R.id.app_name_tv);
        initAnimation();
    }

    private void initAnimation()
    {
        startLogoInner1();
        startLogoOuterAndAppName();
    }

    private void startLogoInner1()
    {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_top_in);
        mLogoWordIv.startAnimation(animation);
    }

    private void startLogoOuterAndAppName()
    {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float fraction = animation.getAnimatedFraction();
                if (fraction >= 0.8 && !isShowingRubberEffect)
                {
                    isShowingRubberEffect = true;
                    startLogoOuter();
                    startShowAppName();
                    finishActivity();
                } else if (fraction >= 0.95)
                {
                    valueAnimator.cancel();
                    startLogoInner2();
                }

            }
        });
        valueAnimator.start();
    }



    private void startLogoOuter()
    {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playTogether(ObjectAnimator.ofFloat(mLogoBgIv, "scaleX", new float[]{1.0F, 1.25F, 0.75F, 1.15F, 1.0F}), ObjectAnimator.ofFloat(mLogoBgIv, "scaleY", new float[]{1.0F, 0.75F, 1.25F, 0.85F, 1.0F}));
        animatorSet.start();
    }

    private void startShowAppName()
    {
        ObjectAnimator.ofFloat(mAppNameTv,"alpha",new float[]{0,1}).setDuration(1000).start();
        ObjectAnimator.ofFloat(mLogoTrumpetIv,"alpha",new float[]{0,1}).setDuration(1000).start();
    }

    private void startLogoInner2()
    {
        ObjectAnimator.ofFloat(mLogoWordIv, "translationY", new float[]{0.0F, 0.0F, -30.0F, 0.0F, -15.0F, 0.0F, 0.0F});
    }

  private void finishActivity()
  {
      Observable.timer(5000, TimeUnit.MILLISECONDS)
              .compose(RxJavaCustomTransform.<Long>defaultSchedulers())
              .subscribe(new Action1<Long>() {
                  @Override
                  public void call(Long aLong)
                  {

                      if (!ConfigManage.getInstance().getBool(CommonFieldUtil.ISFIRST))
                      {
                          intent=new Intent(SplashActivity.this,LoginActivity.class);
                      }
                      else
                      {
                          intent=new Intent(SplashActivity.this,MyTabActivity.class);
                      }
                      startActivity(intent);
                      overridePendingTransition(0, android.R.anim.fade_out);
                      finish();
                  }
              });

  }



}
