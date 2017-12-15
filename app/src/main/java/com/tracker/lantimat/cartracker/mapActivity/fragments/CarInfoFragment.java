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
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.mapActivity.adapters.CarsInfoAdapter;
import com.tracker.lantimat.cartracker.mapActivity.models.CarState;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import org.osmdroid.config.Configuration;

import java.util.ArrayList;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class CarInfoFragment extends Fragment implements MapActivity.CarInfoFragmentListener {

    final static String TAG = "CarInfoFragment";

    TextView textView;

    private RecyclerView mRecyclerView;
    private CarsInfoAdapter carsInfoAdapter;
    private ArrayList<CarState> ar = new ArrayList<>();

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

        initRecyclerView(v);
        initView();


        return v;
    }

    private void initRecyclerView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
        carsInfoAdapter = new CarsInfoAdapter(getContext(), ar);
        mRecyclerView.setAdapter(carsInfoAdapter);
    }

    @Override
    public void onResume() {
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        super.onResume();
    }


    @Override
    public void addDate(ArrayList<CarState> ar) {
        this.ar.clear();
        this.ar.addAll(ar);
        carsInfoAdapter.notifyDataSetChanged();
        Log.d(TAG, "addDate");
    }
}
