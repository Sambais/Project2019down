/**
 *
 */
package com.hnkjrjxy.project2019down.fragment.zhufragment;


import android.content.Context;
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
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.fragment.Fragment_1;
import com.hnkjrjxy.project2019down.fragment.Fragment_4;
import com.hnkjrjxy.project2019down.fragment.Fragment_5;
import com.hnkjrjxy.project2019down.fragment.Fragment_6;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.view.NewMyListView;

import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_home extends Fragment {

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
    private ArrayList tabtitle ;
    private String[] tabnexttitle = {"关注", "推荐", "最新"};
    private ArrayList tabtitles;
    private static SwipeRefreshLayout swiperefreshlayout;
    private AppBarLayout myappBarLayout;
    private ViewPager myviewpager;
    private TextView yubeit1;
    private ViewPager myviewpagernext;
    private CoordinatorLayout coordinatorlayout;
    private SwipeRefreshLayout.OnRefreshListener listener;
    private int ztpd=0;
    private ArrayList<ArrayList> list1;
    private ArrayList<ArrayList> list2;
    private ArrayList<ArrayList> list3;
    private ArrayList<ArrayList> list4;
    private ArrayList<ArrayList> list5;
    private static Context context;
    public static int num=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a1, container, false);
        Log.i("YAG", "onCreateView:我先 ");
        initView(view,0);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //TODO now visible to user
            Log.i("Fragment5", "onHiddenChanged  hidden刷新数据-----------"+MyApplication.getZtpd());
            if (MyApplication.getZtpd()==1) {
                MyApplication.setZtpd(0);
                Log.i("Fragment5", "onHiddenChanged  hidden刷新数据-----------"+MyApplication.getZtpd());
            }
        } else {
            //TODO now invisible to user
            Log.i("YAG", "onHiddenChanged  ！hidden刷新数据");
        }
    }


    //为Fragment5界面的数据做准备
    public static void getFragment5Data() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",MyApplication.getToken());
        jsonObject.addProperty("num",num);
        Log.i("Fragment5", "getData: "+jsonObject.toString());
        Http.Post(context, "Invitation/GetInvitation",
                jsonObject.toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
                        Log.i("Fragment5", "onResponse: --------" + object);
                        Fragment_5.Data(object);
                        Fragment_6.Data(object);
                        swiperefreshlayout.setRefreshing(false);
                        num++;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("Fragment5", "onResponse: ++++" + volleyError);
                    }
                });
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
            context = getActivity();
            //进行首次拿数据,之后不再执行
            getFragment5Data();
            //顶部导航文字集合
            tabtitle=new ArrayList();
            tabtitle.add("收藏");
            tabtitle.add("热门");
            tabtitle.add("情绪");
            tabtitle.add("社交");
            tabtitle.add("爱好");
            //测试数据
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


            //ArrayList转换为数组
            String[] array1 = (String[])MyApplication.hotpindao.toArray(new String[MyApplication.hotpindao.size()]);
            String[] array2 = (String[])MyApplication.hotpindao_p.toArray(new String[MyApplication.hotpindao_p.size()]);

            //为顶部ViewPager添加fragment
            fragments = new ArrayList<>();
            fragments.add(new Fragment_1());
            fragments.add(new Fragment_1().setData(array1,array2));
            fragments.add(new Fragment_1().setData(MyApplication.pindao1,MyApplication.pindao1_p));
            fragments.add(new Fragment_1().setData(MyApplication.pindao2,MyApplication.pindao2_p));
            fragments.add(new Fragment_1().setData(MyApplication.pindao3,MyApplication.pindao3_p));

            fragment2s = new ArrayList<>();
            fragment2s.add(new Fragment_4().setData(13));
            fragment2s.add(new Fragment_5().setData(0));
            fragment2s.add(new Fragment_6().setData(0));

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

            //默认选中热门与推荐
            tab.getTabAt(1).select();
            tab1.getTabAt(1).select();

            //设置下拉刷新环形加载条的颜色，最多使用四个颜色
            swiperefreshlayout.setColorSchemeResources(R.color.colorPrimary, R.color.color1);
            //设置下拉是否开始缩放，起点是20的高度，最多到达100的高度
            swiperefreshlayout.setProgressViewOffset(false, 20, 100);

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
                        //开始再拿Ffragment5的数据
                        getFragment5Data();
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 2500);
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
            return (CharSequence) tabtitle.get(position);
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
