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

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_1 extends Fragment {

    public static TextView textview;
    private GridView fragment_gv;
    private Integer colors[]={R.color.c1,R.color.c2,R.color.c3,R.color.c4,R.color.c5,R.color.c6,R.color.c7};
    private Adapter adapter;
    private String[] tabtitle;
    private String[] tabtitle_p;
    private int num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr1, container, false);
        initView(view);
        return view;
    }

    public Fragment_1 setData(String[] pin,String[] pin_p){
        tabtitle=pin;
        tabtitle_p=pin_p;
        return this;
    }


    private void initView(View view) {
        fragment_gv = (GridView) view.findViewById(R.id.fragment_gv);
        adapter=new Adapter();
        fragment_gv.setAdapter(adapter);
    }

    class Adapter extends BaseAdapter{
        @Override
        public int getCount() {
            if (tabtitle==null){
                return 1;
            }else {
                return tabtitle.length;
            }
        }

        @Override
        public Object getItem(int position) {
            if (tabtitle!=null){
                return tabtitle[position];
            }else {
                return position;
            }
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
            initializeViews(position, (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(int position, ViewHolder holder) {
            Random random=new Random();
            num = random.nextInt(7);
            if (tabtitle!=null){
                holder.m2.setImageResource(colors[num]);
                holder.m1.setBackgroundResource(getActivity().getResources().getIdentifier(tabtitle_p[position],"mipmap",getActivity().getPackageName()));
                holder.t1.setText(tabtitle[position]);
            }else {
                holder.m2.setImageResource(R.color.color2);
                holder.m1.setBackgroundResource(R.mipmap.add);
                holder.t1.setText("添加...");
            }
        }

        protected class ViewHolder {
            private ImageView m1;
            private TextView t1;
            private CircleImageView m2;

            public ViewHolder(View view) {
                m1 = (ImageView) view.findViewById(R.id.m1);
                m2 = (CircleImageView) view.findViewById(R.id.m2);
                t1 = (TextView) view.findViewById(R.id.t1);
            }
        }
    }
}
