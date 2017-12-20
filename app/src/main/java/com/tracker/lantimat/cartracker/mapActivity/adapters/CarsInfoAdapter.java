package com.tracker.lantimat.cartracker.mapActivity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.API.Models;
import com.tracker.lantimat.cartracker.mapActivity.API.State;
import com.tracker.lantimat.cartracker.mapActivity.models.CarState;
import com.tracker.lantimat.cartracker.reportActivity.Report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by GabdrakhmanovII on 03.11.2017.
 */

public class CarsInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CarState> mList;
    private Context context;


    public CarsInfoAdapter(Context context, ArrayList<CarState> itemList) {
        this.context = context;
        this.mList = itemList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_car_info, parent, false);
        return new SecondViewHolder(view);

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((SecondViewHolder) holder).tvTitle.setText(mList.get(position).getStateName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm");
        ((SecondViewHolder) holder).tvMsg.setText(mList.get(position).getStateValue());
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




    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);

        }
    }

    public static class SecondViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvMsg;
        private ImageView imageView;

        public SecondViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvMsg = (TextView) itemView.findViewById(R.id.tvMsg);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
