package com.hnkjrjxy.project2019down;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_chat;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_home;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_msg;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_self;


public class MainActivity extends FragmentActivity {

    private FragmentManager fragmentManager;
    private BottomNavigationView navigation1;
    private Fragment_home fragment1;
    private Fragment_chat fragment2;
    private Fragment_msg fragment3;
    private Fragment_self fragment4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        navigation1 = (BottomNavigationView) findViewById(R.id.navigation);
        //得到BottomNavigationMenuView子界面菜单
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation1.getChildAt(0);
        //遍历菜单，当遍历到第三个子界面时将图标的大小设置为36
        for (int i = 0; i < menuView.getChildCount(); i++) {
            if (i == 2) {
                final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
                final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, displayMetrics);
                iconView.setLayoutParams(layoutParams);
            }
        }

        //navigation1监听，事件处于MainAtcivity的顶部
        navigation1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        showFragment(1);
//                    fragmentManager.beginTransaction().replace(R.id.main_center,new Fragment_home()).commit();
                        return true;
                    case R.id.navigation_dashboard:
                        showFragment(2);
//                    fragmentManager.beginTransaction().replace(R.id.main_center,new Fragment_chat()).commit();
                        return true;
                    case R.id.add_informatization:
                        //此处使用activity
                        return true;
                    case R.id.navigation_notifications:
                        showFragment(3);
//                    fragmentManager.beginTransaction().replace(R.id.main_center,new Fragment_msg()).commit();
                        return true;
                    case R.id.myself:
                        showFragment(4);
//                    fragmentManager.beginTransaction().replace(R.id.main_center,new Fragment_self()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        fragmentManager=getSupportFragmentManager();
        showFragment(1);
    }

    public void showFragment(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);

        switch (index) {
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (fragment1 != null)
                    ft.show(fragment1);
                    // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    fragment1 = new Fragment_home();
                    ft.add(R.id.main_center, fragment1);
                }
                break;
            case 2:
                if (fragment2 != null)
                    ft.show(fragment2);
                else {
                    fragment2 = new Fragment_chat();
                    ft.add(R.id.main_center, fragment2);
                }
                break;
            case 3:
                if (fragment3 != null)
                    ft.show(fragment3);
                else {
                    fragment3 = new Fragment_msg();
                    ft.add(R.id.main_center, fragment3);
                }
                break;
            case 4:
                if (fragment4 != null)
                    ft.show(fragment4);
                else {
                    fragment4 = new Fragment_self();
                    ft.add(R.id.main_center, fragment4);
                }
                break;
        }
        ft.commit();
    }

    // 当fragment已被实例化，就隐藏起来
    public void hideFragments(FragmentTransaction ft) {
        if (fragment1 != null)
            ft.hide(fragment1);
        if (fragment2 != null)
            ft.hide(fragment2);
        if (fragment3 != null)
            ft.hide(fragment3);
        if (fragment4 != null)
            ft.hide(fragment4);
    }
}
