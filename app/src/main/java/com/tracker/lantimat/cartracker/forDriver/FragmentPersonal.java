package com.tracker.lantimat.cartracker.forDriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import com.squareup.picasso.Picasso;
import com.tracker.lantimat.cartracker.LoginActivity;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.models.User;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Alexander on 10.01.2018.
 */

public class FragmentPersonal extends Fragment {

    private static final int STATE_AUTH = 1;
    private static final int STATE_NOT_AUTH = 2;

    public static final int REQUEST_CODE = 20;
    private static final int RC_SIGN_IN = 123;

    private View view;
    private TextView tvToolbarTitle, tvToolbarSubtitle;
    private TextView tvSignInOut, tv3;
    private CircleImageView ivToolbarProfile;
    ImageView iv7;

    private ConstraintLayout clSignInOut, clEditProfile, clNotifications, clAddresses, clMyAds;
    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;

    CheckUserCallback checkUserCallback;

    interface CheckUserCallback {
        void onSuccess(User user);
        void onUserDoNotCreate();
    }

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        mAuth = FirebaseAuth.getInstance();
        tvToolbarTitle = view.findViewById(R.id.tvToolbarTitle);
        tvToolbarSubtitle = view.findViewById(R.id.tvToolbarSubtitle);
        ivToolbarProfile = view.findViewById(R.id.profile_image);
        setStatusBarTranslucent(true);
        tvSignInOut = view.findViewById(R.id.tvSignInOut);
        clSignInOut = view.findViewById(R.id.clSignInOut);
        clEditProfile = view.findViewById(R.id.clProfile);
        clNotifications = view.findViewById(R.id.clNotifications);
        clAddresses = view.findViewById(R.id.clAddresses);
        //clMyAds = view.findViewById(R.id.clMyAds);
        iv7 = view.findViewById(R.id.iv7);

        tv3 = view.findViewById(R.id.tv3);

        updateUI();
        btnClickListeners();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI () {
        if(AuthHelper.isAuth()) {
            updateUI(STATE_AUTH);
        } else {
            updateUI(STATE_NOT_AUTH);
        }
    }

    private void addressButtonActive() {
        clAddresses.setClickable(true);
    }

    private void addressButtonNonActive() {
        clAddresses.setClickable(false);
    }

    private void updateUI(int state) {

        User user = AuthHelper.getUser();
        switch (state) {
            case STATE_AUTH:
                tvSignInOut.setText(R.string.title_personal_sign_out);
                tvToolbarTitle.setText(user.getDisplayName());
                if (user.getImgUrl() != null)
                   Picasso.with(getContext())
                            .load(user.getImgUrl())
                            .placeholder(R.drawable.placeholder_image)
                            .into(ivToolbarProfile);

                addressButtonActive();
                break;
            case STATE_NOT_AUTH:
                tvSignInOut.setText(R.string.title_personal_sign_in);
                tvToolbarTitle.setText("Вы не авторизованы");
                //tvToolbarSubtitle.setText("");
                Picasso.with(getContext())
                        .load(R.mipmap.ic_launcher)
                        .into(ivToolbarProfile);

                addressButtonNonActive();
                break;
        }

    }

    private void btnClickListeners() {
        clSignInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AuthHelper.isAuth()) AuthHelper.signOut();
                else startActivity(new Intent(getContext(), LoginActivity.class));
                updateUI();
            }
        });

        clEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), ProfileCreateActivity.class));
            }
        });

        clNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), NotificationsActivity.class));
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RESULT_OK) {
            updateUI(STATE_AUTH);
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}