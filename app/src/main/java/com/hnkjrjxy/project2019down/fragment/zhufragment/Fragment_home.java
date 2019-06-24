/**
 *
 */
package com.hnkjrjxy.project2019down.fragment.zhufragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.entry.MyHomeTop;
import com.hnkjrjxy.project2019down.fragment.Fragment_1;
import com.hnkjrjxy.project2019down.fragment.Fragment_3;
import com.hnkjrjxy.project2019down.fragment.Fragment_4;
import com.hnkjrjxy.project2019down.fragment.Fragment_5;
import com.hnkjrjxy.project2019down.fragment.Fragment_6;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.view.NewMyListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class Fragment_home extends Fragment {

    private int photo[] = {R.mipmap.gv1_p1, R.mipmap.gv1_p2, R.mipmap.gv1_p3, R.mipmap.gv1_p4, R.mipmap.gv1_p5, R.mipmap.gv1_p6};
    private TabLayout tab;
    private TabLayout tab1;
    private Toolbar toolbar;
    private NewMyListView mylistview;
    private NestedScrollView nestedScrollView;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
    private ArrayList<Fragment> fragment2s;
    private FragmentAdapter fragmentAdapter;
    private FragmentNextAdapter fragmentNextAdapter;
    private String[] tabtitle = {"收藏", "热门", "情绪", "社交", "爱好", "生活"};
    private String[] tabnexttitle = {"关注", "推荐", "最新"};
    private ArrayList tabtitles;
    private SwipeRefreshLayout swiperefreshlayout;
    private AppBarLayout myappBarLayout;
    private ViewPager myviewpager;
    private TextView yubeit1;
    private ViewPager myviewpagernext;
    private CoordinatorLayout coordinatorlayout;
    private SwipeRefreshLayout.OnRefreshListener listener;
    private int ztpd=0;
    private ArrayList<MyHomeTop.DataBean> list1;
    private ArrayList<ArrayList> list2;
    private ArrayList<ArrayList> list3;
    private ArrayList<ArrayList> list4;
    private ArrayList<ArrayList> list5;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a1, container, false);
        initView(view,0);
        return view;
    }

    public void initView(View view,int zt) {
        ztpd=zt;
        if (ztpd==1){
            Log.i("TAG", "initView: 我想到顶部");
            //方法重载，直接用，默认带动画效果慢慢展开或折叠，拿走不谢
            myappBarLayout.setExpanded(true,true);

            //暂时只能用三个Fragment解决双击回到顶部并且刷新的操作
           if (tab1.getSelectedTabPosition()==0) {
               Fragment_4.Weizhi(0);
           }else if (tab1.getSelectedTabPosition()==1){
               Fragment_5.Weizhi(0);
           }else {
               Fragment_6.Weizhi(0);
           }

        }else {
            list1=new ArrayList();
            list2=new ArrayList();
            list3=new ArrayList();
            list4=new ArrayList();
            list5=new ArrayList();
            tabtitles = new ArrayList();
            for (int i = 0; i < 20; i++) {
                tabtitles.add("第" + i + "个数据");
            }
            coordinatorlayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorlayout);
            tab = (TabLayout) view.findViewById(R.id.tab);
            tab1 = (TabLayout) view.findViewById(R.id.tab1);
            toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            yubeit1 = (TextView) view.findViewById(R.id.yubeit1);
            myviewpager = (ViewPager) view.findViewById(R.id.myviewpager);
            myviewpagernext = (ViewPager) view.findViewById(R.id.myviewpagernext);
            nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedscrollview);
            swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
            myappBarLayout = (AppBarLayout) view.findViewById(R.id.appbarlayout);

            //为顶部ViewPager添加fragment
            fragments = new ArrayList<>();
            fragments.add(new Fragment_1());
            fragments.add(new Fragment_3());
            fragments.add(new Fragment_1());
            fragments.add(new Fragment_3());
            fragments.add(new Fragment_1());
            fragments.add(new Fragment_1());

            fragment2s = new ArrayList<>();
            fragment2s.add(new Fragment_4());
            fragment2s.add(new Fragment_5());
            fragment2s.add(new Fragment_6());

            //上部分频道适配器
            fragmentManager = getChildFragmentManager();
            fragmentAdapter = new FragmentAdapter(fragmentManager);
            myviewpager.setAdapter(fragmentAdapter);
            myviewpager.setOffscreenPageLimit(6);

            //下部分帖子适配器
            fragmentNextAdapter = new FragmentNextAdapter(fragmentManager);
            myviewpagernext.setAdapter(fragmentNextAdapter);
            myviewpagernext.setOffscreenPageLimit(3);

            //上部分TabLayout绑定ViewPager
            tab.setupWithViewPager(myviewpager);

            //下部分TabLayout绑定ViewPager
            tab1.setupWithViewPager(myviewpagernext);

            //设置下拉刷新环形加载条的颜色，最多使用四个颜色
            swiperefreshlayout.setColorSchemeResources(R.color.colorPrimary, R.color.color1);
            //设置下拉是否开始缩放，起点是20的高度，最多到达100的高度
            swiperefreshlayout.setProgressViewOffset(false, 20, 100);

            //从后台获取数据
            getData();
        }
        layoutlistener();
    }

    private void layoutlistener() {
        //下拉刷新SwipeRefreshLayout监听

        listener = new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tabtitles.add("第" + tabtitles.size() + "个数据");
                        swiperefreshlayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        Collections.reverse(tabtitles);
                    }
                }, 2000);
            }
        };

        swiperefreshlayout.setOnRefreshListener(listener);

        //判断AppBarLayout往上偏移的量是否为0，为0时处于界面的顶部此时可以执行下拉刷新
        myappBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
