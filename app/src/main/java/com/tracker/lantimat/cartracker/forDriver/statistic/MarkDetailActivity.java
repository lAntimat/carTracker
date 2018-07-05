package com.tracker.lantimat.cartracker.forDriver.statistic;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.utils.CustomProgressBar;

public class MarkDetailActivity extends AppCompatActivity {

    CustomProgressBar markBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_detail);

        markBar = findViewById(R.id.mark);

        setupMarkBar();
    }

    private void setupMarkBar() {
        markBar.setMax(100);
        markBar.setProgress(95);
        markBar.setText("9.5");
        markBar.setTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
    }
}
