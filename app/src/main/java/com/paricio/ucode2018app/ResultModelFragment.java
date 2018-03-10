package com.paricio.ucode2018app;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.paricio.ucode2018app.Network.RetrofitHelper;
import com.paricio.ucode2018app.Network.UCodeService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class ResultModelFragment extends Fragment {

    private View view;
    private ImageView image;
    private String path;
    private ProgressBar progressBar;
    private File file;
    private UCodeService service;
    private boolean failure;

    public static ResultModelFragment newInstance() {
        return new ResultModelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_model_fragment,container,false);
        this.view = view;

        failure = false;
        path = getActivity().getIntent().getStringExtra("imagepath");

        service = new RetrofitHelper().getUCodeService();
        file = new File(path);
        image = view.findViewById(R.id.image);
        progressBar = view.findViewById(R.id.pb_loading);
        Picasso.with(view.getContext()).load(file).into(image);

        progressBar.setVisibility(View.VISIBLE);
        callServerGetNewModel();
        return view;
    }


    private void callServerGetNewModel() {
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);



        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, "image");

        service.getImage(description,body).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.newThread()).
                onErrorReturn(this::onFailure).
                subscribe(this::showImage);

    }

    private String onFailure(Throwable throwable) {
        Log.i("Network error", throwable.getMessage());
        failure = true;
        return throwable.getMessage();
    }

    private void showImage(String file) {
        if (!failure) {
            Log.i("RESULT", file);
            //Picasso.with(view.getContext()).load(file).into(image);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


}