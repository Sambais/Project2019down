package com.hnkjrjxy.project2019down.fragment.zhufragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.fragment.Fragment_1;

import java.util.ArrayList;

public class Fragment_msg extends Fragment {
    private TabLayout a3_tab;
    private ViewPager a3_vp;
    private MsgAdapter msgAdapter;
    private String title[]={"我的消息","我的好友"};
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a3, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        fragments=new ArrayList<>();
        a3_tab = (TabLayout) view.findViewById(R.id.a3_tab);
        a3_vp = (ViewPager) view.findViewById(R.id.a3_vp);

        fragments.add(new Fragment_1());
        fragments.add(new Fragment_1());

        fragmentManager=getChildFragmentManager();
        msgAdapter=new MsgAdapter(fragmentManager);
        a3_vp.setAdapter(msgAdapter);

        //TabLayout与ViewPager绑定
        a3_tab.setupWithViewPager(a3_vp);
    }

    class MsgAdapter extends FragmentPagerAdapter {

        public MsgAdapter(FragmentManager fm) {
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
            return title[position];
        }
    }
}
