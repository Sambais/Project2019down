/**
 *
 */
package com.hnkjrjxy.project2019down.fragment.zhufragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.entry.Invitation;
import com.hnkjrjxy.project2019down.entry.ShanChat;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.ToastUtil;
import com.wx.goodview.GoodView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_chat extends Fragment {

    private static Integer colors[] = {R.color.c1, R.color.c2, R.color.c3, R.color.c4, R.color.c5, R.color.c6, R.color.c7};
    private String data[];
    private RecyclerView recyclerView;
    private GeneralAdapter generalAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<ShanChat.DataBean> dataBeans;
    private static final String TAG = "Fragment_chat";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a2, container, false);
        initView(view, 0);
        return view;
        //
    }

    public Fragment_chat setData(String[] data) {
        this.data = data;
        return this;
    }

    //
    private void MoveToPosition(int n) {
        recyclerView.scrollToPosition(n);
    }

    public void initView(View view, int zt) {
        dataBeans = new ArrayList<>();
        getData();
        if (zt == 1) {
            MoveToPosition(0);
            Toast.makeText(getContext(), "完成", Toast.LENGTH_SHORT).show();
        } else {
            recyclerView = (RecyclerView) view.findViewById(R.id.chat_recyclerview);
            //RecyclerView绑定适配器
            //设置LayoutManager为LinearLayoutManager
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            generalAdapter = new GeneralAdapter();
            recyclerView.setAdapter(generalAdapter);
            //设置Item增加、移除动画
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                }

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
                        }
                    }
                }
            });
        }
    }

    private void getData() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",MyApplication.getToken());
        Log.i(TAG, "getData: "+jsonObject.toString());
        Http.Post(getActivity(), "Invitation/GetChat",
                jsonObject.toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
                        Log.i(TAG, "onResponse: "+object);
                        if(object.optString("msg").equals("S")){
                            Gson gson = new Gson();
                            ShanChat invitation = gson.fromJson(object.toString(),ShanChat.class);
                            for (int i = 0; i < invitation.getData().size(); i++) {
                                dataBeans.add(invitation.getData().get(i));
                            }
                            generalAdapter.notifyDataSetChanged();
                        }else{
                            ToastUtil.toToast(object.optString("data"));
                        }
                    }
                });
    }

    class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.MyViewHolder> {
        //当前上下文对象
        Context context;

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            //实例化得到Item布局文件的View对象
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.chatcard, null);
            //返回MyViewHolder的对象
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.tv_content.setText(dataBeans.get(i).getAsme());
            myViewHolder.textView.setText(dataBeans.get(i).getUsername());
            Random random = new Random();
            int num = random.nextInt(7);
            myViewHolder.imageView.setImageResource(colors[num]);
            myViewHolder.touxiang.setText(dataBeans.get(i).getUsername().substring(0, 1) + "");
        }

        @Override
        public int getItemCount() {
            return dataBeans.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            TextView touxiang;
            CircleImageView imageView;
            TextView tv_content;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.m1);
                touxiang = itemView.findViewById(R.id.touxiang);
                textView = itemView.findViewById(R.id.t1);
                tv_content = itemView.findViewById(R.id.tv_content);
            }
        }
    }


}
