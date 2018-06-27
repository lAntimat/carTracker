package com.tracker.lantimat.cartracker.mapActivity.fragments;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.API.TrackR;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.mapActivity.bottomSheetsTimeline.TimeLineAdapter;
import com.tracker.lantimat.cartracker.mapActivity.bottomSheetsTimeline.model.OrderStatus;
import com.tracker.lantimat.cartracker.mapActivity.bottomSheetsTimeline.model.Orientation;
import com.tracker.lantimat.cartracker.mapActivity.bottomSheetsTimeline.model.TimeLineModel;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import org.osmdroid.config.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class TrackFragment extends Fragment implements MapActivity.TrackFragmentListener {

    final static String TAG = "TrackFragment";

    Button btnTrack;
    EditText edTrackStart, edTrackStop;

    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    private Orientation mOrientation = Orientation.VERTICAL;
    private boolean mWithLinePadding = false;

    public TrackFragment() {
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

        View v = inflater.inflate(R.layout.fragment_track, null);

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
        ((MapActivity) context).registerTrackFragmentListener(this);
        super.onAttach(context);
    }

    private void initView() {
        //setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation, mWithLinePadding);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private void addDataToRecyclerView(ArrayList<TrackR> tracks) {
        //arraySort(tracks);
    }



    private void arraySort(ArrayList<Track> tracks) {

        if(mDataList!=null) mDataList.clear();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");

        double speedLimit = 1;

        for (int i = 0; i < tracks.size(); i++) {

            double distance = 0D;
            String startDate = "";
            String endDate = "";
            long driveTime = 0;


            if (tracks.get(i).getSpeed() <= speedLimit) {
                startDate = dfTime.format(tracks.get(i).getTimestamp());

                do {
                    i++;
                    if (i == tracks.size()) return;
                } while (tracks.get(i).getSpeed() <= speedLimit);

                endDate = dfTime.format(tracks.get(i).getTimestamp());

            }

            if (tracks.get(i).getSpeed() > speedLimit) {
                do {
                    if( i < 1) i++;
                    else if (tracks.size() > 0) {
                        distance += distanceBetween(tracks.get(i - 1).getGeoPoint(), tracks.get(i).getGeoPoint()); //Расстояние между двумя точками
                        //Log.d(TAG, "distance " + distance);
                        driveTime += timeBetween(tracks.get(i - 1).getTimestamp(), tracks.get(i).getTimestamp());
                        //Log.d(TAG, "driveTime " + driveTime);
                        i++;
                        if (i == tracks.size()) return;
                    }
                } while (tracks.get(i).getSpeed() > speedLimit);

                if (i == tracks.size()) return;
                }

            Date tempDate = new Date(driveTime);

            String driveTimeStr = "";
            String distanceStr = "";

            distanceStr = String.valueOf(distance) + " метров";

            ///driveTimeStr = String.valueOf(driveTime / 1000 / 60);

            driveTimeStr = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(driveTime),
                    TimeUnit.MILLISECONDS.toSeconds(driveTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(driveTime))
            );

            mDataList.add(new TimeLineModel("Остановка", "Движение", startDate + "-" + endDate, distanceStr, driveTimeStr, OrderStatus.ACTIVE));
            Log.d(TAG, "datalist add");


            driveTime = 0;
            distance = 0D;
        }
    }

    public double distanceBetween(com.google.firebase.firestore.GeoPoint geoPoint, com.google.firebase.firestore.GeoPoint geoPoint2) {
        double lon, lat, lon2, lat2;
        lat = geoPoint.getLatitude();
        lon = geoPoint.getLongitude();
        lat2 = geoPoint2.getLatitude();
        lon2 = geoPoint2.getLongitude();
        return 111.2 * Math.sqrt((lon - lon2) * (lon - lon2) + (lat - lat2) * Math.cos(Math.PI * lon / 180) * (lat - lat2) * Math.cos(Math.PI * lon / 180));
    }

    public long timeBetween(Date date, Date date2) {
        return date2.getTime() - date.getTime();
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
    public void addTracks(ArrayList<TrackR> tracks) {
        addDataToRecyclerView(tracks);
        mTimeLineAdapter.notifyDataSetChanged();
    }
}
