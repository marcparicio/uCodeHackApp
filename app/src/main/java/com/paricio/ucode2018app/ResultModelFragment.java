package com.paricio.ucode2018app;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

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

        Log.i("BFRE", description.contentType().charset().displayName());
        Log.i("BFRE", body.headers().toString());
        service.getImage(description,body).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.newThread()).
                onErrorReturn(this::onFailure).
                subscribe(this::showImage);

    }

    private String onFailure(Throwable throwable) {
        Log.i("Network error", throwable.getMessage());
        failure = true;
        progressBar.setVisibility(View.INVISIBLE);
        return throwable.getMessage();
    }

    private void showImage(String file) {
        if (!failure) {
            byte[] decodedString = Base64.decode(file, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            image.setImageBitmap(decodedByte);
            saveToInternalStorage(decodedByte);

        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());

        File myDir = cw.getDir("images" , Context.MODE_PRIVATE);
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        FileOutputStream fos = null;
        try {
            fos= new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}