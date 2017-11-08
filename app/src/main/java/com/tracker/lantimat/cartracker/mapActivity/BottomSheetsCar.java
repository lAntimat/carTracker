package com.tracker.lantimat.cartracker.mapActivity;


import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tracker.lantimat.cartracker.R;

import java.util.Date;

/**
 * Created by GabdrakhmanovII on 03.08.2017.
 */

public class BottomSheetsCar {

    private static final String TAG = "BottomSheetsTrack";

    public MyClickListener prevBtnClick;
    public MyClickListener nextBtnClick;
    public BottomSheetBehavior bottomSheetBehavior;
    private Context context;
    private MapActivity activity;
    private ImageView btnPrev, btnNext;
    private TextView tvName, tvSub, tvStatus, tvSpeed;

    private ProgressBar progressBar;
    private SeekBar seekBar;

    private boolean clicked = false;
    private boolean isPressed;

    private View peekContent;
    private View peekProgressBar;

    LinearLayout l;

    public interface MyClickListener {
        public void OnClick();
    }

    public BottomSheetsCar(MapActivity activity) {
        this.activity = activity;
        // get the bottom sheet view
        l = (LinearLayout) activity.findViewById(R.id.bottom_sheet_car);
        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(l);
        //View peakView = activity.findViewById(R.id.peek_view);
        //bottomSheetBehavior.setPeekHeight(peakView.getHeight());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        //peakView.requestLayout();
        initView();
    }

    private void initView() {
        tvName = (TextView) l.findViewById(R.id.tvTitle);
        tvSub = (TextView) l.findViewById(R.id.tvSubTitle);
        tvStatus = (TextView) l.findViewById(R.id.tvStatus);
        tvSpeed = (TextView) l.findViewById(R.id.tvSpeed);

        btnNext = (ImageView) l.findViewById(R.id.ivNext);
        btnPrev = (ImageView) l.findViewById(R.id.ivPrev);

        peekContent = (RelativeLayout) l.findViewById(R.id.peek_content);
        peekProgressBar = (RelativeLayout) l.findViewById(R.id.peek_progress);

        listeners();
    }

    public void showProgress() {
        peekProgressBar.setVisibility(View.VISIBLE);
        peekContent.setVisibility(View.GONE);
    }

    public void hideProgress() {
        peekProgressBar.setVisibility(View.GONE);

        peekContent.setAlpha(0.0f);
        peekContent.setVisibility(View.VISIBLE);
        peekContent.animate()
                .alpha(1.0f)
                .setListener(null);

        bottomSheetBehavior.setHideable(false);
    }

    public void setPrevBtnClick(MyClickListener listener) {
        this.prevBtnClick = listener;
    }

    public void setNextBtnClick(MyClickListener listener) {
        this.nextBtnClick = listener;
    }

    public void setCarName(String name) {
        tvName.setText(name);
    }

    public void setCarNumber(String number) {
        tvSub.setText(number);
    }

    public void setSpeed(String speed) {
        tvSpeed.setText(speed);
    }

    public void setDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
        tvStatus.setText(df.format(date));
    }

    public void setSeekBarMax(int max) {
        if (max != 0) seekBar.setMax(max - 1);
        seekBar.setProgress(0);
    }

    public void setSeekBarProgress(int progress) {
        seekBar.setProgress(progress);
    }


    public void setState(int state) {
        if (state == BottomSheetBehavior.STATE_HIDDEN) bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(state);
    }

    public void setPeekVisibility(Boolean b) {
        clicked = true;
        Log.d("BottomSheetsHelper", "setPeekVisibility");
        if (b) {
            if (!isPeekVisible()) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN & clicked) {
                            bottomSheetBehavior.setPeekHeight(120);
                            //relativeLayoutPeek.setVisibility(View.VISIBLE);
                            clicked = false;
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });
            } else bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        } else {
            bottomSheetBehavior.setPeekHeight(0);
        }
    }


    public boolean isPeekVisible() {
        if (bottomSheetBehavior.getPeekHeight() == 0) return false;
        else return true;
    }

    private void listeners() {
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.mapPresenter.prevCar();


            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nextBtnClick.OnClick();
                //setState(BottomSheetBehavior.STATE_HIDDEN);
                activity.mapPresenter.nextCar();

            }
        });
    }

}
