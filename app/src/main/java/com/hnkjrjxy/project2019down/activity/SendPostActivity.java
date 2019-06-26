package com.hnkjrjxy.project2019down.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.util.BitmapUtil;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.MyGlideEngine;
import com.hnkjrjxy.project2019down.util.PhotoUtil;
import com.hnkjrjxy.project2019down.util.ToastUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SendPostActivity extends Activity {
    private ImageView post_back;
    private TextView post_send;
    private EditText add_text;
    private GridView add_image;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private PhotoAdapter photoAdapter;
    private List<Uri> result;
    private ListView check_list;
    private DbAdapter dbAdapter;
    private String title[]={"发布身份","选择话题","画质","互动权限"};
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private static final String TAG = "SendPostActivity";
    private String img = "";
    private int channelid = 0;
    private String content = "";
    private boolean isOK = false;
    private String pindao=null,huazhi="正常";
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpost);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(SendPostActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(SendPostActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        }


//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            //申请WRITE_EXTERNAL_STORAGE权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
//        }
    }



    private void initView() {
        result = new ArrayList<>();
        post_back = (ImageView) findViewById(R.id.post_back);
        post_send = (TextView) findViewById(R.id.post_send);
        add_text = (EditText) findViewById(R.id.add_text);
        add_image = (GridView) findViewById(R.id.add_image);
        check_list = (ListView) findViewById(R.id.check_list);

        photoAdapter = new PhotoAdapter();
        add_image.setAdapter(photoAdapter);

        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    Matisse.from(SendPostActivity.this)
                            .choose(MimeType.of(MimeType.JPEG,MimeType.PNG))//图片类型
                            .countable(true)//true:选中后显示数字;false:选中后显示对号
                            .maxSelectable(1)//可选的最大数
                            .capture(false)//选择照片时，是否显示拍照
                            .captureStrategy(new CaptureStrategy(true, "com.example.xx.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                            .imageEngine(new MyGlideEngine())//图片加载引擎
                            .forResult(REQUEST_CODE_CHOOSE);//
            }
        });

        dbAdapter=new DbAdapter();
        check_list.setAdapter(dbAdapter);

        check_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        new MaterialDialog.Builder(SendPostActivity.this)
                                .title("请选择话题")
                                .titleColor(Color.parseColor("#5CACEE"))
                                .negativeText("取消")
                                .items(MyApplication.allpindao)
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        Toast.makeText(SendPostActivity.this, "我的id"+which + "    频道: " + text , Toast.LENGTH_SHORT).show();
                                        pindao= (String) text;
                                        channelid = which+1;
                                        dbAdapter.notifyDataSetChanged();
                                    }
                                })
                                .show();
                        break;
                    case 2:
                        new MaterialDialog.Builder(SendPostActivity.this)
                                .title("请选择话题")
                                .titleColor(Color.parseColor("#5CACEE"))
                                .negativeText("取消")
                                .items(new String[]{"正常","原图"})
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        huazhi= (String) text;
                                        dbAdapter.notifyDataSetChanged();
                                    }
                                })
                                .show();
                        break;
                }
            }
        });

        post_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = add_text.getText().toString();
                if(TextUtils.isEmpty(content)){
                    ToastUtil.toToast("请输入要分享的内容");
                    return;
                }else if(channelid == 0){
                    ToastUtil.toToast("请选择要分享到的频道");
                    return;
                }
                //正常画质
                if (huazhi.equals("正常")){
                    //设置要展示的图片列表url集合
//            Log.i(TAG, "onActivityResult: "+BitmapUtil.getRealPath(result.get(0),this));
                    for (int i = 0; i < result.size(); i++) {
                        img += BitmapUtil.bitmapToBase64(PhotoUtil.compressImageUpload(BitmapUtil.getRealPath(result.get(i),SendPostActivity.this)));
                    }
                }else {
                    //原图画质
                    for (int i = 0; i < result.size(); i++) {
                        img += BitmapUtil.bitmapToBase64(BitmapUtil.getRealPath(result.get(i),SendPostActivity.this));
                    }
                }
                //保持屏幕常亮，以防数据中断
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("img", img);
                jsonObject.addProperty("content", content);
                jsonObject.addProperty("time", sdf.format(new Date()));
                jsonObject.addProperty("token", MyApplication.getToken());
                jsonObject.addProperty("id", MyApplication.sharedPreferences.getInt("id",0));
                jsonObject.addProperty("channelid", channelid);
                materialDialog = new MaterialDialog.Builder(SendPostActivity.this)
                        .content("请等待...")
                        .progress(true, 0)
                        .show();
                materialDialog.setCanceledOnTouchOutside(false);
