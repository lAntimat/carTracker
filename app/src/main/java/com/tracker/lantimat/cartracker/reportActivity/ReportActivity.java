package com.tracker.lantimat.cartracker.reportActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.tracker.lantimat.cartracker.R;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity implements ReportView {

    RecyclerView recyclerView;
    ReportRecyclerAdapter reportRecyclerAdapter;
    ArrayList<Report> ar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Отчеты");
        setSupportActionBar(toolbar);
        final ReportPresenter presenter = new ReportPresenter(this, getApplicationContext());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               presenter.sendDate();
            }
        });


        initRecyclerView();
        addData();
        presenter.connect();

    }

    public void initRecyclerView() {
        reportRecyclerAdapter = new ReportRecyclerAdapter(ar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(reportRecyclerAdapter);
    }

    public void addData() {
        ar.add(new Report("title", "msg"));
        ar.add(new Report("title", "msg"));
        ar.add(new Report("title", "msg"));
        ar.add(new Report("title", "msg"));
        ar.add(new Report("title", "msg"));
        ar.add(new Report("title", "msg"));


    }

    @Override
    public void showReports() {

    }
}
