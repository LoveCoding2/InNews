package com.example.weidu;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.utils.PhoneUtils;
import com.example.util.ConfigManage;
import com.example.util.MD5Util;
import com.example.util.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegisterActivity extends AppCompatActivity {
   EditText registename;
    EditText registercode;
    EditText registerpwd;
    Button codebtn;
    Button registerbtn;
    private final String appKey="1ab0b98ba0628";
    private final String appSecret="addbdb0f05aad81cabf48bfb46a1b430";
    private int i=60;
    //控制按钮样式是否改变
    private boolean tag = true;
    private EventHandler eh;
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
                    Toast.makeText(RegisterActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //获取验证码成功
                    Toast.makeText(RegisterActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //返回支持发送验证码的国家列表
                    Toast.makeText(RegisterActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registename=(EditText)findViewById(R.id.register_name);
        registercode=(EditText)findViewById(R.id.verifycode);
        codebtn=(Button)findViewById(R.id.getcode);
        registerbtn=(Button)findViewById(R.id.register_btn);
        registerpwd=(EditText)findViewById(R.id.register_pwd);

        ConfigManage.getInstance().setString("myphone",registename.getText().toString());

        // 启动短信验证sdk
        SMSSDK.initSDK(this, appKey, appSecret);


        eh=new EventHandler()
        {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.obj = data;
                        handler.sendMessage(msg);
                        Logger.d("提交验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Message msg = new Message();
                        //获取验证码成功
                        msg.arg1 = 1;
                        msg.obj = "获取验证码成功";
                        handler.sendMessage(msg);
                        Logger.d("获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        Message msg = new Message();
                        //返回支持发送验证码的国家列表
                        msg.arg1 = 2;
                        msg.obj = "返回支持发送验证码的国家列表";
                        handler.sendMessage(msg);
                        Logger.d("返回支持发送验证码的国家列表");
                    }
                } else {
                    Message msg = new Message();
                    //返回支持发送验证码的国家列表
                    msg.arg1 = 3;
                    msg.obj = "验证失败";
                    handler.sendMessage(msg);
                    ((Throwable) data).printStackTrace();
                }
            }
        };

        SMSSDK.registerEventHandler(eh); //注册短信回调*/

        codebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //获取验证码操作
                if(registename.getText().toString().equals(""))
                {
                    ToastUtil.showShortToast(RegisterActivity.this,"手机号不能为空");
                }
                else
                {
                    //填写了手机号码
                    if(isMobileNO(registename.getText().toString()))
                    {
                        if (ConfigManage.getInstance().getString("myphone").equals(registename.getText().toString()))
                        {
                            ToastUtil.showShortToast(RegisterActivity.this,"该手机号已被注册,请使用新的手机号");
                        }
                        else
                        {
                            //如果手机号码无误，则发送验证请求
                            codebtn.setClickable(true);
                            changeBtnGetCode();
                            SMSSDK.getSupportedCountries();
                            //getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, new SmsObserver(new Handler()));
                            SMSSDK.getVerificationCode("86",registename.getText().toString());
                        }

                    }else{
                        //手机号格式有误
                        ToastUtil.showShortToast(RegisterActivity.this,"手机号格式错误，请检查");
                    }
                }
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Logger.d(MD5Util.MD5(registerpwd.getText().toString()));
                //Logger.d(MD5Util.decryptMD5(MD5Util.decryptMD5(MD5Util.MD5(registerpwd.getText().toString()))));
                ConfigManage.getInstance().setString("mypwd", MD5Util.MD5(registerpwd.getText().toString()));
                Pattern pattern = Pattern.compile("[0-9a-zA-Z]{6,20}");
                Matcher matcher = pattern.matcher(registerpwd.getText().toString());
                if (matcher.matches())
                {
                    Intent regisintent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(regisintent);
                    finish();
                }

            }
        });

    }


    private boolean isMobileNO(String phone)
    {
       /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phone)) return false;
        else return phone.matches(telRegex);
    }

    /*
 * 改变按钮样式
 * */
    private void changeBtnGetCode()
    {

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        if (RegisterActivity.this == null) {
                            break;
                        }

                        RegisterActivity.this.runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run() {
                                codebtn.setText("获取验证码(" + i + ")");
                                codebtn.setClickable(false);
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;

                if (RegisterActivity.this != null)
                {
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            codebtn.setText("获取验证码");
                            codebtn.setClickable(true);
                        }
                    });
                }
            }
        };
        thread.start();
    }



   /*class SmsObserver extends ContentObserver
   {

        public SmsObserver(Handler handler)
        {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange)
        {
            StringBuilder sb = new StringBuilder();
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            while (cursor.moveToNext())
            {
                sb.append("body=" + cursor.getString(cursor.getColumnIndex("body")));
            }
            //cursor.moveToNext();

            Logger.d(sb.toString());
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(sb.toString());
            registercode.setText(matcher.replaceAll(""));
            cursor.close();
            super.onChange(selfChange);
        }
    }*/
}
