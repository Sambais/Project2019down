package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.util.Http;

import org.json.JSONObject;

public class LoginActivity extends Activity {
    private ImageView login_back;
    private TextView login_ok;
    private EditText login_phone;
    private EditText login_pwd;
    private TextView login_forget;
    private TextView register_new;

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

        register_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        Http.Post(this, "Data/Login", "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                if(jsonObject.optString("code").equals())
            }
        });

    }
}
