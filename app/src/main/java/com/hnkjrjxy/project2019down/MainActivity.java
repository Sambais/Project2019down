package com.hnkjrjxy.project2019down;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_chat;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_home;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_msg;
import com.hnkjrjxy.project2019down.fragment.zhufragment.Fragment_self;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.ToastUtil;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity==";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(R.id.main_center,new Fragment_home()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentManager.beginTransaction().replace(R.id.main_center,new Fragment_chat()).commit();
                    return true;
                case R.id.add_informatization:
                    //此处使用activity
                    return true;
                case R.id.navigation_notifications:
                    fragmentManager.beginTransaction().replace(R.id.main_center,new Fragment_msg()).commit();
                    return true;
                case R.id.myself:
                    fragmentManager.beginTransaction().replace(R.id.main_center,new Fragment_self()).commit();
                    return true;
            }
            return false;
        }
    };

    private FragmentManager fragmentManager;
    private BottomNavigationView navigation1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(MainActivity.this,MyService.class);
        startService(intent);

        setContentView(R.layout.activity_main);
        //隐藏自带的标题title
        getSupportActionBar().hide();
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
        navigation1.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Intent intent = new Intent(MainActivity.this,MyService.class);
        stopService(intent);

    }

    private void initView() {
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_center,new Fragment_home()).commit();
    }

}