//                Log.i("TAG", "onOffsetChanged:         " + i);
                if (i == 0) {
                    //设置标签的字体颜色，1为未选中标签的字体颜色，2为被选中标签的字体颜色
                    //这个设置会让tab1的点击时间下方横线卡断
//                    tab1.setTabTextColors(Color.BLACK,Color.parseColor("#5CACEE"));
                    tab1.setSelectedTabIndicatorColor(Color.parseColor("#5CACEE"));
                    tab1.setBackgroundColor(Color.parseColor("#fafafa"));
                    yubeit1.setBackgroundColor(Color.parseColor("#fafafa"));
                    swiperefreshlayout.setEnabled(true);//可刷新

                    if (ztpd ==1){
                        //开始启动刷新的状态
                        swiperefreshlayout.setRefreshing(true);
                        listener.onRefresh();
                        ztpd=0;
                    }
                } else {
                    float s = 255 - (Float.parseFloat(Math.abs(i) + "") / 661 * 255);
                    tab1.getBackground().mutate().setAlpha((int) s);
                    yubeit1.getBackground().mutate().setAlpha((int) s);
                    if (Math.abs(i) == 661) {
//                        tab1.setTabTextColors(Color.WHITE,Color.WHITE);
                        tab1.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                    }
                    swiperefreshlayout.setEnabled(false);//不能刷新，此时为滑动
                }
            }
        });
    }

    private void getData() {
        //获取顶部title
        getChannel();
    }

    private void getChannel() {
        //http://hnkj3172.mynatapp.cc:80/Init/Channel
        Http.Get(getActivity(), "Init/Channel", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                Gson gson=new Gson();
                MyHomeTop myHomeTop = gson.fromJson(object.toString(),MyHomeTop.class);
//                Homechannel1 homechannel1=gson.fromJson(myHomeTop.getData().get(1).toString(),Homechannel1.class);
                //这样就可以拿数据了
                Log.i("Gson", "onResponse: "+myHomeTop.getData().toString());
                Log.i("Gson", "onResponse: "+myHomeTop.getData().size());
                for (int i = 0; i <myHomeTop.getData().get(1).size() ; i++) {
                    if (myHomeTop.getData().get(1).get(3).getChannel_id()==1){
                        list1.add(myHomeTop.getData().get(1).get(i));
                    }
                }
                Log.i("Gson", "onResponse: "+list1);

            }
        });
    }


    //顶部频道viewpager适配器
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


    //下部分帖子适配器
    class FragmentNextAdapter extends FragmentPagerAdapter {
        public FragmentNextAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragment2s.get(i);
        }

        @Override
        public int getCount() {
            return fragment2s.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabnexttitle[position];
        }
    }
}
