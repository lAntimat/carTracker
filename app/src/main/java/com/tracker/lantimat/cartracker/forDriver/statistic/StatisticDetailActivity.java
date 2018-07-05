package com.tracker.lantimat.cartracker.forDriver.statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.forDriver.statistic.models.ChartData;
import com.tracker.lantimat.cartracker.forDriver.statistic.models.MiniStatisticChart;
import com.tracker.lantimat.cartracker.utils.DayAxisValueFormatter;
import com.tracker.lantimat.cartracker.utils.XYMarkerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticDetailActivity extends AppCompatActivity {

    private BarChart mChart;
    private MiniStatisticChart chartData;
    private TextView tvToday, tvTotal, tvTodayValue, tvTotalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvToday = findViewById(R.id.tvTimeTitle);
        tvTodayValue = findViewById(R.id.tvTimeValue);
        tvTotal = findViewById(R.id.tvDistanceTitle);
        tvTotalValue = findViewById(R.id.tvDistanceValue);

        chartData = getIntent().getExtras().getParcelable(MiniChartFragment.EXTRA_CHART);
        toolbar.setTitle(chartData.title);

        firstFillTextViews();

        initChart();
    }

    private void firstFillTextViews() {
        SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");
        Long value = chartData.chart.get(chartData.chart.size() - 1).value;
        Date date = chartData.chart.get(chartData.chart.size() - 1).date;

        switch (chartData.type) {
            case MiniStatisticChart.DISTANCE:
                tvToday.setText("За " + sf.format(date));
                tvTodayValue.setText(value + " км");
                tvTotal.setText("Всего");
                tvTotalValue.setText(getFullValue(chartData) + " км");
                break;
            case MiniStatisticChart.DRIVE_TIME:
                tvToday.setText("За " + sf.format(date));
                tvTodayValue.setText(value + " ч");
                tvTotal.setText("Всего");
                tvTotalValue.setText(getFullValue(chartData) + " ч");
                break;
            case MiniStatisticChart.FUEL_CONSUMPTION:
                tvToday.setText("За " + sf.format(date));
                tvTodayValue.setText(value + " л");
                tvTotal.setText("Всего");
                tvTotalValue.setText(getFullValue(chartData) + "л");
                break;
        }
    }

    private long getFullValue(MiniStatisticChart miniStatisticChart) {
        long distance = 0;

        for (int i = 0; i < miniStatisticChart.chart.size(); i++) {
            distance = distance + miniStatisticChart.chart.get(i).value;
        }
        return distance;
    }

    private void initChart() {
        mChart = findViewById(R.id.chart1);
        //mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        //mChart.getDescription().setEnabled(true);

        mChart.getXAxis().setTextColor(Color.WHITE);
        mChart.getAxisLeft().setTextColor(Color.WHITE);
        mChart.getAxisRight().setTextColor(Color.WHITE);
        mChart.getLegend().setTextColor(Color.WHITE);

        mChart.setTouchEnabled(true);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(30);
        mChart.zoomToCenter(10, 0);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(true);

        mChart.setHighlightFullBarEnabled(true);

        List<BarEntry> entries = new ArrayList<BarEntry>();

        int i = 0;
        for (ChartData data : chartData.chart) {
            i++;
            // turn your data into Entry objects
            entries.add(new BarEntry(i, data.value));
        }

        BarDataSet dataSet = new BarDataSet(entries, ""); // add entries to dataset
        dataSet.setColors(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent1));
        mChart.moveViewToX(chartData.chart.size());

        BarData barData = new BarData(dataSet);
        mChart.setData(barData);
        mChart.invalidate(); // refresh

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart


        onChartClick();
    }

    private void onChartClick() {

        SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int x = (int) e.getX();
                Long value = chartData.chart.get(x-1).value;
                Date date = chartData.chart.get(x-1).date;

                Log.d("onScrollChange", "Selected " + e.getX());
                switch (chartData.type) {
                    case MiniStatisticChart.DISTANCE:
                        tvToday.setText("За " + sf.format(date));
                        tvTodayValue.setText(value + " км");
                        break;
                    case MiniStatisticChart.DRIVE_TIME:
                        tvToday.setText("За " + sf.format(date));
                        tvTodayValue.setText(value + " ч");
                        break;
                    case MiniStatisticChart.FUEL_CONSUMPTION:
                        tvToday.setText("За " + sf.format(date));
                        tvTodayValue.setText(value + " л");
                        break;
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }


}
