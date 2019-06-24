package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.entry.UserInfo;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.ToastUtil;

import org.json.JSONObject;

public class LoginActivity extends Activity {
    private ImageView login_back;
    private TextView login_ok;
    private EditText login_phone;
    private EditText login_pwd;
    private TextView login_forget;
    private TextView register_new;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }

    private void initView() {
        login_back = (ImageView) findViewById(R.id.login_back);
        login_ok = (TextView) findViewById(R.id.login_ok);
        login_phone = (EditText) findViewById(R.id.login_phone);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        login_forget = (TextView) findViewById(R.id.login_forget);
        register_new = (TextView) findViewById(R.id.register_new);

        login_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                checkEditText(s.toString(),login_phone,"手机号不能为空");
            }
        });

        login_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                checkEditText(s.toString(),login_pwd,"密码不能为空");
            }
        });

        register_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);
            }
        });

        login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

    }

    private void checkEditText(String s,EditText ed,String msg){
        if(TextUtils.isEmpty(s)){
            ed.setError(msg);
        }
    }

    private void submit() {
        String phone = login_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = login_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject jObject = new JsonObject();
        jObject.addProperty("token",MyApplication.token);
        jObject.addProperty("phone",phone);
        jObject.addProperty("pwd",pwd);
        Log.i(TAG, "submit: "+jObject);
        Http.Post(this, "Data/Login", jObject.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                if(jsonObject.optString("msg").equals("S")){
                    UserInfo userInfo = gson.fromJson(jsonObject.toString(),UserInfo.class);
                    MyApplication.setUserInfo(userInfo);
                    MyApplication.editor.putInt("id",userInfo.getData().get(0).getId());
                    MyApplication.editor.putString("age",userInfo.getData().get(1).getAge());
                    MyApplication.editor.putString("sex",userInfo.getData().get(1).getSex());
                    MyApplication.editor.commit();
                    MyApplication.setIsLogin(true);
                    ToastUtil.toToast("登录成功!");
                    finish();
                }else{
                    ToastUtil.toToast(jsonObject.optString("data"));
                }
            }
        });

    }
}
