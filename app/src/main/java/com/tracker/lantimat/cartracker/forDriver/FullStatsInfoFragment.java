package com.tracker.lantimat.cartracker.forDriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.forDriver.mainInfoFragment.MainState;
import com.tracker.lantimat.cartracker.forDriver.mainInfoFragment.MainStatesAdapter;
import com.tracker.lantimat.cartracker.mapActivity.API.ApiUtils;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.API.SOService;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.reportActivity.ReportActivity;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class FullStatsInfoFragment extends Fragment {

    final static String TAG = "FullStatsInfoFragment";

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

        View v = inflater.inflate(R.layout.full_stats_info_fragment, null);
        btnClickListener();
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        collapsingToolbarLayout =  v.findViewById(R.id.collapsingToolbar);
        //setSupportActionBar(toolbar);
        initRecyclerView(v);

        if(AuthHelper.isAuth()) {
            getCarInfo();
        } else {
            AuthHelper.login(new AuthHelper.OnLoginCallback() {
                @Override
                public void onSuccess() {
                    getCarInfo();
                }

                @Override
                public void onFailure() {

                }
            });
        }

        return v;
    }

    private void getCarInfo() {
        SOService mService;
        mService = ApiUtils.getSOService();

        mService.getObjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<CarsR>>() {
                    @Override
                    public void onNext(ArrayList<CarsR> cars) {
                        Log.d(TAG, "onComplete");
                        if(cars!=null) {
                            arCars.addAll(cars);
                            for (CarsR car:cars) {
                                if(car.get_id().equals("5aa907d3aeb4b811003d4cbb")) updateUI(car);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }


    public void initRecyclerView(View v) {
        ar.add(new MainState("Пробег", "230 км"));
        ar.add(new MainState("Общий пробег", "23406 км"));
        ar.add(new MainState("Температура двигателя", "90`С"));

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

    public void updateUI(CarsR car) {
        //toolbar.setTitle(car.getName());
        //SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        //toolbar.setSubtitle("Последнее обновление " + sf.format(new Date((long) car.getState().getTime())));

        ar.clear();
        ar.add(new MainState("Полный пробег", car.getState().getCan_odo_km() + " км"));
        ar.add(new MainState("Потребление топлива", car.getState().getCons_fuel_l() + " л"));
        ar.add(new MainState("Суммарное время работы двиг.", car.getState().getUptime() + " мин."));

        adapter.notifyDataSetChanged();

    }

    public void btnClickListener() {
        clickListener = view -> {
            switch (view.getId()) {
                case R.id.btn4:
                    startActivity(new Intent(getContext(), ReportActivity.class));
                    break;
                case R.id.btn5:
                    startActivity(new Intent(getContext(), MapActivity.class));
                    break;
            }
        };
    }
}
