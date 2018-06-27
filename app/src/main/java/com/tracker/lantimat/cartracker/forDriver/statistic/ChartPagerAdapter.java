package com.tracker.lantimat.cartracker.forDriver.statistic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tracker.lantimat.cartracker.forDriver.statistic.models.MiniStatisticChart;

import java.util.ArrayList;

public class ChartPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<MiniStatisticChart> images;

    public ChartPagerAdapter(FragmentManager fm, ArrayList<MiniStatisticChart> images) {
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int position) {
        MiniStatisticChart image = images.get(position);
        return MiniChartFragment.newInstance(image);
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
