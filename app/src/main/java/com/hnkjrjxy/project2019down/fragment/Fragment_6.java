/**
 *
 */
package com.hnkjrjxy.project2019down.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.activity.LookPhoto;
import com.hnkjrjxy.project2019down.entry.Invitation;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_home;
import com.hnkjrjxy.project2019down.util.DateUtil;
import com.hnkjrjxy.project2019down.util.Http;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.wx.goodview.GoodView;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class Fragment_6 extends Fragment {

    private static final String TAG = "Fragment_5";

    private static LinearLayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static GeneralAdapter generalAdapter;
    private static Context context;
    private static int num = 20;
    private static String asd = "123";
    int i = 0;
    //目标项是否在最后一个可见项之后
    private static boolean mShouldScroll;
    //记录目标项位置
    private static int mToPosition;
    private int kejian;
    private TextView tishi;
    public static boolean login = false;
    private static Invitation invitation;
    public static ArrayList<Invitation.DataBean> list;
    private ClassicsFooter foot;
    private SmartRefreshLayout refreshlayout;

//    public static void FirstTop(JSONObject jsonObject) {
//        if (jsonObject.optString("msg").equals("S")) {
//            Gson gson = new Gson();
//            Invitation invitation = gson.fromJson(jsonObject.toString(), Invitation.class);
//            for (int i = 0; i < invitation.getData().size(); i++) {
//                list.set(0, invitation.getData().get(i));
//            }
//            generalAdapter.notifyDataSetChanged();
//        }
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr5, container, false);
        initView(view);
        return view;
    }

    public Fragment_6 setData(int s) {
        kejian = s;
        return this;
    }

    public static void Weizhi(int n) {
        asd = "789";
        recyclerView.scrollToPosition(0);
        recyclerView.setFocusableInTouchMode(false);
        recyclerView.setFocusable(false);
        recyclerView.setHasFixedSize(true);
    }

    public static void Data(JSONObject object) {
        if (object.optString("msg").equals("S")) {
            Gson gson = new Gson();
            Invitation invitation = gson.fromJson(object.toString(), Invitation.class);
            if (invitation.getData() != null && invitation.getData().size() != 0) {
                Log.i("Fragment5", "Data: --------------" + invitation.getData().get(0).getInfo().getId());
                Log.i("Fragment5", "Data: --------------" + invitation.getData().get(0).getInfo().getDescription());
                Log.i("Fragment5", "Data: --------------" + invitation.getData().get(0).getInfo().getSendname());
                Log.i("Fragment5", "Data: --------------" + invitation.getData().get(0).getInfo().getTime());
                Log.i("Fragment5", "Data: --------------" + invitation.getData().get(0).getInfo().getChannelId());
                Log.i("Fragment5", "Data: --------------" + invitation.getData().get(0).getInfo().getUid());
                Log.i("Fragment5", "Data: --------------" + invitation.getData().get(0).getInvitationImages());
                Log.i("Fragment5", "Data: --------------" + invitation.getData().size());
//                Collections.reverse(list);
                for (int i = 0; i < invitation.getData().size(); i++) {
                    list.add(invitation.getData().get(i));
                }
                generalAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(context, "暂无动态", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toasty.error(context, "到底啦！").show();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (MyApplication.sharedPreferences.getInt("id", 0) != 0) {
                login = true;
            }
        }
    }

    public void initView(View view) {
        list = new ArrayList<>();
        context = getActivity();
        foot = (ClassicsFooter) view.findViewById(R.id.foot);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tishi = (TextView) view.findViewById(R.id.tishi);
        if (kejian == 0) {
            tishi.setVisibility(View.GONE);
        }
        //RecyclerView绑定适配器
        //设置LayoutManager为LinearLayoutManager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        generalAdapter = new GeneralAdapter();
        generalAdapter.setHasStableIds(true);
        recyclerView.setAdapter(generalAdapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        refreshlayout = (SmartRefreshLayout) view.findViewById(R.id.refreshlayout);
        refreshlayout.setEnableRefresh(false);//// 启用刷新
        refreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Fragment_home.getFragment6Data();
                refreshlayout.finishLoadmore(2000);//传入false表示加载失败
            }
        });
    }


    class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.MyViewHolder> {
        //当前上下文对象
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            //实例化得到Item布局文件的View对象
            View v = LayoutInflater.from(context).inflate(R.layout.mycard, null);
            //返回MyViewHolder的对象
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
            //用户名
            myViewHolder.textView.setText(list.get(i).getInfo().getSendname() + "");
            //头像底部随机rgb颜色
            Random random = new Random();
            num = random.nextInt(7);
            myViewHolder.tv_like.setText(random.nextInt(300)+"");
            myViewHolder.imageView.setImageResource(Fragment_5.colors[num]);
            //帖子频道
            myViewHolder.tv_channel.setText("#" + MyApplication.allpindao.get(list.get(i).getInfo().getChannelId()-1) + "之海");
            //帖子正文
            myViewHolder.tv_content.setText(list.get(i).getInfo().getDescription() + "");
            //头像中心文字首字
            myViewHolder.touxiang.setText(list.get(i).getInfo().getSendname().substring(0, 1) + "");
            //头像颜色为白色
            myViewHolder.touxiang.setTextColor(Color.WHITE);
            //右下角发帖时间
            try {
                myViewHolder.tv_time.setText(DateUtil.getDate(list.get(i).getInfo().getTime() + ""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (list.get(i).getInvitationImages().size() == 0) {
                myViewHolder.tv_photo.setVisibility(View.GONE);
            } else {
                myViewHolder.tv_photo.setVisibility(View.VISIBLE);
                Glide.with(context).load(Http.imgpath + list.get(i).getInvitationImages().get(0).getImagePath())
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
                    intent.putExtra("myuri",Http.imgpath + list.get(i).getInvitationImages().get(0).getImagePath());
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
            return list.size();
        }


        //此方法必须重写，否则容易出现数据错乱的情况
        @Override
        public int getItemViewType(int position) {
            return position;
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
