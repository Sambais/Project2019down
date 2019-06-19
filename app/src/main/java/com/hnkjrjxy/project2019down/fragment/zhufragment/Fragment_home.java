/**
 *
 */
package com.hnkjrjxy.project2019down.fragment.zhufragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.fragment.Fragment_1;
import com.hnkjrjxy.project2019down.fragment.Fragment_3;
import com.hnkjrjxy.project2019down.view.NewMyListView;

import java.util.ArrayList;
import java.util.Collections;


public class Fragment_home extends Fragment {

    private int photo[] = {R.mipmap.gv1_p1, R.mipmap.gv1_p2, R.mipmap.gv1_p3, R.mipmap.gv1_p4, R.mipmap.gv1_p5, R.mipmap.gv1_p6};
    private GridView fragment_gv;
    private MylistAdapter mylistAdapter;
    private TabLayout tab;
    private Toolbar toolbar;
    private NewMyListView mylistview;
    private NestedScrollView nestedScrollView;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
    private FragmentAdapter fragmentAdapter;
    private String[] tabtitle = {"收藏", "热门", "情绪", "社交", "爱好", "生活"};
    private ArrayList tabtitles;
    private SwipeRefreshLayout refreshLayout;
    private AppBarLayout appBarLayout;
    private ViewPager myviewpager;
    private boolean isLoad;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ceshi, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tabtitles = new ArrayList();
        for (int i = 0; i <20; i++) {
            tabtitles.add("第" + i + "个数据");
        }
        tab = (TabLayout) view.findViewById(R.id.tab);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        myviewpager = (ViewPager) view.findViewById(R.id.myviewpager);
        mylistview = (NewMyListView) view.findViewById(R.id.mylistview);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appBarLayout);

        //为顶部ViewPager添加fragment
        fragments = new ArrayList<>();
        fragments.add(new Fragment_1());
        fragments.add(new Fragment_3());
        fragments.add(new Fragment_1());
        fragments.add(new Fragment_3());
        fragments.add(new Fragment_1());
        fragments.add(new Fragment_1());

        fragmentManager = getChildFragmentManager();
        fragmentAdapter = new FragmentAdapter(fragmentManager);
        myviewpager.setAdapter(fragmentAdapter);
        myviewpager.setOffscreenPageLimit(6);

        mylistAdapter = new MylistAdapter();
        mylistview.setAdapter(mylistAdapter);

        //TabLayout绑定ViewPager
        tab.setupWithViewPager(myviewpager);

        //设置下拉刷新环形加载条的颜色，最多使用四个颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.color1);
        //设置下拉是否开始缩放，起点是20的高度，最多到达100的高度
        refreshLayout.setProgressViewOffset(false, 20, 100);

        //下拉刷新SwipeRefreshLayout监听
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tabtitles.add("第" + tabtitles.size() + "个数据");
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        Collections.reverse(tabtitles);
                        mylistAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }

        });

        // listview自定义存在问题该处暂时不引用
        mylistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                // isLoad为true就开始加载数据
                Log.i("TAG", "onScroll:   set      "+i);
                switch (i) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        Log.i("onScroll","已经停止：SCROLL_STATE_IDLE");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        Log.i("onScroll","开始滚动：SCROLL_STATE_FLING");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        Log.i("onScroll","正在滚动：SCROLL_STATE_TOUCH_SCROLL");
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //记录当屏幕显示的条目数与总条目数减去1，就说明快到底部了就为true
                Log.i("onScroll", "onScroll: "+firstVisibleItem+"           "+visibleItemCount+"              "+(totalItemCount-2));
                isLoad = ((firstVisibleItem+visibleItemCount)>=(totalItemCount-2));
                Log.i("onScroll", "onScroll: "+isLoad);

                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = mylistview.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                        Log.d("onScroll", "##### 滚动到顶部 #####");
                    }
                }else {
                    if (isLoad){
                        Log.i("onScroll", "onScroll:   setOnScrollListener 在执行");
                        tabtitles.add("新数据");
                        Log.i("onScroll", "onScroll:   setOnScrollListener 在执行" + tabtitles.size());
                        mylistAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "开始加载数据", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //判断AppBarLayout往上偏移的量是否为0，为0时处于界面的顶部此时可以执行下拉刷新
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                Log.i("TAG", "onOffsetChanged:         " + i);
                if (i == 0) {
                    refreshLayout.setEnabled(true);//可刷新
                } else {
                    refreshLayout.setEnabled(false);//不能刷新，此时为滑动
                }
            }
        });
    }


    //顶部viewpager适配器
    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitle[position];
        }
    }

    //下段自定义listview适配器
    class MylistAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return tabtitles.size();
        }

        @Override
        public Object getItem(int position) {
            return tabtitles.get(position);
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
            holder.m1.setBackgroundResource(photo[1]);
            holder.t1.setText(tabtitles.get(position) + "");
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
