package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hnkjrjxy.project2019down.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
    }

    private void initView() {
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
                        isLook ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        register_btn_get.setOnClickListener(this);
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

        String authcode = register_authcode.getText().toString().trim();
        if (TextUtils.isEmpty(authcode)) {
            Toast.makeText(this, "输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = register_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
