package com.tracker.lantimat.cartracker.reportActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.utils.ItemClickSupport;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class AddReportFragment extends Fragment implements ReportImagesRecyclerAdapter.BtnClickListener, ReportActivity.AddReportFragmentListener {

    final static String TAG = "AddReportFragment";
    final static int TITLE_MAX_SIZE = 20;
    final static int MSG_MAX_SIZE = 100;

    View content;
    ProgressBar progressBar;
    ArrayList<Cars> ar = new ArrayList<>();

    Uri file;

    ArrayList<UploadImage> arUploadImages = new ArrayList<>();

    RecyclerView recyclerView;
    ReportImagesRecyclerAdapter reportImagesRecyclerAdapter;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;

    ImageView btnClose;
    ImageView imageView;
    EditText edTitle, edMsg;
    TextView tvTitleCount, tvMsgCount;

    Target target;

    @Override
    public void onProgressUpdate(int position, int progress) {
        reportImagesRecyclerAdapter.setProgress(position, progress);

    }

    @Override
    public void onSuccess(int position) {
        reportImagesRecyclerAdapter.setIsLoaded(position,true);

    }

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
        tvTitleCount = (TextView) v.findViewById(R.id.tvTitleCount);
        tvMsgCount = (TextView) v.findViewById(R.id.tvMsgCount);

        btnClose = (ImageView) v.findViewById(R.id.iv_close);

        fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);

        content = v.findViewById(R.id.content_layout);
        //content.setVisibility(View.INVISIBLE);


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            imageView.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        initRecyclerView(v);
        btnListeners();
        initTextWatcher();

        return v;
    }

    private void initTextWatcher() {
        edTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvTitleCount.setText(charSequence.length() + "/" + TITLE_MAX_SIZE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvMsgCount.setText(charSequence.length() + "/" + MSG_MAX_SIZE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((ReportActivity)getActivity()).registerDataUpdateListener(this);
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        arUploadImages.add(new UploadImage(Uri.parse("uri"), false));
        reportImagesRecyclerAdapter = new ReportImagesRecyclerAdapter(getContext(), arUploadImages, this);
        recyclerView.setAdapter(reportImagesRecyclerAdapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
    }


    private void btnListeners() {


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Report report = new Report(edTitle.getText().toString(), edMsg.getText().toString(), new Date());
                ((ReportActivity)getActivity()).presenter.sendData(report);
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
                //Picasso.with(getContext()).load(file).fit().centerCrop().into(imageView);
                //arUploadImages.add((arUploadImages.size() - 1), new UploadImage(file, false));
                reportImagesRecyclerAdapter.add(file);
                upload(file);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void upload(Uri file) {

            target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d(TAG, "onBitmapLoaded");
                uploadImage(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d(TAG, "onBitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d(TAG, "onPrepareLoad");
            }
        };

        Picasso.with(getContext()).load(file).resize(1000, 1000).into(target);
    }

    public void uploadImage(Bitmap bitmap) {
        // Get the data from an ImageView as bytes
        //Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] data = baos.toByteArray();
        //Log.d(TAG, "uploadImage");

        if (data.length > 0) {
            ((ReportActivity) getContext()).presenter.uploadReport(data);
        }
    }

    @Override
    public void ivAddClick() {
        takePicture();
    }

    @Override
    public void ivDelClick() {

    }
}