//                materialDialog.setCancelable(false);
                Http.Post(SendPostActivity.this, "Invitation/SetFileUpload", jsonObject.toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i(TAG, "onResponse: =========="+jsonObject);
                        if(jsonObject.optString("msg").equals("S")){
                            materialDialog.dismiss();
                            ToastUtil.toToast("发送成功~");
                            finish();
                        }
                    }
                });
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            Log.i(TAG, "onActivityResult: "+data);
            result = Matisse.obtainResult(data);
//            add_text.setText(result.toString());
            photoAdapter.notifyDataSetChanged();
        }
    }

    class DbAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return title.length-1;
        }

        @Override
        public Object getItem(int position) {
            return title[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(SendPostActivity.this).inflate(R.layout.post_item, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(position,(String)getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(int position,String object, ViewHolder holder) {
            if (position==0){
                holder.postItemM1.setImageResource(R.mipmap.shenfen);
                holder.postItemXinxi.setVisibility(View.VISIBLE);
                holder.postItemT1.setText(""+object);
                holder.postItemT2.setText(MyApplication.sharedPreferences.getString("username","null"));
            }else if (position==1){
                holder.postItemXinxi.setVisibility(View.VISIBLE);
                holder.postItemM2.setVisibility(View.GONE);
                holder.postItemM1.setImageResource(R.mipmap.pindao);
                if (pindao==null){
                    holder.postItemT2.setText("请选择话题");
                }else {
                    holder.postItemT2.setText(pindao+"");
                }
                holder.postItemT1.setText(""+object);
            }else {
                holder.postItemXinxi.setVisibility(View.VISIBLE);
                holder.postItemM2.setVisibility(View.GONE);
                holder.postItemM1.setImageResource(R.mipmap.huazhi);
                holder.postItemT2.setText(huazhi);
                holder.postItemT1.setText(""+object);
            }
        }

        protected class ViewHolder {
            private ImageView postItemM1;
            private TextView postItemT1;
            private LinearLayout postItemXinxi;
            private ImageView postItemM2;
            private TextView postItemT2;
            private ImageView setItemM1;

            public ViewHolder(View view) {
                postItemM1 = (ImageView) view.findViewById(R.id.post_item_m1);
                postItemT1 = (TextView) view.findViewById(R.id.post_item_t1);
                postItemXinxi = (LinearLayout) view.findViewById(R.id.post_item_xinxi);
                postItemM2 = (ImageView) view.findViewById(R.id.post_item_m2);
                postItemT2 = (TextView) view.findViewById(R.id.post_item_t2);
                setItemM1 = (ImageView) view.findViewById(R.id.set_item_m1);
            }
        }
    }

    class PhotoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (result.isEmpty()) {
                return 1;
            }
//            else if (result.size() < 9) {
//                return result.size() + 1;
//            }
            else {
                return result.size();
            }

        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(SendPostActivity.this).inflate(R.layout.setpost_photo, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(position, (Object) getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(int position, Object object, ViewHolder holder) {
            if (result.isEmpty()) {
                holder.setpostPhoto.setImageResource(R.mipmap.addimage);
            }
            //加载一张图片暂时不使用以下
//            else if (result.size() < 9) {
//                Glide.with(SendPostActivity.this)
//                        .asBitmap() // some .jpeg files are actually gif
//                        .load(result.get(position))
//                        .apply(new RequestOptions() {{
//                            override(Target.SIZE_ORIGINAL);
//                        }})
//                        .into(holder.setpostPhoto);
//            }
            else {
                Glide.with(SendPostActivity.this)
                        .asBitmap() // some .jpeg files are actually gif
                        .load(result.get(position))
                        .apply(new RequestOptions() {{
                            override(Target.SIZE_ORIGINAL);
                        }})
                        .into(holder.setpostPhoto);
            }
        }

        protected class ViewHolder {
            private ImageView setpostPhoto;

            public ViewHolder(View view) {
                setpostPhoto = (ImageView) view.findViewById(R.id.setpost_photo);
            }
        }
    }

}
