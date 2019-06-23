package com.hnkjrjxy.project2019down;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import com.hnkjrjxy.project2019down.activity.SendPostActivity;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_chat;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_home;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_msg;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_self;

import q.rorbin.badgeview.QBadgeView;


public class MainActivity extends FragmentActivity {

    private FragmentManager fragmentManager;
    private BottomNavigationView navigation1;
    private Fragment_home fragment1;
    private Fragment_chat fragment2;
    private Fragment_msg fragment3;
    private Fragment_self fragment4;
    private long starttime=0;
    private QBadgeView qBadgeView1,qBadgeView2,qBadgeView3,qBadgeView4;
    private BottomNavigationMenuView menuView;
    private int select=0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        Intent intent = new Intent(MainActivity.this,MyService.class);
        startService(intent);

        //设置选中和未选中的样式
        navigation1.setItemTextAppearanceActive(R.style.bottom_selected_text);
        navigation1.setItemTextAppearanceInactive(R.style.bottom_normal_text);

        //在FrameLayout中使用了骚操作，以下注释代码设置图标的大小可以不使用
//        //遍历菜单，当遍历到第三个子界面时将图标的大小设置为45
////        for (int i = 0; i < menuView.getChildCount(); i++) {
////            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
////            //noinspection RestrictedApi
////            item.setShifting(false);
////            // set once again checked value, so view will be updated
////            //noinspection RestrictedApi
////            item.setChecked(item.getItemData().isChecked());
////            if (i == 2) {
////                final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
////                final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
////                final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
////                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, displayMetrics);
////                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, displayMetrics);
////                iconView.setLayoutParams(layoutParams);
////            }
////        }
        //设置角标
        showBadgeView(1,56);
        showBadgeView(3,150);
        showBadgeView(4,3);

        //navigation1监听，事件处于MainAtcivity的顶部
        navigation1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            private View fragmentview;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        select=0;
                        //判断双击事件刷新
                        long endtime1 = System.currentTimeMillis();
                        if (endtime1 -starttime<=ViewConfiguration.getDoubleTapTimeout()){
                            starttime=0;
                            fragmentview = LayoutInflater.from(MainActivity.this).inflate(R.layout.a1,null);
                            fragment1.initView(fragmentview,1);
                        }else {
                            starttime= endtime1;
                            showFragment(1);
                        }
                        return true;
                    case R.id.navigation_dashboard:
                        select=1;
                        long endtime2 = System.currentTimeMillis();
                        if (endtime2 -starttime<=ViewConfiguration.getDoubleTapTimeout()){
                            starttime=0;
                            fragmentview = LayoutInflater.from(MainActivity.this).inflate(R.layout.a2,null);
                            fragment2.initView(fragmentview,1);
                        }else {
                            starttime= endtime2;
                            showFragment(2);
                        }
                        return true;
                    case R.id.add_informatization:
                        Log.i("select", "onNavigationItemSelected: "+select);
                        navigation1.setSelectedItemId(navigation1.getMenu().getItem(select).getItemId());
                        startActivity(new Intent(MainActivity.this,SendPostActivity.class));
                        return false;
                    case R.id.navigation_notifications:
                        select=3;
                        showFragment(3);
                        qBadgeView3.hide(true);
                        return true;
                    case R.id.myself:
                        select=4;
                        showFragment(4);
                        //将其角标数量设置为0即为不显示
                        qBadgeView4.hide(true);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(MainActivity.this,MyService.class);
        stopService(intent);
    }

    private void initView() {
        qBadgeView1=new QBadgeView(this);
        qBadgeView2=new QBadgeView(this);
        qBadgeView3=new QBadgeView(this);
        qBadgeView4=new QBadgeView(this);
        navigation1 = (BottomNavigationView) findViewById(R.id.navigation);
        //得到BottomNavigationMenuView子界面菜单
        menuView = (BottomNavigationMenuView) navigation1.getChildAt(0);
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

    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex tab索引
     * @param showNumber 显示的数字，小于等于0是将不显示
     */
    private void showBadgeView(int viewIndex, int showNumber) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation1.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);

            //根据展示的数量调整角标显示的位置
            int s=0;
            if (showNumber>100){
                s=12;
            }else if(showNumber<10){
                s=11;
            }else {
                s=25;
            }


            // 显示badegeview
            if (viewIndex==0){
                qBadgeView1.bindTarget(view).setGravityOffset(s, 3, false).setBadgeNumber(showNumber);
            }else if (viewIndex==1){
                qBadgeView2.bindTarget(view).setGravityOffset(s, 3, false).setBadgeNumber(showNumber);
            }else if (viewIndex==3){
                qBadgeView3.bindTarget(view).setGravityOffset(s, 3, false).setBadgeNumber(showNumber);
            }else if (viewIndex==4){
                qBadgeView4.bindTarget(view).setGravityOffset(s, 3, true).setBadgeNumber(showNumber);
            }
        }
    }
}
