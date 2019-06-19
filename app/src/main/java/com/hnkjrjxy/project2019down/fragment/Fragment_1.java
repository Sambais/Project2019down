/**
 *
 */
package com.hnkjrjxy.project2019down.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnkjrjxy.project2019down.R;


public class Fragment_1 extends Fragment {

    public static TextView textview;
    private int photo[] = {R.mipmap.gv1_p1,R.mipmap.gv1_p2,R.mipmap.gv1_p3,R.mipmap.gv1_p4,R.mipmap.gv1_p5,R.mipmap.gv1_p6};
    private GridView fragment_gv;
    private Adapter adapter;
    private String[] tabtitle = {"收藏", "热门", "情绪", "社交", "爱好", "生活"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr1, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        fragment_gv = (GridView) view.findViewById(R.id.fragment_gv);
        adapter=new Adapter();
        fragment_gv.setAdapter(adapter);
    }

    class Adapter extends BaseAdapter{
        @Override
        public int getCount() {
            return tabtitle.length;
        }

        @Override
        public Object getItem(int position) {
            return tabtitle[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.main_lits_item, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(position,(String)getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(int position,String object, ViewHolder holder) {
            holder.m1.setBackgroundResource(photo[position]);
            holder.t1.setText(tabtitle[position]);
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
