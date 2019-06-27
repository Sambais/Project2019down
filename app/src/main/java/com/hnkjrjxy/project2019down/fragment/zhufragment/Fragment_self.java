package com.hnkjrjxy.project2019down.fragment.zhufragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.activity.LoginActivity;
import com.hnkjrjxy.project2019down.activity.SettingActivity;
import com.hnkjrjxy.project2019down.entry.Invitation;
import com.hnkjrjxy.project2019down.fragment.Fragment_5;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.ToastUtil;
import com.hnkjrjxy.project2019down.view.MySwipeRefreshLayout;
import com.wx.goodview.GoodView;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Fragment_self extends Fragment {

    private static final String TAG = "Fragment_self";

    private RecyclerView a4_list;
    private MyAdapter listAdapter;
    private ImageView setting;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout.OnRefreshListener listener;
    private MySwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Invitation.DataBean> dataBeans;
    private int num = 0;
    private Context context;
    private static Integer colors[] = {R.color.c1, R.color.c2, R.color.c3, R.color.c4, R.color.c5, R.color.c6, R.color.c7};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a4, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        dataBeans = new ArrayList<>();
        context = getActivity();
        a4_list = (RecyclerView) view.findViewById(R.id.a4_list);
        setting = (ImageView) view.findViewById(R.id.setting);
        swipeRefreshLayout = (MySwipeRefreshLayout) view.findViewById(R.id.myswiperefreshlayout);
        //设置下拉刷新环形加载条的颜色，最多使用四个颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.color1);
        //设置下拉是否开始缩放，起点是20的高度，最多到达100的高度
        swipeRefreshLayout.setProgressViewOffset(false, 20, 100);
        getData();
        layoutManager = new LinearLayoutManager(getActivity());
        a4_list.setLayoutManager(layoutManager);
        listAdapter = new MyAdapter();
        a4_list.setAdapter(listAdapter);
        //设置Item增加、移除动画
        a4_list.setItemAnimator(new DefaultItemAnimator());
        a4_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) { }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                //得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部
                //lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount()-1   则改控件处于最底部
                //dx>0 则表示 右滑 ， dx<0 表示 左滑
                //dy <0 表示 上滑， dy>0 表示下滑
                //通过这几个参数就可以监听 滑动方向的状态。
                //判断是否向下滑动，如果向下滑动即将到底部的时候进行预加载
                if (dy > 0) {
                    //双重判断，以防滑动太快导致没有检测到滑动的位置信息
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 4 ||
                            lastPosition == recyclerView.getLayoutManager().getItemCount() - 3) {
                        //在此处再次拿数据进行适配器的刷新
                        getData();
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isIsLogin()){
                    Intent intent=new Intent(getActivity(),SettingActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        layoutlistener();
    }

    private void getData() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",MyApplication.getToken());
        jsonObject.addProperty("id",MyApplication.sharedPreferences.getInt("id",0));
        jsonObject.addProperty("num",num);
        Log.i(TAG, "getData: "+jsonObject.toString());
        Http.Post(getActivity(), "Invitation/GetMeInvitation",
                jsonObject.toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
                        if(object.optString("msg").equals("S")){
                            Gson gson = new Gson();
                            Invitation invitation = gson.fromJson(object.toString(),Invitation.class);
                            for (int i = 0; i < invitation.getData().size(); i++) {
                                dataBeans.add(invitation.getData().get(i));
                            }
                            num++;
                            listAdapter.notifyDataSetChanged();
                        }else{
                            ToastUtil.toToast(object.optString("data"));
                        }
                    }
        });
    }

    private void layoutlistener() {
        //下拉刷新SwipeRefreshLayout监听

        listener = new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        };

        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.mycard, null);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyAdapter.MyViewHolder myViewHolder, final int i) {
            //用户名
            myViewHolder.textView.setText(dataBeans.get(i).getInfo().getSendname() + "");
            //头像底部随机rgb颜色
            Random random = new Random();
            num = random.nextInt(7);
            myViewHolder.tv_like.setText(random.nextInt(300)+"");
            myViewHolder.imageView.setImageResource(colors[num]);
            //帖子频道
            myViewHolder.tv_channel.setText("#" + MyApplication.allpindao.get(dataBeans.get(i).getInfo().getChannelId()-1) + "之海");
            //帖子正文
            myViewHolder.tv_content.setText(dataBeans.get(i).getInfo().getDescription() + "");
            //头像中心文字首字
            myViewHolder.touxiang.setText(dataBeans.get(i).getInfo().getSendname().substring(0, 1) + "");
            //头像颜色为白色
            myViewHolder.touxiang.setTextColor(Color.WHITE);
            //右下角发帖时间
            myViewHolder.tv_time.setText(dataBeans.get(i).getInfo().getTime() + "");
            if (dataBeans.get(i).getInvitationImages().size() == 0) {
                myViewHolder.tv_photo.setVisibility(View.GONE);
            } else {
                myViewHolder.tv_photo.setVisibility(View.VISIBLE);
                Glide.with(context).load(Http.imgpath + dataBeans.get(i).getInvitationImages().get(0).getImagePath())
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
                        Toast.makeText(context, "用户信息", Toast.LENGTH_SHORT).show();
                    }else {
                        Toasty.error(context, "请先登录").show();
                    }
                }
            });
            myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.isIsLogin()) {
                        Toast.makeText(context, "" + i, Toast.LENGTH_SHORT).show();
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
            myViewHolder.tv_like.setText((Integer.parseInt(myViewHolder.tv_like.getText()+"")+1)+"");
            myViewHolder.mgoodView.show(view);
        }

        //点赞恢复
        public void collection2(View view, MyViewHolder myViewHolder) {
            ((ImageView) view).setImageResource(R.mipmap.love1);
            myViewHolder.mgoodView.setTextInfo("取消赞", Color.parseColor("#c9c9c9"), 12);
            myViewHolder.tv_like.setText((Integer.parseInt(myViewHolder.tv_like.getText()+"")-1)+"");
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
