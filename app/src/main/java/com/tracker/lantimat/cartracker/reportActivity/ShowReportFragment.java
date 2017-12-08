package com.tracker.lantimat.cartracker.reportActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class ShowReportFragment extends Fragment {

    final static String TAG = "AddReportFragment";
    final static int TITLE_MAX_SIZE = 20;
    final static int MSG_MAX_SIZE = 100;

    View content;
    ProgressBar progressBar;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    ViewPager viewPager;


    public static ShowReportFragment newInstance(Report report) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("report", report);

        ShowReportFragment fragment = new ShowReportFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_show_report, null);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Просмотр отчета");
        content = v.findViewById(R.id.content_layout);
        //content.setVisibility(View.INVISIBLE);
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);

        ReportAdapter reportAdapter = new ReportAdapter(getContext(), (Report)getArguments().getParcelable("report"));
        viewPager.setAdapter(reportAdapter);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class ReportAdapter extends PagerAdapter {

        Context context;
        LayoutInflater layoutInflater;
        Report report;

        public ReportAdapter(Context context, Report report) {
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.report = report;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = layoutInflater.inflate(R.layout.show_report_viewpager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

            Picasso.with(context).load(report.getArImg().get(position))
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public int getCount() {
            if(report.getArImg() != null){
                return report.getArImg().size();
            } else return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }
}
