package com.tracker.lantimat.cartracker.mapActivity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.mapActivity.models.User;

import org.osmdroid.config.Configuration;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class UserInfoFragment extends Fragment implements MapActivity.UserInfoFragmentListener {

    final static String TAG = "CarInfoFragment";

    TextView tvName, tvSubname, tvOther;
    CircleImageView profileImage;

    View content;
    ProgressBar progressBar;

    public UserInfoFragment() {
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

        View v = inflater.inflate(R.layout.fragment_user_info, null);
        tvName = (TextView) v.findViewById(R.id.tvTitle);
        tvSubname = (TextView) v.findViewById(R.id.tvSubTitle);
        tvOther = (TextView) v.findViewById(R.id.tvOther);

        profileImage = (CircleImageView) v.findViewById(R.id.profile_image);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        content = v.findViewById(R.id.content_layout);
        content.setVisibility(View.INVISIBLE);

        /**Доступ к фрагменту
         /*FragmentManager fm = getActivity().getSupportFragmentManager();
         MapFragment fragment = (MapFragment) fm.findFragmentByTag(Constants.MAP_FRAGMENT);
         //fragment.showTrack();*/


        initView();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        ((MapActivity) context).registerUserInfoFragmentListener(this);
        super.onAttach(context);
    }

    private void initView() {

    }

    @Override
    public void onResume() {
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        super.onResume();
    }

    @Override
    public void addDate(User user) {
        Log.d(TAG, "addDate");

        content.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        Picasso.with(getContext()).load(user.getImgUrl()).into(profileImage);

        tvName.setText(user.getName());
        tvSubname.setText(user.getSubName());
        tvOther.setText("Номер вод. уд. " + user.getDriverLicenseId() + "\nНомер телефона: " + "+79600000000");
    }

    @Override
    public void showLoading() {
        content.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }
}
