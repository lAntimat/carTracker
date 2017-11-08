package com.tracker.lantimat.cartracker.mapActivity.bottomSheetsTimeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.tracker.lantimat.cartracker.R;

import butterknife.ButterKnife;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView tvSubTitle;
    TextView tvParkingTime;
    TextView tvDistance;
    TextView tvTime;
    TimelineView mTimelineView;



    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        tvTitle = (TextView) itemView.findViewById(R.id.text_timeline_date);
        tvSubTitle = (TextView) itemView.findViewById(R.id.text_timeline_title);
        tvParkingTime = (TextView) itemView.findViewById(R.id.tvParkingTime);
        tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        mTimelineView.initLine(viewType);
    }
}
