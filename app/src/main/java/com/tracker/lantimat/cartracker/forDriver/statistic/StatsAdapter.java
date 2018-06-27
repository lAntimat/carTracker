package com.tracker.lantimat.cartracker.forDriver.statistic;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by GabdrakhmanovII on 03.11.2017.
 */

public class StatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> mList;
    private ArrayList<ArrayList<MiniStatisticChart>> arChart;
    private Context context;


    public StatsAdapter(Context context, ArrayList<ArrayList<MiniStatisticChart>> arChart, int driveMark) {
        this.context = context;
        this.arChart = arChart;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_for_driver_main_page_stats, parent, false);
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_chart, parent, false);
        return new ViewPagerHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        ((ViewHolder) holder).tvText.setText(mList.get(position).text);

        /*Object obj = null;
        obj = mList.get(position);
        if(obj instanceof String) {
            ((ViewHolder) holder).tvTitle.setText((String) obj);
        }*/


        ((ViewPagerHolder) holder).pager.setId(position+1);


        //((ViewPagerHolder) holder).pager.setAdapter(adapter);

        ((ViewPagerHolder) holder).pager.setAdapter(new ChartPagerAdapter(((FragmentActivity)context).getSupportFragmentManager(), arChart.get(position)));
        ((ViewPagerHolder) holder).indicator.setViewPager(((ViewPagerHolder) holder).pager);

        /*if (mList.get(position).img!=-1) {
            Drawable drawable = ContextCompat.getDrawable(context, mList.get(position).img);
            ((ViewHolder) holder).imageView.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).imageView.setImageDrawable(drawable);
            ((ViewHolder) holder).imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent1));
        } else ((ViewHolder) holder).imageView.setVisibility(View.GONE);

        if (mList.get(position).percent != -1) {
            ((ViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).progressBar.setProgress(mList.get(position).percent);
        } else ((ViewHolder) holder).progressBar.setVisibility(View.GONE);*/
    }

    @Override
    public int getItemCount() {
        if (arChart == null)
            return 0;
        return arChart.size();
    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }


    public static class DriveMarkHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvText;
        public ImageView imageView;
        public ProgressBar progressBar;

        public DriveMarkHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public static class ViewPagerHolder extends RecyclerView.ViewHolder {
        public ViewPager pager;
        private CircleIndicator indicator;

        public ViewPagerHolder(View itemView) {
            super(itemView);
            pager = itemView.findViewById(R.id.pager);
            indicator  = itemView.findViewById(R.id.indicator);
        }
    }
}
