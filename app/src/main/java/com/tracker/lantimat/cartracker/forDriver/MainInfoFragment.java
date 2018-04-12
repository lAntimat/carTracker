package com.tracker.lantimat.cartracker.forDriver;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import static com.tracker.lantimat.cartracker.utils.MyApplication.getContext;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class MainInfoFragment extends Fragment {

    final static String TAG = "MainInfoFragment";

    private RecyclerView recyclerView;
    private MainStatesAdapter adapter;
    private ArrayList<MainState> ar = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.main_info_fragment, null);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        initRecyclerView(v);
        return v;
    }

    public void initRecyclerView(View v) {

        ar.add(new MainState("Топливо", "22л", R.drawable.gas_station, 60));
        ar.add(new MainState("Аккумулятор", "100%/13 V", R.drawable.car_battery));
        ar.add(new MainState("Двигатель", "", R.drawable.engine_outline));


        adapter = new MainStatesAdapter(getContext(), ar);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
    }

}
