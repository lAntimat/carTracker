package com.tracker.lantimat.cartracker.forDriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appizona.yehiahd.fastsave.FastSave;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.forDriver.mainInfoFragment.MainInfoFragment;
import com.tracker.lantimat.cartracker.forDriver.mainInfoFragment.MainState;
import com.tracker.lantimat.cartracker.forDriver.mainInfoFragment.MainStatesAdapter;
import com.tracker.lantimat.cartracker.forDriver.profile.ProfileFragment;
import com.tracker.lantimat.cartracker.forDriver.statistic.FullStatsInfoFragment;

import java.util.ArrayList;

import static com.tracker.lantimat.cartracker.utils.MyApplication.getContext;

public class Main2Activity extends AppCompatActivity {

    static final int PAGE_COUNT = 4;

    private RecyclerView recyclerView;
    private MainStatesAdapter adapter;
    private ArrayList<MainState> ar = new ArrayList<>();

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FastSave.init(getApplicationContext());
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initPagerAdapter();
        setupBottomBar();
    }

    private void initPagerAdapter() {

        //Инициализация ViewPager
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(2);
    }

    private void setupBottomBar() { //Инициализация нижнего бара
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


        //Создаем айтемы
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Главная", R.drawable.home, R.color.colorBottomNavigationPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Инф.", R.drawable.google_analytics, R.color.colorBottomNavigationPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Уведомления", R.drawable.bell, R.color.colorBottomNavigationPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Профиль", R.drawable.account, R.color.colorBottomNavigationPrimary);

        //Добавляем в бар
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        // Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.colorAccent1));

        // Set background color
        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark1));

        // Add or remove notification for each item
        //bottomNavigation.setNotification("1", 2);



        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                pager.setCurrentItem(position);
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });

        //Устанавливаем текущий элемент
        bottomNavigation.setCurrentItem(0,false);

    }

    public void openMarkDetail(View view) {
        startActivity(new Intent(this, MarkDetailActivity.class));
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainInfoFragment();
                case 1:
                    return new FullStatsInfoFragment();
                case 2:
                    return new NotificationFragment();
                case 3:
                    return new FragmentPersonal();
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
