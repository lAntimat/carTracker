package com.tracker.lantimat.cartracker.mapActivity.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.reportActivity.Report;

import java.util.ArrayList;

/**
 * Created by GabdrakhmanovII on 03.11.2017.
 */

public class CarsListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CarsR> mList;


    public CarsListRecyclerAdapter(ArrayList<CarsR> itemList) {
        this.mList = itemList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_report, parent, false);
        return new SecondViewHolder(view);

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((SecondViewHolder) holder).tvTitle.setText(mList.get(position).getName());
        ((SecondViewHolder) holder).tvMsg.setText(mList.get(position).getType());

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

        public SecondViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvMsg = (TextView) itemView.findViewById(R.id.tvMsg);
        }
    }
}
