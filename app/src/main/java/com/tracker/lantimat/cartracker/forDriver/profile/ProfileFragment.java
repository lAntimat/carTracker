package com.tracker.lantimat.cartracker.forDriver.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tracker.lantimat.cartracker.LoginActivity;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.forDriver.mainInfoFragment.MainState;
import com.tracker.lantimat.cartracker.forDriver.mainInfoFragment.MainStatesAdapter;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;

import java.util.ArrayList;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class ProfileFragment extends Fragment {

    final static String TAG = "MainInfoFragment";

    private RecyclerView recyclerView;
    private MainStatesAdapter adapter;
    private ArrayList<MainState> ar = new ArrayList<>();
    private ArrayList<CarsR> arCars = new ArrayList<>();
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private View.OnClickListener clickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.profile_fragment, null);
        btnClickListener();
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        v.findViewById(R.id.btn1).setOnClickListener(clickListener);
        collapsingToolbarLayout =  v.findViewById(R.id.collapsingToolbar);

        return v;
    }

    public void btnClickListener() {
        clickListener = view -> {
            switch (view.getId()) {
                case R.id.btn1:
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    break;
            }
        };
    }
}
