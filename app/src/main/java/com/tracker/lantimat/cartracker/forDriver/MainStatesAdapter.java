package com.tracker.lantimat.cartracker.forDriver;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.models.CarState;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by GabdrakhmanovII on 03.11.2017.
 */

public class MainStatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainState> mList;
    private Context context;


    public MainStatesAdapter(Context context, ArrayList<MainState> itemList) {
        this.context = context;
        this.mList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_for_driver_main_page_stats, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ViewHolder) holder).tvTitle.setText(mList.get(position).title);
        ((ViewHolder) holder).tvText.setText(mList.get(position).text);

        Drawable drawable = ContextCompat.getDrawable(context, mList.get(position).img);
        if (drawable!=null) {
            ((ViewHolder) holder).imageView.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).imageView.setImageDrawable(drawable);
            ((ViewHolder) holder).imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent1));
        } else ((ViewHolder) holder).imageView.setVisibility(View.GONE);

        if (mList.get(position).percent != -1) {
            ((ViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).progressBar.setProgress(mList.get(position).percent);
        } else ((ViewHolder) holder).progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvText;
        public ImageView imageView;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
