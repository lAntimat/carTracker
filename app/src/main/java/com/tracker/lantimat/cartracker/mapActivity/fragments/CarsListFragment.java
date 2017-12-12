package com.tracker.lantimat.cartracker.mapActivity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.mapActivity.adapters.CarsListRecyclerAdapter;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import java.util.ArrayList;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class CarsListFragment extends Fragment {

    final static String TAG = "CarListFragment";

    View content;
    ProgressBar progressBar;
    ArrayList<CarsR> ar = new ArrayList<>();

    RecyclerView recyclerView;
    CarsListRecyclerAdapter carsRecyclerAdapter;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    ImageView btnClose;

    public static CarsListFragment newInstance(ArrayList<CarsR> ar) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ar", ar);

        CarsListFragment fragment = new CarsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //important! set your user agent to prevent getting banned from the osm servers
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cars_list, null);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Все автомобили");

        btnClose = (ImageView) v.findViewById(R.id.iv_close);

        content = v.findViewById(R.id.content_layout);
        content.setVisibility(View.INVISIBLE);

        /**Доступ к фрагменту
         /*FragmentManager fm = getActivity().getSupportFragmentManager();
         MapFragment fragment = (MapFragment) fm.findFragmentByTag(Constants.MAP_FRAGMENT);
         //fragment.showTrack();*/

        readBundle(getArguments());
        initRecyclerView(v);

        btnListeners();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initView() {

    }

    private void btnListeners() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity)getActivity()).mapPresenter.hideCarsList();
            }
        });
    }

    public void initRecyclerView(View v) {
        carsRecyclerAdapter = new CarsListRecyclerAdapter(ar);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(carsRecyclerAdapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ((MapActivity)getActivity()).mapPresenter.setCar(position);
                ((MapActivity)getActivity()).mapPresenter.hideCarsList();

            }
        });
    }


    private void readBundle(Bundle bundle) {
        if (bundle != null) {
           ar = bundle.getParcelableArrayList("ar");
            toolbar.setSubtitle("количество " + ar.size());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
