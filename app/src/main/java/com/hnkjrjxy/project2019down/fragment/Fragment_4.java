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

import com.hnkjrjxy.project2019down.R;


public class Fragment_4 extends Fragment {
    private RecyclerView recyclerView;
    private GeneralAdapter generalAdapter;

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
            return 20;
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
