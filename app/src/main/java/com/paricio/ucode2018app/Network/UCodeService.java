package com.paricio.ucode2018app.Network;


import java.io.File;
import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UCodeService {

    @Multipart
    @POST("upload")
    Single<String> getImage(@Part("description") RequestBody description, @Part MultipartBody.Part file);

}

