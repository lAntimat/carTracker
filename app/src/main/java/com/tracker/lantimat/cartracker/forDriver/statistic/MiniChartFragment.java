package com.tracker.lantimat.cartracker.forDriver.statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.forDriver.statistic.models.ChartData;
import com.tracker.lantimat.cartracker.forDriver.statistic.models.MiniStatisticChart;
import com.tracker.lantimat.cartracker.utils.DayAxisValueFormatter;
import com.tracker.lantimat.cartracker.utils.XYMarkerView;

import java.util.ArrayList;
import java.util.List;


public class MiniChartFragment extends Fragment {

    private static final String EXTRA_CHART = "charts";
    private MiniStatisticChart chartData;
    protected BarChart mChart;
    private TextView tvValue;
    private TextView tvName;

    public MiniChartFragment() {
        // Required empty public constructor
    }

    public static MiniChartFragment newInstance(MiniStatisticChart chart) {
        MiniChartFragment fragment = new MiniChartFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CHART, chart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewpager_item_mini_chart, container, false);

        tvValue = v.findViewById(R.id.tvValue);
        tvName = v.findViewById(R.id.tvName);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chartData = getArguments().getParcelable(EXTRA_CHART);

        initChart(view);


    }

    private void initChart(View v) {
        mChart = v.findViewById(R.id.chart1);
        //mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        mChart.getXAxis().setTextColor(Color.WHITE);
        mChart.getAxisLeft().setTextColor(Color.WHITE);
        mChart.getAxisRight().setTextColor(Color.WHITE);
        mChart.getLegend().setTextColor(Color.WHITE);

        mChart.setTouchEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(15);
        mChart.zoomToCenter(5, 0);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);

        mChart.setHighlightFullBarEnabled(true);

        List<BarEntry> entries = new ArrayList<BarEntry>();

        int i = 0;
        long distance = 0;
        for (ChartData data : chartData.chart) {
            i++;
            // turn your data into Entry objects
            entries.add(new BarEntry(i, data.value));

            distance += data.value;
        }

        long averageConsumption = distance / i;
        tvName.setText(chartData.title);

        switch (chartData.type) {
            case 1:
                tvValue.setText(averageConsumption + " км");
                break;
            case 2:
                tvValue.setText(averageConsumption + " час");
                break;
            case 3:
                tvValue.setText(averageConsumption + " литр");
                break;
        }

        BarDataSet dataSet = new BarDataSet(entries, ""); // add entries to dataset
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

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

        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
    }
}
