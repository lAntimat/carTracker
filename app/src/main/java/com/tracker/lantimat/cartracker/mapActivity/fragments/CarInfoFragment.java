package com.tracker.lantimat.cartracker.mapActivity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.mapActivity.bottomSheetsTimeline.TimeLineAdapter;
import com.tracker.lantimat.cartracker.mapActivity.bottomSheetsTimeline.model.Orientation;
import com.tracker.lantimat.cartracker.mapActivity.bottomSheetsTimeline.model.TimeLineModel;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import org.osmdroid.config.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class CarInfoFragment extends Fragment implements MapActivity.CarInfoFragmentListener {

    final static String TAG = "CarInfoFragment";

    TextView textView;

    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    private Orientation mOrientation = Orientation.VERTICAL;
    private boolean mWithLinePadding = false;

    public CarInfoFragment() {
    }

    //public static TrackFragment newInstance() {
    //TrackFragment fragment = new TrackFragment();

    //return fragment;
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //important! set your user agent to prevent getting banned from the osm servers
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_car_info, null);
        textView = (TextView) v.findViewById(R.id.textView);

        /**Доступ к фрагменту
         /*FragmentManager fm = getActivity().getSupportFragmentManager();
         MapFragment fragment = (MapFragment) fm.findFragmentByTag(Constants.MAP_FRAGMENT);
         //fragment.showTrack();*/


        initRecyclerView(v);
        initView();


        return v;
    }

    private void initRecyclerView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.d(TAG, "item click by position: " + position);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        ((MapActivity) context).registerCarInfoFragmentListener(this);
        super.onAttach(context);
    }

    private void initView() {
        //setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation, mWithLinePadding);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private void addDataToRecyclerView(ArrayList<Track> tracks) {

    }

    private LinearLayoutManager getLinearLayoutManager() {
        if (mOrientation == Orientation.HORIZONTAL) {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        }
    }

    @Override
    public void onResume() {
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        super.onResume();
    }


    @Override
    public void addDate(CarsR car) {
        Log.d(TAG, "addDate");
        textView.setText("Скорость " + car.getState().getSpeed() + "\nнапряжение батареи" + car.getState().getBat_voltage() + "\nДвигатель работает " + car.getState().getEgts() + "\n" + car.getState().getAngle());
    }
}
