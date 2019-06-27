package com.hnkjrjxy.project2019down.fragment.zhufragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.activity.LoginActivity;
import com.hnkjrjxy.project2019down.activity.LookPhoto;
import com.hnkjrjxy.project2019down.activity.SettingActivity;
import com.hnkjrjxy.project2019down.entry.Invitation;
import com.hnkjrjxy.project2019down.fragment.Fragment_5;
import com.hnkjrjxy.project2019down.util.DateUtil;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.view.MySwipeRefreshLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.wx.goodview.GoodView;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Fragment_self extends Fragment {

    private static final String TAG = "Fragment_self";

    private RecyclerView a4_list;
    private static MyAdapter listAdapter;
    private ImageView setting;
    private LinearLayoutManager layoutManager;
    private MySwipeRefreshLayout swipeRefreshLayout;
    public static ArrayList<Invitation.DataBean> dataBeans;
    private int num = 0;
    public static int num3 = 0;
    private static Context context;
    private static Integer colors[] = {R.color.c1, R.color.c2, R.color.c3, R.color.c4, R.color.c5, R.color.c6, R.color.c7};
    private SmartRefreshLayout self_smatr;
    private int ztpd = 0;
    private TextView self_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a4, container, false);
        initView(view, 0);
        return view;
    }

    public void initView(View view, int zt) {
        ztpd = zt;
        if (ztpd == 1) {
            Log.i("TAG", "initView: 我想到顶部");
            //方法重载，直接用，默认带动画效果慢慢展开或折叠，拿走不谢
            a4_list.scrollToPosition(0);
        } else {
            dataBeans = new ArrayList<>();
            context = getActivity();
            self_text = (TextView) view.findViewById(R.id.self_text);
            a4_list = (RecyclerView) view.findViewById(R.id.a4_list);
            setting = (ImageView) view.findViewById(R.id.setting);
            getData();
            self_text.setText(MyApplication.sharedPreferences.getString("username","null"));
            layoutManager = new LinearLayoutManager(getActivity());
            a4_list.setLayoutManager(layoutManager);
            listAdapter = new MyAdapter();
            a4_list.setAdapter(listAdapter);
            //设置Item增加、移除动画
            a4_list.setItemAnimator(new DefaultItemAnimator());
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.isIsLogin()) {
                        Intent intent = new Intent(getActivity(), SettingActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
            self_smatr = (SmartRefreshLayout) view.findViewById(R.id.self_smatr);
            self_smatr.setEnableRefresh(false);//启用刷新
            self_smatr.setEnableLoadmore(true);
            self_smatr.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    getData();
                    refreshlayout.finishLoadmore(2500);//传入false表示加载失败
                }
            });
        }
    }

    public static void getData() {
        if (num3==0){
            dataBeans=new ArrayList<>();
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", MyApplication.getToken());
        jsonObject.addProperty("id", MyApplication.sharedPreferences.getInt("id", 0));
        jsonObject.addProperty("num", num3);
        Log.i(TAG, "getData: " + jsonObject.toString());
        Http.Post(context, "Invitation/GetMeInvitation",
                jsonObject.toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
                        Log.i(TAG, "onResponse: " + object);
                        if (object.optString("msg").equals("S")) {
                            Gson gson = new Gson();
                            Invitation invitation = gson.fromJson(object.toString(), Invitation.class);
                            for (int i = 0; i < invitation.getData().size(); i++) {
                                dataBeans.add(invitation.getData().get(i));
                            }
                            num3++;
                            listAdapter.notifyDataSetChanged();
                        } else {
                            Toasty.error(context, "到底啦！").show();
                        }
                    }
                });
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.mycard, null);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
            //用户名
            myViewHolder.textView.setText(dataBeans.get(i).getInfo().getSendname() + "");
            //头像底部随机rgb颜色
            Random random = new Random();
            num = random.nextInt(7);
            myViewHolder.tv_like.setText(random.nextInt(300) + "");
            myViewHolder.imageView.setImageResource(Fragment_5.colors[num]);
            //帖子频道
            myViewHolder.tv_channel.setText("#" + MyApplication.allpindao.get(dataBeans.get(i).getInfo().getChannelId() - 1) + "之海");
            //帖子正文
            myViewHolder.tv_content.setText(dataBeans.get(i).getInfo().getDescription() + "");
            //头像中心文字首字
            myViewHolder.touxiang.setText(dataBeans.get(i).getInfo().getSendname().substring(0, 1) + "");
            //头像颜色为白色
            myViewHolder.touxiang.setTextColor(Color.WHITE);
            //右下角发帖时间
            try {
                myViewHolder.tv_time.setText(DateUtil.getDate(dataBeans.get(i).getInfo().getTime() + ""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dataBeans.get(i).getInvitationImages().size() == 0) {
                myViewHolder.tv_photo.setVisibility(View.GONE);
            } else {
                myViewHolder.tv_photo.setVisibility(View.VISIBLE);
                Glide.with(context).load(Http.imgpath + dataBeans.get(i).getInvitationImages().get(0).getImagePath())
                        .placeholder(R.mipmap.huazhi)
                        //当加载图片失败时，通过error(Drawable drawable)方法设置加载失败后的图片显示：
                        .error(R.mipmap.ic_launcher)
                        //使用centerCrop是利用图片图填充ImageView设置的大小，如果ImageView的Height是match_parent则图片就会被拉伸填充
                        .centerCrop()
                        //使用centerCrop是利用图片图填充ImageView设置的大小，如果ImageView的Height是match_parent则图片就会被拉伸填充
                        .fitCenter()
                        // 缓存所有版本的图像（默认行为）
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(myViewHolder.tv_photo);
            }

            myViewHolder.tv_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,LookPhoto.class);
                    intent.putExtra("myuri",Http.imgpath + dataBeans.get(i).getInvitationImages().get(0).getImagePath());
                    context.startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });


            myViewHolder.dianzan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MyApplication.isIsLogin()) {
                        if (myViewHolder.cheack) {
                            collection1(view, myViewHolder);
                        } else {
                            collection2(view, myViewHolder);
                        }
                        myViewHolder.cheack = !myViewHolder.cheack;
                    }else {
                        Toasty.error(context, "请先登录").show();
                    }
                }
            });

            myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.isIsLogin()) {
                        Toasty.success(context, "用户信息").show();
                    }else {
                        Toasty.error(context, "请先登录").show();
                    }
                }
            });
            myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.isIsLogin()) {
//                            Toast.makeText(context, "" + i, Toast.LENGTH_SHORT).show();
                    }else {
                        Toasty.error(context, "请先登录").show();
                    }
                }
            });
        }


        //点赞变红
        public void collection1(View view, MyViewHolder myViewHolder) {
            ((ImageView) view).setImageResource(R.mipmap.love2);
            myViewHolder.mgoodView.setTextInfo("赞", Color.parseColor("#f66467"), 12);
            myViewHolder.tv_like.setText((Integer.parseInt(myViewHolder.tv_like.getText() + "") + 1) + "");
            myViewHolder.mgoodView.show(view);
        }

        //点赞恢复
        public void collection2(View view, MyViewHolder myViewHolder) {
            ((ImageView) view).setImageResource(R.mipmap.love1);
            myViewHolder.mgoodView.setTextInfo("取消赞", Color.parseColor("#c9c9c9"), 12);
            myViewHolder.tv_like.setText((Integer.parseInt(myViewHolder.tv_like.getText() + "") - 1) + "");
            myViewHolder.mgoodView.show(view);
        }

        @Override
        public int getItemCount() {
            return dataBeans.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView dianzan;
            ImageView tv_photo;
            TextView touxiang;
            TextView tv_time;
            CircleImageView imageView;
            CardView cardView;
            LinearLayout linearLayout;
            GoodView mgoodView;
            Boolean cheack = true;
            TextView tv_channel;
            TextView tv_content;
            TextView tv_like;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.m1);
                tv_photo = itemView.findViewById(R.id.tv_photo);
                touxiang = itemView.findViewById(R.id.touxiang);
                tv_time = itemView.findViewById(R.id.tv_time);
                textView = itemView.findViewById(R.id.t1);
                tv_channel = itemView.findViewById(R.id.tv_channel);
                tv_content = itemView.findViewById(R.id.tv_content);
                cardView = itemView.findViewById(R.id.card);
                dianzan = itemView.findViewById(R.id.dianzan);
                linearLayout = itemView.findViewById(R.id.user_xinxi);
                tv_like = itemView.findViewById(R.id.tv_like);
                mgoodView = new GoodView(context);
            }
        }

    }
}
