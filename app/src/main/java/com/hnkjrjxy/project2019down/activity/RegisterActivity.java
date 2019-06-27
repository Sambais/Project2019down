package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.SendYZM;
import com.hnkjrjxy.project2019down.util.ToastUtil;

import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

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
    private boolean isRegister;
    private String phone;
    private String yzm = "";

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
                        register_btn_get.setText((120-num)+"(S)");
                        if(num == 120){
                            num = 0;
                            isGet = true;
                            register_btn_get.setBackgroundResource(R.drawable.register_btn);
                            register_btn_get.setEnabled(true);
                            register_btn_get.setText("获取验证码");
                            timer.cancel();
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
                if(!isGet){
                    submit();
                }
                break;
        }
    }

    //发送验证码
    private void submit() {
        // validate
        phone = register_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        isRegister = false;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",MyApplication.getToken());
        jsonObject.addProperty("phone", phone);
        Http.Post(this, "Data/Select", jsonObject.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(jsonObject.optString("msg").equals("S")){
                    ToastUtil.toToast("该号码已注册过了");
                    return;
                }else{
                    isRegister = true;
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isRegister){
                    String regex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
                    if(phone.length() != 11){
                        ToastUtil.toToast("手机号应为11位,请检查您的手机号是否正确");
                        return;
                    }else{
                        Pattern p = Pattern.compile(regex);
                        Matcher m = p.matcher(phone);
                        if(!m.matches()){
                            ToastUtil.toToast("手机号码格式不正确，请重新输入");
                            return;
                        }
                    }
                    Random random = new Random();
                    for (int i = 0; i < 6; i++) {
                        yzm += (random.nextInt(9))+"";
                    }
                    SendYZM.send(RegisterActivity.this,phone,yzm);
                    isGet = false;
                    register_btn_get.setBackgroundResource(R.drawable.register_btn_null);
                    register_btn_get.setEnabled(false);
                    startTimer();
                }
            }
        },500);
    }

    private void register() {
        String phone = register_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", MyApplication.getToken());
        jsonObject.addProperty("phone", phone);
        Http.Post(this, "Data/Select", jsonObject.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject.optString("msg").equals("S")) {
                    ToastUtil.toToast("该号码已注册过了");
                    return;
                }
            }
        });

        String regex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        if (phone.length() < 11) {
            ToastUtil.toToast("手机号应为11位,请检查您的手机号是否正确");
            return;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            if (!m.matches()) {
                ToastUtil.toToast("手机号码格式不正确，请重新输入");
                return;
            }

            String authcode = register_authcode.getText().toString().trim();
            if (TextUtils.isEmpty(authcode)) {
                Toast.makeText(this, "输入验证码", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!authcode.equals(yzm)){
                Toast.makeText(this, "验证码不正确!", Toast.LENGTH_SHORT).show();
                return;
            }
            String pwd = register_pwd.getText().toString().trim();
            if (TextUtils.isEmpty(pwd)) {
                Toast.makeText(this, "输入密码", Toast.LENGTH_SHORT).show();
                return;
            } else if (!pwd.matches("^(?!\\d+$)[\\da-zA-Z]+$")) {
                ToastUtil.toToast("密码不能为纯数字");
                return;
            }
            Intent intent = new Intent(RegisterActivity.this, UserInfoSetting.class);
            Bundle bundle = new Bundle();
            bundle.putString("phone", phone);
            bundle.putString("pwd", pwd);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    }
}
