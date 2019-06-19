/**
 *
 */
package com.hnkjrjxy.project2019down.fragment.zhufragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hnkjrjxy.project2019down.R;


public class Fragment_chat extends Fragment {

    private int photo[] = {R.mipmap.gv1_p1, R.mipmap.gv1_p2, R.mipmap.gv1_p3, R.mipmap.gv1_p4, R.mipmap.gv1_p5, R.mipmap.gv1_p6};
    private String data[];
    private Adapter adapter;
    private ListView fr2_list;
    private String[] tabtitle = {"收藏", "热门", "情绪", "社交", "爱好", "生活"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a2, container, false);
        initView(view);
        return view;
        //
    }

    public Fragment_chat setData(String[] data) {
        this.data = data;
        return this;
    }

    private void initView(View view) {
        fr2_list = (ListView) view.findViewById(R.id.fr2_list);
        adapter = new Adapter();
        fr2_list.setAdapter(adapter);

}

    class Adapter extends BaseAdapter {
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
            initializeViews(position, (String) getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(int position, String object, ViewHolder holder) {
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
