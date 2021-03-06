package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MainActivity;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.util.ClearDataUtils;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.ToastUtil;
import com.suke.widget.SwitchButton;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class SettingActivity extends Activity {
    private ListView set_list;
    private SettingAdapter settingAdapter;
    private String data[] = {
            "个人资料",
            "评论昵称",
            "我喜欢的",
            "声音提醒",
            "给我们一点建议",
            "清除缓存",
            "检查更新",
            "退出登录"
    };
    private ImageView set_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        initView();
    }

    private void initView() {
        set_list = (ListView) findViewById(R.id.set_list);
        settingAdapter = new SettingAdapter();
        set_list.setAdapter(settingAdapter);
        set_back = (ImageView) findViewById(R.id.set_back);
        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        set_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Toasty.warning(SettingActivity.this,"改功能未实现").show();
                        break;
                    case 2:
                        Toasty.warning(SettingActivity.this,"改功能未实现").show();
                        break;
                    case 4:
                        Toasty.warning(SettingActivity.this,"改功能未实现").show();
                        break;
                    case 5:
                        ClearDataUtils.clearAllCache(SettingActivity.this);
                        settingAdapter.notifyDataSetChanged();
                        ToastUtil.toToast("缓存已清除");
                        break;
                    case 6:
                        Toasty.success(SettingActivity.this,"暂无最新版本").show();
                        break;
                    case 7:
                        new MaterialDialog.Builder(SettingActivity.this)
                                .title("你要走了吗？")
                                .positiveColor(Color.parseColor("#5CACEE"))
                                .positiveText("退出登录")
                                .negativeColor(Color.parseColor("#5CACEE"))
                                .negativeText("留下来")
                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        switch (which){
                                            case POSITIVE:
                                                JsonObject jsonObject = new JsonObject();
                                                jsonObject.addProperty("id",MyApplication.sharedPreferences.getInt("id",0));
                                                Http.Post(SettingActivity.this, "Data/Exit", jsonObject.toString(), new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject jsonObject) {
                                                    }
                                                });
                                                MyApplication.editor.putString("age",null);
                                                MyApplication.editor.putString("sex",null);
                                                MyApplication.editor.putString("username",null);
                                                MyApplication.editor.putInt("id",0);
                                                MyApplication.editor.commit();
                                                MyApplication.setIsLogin(false);
                                                startActivity(new Intent(SettingActivity.this,MainActivity.class));
                                                finish();
                                                break;
                                            case  NEGATIVE:
                                                dialog.dismiss();
                                                break;
                                        }
                                    }
                                })
                                .contentColor(Color.WHITE)
                                .backgroundColorRes(R.color.alertback)
                                .contentGravity(GravityEnum.END)
                                .show();
                        break;
                }
            }
        });
    }

    class SettingAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(SettingActivity.this).inflate(R.layout.set_item, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(position, (String) getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(int position, String object, ViewHolder holder) {
            if (position == 0) {
                holder.switchButton.setVisibility(View.GONE);
                holder.setItemM1.setVisibility(View.GONE);
                holder.setItemT1.setText(object);
                //todo
                holder.setItemT2.setText(
                        MyApplication.sharedPreferences.getString("age","null")+","
                        +MyApplication.sharedPreferences.getString("sex","null"));
            } else if (position == 3) {
                holder.switchButton.setChecked(MyApplication.sharedPreferences.getBoolean("sytx", false));
                holder.setItemT1.setText(object);
                holder.setItemM1.setVisibility(View.GONE);
                holder.switchButton.setVisibility(View.VISIBLE);
            }  else if (position == data.length - 3) {
                holder.switchButton.setVisibility(View.GONE);
                holder.setItemM1.setVisibility(View.VISIBLE);
                holder.setItemT2.setVisibility(View.VISIBLE);
                holder.setItemT1.setText(object);
                try {
                    holder.setItemT2.setText(ClearDataUtils.getTotalCacheSize(SettingActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (position == data.length - 2) {
                holder.switchButton.setVisibility(View.GONE);
                holder.setItemM1.setVisibility(View.VISIBLE);
                holder.setItemT2.setVisibility(View.VISIBLE);
                holder.setItemT1.setText(object);
                holder.setItemT2.setText(getVerName(SettingActivity.this)+"");
            } else {
                holder.switchButton.setVisibility(View.GONE);
                holder.setItemM1.setVisibility(View.VISIBLE);
                holder.setItemT2.setVisibility(View.GONE);
                holder.setItemT1.setText(object);
            }

            //滑动开关监听
            holder.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    MyApplication.editor.putBoolean("sytx", isChecked);
                    MyApplication.editor.commit();
                }
            });
        }

        protected class ViewHolder {
            private TextView setItemT1;
            private TextView setItemT2;
            private ImageView setItemM1;
            private SwitchButton switchButton;

            public ViewHolder(View view) {
                setItemT1 = (TextView) view.findViewById(R.id.set_item_t1);
                setItemT2 = (TextView) view.findViewById(R.id.set_item_t2);
                setItemM1 = (ImageView) view.findViewById(R.id.set_item_m1);
                switchButton = (SwitchButton) view.findViewById(R.id.switchbutton);
            }
        }
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
