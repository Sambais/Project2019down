package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.ToastUtil;
import com.hnkjrjxy.project2019down.view.Usersetting_view;

import org.json.JSONObject;

import java.util.ArrayList;

public class UserInfoSetting extends Activity {
    private ListView usersetting_lv;
    private BaseAdapter adapter;
    private ArrayList<String[]> is,infos;
    private String age,sex;
    private JsonObject jsonObject;
    private String[] msgs = {
            "你需要填写少量的个人信息，通过智能匹配，遇见有同感的人。你的性别是？",
            "你的年龄是？",
            "确认后，你的个人信息无法修改？"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersetting);
        initView();
        Bundle bundle = getIntent().getExtras();
        jsonObject = new JsonObject();
        jsonObject.addProperty("token",MyApplication.token);
        jsonObject.addProperty("phone",bundle.getString("phone"));
        jsonObject.addProperty("pwd",bundle.getString("pwd"));
    }

    private void initView() {
        is = new ArrayList<>();
        infos = new ArrayList();
        is.add(new String[]{"260","男","女"});
        is.add(new String[]{
                "400",
                "80后",
                "85后",
                "90后",
                "95后",
                "00后",
                "05后",
        });
        is.add(new String[]{"400","确定"});
        infos.add(is.get(0));
        usersetting_lv = (ListView) findViewById(R.id.usersetting_lv);
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return infos.size();
            }

            @Override
            public Object getItem(int position) {
                return infos.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(UserInfoSetting.this).inflate(R.layout.usersetting_item1, null);
                    convertView.setTag(new ViewHolder(convertView));
                }
                initializeViews(position,(String[])getItem(position), (ViewHolder) convertView.getTag(),convertView);
                return convertView;
            }

            private void initializeViews(final int position, String[] object, final ViewHolder holder, View convertView) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,convertView.getHeight());
                convertView.setLayoutParams(params);
                holder.usersettingV.setMsg(msgs[position%3]);
                holder.usersettingV.setSize(Integer.parseInt(object[0]));
                String[] strs = new String[object.length-1];
                for (int i = 1; i < object.length; i++) {
                    strs[i-1] = object[i];
                }
                holder.usersettingV.setInfos(strs);
                holder.usersettingV.setOnCallBack(new Usersetting_view.OnCallBack() {
                    @Override
                    public void callback(String object) {
                        if(object.equals("确定") && sex != null && age != null){
                            showAlert();
                        }else{
                            if(object.length() == 1){
                                sex = object;
                            }else{
                               age = object;
                            }
                            if(infos.size() < 3){
                                infos.add(is.get(infos.size()));
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
            }

            class ViewHolder {
                private Usersetting_view usersettingV;

                public ViewHolder(View view) {
                    usersettingV = (Usersetting_view) view.findViewById(R.id.usersetting_v);
                }
            }
        };
        usersetting_lv.setAdapter(adapter);
    }

    private void showAlert(){
        new MaterialDialog.Builder(UserInfoSetting.this)
                .title("Title")
                .inputRangeRes(2, 8, R.color.colorPrimary)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入真身昵称", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialogs, CharSequence input) {
                        jsonObject.addProperty("username",input.toString());
                        submit();
                    }
                })
                .positiveColor(Color.parseColor("#5CACEE"))
                .positiveText("确定")
                .show();
    }

    private void submit() {
        jsonObject.addProperty("sex",sex);
        jsonObject.addProperty("age",age);
        Http.Post(this, "/Data/Register", jsonObject.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(jsonObject.optString("msg").equals("S")){
                    ToastUtil.toToast("注册成功！");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },100);
                }
            }
        });
    }
}
