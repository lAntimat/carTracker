package com.tracker.lantimat.cartracker.forDriver.statistic;

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
import com.tracker.lantimat.cartracker.forDriver.AuthHelper;
import com.tracker.lantimat.cartracker.forDriver.statistic.models.ChartData;
import com.tracker.lantimat.cartracker.forDriver.statistic.models.MiniStatisticChart;
import com.tracker.lantimat.cartracker.mapActivity.API.ApiUtils;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.API.SOService;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.reportActivity.ReportActivity;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class FullStatsInfoFragment extends Fragment {

    final static String TAG = "FullStatsInfoFragment";

    private RecyclerView recyclerView;
    private StatsAdapter adapter;
    private ArrayList<ArrayList<MiniStatisticChart>> ar = new ArrayList<>();
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
                        if(cars!=null && cars.size() > 5) {
                            arCars.addAll(cars);
                            updateUI(cars.get(5));
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
        Random randomGenerator = new Random();

        ArrayList<MiniStatisticChart> arMinistatisticChart = new ArrayList<>();

        ArrayList<ChartData> arChartData = new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            arChartData.add(new ChartData(new Date(), new Long(randomGenerator.nextInt(300) )));
        }
        arMinistatisticChart.add(new MiniStatisticChart(MiniStatisticChart.DISTANCE, "Средний пройденный путь", arChartData));
        arChartData = new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            arChartData.add(new ChartData(new Date(), new Long(randomGenerator.nextInt(12) )));
        }
        arMinistatisticChart.add(new MiniStatisticChart(MiniStatisticChart.DRIVE_TIME, "Среднее время в пути", arChartData));
        arChartData = new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            arChartData.add(new ChartData(new Date(), new Long(randomGenerator.nextInt(50) )));
        }

        arMinistatisticChart.add(new MiniStatisticChart(MiniStatisticChart.FUEL_CONSUMPTION, "Средний расход", arChartData));

        ar.add(arMinistatisticChart);
        ar.add(arMinistatisticChart);

        adapter = new StatsAdapter(getContext(), ar);

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
        /*ar.add(new MainState("Полный пробег", car.getState().getCan_odo_km() + " км"));
        ar.add(new MainState("Пробег до сброса", car.getState().getCan_odo_p() + " км"));
        ar.add(new MainState("Суммарное время работы двиг.", car.getState().getUptime()/60 + " ч."));
        ar.add(new MainState("Топливо", car.getState().getFuel_lev_p() + "%", R.drawable.gas_station, (int) car.getState().getFuel_lev_p()));
        ar.add(new MainState("Аккумулятор", (int) car.getState().getVoltage() + " V", R.drawable.car_battery));
        ar.add(new MainState("Температура двигателя", car.getState().getEng_temp() + " `С"));*/
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
