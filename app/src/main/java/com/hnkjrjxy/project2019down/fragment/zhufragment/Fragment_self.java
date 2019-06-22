package com.hnkjrjxy.project2019down.fragment.zhufragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.activity.SettingActivity;

public class Fragment_self extends Fragment {

    private ListView a4_list;
    private ListAdapter listAdapter;
    private String tabtitles[] = {"收藏", "热门", "情绪", "社交", "爱好", "生活", "收藏", "热门", "情绪", "社交", "爱好", "生活", "收藏", "热门", "情绪", "社交", "爱好", "生活", "收藏", "热门", "情绪", "社交", "爱好", "生活", "收藏", "热门", "情绪", "社交", "爱好", "生活"};
    private ImageView setting;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a4, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        a4_list = (ListView) view.findViewById(R.id.a4_list);
        setting = (ImageView) view.findViewById(R.id.setting);
        listAdapter = new ListAdapter();
        a4_list.setAdapter(listAdapter);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    class ListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return tabtitles.length;
        }

        @Override
        public Object getItem(int position) {
            return tabtitles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_lits_item, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews((String) getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(String object, ViewHolder holder) {
            holder.t1.setText(object);
        }

        protected class ViewHolder {
            private ImageView m1;
            private TextView t1;

            public ViewHolder(View view) {
                m1 = (ImageView) view.findViewById(R.id.m1);
                t1 = (TextView) view.findViewById(R.id.t1);
            }
        }
    }
}
