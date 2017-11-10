package com.tracker.lantimat.cartracker.reportActivity;

import com.tracker.lantimat.cartracker.utils.LoadingView;

import java.util.ArrayList;

/**
 * Created by GabdrakhmanovII on 07.11.2017.
 */

public interface ReportView extends LoadingView {

    void showReports(ArrayList<Report> reports);

    void showToast(String str);

    void showAddReportFragment();

    void hideAddReportFragment();
}
