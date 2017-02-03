package com.example.weidu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.util.AppManager;
import com.example.util.CommonFieldUtil;
import com.example.util.ConfigManage;
import com.example.util.MD5Util;
import com.example.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
   EditText loginname;
    EditText loginpas;
    Button loginbtn;
   TextView goregister;
    private long exittime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         loginname=(EditText)findViewById(R.id.login_name);
        loginpas=(EditText)findViewById(R.id.login_pas);
        loginbtn=(Button)findViewById(R.id.login_btn);
        goregister=(TextView)findViewById(R.id.goregister);
        goregister.setPaintFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        goregister.getPaint().setAntiAlias(true);//抗锯齿

        goregister.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent gointent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(gointent);
                finish();
            }
        });

       loginname.setText(ConfigManage.getInstance().getString("myphone"));

        loginbtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ConfigManage.getInstance().setBool(CommonFieldUtil.ISFIRST,true);

                if (TextUtils.isEmpty(loginpas.getText().toString()))
                {
                    ToastUtil.showShortToast(LoginActivity.this,"密码为空");
                }
                else
                {
                    if (ConfigManage.getInstance().getString("mypwd").equals(MD5Util.MD5(loginpas.getText().toString())))
                    {
                        loginbtn.setClickable(true);
                        Intent intent=new Intent(LoginActivity.this,MyTabActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        ToastUtil.showShortToast(LoginActivity.this,"用户名或密码错误");
                    }
                }

            }
        });


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

