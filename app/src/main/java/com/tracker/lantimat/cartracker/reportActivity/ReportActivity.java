package com.tracker.lantimat.cartracker.reportActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.utils.AnimationUtils;
import com.tracker.lantimat.cartracker.utils.RevealAnimationSetting;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class ReportActivity extends AppCompatActivity implements ReportView {

    public static final String ARG_REVEAL_SETTINGS = "ARG_REVEAL_SETTINGS";

    public ReportPresenter presenter;
    private ProgressBar progressBar;
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
        presenter = new ReportPresenter(this, getApplicationContext());

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showAddReportFragment();
            }
        });


        initRecyclerView();
        //addData();
        presenter.connect();

    }

    public void initRecyclerView() {
        reportRecyclerAdapter = new ReportRecyclerAdapter(getApplicationContext(), ar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(reportRecyclerAdapter);
    }

    @Override
    public void hideAddReportFragment() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() != 0) {
            manager.popBackStack();
        }
    }

    @Override
    public void showReports(ArrayList<Report> reports) {
        ar.clear();
        ar.addAll(reports);
        reportRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddReportFragment() {

        AddReportFragment addReportFragment = new AddReportFragment();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.container, addReportFragment)
                .addToBackStack("report")
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() != 0) {
            manager.popBackStack();
        } else super.onBackPressed();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
