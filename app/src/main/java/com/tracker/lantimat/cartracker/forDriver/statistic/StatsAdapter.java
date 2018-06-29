package com.tracker.lantimat.cartracker.forDriver.statistic;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.forDriver.statistic.models.MiniStatisticChart;
import com.tracker.lantimat.cartracker.utils.CustomProgressBar;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by GabdrakhmanovII on 03.11.2017.
 */

public class StatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> mList;
    private ArrayList<ArrayList<MiniStatisticChart>> arChart;
    private Context context;

    private final int MARK_VIEW = 0; //position for items type in RecyclerView
    private final int CHARTS_VIEW = 1;


    public StatsAdapter(Context context, ArrayList<ArrayList<MiniStatisticChart>> arChart, float driveMark) {
        this.context = context;
        this.arChart = arChart;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CHARTS_VIEW)
            return new ViewPagerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_chart, parent, false));
        else
            return new DriveMarkHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_drive_mark, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == CHARTS_VIEW) {
            ((ViewPagerHolder) holder).pager.setId(position + 1);
            ((ViewPagerHolder) holder).pager.setAdapter(new ChartPagerAdapter(((FragmentActivity) context).getSupportFragmentManager(), arChart.get(position)));
            ((ViewPagerHolder) holder).indicator.setViewPager(((ViewPagerHolder) holder).pager);
        } else if (position == MARK_VIEW) {
            ((DriveMarkHolder) holder).markBar.setMax(100);
            ((DriveMarkHolder) holder).markBar.setProgress(95);
            ((DriveMarkHolder) holder).markBar.setText("9.5");
            ((DriveMarkHolder) holder).markBar.setTextColor(ContextCompat.getColor(context, R.color.md_white_1000));
        }

    }

    @Override
    public int getItemCount() {
        if (arChart == null)
            return 0;
        return arChart.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    public static class DriveMarkHolder extends RecyclerView.ViewHolder {
        public CustomProgressBar markBar;

        public DriveMarkHolder(View itemView) {
            super(itemView);
            markBar = itemView.findViewById(R.id.mark);
        }
    }

    public static class ViewPagerHolder extends RecyclerView.ViewHolder {
        public ViewPager pager;
        private CircleIndicator indicator;

        public ViewPagerHolder(View itemView) {
            super(itemView);
            pager = itemView.findViewById(R.id.pager);
            indicator = itemView.findViewById(R.id.indicator);
        }
    }
}
