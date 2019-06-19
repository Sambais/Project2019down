package com.hnkjrjxy.project2019down.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NewMyListView extends ListView {
    public NewMyListView(Context context) {
        super(context);
    }

    public NewMyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewMyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
