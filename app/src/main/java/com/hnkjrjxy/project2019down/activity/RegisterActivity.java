package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.ToastUtil;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private ImageView register_back;
    private TextView register_ok;
    private EditText register_phone;
    private EditText register_authcode;
    private Button register_btn_get;
    private EditText register_pwd;
    private ImageView register_pwd_look;
    private boolean isLook = false;
    private boolean isGet = false;
    private int num = 0;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
    }

    private void initView() {
        timer = new Timer();
        register_back = (ImageView) findViewById(R.id.register_back);
        register_ok = (TextView) findViewById(R.id.register_ok);
        register_phone = (EditText) findViewById(R.id.register_phone);
        register_authcode = (EditText) findViewById(R.id.register_authcode);
        register_btn_get = (Button) findViewById(R.id.register_btn_get);
        register_pwd = (EditText) findViewById(R.id.register_pwd);
        register_pwd_look = (ImageView) findViewById(R.id.register_pwd_look);

        register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register_pwd_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLook = !isLook;
                register_pwd_look.setImageResource(
                        isLook ? R.mipmap.openeye : R.mipmap.closeeye);

                register_pwd.setInputType(
                        isLook ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_TEXT
                                : InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            }
        });

        register_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        register_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                checkEditText(s.toString(),register_phone,"手机号不能为空");
            }
        });

        register_authcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                checkEditText(s.toString(),register_authcode,"验证码不能为空");
            }
        });

        register_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                checkEditText(s.toString(),register_pwd,"密码不能为空");
            }
        });

        register_btn_get.setOnClickListener(this);
    }

    private void checkEditText(String s,EditText ed,String msg){
        if(TextUtils.isEmpty(s)){
            ed.setError(msg);
        }
    }

    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                num++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        register_btn_get.setText((60-num)+"(S)");
                        if(num == 60){
                            timer.cancel();
                            isGet = true;
                            register_btn_get.setBackgroundResource(R.drawable.register_btn_null);
                        }
                    }
                });
            }
        },0,1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_get:
                if(isGet){

                }
                break;
        }
    }

    private void submit() {
        // validate
        String phone = register_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if(phone.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\\\d{8}$")){
            ToastUtil.toToast("手机号码格式不正确，请重新输入");
            return;
        }

        ToastUtil.toToast("已发送验证码消息");
        isGet = false;
        register_btn_get.setBackgroundResource(R.drawable.register_btn);

    }

    private void register(){
        String phone = register_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
//        String authcode = register_authcode.getText().toString().trim();
//        if (TextUtils.isEmpty(authcode)) {
//            Toast.makeText(this, "输入验证码", Toast.LENGTH_SHORT).show();
//            return;
//        }
        String pwd = register_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "输入密码", Toast.LENGTH_SHORT).show();
            return;
        }else if(pwd.matches("^(?!\\d+$)[\\da-zA-Z]+$")){
            ToastUtil.toToast("密码不能为纯数字");
            return;
        }
        Intent intent = new Intent(RegisterActivity.this,UserInfoSetting.class);
        Bundle bundle = new Bundle();
        bundle.putString("phone",phone);
        bundle.putString("pwd",pwd);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
