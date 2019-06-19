/**
 *
 */
package com.hnkjrjxy.project2019down.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hnkjrjxy.project2019down.R;


public class Fragment_4 extends Fragment {
    private RecyclerView recyclerView;
    private GeneralAdapter generalAdapter;
    private int num=20;
    int i=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr4, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        //RecyclerView绑定适配器
        //设置LayoutManager为LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        generalAdapter = new GeneralAdapter();
        recyclerView.setAdapter(generalAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount()-1);
                //得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom =  recyclerView.getBottom()-recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition  = recyclerView.getLayoutManager().getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部
//                if(lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount()-1 ){
//                    Toast.makeText(getActivity(), "滑动到底了", Toast.LENGTH_SHORT).show();
//                }

                //dx>0 则表示 右滑 ， dx<0 表示 左滑
                //dy <0 表示 上滑， dy>0 表示下滑
                //通过这几个参数就可以监听 滑动方向的状态。
                //判断是否向下滑动，如果向下滑动即将到底部的时候进行预加载
                if (dy>0){
                    //双重判断，以防滑动太快导致没有检测到滑动的位置信息
                    if(lastPosition == recyclerView.getLayoutManager().getItemCount()-4||
                            lastPosition == recyclerView.getLayoutManager().getItemCount()-3){
                        //在此处再次拿到数据进行适配器的刷新
                        num=num+20;
                        i++;
                        generalAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "滑动快要到底了       "+i+"             "+num, Toast.LENGTH_SHORT).show();
                    }
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
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.main_lits_item, null);
            //返回MyViewHolder的对象
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.textView.setText("7777");
            myViewHolder.imageView.setBackgroundResource(R.mipmap.gv1_p1);
        }

        @Override
        public int getItemCount() {
            return num;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.m1);
                textView = itemView.findViewById(R.id.t1);
            }
        }
    }
}
