package com.tracker.lantimat.cartracker.reportActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.mapActivity.adapters.CarsListRecyclerAdapter;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class AddReportFragment extends Fragment {

    final static String TAG = "CarListFragment";

    View content;
    ProgressBar progressBar;
    ArrayList<Cars> ar = new ArrayList<>();

    Uri file;

    RecyclerView recyclerView;
    CarsListRecyclerAdapter carsRecyclerAdapter;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;

    ImageView btnClose;
    ImageView imageView;
    EditText edTitle;
    EditText edMsg;

    public static AddReportFragment newInstance() {
        Bundle bundle = new Bundle();
        //bundle.putParcelableArrayList("ar", ar);

        AddReportFragment fragment = new AddReportFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_report, null);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Отправка отчета");

        imageView = (ImageView) v.findViewById(R.id.imageView);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();

        edTitle = (EditText) v.findViewById(R.id.tvTitle);
        edMsg = (EditText) v.findViewById(R.id.tvMsg);

        btnClose = (ImageView) v.findViewById(R.id.iv_close);

        fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);

        content = v.findViewById(R.id.content_layout);
        //content.setVisibility(View.INVISIBLE);


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            imageView.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

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


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the data from an ImageView as bytes
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] data = baos.toByteArray();

                Report report = new Report(edTitle.getText().toString(), edMsg.getText().toString(), new Date());

                if (data.length > 0) {
                    ((ReportActivity) getActivity()).presenter.uploadReport(report, data);
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReportActivity)getActivity()).presenter.hideFragment();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = getOutputMediaFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 100);
    }

    private Uri getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File path = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        Uri photoUri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".com.tracker.lantimat.cartracker.provider",
                path);

        return photoUri;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                imageView.setEnabled(true);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Picasso.with(getContext()).load(file).fit().centerCrop().into(imageView);
                //imageView.setImageURI(file);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
