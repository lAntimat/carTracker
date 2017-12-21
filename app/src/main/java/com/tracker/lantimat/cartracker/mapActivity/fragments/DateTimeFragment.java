package com.tracker.lantimat.cartracker.mapActivity.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.mapActivity.adapters.CarsListRecyclerAdapter;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class DateTimeFragment extends Fragment {

    final static String TAG = "DateTimeFragment";
    final static int FROM = 1;
    final static int TO = 2;

    private int fromOrTo = -1;

    View content;
    Toolbar toolbar;
    ImageView btnClose;
    TextView tvFrom, tvTo;
    Button btn;
    Calendar dateAndTime = Calendar.getInstance();
    long timeBegin;
    long timeEnd;

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            dateAndTime.set(Calendar.YEAR, i);
            dateAndTime.set(Calendar.MONTH, i1);
            dateAndTime.set(Calendar.DAY_OF_MONTH, i2);
            Date date = new Date(dateAndTime.getTimeInMillis());
            //mapPresenter.loadTrack(date);
            //mapPresenter.getTrack("", dateAndTime.getTimeInMillis(), new Date().getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            String formattedDate = simpleDateFormat.format(date);
            switch (fromOrTo) {
                case FROM:
                    timeBegin = date.getTime();
                    tvFrom.setText(formattedDate);
                    break;
                case TO:
                    timeEnd = date.getTime();
                    tvTo.setText(formattedDate);
                    break;
            }
        }
    };

    public static DateTimeFragment newInstance(ArrayList<CarsR> ar) {
        Bundle bundle = new Bundle();
        //bundle.putParcelableArrayList("ar", ar);

        DateTimeFragment fragment = new DateTimeFragment();
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

        View v = inflater.inflate(R.layout.fragment_date_time, null);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Получить трек");

        btnClose = (ImageView) v.findViewById(R.id.iv_close);

        tvFrom = (TextView) v.findViewById(R.id.tvFrom);
        tvTo = (TextView) v.findViewById(R.id.tvTo);

        btn = (Button) v.findViewById(R.id.btnTrack);


        //readBundle(getArguments());
        btnListeners();

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void btnListeners() {
        tvFrom.setOnClickListener(view -> {
            fromOrTo = FROM;
            new DatePickerDialog(getActivity(), d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        tvTo.setOnClickListener(view -> {
            fromOrTo = TO;
            new DatePickerDialog(getActivity(), d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        btn.setOnClickListener(view -> {
                ((MapActivity)getActivity()).mapPresenter.getTrack("", timeBegin, timeEnd);
                ((MapActivity)getActivity()).mapPresenter.hideDateTimeFragment();
        });

    }



    private void readBundle(Bundle bundle) {
        if (bundle != null) {
           //ar = bundle.getParcelableArrayList("ar");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
