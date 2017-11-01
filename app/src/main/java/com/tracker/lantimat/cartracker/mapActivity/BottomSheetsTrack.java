package com.tracker.lantimat.cartracker.mapActivity;


import android.content.Context;
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

/**
 * Created by GabdrakhmanovII on 03.08.2017.
 */

public class BottomSheetsTrack {

    private static final String TAG = "BottomSheetsTrack";

    public MyClickListener prevBtnClick;
    public MyClickListener nextBtnClick;
    private BottomSheetBehavior bottomSheetBehavior;
    private Context context;
    private MapActivity activity;
    private ImageView btnPrev, btnNext;
    private TextView tvTravelTime, tvWayLength;

    private ProgressBar progressBar;
    private SeekBar seekBar;

    private boolean clicked = false;
    private boolean isPressed;

    private View peekContent;
    private View peekProgressBar;

    public interface MyClickListener {
        public void OnClick();
    }

    public BottomSheetsTrack(MapActivity activity) {
        this.activity = activity;
        // get the bottom sheet view
        LinearLayout llBottomSheet = (LinearLayout) activity.findViewById(R.id.bottom_sheet);
        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        //View peakView = activity.findViewById(R.id.peek_view);
        //bottomSheetBehavior.setPeekHeight(peakView.getHeight());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        //peakView.requestLayout();

        initView();
    }

    private void initView() {
        tvTravelTime = (TextView) activity.findViewById(R.id.tvName);
        tvWayLength = (TextView) activity.findViewById(R.id.tvDate);

        btnNext = (ImageView) activity.findViewById(R.id.ivNext);
        btnPrev = (ImageView) activity.findViewById(R.id.ivPrev);

        //progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        seekBar = (SeekBar) activity.findViewById(R.id.seekBar);

        peekContent = (RelativeLayout) activity.findViewById(R.id.peek_content);
        peekProgressBar = (RelativeLayout) activity.findViewById(R.id.peek_progress);

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

    public void setTime(String str) {
        tvTravelTime.setText("Время " + str);
    }

    public void setSpeed(Double speed) {
        tvWayLength.setText("Скорость " + speed * 3.6);
    }

    public void setSeekBarMax(int max) {
        if (max != 0) seekBar.setMax(max - 1);
        seekBar.setProgress(0);
    }

    public void setSeekBarProgress(int progress) {
        seekBar.setProgress(progress);
    }


    public void setState(int state) {
        if(state == BottomSheetBehavior.STATE_HIDDEN) bottomSheetBehavior.setHideable(true);
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
                //prevBtnClick.OnClick();
                //setState(BottomSheetBehavior.STATE_HIDDEN);
                activity.mapPresenter.trackSeekBar(seekBar.getProgress() - 1);
                Log.d(TAG, "Progress " + seekBar.getProgress());

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nextBtnClick.OnClick();
                //setState(BottomSheetBehavior.STATE_HIDDEN);
                activity.mapPresenter.trackSeekBar(seekBar.getProgress() + 1);

            }
        });

        btnNext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                doPressDown();
                return false;
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                activity.mapPresenter.trackSeekBar(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void doPressDown() {

        isPressed = true;

        new Thread() {
            public void run() {

                while (isPressed) {
                    activity.mapPresenter.trackSeekBar(seekBar.getProgress() + 1);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // here we increment till user release the button
            }
        };
    }

    private void doPressRelease() {
        isPressed = false;
    }
}
