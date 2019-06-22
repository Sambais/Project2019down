package com.hnkjrjxy.project2019down.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hnkjrjxy.project2019down.R;

public class Usersetting_view extends LinearLayout {

    private String[] infos;
    private ListView lv;
    private TextView tv_msg;
    private BaseAdapter adapter;
    private OnCallBack onCallBack;
    private String msg;
    private int size = 260;

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public interface OnCallBack{
        void callback(String object);
    }

    public Usersetting_view(Context context) {
        super(context);
        init();
    }

    public Usersetting_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Usersetting_view(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.usersetting_view,null);
        tv_msg = view.findViewById(R.id.usersetting_msg);
        lv = view.findViewById(R.id.usersetting_btn);
        addView(view);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        tv_msg.setText(msg);
    }

    public void setInfos(final String[] infos) {
        this.infos = infos;
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return infos.length;
            }

            @Override
            public Object getItem(int position) {
                return infos[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.usersetting_item2, null);
                    convertView.setTag(new ViewHolder(convertView));
                }
                initializeViews((String)getItem(position), (ViewHolder) convertView.getTag());
                return convertView;
            }

            private void initializeViews(final String object, ViewHolder holder) {
                LayoutParams params = new LayoutParams(size,ViewGroup.LayoutParams.MATCH_PARENT);
                params.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                holder.sersettingInfo.setLayoutParams(params);
                holder.sersettingInfo.setText(object);
                holder.sersettingInfo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("TAG", "onClick: !!!!!");
                        if(onCallBack != null) onCallBack.callback(object);
                    }
                });
            }

            class ViewHolder {
                private Button sersettingInfo;

                public ViewHolder(View view) {
                    sersettingInfo = (Button) view.findViewById(R.id.sersetting_info);
                }
            }
        };
        lv.setAdapter(adapter);
    }
}
